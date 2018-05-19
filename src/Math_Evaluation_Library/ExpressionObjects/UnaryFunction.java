package Math_Evaluation_Library.ExpressionObjects;

import Math_Evaluation_Library.Constants.PredefinedTeX;
import Math_Evaluation_Library.Expressions.*;

/**
 * Created by Antonio on 2017-10-14.
 */
public abstract class UnaryFunction {

    public static int maxStrLength = 7;
    public static int minStrLength = 1;
    protected String function = "";
    private String description = "";

    public UnaryFunction(String function){
        this.function = function;
        if (function.length() > maxStrLength){
            maxStrLength = function.length();
        }
        else if (function.length() <  minStrLength){
            minStrLength = function.length();
        }
    }
    public UnaryFunction(String function, String description){
        this(function);
        this.description = description;
    }

    public abstract Expression evaluate(Expression x);
    public String infix(Expression x){
        if ((x instanceof NumberExpression && !(x instanceof ComplexExpression) && x.valueOf() > 0) ||
                (x instanceof UnaryExpression && ((UnaryExpression) x).function().toString().equals("abs"))){
            return function+x.infix();
        }
        return function+"("+x.infix()+")";
    }
    public String postfix(Expression x){    return x.postfix()+" "+function;        }
    public String toTeX(Expression x){
        if ((x instanceof NumberExpression && !(x instanceof ComplexExpression) && x.valueOf() > 0) ||
                (x instanceof UnaryExpression && ((UnaryExpression) x).function().toString().equals("abs"))){
            if (PredefinedTeX.functions.containsKey(function)){
                return PredefinedTeX.functions.get(function)+x.toTeX();
            }
            return "\\operatorname{"+function+"}"+x.toTeX();
        }
        if (PredefinedTeX.functions.containsKey(function)){
            return PredefinedTeX.functions.get(function)+"\\left("+x.toTeX()+"\\right)";
        }
        return "\\operatorname{"+function+"}\\left("+x.toTeX()+"\\right)";
    }

    public Expression getDerivative(Expression x){
        return new InvalidExpression("("+function+"("+x.infix()+"))'");
    }
    public Expression getIntegral(Expression x){
        return new InvalidExpression("ʃ("+function+"("+x.infix()+"))");
    }
    public Expression simplify(Expression x){
        return new UnaryExpression(this, x.simplify());
    }

    public String toString() {
        return function;
    }
    public String getDescription() {
        return description;
    }
}
