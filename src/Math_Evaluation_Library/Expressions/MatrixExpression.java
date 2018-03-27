package Math_Evaluation_Library.Expressions;

import Math_Evaluation_Library.LinearAlgebra._Matrix_;
import Math_Evaluation_Library.Miscellaneous.Fraction;
import org.jblas.DoubleMatrix;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Double.NaN;

/**
 * Created by Antonio on 2017-11-23.
 */
public class MatrixExpression extends Expression {

    String strMatrix = null;
    DoubleMatrix m;

    public MatrixExpression(String strMatrix){
        m = _Matrix_.toDoubleMatrix(strMatrix);
    }
    public MatrixExpression(DoubleMatrix m){
        this.m = m;
    }

    public DoubleMatrix doubleMatrix(){
        return m;
    }
    public String strMatrix(){
        if (strMatrix == null)  strMatrix = _Matrix_.toStrMatrix(m);
        return strMatrix;
    }

    @Override public double valueOf() {
        return NaN;
    }
    @Override public Expression evaluate() {
        return this;
    }

    @Override
    public boolean containsVar(String var) {
        return false;
    }

    @Override public boolean isValid() {    return true;    }

    @Override
    public boolean equals(Expression e) {
        return e instanceof MatrixExpression && m.equals(((MatrixExpression) e).doubleMatrix());
    }

    @Override
    public List<Double> getNumbers() {
        List<Double> numbers = new ArrayList<>(m.toArray().length);
        for (double n : m.toArray()){
            numbers.add(n);
        }
        return numbers;
    }

    @Override
    public String infix() {
        return strMatrix();
    }

    @Override
    public String postfix() {
        return strMatrix();
    }

    @Override
    public String toTeX() {
        StringBuilder TeX = new StringBuilder();
        TeX.append("\\begin{bmatrix} ");
        double[][] values = m.toArray2();
        for (int i = 0; i<values.length; i++){
            if (i != 0){
                TeX.append(" \\\\ ");
            }
            for (int j = 0; j<values[i].length; j++){
                if (j != 0){
                    TeX.append(" & ");
                }
                TeX.append(Fraction.toExpression(values[i][j]).simplify().toTeX());
            }
        }
        TeX.append(" \\end{bmatrix}");
        return TeX.toString();
    }

    @Override public String hardcode(String spacing) { return spacing+"new "+getClass().getSimpleName()+"(\""+ strMatrix()+"\")"; }
}
