package Math_Evaluation_Library.Expressions;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Double.NaN;

/**
 * Created by Antonio on 2017-11-23.
 */
public class InvalidExpression extends Expression {

    public InvalidExpression(String error){
        setError(error);
    }

    @Override
    public double valueOf() {
        return NaN;
    }
    @Override
    public Expression evaluate() {
        return this;
    }

    @Override
    public boolean containsVar(String var) {
        return false;
    }

    @Override public boolean isValid() {    return false;   }
    @Override
    public boolean equals(Expression e) {
        return e instanceof InvalidExpression && error.equals(((InvalidExpression) e).getError());
    }

    @Override
    public List<Double> getNumbers() {
        return new ArrayList<>();
    }

    @Override public String infix() { return error; }
    @Override public String postfix() { return error; }
    @Override public String hardcode(String spacing) { return spacing+"new "+getClass().getSimpleName()+"(\""+error+"\")"; }


    String error = "";

    public String getError(){   return error;   }
    public void setError(String message){   error = message;   }
    public boolean hasError(){   return error.length()>0;   }
    public void clearError(){   error = "";   }
}
