package Math_Evaluation_Library.Miscellaneous;

import Math_Evaluation_Library.ExpressionObjects.Operator;
import Math_Evaluation_Library.ExpressionObjects.UnaryFunction;
import Math_Evaluation_Library.Expressions.*;

import static Math_Evaluation_Library.Engine.Engine.toExpression;
import static Math_Evaluation_Library.ExpressionObjects.Operators.getOperator;
import static Math_Evaluation_Library.ExpressionObjects.UnaryFunctions.getUnaryFunction;

/**
 * Created by Antonio on 2017-07-11.
 */
public class Simplify {

    public static final Operator addition = getOperator("+");
    public static Expression simplifyAddition(Expression e1, Expression e2){
        Expression s1 = e1.simplify(), s2 = e2.simplify();
        if (s1 instanceof NumberExpression){
            //m+n   where m and n are numbers
            if (s2 instanceof NumberExpression){
                if (s1 instanceof ComplexExpression){
                    ComplexExpression c1 = (ComplexExpression) s1;
                    if (s2 instanceof ComplexExpression){
                        ComplexExpression c2 = (ComplexExpression) s2;
                        return new ComplexExpression(c1.getReal()+c2.getReal(), c1.getImaginary()+c2.getImaginary());
                    }
                    return new ComplexExpression(c1.getReal()+s2.valueOf(), c1.getImaginary());
                }
                if (s2 instanceof ComplexExpression){
                    ComplexExpression c = (ComplexExpression) s2;
                    return new ComplexExpression(s1.valueOf()+c.getReal(), c.getImaginary());
                }
                return new NumberExpression(s1.valueOf()+s2.valueOf());
            }
            //0+n
            if (s1.valueOf() == 0)  return s2;
        }
        else if (s2 instanceof NumberExpression){
            //n+0
            if (s2.valueOf() == 0)  return s1;
        }
        else if (s1 instanceof OperatorExpression){
            OperatorExpression oe1 = (OperatorExpression) s1;
            if (s2 instanceof OperatorExpression){
                OperatorExpression oe2 = (OperatorExpression) s2;
                if (oe1.equals(oe2.operator().toString())) {
                    //m*x+n*x = (m+n)*x
                    if (oe1.equals("*")) {
                        if (oe1.param1().equals(oe2.param1())) {
                            return simplifyMultiplication(simplifyAddition(oe1.param2(), oe2.param2()), oe1.param1());
                        }
                        if (oe1.param1().equals(oe2.param2())) {
                            return simplifyMultiplication(simplifyAddition(oe1.param2(), oe2.param1()), oe1.param1());
                        }
                        if (oe1.param2().equals(oe2.param1())) {
                            return simplifyMultiplication(simplifyAddition(oe1.param1(), oe2.param2()), oe1.param2());
                        }
                        if (oe1.param2().equals(oe2.param2())) {
                            return simplifyMultiplication(simplifyAddition(oe1.param1(), oe2.param1()), oe1.param2());
                        }
                    }
                    //sin²x + cos²x = 1
                    else if (oe1.equals("^") &&
                                oe1.param2().valueOf() == 2 && oe2.param2().valueOf() == 2 &&
                                oe1.param1() instanceof UnaryExpression && oe2.param1() instanceof UnaryExpression) {
                            UnaryExpression ue1 = (UnaryExpression) oe1.param1(), ue2 = (UnaryExpression) oe2.param1();
                            if (((ue1.equals("sin") && ue2.equals("cos")) || (ue1.equals("cos") && ue2.equals("sin"))) &&
                                    ue1.param().equals(ue2.param())) {
                                return new NumberExpression(1);
                            }
                        }
                }
            }
            //m*x+x = (m+1)*x
            else if (oe1.equals("*")) {
                if (oe1.param1().equals(s2)) {
                    return simplifyMultiplication(simplifyAddition(oe1.param2(), new NumberExpression(1)), s2);
                }
                if (oe1.param2().equals(s2)) {
                    return simplifyMultiplication(simplifyAddition(oe1.param1(), new NumberExpression(1)), s2);
                }
            }
        }
        //x+m*x = (m+1)*x
        else if (s2 instanceof OperatorExpression){
            OperatorExpression oe2 = (OperatorExpression) s2;
            if (oe2.equals("*")) {
                if (oe2.param1().equals(s1)) {
                    return simplifyMultiplication(simplifyAddition(oe2.param2(), new NumberExpression(1)), s1);
                }
                if (oe2.param2().equals(s1)) {
                    return simplifyMultiplication(simplifyAddition(oe2.param1(), new NumberExpression(1)), s1);
                }
            }
        }
        else if (s2 instanceof UnaryExpression){
            UnaryExpression ue = (UnaryExpression) s2;
            if (ue.equals("neg"))    return simplifySubtraction(s1, ue.param());
        }
        return new OperatorExpression(addition, s1, s2);
    }

