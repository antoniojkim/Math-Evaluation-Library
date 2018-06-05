package Math_Evaluation_Library.Expressions;

import Math_Evaluation_Library.Constants.Constants;
import Math_Evaluation_Library.Constants.Scripts;
import Math_Evaluation_Library.Engine.Engine;
import Math_Evaluation_Library.Engine.Scanner;
import Math_Evaluation_Library.ExpressionObjects.MultiParamFunction;
import Math_Evaluation_Library.ExpressionObjects.Operator;
import Math_Evaluation_Library.LinearAlgebra._Matrix_;
import Math_Evaluation_Library.Miscellaneous.Fraction;
import Math_Evaluation_Library.Objects.Pair;
import Math_Evaluation_Library.Objects._Number_;
import Math_Evaluation_Library.Print;
import Math_Evaluation_Library.Search;
import Math_Evaluation_Library.UnitConversion._UnitConversion_;

import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

import static Math_Evaluation_Library.ExpressionObjects.MultiParamFunctions.getMultiParamFunction;
import static Math_Evaluation_Library.ExpressionObjects.MultiParamFunctions.isMultiParamFunction;
import static Math_Evaluation_Library.ExpressionObjects.Operators.getOperator;
import static Math_Evaluation_Library.ExpressionObjects.Operators.isOperator;
import static Math_Evaluation_Library.ExpressionObjects.UnaryFunctions.getUnaryFunction;
import static Math_Evaluation_Library.ExpressionObjects.UnaryFunctions.isUnaryFunction;

/**
 * Created by Antonio on 2017-11-23.
 */
public abstract class Expression {

    public double valueOf() {
        return evaluate().valueOf();
    }
    public double getValue(){
        return valueOf();
    }
    public double valueAt(double x){
        set(Engine.var, x);
        double n = valueOf();
        unset(Engine.var);
        return n;
    }
    public abstract Expression evaluate();
    public Expression evaluate(double x){
        set(Engine.var, x);
        Expression n = evaluate();
        unset(Engine.var);
        return n;
    }
    public Expression evaluate(Expression x){
        set(Engine.var, x);
        Expression n = evaluate();
        unset(Engine.var);
        return n;
    }

    public void set(String var, double val) {
        set(var, new NumberExpression(val));
    }
    public void set(String var, Expression val){}
    public void unset(){}
    public void unset(String var){}
    public boolean containsVar(String var){     return false;   }

    public abstract boolean isValid();
    public abstract boolean equals(Expression e);
    public boolean equals(String function){  return false;   }

    public boolean isNumberExpression(){
        return this instanceof NumberExpression;
    }
    public boolean isInteger(){
        return valueOf()%1 == 0;
    }
    public boolean isRational(){
        return isInteger();
    }

    public String toString(){
        return infix();
    }
    public NumberExpression toNumberExpression(){
        return new NumberExpression(valueOf());
    }
    public Expression toRational(){
        return Fraction.toExpression(valueOf());
    }
    public abstract List<Double> getNumbers();

    public abstract String infix();
    public String getInfix(){
        return infix();
    }
    public abstract String postfix();
    public String getPostfix(){
        return postfix();
    }
    public abstract String toTeX();
    public String getTex(){
        return toTeX();
    }

    public String hardcode(){   return hardcode("");    }
    public abstract String hardcode(String spacing);

    public Expression calculateDerivative(){
        return new InvalidExpression("("+infix()+")'");
    }
    public Expression calculateIntegral(){
        return new InvalidExpression("ʃ"+infix());
    }
    public Expression simplify(){   return this;    }

    public Expression add(double e){
        return add(new NumberExpression(e));
    }
    public Expression add(Expression e){
        return new OperatorExpression("+", this, e);
    }
    public Expression subtract(double e){
        return subtract(new NumberExpression(e));
    }
    public Expression subtract(Expression e){
        return new OperatorExpression("-", this, e);
    }
    public Expression times(double e){
        return multiply(new NumberExpression(e));
    }
    public Expression times(Expression e){
        return multiply(e);
    }
    public Expression multiply(double e){
        return multiply(new NumberExpression(e));
    }
    public Expression multiply(Expression e){
        return new OperatorExpression("*", this, e);
    }
    public Expression divide(double e){
        return divide(new NumberExpression(e));
    }
    public Expression divide(Expression e){
        return new OperatorExpression("/", this, e);
    }
    public Expression mod(double e){
        return mod(new NumberExpression(e));
    }
    public Expression mod(Expression e){
        return new OperatorExpression("%", this, e);
    }
    public Expression pow(double e){
        return pow(new NumberExpression(e));
    }
    public Expression pow(Expression e){
        return new OperatorExpression("^", this, e);
    }
    public Expression negate(){
        return negate(true);
    }
    public Expression negate(boolean negate){
        if (negate){
            return new UnaryExpression("neg", this);
        }
        return this;
    }

