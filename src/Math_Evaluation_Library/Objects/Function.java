package Math_Evaluation_Library.Objects;

import Math_Evaluation_Library.Calculus.Derivative;
import Math_Evaluation_Library.Engine.Engine;
import Math_Evaluation_Library.Miscellaneous.MathRound;
import Math_Evaluation_Library.Miscellaneous.Simplify;
import Math_Evaluation_Library.Miscellaneous._Random_;
import Math_Evaluation_Library.Search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Double.NaN;

/**
 * Created by Antonio on 2017-07-12.
 */
public class Function {

    private String function = "";
    private String format = "";
    private int type = 0;
    private List<String> outputs = new ArrayList<>();
    private List<Integer> varIndices = new ArrayList<>();
    private List<Integer> varOpIndices = new ArrayList<>();
    private List<String> terms = new ArrayList<>();
    private Boolean isNumber = false;
    private double value = NaN;
    private Boolean integrable = null;
    private String derivative = "";
    private String integral = "";

    private boolean containsVar = false;
    private boolean containsVarOp = false;


    public Function(Function f){
        function = f.function();
        format = f.format;
        type = f.getType();
        outputs.clear();
        outputs.addAll(f.getOutputs());
        terms.clear();
        terms.addAll(f.getTerms());
        isNumber = f.isNumber();
        value = f.getValue();
        integrable = f.isIntegrable();
        integral = f.getIntegral();
        derivative = f.getDerivative();
        containsVar = f.isContainsVar();
        containsVarOp = f.isContainsVarOp();
    }
    public Function(String fx){
        this(fx, true);
    }
    public Function(String fx, boolean simplify){
        if (!fx.toLowerCase().contains("error")){
            function = fx;
            if (!checkBrackets(function)){
                type = -1;
            }
            if (isFunction()){
                removeExcessBrackets();
                checkSyntax();
                if (function.toLowerCase().contains("error")) {
                    type = -1;
                }
                if (isFunction()){
                    if (simplify){
                        function = Simplify.simplify(function);
                    }
                    format = Engine.toPostfix(function);
                    String[] split = format.split(" ");
                    for (int i = 0; i<split.length; i++){
                        if (split[i].equals(Engine.var)){
                            varIndices.add(i);
                        }
                        else if (split[i].equals(Engine.varOp)){
                            varOpIndices.add(i);
                        }
                        outputs.add(split[i]);
                    }
                    containsVar = !varIndices.isEmpty();
                    containsVarOp = !varOpIndices.isEmpty();
                    value = evaluate();
                }
            }
        }
        else{
            type = 0;
            function = "Not a Function";
        }
    }

    public void checkSyntax(){
        function = Engine.fixSyntax(function);
    }

    private void removeExcessBrackets(){
        if (function.length() > 2 &&
                function.startsWith("(") && function.endsWith(")") && checkBrackets(function.substring(1, function.length()-1))){
            function = function.substring(1, function.length()-1);
        }
    }

    private boolean checkBrackets(String function){
        int bracket = 0;
        for (int a = 0; a<function.length(); a++){
            if (function.substring(a, a+1).equals("(")){
                bracket++;
            }
            else if (function.substring(a, a+1).equals(")")){
                bracket--;
            }
            if (bracket < 0){
                return false;
            }
        }
        return true;
    }

    public double at(double num){
        return evaluate(num);
    }
    public double at(double x, double y){
        return evaluate(x, y);
    }
    public double of(double num){
        return evaluate(num);
    }
    public double of(double x, double y){
        return evaluate(x, y);
    }
    public double evaluate(){
        if (isFunction()){
            String evaluated = Engine.evaluate(outputs);
            if (_Number_.isNumber(evaluated)){
                isNumber = true;
                return _Number_.getNumber(evaluated);
            }
        }
        isNumber = false;
        return NaN;
    }
    public double evaluate(double x){
        if (isFunction()){
            if (isNumber) {
                return value;
            }
            try{
                return _Number_.getNumber(evaluateString(x));
            }catch(NumberFormatException e){}
        }
        return NaN;
    }
    public double evaluate(double x, double y){
        if (isFunction()){
            if (isNumber) {
                return value;
            }
            try{
                return _Number_.getNumber(evaluateString(x, y));
            }catch(NumberFormatException e){}
        }
        return NaN;
    }
    public String evaluateString(){
        if (isFunction()){
            if (isNumber) {
                return value+"";
            }
            return Engine.evaluate(outputs);
        }
        return "NaN";
    }
    public String evaluateString(double num){
        if (isNumber) {
            return String.valueOf(value);
        }
        if (isFunction() && !containsVarOp){
            List<String> list = new ArrayList<>(outputs);
            Search.replace(list, varIndices, String.valueOf(num));
            return Engine.evaluate(list);
        }
        if (containsVarOp && !containsVar){
            List<String> list = new ArrayList<>(outputs);
            Search.replace(list, varOpIndices, String.valueOf(num));
            return Engine.evaluate(list);
        }
        return "NaN";
    }
    public String evaluateString(double x, double y){
        if (isFunction()){
            if (isNumber) {
                return value+"";
            }
            List<String> list = new ArrayList<>(outputs);
            Collections.replaceAll(list, Engine.var, String.valueOf(x));
            Collections.replaceAll(list, Engine.varOp, String.valueOf(y));
            return Engine.evaluate(list);
        }
        return "NaN";
    }

    public boolean isNumber(){
        return isNumber;
    }
    public boolean isFunction(){
        if (type == -1){
            return false;
        }
        return true;
    }

