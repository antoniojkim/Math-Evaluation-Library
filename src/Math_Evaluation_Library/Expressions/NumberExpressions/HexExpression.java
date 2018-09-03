package Math_Evaluation_Library.Expressions.NumberExpressions;

import Math_Evaluation_Library.Expressions.Expression;
import Math_Evaluation_Library.Objects._Number_;

import static java.lang.Double.NaN;

/**
 * Created by Antonio on 2017-11-23.
 */
public class HexExpression extends NumberExpression {

    String hexString;

    public HexExpression(String hex){
        super(_Number_.fromHex(hex));
    }
    public HexExpression(double value){
        super(value);
        hexString = Long.toHexString((long)value);
    }

    @Override
    public String infix() {
        return hexString;
    }
    @Override
    public String postfix() {
        return hexString;
    }

    @Override
    public String hardcode(String spacing) {
        return spacing+"new "+getClass().getSimpleName()+"("+_Number_.format(n)+")";
    }
}
