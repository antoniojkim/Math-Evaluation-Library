package Math_Evaluation_Library.Constants;

import java.util.HashMap;

/**
 * Created by Antonio on 2018-03-25.
 */
public class PredefinedTeX {

    public static final HashMap<String, String> functions = createFunctions();

    public static HashMap<String, String> createFunctions(){
        String[] functions = {
                "sin", "cos", "tan", "csc", "sec", "cot",
                "arcsin", "arccos", "arctan", "arccsc", "arcsec", "arccot",
                "sinh", "cosh", "tanh", "coth"
        };
        HashMap<String, String> function = new HashMap<>();
        for (String f : functions){
            function.put(f, "\\"+f);
        }
        return function;
    }

}