    public int getType(){
        if (type == 0){
            List<Double> numbers = new ArrayList<>();
            numbers.addAll(_Number_.extractNumbers(function));
            while(numbers.size() < 3){
                numbers.add(1.0);
            }
            if (numbers.size() == 3){
                boolean containsLogarithm = function.contains("ln") || function.contains("log");
                String[] trigFunctions = {"sin", "cos", "tan", "csc", "sec", "cot"};
                boolean[] containsTrig = {false, false, false, false, false, false};
                boolean containsTrigFunction = false;
                for (int a = 0; a<trigFunctions.length; a++){
                    containsTrig[a] = function.contains(trigFunctions[a]);
                    if (containsTrig[a]){
                        containsTrigFunction = true;
                    }
                }
                boolean containsInverseTrig = function.contains("arcsin") || function.contains("arccos") || function.contains("arctan") ||
                        function.contains("arccsc") || function.contains("arcsec") || function.contains("arccot");
                boolean containsExponent = function.contains("^");
//                List<double[]> numberList = IntegrationDatabase.getNumbers(numbers);
//                for (int a = 0; a<numberList.size(); a++){
//                    double k = numberList.get(a)[0];
//                    double l = numberList.get(a)[1];
//                    double m = numberList.get(a)[2];
//                    if (containsLogarithm){
//                        if (equals(k+"*"+m+"*ln("+l+"*x)", 1, 2)){
//                            return type = 1;
//                        }
//                        if (m > 0 && m != 1 && equals((k/Math.log(m))+"*ln("+l+"*x)", 1, 2)){
//                            return type = 1;
//                        }
//                        if (equals(k+"*"+m+"*log("+l+"*x)", 1, 2)){
//                            return type = 1;
//                        }
//                    }
//                    if (containsTrigFunction){
//                        if (containsInverseTrig){
//                            for (int b = 0; b<trigFunctions.length; b++){
//                                if (containsTrig[b] == true){
//                                    if (equals(k+"*arc"+trigFunctions[b]+"("+l+"*x)", 1, 2)){
//                                        return type = 2;
//                                    }
//                                }
//                            }
//                        }
//                        for (int b = 0; b<trigFunctions.length; b++){
//                            if (containsTrig[b] == true){
//                                if (equals(k+"*"+trigFunctions[b]+"("+l+"*x)", 1, 2)){
//                                    return type = 4;
//                                }
//                            }
//                        }
//                    }
//                    if (containsExponent){
//                        if (equals(k+"*("+l+"*x)^("+m+")", 1, 2)){
//                            return type = 3;
//                        }
//                        if (equals(k+"*e^("+l+"*x)", 1, 2)){
//                            return type = 5;
//                        }
//                    }
////                    if (equals(k+"*"+m+"*ln("+l+"*x)", 1, 2)){
////                        return type = 1;
////                    }
//                }
            }
            type = 6;
        }
        return type;
    }
    public List<String> getOutputs(){
        return outputs;
    }
    public double getValue(){
        return value;
    }

    public boolean isIntegrable(){
        if (integrable == null){
//            integrable = isIntegrable.check(function);
        }
        return integrable;
    }
    public void setIntegrable(){
        integrable = true;
    }
    public void setIntegrable(boolean isIntegrable){
        integrable = isIntegrable;
    }
    public String getDerivative(){
        if (derivative.equals("")){
            derivative = Derivative.calculate(function);
        }
        return derivative;
    }
    public String getIntegral(){
        if (integral.equals("")){
            if (isIntegrable()){
//                integral = IntegrationEngine.antidifferentiate(function, 2, 2);
            }
            else{
                integral = "Not Antidifferentiable";
            }
        }
        return integral;
    }
    public String getIntegral(int checkSubstitution, int checkParts){
        if (integral.equals("")){
            if (isIntegrable()){
//                integral = IntegrationEngine.antidifferentiate(function, checkSubstitution, checkParts-1);
            }
            else{
                integral = "Not Antidifferentiable";
            }
        }
        return integral;
    }

    public boolean isContainsVar() {
        return containsVar;
    }
    public boolean isContainsVarOp() {
        return containsVarOp;
    }

    public double value(){
        return value;
    }
    public String function(){
        return function;
    }
    public boolean equals(String compare, double low, double high){
        return equals(this, new Function(compare), low, high);
    }
    public boolean equals(Function compare, double low, double high){
        return equals(this, new Function(compare), low, high);
    }
    public static boolean equals(Function f, Function g, double low, double high){
        if (f.function().equals(g.function())){
            return true;
        }
        //System.out.println(function+"          "+compare);
        for (int a = 0; a<5; a++){
            double r = MathRound.round(_Random_.random(low, high), 5);
            String functionEval = MathRound.roundf(f.of(r), 10);
            String functionCompare = MathRound.roundf(g.of(r), 10);
            if (!functionEval.equals(functionCompare) || !_Number_.isNumber(functionEval)){
                return false;
            }
        }
        return true;
    }

    public void print(){
        System.out.println(function+"");
    }
    public void printFormat(){
        System.out.println(format+"");
    }
    public void printTerms(){
        getTerms();
        for (int a = 0; a<terms.size(); a++){
            System.out.print(terms.get(a)+"     ");
        }
        System.out.println("");
    }
    public void printType(){
        System.out.println(getType());
    }

    public List<String> getTerms(){
        if (terms.isEmpty()){
            List<Integer> indices = Search.getIndices(function, "+-");
            indices.add(function.length());
            terms.add(function.substring(0, indices.get(0)));
            String term = "";
            for (int a = 1; a<indices.size(); a++){
                term = function.substring(indices.get(a-1), indices.get(a));
                if (term.startsWith("+")){
                    terms.add(term.substring(1));
                }
                else{
                    terms.add(term);
                }
            }
        }
        return terms;
    }


}
