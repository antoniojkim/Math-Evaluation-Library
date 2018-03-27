package Math_Evaluation_Library.Calculus;

import Math_Evaluation_Library.ExpressionObjects.Operators;
import Math_Evaluation_Library.Expressions.*;
import Math_Evaluation_Library.Miscellaneous.MathRound;

import static Math_Evaluation_Library.Engine.Engine.toExpression;
import static Math_Evaluation_Library.ExpressionObjects.UnaryFunctions.getUnaryFunction;

/**
 * Created by Antonio on 2017-07-13.
 */
public class Derivative {

    public static double value(String function, double num){
        Expression derivative = calculate(function);
        if (derivative.isValid()){
            return derivative.valueAt(num);
        }
        return nDeriv(function, num);
    }

    public static double nDeriv(String function, double num){
        Expression f = toExpression(function);
        double h = 7.5050268766E-6;                 //0.00007505021;0.000001;//
        double nderiv = MathRound.round((f.valueAt(num+h)-f.valueAt(num-h))/(2*h), 9);
        return nderiv;
    }

    public static Expression calculate(String function){
        return calculate(toExpression(function));
    }
    public static Expression calculate(Expression f){
        //String simplified = Simplify.simplify(function);
        if (f.isValid()) {
            Expression derivative = f.calculateDerivative();
            return derivative.simplify();
        }
        return new InvalidExpression("("+f.infix()+")'");
    }

    public static Expression exponentDerivative(Expression x, Expression y){
        if (y instanceof NumberExpression){
            return new OperatorExpression("*", y,
                    new OperatorExpression("*", x.calculateDerivative(),
                            new OperatorExpression("^", x, new NumberExpression(y.valueOf()-1))));
        }
        else if (x instanceof ConstantExpression && ((ConstantExpression) x).getConstant() == 'ℯ'){
            return new OperatorExpression("*",
                    new OperatorExpression("^", x, y),
                    y.calculateDerivative());
        }
        else if (x instanceof NumberExpression){
            return new OperatorExpression(Operators.getOperator("*"),
                    new OperatorExpression(Operators.getOperator("*"),
                            new OperatorExpression(Operators.getOperator("^"), x, y),
                            new UnaryExpression(getUnaryFunction("ln"), new NumberExpression(2))),
                    y.calculateDerivative());
        }
        return new InvalidExpression("");
    }
    public static Expression sqrtDerivative(Expression x){
        return new OperatorExpression("/",
                x.calculateDerivative(),
                new OperatorExpression("*",
                        new NumberExpression(2),
                        new OperatorExpression("√", x)));
    }

}
