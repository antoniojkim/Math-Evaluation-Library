package Math_Evaluation_Library.Expressions.NumberExpressions;

import Math_Evaluation_Library.Expressions.Expression;
import Math_Evaluation_Library.Objects._Number_;

import static java.lang.Double.NaN;

/**
 * Created by Antonio on 2017-11-23.
 */
public class ComplexExpression extends NumberExpression {

    double imaginary;

    public ComplexExpression(double real, double imaginary){
        super(real);
        this.imaginary = imaginary;
    }
    public ComplexExpression(double imaginary){
        super(0);
        this.imaginary = imaginary;
    }
    public ComplexExpression(String imaginary){
        this(_Number_.getNumber(imaginary, true));
    }

    @Override
    public double valueOf() {
        return imaginary == 0 ? n : NaN;
    }
    public double getReal() {
        return n;
    }
    public double getImaginary() {
        return imaginary;
    }
    @Override
    public boolean equals(Expression e) {
        if (e instanceof ComplexExpression){
            ComplexExpression c = (ComplexExpression) e;
            return getReal() == c.getReal() && getImaginary() == c.getImaginary();
        }
        return false;
    }

    @Override
    public String infix() {
        String infix = "";
        if (imaginary > 0){
            if (imaginary != 1)    infix += _Number_.format(imaginary);
            infix += "i";
            if (n != 0){
                infix = _Number_.format(n)+"+"+infix;
            }
        }
        else if (imaginary < 0){
            if (imaginary != -1)    infix += _Number_.format(-imaginary);
            infix += "i";
            if (n != 0){
                infix = _Number_.format(n)+"-"+infix;
            }
        }
        else{
            infix = _Number_.format(n);
        }
        return infix;
    }

    @Override
    public String postfix() {
        String postfix = "";
        if (imaginary > 0){
            if (imaginary != 1)    postfix += _Number_.format(imaginary);
            postfix += "i";
            if (n != 0){
                postfix = _Number_.format(n)+" "+postfix+" +";
            }
        }
        else if (imaginary < 0){
            if (imaginary != -1)    postfix += _Number_.format(-imaginary);
            postfix += "i";
            if (n != 0){
                postfix = _Number_.format(n)+" "+postfix+" -";
            }
        }
        else{
            postfix = _Number_.format(n);
        }
        return postfix;
    }

    @Override
    public String hardcode(String spacing) {
        return spacing+"new "+getClass().getSimpleName()+"("+_Number_.format(n)+", "+_Number_.format(imaginary)+")";
    }
}
