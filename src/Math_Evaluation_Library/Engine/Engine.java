package Math_Evaluation_Library.Engine;

import Math_Evaluation_Library.Constants.Const;
import Math_Evaluation_Library.Constants.GreekLetters;
import Math_Evaluation_Library.Constants.StringReplacements;
import Math_Evaluation_Library.ExpressionObjects.Operator;
import Math_Evaluation_Library.ExpressionObjects.TextFunction;
import Math_Evaluation_Library.ExpressionObjects.UnaryFunction;
import Math_Evaluation_Library.ExpressionObjects.UnaryFunctions;
import Math_Evaluation_Library.Expressions.*;
import Math_Evaluation_Library.Expressions.NumberExpressions.ComplexExpression;
import Math_Evaluation_Library.Expressions.NumberExpressions.NumberExpression;
import Math_Evaluation_Library.Miscellaneous.Fraction;
import Math_Evaluation_Library.Objects.Pair;
import Math_Evaluation_Library.Objects._Number_;
import Math_Evaluation_Library.Search;

import java.util.*;

import static Math_Evaluation_Library.ExpressionObjects.MultiParamFunctions.getMultiParamFunction;
import static Math_Evaluation_Library.ExpressionObjects.MultiParamFunctions.isMultiParamFunction;
import static Math_Evaluation_Library.ExpressionObjects.Operators.getOperator;
import static Math_Evaluation_Library.ExpressionObjects.TextFunctions.getTextFunction;
import static Math_Evaluation_Library.ExpressionObjects.TextFunctions.isTextFunction;
import static Math_Evaluation_Library.ExpressionObjects.UnaryFunctions.getUnaryFunction;
import static Math_Evaluation_Library.ExpressionObjects.UnaryFunctions.isUnaryFunction;
import static java.lang.Double.NaN;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**

 @author Antonio's Laptop
 */
public class Engine {

    public static final String x = "\uD835\uDC65";
    public static String var = "x", varOp = "y";
    public static String error = "Error";
    static boolean debug = false;
    static double ans = 1;
    public static Map<String, Double> variables = new HashMap<>();
    public static Map<String, Expression> variableFunctions = new HashMap<>();

    public static HashMap<Character, Character> implicit = initializeImplicit();
    public static HashMap<Character, Character> checkImplicit = initializeCheckImplicit();

    public static HashMap<Character, Character> initializeImplicit(){
        HashMap<Character, Character> implicit = new HashMap<>();
        char[] array = {')', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', var(), varOp(), '}', 'γ', 'π', 'ϕ', 'e'};
        for (char c : array)    implicit.put(c, c);
        return implicit;
    }
    public static HashMap<Character, Character> initializeCheckImplicit(){
        HashMap<Character, Character> checkImplicit = new HashMap<>();
        char[] array = {'(', var(), varOp(), '{', 'γ', 'π', 'ϕ', 'e', '√'};
        for (char c : array)    checkImplicit.put(c, c);
        return checkImplicit;
    }

    public static String fixSyntax(String function) {
        List<Scanner.Token> tokens = Scanner.scan(function);
        StringBuilder sb = new StringBuilder();
        for (Scanner.Token t : tokens){
            sb.append(t.getLexeme());
        }
        return sb.toString();
    }

    public static double evaluate(String function, double num) {
        return evaluate(function, _Number_.format(num), "");
    }
    public static double evaluate(String function, double x, double y) {
        return evaluate(function, _Number_.format(x), _Number_.format(y));
    }

    public static double evaluate(String function) {
        return evaluate(function, "", "");
    }
    public static double evaluate(String function, String x, String y) {

        function = function.trim();
        if (function.length() == 0)    return NaN;
        if (function.toLowerCase().contains("error"))     return NaN;

        Expression preprocessed = preprocessor(function);
        if (!(preprocessed instanceof InvalidExpression)){
            return preprocessed.valueOf();
        }

        try {
            return _Number_.getNumber(function.trim());
        } catch (NumberFormatException e){}
        Expression e = toExpression(function);

        if (x.length() > 0) e.set(var, toExpression(x));
        if (y.length() > 0) e.set(varOp, toExpression(y));
        Expression evaluated = e.evaluate();
        return (ans = evaluated.valueOf());
    }