    public static final Operator subtract = getOperator("-");
    public static final UnaryFunction neg = getUnaryFunction("neg");
    public static Expression simplifySubtraction(Expression e1, Expression e2){
        Expression s1 = e1.simplify(), s2 = e2.simplify();
        if (s1 instanceof NumberExpression){
            if (s2 instanceof NumberExpression){
                if (s1 instanceof ComplexExpression){
                    ComplexExpression c1 = (ComplexExpression) s1;
                    if (s2 instanceof ComplexExpression){
                        ComplexExpression c2 = (ComplexExpression) s2;
                        return new ComplexExpression(c1.getReal()-c2.getReal(), c1.getImaginary()-c2.getImaginary());
                    }
                    return new ComplexExpression(c1.getReal()-s2.valueOf(), c1.getImaginary());
                }
                if (s2 instanceof ComplexExpression){
                    ComplexExpression c = (ComplexExpression) s2;
                    return new ComplexExpression(s1.valueOf()-c.getReal(), -c.getImaginary());
                }
                return new NumberExpression(s1.valueOf()-s2.valueOf());
            }
            if (s1.valueOf() == 0)  return neg.simplify(s2);
        }
        else if (s2 instanceof NumberExpression){
            if (s2.valueOf() == 0)  return s1;
        }
        else if (s1 instanceof OperatorExpression){
            OperatorExpression oe1 = (OperatorExpression) s1;
            if (s2 instanceof OperatorExpression){
                OperatorExpression oe2 = (OperatorExpression) s2;
                if (oe1.equals(oe2.operator().toString())) {
                    //m*x-n*x = (m-n)*x
                    if (oe1.equals("*") && oe2.equals("*")) {
                        if (oe1.param1().equals(oe2.param1())) {
                            return simplifyMultiplication(simplifySubtraction(oe1.param2(), oe2.param2()), oe1.param1());
                        }
                        if (oe1.param1().equals(oe2.param2())) {
                            return simplifyMultiplication(simplifySubtraction(oe1.param2(), oe2.param1()), oe1.param1());
                        }
                        if (oe1.param2().equals(oe2.param1())) {
                            return simplifyMultiplication(simplifySubtraction(oe1.param1(), oe2.param2()), oe1.param2());
                        }
                        if (oe1.param2().equals(oe2.param2())) {
                            return simplifyMultiplication(simplifySubtraction(oe1.param1(), oe2.param1()), oe1.param2());
                        }
                    }
                    //cos²x - sin²x = cos(2x)
                    else if (oe1.equals("^") && oe2.equals("^") &&
                            oe1.param2().valueOf() == 2 && oe2.param2().valueOf() == 2 &&
                            oe1.param1() instanceof UnaryExpression && oe2.param1() instanceof UnaryExpression) {
                        UnaryExpression ue1 = (UnaryExpression) oe1.param1(), ue2 = (UnaryExpression) oe2.param1();
                        if ((ue1.equals("cos") && ue2.equals("sin")) && ue1.param().equals(ue2.param())) {
                            return new UnaryExpression(ue1.function(),
                                    simplifyMultiplication(new NumberExpression(2), ue1.param()));
                        }
                    }
                }
            }
            //m*x-x = (m-1)*x
            else if (oe1.equals("*")) {
                if (oe1.param1().equals(s2)) {
                    return simplifyMultiplication(simplifySubtraction(oe1.param2(), new NumberExpression(1)), s2);
                }
                if (oe1.param2().equals(s2)) {
                    return simplifyMultiplication(simplifySubtraction(oe1.param1(), new NumberExpression(1)), s2);
                }
            }
        }
        //x+m*x = (1-m)*x
        else if (s2 instanceof OperatorExpression){
            OperatorExpression oe2 = (OperatorExpression) s2;
            if (oe2.equals("*")) {
                if (oe2.param1().equals(s1)) {
                    return simplifyMultiplication(simplifySubtraction(new NumberExpression(1), oe2.param2()), s1);
                }
                if (oe2.param2().equals(s1)) {
                    return simplifyMultiplication(simplifySubtraction(new NumberExpression(1), oe2.param1()), s1);
                }
            }
        }
        return new OperatorExpression(subtract, s1, s2);
    }

