package Math_Evaluation_Library.Statistics;

/**
 * Created by Antonio on 2017-07-12.
 */
public class Stats {

    public static double variance(double[] data){
        double mean = 0;
        for (double value : data){
            mean += value;
        }
        mean /= data.length;
        double variance = 0;
        for (double value : data){
            variance += Math.pow(value-mean, 2);
        }
        variance /= (data.length-1);
        return variance;
    }

    public static double stndv(double[] data){
        return Math.sqrt(variance(data));
    }
    public static double stnDev(double[] data){
        return Math.sqrt(variance(data));
    }

}
