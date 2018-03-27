package Math_Evaluation_Library.ExpressionObjects;

import Math_Evaluation_Library.Expressions.Expression;

import static Math_Evaluation_Library.Engine.Engine.toExpression;

/**
 * Created by Antonio on 2017-10-14.
 */
public abstract class MultiParamFunction {

    public static int maxStrLength = 7;
    public static int minStrLength = 1;
    private int numParameters = -1;
    private String function = "";
    private String description = "";

    public MultiParamFunction (String function, int numParameters){
        this.function = function;
        if (function.length() > maxStrLength){
            maxStrLength = function.length();
        }
        else if (function.length() < minStrLength){
            minStrLength = function.length();
        }
        this.numParameters = numParameters;
    }
    public MultiParamFunction (String function, int numParameters, String description){
        this(function, numParameters);
        this.description = description;
    }

    public abstract Expression evaluate(Expression[] parameters);

    public int getNumParameters() {
        return numParameters;
    }
    public boolean isFlexible() {
        return numParameters == -1;
    }

    public String getFunction() {
        return function;
    }
    public String getTeX(Expression[] parameters) {
        StringBuilder TeX = new StringBuilder("\\operatorname{"+getFunction()+"}\\left(");
        boolean first = true;
        for (Expression e : parameters){
            if (first){
                first = false;
            }
            else{
                TeX.append(", ");
            }
            TeX.append(e.toTeX());
        }
        TeX.append("\\right)");
        return TeX.toString();
    }
    public String getDescription() {
        return description;
    }

    public Expression[] convert(String[] parameters){
        Expression[] ets = new Expression[parameters.length];
        for (int i = 0; i<parameters.length; ++i){
            ets[i] = toExpression(parameters[i]);
        }
        return ets;
    }
}
