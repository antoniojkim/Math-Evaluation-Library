package Math_Evaluation_Library.Miscellaneous;

import Math_Evaluation_Library.Engine.Engine;
import Math_Evaluation_Library.Objects._Number_;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Antonio on 2017-07-12.
 */
public class _Function_ {

//    public static boolean isFunction (String str){
//        String check = str.toLowerCase();
//        if (check.contains("error") || check.contains("nan") || check.contains("infinity")){
//            return false;
//        }
//        Function f = new Function(str);
//        for (int a = 10; a>1; a--){
//            double r1 = MathRound.round(_Random_.random(1, a), 5);
//            double r2 = MathRound.round(_Random_.random(1, a), 5);
//            double r3 = MathRound.round(_Random_.random(1, a), 5);
//            if (_Number_.isNumber(f.of(r1)) && _Number_.isNumber(f.of(r2)) && _Number_.isNumber(f.of(r3))){
//                return true;
//            }
//        }
//        return false;
//    }
//    public static double getFunctionNumber (String str){
//        Function f = new Function(str);
//        for (int a = 5; a>0; a--){
//            double r1 = MathRound.round(_Random_.random(0, a), 5);
//            double r2 = MathRound.round(_Random_.random(0, a), 5);
//            double r3 = MathRound.round(_Random_.random(0, a), 5);
//            try{
//                double f2 = f.of(r2);
//                if (f.of(r1) == f2 && f2 == f.of(r3)){
//                    return f2;
//                }
//            }catch(NumberFormatException e){}
//        }
//        return NaN;
//    }


    public static List<Double> extractNumbers (String function){
        return extractNumbers(function, true);
    }
    public static List<Double> extractNumbers (String function, boolean getNegative){
        List<Double> numbers = new ArrayList<>();
        String number = "";
        for (int x = 0; x<function.length(); x++){
            try{
                try{
                    int digit = Integer.parseInt(function.substring(x, x+1));
                    number += digit+"";
                }catch(NumberFormatException e){
                    double digit = Double.parseDouble(function.substring(x, x+1));
                }
            }
            catch(NumberFormatException e){
                if (function.substring(x, x+1).equals(".")){
                    number+=".";
                }
                else if (function.substring(x, x+1).equals("-")){
                    if (number.equals("")){
                        number+="-";
                    }
                    else{
                        numbers.add(Engine.evaluate(number));
                        number = "-";
                    }
                }
                else if (function.substring(x, x+1).equals("/") && _Number_.isNumber(number)
                        && _Number_.isNumber(function.substring(x+1, x+2))){
                    number+="/"+function.substring(x+1, x+2);
                    x++;
                }
                else if (number.equals("-")){
                    if (getNegative){
                        numbers.add(-1.0);
                    }
                    number = "";
                }
                else if (!number.equals("")){
                    numbers.add(Engine.evaluate(number));
                    number = "";
                }
            }
            if (x == function.length()-1 && !number.equals("")){
                numbers.add(Engine.evaluate(number));
                number = "";
            }
        }
        if (numbers.size() < 3 && function.contains("√") && !function.contains("+")){
            numbers.add(0.5);
        }
        if (numbers.size() < 3 && function.contains("log")){
            numbers.add(10.0);
        }
        if (numbers.size() == 0){
            numbers.add(1.0);
        }
        return numbers;
    }

}
