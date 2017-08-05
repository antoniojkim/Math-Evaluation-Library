package Math_Evaluation_Library.Miscellaneous;

import Math_Evaluation_Library.Objects._Number_;

import java.math.BigDecimal;

import static java.lang.Double.NaN;

/**
 * Created by Antonio on 2017-07-11.
 */
public class MathRound {

    public static double round(double num, int place){
        try{
            if (place == 0){
                return num;
            }
            double rounded = num*Math.pow(10, place);
            if (Math.abs(rounded-Math.ceil(rounded)) <  Math.abs(rounded-Math.floor(rounded))){
                return Math.ceil(rounded)/Math.pow(10, place);
            }
            return Math.floor(rounded)/Math.pow(10, place);
        }catch(NumberFormatException e){}
        return NaN;
    }
    public static String roundf (double num, int place){
        return roundf(num, place, true);
    }
    public static String roundf (double num, int place, boolean bounded){
        if (num == Double.POSITIVE_INFINITY){
            return "∞";
        }
        if (num == Double.NEGATIVE_INFINITY){
            return "-∞";
        }
        if (!_Number_.isNumber(num)){
            return "NaN";
        }
        if (bounded){
            num = round(num, place);
        }
        return roundf(String.valueOf(num), place, true);
    }
    public static String roundf (String num, int place){
        return roundf(num, place, true);
    }
    public static String roundf(String number, int place, boolean bounded){
        try{
            double num = _Number_.getNumber(number);
            number = String.valueOf(round(num, place));
            boolean containsE = number.contains("E");
            if (containsE && (!bounded || num < Math.pow(10, 15))){
                BigDecimal bi = new BigDecimal(num);
                number = bi.toPlainString();
            }
            if (num%1 == 0 && number.contains(".") && !containsE){
                number = number.substring(0, number.indexOf("."));
            }
            if (bounded){
                number = _Number_.commaFormat(number);
            }
        }catch(NumberFormatException e){}
        return _Number_.format(number);
    }
}
