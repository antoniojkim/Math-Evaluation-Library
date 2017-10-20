package Math_Evaluation_Library.Statistics;

import static Math_Evaluation_Library.Statistics.Combinatorics.choose;

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

    public static double binomialDistribution(int n, double p, int x){
        if (n>=0 && p>=0 && p<=1 && x>=0 && x<=n){
            return choose(n, x)*Math.pow(p, x)*Math.pow(1-p, n-x);
        }
        return 0;
    }

    public static double negativeBinomialDistribution(int k, double p, int x){
        if (k>=1 && p>=0 && p<=1 && x>=k){
            return choose(x+k-1, k-1)*Math.pow(p, x)*Math.pow(1-p, x);
        }
        return 0;
    }

    public static double geometricDistribution(double p, int x){
        if (p>=0 && p<=1 && x>=1){
            return Math.pow(p, x)*Math.pow(1-p, x);
        }
        return 0;
    }

    public static double poissonDistribution(double lambda, int x){
        return Math.exp(-lambda)*Math.pow(lambda, x)/Combinatorics.fact(x);
    }

    public static double uniformDistribution(int a, int b, int x){
        return (a<=x && x<=b ? 1/(b-a+1) : 0);
    }

}
