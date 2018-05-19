package Math_Evaluation_Library.Expressions;

import Math_Evaluation_Library.Engine.Engine;
import Math_Evaluation_Library.Miscellaneous.MathRound;
import Math_Evaluation_Library.Objects._Number_;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static Math_Evaluation_Library.ExpressionObjects.Operators.getOperator;

/**
 * Created by Antonio on 2017-11-23.
 */
public class NumberExpression extends Expression {

    protected double n;
    protected String formatted = null;

    public NumberExpression(String str){
        n = MathRound.round(_Number_.getNumber(str, true), 14);
    }
    public NumberExpression(long n){
        this.n = n;
        formatted = String.valueOf(n);
    }
    public NumberExpression(double n){
        this(n, true);
    }
    public NumberExpression(double n, boolean round){
        if (round && n != Double.POSITIVE_INFINITY && n != Double.NEGATIVE_INFINITY){
            this.n = MathRound.round(n, 14);
        }
        else{
            this.n = n;
        }
    }

    @Override
    public double valueOf() {
        return n;
    }
    @Override
    public Expression evaluate() {
        return this;
    }

    @Override
    public boolean containsVar(String var) {
        return false;
    }

    @Override public boolean isValid() {    return true;    }

    @Override
    public boolean equals(Expression e) {
        return n == e.valueOf();
    }

    @Override
    public List<Double> getNumbers() {
        return new ArrayList<>(Collections.singletonList(n));
    }

    @Override public String infix() {
        if (formatted == null)  formatted = _Number_.format(n);
        return formatted;
    }
    @Override public String postfix() { return infix();  }
    @Override public String toTeX() {   return infix(); }

    @Override public String hardcode(String spacing) { return spacing+"new "+getClass().getSimpleName()+"("+infix()+")"; }

    @Override public Expression calculateDerivative(){
        return new NumberExpression(0);
    }
    @Override public Expression calculateIntegral(){
        return new OperatorExpression(getOperator("*"), this, new VariableExpression(Engine.var));
    }

}
