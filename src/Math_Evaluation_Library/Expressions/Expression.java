package Math_Evaluation_Library.Expressions;

import Math_Evaluation_Library.Engine.Engine;
import Math_Evaluation_Library.Miscellaneous.Fraction;

import java.util.List;

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
}
