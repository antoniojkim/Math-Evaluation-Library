package Math_Evaluation_Library.Geometry;

import static java.lang.Double.NaN;

/**
 * Created by Antonio on 2017-07-12.
 */
public class Geometric {

    public static double ED(double... arguments) {
        return euclideanDistance(arguments);
    }
    public static double euclideanDistance(double... arguments){
        double distance = 0;
        for (double argument : arguments){
            distance += argument*argument;
        }
        return Math.sqrt(distance);
    }

    public static double pointDistance(double x1, double y1, double x2, double y2){
        return Math.sqrt(Math.pow(x2-x1, 2) + (Math.pow(y2-y1, 2)));
    }

    public static double calculateHypoteneuse(double a, double b){
        if (a > 0 && b > 0){
            return Math.sqrt(a*a+b*b);
        }
        return NaN;
    }
    public static double calculatePythagoreanSide(double a, double c){
        if (a > 0 && c > a){
            return Math.sqrt(c*c-a*a);
        }
        return NaN;
    }

}
