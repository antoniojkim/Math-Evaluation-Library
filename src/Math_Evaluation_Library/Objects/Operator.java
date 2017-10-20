package Math_Evaluation_Library.Objects;

/**
 * Created by Antonio on 2017-10-14.
 */
public class Operator extends MathObject {

    private int precedence = 1;
    private boolean singleOperator = false;
    private boolean associable = false;
    private String description = null;

    public Operator (String operator, int precedence, boolean singleOperator, boolean associability){
        this.function = operator; // for the purposes of this class, function represents the operator
        this.precedence = precedence;
        this.singleOperator = singleOperator;
        this.associable = associability;
    }
    public Operator (String operator, int precedence, boolean singleOperator, boolean associability, String description){
        this(operator, precedence, singleOperator, associability);
        this.description = description;
    }

    public int getPrecedence() {
        return precedence;
    }

    public boolean isSingleOperator() {
        return singleOperator;
    }

    public boolean isAssociable() {
        return associable;
    }

    public String evaluate(double x){ return ""; }
    public String evaluate(double x, double y){ return ""; }
    public String evaluate(String x, String y){ return ""; }

}
