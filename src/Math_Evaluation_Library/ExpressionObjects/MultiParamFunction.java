package Math_Evaluation_Library.ExpressionObjects;

import Math_Evaluation_Library.Engine.Scanner;
import Math_Evaluation_Library.Expressions.Expression;

import java.util.List;

import static Math_Evaluation_Library.Expressions.Expression.toExpression;

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

    public Expression[] convert(List<List<Scanner.Token>> parameters){
        Expression[] ets = new Expression[parameters.size()];
        for (int j = 0; j<parameters.size(); ++j){
            ets[j] = toExpression(parameters.get(j));
        }
        return ets;
    }
}
