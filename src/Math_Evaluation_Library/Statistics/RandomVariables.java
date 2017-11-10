package Math_Evaluation_Library.Statistics;

import Math_Evaluation_Library.Miscellaneous.MathRound;

import static Math_Evaluation_Library.Statistics.Combinatorics.choose;
import static java.lang.Double.NaN;

/**
 * Created by Antonio on 2017-10-04.
 */
public class RandomVariables {

    public static double hyperGeometricDistribution(int N, int r, int n, int x){
        if (x >= Math.max(0, n-(N-r)) && x <= Math.min(n, r)){
            return choose(r, x)*choose(N-r, n-x)/choose(N, n);
        }
        return 0;
    }
    public static double hyperGeometricDistribution_ExpectedValue(double N, int r, int n){
        if (N%1 == 0 && N > 0 && r >= 0 && n >= 0) {
            return (n * r) / N;
        }
        return NaN;
    }
    public static double hyperGeometricDistribution_Variance(double N, int r, int n){
        if (N%1 == 0 && N > 1 && r >= 0 && n >= 0) {
            return (n * r * (N - r) * (N - n)) / (N*N*(N-1));
        }
        return NaN;
    }

    public static double binomialDistribution(int n, double p, int x){
        if (n>=0 && p>=0 && p<=1 && x>=0 && x<=n){
            return choose(n, x)*Math.pow(p, x)*Math.pow(1-p, n-x);
        }
        return 0;
    }
    public static double binomialDistribution_ExpectedValue(int n, double p){
        return (n>=0 && p>=0 && p<=1) ? (n * p) : NaN;
    }
    public static double binomialDistribution_Variance(int n, double p){
        return (n>=0 && p>=0 && p<=1) ? (n * p * (1 - p)) : NaN;
    }

    public static double negativeBinomialDistribution(int k, double p, int x){
        if (k>=1 && p>=0 && p<=1){
            return choose(x+k-1, x)*Math.pow(p, k)*Math.pow(1-p, x);
        }
        return 0;
    }
    public static double negativeBinomialDistribution_ExpectedValue(int k, double p){
        return (k>=1 && p>=0 && p<=1) ? k*(1-p)/p : NaN;
    }
    public static double negativeBinomialDistribution_Variance(int k, double p){
        return (k>=1 && p>=0 && p<=1) ? k*(1-p)/(p*p) : NaN;
    }

    public static double geometricDistribution(double p, int x){
        if (p>=0 && p<=1 && x>=0){
            return Math.pow(p, x)*Math.pow(1-p, x);
        }
        return 0;
    }
    public static double geometricDistribution_ExpectedValue(double p){
        return (p>=0 && p<=1) ? (1-p)/p : NaN;
    }
    public static double geometricDistribution_Variance(double p){
        return (p>=0 && p<=1) ? (1-p)/(p*p) : NaN;
    }

    public static double poissonDistribution(double lambda, int x){
        return Math.exp(-lambda)*Math.pow(lambda, x)/Combinatorics.fact_int(x);
    }
    public static double poissonDistribution_ExpectedValue(double lambda){
        return lambda;
    }
    public static double poissonDistribution_Variance(double lambda){
        return lambda;
    }

    public static double uniformDistribution(int a, int b, int x){
        return (a<=x && x<=b ? 1/(b-a+1) : 0);
    }

    public static double exponentialDistribution(double theta, double x){
        return (x > 0 && theta > 0) ? Math.exp(-x/theta)/theta : 0;
    }
    public static double exponentialDistributionCDF(double theta, double x){
        return (x > 0 && theta > 0) ? 1-Math.exp(-x/theta) : 0;
    }
    public static double exponentialDistribution_ExpectedValue(double theta){
        return (theta > 0) ? theta : NaN;
    }
    public static double exponentialDistribution_Variance(double theta){
        return (theta > 0) ? theta*theta : NaN;
    }

    public static double normalDistribution(double mean, double stdv, double x){
        if (stdv > 0){
            double t = x-mean;
            return Math.exp(-0.5*t*t/(stdv))/(0.3989422804014327*Math.sqrt(stdv));
        }
        return NaN;
    }
    public static double standardNormalDistributionCDF(double x){ // Cumulative Distribution Function for Standard Normal Distribution
        if (x >= 0){
//            static final double stdNCDF_a1 =  0.254829592;
//            static final double stdNCDF_a2 = -0.284496736;
//            static final double stdNCDF_a3 =  1.421413741;
//            static final double stdNCDF_a4 = -1.453152027;
//            static final double stdNCDF_a5 =  1.061405429;
//            static final double stdNCDF_p =  0.3275911;

            final double sign = x < 0 ? -0.5 : 0.5;
            x = Math.abs(x)/ 1.41421356237309505;

            // A&S formula 7.1.26
            double t = 1.0/(1.0 + 0.3275911 *x);
            double y = 1.0 - (((((1.061405429 *t - 1.453152027)*t) + 1.421413741)*t - 0.284496736)*t + 0.254829592)*t*Math.exp(-x*x);

            return MathRound.round(0.5 + sign*y, 6); // MathRound.round( 0.5* Special.errorFunction(x/1.41421356237309505), 7)
        }
        return 0;
    }
    public static double standardNormalDistributionCDFInverse(double x){ // Cumulative Distribution Function for Standard Normal Distribution
        if (x >= 0 && x <= 1){

            double y = x-0.5;
            double z = y*y;

            double t = y*(((-25.44106049637*z+41.39119773534)*z-18.61500062529)*z+2.50662823884)/
                    ((((3.13082909833*z-21.06224101826)*z+23.08336743743)*z-8.47351093090)*z+1);

            return MathRound.round(t, 4);
        }
        return NaN;
    }

}
