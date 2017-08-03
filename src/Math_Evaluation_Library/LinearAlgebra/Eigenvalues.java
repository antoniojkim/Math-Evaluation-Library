package Math_Evaluation_Library.LinearAlgebra;

import Math_Evaluation_Library.Calculus.Roots;
import Math_Evaluation_Library.Objects.Fraction;
import Math_Evaluation_Library.Objects.Matrix;
import Math_Evaluation_Library.Sort;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Antonio on 2017-07-22.
 */
public class Eigenvalues {

    public static List<Fraction> calculate(Matrix matrix){
        return calculateEigenvalues(matrix);
    }
    public static List<Fraction> calculateEigenvalues(Matrix m){
        if (m.numRows() == m.numColumns()){
            Matrix matrix = m.getCopy();
            matrix.println();

            List<Fraction> eigenvalues = new ArrayList<>();

            if (matrix.numRows() == 2){
                eigenvalues.addAll(get2by2EigenValues(matrix));
            }
            else if (matrix.numRows() == 3){
                eigenvalues.addAll(get3by3EigenValues(matrix));
            }
            else{
                Matrix lambda = new Matrix(m.numRows(), m.numColumns(), -1);
                lambda.println();

            }

            Sort.quicksort(eigenvalues);
            return eigenvalues;
        }
        return null;
    }
    private static List<Fraction> get2by2EigenValues(Matrix m){
        if (m.numRows() == m.numColumns() && m.numRows() == 2){
            Fraction a = m.get(0, 0);
            Fraction b = m.get(0, 1);
            Fraction c = m.get(1, 0);
            Fraction d = m.get(1, 1);
            return Roots.quadraticFormula(new Fraction(1), a.getCopy().add(d).multiply(-1), a.getCopy().multiply(d).subtract(b.getCopy().multiply(c)));
        }
        return null;
    }
    private static List<Fraction> get3by3EigenValues(Matrix m){
        if (m.numRows() == m.numColumns() && m.numRows() == 3){
            double a = m.get(0, 0).getValue();
            double b = m.get(0, 1).getValue();
            double c = m.get(0, 2).getValue();
            double d = m.get(1, 0).getValue();
            double e = m.get(1, 1).getValue();
            double f = m.get(1, 2).getValue();
            double g = m.get(2, 0).getValue();
            double h = m.get(2, 1).getValue();
            double i = m.get(2, 2).getValue();
            List<Double> roots = Roots.cubicFormula(-1, i+e+a, f*h-e*i+c*g+b*d-a*i-a*e, c*d*h-c*e*g+b*f*g-b*d*i-a*f*h+a*e*i);
            List<Fraction> eigenvalues = new ArrayList<>();
            for (int j = 0; j<roots.size(); j++){
                eigenvalues.add(new Fraction(roots.get(j)));
            }
            return eigenvalues;
        }
        return null;
    }

}
