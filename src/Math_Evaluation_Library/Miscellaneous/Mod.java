package Math_Evaluation_Library.Miscellaneous;

/**
 * Created by Antonio on 2017-07-11.
 */
public class Mod {

    public static int gcf (int num1, int num2){
        return gcd(num1, num2);
    }
    public static int gcd (int num1, int num2){
        int high = Math.abs(Math.max(num1, num2));
        int low = Math.abs(Math.min(num1, num2));
        int remainder = high%low;
        if (remainder == 0){
            return low;
        }
        return gcd(low, remainder);
    }
    public static int lcm (int num1, int num2){
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