    public static final String separator = "│";
    private static Expression preprocessor(String function){
        function = fixSyntax(function);
        int index = function.indexOf("⩵");
        if (index != -1){
            if (function.substring(0, index).equals(function.substring(index+1))){
                return new StringExpression("Strings are equal");
            }
            else if (function.substring(0, index).equalsIgnoreCase(function.substring(index+1))){
                return new StringExpression("Strings are equal ignoring case");
            }
            return new StringExpression("Strings are not equal");
        }
        index = function.indexOf("≔");
        if (index != -1){
            String lhs = function.substring(0, index);
            String rhs = function.substring(index+1);
            Expression value = toExpression(rhs);
            Expression evaluated = value.evaluate();
            if (evaluated instanceof NumberExpression){
                variables.put(lhs, evaluated.valueOf());
                return evaluated;
            } else if (variables.containsKey(lhs)) {
                variables.remove(lhs);
                return new StringExpression("");
            } else {
                if (rhs.length() == 0 && variableFunctions.containsKey(lhs)){
                    variableFunctions.remove(lhs);
                    return new StringExpression("");
                }
                value.unset();
                variableFunctions.put(lhs, value);
            }
            return value;
        }
        index = function.indexOf(separator);
        if (index != -1){
            String post = function.substring(index+1);
            function = function.substring(0, index);
            if (_Number_.isNumber(post))    return new NumberExpression(evaluate(function, post, ""));
            String[] conditions = Search.split(Search.replace(post, " ", ""), ",");
            if (conditions.length == 2 && _Number_.isNumber(conditions[0]) && _Number_.isNumber(conditions[1])){
                Expression e = toExpression(function), c1 = toExpression(conditions[0]), c2 = toExpression(conditions[1]);
                if (e.containsVar(Engine.varOp)) {
                    e.set(Engine.var, c1);
                    e.set(Engine.varOp, c2);
                    return e.evaluate();
                }
                return new OperatorExpression(getOperator("-"), e.evaluate(c2), e.evaluate(c1)).evaluate();
            }
            for (String condition : conditions){
                if (_Number_.isNumber(condition)){
                    function = Search.replace(function, new String[][]{
                            {"${"+var+"}", "("+condition+")"},
                            {"{"+var+"}", "("+condition+")"},
                            {"$"+var, "("+condition+")"},
                            {"("+var+")", "("+condition+")"}
                    });
                    continue;
                }
                int eqindex = condition.indexOf("=");
                if (eqindex != -1){
                    String value = condition.substring(eqindex+1);
                    if (_Number_.isNumber(value)){
                        String var = condition.substring(0, eqindex);
                        function = Search.replace(function, new String[][]{
                                {"${"+var+"}", "("+value+")"},
                                {"{"+var+"}", "("+value+")"},
                                {"$"+var, "("+value+")"},
                                {"("+var+")", "("+value+")"}
                        });
                    }
                }
            }
            return new NumberExpression(evaluate(function));
        }
        return new InvalidExpression("Nothing to Preprocess");
    }

    public static String evaluateString(String function){
        return evaluateString(function, "");
    }
    public static String evaluateDF(String function){
        return evaluateString(function, "= ");
    }
    public static String evaluateString(String function, String df){
        return evaluateString(function, "", df);
    }
    public static String evaluateString(String function, String x, String df){
        boolean displayFormat = df.length() > 0;

        function = function.trim();
        if (function.length() == 0)    return "NaN";
        if (function.charAt(0) == '#') {
            return "";
        }

        Expression preprocessed = preprocessor(function);
        if (!(preprocessed instanceof InvalidExpression))    return preprocessed.toString();

        Expression e = toExpression(function);
        if (e instanceof InvalidExpression) {
            return ((InvalidExpression)e).getError();
        }

        if (x.length() > 0) {
            Expression xe = toExpression(x);
            e.set(var, xe);
        }
        Expression ev = e.evaluate();
        if (ev instanceof NumberExpression){
            if (displayFormat && !(ev instanceof ComplexExpression)) {
                String evaluated = ev.toString();
                int index = evaluated.indexOf("E");
                if (index != -1) {
                    String standard = _Number_.convertToStandard(evaluated);
                    if (!evaluated.equals(standard)) return "= " + evaluated + " = " + standard;
                    return "= " + evaluated;
                } else {
                    Expression f = Fraction.toExpression(ev.valueOf());
                    String infix = f.infix();
                    if (f instanceof NumberExpression || infix.contains("Infinity") || function.trim().contains(infix)) {
                        return "= " + evaluated;
                    } else if (evaluated.length() > 13) {
                        return "=  " + infix + " ≈ " + evaluated;
                    }
                    return "=  " + infix + " = " + evaluated;
                }
            }
            return df+ev.toString();
        }
        else if (ev instanceof StringExpression){
            return df+ev.infix();
        }
        return df+e.simplify().infix();
    }

