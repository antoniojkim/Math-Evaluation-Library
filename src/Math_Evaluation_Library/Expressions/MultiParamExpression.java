package Math_Evaluation_Library.Expressions;

import Math_Evaluation_Library.ExpressionObjects.MultiParamFunction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Antonio on 2017-11-23.
 */
public class MultiParamExpression extends Expression {

    MultiParamFunction function;
    Expression[] parameters;

    public MultiParamExpression(MultiParamFunction function, String... parameters){
        this.function = function;
        this.parameters = function.convert(parameters);
    }
    public MultiParamExpression(MultiParamFunction function, Expression[] parameters){
        this.function = function;
        this.parameters = parameters;
    }

    @Override
    public Expression evaluate() {
        if (function.isFlexible() || function.getNumParameters() == parameters.length) {
            return function.evaluate(parameters);
        }
        return new InvalidExpression("Invalid Number of Arguments for "+function.getFunction()+
                ":  E: "+function.getNumParameters()+"  A:"+parameters.length);
    }

    @Override
    public void set(String var, Expression val) {
        for (Expression e : parameters){
            e.set(var, val);
        }
    }

    @Override
    public void unset() {
        for (Expression e : parameters){
            e.unset();
        }
    }

    @Override
    public void unset(String var) {
        for (Expression e : parameters){
            e.unset(var);
        }
    }

    @Override
    public boolean containsVar(String var) {
        for (Expression e : parameters){
            if (e.containsVar(var)) return true;
        }
        return false;
    }

    @Override
    public boolean isValid() {
        for (Expression e : parameters){
            if (!e.isValid()) return false;
        }
        return true;
    }

    @Override
    public boolean equals(Expression e) {
        if (e instanceof MultiParamExpression){
            MultiParamExpression mpe = (MultiParamExpression) e;
            if (function.getFunction().equals(mpe.getFunction().getFunction()) &&
                    parameters.length == mpe.getParameters().length){
                for (int i = 0; i<parameters.length; ++i){
                    if (!parameters[i].equals(mpe.getParameters()[i]))  return false;
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Double> getNumbers() {
        List<Double> list = new ArrayList<>();
        for (Expression e : parameters){
            list.addAll(e.getNumbers());
        }
        return list;
    }

    @Override
    public String infix() {
        StringBuilder infix = new StringBuilder(function.getFunction());
        infix.append("(");
        boolean first = true;
        for (Expression e : parameters){
            if (first){
                first = false;
            }
            else{
                infix.append(", ");
            }
            infix.append(e.infix());
        }
        infix.append(")");
        return infix.toString();
    }

    @Override
    public String postfix() {
        StringBuilder postfix = new StringBuilder(function.getFunction());
        for (Expression e : parameters){
            postfix.append(" ").append(e.infix());
        }
        return postfix.toString();
    }

    @Override
    public String hardcode(String spacing) {
        String hardcode = spacing+"new "+getClass().getSimpleName()+"(getMultiParamFunction(\""+function.getFunction()+"\")";
        for (Expression e : parameters){
            hardcode += ",\n"+e.hardcode(spacing+"        ");
        }
        return hardcode+")";
    }

    @Override
    public Expression simplify(){
        Expression[] simplified = new Expression[parameters.length];
        for (int i = 0; i<parameters.length; ++i){
            simplified[i] = parameters[i].simplify();
        }
        return new MultiParamExpression(function, simplified);
    }

    public MultiParamFunction getFunction(){    return function;    }
    public Expression[] getParameters(){    return parameters;    }
}