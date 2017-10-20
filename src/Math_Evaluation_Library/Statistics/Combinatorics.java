package Math_Evaluation_Library.Statistics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Double.NaN;

/**
 * Created by Antonio on 2017-10-04.
 */
public class Combinatorics {

    private static List<Double> factorials = new ArrayList<>(Arrays.asList(1.0, 1.0, 2.0, 6.0, 24.0, 120.0, 720.0, 5040.0));
    public static double fact(int num){
        try{
            if (num < 0 || num > 170){
                return NaN;
            }
            if (num < 2){
                return 1;
            }
            if (num < factorials.size()){
                return factorials.get(num);
            }
            double factorial = factorials.get(factorials.size()-1);
            for (double i = factorials.size(); i<=num; i++){
                factorial *= i;
                factorials.add(factorial);
            }
            return factorial;
        }catch(NumberFormatException e){
            return NaN;
        }
    }

    public static double permute(int n, int r){
        if (r == 0)    return 1;
        if (r == 1)    return n;
        if (n > 170){
            double num = n;
            for (int i = n-1; i>(n-r); i--){
                num *= i;
            }
            if (num == Double.POSITIVE_INFINITY) return NaN;
            return num;
        }
        return (fact(n) / fact(n - r));
    }

    public static double choose(int n, int r){
        if (r == 0)    return 1;
        if (r == 1)    return n;
        if (2*r > n)   r = n-r;
        if (n > 170){
            double num = n;
            for (int i = n-1; i>(n-r); i--){
                num *= i;
            }
            if (num == Double.POSITIVE_INFINITY) return NaN;
            return num/fact(r);
        }
        return (fact(n) / (fact(n - r) * fact(r)));
    }

}
