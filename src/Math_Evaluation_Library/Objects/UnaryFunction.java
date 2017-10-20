package Math_Evaluation_Library.Objects;

import static java.lang.Double.NaN;

/**
 * Created by Antonio on 2017-10-14.
 */
public class UnaryFunction extends MathObject {

    public static int maxStrLength = 7;
    public static int minStrLength = 3;
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

    public double evaluate(double x){ return NaN; }

    public String getDescription() {
        return description;
    }
}
