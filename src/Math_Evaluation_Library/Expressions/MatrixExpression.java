package Math_Evaluation_Library.Expressions;

import Math_Evaluation_Library.LinearAlgebra._Matrix_;
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

    public DoubleMatrix getMatrix(){
        return m;
    }
    public String getStrMatrix(){
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
        return e instanceof MatrixExpression && m.equals(((MatrixExpression) e).getMatrix());
    }

    @Override
    public List<Double> getNumbers() {
        return new ArrayList<>();
    }

    @Override
    public String infix() {
        return getStrMatrix();
    }

    @Override
    public String postfix() {
        return getStrMatrix();
    }

    @Override public String hardcode(String spacing) { return spacing+"new "+getClass().getSimpleName()+"(\""+getStrMatrix()+"\")"; }
}
