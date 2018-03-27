package Math_Evaluation_Library.LinearAlgebra;

import Math_Evaluation_Library.Calculus.Roots;
import org.jblas.DoubleMatrix;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Antonio on 2017-07-22.
 */
public class Eigenvalues {

    public static List<Double> calculate(DoubleMatrix matrix){
        return calculateEigenvalues(matrix);
    }
    public static List<Double> calculateEigenvalues(DoubleMatrix m){
        if (m.rows == m.columns){
            DoubleMatrix matrix = m.dup();

            List<Double> eigenvalues = new ArrayList<>();

            if (matrix.rows == 2){
                eigenvalues.addAll(get2by2EigenValues(matrix));
            }
            else if (matrix.rows == 3){
                eigenvalues.addAll(get3by3EigenValues(matrix));
            }

            eigenvalues.sort(Comparator.naturalOrder());
            return eigenvalues;
        }
        return null;
    }
    private static List<Double> get2by2EigenValues(DoubleMatrix m){
        if (m.rows == 2 && m.rows == m.columns){
            double a = m.get(0, 0);
            double b = m.get(1, 0);
            double c = m.get(0, 1);
            double d = m.get(1, 1);
            return Roots.quadraticFormula(1.0, -(a+d), a*d-b*c);
        }
        return null;
    }
    private static List<Double> get3by3EigenValues(DoubleMatrix m){
        if (m.rows == 3 && m.rows == m.columns){
            double a = m.get(0, 0);
            double b = m.get(1, 0);
            double c = m.get(2, 0);
            double d = m.get(0, 1);
            double e = m.get(1, 1);
            double f = m.get(2, 1);
            double g = m.get(0, 2);
            double h = m.get(1, 2);
            double i = m.get(2, 2);
            return Roots.cubicFormula(-1, i+e+a, f*h-e*i+c*g+b*d-a*i-a*e, c*d*h-c*e*g+b*f*g-b*d*i-a*f*h+a*e*i);
        }
        return null;
    }

}
