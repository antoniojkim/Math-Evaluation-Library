package Math_Evaluation_Library.Expressions;

import Math_Evaluation_Library.Engine.Engine;
import Math_Evaluation_Library.Objects._Number_;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static Math_Evaluation_Library.ExpressionObjects.Operators.getOperator;

/**
 * Created by Antonio on 2017-11-23.
 */
public class NumberExpression extends Expression {

    protected double n;

    public NumberExpression(String str){
        n = _Number_.getNumber(str, true);
    }
    public NumberExpression(double n){
        this.n = n;
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
        return e instanceof NumberExpression && n == e.valueOf();
    }

    @Override
    public List<Double> getNumbers() {
        return new ArrayList<>(Arrays.asList(n));
    }

    @Override public String infix() {   return _Number_.format(n);  }
    @Override public String postfix() { return _Number_.format(n);  }
    @Override public String hardcode(String spacing) { return spacing+"new "+getClass().getSimpleName()+"("+infix()+")"; }

    @Override public Expression getDerivative(){
        return new NumberExpression(0);
    }
    @Override public Expression getIntegral(){
        return new OperatorExpression(getOperator("*"), this, new VariableExpression(Engine.var));
    }

}