    static UnaryFunction neg = UnaryFunctions.getUnaryFunction("neg");
    static Operator subtract = getOperator("-");

    public static Expression toExpression(String infixFunction){
        return toExpression(infixFunction, false);
    }
    public static Expression toExpression(String infixFunction, boolean displayFormat){

        if (infixFunction.length() == 0)    return new InvalidExpression("Empty Input Error");

        String def = infixFunction;
        int lb = infixFunction.indexOf("(");
        if (lb != -1){
            def = Engine.fixSyntax(infixFunction.substring(0, lb));
        }
        if (lb == -1 || lb == infixFunction.length()-1){
            if (isUnaryFunction(def)){
                return new StringExpression(getUnaryFunction(def).getDescription());
            } if (isTextFunction(def)){
                return new StringExpression(getTextFunction(def).getDescription());
            } if (isMultiParamFunction(def)){
                return new StringExpression(getMultiParamFunction(def).getDescription());
            }
        }
        else if (isTextFunction(def)){//Check text cases
            String parameters = infixFunction.substring(lb+1);
            if (parameters.endsWith(")"))    parameters = parameters.substring(0, parameters.length()-1);
            TextFunction tf = getTextFunction(def);
            if (tf.numParameters() == null) {
                return new StringExpression(tf.evaluate(parameters, displayFormat));
            }
            return new StringExpression(tf.evaluate(Search.split(parameters, ","), displayFormat));
        }

        infixFunction = Engine.fixSyntax(infixFunction);

        Expression preprocessed = preprocessor(infixFunction);
        if (!(preprocessed instanceof InvalidExpression))    return preprocessed;

        if (infixFunction.toLowerCase().contains("error")) {
            return new InvalidExpression(infixFunction);
        }
        try {
            if (infixFunction.charAt(0) == '-' && !Character.isDigit(infixFunction.charAt(1))) {
                return toExpression(infixFunction.substring(1)).negate();
            }
        } catch (StringIndexOutOfBoundsException e){}

        if (displayFormat){
            String constant = Const.getConstant(infixFunction);
            if (!constant.equals("NaC"))    return new StringExpression(constant);
            try {   return new NumberExpression(_Number_.getNumber(infixFunction));    } catch (NumberFormatException ignored){}
        }
        for (String[] formula : Formulas.formulas){
            if (formula[0].equals(infixFunction))    return new StringExpression(formula[1]);
        }

        List<Scanner.Token> tokens = Scanner.scan(infixFunction);
//        System.out.println(infixFunction+"    "+tokens);
        return Expression.toExpression(tokens);
    }

    static List<String> replaceable = new ArrayList<>();
    static List<String> replaceWith = new ArrayList<>();
    public static Pair<String, Integer> autocorrect(String input, int caret){
        if (input.length() > 0 && (input.charAt(0) == '#')){
            return new Pair<>(input, caret);
        }
        caret = Math.min(caret, input.length());
        if (replaceable.isEmpty()){
            for (String[] replaceStrs : StringReplacements.dynamicInputReplacement){
                replaceable.add(replaceStrs[0]);
                replaceWith.add(replaceStrs[1]);
            }
//        for (String[] pair : GreekLetters.greekLetterPairs){
//            replaceable.add(pair[0]);
//            replaceWith.add(pair[1]);
//        }
            if (input.contains("x")){
                replaceable.addAll(Arrays.asList("**"));
                replaceWith.addAll(Arrays.asList("^"));
            }
            else{
                replaceable.addAll(Arrays.asList("×*", "××", "*"));
                replaceWith.addAll(Arrays.asList("^", "^", "×"));
            }
        }
        for (int i = Math.min(caret, GreekLetters.maxStrLength); i >= GreekLetters.minStrLength; --i){
            String s = input.substring(caret-i, caret);
            if (GreekLetters.isGreekLetter(s)){
                input = input.substring(0, caret-i)+GreekLetters.getGreekLetter(s)+input.substring(caret);
                caret -= (i-1);
                break;
            }
        }
        for (int a = 0; a<replaceable.size();){
            int index = input.indexOf(replaceable.get(a));
            if (index != -1){
                input = input.substring(0, index)+replaceWith.get(a)+input.substring(index+replaceable.get(a).length());
                caret = index+replaceWith.get(a).length();
            }
            else{
                a++;
            }
        }
        return new Pair<>(input, caret);
    }

    public static char var(){
        return var.charAt(0);
    }
    public static char varOp(){
        return varOp.charAt(0);
    }

}
