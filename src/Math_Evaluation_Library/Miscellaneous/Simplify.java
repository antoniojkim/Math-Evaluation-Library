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
                return new NumberExpression(s1.valueOf()+s2.valueOf());
            }
            //0+n
            if (s1.valueOf() == 0)  return s2;
        }
        else if (s2 instanceof NumberExpression){
            //n+0
            if (s2.valueOf() == 0)  return s1;
        }
        else if (s1 instanceof OperatorExpression && s2 instanceof OperatorExpression){
            OperatorExpression oe1 = (OperatorExpression) s1, oe2 = (OperatorExpression) s2;
            if (oe1.getOperator().toString().equals(oe2.getOperator().toString())){
                //sin²x + cos²x = 1
                if (oe1.getOperator().toString().equals("^") &&
                        oe1.getParam2().valueOf() == 2 && oe2.getParam2().valueOf() == 2 &&
                        oe1.getParam1() instanceof UnaryExpression && oe2.getParam1() instanceof UnaryExpression) {
                    UnaryExpression ue1 = (UnaryExpression) oe1.getParam1(), ue2 = (UnaryExpression) oe2.getParam1();
                    if (((ue1.getFunction().getFunction().equals("sin") && ue2.getFunction().getFunction().equals("cos")) ||
                            (ue1.getFunction().getFunction().equals("cos") && ue2.getFunction().getFunction().equals("sin"))) &&
                            ue1.getParam().equals(ue2.getParam())) {
                        return new NumberExpression(1);
                    }
                }
                //m*x+n*x = (m+n)*x
                else if (oe1.getOperator().toString().equals("*")){
                    if (oe1.getParam1().equals(oe2.getParam1())){
                        return simplifyMultiplication(simplifyAddition(oe1.getParam2(), oe2.getParam2()), oe1.getParam1());
                    }
                    if (oe1.getParam1().equals(oe2.getParam2())){
                        return simplifyMultiplication(simplifyAddition(oe1.getParam2(), oe2.getParam1()), oe1.getParam1());
                    }
                    if (oe1.getParam2().equals(oe2.getParam1())){
                        return simplifyMultiplication(simplifyAddition(oe1.getParam1(), oe2.getParam2()), oe1.getParam2());
                    }
                    if (oe1.getParam2().equals(oe2.getParam2())){
                        return simplifyMultiplication(simplifyAddition(oe1.getParam1(), oe2.getParam1()), oe1.getParam2());
                    }
                }
            }
        }
        else if (s2 instanceof UnaryExpression){
            UnaryExpression ue = (UnaryExpression) s2;
            if (ue.getFunction().getFunction().equals("neg")){
                return simplifySubtraction(s1, ue.getParam());
            }
        }
        return new OperatorExpression(addition, s1, s2);
    }

    public static final Operator subtract = getOperator("-");
    public static final UnaryFunction neg = getUnaryFunction("neg");
    public static Expression simplifySubtraction(Expression e1, Expression e2){
        Expression s1 = e1.simplify(), s2 = e2.simplify();
        if (s1 instanceof NumberExpression){
            if (s2 instanceof NumberExpression){
                return new NumberExpression(s1.valueOf()-s2.valueOf());
            }
            if (s1.valueOf() == 0)  return neg.simplify(s2);
        }
        else if (s2 instanceof NumberExpression){
            if (s2.valueOf() == 0)  return s1;
        }
        else if (s1 instanceof OperatorExpression && s2 instanceof OperatorExpression){
            OperatorExpression oe1 = (OperatorExpression) s1, oe2 = (OperatorExpression) s2;
            //cos²x - sin²x = cos(2x)
            if (oe1.getOperator().toString().equals("^") && oe2.getOperator().toString().equals("^") &&
                    oe1.getParam2().valueOf() == 2 && oe2.getParam2().valueOf() == 2 &&
                    oe1.getParam1() instanceof UnaryExpression && oe2.getParam1() instanceof UnaryExpression) {
                UnaryExpression ue1 = (UnaryExpression) oe1.getParam1(), ue2 = (UnaryExpression) oe2.getParam1();
                if ((ue1.getFunction().getFunction().equals("cos") && ue2.getFunction().getFunction().equals("sin")) &&
                        ue1.getParam().equals(ue2.getParam())) {
                    return new UnaryExpression(ue1.getFunction(),
                            simplifyMultiplication(new NumberExpression(2), ue1.getParam()));
                }
            }
            //m*x-n*x = (m-n)*x
            else if (oe1.getOperator().toString().equals("*") && oe2.getOperator().toString().equals("*")){
                if (oe1.getParam1().equals(oe2.getParam1())){
                    return simplifyMultiplication(simplifySubtraction(oe1.getParam2(), oe2.getParam2()), oe1.getParam1());
                }
                if (oe1.getParam1().equals(oe2.getParam2())){
                    return simplifyMultiplication(simplifySubtraction(oe1.getParam2(), oe2.getParam1()), oe1.getParam1());
                }
                if (oe1.getParam2().equals(oe2.getParam1())){
                    return simplifyMultiplication(simplifySubtraction(oe1.getParam1(), oe2.getParam2()), oe1.getParam2());
                }
                if (oe1.getParam2().equals(oe2.getParam2())){
                    return simplifyMultiplication(simplifySubtraction(oe1.getParam1(), oe2.getParam1()), oe1.getParam2());
                }
            }
        }
        return new OperatorExpression(subtract, s1, s2);
    }

    public static final Operator multiply = getOperator("*");
    public static Expression simplifyMultiplication(Expression e1, Expression e2){
        Expression s1 = e1.simplify(), s2 = e2.simplify();
        if (s1.equals(s2)){
            return new OperatorExpression("^", s1, new NumberExpression(2));
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
                if (oe.getParam1() instanceof NumberExpression && !(oe.getParam1() instanceof ConstantExpression)){
                    return new OperatorExpression(oe.getOperator(),
                            new NumberExpression(s1.valueOf()*oe.getParam1().valueOf()),
                            oe.getParam2());
                }
                else if (oe.getOperator().toString().equals("/") && oe.getParam2() instanceof NumberExpression &&
                        (s1.valueOf()/oe.getParam2().valueOf())%1 == 0){
                    return simplifyMultiplication(new NumberExpression(s1.valueOf()/oe.getParam2().valueOf()),
                            oe.getParam1());
                }
            }
            else if (s2 instanceof UnaryExpression){
                UnaryExpression ue = (UnaryExpression) s2;
                if (ue.getFunction().getFunction().equals("neg")){
                    return simplifyMultiplication(new NumberExpression(-s1.valueOf()), ue.getParam());
                }
            }
        }
        else if (s2 instanceof NumberExpression){
            if (s2.valueOf() == 0)  return new NumberExpression(0);
            if (s2.valueOf() == 1)  return s1;
            if (s2.valueOf() == -1) return neg.simplify(s1);
            if (s1 instanceof OperatorExpression){
                OperatorExpression oe = (OperatorExpression) s1;
                if (oe.getParam1() instanceof NumberExpression && !(oe.getParam1() instanceof ConstantExpression)){
                    return new OperatorExpression(oe.getOperator(),
                            new NumberExpression(s2.valueOf()*oe.getParam1().valueOf()),
                            oe.getParam2());
                }
                else if (oe.getOperator().toString().equals("/") && oe.getParam2() instanceof NumberExpression &&
                        (s1.valueOf()/oe.getParam2().valueOf())%1 == 0){
                    return simplifyMultiplication(new NumberExpression(s2.valueOf()/oe.getParam2().valueOf()),
                            oe.getParam1());
                }
            }
            return new OperatorExpression(multiply, s2, s1);
        }
        else if (s1 instanceof OperatorExpression && s2 instanceof OperatorExpression){
            OperatorExpression oe1 = (OperatorExpression) s1, oe2 = (OperatorExpression) s2;
            if (oe1.getOperator().toString().equals("^") && oe2.getOperator().toString().equals("^")){
                //x^m*x^n = x^(m+n)
                if (oe1.getParam1().equals(oe2.getParam1())){
                    return simplifyExponent(oe1.getParam1(), simplifyAddition(oe1.getParam2(), oe2.getParam2()));
                }
                //m^x*n^x = (m*n)^x
                if (oe1.getParam2().equals(oe2.getParam2())){
                    return simplifyExponent(simplifyMultiplication(oe1.getParam1(), oe2.getParam1()), oe1.getParam2());
                }
            }
        }
        else if (s1 instanceof UnaryExpression && s2 instanceof UnaryExpression){
            UnaryExpression ue1 = (UnaryExpression) s1;
            if (ue1.getFunction().getFunction().equals("neg")){
                return ue1.getFunction().simplify(simplifyMultiplication(ue1.getParam(), s2));
            }
            UnaryExpression ue2 = (UnaryExpression) s2;
            if (ue2.getFunction().getFunction().equals("neg")){
                return ue2.getFunction().simplify(simplifyMultiplication(ue2.getParam(), s1));
            }
        }
        return new OperatorExpression(multiply, s1, s2);
    }

    public static final Operator division = getOperator("/");
    public static Expression simplifyDivision(Expression e1, Expression e2){
        Expression s1 = e1.simplify(), s2 = e2.simplify();
        if (s1.equals(s2)){
            return new NumberExpression(1);
        }
        else if (s1 instanceof NumberExpression){
            if (s1.valueOf() == 0)  return new NumberExpression(0);
            if (s2 instanceof NumberExpression && (s1.valueOf()/s2.valueOf())%1 == 0){
                return new NumberExpression(s1.valueOf()/s2.valueOf());
            }
        }
        else if (s2 instanceof NumberExpression){
            if (s2.valueOf() == 1)  return s1;
            if (s1 instanceof OperatorExpression){
                OperatorExpression oe = (OperatorExpression) s1;
                if (oe.getParam1() instanceof NumberExpression){
                    return new OperatorExpression(oe.getOperator(),
                            new NumberExpression(oe.getParam1().valueOf()/s2.valueOf()),
                            oe.getParam2());
                }
            }
        }
        else if (s1 instanceof OperatorExpression && s2 instanceof OperatorExpression){
            OperatorExpression oe1 = (OperatorExpression) s1, oe2 = (OperatorExpression) s2;
            if (oe1.getOperator().toString().equals("^") && oe2.getOperator().toString().equals("^")){
                //x^m/x^n = x^(m-n)
                if (oe1.getParam1().equals(oe2.getParam1())){
                    return simplifyExponent(oe1.getParam1(), simplifySubtraction(oe1.getParam2(), oe2.getParam2()));
                }
                //m^x/n^x = (m/n)^x
                if (oe1.getParam2().equals(oe2.getParam2())){
                    return simplifyExponent(simplifyDivision(oe1.getParam1(), oe2.getParam1()), oe1.getParam2());
                }
            }
        }
        else if (s1 instanceof UnaryExpression && s2 instanceof UnaryExpression){
            UnaryExpression ue1 = (UnaryExpression) s1, ue2 = (UnaryExpression) s2;
            if (ue1.getParam().equals(ue2.getParam())) {
                if (ue1.getFunction().getFunction().equals("sin") && ue2.getFunction().getFunction().equals("cos")) {
                    return new UnaryExpression("tan", ue1.getParam());
                }
                if (ue1.getFunction().getFunction().equals("cos") && ue2.getFunction().getFunction().equals("sin")) {
                    return new UnaryExpression("cot", ue1.getParam());
                }
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
        else if (s1 instanceof NumberExpression && s1.valueOf() == 0){
            return new NumberExpression(0);
        }
        return new OperatorExpression(pow, s1, s2);
    }

    public static Expression simplify(String function){
        Expression e = toExpression(function);
        return e.simplify();
    }

}
