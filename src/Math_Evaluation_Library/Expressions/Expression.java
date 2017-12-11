package Math_Evaluation_Library.Expressions;

import Math_Evaluation_Library.Engine.Engine;

import java.util.List;

/**
 * Created by Antonio on 2017-11-23.
 */
public abstract class Expression {

    public double valueOf() {
        return evaluate().valueOf();
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

    public String toString(){
        return infix();
    }
    public abstract List<Double> getNumbers();

    public abstract String infix();
    public abstract String postfix();

    public String hardcode(){   return hardcode("");    }
    public abstract String hardcode(String spacing);

    public Expression getDerivative(){
        return new InvalidExpression("("+infix()+")'");
    }
    public Expression getIntegral(){
        return new InvalidExpression("ʃ"+infix());
    }
    public Expression simplify(){   return this;    }
}
