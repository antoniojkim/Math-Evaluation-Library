package Math_Evaluation_Library.ExpressionObjects;

import Math_Evaluation_Library.Expressions.Expression;
import Math_Evaluation_Library.Expressions.InvalidExpression;
import Math_Evaluation_Library.Expressions.OperatorExpression;
import javafx.beans.binding.NumberExpression;

/**
 * Created by Antonio on 2017-10-14.
 */
public abstract class Operator {

    private int precedence = 1;
    private boolean singleOperator = false;
    private boolean associable = false;
    protected String operator = "";
    //private String description = "";

    public Operator (String operator, int precedence, boolean singleOperator, boolean associability){
        this.operator = operator; // for the purposes of this class, function represents the operator
        this.precedence = precedence;
        this.singleOperator = singleOperator;
        this.associable = associability;
    }
//    public Operator (String operator, int precedence, boolean singleOperator, boolean associability, String description){
//        this(operator, precedence, singleOperator, associability);
//        this.description = description;
//    }

    public int getPrecedence() {
        return precedence;
    }
    public boolean isSingleOperator() {
        return singleOperator;
    }
    public boolean isAssociable() {
        return associable;
    }

    public Expression evaluate(Expression x){
        return new InvalidExpression("Undefined Operator Error:  "+operator);
    }
    public Expression evaluate(Expression x, Expression y){
        return new InvalidExpression("Undefined Operator Error:  "+operator);
    }

    public String infix(Expression e1){
        return associable ? e1.infix()+operator : operator+e1.infix();
    }
    public String infix(Expression e1, Expression e2){
        return e1.infix()+operator+e2.infix();
    }

    public String postfix(Expression e1){
        return e1.postfix()+" "+operator;
    }
    public String postfix(Expression e1, Expression e2){
        return e1.postfix()+" "+e2.postfix()+" "+operator;
    }

    public String toTeX(Expression e1){
        return associable ? e1.toTeX()+operator : operator+e1.toTeX();
    }
    public String toTeX(Expression e1, Expression e2){
        return e1.toTeX()+operator+e2.toTeX();
    }

    public Expression getDerivative(Expression e1){
        return new InvalidExpression("("+(associable ? e1.infix()+operator : operator+e1.infix())+")'");
    }
    public Expression getDerivative(Expression e1, Expression e2){
        return new InvalidExpression("("+e1.infix()+operator+e2.infix()+")'");
    }
    public Expression getIntegral(Expression e1){
        return new InvalidExpression("(ʃ("+(associable ? e1.infix()+operator : operator+e1.infix())+"))");
    }
    public Expression getIntegral(Expression e1, Expression e2){
        return new InvalidExpression("(ʃ("+e1.infix()+operator+e2.infix()+"))");
    }
    public Expression simplify(Expression e1){
        return new OperatorExpression(this, e1);
    }
    public Expression simplify(Expression e1, Expression e2){
        Expression s1 = e1.simplify(), s2 = e2.simplify();
        if (s1 instanceof NumberExpression && s2 instanceof NumberExpression){
            return evaluate(s1, s2);
        }
        return new OperatorExpression(this, e1, e2);
    }

    public String toString() {       return operator;        }
    //public String getDescription() {    return description;     }


}
