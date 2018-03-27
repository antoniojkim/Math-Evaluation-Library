package Math_Evaluation_Library.ExpressionObjects;

import Math_Evaluation_Library.Calculus.Derivative;
import Math_Evaluation_Library.Calculus.Integral;
import Math_Evaluation_Library.Constants.Scripts;
import Math_Evaluation_Library.Expressions.*;
import Math_Evaluation_Library.LinearAlgebra._Matrix_;
import Math_Evaluation_Library.LinearAlgebra._Vector_;
import Math_Evaluation_Library.Miscellaneous.Simplify;
import Math_Evaluation_Library.Objects._Number_;
import Math_Evaluation_Library.Statistics.Combinatorics;

import java.util.HashMap;

import static Math_Evaluation_Library.ExpressionObjects.UnaryFunctions.getUnaryFunction;

/**
 * Created by Antonio on 2017-10-15.
 */
public class Operators {

//    public static void main(String[] args){
//        for (Operator operator : operators){
//            String param = "\""+operator.getVar()+"\", "+operator.getPrecedence()+", "+operator.isSingleOperator()+", "+operator.isAssociable();
//            System.out.println("operatorHashMap.put(\""+operator.getVar()+"\", new Operator("+param+"));");
//        }
//    }

    public static HashMap<String, String> infixBracketOperatorsL = new HashMap<>();
    public static HashMap<String, String> infixBracketOperatorsR = new HashMap<>();
    public static HashMap<String, String> infixBracketOperatorsLdiv = new HashMap<>();
    public static HashMap<String, String> infixBracketOperatorsRdiv = new HashMap<>();
    public static HashMap<String, Operator> operatorHashMap = createMap();

