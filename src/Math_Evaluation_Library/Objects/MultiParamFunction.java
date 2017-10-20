package Math_Evaluation_Library.Objects;

/**
 * Created by Antonio on 2017-10-14.
 */
public class MultiParamFunction extends MathObject {

    public static int maxStrLength = 7;
    public static int minStrLength = 1;
    private int numParameters = -1;
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

    public String evaluate(String[] parameters){ return ""; }

    public int getNumParameters() {
        return numParameters;
    }
    public boolean isFlexible() {
        return numParameters == -1;
    }

    public String getDescription() {
        return description;
    }
}
