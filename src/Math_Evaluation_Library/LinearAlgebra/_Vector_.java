package Math_Evaluation_Library.LinearAlgebra;

import Math_Evaluation_Library.Objects.Fraction;

/**
 * Created by Antonio on 2017-07-22.
 */
public class _Vector_ {

    public static double dotProduct(double[] v, double[] w){
        double dot = 0;
        for (int a = 0; a<Math.min(w.length, v.length); a++){
            dot += v[a]*w[a];
        }
        return dot;
    }
    public static Fraction dotProduct(Fraction[] v, Fraction[] w){
        Fraction dot = new Fraction();
        for (int a = 0; a<Math.min(w.length, v.length); a++){
            dot.add(v[a].getCopy().multiply(w[a]));
        }
        return dot;
    }


    public static double[] crossProduct(double[] v, double[] w){
        double[] cross = {
                v[1]*w[2]-w[1]*v[2],
                v[2]*w[0]-w[2]*v[0],
                v[0]*w[1]-w[0]*v[1]};
        return cross;
    }

}
