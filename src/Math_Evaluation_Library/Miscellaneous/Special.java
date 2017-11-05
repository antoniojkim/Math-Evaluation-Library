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

}