    public static Expression toExpression(List<Scanner.Token> tokens){
        if (tokens.size() == 1 && tokens.get(0).getType() == Scanner.TokenType.NUM){
            return new NumberExpression(tokens.get(0).getLexeme());
        }
        Stack<Expression> output = new Stack<>();
        Stack<String> stack = new Stack<>();
        try {
            for (int i = 0; i<tokens.size(); ++i) {
                Scanner.Token token = tokens.get(i);

//                System.out.println();
//                System.out.println(Scanner.join(tokens.subList(i, tokens.size()), " "));
//                System.out.println(stack);
//                System.out.println(output);
//                System.out.println(token+"    "+token.getType());

                if (token.getType() == Scanner.TokenType.NUM){
                    output.push(new NumberExpression(token.getLexeme()));
                } else if (token.getLexeme().equals(Engine.var) || token.getLexeme().equals(Engine.varOp)) {
                    output.push(new VariableExpression(token.getLast()));
                } else if (token.length() == 1 && token.getLast() == 'i') {
                    if (i > 0 && tokens.get(i-1).getType() == Scanner.TokenType.NUM){
                        output.push(new ComplexExpression(output.pop().valueOf()));
                    }
                    else{
                        output.push(new ComplexExpression(1));
                    }
                } else if (Constants.isConstant(token.getLexeme())) {
                    output.push(new ConstantExpression(token.getLexeme()));
                } else if (token.getType() == Scanner.TokenType.SUPERSCRIPT){
                    if (!output.isEmpty() && output.peek() instanceof MatrixExpression){
                        if (token.getLexeme().equals("ᴵ")){
                            ((MatrixExpression)output.peek()).invert();
                        }
                        else if (token.getLexeme().equals("ᵀ")){
                            ((MatrixExpression)output.peek()).transpose();
                        }
                    }
                    else{
                        stack.push("^");
                        output.push(new NumberExpression(Scripts.toRegularScript(token.getLexeme())));
                    }
                }
                else if (token.getType() == Scanner.TokenType.LPAREN){
                    List<Integer> commas = null;
                    if (i+2 < tokens.size() &&
                            tokens.get(i+1).getType() == Scanner.TokenType.STR &&
                            tokens.get(i+2).getType() == Scanner.TokenType.RPAREN){

                        output.push(new VariableExpression(tokens.get(i+1).getLexeme()));
                        i += 2;
                    }
                    else if ((commas = Search.searchTokens(tokens, Scanner.TokenType.COMMA, i+1)).size() > 1){
                        output.push(_Matrix_.toExpression(tokens, i));
                        i = commas.get(commas.size()-1);
                    }
                    else {
                        stack.push("(");
                    }
                } else if (token.getType() == Scanner.TokenType.RPAREN){
                    if (stack.contains("(")) {
                        while (!stack.isEmpty()) {
                            String s = stack.pop();
                            if (s.equals("(")) break;
                            stackElementToExpression(s, output);
                        }
                    } else {
                        return new InvalidExpression("Bracket Count Error:  Missing '(' Bracket");
                    }
                } else if (token.getType() == Scanner.TokenType.LCURLY){
                    List<Integer> commas = null;
                    if (i+2 < tokens.size() &&
                            tokens.get(i+1).getType() == Scanner.TokenType.STR &&
                            tokens.get(i+2).getType() == Scanner.TokenType.RCURLY){

                        output.push(new VariableExpression(tokens.get(i+1).getLexeme()));
                        i += 2;
                    }
                    else if ((commas = Search.searchTokens(tokens, Scanner.TokenType.COMMA, i+1)).size() > 0){
                        output.push(_Matrix_.toExpression(tokens, i));
                        i = commas.get(commas.size()-1);
                    }
                } else if (token.getType() == Scanner.TokenType.LSQUARE){
                    List<Integer> indices = null;
                    if (i+2 < tokens.size() &&
                            tokens.get(i+1).getType() == Scanner.TokenType.STR &&
                            tokens.get(i+2).getType() == Scanner.TokenType.RSQUARE){

                        output.push(new VariableExpression(tokens.get(i+1).getLexeme()));
                        i += 2;
                    }
                    else if ((indices = Search.searchTokens(tokens, Scanner.TokenType.RIGHTARROW, i+1)).size() == 2){
                        if (indices.get(0) >= i+3){
                            int end = indices.get(0)-1;
                            Pair<String, Integer> unit1 = _UnitConversion_.getUnit(tokens, end);
                            if (unit1 == null){
                                return new InvalidExpression("Invalid Unit:   "+tokens.get(end).getLexeme()+" in "+Print.toString(tokens));
                            }
                            end = unit1.getB();
                            Expression[] params = {
                                    Expression.toExpression(tokens.subList(i+1, end+1)),
                                    new StringExpression(unit1.getA()),
                                    new StringExpression("")
                            };
                            end = indices.get(1)-1;
                            Pair<String, Integer> unit2 = _UnitConversion_.getUnit(tokens, end);
                            if (unit2 == null){
                                return new InvalidExpression("Invalid Unit:   "+tokens.get(end).getLexeme()+" in "+Print.toString(tokens));
                            }
                            params[2] = new StringExpression(unit2.getA());
                            output.push(new MultiParamExpression("unit", params));
                        }
                        i = indices.get(1);
                    }
                    else if ((indices = Search.searchTokens(tokens, Scanner.TokenType.COMMA, i+1)).size() > 0){
                        output.push(_Matrix_.toExpression(tokens, i));
                        i = indices.get(indices.size()-1);
                    }
                }
                else if (token.getType() == Scanner.TokenType.OPERATOR){
                    if (token.getLast() == '-' && (i == 0 || !tokens.get(i-1).isImplicit())){
                        stack.push("neg");
                    }
                    else{
                        Operator operator1 = getOperator(token.getLexeme());
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
                    }
                }
                else if (isMultiParamFunction(token.getLexeme())){
                    if (tokens.get(i+1).getType() != Scanner.TokenType.LPAREN){
                        return new InvalidExpression("Missing Left Parenthesis:   "+Print.toString(tokens));
                    }
                    MultiParamFunction multiParamFunction = getMultiParamFunction(token.getLexeme());
                    List<List<Scanner.Token>> parameters = Search.splitTokens(tokens, i+2);
                    if (!multiParamFunction.isFlexible() && multiParamFunction.getNumParameters() != parameters.size()) {
                        return new InvalidExpression("Syntax Error - Invalid Number of Parameters.  " +
                                "E:" + multiParamFunction.getNumParameters() + "   A:" + parameters.size());
                    }
                    Expression[] params = multiParamFunction.convert(parameters);
                    output.push(new MultiParamExpression(multiParamFunction, params));
                    i = Search.tokenIndex(tokens, Scanner.TokenType.RPAREN, i+2);
                }
                else if (isUnaryFunction(token.getLexeme())){
                    if (stack.empty() || stack.peek().equals("(") ||
                            stack.peek().equals("neg") || isOperator(stack.peek())) {
                        if (i+1 < tokens.size() && tokens.get(i+1).getType() == Scanner.TokenType.SUPERSCRIPT){
                            ++i;
                            stack.push("^");
                            stack.push(Scripts.toRegularScript(tokens.get(i).getLexeme()));
                        }
                        stack.push(token.getLexeme());
                    } else if (!stack.isEmpty()) {
                        output.push(new UnaryExpression(stack.pop(), output.pop()));
                    }
                }
                else if (token.getLexeme().equals("intmax")) {
                    output.push(new NumberExpression(2147483647));
                }
                else if (token.getLexeme().equals("intmin")) {
                    output.push(new NumberExpression(-2147483648));
                }
                else{
                    return new InvalidExpression("Unrecognized Token:   "+token.getLexeme()+" in "+Print.toString(tokens));
                }
            }
        }catch(EmptyStackException e){
            System.out.println("Empty Stack Exception");
        }
//        if (token.length() > 0) {
//            output.push(new NumberExpression(token.toString()));
//        }
        while (!stack.empty()) {
//            System.out.println();
//            System.out.println(stack);
//            System.out.println(output);
            String s = stack.pop();
            stackElementToExpression(s, output);
//            System.out.println();
//            System.out.println(stack);
//            System.out.println(output);
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
}
