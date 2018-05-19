package Math_Evaluation_Library.Statistics;

import java.util.Arrays;

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
    public static double varianceGeneral(double[] data){
        double mean = 0;
        for (double value : data){
            mean += value;
        }
        mean /= data.length;
        double variance = 0;
        for (double value : data){
            variance += value*value;
        }
        variance = (variance-data.length*mean*mean)/(data.length-1);
        return variance;
    }

    public static double stndv(double[] data){
        return Math.sqrt(variance(data));
    }
    public static double stnDev(double[] data){
        return Math.sqrt(variance(data));
    }


    public static double kurtosis(double[] y){
        double mean = 0;
        for (double value : y){
            mean += value;
        }
        mean /= y.length;

        double k1 = 0;
        for (double value : y){
            k1 += Math.pow(value-mean, 4);
        }
        k1 /= y.length;

        double k2 = 0;
        for (double value : y){
            k2 += Math.pow(value-mean, 2);
        }
        k2 = Math.pow(k2/y.length, 2);
        return k1/k2;
    }
    public static double skewness(double[] y){
        double mean = 0;
        for (double value : y){
            mean += value;
        }
        mean /= y.length;

        double s1 = 0;
        for (double value : y){
            s1 += Math.pow(value-mean, 3);
        }
        s1 /= y.length;

        double s2 = 0;
        for (double value : y){
            s2 += Math.pow(value-mean, 2);
        }
        s2 = Math.pow(s2/y.length, 1.5);
        return s1/s2;
    }
    public static double correlation(double[][] data){
        double xmean = 0, ymean = 0;
        for (double[] value : data){
            xmean += value[0];
            ymean += value[1];
        }
        xmean /= data.length;
        ymean /= data.length;

        double sxy = 0, sxx = 0, syy = 0;
        for (double[] value : data){
            sxy += (value[0]-xmean)*(value[1]-ymean);
            sxx += Math.pow((value[0]-xmean), 2);
            syy += Math.pow((value[1]-ymean), 2);
        }

        return sxy/ Math.sqrt(sxx*syy);
    }


    public static double getMax(double... values){
        double max = values[0];
        for (int i = 1; i<values.length; ++i){
            if (values[i] > max){
                max = values[i];
            }
        }
        return max;
    }
    public static double getMin(double... values){
        double min = values[0];
        for (int i = 1; i<values.length; ++i){
            if (values[i] < min){
                min = values[i];
            }
        }
        return min;
    }
    public static double[] getQuartiles(double... values){
        Arrays.sort(values);
        double m = (values.length+1)/2.0;
        double median = m%1 == 0 ? values[(int)m-1] : (values[(int)Math.floor(m)-1]+values[(int)Math.ceil(m)-1])/2;
        m /= 2;
        double Q1 = m%1 == 0 ? values[(int)m-1] : (values[(int)Math.floor(m)-1]+values[(int)Math.ceil(m)-1])/2;
        m *= 3;
        double Q3 = m%1 == 0 ? values[(int)m-1] : (values[(int)Math.floor(m)-1]+values[(int)Math.ceil(m)-1])/2;

        return new double[]{Q1, median, Q3};
    }


}
