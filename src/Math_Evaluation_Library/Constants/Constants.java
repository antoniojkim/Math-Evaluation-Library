package Math_Evaluation_Library.Constants;

import Math_Evaluation_Library.Search;

import static java.lang.Double.NaN;

/**
 * Created by Antonio on 2017-07-12.
 */
public class Constants {


    public static final double pi = 3.14159265358979324;
    public static final double e = 2.71828182845904523536;
    public static final double gr = 1.6180339887499;
    public static final double sqrt2 = 1.41421356237309505;
    public static final double ln2 = 0.6931471805599453;
    public static final double euler_macheroni = 0.57721566490153286;

    public static double getConstant(String str){
        if (str.length() == 1){
            return getConstant(str.charAt(0));
        }
        if (str.length() == 2){
            char char1 = str.charAt(0), char2 = str.charAt(1);
            if (char1 == '√' && char2 == '2') {
                return sqrt2;
            }
            if (char1 == '-') {
                return getNegativeConstant(char2);
            }
        }
        return NaN;
    }
    public static double getConstant(char c){
        switch (c){
            case 'π':   return pi;
            case 'e':   return e;
            case 'ℯ':   return e;
            case 'ϕ':   return gr;
            case 'γ':   return euler_macheroni;
            case '∞':   return Double.POSITIVE_INFINITY;
            default:    return NaN;
        }
    }
    public static String getTeX(char c){
        switch (c){
            case 'π':   return "\\pi";
            case 'ℯ':   return "e";
            case 'ϕ':   return "\\phi";
            case 'γ':   return "\\gamma";
            case '∞':   return "\\infty";
            default:    return String.valueOf(c);
        }
    }

    public static char[] constantChar = {'e', 'γ', 'π', 'ϕ', '∞'};
    public static boolean isConstant(String str){
        if (str.length() == 1){
            return isConstant(str.charAt(0));
        }
        if (str.length() == 2){
            char char1 = str.charAt(0), char2 = str.charAt(1);
            if (char1 == '√' && char2 == '2') {
                return true;
            }
            if (char1 == '-') {
                return isConstant(char2);
            }
        }
        return false;
    }
    public static boolean isConstant(char c){
        return Search.contains(constantChar, c);
    }


    public static final double negative_pi = -3.14159265358979324;
    public static final double negative_e = -2.71828182845904523536;
    public static final double negative_gr = -1.6180339887499;
    public static final double negative_sqrt2 = -1.41421356237309505;
    public static final double negative_euler_macheroni = -0.57721566490153286;

    public static double getNegativeConstant(char c){
        switch (c){
            case 'π':   return negative_pi;
            case 'e':   return negative_e;
            case 'ℯ':   return negative_e;
            case 'ϕ':   return negative_gr;
            case 'γ':   return negative_euler_macheroni;
            case '∞':   return Double.NEGATIVE_INFINITY;
            default:    return NaN;
        }
    }


}