    public static HashMap<String, Operator> createMap(){
        infixBracketOperatorsL.put("+", "+");
        infixBracketOperatorsL.put("-", "-");
        infixBracketOperatorsL.put("/", "/");
        infixBracketOperatorsR.put("+", "+");
        infixBracketOperatorsR.put("-", "-");
        infixBracketOperatorsLdiv.put("+", "+");
        infixBracketOperatorsLdiv.put("-", "-");
        infixBracketOperatorsLdiv.put("/", "/");
        infixBracketOperatorsLdiv.put("%", "%");
        infixBracketOperatorsRdiv.put("+", "+");
        infixBracketOperatorsRdiv.put("-", "-");
        infixBracketOperatorsRdiv.put("*", "*");
        infixBracketOperatorsRdiv.put("·", "·");
        infixBracketOperatorsRdiv.put("/", "/");
        infixBracketOperatorsRdiv.put("%", "%");

        HashMap<String, Operator> operatorHashMap = new HashMap<>();
        operatorHashMap.put("!", new Operator("!", 4, true, true){
            @Override
            public Expression evaluate(Expression x) {
                return new NumberExpression(Combinatorics.fact(x.valueOf()));
            }
        });
        operatorHashMap.put("%", new Operator("%", 3, false, true){
            @Override public Expression evaluate(Expression x, Expression y) {
                return new NumberExpression(x.valueOf() % y.valueOf());
            }
        });
        operatorHashMap.put("&", new Operator("&", 3, false, true){
            @Override public Expression evaluate(Expression e1, Expression e2) {
                double x = e1.valueOf(), y = e2.valueOf();
                if (x%1 == 0 && y%1 == 0) {
                    return new NumberExpression(((int)x) & ((int)y));
                }
                return new InvalidExpression("Invalid Argument Error:  & expected integer arguments");
            }
            @Override public String toTeX(Expression e1, Expression e2){
                return e1.toTeX()+"\\& "+e2.toTeX();
            }
        });
        operatorHashMap.put("*", new Operator("*", 3, false, true){
            @Override public Expression evaluate(Expression x, Expression y) {
                boolean xIsMatrix = x instanceof MatrixExpression;
                boolean yIsMatrix = y instanceof MatrixExpression;
                if (xIsMatrix){
                    if (yIsMatrix){
                        MatrixExpression me1 = (MatrixExpression) x, me2 = (MatrixExpression) y;
                        if (me1.doubleMatrix().rows == me2.doubleMatrix().columns){
                            return new MatrixExpression(me1.doubleMatrix().mmul(me2.doubleMatrix()));
                        }
                        else if (me1.doubleMatrix().rows == me2.doubleMatrix().rows &&
                                me1.doubleMatrix().columns == me2.doubleMatrix().columns){
                            if (me1.doubleMatrix().rows > 1 && me1.doubleMatrix().columns == 1){
                                if (me1.doubleMatrix().rows == 3){
                                    return new MatrixExpression(
                                            _Matrix_.toStrMatrix(_Vector_.crossProduct(me1.doubleMatrix().toArray(), me2.doubleMatrix().toArray()))
                                    );
                                }
                                return new NumberExpression(me1.doubleMatrix().dot(me2.doubleMatrix()));
                            }
                            else if (me1.doubleMatrix().rows == 1 && me1.doubleMatrix().columns > 1){
                                if (me1.doubleMatrix().columns == 3) {
                                    return new MatrixExpression(
                                            _Matrix_.toStrMatrix(_Vector_.crossProduct(me1.doubleMatrix().toArray(), me2.doubleMatrix().toArray()))
                                    );
                                }
                                return new NumberExpression(me1.doubleMatrix().dot(me2.doubleMatrix()));
                            }
                            else {
                                return new MatrixExpression(me1.doubleMatrix().mul(me2.doubleMatrix()));
                            }
                        }
                        return new InvalidExpression("Invalid Matrix Operation:  "+me1+"*"+me2);
                    }
                    return new MatrixExpression(
                            ((MatrixExpression)x).doubleMatrix().mmul(y.valueOf()));
                }
                else if (yIsMatrix){
                    return new MatrixExpression(
                            ((MatrixExpression)y).doubleMatrix().mmul(x.valueOf()));
                }
                else if (y instanceof ComplexExpression){
                    ComplexExpression c2 = (ComplexExpression) y;
                    if (x instanceof ComplexExpression){
                        ComplexExpression c1 = (ComplexExpression) x;
                        return new ComplexExpression(c1.getReal()*c2.getReal()-c1.getImaginary()*c2.getImaginary(),
                                c1.getReal()*c2.getImaginary()+c1.getImaginary()*c2.getReal());
                    }
                    double n = x.valueOf();
                    return new ComplexExpression(n*c2.getReal(), n*c2.getImaginary());
                }
                else if (x instanceof ComplexExpression){
                    ComplexExpression c = (ComplexExpression) x;
                    double n = y.valueOf();
                    return new ComplexExpression(c.getReal()*n, c.getImaginary()*n);
                }
                return new NumberExpression(x.valueOf()*y.valueOf());
            }
            @Override public String infix(Expression e1, Expression e2){
                return multiplyInfix("*", e1, e2);
            }
            @Override public String toTeX(Expression e1, Expression e2){
                return multiplyTeX("\\times ", e1, e2);
            }
            @Override public Expression getDerivative(Expression f, Expression g){
                Expression fprime = f.calculateDerivative(), gprime = g.calculateDerivative();
                return new OperatorExpression(Operators.getOperator("+"),
                        new OperatorExpression(Operators.getOperator("*"), f, gprime),
                        new OperatorExpression(Operators.getOperator("*"), g, fprime));
            }
            @Override public Expression getIntegral(Expression f, Expression g){
                if (f instanceof NumberExpression){
                    return new OperatorExpression(Operators.getOperator("*"),
                            f, g.calculateIntegral());
                }
                if (g instanceof NumberExpression){
                    return new OperatorExpression(Operators.getOperator("*"),
                            g, f.calculateIntegral());
                }
                return super.getIntegral(f, g);
            }
            @Override public Expression simplify(Expression e1, Expression e2){
                return Simplify.simplifyMultiplication(e1, e2);
            }
        });
        operatorHashMap.put("+", new Operator("+", 2, false, true){
            @Override public Expression evaluate(Expression x, Expression y) {
                boolean xIsMatrix = x instanceof MatrixExpression;
                boolean yIsMatrix = y instanceof MatrixExpression;
                if (xIsMatrix){
                    if (yIsMatrix){
                        return new MatrixExpression(
                                ((MatrixExpression)x).doubleMatrix().add
                                (((MatrixExpression)y).doubleMatrix()));
                    }
                    return new MatrixExpression(
                            ((MatrixExpression)x).doubleMatrix().add(y.valueOf()));
                }
                else if (yIsMatrix){
                    return new MatrixExpression(
                            ((MatrixExpression)y).doubleMatrix().addi(x.valueOf()));
                }
                if (y instanceof ComplexExpression){
                    ComplexExpression c2 = (ComplexExpression) y;
                    if (x instanceof ComplexExpression){
                        ComplexExpression c1 = (ComplexExpression) x;
                        return new ComplexExpression(c1.getReal()+c2.getReal(), c1.getImaginary()+c2.getImaginary());
                    }
                    return new ComplexExpression(x.valueOf()+c2.getReal(), c2.getImaginary());
                }
                else if (x instanceof ComplexExpression){
                    ComplexExpression c = (ComplexExpression) x;
                    return new ComplexExpression(c.getReal()+y.valueOf(), c.getImaginary());
                }
                return new NumberExpression(x.valueOf()+y.valueOf());
            }
            @Override public Expression getDerivative(Expression x, Expression y){
                return new OperatorExpression(this, x.calculateDerivative(), y.calculateDerivative());
            }
            @Override public Expression getIntegral(Expression x, Expression y){
                return new OperatorExpression(this, x.calculateIntegral(), y.calculateIntegral());
            }
            @Override public Expression simplify(Expression e1, Expression e2){
                return Simplify.simplifyAddition(e1, e2);
            }
        });
        operatorHashMap.put("-", new Operator("-", 2, false, true){
            @Override public Expression evaluate(Expression x, Expression y) {
                boolean xIsMatrix = x instanceof MatrixExpression;
                boolean yIsMatrix = y instanceof MatrixExpression;
                if (xIsMatrix){
                    if (yIsMatrix){
                        return new MatrixExpression(
                                ((MatrixExpression)x).doubleMatrix().sub
                                        (((MatrixExpression)y).doubleMatrix()));
                    }
                    return new MatrixExpression(
                            ((MatrixExpression)x).doubleMatrix().sub(y.valueOf()));
                }
                else if (yIsMatrix){
                    return new MatrixExpression(
                            ((MatrixExpression)y).doubleMatrix().rsub(x.valueOf()));
                }
                else if (y instanceof ComplexExpression){
                    ComplexExpression c2 = (ComplexExpression) y;
                    if (x instanceof ComplexExpression){
                        ComplexExpression c1 = (ComplexExpression) x;
                        return new ComplexExpression(c1.getReal()-c2.getReal(), c1.getImaginary()-c2.getImaginary());
                    }
                    return new ComplexExpression(x.valueOf()-c2.getReal(), -c2.getImaginary());
                }
                else if (x instanceof ComplexExpression){
                    ComplexExpression c = (ComplexExpression) x;
                    return new ComplexExpression(c.getReal()-y.valueOf(), c.getImaginary());
                }
                return new NumberExpression(x.valueOf()-y.valueOf());
            }
            @Override public Expression getDerivative(Expression x, Expression y){
                return new OperatorExpression(this, x.calculateDerivative(), y.calculateDerivative());
            }
            @Override public Expression getIntegral(Expression x, Expression y){
                return new OperatorExpression(this, x.calculateIntegral(), y.calculateIntegral());
            }
            @Override public Expression simplify(Expression e1, Expression e2){
                return Simplify.simplifySubtraction(e1, e2);
            }
        });
        operatorHashMap.put("/", new Operator("/", 3, false, true){
            @Override public Expression evaluate(Expression x, Expression y) {
                boolean xIsMatrix = x instanceof MatrixExpression;
                boolean yIsMatrix = y instanceof MatrixExpression;
                if (xIsMatrix){
                    if (yIsMatrix){
                        return new MatrixExpression(
                                ((MatrixExpression)x).doubleMatrix().div
                                        (((MatrixExpression)y).doubleMatrix()));
                    }
                    return new MatrixExpression(
                            ((MatrixExpression)x).doubleMatrix().div(y.valueOf()));
                }
                else if (yIsMatrix){
                    return new MatrixExpression(
                            ((MatrixExpression)y).doubleMatrix().rdiv(x.valueOf()));
                }
                else if (y instanceof ComplexExpression){
                    ComplexExpression c2 = (ComplexExpression) y;
                    if (x instanceof ComplexExpression){
                        ComplexExpression c1 = (ComplexExpression) x;
                        double c2d2 = Math.pow(c2.getReal(), 2)+Math.pow(c2.getImaginary(), 2);
                        return new ComplexExpression(
                                (c1.getReal()*c2.getReal()+c1.getImaginary()*c2.getImaginary())/c2d2,
                                (c1.getImaginary()*c2.getReal()-c1.getReal()*c2.getImaginary())/c2d2);
                    }
                    double n = x.valueOf();
                    double c2d2 = Math.pow(c2.getReal(), 2)+Math.pow(c2.getImaginary(), 2);
                    return new ComplexExpression(
                            (n*c2.getReal())/c2d2,
                            (-n*c2.getImaginary())/c2d2);
                }
                else if (x instanceof ComplexExpression){
                    ComplexExpression c = (ComplexExpression) x;
                    double n = y.valueOf();
                    return new ComplexExpression(c.getReal()/n, c.getImaginary()/n);
                }
                return new NumberExpression(x.valueOf()/y.valueOf());
            }
            @Override public String infix(Expression e1, Expression e2){
                String e1infix = e1.infix(), e2infix = e2.infix();
                if (e1 instanceof OperatorExpression && infixBracketOperatorsLdiv.containsKey(((OperatorExpression) e1).operator().toString())){
                    e1infix = "("+e1infix+")";
                }
                if (e2 instanceof OperatorExpression && infixBracketOperatorsRdiv.containsKey(((OperatorExpression) e2).operator().toString())){
                    e2infix = "("+e2infix+")";
                }
                return e1infix+operator+e2infix;
            }
            @Override public String toTeX(Expression e1, Expression e2){
                return "\\dfrac{"+e1.toTeX()+"}{"+e2.toTeX()+"}";
            }
            @Override public Expression getDerivative(Expression f, Expression g){
                Expression fprime = f.calculateDerivative(), gprime = g.calculateDerivative();
                return new OperatorExpression(Operators.getOperator("/"),
                        new OperatorExpression(Operators.getOperator("-"),
                                new OperatorExpression(Operators.getOperator("*"), g, fprime),
                                new OperatorExpression(Operators.getOperator("*"), f, gprime)),
                        new OperatorExpression(Operators.getOperator("^"),
                                g, new NumberExpression(2)));
            }
            @Override public Expression getIntegral(Expression f, Expression g){
                if (g instanceof NumberExpression){
                    return new OperatorExpression(Operators.getOperator("/"),
                            f.calculateIntegral(), g);
                }
                if (f instanceof NumberExpression && g instanceof VariableExpression){
                    return new OperatorExpression(Operators.getOperator("*"), f,
                            new UnaryExpression(getUnaryFunction("ln"),
                                    new UnaryExpression(getUnaryFunction("abs"), g)));
                }
                return super.getIntegral(f, g);
            }
            @Override public Expression simplify(Expression e1, Expression e2){
                return Simplify.simplifyDivision(e1, e2);
            }
        });
        operatorHashMap.put("C", new Operator("C", 4, false, true){
            @Override public Expression evaluate(Expression e1, Expression e2) {
                double x = e1.valueOf(), y = e2.valueOf();
                if (x % 1 == 0 && y % 1 == 0)
                    return new NumberExpression(Combinatorics.choose((int)x, (int)y));
                return new InvalidExpression("Invalid Argument Error:  C expected integer arguments");
            }
            @Override public String toTeX(Expression e1, Expression e2){
                return "\\binom{"+e1.toTeX()+"}{"+e2.toTeX()+"}";
            }
        });
        operatorHashMap.put("P", new Operator("P", 4, false, true){
            @Override public Expression evaluate(Expression e1, Expression e2) {
                double x = e1.valueOf(), y = e2.valueOf();
                if (x % 1 == 0 && y % 1 == 0)
                    return new NumberExpression(Combinatorics.permute((int)x, (int)y));
                return new InvalidExpression("Invalid Argument Error:  P expected integer arguments");
            }
            @Override public String toTeX(Expression e1, Expression e2){
                return "{}_{"+e1.toTeX()+"}P_{"+e2.toTeX()+"}";
            }
        });
        operatorHashMap.put("^", new Operator("^", 4, false, false){
            @Override public Expression evaluate(Expression e1, Expression e2) {
                double x = e1.valueOf(), y = e2.valueOf();
                return new NumberExpression((e1 instanceof VariableExpression && e1.toString().equals("e")) ?
                        Math.exp(y) : Math.pow(x, y));
            }
            @Override public String infix(Expression e1, Expression e2){
                String e1infix = e1.infix();
                if (e1 instanceof OperatorExpression && infixBracketOperatorsRdiv.containsKey(((OperatorExpression) e1).operator().toString())){
                    e1infix = "("+e1infix+")";
                }
                if (e2 instanceof NumberExpression){
                    String superscript = Scripts.toSuperScript(_Number_.format(e2.valueOf()));
                    if (e1 instanceof UnaryExpression){
                        UnaryExpression ue = (UnaryExpression) e1;
                        if (!((UnaryExpression) e1).function().toString().equals("abs")) {
                            if (ue.param() instanceof NumberExpression && ue.param().valueOf() > 0) {
                                return ue.function().toString() + superscript + ue.param().infix();
                            }
                            return ue.function().toString() + superscript + "(" + ue.param().infix() + ")";
                        }
                    }
                    return e1infix+superscript;
                }
                String e2infix = e2.infix();
                if (e2 instanceof OperatorExpression && infixBracketOperatorsRdiv.containsKey(((OperatorExpression) e2).operator().toString())){
                    e2infix = "("+e2infix+")";
                }
                return e1infix+operator+e2infix;
            }
            @Override public String toTeX(Expression e1, Expression e2){
                return "{"+e1.toTeX()+"}^{"+e2.toTeX()+"}";
            }
            @Override public Expression getDerivative(Expression x, Expression y){
                Expression e = Derivative.exponentDerivative(x, y);
                if (e.isValid())    return e;
                return super.getDerivative(x, y);
            }
            @Override public Expression getIntegral(Expression x, Expression y){
                if (x instanceof VariableExpression && y instanceof NumberExpression && y.valueOf() != -1){
                    return new OperatorExpression(Operators.getOperator("/"),
                            new OperatorExpression(Operators.getOperator("^"), x,
                                    new NumberExpression(y.valueOf()+1)),
                            new NumberExpression(y.valueOf()+1));
                }
                else if (x instanceof ConstantExpression && ((ConstantExpression) x).getConstant() == 'ℯ' &&
                        y instanceof VariableExpression){
                    return new OperatorExpression(Operators.getOperator("^"), x, y);
                }
                else if (x instanceof UnaryExpression && y instanceof NumberExpression){
                    if (y.valueOf() == 2){
                        UnaryExpression ue = (UnaryExpression) x;
                        if (ue.function().toString().equals("sec") && ue.param() instanceof VariableExpression){
                            return new UnaryExpression(getUnaryFunction("tan"), ue.param());
                        }
                    }
                }
                return super.getIntegral(x, y);
            }
            @Override public Expression simplify(Expression e1, Expression e2){
                return Simplify.simplifyExponent(e1, e2);
            }
        });
        operatorHashMap.put("~", new Operator("~", 4, true, false){
            @Override public Expression evaluate(Expression e1) {
                double x = e1.valueOf();
                if (x%1 == 0) {
                    return new NumberExpression(~((int)x));
                }
                return new InvalidExpression("Invalid Argument Error:  ~ expects integer argument");
            }
            @Override public String toTeX(Expression e){
                return "\\sim "+e.toTeX();
            }
        });
        operatorHashMap.put("°", new Operator("°", 4, true, false){
            @Override public Expression evaluate(Expression x) {
                return new NumberExpression(Math.toRadians(x.valueOf()));
            }
        });
        operatorHashMap.put("·", new Operator("·", 3, false, true){
            @Override public Expression evaluate(Expression x, Expression y) {
                boolean xIsMatrix = x instanceof MatrixExpression;
                boolean yIsMatrix = y instanceof MatrixExpression;
                if (xIsMatrix){
                    if (yIsMatrix){
                        return new NumberExpression(
                                ((MatrixExpression)x).doubleMatrix().dot
                                        (((MatrixExpression)y).doubleMatrix()));
                    }
                    return new MatrixExpression(
                            ((MatrixExpression)x).doubleMatrix().mmul(y.valueOf()));
                }
                else if (yIsMatrix){
                    return new MatrixExpression(
                            ((MatrixExpression)y).doubleMatrix().mmul(x.valueOf()));
                }
                else if (y instanceof ComplexExpression){
                    ComplexExpression c2 = (ComplexExpression) y;
                    if (x instanceof ComplexExpression){
                        ComplexExpression c1 = (ComplexExpression) x;
                        return new ComplexExpression(c1.getReal()*c2.getReal()-c1.getImaginary()*c2.getImaginary(),
                                c1.getReal()*c2.getImaginary()+c1.getImaginary()*c2.getReal());
                    }
                    double n = x.valueOf();
                    return new ComplexExpression(n*c2.getReal(), n*c2.getImaginary());
                }
                else if (x instanceof ComplexExpression){
                    ComplexExpression c = (ComplexExpression) x;
                    double n = y.valueOf();
                    return new ComplexExpression(c.getReal()*n, c.getImaginary()*n);
                }
                return new NumberExpression(x.valueOf()*y.valueOf());
            }
            @Override public String infix(Expression e1, Expression e2){
                return multiplyInfix("·", e1, e2);
            }
            @Override public String toTeX(Expression e1, Expression e2){
                return multiplyTeX("\\cdot ", e1, e2);
            }
            @Override public Expression getDerivative(Expression f, Expression g){
                Expression fprime = f.calculateDerivative(), gprime = g.calculateDerivative();
                return new OperatorExpression("+",
                        new OperatorExpression(this, f, gprime),
                        new OperatorExpression(this, g, fprime));
            }
            @Override public Expression getIntegral(Expression f, Expression g){
                if (f instanceof NumberExpression){
                    return new OperatorExpression(this,
                            f, g.calculateIntegral());
                }
                if (g instanceof NumberExpression){
                    return new OperatorExpression(this,
                            g, f.calculateIntegral());
                }
                return super.getIntegral(f, g);
            }
            @Override public Expression simplify(Expression e1, Expression e2){
                return Simplify.simplifyMultiplication(e1, e2);
            }
        });
        operatorHashMap.put("ʳ", new Operator("ʳ", 4, true, false){
            @Override public Expression evaluate(Expression x) {
                return new NumberExpression(Math.toDegrees(x.valueOf()));
            }
        });
        operatorHashMap.put("√", new Operator("√", 4, true, false){
            @Override public Expression evaluate(Expression x) {
                return new NumberExpression(Math.sqrt(x.valueOf()));
            }
            @Override public String infix(Expression e){
                if (!(e instanceof NumberExpression || e instanceof VariableExpression)){
                    return operator+"("+e.infix()+")";
                }
                return super.infix(e);
            }
            @Override public String toTeX(Expression e){
                return "\\sqrt{"+e.toTeX()+"}";
            }
            @Override public Expression getDerivative(Expression x){
                return Derivative.sqrtDerivative(x);
            }
            @Override public Expression getIntegral(Expression x){
                Expression e = Integral.sqrtIntegral(x);
                if (e.isValid())    return e;
                return super.getIntegral(x);
            }
            @Override public Expression simplify(Expression e1){
                return Simplify.simplifyExponent(e1, new NumberExpression(0.5));
            }
        });
        operatorHashMap.put("∛", new Operator("∛", 4, true, false){
            @Override public Expression evaluate(Expression x) {
                return new NumberExpression(Math.cbrt(x.valueOf()));
            }
            @Override public String toTeX(Expression e){
                return "\\sqrt[3]{"+e.toTeX()+"}";
            }
        });
        operatorHashMap.put("∨", new Operator("∨", 3, false, true){
            @Override public Expression evaluate(Expression e1, Expression e2) {
                double x = e1.valueOf(), y = e2.valueOf();
                if (x%1 == 0 && y%1 == 0) {
                    return new NumberExpression(((int)x) | ((int)y));
                }
                return new InvalidExpression("Invalid Argument Error:  ∨ expected integer arguments");
            }
            @Override public String toTeX(Expression e1, Expression e2){
                return e1.toTeX()+"\\lor "+e2.toTeX();
            }
        });
        operatorHashMap.put("≪", new Operator("≪", 3, false, true){
            @Override public Expression evaluate(Expression e1, Expression e2) {
                double x = e1.valueOf(), y = e2.valueOf();
                if (x%1 == 0 && y%1 == 0) {
                    return new NumberExpression(((int)x) << ((int)y));
                }
                return new InvalidExpression("Invalid Argument Error:  ≪ expected integer arguments");
            }
            @Override public String toTeX(Expression e1, Expression e2){
                return e1.toTeX()+"\\ll "+e2.toTeX();
            }
        });
        operatorHashMap.put("≫", new Operator("≫", 3, false, true){
            @Override public Expression evaluate(Expression e1, Expression e2) {
                double x = e1.valueOf(), y = e2.valueOf();
                if (x%1 == 0 && y%1 == 0) {
                    return new NumberExpression(((int)x) >> ((int)y));
                }
                return new InvalidExpression("Invalid Argument Error:  ≫ expected integer arguments");
            }
            @Override public String toTeX(Expression e1, Expression e2){
                return e1.toTeX()+"\\ll "+e2.toTeX();
            }
        });
        operatorHashMap.put("⊕", new Operator("⊕", 3, false, true){
            @Override public Expression evaluate(Expression e1, Expression e2) {
                double x = e1.valueOf(), y = e2.valueOf();
                if (x%1 == 0 && y%1 == 0) {
                    return new NumberExpression(((int)x) ^ ((int)y));
                }
                return new InvalidExpression("Invalid Argument Error:  ⊕ expected integer arguments");
            }
            @Override public String toTeX(Expression e1, Expression e2){
                return e1.toTeX()+"\\oplus "+e2.toTeX();
            }
        });
        return operatorHashMap;
    }