    public static final Operator multiply = getOperator("*");
    public static Expression simplifyMultiplication(Expression e1, Expression e2){
        Expression s1 = e1.simplify(), s2 = e2.simplify();
        if (s1.equals(s2)){
            return simplifyExponent(s1, new NumberExpression(2));
        }
        else if (s1 instanceof NumberExpression){
            if (s2 instanceof NumberExpression){
                return new NumberExpression(s1.valueOf()*s2.valueOf());
            }
            if (s1.valueOf() == 0)  return new NumberExpression(0);
            if (s1.valueOf() == 1)  return s2;
            if (s1.valueOf() == -1) return neg.simplify(s2);
            if (s2 instanceof OperatorExpression){
                OperatorExpression oe = (OperatorExpression) s2;
                if (oe.param1() instanceof NumberExpression && !(oe.param1() instanceof ConstantExpression)){
                    return new OperatorExpression(oe.operator(),
                            new NumberExpression(s1.valueOf()*oe.param1().valueOf()),
                            oe.param2());
                }
                else if (oe.equals("/") && oe.param2() instanceof NumberExpression &&
                        (s1.valueOf()/oe.param2().valueOf())%1 == 0){
                    return simplifyMultiplication(new NumberExpression(s1.valueOf()/oe.param2().valueOf()),
                            oe.param1());
                }
            }
            else if (s2 instanceof UnaryExpression){
                UnaryExpression ue = (UnaryExpression) s2;
                if (ue.equals("neg")){
                    return simplifyMultiplication(new NumberExpression(-s1.valueOf()), ue.param());
                }
            }
        }
        else if (s2 instanceof NumberExpression){
            if (s2.valueOf() == 0)  return new NumberExpression(0);
            if (s2.valueOf() == 1)  return s1;
            if (s2.valueOf() == -1) return neg.simplify(s1);
            if (s1 instanceof OperatorExpression){
                OperatorExpression oe = (OperatorExpression) s1;
                if (oe.param1() instanceof NumberExpression && !(oe.param1() instanceof ConstantExpression)){
                    return new OperatorExpression(oe.operator(),
                            new NumberExpression(s2.valueOf()*oe.param1().valueOf()),
                            oe.param2());
                }
                else if (oe.operator().toString().equals("/") && oe.param2() instanceof NumberExpression &&
                        (s1.valueOf()/oe.param2().valueOf())%1 == 0){
                    return simplifyMultiplication(new NumberExpression(s2.valueOf()/oe.param2().valueOf()),
                            oe.param1());
                }
            }
            return new OperatorExpression(multiply, s2, s1);
        }
        else if (s1 instanceof OperatorExpression && s2 instanceof OperatorExpression){
            OperatorExpression oe1 = (OperatorExpression) s1, oe2 = (OperatorExpression) s2;
            if (oe1.operator().toString().equals("^") && oe2.operator().toString().equals("^")){
                //x^m*x^n = x^(m+n)
                if (oe1.param1().equals(oe2.param1())){
                    return simplifyExponent(oe1.param1(), simplifyAddition(oe1.param2(), oe2.param2()));
                }
                //m^x*n^x = (m*n)^x
                if (oe1.param2().equals(oe2.param2())){
                    return simplifyExponent(simplifyMultiplication(oe1.param1(), oe2.param1()), oe1.param2());
                }
            }
        }
        else if (s1 instanceof UnaryExpression && s2 instanceof UnaryExpression){
            UnaryExpression ue1 = (UnaryExpression) s1;
            if (ue1.equals("neg")){
                return neg.simplify(simplifyMultiplication(ue1.param(), s2));
            }
            UnaryExpression ue2 = (UnaryExpression) s2;
            if (ue2.equals("neg")){
                return neg.simplify(simplifyMultiplication(ue2.param(), s1));
            }
        }
        else if (s2 instanceof VariableExpression){
            return new OperatorExpression(multiply, s2, s1);
        }
        return new OperatorExpression(multiply, s1, s2);
    }

