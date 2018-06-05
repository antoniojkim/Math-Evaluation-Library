package Math_Evaluation_Library.Engine;

import Math_Evaluation_Library.Constants.Const;
import Math_Evaluation_Library.ExpressionObjects.Operator;
import Math_Evaluation_Library.ExpressionObjects.TextFunction;
import Math_Evaluation_Library.ExpressionObjects.UnaryFunction;
import Math_Evaluation_Library.ExpressionObjects.UnaryFunctions;
import Math_Evaluation_Library.Expressions.*;
import Math_Evaluation_Library.Miscellaneous.Fraction;
import Math_Evaluation_Library.Objects._Number_;
import Math_Evaluation_Library.Search;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
//        if (function.length() == 0)    return "";
//        error = "Math Error";
//        // Find abs brackets
//        List<Integer> absBracketIndices = Search.getIndices(function, "|");
//        if (absBracketIndices.size()%2 != 0)    return (error = "Absolute Value Bracket Error");
//        for (int a = 0; a < absBracketIndices.size(); a += 2) {
//            int open = absBracketIndices.get(a) + 5 * a / 2;
//            int close = absBracketIndices.get(a + 1) + 5 * a / 2;
//            function = function.substring(0, open) + "(abs(" + function.substring(open + 1, close) + "))" + function.substring(close + 1);
//        }
//        function = Search.replace(function, " ", "");
////        function = Search.replace(function, StringReplacements.dynamicInputReplacement);
////        function = Search.replace(function, StringReplacements.capitalInstances);
//        check: for (int i = 0; i<function.length(); ++i) {
//            for (int j = Math.min(StringReplacements.maxDynamicInputLen, function.length() - i);
//                 j >= StringReplacements.minDynamicInputLen; --j) {
//                String f = function.substring(i, i + j);
//                if (dynamicInputMap.containsKey(f)) {
//                    function = function.substring(0, i) + dynamicInputMap.get(f) + function.substring(i + j);
//                    i += j - 1;
//                    continue check;
//                }
//            }
//            for (int j = Math.min(StringReplacements.maxCapitalInstancesLen, function.length() - i);
//                 j >= StringReplacements.minCapitalInstancesLen; --j) {
//                String f = function.substring(i, i + j);
//                if (capitalInstancesMap.containsKey(f)) {
//                    function = function.substring(0, i) + capitalInstancesMap.get(f) + function.substring(i + j);
//                    i += j - 1;
//                    continue check;
//                }
//            }
////            for (int j = Math.min(StringReplacements.maxFormReplacementsLen, function.length()-i);
////                 j >= StringReplacements.minFormReplacementsLen; --j){
////                String f = function.substring(i, i+j);
////                if (formReplacementsMap.containsKey(f)){
////                    function = function.substring(0, i)+formReplacementsMap.get(f)+function.substring(i+j);
////                    i += j-1;
////                    continue check;
////                }
////            }
////            for (int j = Math.min(StringReplacements.maxFormReplacements2Len, function.length()-i);
////                 j >= StringReplacements.minFormReplacements2Len; --j){
////                String f = function.substring(i, i+j);
////                if (formReplacements2Map.containsKey(f)){
////                    function = function.substring(0, i)+formReplacements2Map.get(f)+function.substring(i+j);
////                    i += j-1;
////                    continue check;
////                }
////            }
//        }
//        boolean previousCharacterIsNum = false;
//        check: for (int i = 0; i<function.length(); ++i){
//            char f1 = function.charAt(i);
//            if (_Number_.isNumber(f1, false)){
//                previousCharacterIsNum = true;
//            }
//            else if (previousCharacterIsNum){
//                previousCharacterIsNum = false;
//                if (f1 == 'E'){
//                    function = function.substring(0, i)+"*10^"+function.substring(i+1);
//                    i += 3;
//                    continue check;
//                }
//                else if (isOperator(f1)){
//                    continue check;
//                }
//            }
//            else{
//                for (int j = Stats.min(StringReplacements.maxFormReplacementsLen, function.length()-i);
//                     j >= StringReplacements.minFormReplacementsLen; --j){
//                    String f = function.substring(i, i+j);
//                    if (formReplacementsMap.containsKey(f)){
//                        function = function.substring(0, i)+formReplacementsMap.get(f)+function.substring(i+j);
//                        i -= 1;
//                        continue check;
//                    }
//                }
//                for (int j = Stats.min(
//                        UnaryFunction.maxStrLength,
//                        MultiParamFunction.maxStrLength,
//                        TextFunction.maxStrLength,
//                        GreekLetters.maxStrLength,
//                        function.length()-i);
//                     j >= Stats.min(
//                             UnaryFunction.minStrLength,
//                             MultiParamFunction.minStrLength,
//                             TextFunction.minStrLength,
//                             GreekLetters.minStrLength); --j){
//                    String f = function.substring(i, i+j);
//                    boolean b = false;
//                    if (isUnaryFunction(f) || (b = isUnaryFunction(f.toLowerCase()))){
//                        if (b) function = function.substring(0, i)+f.toLowerCase()+function.substring(i+j);
//                        i += j-1;
//                        continue check;
//                    }
//                    if (isMultiParamFunction(f) || (b = isMultiParamFunction(f.toLowerCase()))){
//                        if (b) function = function.substring(0, i)+f.toLowerCase()+function.substring(i+j);
//                        i += j-1;
//                        continue check;
//                    }
//                    if (isTextFunction(f) || (b = isTextFunction(f.toLowerCase()))){
//                        if (b) function = function.substring(0, i)+f.toLowerCase()+function.substring(i+j);
//                        i += j-1;
//                        continue check;
//                    }
//                    if (isGreekLetter(f)){
//                        function = function.substring(0, i)+getGreekLetter(f)+function.substring(i+j);
//                        continue check;
//                    }
//                }
//                for (int j = Math.min(StringReplacements.maxFormReplacements2Len, function.length()-i);
//                     j >= StringReplacements.minFormReplacements2Len; --j){
//                    String f = function.substring(i, i+j);
//                    if (formReplacements2Map.containsKey(f)){
//                        function = function.substring(0, i)+formReplacements2Map.get(f)+function.substring(i+j);
//                        i -= 1;
//                        continue check;
//                    }
//                }
//            }
//            if (i+1 < function.length()){
//                char f = function.charAt(i+1);
//                //Implicit Multiplication
//                if (implicit.containsKey(f1)) {
//                    if (checkImplicit.containsKey(f)) {
//                        function = function.substring(0, i+1) + "*" + function.substring(i+1);
//                        f = function.charAt(i+1);
//                    }
//                }
//                if (i > 0 && Scripts.isSuperScript(f1) && checkImplicit.containsKey(f)) {
//                    char f2 = function.charAt(i-1);
//                    if (implicit.containsKey(f2) || Scripts.isSuperScript(f2)){
//                        function = function.substring(0, i+1) + "*" + function.substring(i+1);
//                        f2 = function.charAt(i+1);
//                    }
//                }
//            }
//        }
//
////        function = Search.replace(function, StringReplacements.formReplacements);
//        function = Search.replace(function, "ans", String.valueOf(ans));
////        try {
////            for (int a = 1; a < function.length(); a++) {
////                char f = function.charAt(a);
////                char f1 = function.charAt(a-1);
////                //Implicit Multiplication
////                if (implicit.containsKey(f1)) {
////                    if (checkImplicit.containsKey(f)) {
////                        function = function.substring(0, a) + "*" + function.substring(a);
////                        f = function.charAt(a);
////                    }
////                }
////                if (a > 1 && Scripts.isSuperScript(f1) && checkImplicit.containsKey(f)) {
////                    char f2 = function.charAt(a-2);
////                    if (implicit.containsKey(f2) || Scripts.isSuperScript(f2)){
////                        function = function.substring(0, a) + "*" + function.substring(a);
////                        f2 = function.charAt(a);
////                    }
////                }
////            }
////        } catch (StringIndexOutOfBoundsException e){ return (error = "Invalid Input Error - Found while fixing implicit multiplication"); }
////        function = Search.replace(function, StringReplacements.formReplacements2);
//        int numLbracket = 0;
//        int numRbracket = 0;
//        for (int a = 0; a < function.length(); a++) {
//            char c = function.charAt(a);
//            if (c == '(') {
//                numLbracket++;
//            } else if (c == ')') {
//                numRbracket++;
//            }
//        }
//        StringBuilder functionBuilder = new StringBuilder(function);
//        while (numRbracket < numLbracket){
//            functionBuilder.append(")");
//            numRbracket++;
//        }
//        function = functionBuilder.toString();
//        return function;
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

    public static char var(){
        return var.charAt(0);
    }
    public static char varOp(){
        return varOp.charAt(0);
    }

}
