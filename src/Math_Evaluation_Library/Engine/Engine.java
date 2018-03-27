package Math_Evaluation_Library.Engine;

import Math_Evaluation_Library.Constants.Const;
import Math_Evaluation_Library.Constants.Constants;
import Math_Evaluation_Library.Constants.Scripts;
import Math_Evaluation_Library.Constants.StringReplacements;
import Math_Evaluation_Library.ExpressionObjects.MultiParamFunction;
import Math_Evaluation_Library.ExpressionObjects.Operator;
import Math_Evaluation_Library.ExpressionObjects.UnaryFunction;
import Math_Evaluation_Library.ExpressionObjects.UnaryFunctions;
import Math_Evaluation_Library.Expressions.*;
import Math_Evaluation_Library.Miscellaneous.Fraction;
import Math_Evaluation_Library.Objects.TextFunction;
import Math_Evaluation_Library.Objects._Number_;
import Math_Evaluation_Library.Search;
import Math_Evaluation_Library.UnitConversion._UnitConversion_;

import java.util.*;

import static Math_Evaluation_Library.ExpressionObjects.MultiParamFunctions.getMultiParamFunction;
import static Math_Evaluation_Library.ExpressionObjects.MultiParamFunctions.isMultiParamFunction;
import static Math_Evaluation_Library.ExpressionObjects.Operators.getOperator;
import static Math_Evaluation_Library.ExpressionObjects.Operators.isOperator;
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
    static Map<String, Double> variables = new HashMap<>();
    static Map<String, Expression> variableFunctions = new HashMap<>();

    public static HashMap<Character, Character> implicit = initializeImplicit();
    public static HashMap<Character, Character> checkImplicit = initializeCheckImplicit();

    public static HashMap<Character, Character> initializeImplicit(){
        HashMap<Character, Character> implicit = new HashMap<>();
        char[] array = {')', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', var(), varOp(), '}', 'γ', 'π', 'ϕ', 'ℯ'};
        for (char c : array)    implicit.put(c, c);
        return implicit;
    }
    public static HashMap<Character, Character> initializeCheckImplicit(){
        HashMap<Character, Character> checkImplicit = new HashMap<>();
        char[] array = {'(', var(), varOp(), '{', 'γ', 'π', 'ϕ', 'ℯ', '√'};
        for (char c : array)    checkImplicit.put(c, c);
        return checkImplicit;
    }

    public static String fixSyntax(String function) {
        if (function.length() == 0){
            return "";
        }
        error = "Math Error";
        // Find abs brackets
        List<Integer> absBracketIndices = Search.getIndices(function, "|");
        if (absBracketIndices.size()%2 != 0)    return (error = "Absolute Value Bracket Error");
        for (int a = 0; a < absBracketIndices.size(); a += 2) {
            int open = absBracketIndices.get(a) + 5 * a / 2;
            int close = absBracketIndices.get(a + 1) + 5 * a / 2;
            function = function.substring(0, open) + "(abs(" + function.substring(open + 1, close) + "))" + function.substring(close + 1);
        }
        function = Search.replace(function, " ", "");
        function = Search.replace(function, StringReplacements.dynamicInputReplacement);
        function = Search.replace(function, StringReplacements.capitalInstances);
        function = function.toLowerCase();
        function = Search.replace(function, StringReplacements.formReplacements);
        function = Search.replace(function, "ans", String.valueOf(ans));
        //Implicit Multiplication
        try {
            for (int a = 1; a < function.length(); a++) {
                char f = function.charAt(a);
                char f1 = function.charAt(a-1);
                boolean implicitContainsf1 = implicit.containsKey(f1);
                if (implicitContainsf1) {
                    if (checkImplicit.containsKey(f)) {
                        function = function.substring(0, a) + "*" + function.substring(a);
                        f = function.charAt(a);
                    }
                }
                if (a > 1 && Scripts.isSuperScript(f1) && checkImplicit.containsKey(f)) {
                    char f2 = function.charAt(a-2);
                    if (implicit.containsKey(f2)){
                        function = function.substring(0, a) + "*" + function.substring(a);
                        f = function.charAt(a);
                    }
                }
            }
        } catch (StringIndexOutOfBoundsException e){ return (error = "Invalid Input Error - Found while fixing implicit multiplication"); }
        function = Search.replace(function, StringReplacements.formReplacements2);
        int numLbracket = 0;
        int numRbracket = 0;
        for (int a = 0; a < function.length(); a++) {
            char c = function.charAt(a);
            if (c == '(') {
                numLbracket++;
            } else if (c == ')') {
                numRbracket++;
            }
        }
        while (numRbracket < numLbracket){
            function += ")";
            numRbracket++;
        }
        return function;
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
                    if (f instanceof NumberExpression || infix.contains("Infinity") || function.trim().contains(infix) || infix.length() > 12) {
                        return "= " + f.valueOf();
                    } else if (evaluated.length() > 13) {
                        return "=  " + infix + " ≈ " + f.valueOf();
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
        infixFunction = Engine.fixSyntax(infixFunction);

        if (infixFunction.length() == 0)    return new InvalidExpression("Empty Input Error");

        String def = infixFunction;
        int lb = infixFunction.indexOf("(");
        if (lb != -1){
            def = infixFunction.substring(0, lb);
        }
        String mpf = Search.replace(def, StringReplacements.dynamicInputReplacement);
        if (isMultiParamFunction(mpf) && (lb == -1 || lb == infixFunction.length()-1)){
            return new StringExpression(getMultiParamFunction(mpf).getDescription());
        }
        if (isUnaryFunction(def) && (lb == -1 || lb == infixFunction.length()-1)){
            return new StringExpression(getUnaryFunction(def).getDescription());
        }
        int textIndex = TextFunctions.textFunctionsIndex(def);
        if (textIndex != -1){
            if (lb == -1 || lb == infixFunction.length()-1){
                return new StringExpression(TextFunctions.textFunctions[textIndex].getDescription());
            }
            //Check text cases
            String parameters = infixFunction.substring(lb+1);
            if (parameters.lastIndexOf(')') != -1)    parameters = parameters.substring(0, parameters.length()-1);
            TextFunction tf = TextFunctions.textFunctions[textIndex];
            if (tf.numParameters() == null) {
                return new StringExpression(tf.evaluate(parameters, displayFormat));
            }
            return new StringExpression(tf.evaluate(Search.split(parameters, ","), displayFormat));
        }

        String constant = Const.getConstant(infixFunction);
        if (!constant.equals("NaC"))    return new StringExpression(constant);
        try {   return new NumberExpression(_Number_.getNumber(infixFunction));    } catch (NumberFormatException ignored){}

        for (String[] formula : Formulas.formulas){
            if (formula[0].equals(infixFunction))    return new StringExpression(formula[1]);
        }

        Expression preprocessed = preprocessor(infixFunction);
        if (!(preprocessed instanceof InvalidExpression))    return preprocessed;

        if (infixFunction.toLowerCase().contains("error")) {
            return new InvalidExpression(infixFunction);
        }
        try {
            if (infixFunction.charAt(0) == '-' && !Character.isDigit(infixFunction.charAt(1))) {
                return new UnaryExpression(neg, toExpression(infixFunction.substring(1)));
            }
        } catch (StringIndexOutOfBoundsException e){}
        Stack<Expression> output = new Stack<>();
        Stack<String> stack = new Stack<>();
        StringBuilder token = new StringBuilder();//, str = new StringBuilder();
        try {
            for (int i = 0; i < infixFunction.length(); ++i) {
                char c = infixFunction.charAt(i);
//                System.out.println();
//                System.out.println(stack);
//                System.out.println(output);
//                System.out.println(c);
                if (Character.isDigit(c)) {
                    token.append(Character.getNumericValue(c));
                } else if (c == '.' || (c == '-' && (i == 0 ||
                        !Engine.implicit.containsKey(infixFunction.charAt(i - 1))))) {
                    token.append(c);
                } else if (c == ',') {
                    if (token.length() == 0) {
                        return new InvalidExpression("Syntax Error");
                    } else {
                        output.push(new NumberExpression(token.toString()));
                        token = new StringBuilder();
                    }
                } else if (c == 'i'){
                    if (token.length() == 0){
                        output.push(new ComplexExpression(1));
                    }
                    else{
                        output.push(new ComplexExpression(token.toString()));
                    }
                    token = new StringBuilder();
                } else if (c == Engine.var() || c == Engine.varOp()) {
                    if (token.length() > 0) {
                        if (token.toString().equals("-")) {
                            output.push(new UnaryExpression(neg, new VariableExpression(c)));
                        } else {
                            return new InvalidExpression("Variable Error:    Token: " + token + "  char: " + c);
                        }
                        token = new StringBuilder();
                    } else {
                        output.push(new VariableExpression(c));
                    }
                } else if (Constants.isConstant(c)) {
                    if (token.length() > 0) {
                        if (token.toString().equals("-")) {
                            output.push(new ConstantExpression(c, Constants.getNegativeConstant(c)));
                        } else {
                            return new InvalidExpression("Constant Character Token Error:    Token: " + token + "  char: " + c);
                        }
                        token = new StringBuilder();
                    } else {
                        output.push(new ConstantExpression(c, Constants.getConstant(c)));
                    }
                } else {
                    if (token.length() > 0) {
                        if (token.length() == 1 && token.toString().equals("-")){
                            stack.push("neg");
                        }
                        else{
                            output.push(new NumberExpression(token.toString()));
                        }
                        token = new StringBuilder();
                    }
                    StringBuilder superScript = new StringBuilder();
                    for (int j = i; j < infixFunction.length(); ++j) {
                        int scriptIndex = Scripts.getSuperScriptIndex(infixFunction.charAt(j));
                        if (scriptIndex != -1) {
                            superScript.append(Scripts.regularScripts[scriptIndex]);
                        } else {
                            break;
                        }
                    }
                    if (superScript.length() > 0) {
                        infixFunction = infixFunction.substring(0, i)+"^"+superScript.toString()+infixFunction.substring(i+superScript.length());
                        --i;
                        continue;
                    }
                    if (c == '(') {
                        int rb = infixFunction.indexOf(")", i);
                        if (rb == -1)   return new InvalidExpression("Bracket Count Error:  Missing ')' Bracket");
                        String inBracket = infixFunction.substring(i+1, rb);
                        if (variables.containsKey(inBracket)) {
                            output.push(new NumberExpression(variables.get(inBracket)));
                            i = rb;
                        }
                        else if (variableFunctions.containsKey(inBracket)) {
                            output.push(variableFunctions.get(inBracket));
                            i = rb;
                        }
                        else{
                            stack.push("(");
                        }
                        continue;
                    } else if (c == ')') {
                        if (stack.contains("(")) {
                            while (!stack.isEmpty()) {
                                String s = stack.pop();
                                if (s.equals("(")) break;
                                stackElementToExpression(s, output);
                            }
                        } else {
                            return new InvalidExpression("Bracket Count Error:  Missing '(' Bracket");
                        }
                        continue;
                    } else if (c == '{') {
                        int end = Search.indexOf(infixFunction, '}', i);
                        if (end != -1) {
                            String single = infixFunction.substring(i + 1, end);
                            if (!single.contains(",")) {
                                if (variables.containsKey(single)){
                                    output.push(new NumberExpression(variables.get(single)));
                                    i = end;
                                }
                                else{
                                    try {
                                        Double.parseDouble(single);
                                    } catch (NumberFormatException e) {
                                        output.push(new VariableExpression(single));
                                        i = end;
                                    }
                                }
                            } else if (infixFunction.length() > end + 1 && (infixFunction.charAt(end + 1) == 'ᵀ' || infixFunction.charAt(end + 1) == 'ᴵ')) {
                                output.push(new MatrixExpression(infixFunction.substring(i, end + 2)));
                                i = end + 1;
                            } else {
                                output.push(new MatrixExpression(infixFunction.substring(i, end + 1)));
                                i = end;
                            }
                            continue;
                        }
                        return new InvalidExpression("Bracket Count Error:  Missing '}' Bracket");
                    } else if (c == '[') {
                        int unitIndex = infixFunction.indexOf("→", i);
                        if (unitIndex != -1) {
                            int close = infixFunction.indexOf("]", i);
                            if (unitIndex < close) {
                                String[] parameters = _UnitConversion_.isUnitConversion(infixFunction.substring(i + 1, close), unitIndex);
                                if (parameters != null) {
                                    output.push(new MultiParamExpression(getMultiParamFunction("unit"), parameters));
                                    i = close;
                                    continue;
                                } else {
                                    return new InvalidExpression("Invalid Input Error - Invalid Unit Conversion:  " + infixFunction.substring(i + 1, close));
                                }
                            }
                        }
                        char cPlus1 = i + 1 < infixFunction.length() ? infixFunction.charAt(i + 1) : '\0';
                        int semicolon = infixFunction.indexOf(";", i);
                        if (semicolon != -1 || cPlus1 == '[') {
                            int end = Search.indexOf(infixFunction, ']', i);
                            if (end != -1) {
                                if (infixFunction.length() > end + 1 && (infixFunction.charAt(end + 1) == 'ᵀ' || infixFunction.charAt(end + 1) == 'ᴵ')) {
                                    output.push(new MatrixExpression(infixFunction.substring(i, end + 2)));
                                    i = end + 1;
                                } else {
                                    output.push(new MatrixExpression(infixFunction.substring(i, end + 1)));
                                    i = end;
                                }
                                continue;
                            }
                            return new InvalidExpression("Bracket Count Error - Unclosed Hard Bracket");
                        }
                        int comma1 = infixFunction.indexOf(",", i);
                        int close = infixFunction.indexOf("]", i);
                        if (comma1 == -1 || close == -1) {
                            return new InvalidExpression("Syntax Error - Evaluate");
                        }
                        int comma2 = infixFunction.indexOf(",", comma1 + 1);
                        if (comma2 == -1) {
                            Expression e1 = toExpression(infixFunction.substring(i + 1, comma1));
                            Expression e2 = toExpression(infixFunction.substring(comma1 + 1, close));
                            e1.set(Engine.var, e2.evaluate());
                            output.push(e1);
                        } else {
                            Expression a = toExpression(infixFunction.substring(i + 1, comma1));
                            Expression e2 = toExpression(infixFunction.substring(comma1 + 1, comma2));
                            Expression e3 = toExpression(infixFunction.substring(comma2 + 1, close));
                            if (a.containsVar(Engine.varOp)) {
                                a.set(Engine.var, e2.evaluate());
                                a.set(Engine.varOp, e3.evaluate());
                                output.push(a);
                            } else {
                                Expression b = toExpression(infixFunction.substring(i + 1, comma1));
                                a.set(Engine.var, e2.evaluate());
                                b.set(Engine.var, e3.evaluate());
                                output.push(new OperatorExpression(subtract, b, a));
                            }
                        }
                        i = close;
                        continue;
                    }
                    lb = infixFunction.indexOf("(", i);
                    if (infixFunction.indexOf(")", i) < lb) lb = -1;
                    if (lb != -1) {
                        String function = infixFunction.substring(i, lb);
                        if (variableFunctions.containsKey(function)) {
                            int rb = Search.indexOf(infixFunction, ')', lb);
                            Expression e = toExpression(infixFunction.substring(lb + 1, rb));
                            Expression f = variableFunctions.get(function);
                            f.set(var, e);
                            output.push(f);
                            i = rb;
                            continue;
                        }
                        if (isMultiParamFunction(function)) {
                            int rb = Search.indexOf(infixFunction, ')', lb);
                            if (rb != -1 && rb > lb) {
                                MultiParamFunction multiParamFunction = getMultiParamFunction(function);
                                String[] parameters = Search.split(infixFunction.substring(lb + 1, rb), ",", false);
                                if (!multiParamFunction.isFlexible() && multiParamFunction.getNumParameters() != parameters.length) {
                                    return new InvalidExpression("Syntax Error - Invalid Number of Parameters.  E:" + multiParamFunction.getNumParameters() + "   A:" + parameters.length);
                                }
                                output.push(new MultiParamExpression(multiParamFunction, parameters));
                                i = rb;
                                continue;
                            }
                            return new InvalidExpression("Bracket Count Error: Multi Param Function");
                        }
                    }
                    if (isOperator(c)) {
                        Operator operator1 = getOperator(c);
                        while (!stack.isEmpty() && !isOperator(stack.peek()) && !stack.peek().equals("(")) {
                            String top = stack.pop();
                            stackElementToExpression(top, output);
                        }
                        if (stack.isEmpty() || !isOperator(stack.peek())) {
                            stack.push(operator1.toString());
                        } else {
                            while (true) {
                                if (stack.empty()) {
                                    stack.push(operator1.toString());
                                    break;
                                }
                                Operator o2 = getOperator(stack.peek());
                                if (o2 != null && ((operator1.isAssociable() && operator1.getPrecedence() <= o2.getPrecedence()) ||
                                        (!operator1.isAssociable() && operator1.getPrecedence() < o2.getPrecedence()))) {
                                    stack.pop();
                                    Expression e1 = output.pop();
                                    if (o2.isSingleOperator()) {
                                        output.push(new OperatorExpression(o2, e1));
                                        continue;
                                    }
                                    Expression e2 = output.pop();
                                    output.push(new OperatorExpression(o2, e2, e1));
                                } else {
                                    stack.push(operator1.toString());
                                    break;
                                }
                            }
                        }
                    } else {
                        for (int b = UnaryFunction.maxStrLength; b >= UnaryFunction.minStrLength; b--) {
                            if (i + b <= infixFunction.length()) {
                                String mathf = infixFunction.substring(i, i + b);
                                if (isUnaryFunction(mathf)) {
                                    superScript = new StringBuilder();
                                    for (int j = i + mathf.length(); j < infixFunction.length(); j++) {
                                        int index = Scripts.getSuperScriptNumIndex(infixFunction.charAt(j));
                                        if (index != -1) {
                                            superScript.append(Scripts.regularScriptNums[index]);
                                        } else {
                                            break;
                                        }
                                    }
                                    if (stack.empty() || stack.peek().equals("(") || stack.peek().equals("neg")
                                            || (stack.size() > 0 && isOperator(stack.peek()))) {
                                        if (superScript.length() > 0) {
                                            stack.push("^");
                                            stack.push(superScript.toString());
                                        }
                                        stack.push(mathf);
                                        i += mathf.length() + superScript.length() - 1;
                                    } else if (!stack.isEmpty()) {
                                        Expression e = output.pop();
                                        output.push(new UnaryExpression(getUnaryFunction(stack.pop()), e));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }catch(EmptyStackException e){
            System.out.println("Empty Stack Exception:   "+infixFunction);
        }
        if (token.length() > 0) {
            output.push(new NumberExpression(token.toString()));
        }
        while (!stack.empty()) {
            String s = stack.pop();
            stackElementToExpression(s, output);
        }
//        System.out.println();
//        System.out.println(stack);
//        System.out.println(output);
        while (output.size() > 1){
            Expression e2 = output.pop();
            Expression e1 = output.pop();
            output.push(new OperatorExpression("*", e1, e2));
        }
        return output.peek();
    }

    private static void stackElementToExpression(String str, Stack<Expression> output){
        if (!output.empty()){
            if (isOperator(str)){
                Operator operator = getOperator(str);
                Expression e1 = output.pop();
                if (operator.isSingleOperator()){
                    output.push(new OperatorExpression(operator, e1));
                }
                else if (!output.empty()){
                    Expression e2 = output.pop();
                    output.push(new OperatorExpression(operator, e2, e1));
                }
                else{
                    output.push(e1);
                }
            }
            else if (isUnaryFunction(str)){
                Expression e = output.pop();
                output.push(new UnaryExpression(getUnaryFunction(str), e));
            }
        }
        if (_Number_.isNumber(str)){
            output.push(new NumberExpression(str));
        }
    }

    public static char var(){
        return var.charAt(0);
    }
    public static char varOp(){
        return varOp.charAt(0);
    }

}