    public static final Operator division = getOperator("/");
    public static Expression simplifyDivision(Expression e1, Expression e2){
        Expression s1 = e1.simplify(), s2 = e2.simplify();
        if (s1.equals(s2))      return new NumberExpression(1);
        if (s1 instanceof NumberExpression){
            if (s1.valueOf() == 0)  return new NumberExpression(0);
            if (s2 instanceof NumberExpression && (s1.valueOf()/s2.valueOf())%1 == 0){
                return new NumberExpression(s1.valueOf()/s2.valueOf());
            }
        }
        if (s2 instanceof NumberExpression){
            if (s2.valueOf() == 1)  return s1;
            if (s1 instanceof OperatorExpression){
                OperatorExpression oe = (OperatorExpression) s1;
                if (oe.param1() instanceof NumberExpression){
                    return new OperatorExpression(oe.operator(),
                            new NumberExpression(oe.param1().valueOf()/s2.valueOf()),
                            oe.param2());
                }
            }
        }
        if (s1 instanceof OperatorExpression && s2 instanceof OperatorExpression){
            OperatorExpression oe1 = (OperatorExpression) s1, oe2 = (OperatorExpression) s2;
            if (oe1.operator().toString().equals("^") && oe2.operator().toString().equals("^")){
                //x^m/x^n = x^(m-n)
                if (oe1.param1().equals(oe2.param1())){
                    return simplifyExponent(oe1.param1(), simplifySubtraction(oe1.param2(), oe2.param2()));
                }
                //m^x/n^x = (m/n)^x
                if (oe1.param2().equals(oe2.param2())){
                    return simplifyExponent(simplifyDivision(oe1.param1(), oe2.param1()), oe1.param2());
                }
            }
        }
        if (s2 instanceof UnaryExpression){
            UnaryExpression ue2 = (UnaryExpression) s2;
            if (s1 instanceof UnaryExpression) {
                UnaryExpression ue1 = (UnaryExpression) s1;
                if (ue1.param().equals(ue2.param())) {
                    if (ue1.function().toString().equals("sin") && ue2.function().toString().equals("cos")) {
                        return new UnaryExpression("tan", ue1.param());
                    }
                    if (ue1.function().toString().equals("cos") && ue2.function().toString().equals("sin")) {
                        return new UnaryExpression("cot", ue1.param());
                    }
                }
            }
            switch(ue2.function().toString()){
                case "sin": return simplifyMultiplication(s1, new UnaryExpression("csc", ue2.param()));
                case "cos": return simplifyMultiplication(s1, new UnaryExpression("sec", ue2.param()));
                case "tan": return simplifyMultiplication(s1, new UnaryExpression("cot", ue2.param()));
                case "csc": return simplifyMultiplication(s1, new UnaryExpression("sin", ue2.param()));
                case "sec": return simplifyMultiplication(s1, new UnaryExpression("cos", ue2.param()));
                case "cot": return simplifyMultiplication(s1, new UnaryExpression("tan", ue2.param()));
                default:    break;
            }
        }
        return new OperatorExpression(division, s1, s2);
    }

    public static final Operator pow = getOperator("^");
    public static final Operator sqrt = getOperator("√");
    public static Expression simplifyExponent(Expression e1, Expression e2){
        Expression s1 = e1.simplify(), s2 = e2.simplify();
        if (s2 instanceof NumberExpression){
            if (s2.valueOf() == 0)      return new NumberExpression(1);
            if (s2.valueOf() == 1)      return s1;
            if (s2.valueOf() == 0.5)    return new OperatorExpression(sqrt, s1);
            if (s1 instanceof NumberExpression){
                double n = Math.pow(s1.valueOf(), s2.valueOf());
                if (n%1 == 0) {
                    return new NumberExpression(Math.pow(s1.valueOf(), s2.valueOf()));
                }
            }
        }
        if (s1 instanceof NumberExpression && s1.valueOf() == 0){
            return new NumberExpression(0);
        }
        return new OperatorExpression(pow, s1, s2);
    }

    public static Expression simplify(String function){
        Expression e = toExpression(function);
        return e.simplify();
    }

}
