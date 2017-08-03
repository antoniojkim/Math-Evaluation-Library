package Math_Evaluation_Library.Trigonometry;

import static java.lang.Double.NaN;

/**
 * Created by Antonio on 2017-07-11.
 */
public class Trig {

    public static double csc (double num){
        try{
            return (1/Math.sin(num));
        }catch(ArithmeticException e){}
        return NaN;
    }
    public static double sec (double num){
        try{
            return (1/Math.cos(num));
        }catch(ArithmeticException e){}
        return NaN;
    }
    public static double cot (double num){
        try{
            return (1/Math.tan(num));
        }catch(ArithmeticException e){}
        return NaN;
    }

    public static double acsc (double num){
        try{
            return Math.asin(1/num);
        }catch(ArithmeticException e){}
        return NaN;
    }
    public static double asec (double num){
        try{
            return Math.acos(1/num);
        }catch(ArithmeticException e){}
        return NaN;
    }
    public static double acot (double num){
        try{
            return (Math.PI / 2 - Math.atan(num));
        }catch(ArithmeticException e){}
        return NaN;
    }

    public static double csch (double num){
        try{
            return (1/Math.sinh(num));
        }catch(ArithmeticException e){}
        return NaN;
    }
    public static double sech (double num){
        try{
            return (1/Math.cosh(num));
        }catch(ArithmeticException e){}
        return NaN;
    }
    public static double coth (double num){
        try{
            return (1/Math.tanh(num));
        }catch(ArithmeticException e){}
        return NaN;
    }

    public static double asinh (double num){
        try{
            return Math.log(num+Math.sqrt(num*num+1));
        }catch(ArithmeticException e){}
        return NaN;
    }
    public static double acosh (double num){
        try{
            return Math.log(num+Math.sqrt(num*num-1));
        }catch(ArithmeticException e){}
        return NaN;
    }
    public static double atanh (double num){
        try{
            return 0.5*(Math.log(1+num)-Math.log(1-num));
        }catch(ArithmeticException e){}
        return NaN;
    }

    public static double acsch (double num){
        try{
            return asinh(1/num);
        }catch(ArithmeticException e){}
        return NaN;
    }
    public static double asech (double num){
        try{
            return acosh(1/num);
        }catch(ArithmeticException e){}
        return NaN;
    }
    public static double acoth (double num){
        try{
            return atanh(1/num);
        }catch(ArithmeticException e){}
        return NaN;
    }

}
