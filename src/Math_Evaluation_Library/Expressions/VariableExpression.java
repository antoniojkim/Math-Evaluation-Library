package Math_Evaluation_Library.Expressions;

import Math_Evaluation_Library.Engine.Engine;

import java.util.ArrayList;
import java.util.List;

import static Math_Evaluation_Library.ExpressionObjects.Operators.getOperator;

/**
 * Created by Antonio on 2017-11-23.
 */
public class VariableExpression extends Expression {

    String var;
    Expression val = null;
    boolean set = false;

    public VariableExpression(){   var = Engine.var; }
    public VariableExpression(String var){
        this.var = var;
    }
    public VariableExpression(char var){
        this.var = String.valueOf(var);
    }
    public VariableExpression(String var, Expression e){
        this.var = var;
        set = true;
        val = e;
    }
    public VariableExpression(char var, Expression e){
        this(String.valueOf(var), e);
    }

    @Override
    public Expression evaluate() {
        return set ? val : new InvalidExpression("Unset Variable Error: "+var);
    }

    @Override
    public void set(String var, Expression val) {
        if (this.var.equals(var)){
            this.val = val;
            set = true;
        }
    }

    @Override
    public void unset() {
        set = false;
        if (val != null)    val.unset();
    }

    @Override
    public void unset(String var) {
        if (set && this.var.equals(var)){
            set = false;
            val.unset(var);
        }
    }

    @Override
    public boolean containsVar(String var) {
        return this.var.equals(var) || (set && val.containsVar(var));
    }

    @Override public boolean isValid() {    return !set || val.isValid();    }

    @Override
    public boolean equals(Expression e) {
        if (e instanceof VariableExpression){
            VariableExpression ve = (VariableExpression) e;
            if (var.equals(ve.getName())) {
                if (set && ve.isSet()) {
                    return val.equals(ve.getVal());
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Double> getNumbers() {
        if (set)    return val.getNumbers();
        return new ArrayList<>();
    }

    public String getName(){ return var; }

    @Override
    public String infix() {
        return set ? val.infix() : var;
    }

    @Override
    public String postfix() {
        return set ? val.postfix() : var;
    }

    @Override
    public String hardcode(String spacing) {
        if (set){
            return spacing+"new "+getClass().getSimpleName()+"(\""+var+"\",\n" +
                    val.hardcode(spacing+"        ")+")";
        }
        return spacing+"new "+getClass().getSimpleName()+"(\""+var+"\")";
    }

    @Override
    public Expression getDerivative(){
        if (set){
            return val.getDerivative();
        }
        else if (var.equals(Engine.var)){
            return new NumberExpression(1);
        }
        return super.getDerivative();
    }
    @Override
    public Expression getIntegral(){
        if (set){
            return val.getIntegral();
        }
        else if (var.equals(Engine.var)){
            return new OperatorExpression(getOperator("*"),
                    new NumberExpression(0.5),
                    new OperatorExpression(getOperator("^"),
                            new VariableExpression(Engine.var),
                            new NumberExpression(2)));
        }
        return super.getIntegral();
    }

    public boolean isSet(){ return set; }
    public Expression getVal(){     return val;    }
}
