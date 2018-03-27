package Math_Evaluation_Library.Expressions;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Double.NaN;

/**
 * Created by Antonio on 2017-12-07.
 */
public class StringExpression extends Expression {

    String str = "";

    public StringExpression(String str){
        this.str = str;
    }

    @Override public double valueOf() { return NaN; }
    @Override public Expression evaluate() {    return this;    }

    @Override public boolean isValid() {    return false;    }

    @Override
    public boolean equals(Expression e) {
        return e instanceof StringExpression && str.equals(e.toString());
    }

    @Override
    public List<Double> getNumbers() {
        return new ArrayList<>();
    }

    @Override public String infix() {   return str;     }
    @Override public String postfix() { return str;     }
    @Override public String toTeX() {   return str;     }
    @Override public String hardcode(String spacing) { return spacing+"new "+getClass().getSimpleName()+"(\""+str+"\")"; }
}
