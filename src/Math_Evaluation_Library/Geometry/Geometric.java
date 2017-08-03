package Math_Evaluation_Library.Geometry;

/**
 * Created by Antonio on 2017-07-12.
 */
public class Geometric {

    public static double pointDistance(double x1, double y1, double x2, double y2){
        return Math.sqrt(Math.pow(x2-x1, 2) + (Math.pow(y2-y1, 2)));
    }

}
