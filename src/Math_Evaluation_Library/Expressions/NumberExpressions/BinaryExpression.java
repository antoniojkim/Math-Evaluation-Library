package Math_Evaluation_Library.Expressions.NumberExpressions;

import Math_Evaluation_Library.Objects._Number_;

/**
 * Created by Antonio on 2017-11-23.
 */
public class BinaryExpression extends NumberExpression {

    String binaryString;

    public BinaryExpression(String binary){
        super(_Number_.fromBinary(binary));
    }
    public BinaryExpression(double value){
        super(value);
        binaryString = _Number_.toBinary((long)value);
    }
    public BinaryExpression(double value, int padding){
        super(value);
        binaryString = _Number_.toBinary((long)value, padding);
    }

    @Override
    public String infix() {
        return binaryString;
    }
    @Override
    public String postfix() {
        return binaryString;
    }

    @Override
    public String hardcode(String spacing) {
        return spacing+"new "+getClass().getSimpleName()+"("+_Number_.format(n)+")";
    }
}
