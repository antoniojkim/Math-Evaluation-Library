package Math_Evaluation_Library.Expressions;

import Math_Evaluation_Library.ExpressionObjects.Operator;
import Math_Evaluation_Library.ExpressionObjects.Operators;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Antonio on 2017-11-23.
 */
public class OperatorExpression extends Expression {

    Operator operator;
    Expression param1;
    Expression param2;

    public OperatorExpression(String operator, Expression param1){
        this(Operators.getOperator(operator), param1);
    }
    public OperatorExpression(Operator operator, Expression param1){
        this.operator = operator;
        this.param1 = param1;
        this.param2 = null;
    }
    public OperatorExpression(String operator, Expression param1, Expression param2){
        this(Operators.getOperator(operator), param1, param2);
    }
    public OperatorExpression(Operator operator, Expression param1, Expression param2){
        this.operator = operator;
        this.param1 = param1;
        this.param2 = param2;
    }

    @Override
    public Expression evaluate() {
        Expression x = param1.evaluate();
        if (x instanceof InvalidExpression) return x;
        if (operator.isSingleOperator()){
            return operator.evaluate(x);
        }
        Expression y = param2.evaluate();
        if (y instanceof InvalidExpression) return y;
        return operator.evaluate(x, y);
    }


    @Override
    public void set(String var, Expression val) {
        if (param1 != null) param1.set(var, val);
        if (param2 != null) param2.set(var, val);
    }

    @Override
    public void unset() {
        if (param1 != null) param1.unset();
        if (param2 != null) param2.unset();
    }

    @Override
    public void unset(String var) {
        if (param1 != null) param1.unset(var);
        if (param2 != null) param2.unset(var);
    }

    @Override
    public boolean containsVar(String var) {
        return param1.containsVar(var) || param2.containsVar(var);
    }


    @Override
    public boolean isValid() {
        if (operator.isSingleOperator()){
            return param1.isValid();
        }
        return param1.isValid() && param2.isValid();
    }

    @Override
    public boolean equals(Expression e) {
        if (e instanceof OperatorExpression){
            OperatorExpression oe = (OperatorExpression) e;
            if (operator.toString().equals(oe.getOperator().toString())){
                if (operator.isSingleOperator()){
                    return param1.equals(oe.getParam1());
                }
                return param1.equals(oe.getParam1()) && param2.equals(oe.getParam2());
            }
        }
        return false;
    }

    @Override
    public List<Double> getNumbers() {
        List<Double> list = new ArrayList<>();
        if (param1 != null) list.addAll(param1.getNumbers());
        if (param2 != null) list.addAll(param2.getNumbers());
        return list;
    }

    @Override
    public String infix() {
        if (operator.isSingleOperator()){
            return operator.infix(param1);
        }
        return operator.infix(param1, param2);
    }
    @Override
    public String postfix() {
        if (operator.isSingleOperator()){
            return operator.postfix(param1);
        }
        return operator.postfix(param1, param2);
    }

    @Override
    public String hardcode(String spacing) {
        String hardcode = spacing+"new "+getClass().getSimpleName()+"(\""+operator.toString()+"\",\n"+
                param1.hardcode(spacing+"        ");
        if (param2 != null) hardcode += ",\n"+param2.hardcode(spacing+"        ");
        return hardcode+")";
    }

    @Override public Expression getDerivative(){
        if (operator.isSingleOperator()){
            return operator.getDerivative(param1);
        }
        return operator.getDerivative(param1, param2);
    }
    @Override public Expression getIntegral(){
        if (operator.isSingleOperator()){
            return operator.getIntegral(param1);
        }
        return operator.getIntegral(param1, param2);
    }

    @Override public Expression simplify(){
        if (operator.isSingleOperator()){
            return operator.simplify(param1);
        }
        return operator.simplify(param1, param2);
    }

    public Operator getOperator(){    return operator;  }
    public Expression getParam1(){    return param1;  }
    public Expression getParam2(){    return param2;  }

}
