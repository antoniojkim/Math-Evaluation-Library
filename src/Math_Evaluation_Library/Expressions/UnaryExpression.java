package Math_Evaluation_Library.Expressions;

import Math_Evaluation_Library.ExpressionObjects.UnaryFunction;
import Math_Evaluation_Library.ExpressionObjects.UnaryFunctions;

import java.util.List;

/**
 * Created by Antonio on 2017-11-23.
 */
public class UnaryExpression extends Expression {

    UnaryFunction function;
    Expression param;

    public UnaryExpression(String function, Expression e){
        this(UnaryFunctions.getUnaryFunction(function), e);
    }

    public UnaryExpression(UnaryFunction function, Expression e){
        this.function = function;
        this.param = e;
    }

    @Override
    public Expression evaluate(){
        Expression x = param.evaluate();
        if (x instanceof InvalidExpression) return x;
        return function.evaluate(x);
    }

    @Override public void set(String var, Expression val) {   param.set(var, val);    }
    @Override public void unset() {   param.unset();  }
    @Override public void unset(String var) {     param.unset(var);   }
    @Override public boolean containsVar(String var) {    return param.containsVar(var);  }

    @Override public boolean isValid() {    return param.isValid();     }

    @Override
    public boolean equals(Expression e) {
        if (e instanceof UnaryExpression){
            UnaryExpression ue = (UnaryExpression) e;
            if (function.getFunction().equals(ue.getFunction().getFunction())){
                return param.equals(ue.getParam());
            }
        }
        return false;
    }

    @Override
    public List<Double> getNumbers() {
        return param.getNumbers();
    }

    @Override public String infix() {   return function.infix(param);   }
    @Override public String postfix() { return function.postfix(param); }
    @Override
    public String hardcode(String spacing) {
        return spacing+"new "+getClass().getSimpleName()+"(getUnaryFunction(\""+function.getFunction()+"\"),\n" +
                param.hardcode(spacing+"        ")+")";
    }

    @Override public Expression getDerivative(){    return function.getDerivative(param);   }
    @Override public Expression getIntegral(){      return function.getIntegral(param);     }
    @Override public Expression simplify(){         return function.simplify(param);        }

    public UnaryFunction getFunction(){     return function;    }
    public Expression getParam(){     return param;    }
}
