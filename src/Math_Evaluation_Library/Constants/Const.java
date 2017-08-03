package Math_Evaluation_Library.Constants;

import static java.lang.Double.NaN;

/**
 * Created by Antonio on 2017-07-12.
 */
public class Const {

    public static final String pi = "3.141592653589793238462643383279502884197169399375105820974944592307816406286208998628034825342117";
    public static final String e = "2.718281828459045235360287471352662497757247093699959574966967627724076630353547594571382178525";
    public static final String gr = "1.6180339887498948482045868343656381177203091798057628621354486227052604628189024497072072";
    public static final String sqrt2 = "1.41421356237309504880168872420969807856967187537694807317667973799073247846210703885038753432764";
    public static final String euler_macheroni = "0.577215664901532860606512090082402431042159335939923598805767234884867726777664670936947063291";

    public static String getConstant(String str){
        if (str.length() == 1){
            return getConstant(str.charAt(0));
        }
        if (str.length() == 2){
            char char1 = str.charAt(0), char2 = str.charAt(1);
            if (char1 == '√' && char2 == '2') {
                return sqrt2;
            }
            if (char1 == '-' && char2 == '∞') {
                return str;
            }
        }
        return "NaC"; // Not a Constant
    }
    public static String getConstant(char c){
        switch (c){
            case 'π':   return pi;
            case 'e':   return e;
            case 'η':   return e;
            case 'ϕ':   return gr;
            case 'γ':   return euler_macheroni;
            case '∞':   return "∞";
            default:    return "NaC"; // Not a Constant
        }
    }
}
