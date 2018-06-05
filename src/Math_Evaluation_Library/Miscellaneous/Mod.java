package Math_Evaluation_Library.Miscellaneous;

/**
 * Created by Antonio on 2017-07-11.
 */
public class Mod {

    public static long gcf (long num1, long num2){
        return gcd(num1, num2);
    }
    public static long gcd (long num1, long num2){
        long high = Math.abs(Math.max(num1, num2));
        long low = Math.abs(Math.min(num1, num2));
        long remainder = high%low;
        if (remainder == 0){
            return low;
        }
        return gcd(low, remainder);
    }
    public static long lcm (long num1, long num2){
        return num1*num2/gcd(num1, num2);
    }

    public static double gcf (double num1, double num2){
        return gcd(num1, num2);
    }
    public static double gcd (double num1, double num2){
        double high = Math.abs(Math.max(num1, num2));
        double low = Math.abs(Math.min(num1, num2));
        int remainder = (int)(high%low);
        if (remainder == 0){
            return low;
        }
        return gcd(low, remainder);
    }
    public static double lcm (double num1, double num2){
        return num1*num2/gcd(num1, num2);
    }

}