    private static String multiplyInfix(String operator, Expression e1, Expression e2){
        String e1infix = e1.infix(), e2infix = e2.infix();
        if (e2 instanceof OperatorExpression){
            OperatorExpression oe2 = (OperatorExpression) e2;
            boolean b2 = infixBracketOperatorsR.containsKey(oe2.operator().toString());
            if (b2) e2infix = "(" + e2infix + ")";
            if (e1 instanceof OperatorExpression){
                OperatorExpression oe1 = (OperatorExpression) e1;
                boolean b1 = infixBracketOperatorsL.containsKey(oe1.operator().toString());
                if (b1) {
                    if (b2) return "("+e1infix+")"+e2infix;
                    else    e1infix = "(" + e1infix + ")";
                }
                else if (oe1.param2() instanceof NumberExpression && oe2.param1() instanceof NumberExpression){
                    return e1infix+operator+e2infix;
                }
            }
            else if (oe2.param1() instanceof NumberExpression && !(oe2.param1() instanceof ConstantExpression) &&
                    (e1 instanceof NumberExpression || !e1.equals("abs"))){
                return e1infix+operator+e2infix;
            }
        }
        else if (e1 instanceof OperatorExpression){
            OperatorExpression oe1 = (OperatorExpression) e1;
            if (infixBracketOperatorsL.containsKey(oe1.operator().toString()))
                e1infix = "(" + e1infix + ")";
        }
        else if (e2.equals("neg") ||
                (e2 instanceof NumberExpression &&
                        (e1 instanceof NumberExpression || e1.equals("neg")))){
            return e1infix+operator+e2infix;
        }
        return e1infix+e2infix;
    }
    private static String multiplyTeX(String operator, Expression e1, Expression e2){
        String e1TeX = e1.toTeX(), e2TeX = e2.toTeX();
        if (e2 instanceof OperatorExpression){
            OperatorExpression oe2 = (OperatorExpression) e2;
            boolean b2 = infixBracketOperatorsR.containsKey(oe2.operator().toString());
            if (b2) e2TeX = "\\left(" + e2TeX + "\\right)";
            if (e1 instanceof OperatorExpression){
                OperatorExpression oe1 = (OperatorExpression) e1;
                boolean b1 = infixBracketOperatorsL.containsKey(oe1.operator().toString());
                if (b1) {
                    if (b2) return "\\left("+e1TeX+"\\right)"+e2TeX;
                    else    e1TeX = "\\left(" + e1TeX + "\\right)";
                }
                else if (oe1.param2() instanceof NumberExpression && oe2.param1() instanceof NumberExpression){
                    return e1TeX+operator+e2TeX;
                }
            }
            else if (oe2.param1() instanceof NumberExpression && !(oe2.param1() instanceof ConstantExpression) &&
                    (e1 instanceof NumberExpression || !e1.equals("abs"))){
                return e1TeX+operator+e2TeX;
            }
        }
        else if (e1 instanceof OperatorExpression){
            OperatorExpression oe1 = (OperatorExpression) e1;
            if (infixBracketOperatorsL.containsKey(oe1.operator().toString()))
                e1TeX = "\\left(" + e1TeX + "\\right)";
        }
        else if (e2.equals("neg") ||
                (e2 instanceof NumberExpression &&
                        (e1 instanceof NumberExpression || e1.equals("neg")))){
            return e1TeX+operator+e2TeX;
        }
        return e1TeX+e2TeX;
    }

//    public static void main(String[] args){
////        Sort.quicksort(operators);
//        char[] array = {'^', '°', '·', 'ʳ', '√', '≪', '≫', '!', '%', '*', '+', '-', '/', 'C', 'P'};
//        String[] array2 = {"°", "·", "ʳ", "√", "≪", "≫", "!", "%", "*", "+", "-", "/", "C", "P", "^"};
//        Sort.quicksort(array);
//        Sort.quicksort(array2);
//        for (int i = 0; i<array.length; i++){
//            System.out.print(String.evaluate(array[i])+", ");
//        }
//        System.out.println();
//        for (int i = 0; i<array2.length; i++){
//            System.out.print(array2[i]+", ");
//        }
//        System.out.println();
//    }

//    public static final Operator[] operators = {
//            new Operator("!", 4, true, true){
//                @Override
//                public Expression evaluate(Expression x) {
//                    return _Number_.format(Combinatorics.fact(x.valueOf()));
//                }
//            },
//            new Operator("%", 3, false, true){
//                @Override
//                public Expression evaluate(Expression x, Expression y) {
//                    if (y == Double.POSITIVE_INFINITY || y == Double.NEGATIVE_INFINITY){
//                        if (x == Double.POSITIVE_INFINITY || x == Double.NEGATIVE_INFINITY)    return INVALID;
//                        return "0";
//                    }
//                    else if (x == Double.POSITIVE_INFINITY){    return "∞";     }
//                    else if (x == Double.NEGATIVE_INFINITY){    return "-∞";    }
//                    return Search.replace(_Number_.format(x%y), "Infinity", "∞");
//                }
//            },
//            new Operator("&", 3, false, true){
//                @Override
//                public Expression evaluate(Expression x, Expression y) {
//                    if (x%1 == 0 && y%1 == 0) {
//                        return _Number_.format((int)x & (int)y);
//                    }
//                    return INVALID;
//                }
//            },
//            new Operator("*", 3, false, true){
//                @Override
//                public Expression evaluate(Expression x, Expression y) {
//                    return Search.replace(_Number_.format(x*y), "Infinity", "∞");
//                }
//            },
//            new Operator("+", 2, false, true){
//                @Override
//                public Expression evaluate(Expression x, Expression y) {
//                    return Search.replace(_Number_.format(x+y), "Infinity", "∞");
//                }
//            },
//            new Operator("-", 2, false, true){
//                @Override
//                public Expression evaluate(Expression x, Expression y) {
//                    return Search.replace(_Number_.format(x-y), "Infinity", "∞");
//                }
//            },
//            new Operator("/", 3, false, true){
//                @Override
//                public Expression evaluate(Expression x, Expression y) {
//                    if (y == Double.POSITIVE_INFINITY || y == Double.NEGATIVE_INFINITY){
//                        if (x == Double.POSITIVE_INFINITY || x == Double.NEGATIVE_INFINITY)    return INVALID;
//                        return "0";
//                    }
//                    else if (y == 0){
//                        if (x == 0)   return INVALID;
//                        return (x > 0 ? "∞" : "-∞");
//                    }
//                    else if (x == Double.POSITIVE_INFINITY){    return "∞";     }
//                    else if (x == Double.NEGATIVE_INFINITY){    return "-∞";    }
//                    return Search.replace(_Number_.format(x/y), "Infinity", "∞");
//                }
//            },
//            new Operator("C", 4, false, true){
//                @Override
//                public Expression evaluate(Expression x, Expression y) {
//                    if (x % 1 == 0 && y % 1 == 0) {
//                        int n = (int) x;
//                        int r = (int) y;
//                        if (r > n) {
//                            return INVALID;
//                        }
//                        return _Number_.format(Combinatorics.choose(n, r));
//                    }
//                    return INVALID;
//                }
//            },
//            new Operator("P", 4, false, true){
//                @Override
//                public Expression evaluate(Expression x, Expression y) {
//                    if (x % 1 == 0 && y % 1 == 0) {
//                        int n = (int) x;
//                        int r = (int) y;
//                        if (r > n) {
//                            return INVALID;
//                        }
//                        return _Number_.format(Combinatorics.permute(n, r));
//                    }
//                    return INVALID;
//                }
//            },
//            new Operator("^", 4, false, false){
//                @Override
//                public Expression evaluate(Expression x, Expression y) {
//                    if (x == Math.E){
//                        return Search.replace(_Number_.format(Math.exp(y)), "Infinity", "∞");
//                    }
//                    return Search.replace(_Number_.format(Math.pow(x, y)), "Infinity", "∞");
//                }
//            },
//            new Operator("~", 4, true, false){
//                @Override
//                public Expression evaluate(Expression x) {
//                    if (x%1 == 0) {
//                        return _Number_.format(~((int)x));
//                    }
//                    return INVALID;
//                }
//            },
//            new Operator("°", 4, true, false){
//                @Override
//                public Expression evaluate(Expression x) {
//                    return _Number_.format(Math.toRadians(x.valueOf()));
//                }
//            },
//            new Operator("·", 3, false, true){
//                @Override
//                public Expression evaluate(Expression x, Expression y) {
//                    return Search.replace(_Number_.format(x*y), "Infinity", "∞");
//                }
//            },
//            new Operator("ʳ", 4, true, false){
//                @Override
//                public Expression evaluate(Expression x) {
//                    return _Number_.format(Math.toDegrees(x.valueOf()));
//                }
//            },
//            new Operator("√", 4, true, false){
//                @Override
//                public Expression evaluate(Expression x) {
//                    return _Number_.format(Math.sqrt(x.valueOf()));
//                }
//            },
//            new Operator("∛", 4, true, false){
//                @Override
//                public Expression evaluate(Expression x) {
//                    return _Number_.format(Math.cbrt(x.valueOf()));
//                }
//            },
//            new Operator("∨", 3, false, true){
//                @Override
//                public Expression evaluate(Expression x, Expression y) {
//                    if (x%1 == 0 && y%1 == 0) {
//                        return _Number_.format((int)x | (int)y);
//                    }
//                    return INVALID;
//                }
//            },
//            new Operator("≪", 3, false, true){
//                @Override
//                public Expression evaluate(Expression x, Expression y) {
//                    if (x%1 == 0 && y%1 == 0) {
//                        return _Number_.format((int)x << (int)y);
//                    }
//                    return INVALID;
//                }
//            },
//            new Operator("≫", 3, false, true){
//                @Override
//                public Expression evaluate(Expression x, Expression y) {
//                    if (x%1 == 0 && y%1 == 0) {
//                        return _Number_.format((int)x >> (int)y);
//                    }
//                    return INVALID;
//                }
//            },
//            new Operator("⊻", 3, false, true){
//                @Override
//                public Expression evaluate(Expression x, Expression y) {
//                    if (x%1 == 0 && y%1 == 0) {
//                        return _Number_.format((int)x ^ (int)y);
//                    }
//                    return INVALID;
//                }
//            }
//    };

    public static Operator getOperator(String item){
//        int i = operatorIndex(item);
//        return i != -1 ? operators[i] : null;
        return operatorHashMap.get(item);
    }
    public static Operator getOperator(char item){
//        int i = operatorIndex(item);
//        return i != -1 ? operators[i] : null;
        return operatorHashMap.get(String.valueOf(item));
    }
//    public static int operatorIndex(String item){
//        return (item.length() == 1 ? Search.binarySearch(operators, item) : -1);
//    }
//    public static int operatorIndex(char item){
//        return Search.binarySearch(operators, String.evaluate(item));
//    }
    public static boolean isOperator(String item){
//        return (item.length() == 1 ? Search.contains(operators, item) : false);
        return operatorHashMap.containsKey(item);
    }
    public static boolean isOperator(char item){
//        return Search.contains(operators, String.evaluate(item));
        return operatorHashMap.containsKey(String.valueOf(item));
    }

}
