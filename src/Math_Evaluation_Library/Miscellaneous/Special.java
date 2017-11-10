package Math_Evaluation_Library.Miscellaneous;

/**
 * Created by Antonio on 2017-11-04.
 */
public class Special {

    private static final double[] errorFunctionCoefficients = {0.17087277, -0.82215223, 1.48851587, -1.13520398, 0.27886807, -0.18628806, 0.09678418, 0.37409196, 1.00002368, -1.26551223};
    public static double errorFunction(double x){
//        double t = 1/(1+0.5*Math.abs(x));
//        double y = t;
//        //- 1.26551223 + 1.00002368*y + 0.37409196*(y*=t) + 0.09678418*(y*=t) - 0.18628806*(y*=t) + 0.27886807*(y*=t) - 1.13520398*(y*=t) + 1.48851587*(y*=t) - 0.82215223*(y*=t) + 0.17087277*(y*=t)
//
//        double tau = ((((((((0.17087277*t - 0.82215223)*t + 1.48851587)*t - 1.13520398)*t + 0.27886807)*t - 0.18628806)*t + 0.09678418)*t + 0.37409196)*t + 1.00002368)*t - 1.26551223;
//        return MathRound.round(x >= 0 ? 1-t*Math.exp(-(x*x) + tau) : t*Math.exp(-x*x + tau)-1, 7);
        double t = 1.0/(1.0+0.3275911*x);
        double tau = t*(0.254829592+t*(-0.284496736+t*(1.421413741+t*(-1.453152027+t*1.061405429))));
        return MathRound.round(1-tau*Math.exp(-x*x), 7);
    }
    public static double complementaryErrorFunction(double x){
        x += 0.6388;
        return MathRound.round(1.36936*Math.exp(-0.8072*x*x), 4);
    }

    private static final double[] gamma_coefficients = {
            676.5203681218851,
            -1259.1392167224028,
            771.32342877765313,
            -176.61502916214059,
            12.507343278686905,
            -0.13857109526572012,
            9.9843695780195716e-6,
            1.5056327351493116e-7
    };
    public static double gammaFunction(double z){
        boolean isZint = z%1 == 0;
        z--;
        double x = 0.99999999999980993;
        for (int i = 0; i<gamma_coefficients.length; i++){
            x += gamma_coefficients[i]/(z+i+1);
        }
        double t = z + gamma_coefficients.length - 0.5;
        double y = 2.5066282746310005 * Math.pow(t, z+0.5) * Math.exp(-t) * x;
        if (isZint)   return Math.rint(y);
        return MathRound.round(y, 15);
    }

}
