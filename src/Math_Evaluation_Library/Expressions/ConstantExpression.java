package Math_Evaluation_Library.Expressions;

import Math_Evaluation_Library.Constants.Constants;
import Math_Evaluation_Library.Engine.Engine;

import static Math_Evaluation_Library.ExpressionObjects.Operators.getOperator;

/**
 * Created by Antonio on 2017-11-23.
 */
public class ConstantExpression extends NumberExpression {

    char constant;

    public ConstantExpression(String var){
        super(Constants.getConstant(var), false);
        this.constant = var.charAt(var.length()-1);
    }
    public ConstantExpression(char var){
        super(Constants.getConstant(var), false);
        this.constant = var;
    }
    public ConstantExpression(char var, double val){
        super(val, false);
        this.constant = var;
    }

    @Override
    public boolean equals(Expression e) {
        return (e instanceof ConstantExpression && constant == ((ConstantExpression) e).getConstant()) || super.equals(e);
    }

    public char getConstant(){ return constant; }

    @Override
    public String infix() {
        return constant == 'ℯ' ? "e" : String.valueOf(constant);
    }

    @Override
    public String postfix() {
        return infix();
    }

    @Override
    public String toTeX() {
        return Constants.getTeX(constant);
    }

    @Override
    public String hardcode(String spacing) {
        return spacing+"new "+getClass().getSimpleName()+"('"+constant+"', "+new NumberExpression(n).hardcode()+")";
    }

    @Override
    public Expression calculateIntegral(){
        return new OperatorExpression(getOperator("*"), this, new VariableExpression(Engine.var));
    }
}
