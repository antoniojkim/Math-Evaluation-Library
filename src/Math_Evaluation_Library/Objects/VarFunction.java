package Math_Evaluation_Library.Objects;

import Math_Evaluation_Library.Engine.Engine;

/**
 * Created by Antonio on 2017-11-10.
 */
public class VarFunction extends MathObject {

    public static int maxStrLength = 1;
    public static int minStrLength = 1;
    private String f;

    public VarFunction (String name, String function){
        this.function = name;
        if (name.length() > maxStrLength){
            maxStrLength = name.length();
        }
        else if (name.length() < minStrLength){
            minStrLength = name.length();
        }
        f = function;
    }

    public String evaluate(String parameter){
        return Engine.evaluateString(f, parameter);
    }

    public void redefine(String function){
        f = function;
    }

}
