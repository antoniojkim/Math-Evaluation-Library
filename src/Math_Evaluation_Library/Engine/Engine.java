package Math_Evaluation_Library.Engine;

import Math_Evaluation_Library.Constants.Const;
import Math_Evaluation_Library.Constants.Constants;
import Math_Evaluation_Library.Constants.Scripts;
import Math_Evaluation_Library.Constants.StringReplacements;
import Math_Evaluation_Library.Objects.*;
import Math_Evaluation_Library.Search;
import Math_Evaluation_Library.Sort;
import Math_Evaluation_Library.UnitConversion._UnitConversion_;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import static Math_Evaluation_Library.Engine.MultiParamFunctions.multiParamFunctionNamesIndex;
import static Math_Evaluation_Library.Engine.MultiParamFunctions.multiParamFunctions;
import static Math_Evaluation_Library.Engine.Operators.*;
import static Math_Evaluation_Library.Engine.UnaryFunctions.unaryFunctions;
import static Math_Evaluation_Library.Engine.UnaryFunctions.unaryFunctionsIndex;
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
    static boolean debug = false, sorted = false;
    static double ans = 1;
    static List<String> variables = new ArrayList<>();
    static List<Double> values = new ArrayList<>();

    public static void sortFunctions() {
        Sort.quicksort(implicit);
        Sort.quicksort(checkImplicit);
        sorted = true;
    }

    static char[] implicit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ')', '}', 'π', 'η', 'ϕ', 'γ', var(), varOp()};
    static char[] checkImplicit = {'(', '{', var(), varOp(), 'π', 'η', 'ϕ', 'γ', '√'};

    public static String fixSyntax(String function) {
        if (function.length() == 0){
            return "";
        }
        if (!sorted)    sortFunctions();
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
                boolean implicitContainsf1 = implicitContains(f1);
                if (implicitContainsf1) {
                    if (checkImplicitContains(f)) {
                        function = function.substring(0, a) + "*" + function.substring(a);
                        f = function.charAt(a);
                    }
                }
                if (a > 1 && Scripts.isSuperScript(f1) && checkImplicitContains(f)) {
                    char f2 = function.charAt(a-2);
                    if (implicitContains(f2)){
                        function = function.substring(0, a) + "*" + function.substring(a);
                        f = function.charAt(a);
                    }
                }
                if (f1 == ')' && (f != ')' && implicitContains(f))) {
                    function = function.substring(0, a) + "*" + function.substring(a);
                    f = function.charAt(a);
                }
                else if (a+1 < function.length()){
                    char f2 = function.charAt(a+1);
                    if (f1 == '(' && f == '-' && !_Number_.isNumber(f2)) {
                        function = function.substring(0, a) + "1*" + function.substring(a);
                        f = function.charAt(a);
                    }
                    else if (f == '-' && operatorContains(f1) && !_Number_.isNumber(f2)) {
                        function = function.substring(0, a+1) + "1*" + function.substring(a+1);
                    }
                }
            }
        } catch (StringIndexOutOfBoundsException e){ return (error = "Invalid Input Error - Found while fixing implicit multiplication"); }
        function = Search.replace(function, StringReplacements.formReplacements2);
        for (int a = 1; a<function.length(); a++){
            boolean implicitContainsf1 = implicitContains(function.charAt(a-1));
            if (implicitContainsf1){
                if (a+3 <= function.length() && function.substring(a, a + 3).equals("ans") && implicitContains(function.charAt(a-1))){
                    function = function.substring(0, a) + "*" + function.substring(a);
                    a += 4;
                    continue;
                }
                int limit = Math.min(function.length()-a, UnaryFunction.maxStrLength);
                for (int b = UnaryFunction.minStrLength; b < limit; b++) {
                    if (unaryFunctionsIndex(function.substring(a, a+b)) != -1){
                        function = function.substring(0, a) + "*" + function.substring(a);
                        a += b+1;
                        break;
                    }
                }
                limit = Math.min(function.length()-a, MultiParamFunction.maxStrLength);
                for (int b = MultiParamFunction.minStrLength; b < limit; b++) {
                    if (multiParamFunctionNamesIndex(function.substring(a, a+b)) != -1){
                        function = function.substring(0, a) + "*" + function.substring(a);
                        a += b+1;
                        break;
                    }
                }
            }
        }
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

    protected static String replaceVariables(String function){
        // Replace all variables
        for (int a = 0; a < variables.size(); a++) {
            function = Search.replace(function, new String[][]{
                    {"("+variables.get(a)+")", "("+_Number_.format(values.get(a))+")"},
                    {"${"+variables.get(a)+"}", "("+_Number_.format(values.get(a))+")"},
                    {"{"+variables.get(a)+"}", "("+_Number_.format(values.get(a))+")"},
                    {"$"+variables.get(a), "("+_Number_.format(values.get(a))+")"}
            });
        }
        return function;
    }

    public static String getError() {
        return error;
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

        function = replaceVariables(function);
        String preprocessed = preprocessor(function);
        if (_Number_.isNumber(preprocessed))    return _Number_.getNumber(preprocessed);

        try {
            return _Number_.getNumber(function.trim());
        } catch (NumberFormatException e){}
        String format = toPostfix(function);

        List<String> outputs = new ArrayList<>(Arrays.asList(format.split(" ")));
        if (x.length() > 0) Search.replace(outputs, var, x);
        if (y.length() > 0) Search.replace(outputs, varOp, y);
        String evaluated = evaluate(outputs);
        try{
            return (ans = _Number_.getNumber(evaluated));
        }catch(NumberFormatException e){}
        return NaN;
    }

    public static final String separator = "│";
    public static String preprocessor(String function){
        int index = function.indexOf("⩵");
        if (index != -1){
            if (function.substring(0, index).equals(function.substring(index+1))){
                return "Strings are equal";
            }
            else if (function.substring(0, index).equalsIgnoreCase(function.substring(index+1))){
                return "Strings are equal ignoring case";
            }
            return "Strings are not equal";
        }
        index = function.indexOf("≔");
        if (index != -1){
            String var = function.substring(0, index);
            double value = evaluate(function.substring(index+1));
            index = variables.indexOf(var);
            if (index != -1) {
                if (_Number_.isNumber(value)) {
                    values.set(index, value);
                }
                else{
                    variables.remove(index);
                    values.remove(value);
                }
            } else {
                variables.add(var);
                values.add(value);
            }
            return _Number_.format(value);

        }
        index = function.indexOf(separator);
        if (index != -1){
            String post = function.substring(index+1);
            function = function.substring(0, index);
            if (_Number_.isNumber(post))    return _Number_.format(evaluate(function, post, ""));
            String[] conditions = Search.split(Search.replace(post, " ", ""), ",");
            if (conditions.length == 2 && _Number_.isNumber(conditions[0]) && _Number_.isNumber(conditions[1])){
                if (function.contains(Engine.varOp)) {
                    return _Number_.format(evaluate(function, conditions[0], conditions[1]));
                }
                return _Number_.format(evaluate(function, conditions[0], "")-evaluate(function, conditions[1], ""));
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
                        String var = condition.substring(eqindex);
                        function = Search.replace(function, new String[][]{
                                {"${"+var+"}", "("+value+")"},
                                {"{"+var+"}", "("+value+")"},
                                {"$"+var, "("+value+")"},
                                {"("+var+")", "("+value+")"}
                        });
                    }
                }
            }
            return _Number_.format(evaluate(function));
        }
        return "";
    }

    public static String evaluateString(String function){
        return evaluateString(function, "");
    }
    public static String evaluateDF(String function){
        return evaluateString(function, "= ");
    }
    public static String evaluateString(String function, String df){
        boolean displayFormat = df.length() > 0;

        function = function.trim();
        if (function.length() == 0)    return "NaN";

        function = replaceVariables(function);
        String preprocessed = preprocessor(function);
        if (preprocessed.length() > 0)    return preprocessed;

        String constant = Const.getConstant(function);
        if (!constant.equals("NaC"))    return df+constant;
        try {   return df+_Number_.format(_Number_.getNumber(function));    } catch (NumberFormatException e){}

        for (String[] formula : Formulas.formulas){
            if (formula[0].equals(function))    return formula[1];
        }

        int multiIndex = multiParamFunctionNamesIndex(Search.replace(function, "Σ", "sum"));
        if (multiIndex != -1)    return multiParamFunctions[multiIndex].getDescription();
        int unaryIndex = unaryFunctionsIndex(function);
        if (unaryIndex != -1)    return unaryFunctions[multiIndex].getDescription();
        int textIndex = TextFunctions.textFunctionsIndex(function);
        if (textIndex != -1)    return TextFunctions.textFunctions[multiIndex].getDescription();

        String format = toPostfix(function);
        if (!format.toLowerCase().contains("error")) {
            List<String> outputs = new ArrayList<>(Arrays.asList(format.split(" ")));
            String evaluated = evaluate(outputs);
            if (_Number_.isNumber(evaluated)){
                if (displayFormat){
                    int index = evaluated.indexOf("E");
                    if (index != -1){
                        String standard = _Number_.convertToStandard(evaluated);
                        if (!evaluated.equals(standard))    return "= "+evaluated+" = "+standard;
                        return "= "+evaluated;
                    }
                    else{
                        String f = Fraction.calculateFraction(_Number_.getNumber(evaluated, true), false, true).trim();
                        if (_Number_.isNumber(f) || f.contains("Infinity") || function.trim().contains(f) || f.length() > 12){
                            return "= "+_Number_.format(evaluated);
                        }
                        else if (evaluated.length() > 13){
                            return "=  "+f+" ≈ "+Engine.evaluate(f);
                        }
                        return "=  "+f+" = "+_Number_.format(evaluated);
                    }
                }
                return _Number_.format(evaluated);
            }
            if ((!evaluated.toLowerCase().contains("error") && !evaluated.toLowerCase().contains("nan")) ||
                    evaluated.contains("∞") || evaluated.contains("{") || evaluated.contains("}")){
                return evaluated;
            }
        }
        //Check text cases
        int lb = function.indexOf('(');
        if (lb != -1){
            String eqn = Search.replace(function.substring(0, lb), StringReplacements.textFunctionReplacements);
            int index = TextFunctions.textFunctionsIndex(eqn);
            if (index != -1){
                int rb = function.lastIndexOf(')');
                if (rb != -1){
                    String parameters = function.substring(lb+1, rb);
                    TextFunction tf = TextFunctions.textFunctions[index];
                    if (tf.numParameters() == null) {
                        return tf.evaluate(parameters, displayFormat);
                    }
                    return tf.evaluate(Search.split(parameters, ","), displayFormat);
                }
            }
        }
        return "NaN";
    }

    public static String evaluate (List<String> list){
        return MathEngine.evaluate(list);
    }

    public static String toPostfix(String infixFunction) {
        if (!sorted){
            sortFunctions();
        }
        infixFunction = fixSyntax(infixFunction);
        if (infixFunction.toLowerCase().contains("error")) {
            return error;
        }
        try {
            if (infixFunction.charAt(0) == '-' && !_Number_.isNumber(infixFunction.charAt(1))) {
                infixFunction = "-1*" + infixFunction.substring(1);
            }
        }catch (StringIndexOutOfBoundsException e){}
        Stack<String> output = new Stack<>();
        Stack<String> stack = new Stack<>();
        String token = "";
        PARSE:  for (int a = 0; a < infixFunction.length(); a++) {
            char c = infixFunction.charAt(a);
            try {
                if (c == '.') {
                    token += ".";
                } else if (c == '-' && (a == 0 || !implicitContains(infixFunction.charAt(a - 1)))) {
                    token += "-";
                } else if (c == ',') {
                    if (token.length() < 1) {
                        error = "Syntax Error";
                        return error;
                    } else {
                        output.push(token);
                        token = "";
                    }
                } else if (c == var() || c == varOp() || Constants.isConstant(c)) {
                    if (token.length() > 0){
                        System.out.println(stack);
                        System.out.println(output);
                        if (token.equals("-")){
                            output.push(token+(c == 'η' ? 'e' : c));
                        }
                        else{
                            error = "Constant Character Token Error:    Token: "+token+"  char: "+c;
                            return error;
                        }
                        token = "";
                    }
                    else{
                        output.push((c == 'η' ? "e" : String.valueOf(c)));
                    }
                } else{
                    String superScript = "";
                    for (int b = a; b<infixFunction.length(); b++){
                        int scriptIndex = Scripts.getSuperScriptIndex(infixFunction.charAt(b));
                        if (scriptIndex != -1){
                            superScript += Scripts.regularScripts[scriptIndex];
                        }
                        else{
                            break;
                        }
                    }
                    if (superScript.length() > 0){
                        infixFunction = infixFunction.substring(0, a)+"^"+superScript+infixFunction.substring(a+superScript.length());
                        c = infixFunction.charAt(a);
                    }

                    int read = Integer.parseInt(String.valueOf(c));
                    token += read;
                }
            } catch (NumberFormatException e) {
                if (token.length() > 0) {
                    output.push(token);
                    token = "";
                }
                try {
                    if (c == '(') {
                        stack.push("(");
                        if (infixFunction.indexOf(")", a + 1) == -1) {
                            error = "Bracket Count Error:  Missing ')' Bracket";
                            return error;
                        }
                        continue PARSE;
                    } else if (c == ')') {
                        if (stack.contains("(")) {
                            while (!stack.isEmpty()) {
                                String str = stack.pop();
                                if (str.equals("(")) {
                                    break;
                                }
                                output.push(str);
                            }
                        } else {
                            error = "Bracket Count Error:  Missing '(' Bracket";
                            return error;
                        }
                        continue PARSE;
                    } else if (c == '{') {
                        int end = Search.indexOf(infixFunction, '}', a);
                        if (end != -1){
                            if (infixFunction.length() > end+1 && (infixFunction.charAt(end+1) == 'τ' || infixFunction.charAt(end+1) == 'ι')){
                                output.push(infixFunction.substring(a, end+2));
                                a = end+1;
                            }
                            else{
                                output.push(infixFunction.substring(a, end+1));
                                a = end;
                            }
                            continue PARSE;
                        }
                        error = "Bracket Count Error - Unclosed Curly Bracket";
                        return error;
                    } else if (c == '[') {
                        int unitIndex = infixFunction.indexOf("→", a);
                        if (unitIndex != -1){
                            int close = infixFunction.indexOf("]", a);
                            if (unitIndex < close){
                                String[] parameters = _UnitConversion_.isUnitConversion(infixFunction.substring(a+1, close), unitIndex);
                                if (parameters != null) {
                                    output.push("unit");
                                    for (String param : parameters){
                                        output.push(param);
                                    }
                                    a = close;
                                    continue PARSE;
                                }
                                else{
                                    error = "Invalid Input Error - Invalid Unit Conversion:  "+infixFunction.substring(a+1, close);
                                    return error;
                                }
                            }
                        }
                        char cPlus1 = infixFunction.charAt(a+1);
                        int semicolon = infixFunction.indexOf(";", a);
                        if (semicolon != -1 || cPlus1 == '['){
                            int end = infixFunction.indexOf("]", a);
                            if (end != -1){
                                if (infixFunction.length() > end+1 && (infixFunction.charAt(end+1) == 'τ' || infixFunction.charAt(end+1) == 'ι')){
                                    output.push(infixFunction.substring(a, end+2));
                                    a = end+1;
                                }
                                else{
                                    output.push(infixFunction.substring(a, end+1));
                                    a = end;
                                }
                                continue PARSE;
                            }
                            error = "Bracket Count Error - Unclosed Hard Bracket";
                            return error;
                        }
//                        int comma1 = infixFunction.indexOf(",", a);
//                        int close = infixFunction.indexOf("]", a);
//                        if (comma1 == -1 || close == -1) {
//                            error = "Syntax Error - Evaluate";
//                            return error;
//                        }
//                        int comma2 = infixFunction.indexOf(",", comma1 + 1);
//                        if (comma2 == -1) {
//                            output.push("eval");
//                            output.push(infixFunction.substring(a + 1, comma1));
//                            output.push("" + evaluate(infixFunction.substring(comma1 + 1, close)));
//                        } else {
//                            output.push("evalint");
//                            output.push(infixFunction.substring(a + 1, comma1));
//                            output.push("" + evaluate(infixFunction.substring(comma1 + 1, comma2)));
//                            output.push("" + evaluate(infixFunction.substring(comma2 + 1, close)));
//                        }
//                        a = close;
//                        continue PARSE;
                    }
                    int fnIndex = -1;
                    for (int b = MultiParamFunction.minStrLength; b<=MultiParamFunction.maxStrLength && a+b <= infixFunction.length(); b++){
                        fnIndex = multiParamFunctionNamesIndex(infixFunction.substring(a, a+b));
                        if (fnIndex != -1){
                            int lb = infixFunction.indexOf("(", a);
                            try{
                                int rb = lb+Search.getIndices(infixFunction.substring(lb), ")").get(0);
                                MultiParamFunction multiParamFunction = multiParamFunctions[fnIndex];
                                String[] parameters = Search.split(infixFunction.substring(lb+1, rb), ",", false);
                                output.push(multiParamFunction.getName());
                                if (multiParamFunction.isFlexible()){
                                    output.push(parameters.length+"");
                                }
                                else if (multiParamFunction.getNumParameters() != parameters.length) {
                                    error = "Syntax Error - Invalid Number of Parameters.  E:"+multiParamFunction.getNumParameters()+"   A:"+parameters.length;
                                    return error;
                                }
                                for (int i = 0; i < parameters.length; i++) {
                                    output.push(parameters[i].trim());
                                }
                                a = rb;
                                continue PARSE;
                            }catch(IndexOutOfBoundsException parameterLengthError){
                                error = "Syntax Error";
                                return error;
                            }
                        }
                    }
                    int o1 = -1;
                    if (a+1 <= infixFunction.length()){
                        o1 = operatorIndex(c);
                    }
                    if (o1 != -1){
                        Operator operator1 = operators[o1];
                        while (!stack.isEmpty() && operatorIndex(stack.peek()) == -1 && !stack.peek().equals("(")) {
                            output.push(stack.pop());
                        }
                        if (stack.isEmpty() || operatorIndex(stack.peek()) == -1) {
                            stack.push(operator1.getName());
                        } else {
                            while (true) {
                                if (stack.empty()) {
                                    stack.push(operator1.getName());
                                    break;
                                }
                                int o2 = operatorIndex(stack.peek());
                                if (o2 >= 0 && ((operator1.isAssociable() && operator1.getPrecedence() <= operators[o2].getPrecedence()) ||
                                        (!operator1.isAssociable() && operator1.getPrecedence() < operators[o2].getPrecedence()))) {
                                    output.push(stack.pop());
                                } else {
                                    stack.push(operator1.getName());
                                    break;
                                }
                            }
                        }
                    }
                    else{
                        for (int b = UnaryFunction.maxStrLength; b>=2; b--){
                            if (a+b <= infixFunction.length()){
                                String mathf = infixFunction.substring(a, a+b);
                                int orderIndex = unaryFunctionsIndex(mathf);
                                if (orderIndex != -1){
                                    String superScript = "";
                                    for (int i = a + mathf.length(); i<infixFunction.length(); i++){
                                        int index = Scripts.getSuperScriptNumIndex(infixFunction.charAt(i));
                                        if (index != -1){
                                            superScript += Scripts.regularScriptNums[index];
                                        }
                                        else{
                                            break;
                                        }
                                    }
                                    if (stack.empty() || stack.peek().equals("(")
                                            || (stack.size() > 0 && (unaryFunctionsIndex(stack.peek()) >= orderIndex) || operatorIndex(stack.peek()) != -1)) {
                                        if (superScript.length() > 0) {
                                            stack.push("^");
                                            stack.push(superScript);
                                        }
                                        stack.push(mathf);
                                        a += mathf.length()+superScript.length() - 1;
                                    } else if (stack.size() > 0 && unaryFunctionsIndex(stack.peek()) < orderIndex) {
                                        while (!stack.isEmpty()) {
                                            String str = stack.peek();
                                            if (unaryFunctionsIndex(stack.peek()) < orderIndex) {
                                                output.push(stack.pop());
                                            } else {
                                                output.push(stack.pop());
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                } catch (NumberFormatException | StringIndexOutOfBoundsException e2) {
                    error = "Invalid Input Error 2";
                    return error;
                }
            }
        }
        if (token.length() > 0) {
            output.push(token);
            token = "";
        }
        while (!stack.empty()) {
            output.push(stack.pop());
        }
        for (int a = 0; a < output.size(); a++) {
            token += output.get(a) + " ";
        }
        return token.trim();
    }

    public static int implicitIndex(char item){
        if (!sorted){
            sortFunctions();
        }
        return Search.binarySearch(implicit, item);
    }
    public static boolean implicitContains(char item){
        if (!sorted){
            sortFunctions();
        }
        return Search.contains(implicit, item);
    }

    public static int checkImplicitIndex(char item){
        if (!sorted){
            sortFunctions();
        }
        return Search.binarySearch(checkImplicit, item);
    }
    public static boolean checkImplicitContains(String item){
        if (!sorted){
            sortFunctions();
        }
        return Search.contains(checkImplicit, item.charAt(0));
    }
    public static boolean checkImplicitContains(char item){
        if (!sorted){
            sortFunctions();
        }
        return Search.contains(checkImplicit, item);
    }

    public static char var(){
        return var.charAt(0);
    }
    public static char varOp(){
        return varOp.charAt(0);
    }

    public static List<String> getVariables() {
        return variables;
    }
    public static List<Double> getValues() {
        return values;
    }

    public boolean setVariable(String variable) {
        if (!variable.equalsIgnoreCase("e") && !variable.equalsIgnoreCase("π") && variable.length() == 1) {
            this.var = variable;
            return true;
        }
        return false;
    }

}
