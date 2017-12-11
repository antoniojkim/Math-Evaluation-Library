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
//            String param = "\""+operator.getName()+"\", "+operator.getPrecedence()+", "+operator.isSingleOperator()+", "+operator.isAssociable();
//            System.out.println("operatorHashMap.put(\""+operator.getName()+"\", new Operator("+param+"));");
//        }
//    }

    public static HashMap<String, String> infixBracketOperators = new HashMap<>();
    public static HashMap<String, String> infixBracketOperatorsMul = new HashMap<>();
    public static HashMap<String, String> infixBracketOperatorsDiv = new HashMap<>();
    public static HashMap<String, Operator> operatorHashMap = createMap();

    public static HashMap<String, Operator> createMap(){
        infixBracketOperators.put("+", "+");
        infixBracketOperators.put("-", "-");
        infixBracketOperators.put("/", "/");
        infixBracketOperatorsMul.put("+", "+");
        infixBracketOperatorsMul.put("-", "-");
        infixBracketOperatorsMul.put("*", "*");
        infixBracketOperatorsMul.put("/", "/");
        infixBracketOperatorsMul.put("^", "^");
        infixBracketOperatorsMul.put("√", "√");
        infixBracketOperatorsDiv.put("+", "+");
        infixBracketOperatorsDiv.put("-", "-");
        infixBracketOperatorsDiv.put("*", "*");
        infixBracketOperatorsDiv.put("·", "·");
        infixBracketOperatorsDiv.put("/", "/");
        infixBracketOperatorsDiv.put("%", "%");

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
            @Override
            public Expression evaluate(Expression e1, Expression e2) {
                double x = e1.valueOf(), y = e2.valueOf();
                if (x%1 == 0 && y%1 == 0) {
                    return new NumberExpression(((int)x) & ((int)y));
                }
                return new InvalidExpression("Invalid Argument Error:  & expected integer arguments");
            }
        });
        operatorHashMap.put("*", new Operator("*", 3, false, true){
            @Override public Expression evaluate(Expression x, Expression y) {
                boolean xIsMatrix = x instanceof MatrixExpression;
                boolean yIsMatrix = y instanceof MatrixExpression;
                if (xIsMatrix){
                    if (yIsMatrix){
                        MatrixExpression me1 = (MatrixExpression) x, me2 = (MatrixExpression) y;
                        if (me1.getMatrix().rows == me2.getMatrix().columns){
                            return new MatrixExpression(me1.getMatrix().mmul(me2.getMatrix()));
                        }
                        else if (me1.getMatrix().rows == me2.getMatrix().rows &&
                                me1.getMatrix().columns == me2.getMatrix().columns){
                            if (me1.getMatrix().rows > 1 && me1.getMatrix().columns == 1){
                                if (me1.getMatrix().rows == 3){
                                    return new MatrixExpression(
                                            _Matrix_.toStrMatrix(_Vector_.crossProduct(me1.getMatrix().toArray(), me2.getMatrix().toArray()))
                                    );
                                }
                                return new NumberExpression(me1.getMatrix().dot(me2.getMatrix()));
                            }
                            else if (me1.getMatrix().rows == 1 && me1.getMatrix().columns > 1){
                                if (me1.getMatrix().columns == 3) {
                                    return new MatrixExpression(
                                            _Matrix_.toStrMatrix(_Vector_.crossProduct(me1.getMatrix().toArray(), me2.getMatrix().toArray()))
                                    );
                                }
                                return new NumberExpression(me1.getMatrix().dot(me2.getMatrix()));
                            }
                            else {
                                return new MatrixExpression(me1.getMatrix().mul(me2.getMatrix()));
                            }
                        }
                        return new InvalidExpression("Invalid Matrix Operation:  "+me1.getStrMatrix()+"*"+me2.getStrMatrix());
                    }
                    return new MatrixExpression(
                            ((MatrixExpression)x).getMatrix().mmul(y.valueOf()));
                }
                else if (yIsMatrix){
                    return new MatrixExpression(
                            ((MatrixExpression)y).getMatrix().mmul(x.valueOf()));
                }
                return new NumberExpression(x.valueOf()*y.valueOf());
            }
            @Override
            public String infix(Expression e1, Expression e2){
                String e1infix = e1.infix(), e2infix = e2.infix();
                if (e2 instanceof OperatorExpression){
                    OperatorExpression oe2 = (OperatorExpression) e2;
                    if (e1 instanceof OperatorExpression){
                        OperatorExpression oe1 = (OperatorExpression) e1;
                        boolean b1 = infixBracketOperators.containsKey(oe1.getOperator().toString());
                        boolean b2 = infixBracketOperators.containsKey(oe2.getOperator().toString());
                        if (b1 || b2) {
                            if (b1 && b2) return "("+e1infix+")("+e2infix+")";
                            if (b1) e1infix = "(" + e1infix + ")";
                            else    e2infix = "(" + e2infix + ")";
                        }
                        else if (oe1.getParam2() instanceof NumberExpression && oe2.getParam1() instanceof NumberExpression){
                            return e1infix+operator+e2infix;
                        }
                    }
                    else if (e1 instanceof NumberExpression){
                        if (oe2.getParam1() instanceof NumberExpression && !(oe2.getParam1() instanceof ConstantExpression)){
                            return e1infix+operator+e2infix;
                        }
                    }
                }
                else if (e2 instanceof UnaryExpression){
                    UnaryExpression ue = (UnaryExpression) e2;
                    if (ue.getFunction().getFunction().equals("neg")){
                        return e1infix+operator+e2infix;
                    }
                }
                else if (e2 instanceof NumberExpression && e1 instanceof NumberExpression){
                    return e1infix+operator+e2infix;
                }
                return e1infix+e2infix;
            }
            @Override public Expression getDerivative(Expression f, Expression g){
                Expression fprime = f.getDerivative(), gprime = g.getDerivative();
                return new OperatorExpression(Operators.getOperator("+"),
                        new OperatorExpression(Operators.getOperator("*"), f, gprime),
                        new OperatorExpression(Operators.getOperator("*"), g, fprime));
            }
            @Override public Expression getIntegral(Expression f, Expression g){
                if (f instanceof NumberExpression){
                    return new OperatorExpression(Operators.getOperator("*"),
                            f, g.getIntegral());
                }
                if (g instanceof NumberExpression){
                    return new OperatorExpression(Operators.getOperator("*"),
                            g, f.getIntegral());
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
                                ((MatrixExpression)x).getMatrix().add
                                (((MatrixExpression)y).getMatrix()));
                    }
                    return new MatrixExpression(
                            ((MatrixExpression)x).getMatrix().add(y.valueOf()));
                }
                else if (yIsMatrix){
                    return new MatrixExpression(
                            ((MatrixExpression)y).getMatrix().addi(x.valueOf()));
                }
                return new NumberExpression(x.valueOf()+y.valueOf());
            }
            @Override public Expression getDerivative(Expression x, Expression y){
                return new OperatorExpression(this, x.getDerivative(), y.getDerivative());
            }
            @Override public Expression getIntegral(Expression x, Expression y){
                return new OperatorExpression(this, x.getIntegral(), y.getIntegral());
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
                                ((MatrixExpression)x).getMatrix().sub
                                        (((MatrixExpression)y).getMatrix()));
                    }
                    return new MatrixExpression(
                            ((MatrixExpression)x).getMatrix().sub(y.valueOf()));
                }
                else if (yIsMatrix){
                    return new MatrixExpression(
                            ((MatrixExpression)y).getMatrix().rsub(x.valueOf()));
                }
                return new NumberExpression(x.valueOf()-y.valueOf());
            }
            @Override public Expression getDerivative(Expression x, Expression y){
                return new OperatorExpression(this, x.getDerivative(), y.getDerivative());
            }
            @Override public Expression getIntegral(Expression x, Expression y){
                return new OperatorExpression(this, x.getIntegral(), y.getIntegral());
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
                                ((MatrixExpression)x).getMatrix().div
                                        (((MatrixExpression)y).getMatrix()));
                    }
                    return new MatrixExpression(
                            ((MatrixExpression)x).getMatrix().div(y.valueOf()));
                }
                else if (yIsMatrix){
                    return new MatrixExpression(
                            ((MatrixExpression)y).getMatrix().rdiv(x.valueOf()));
                }
                return new NumberExpression(x.valueOf()/y.valueOf());
            }
            @Override
            public String infix(Expression e1, Expression e2){
                String e1infix = e1.infix(), e2infix = e2.infix();
                if (e1 instanceof OperatorExpression && infixBracketOperatorsDiv.containsKey(((OperatorExpression) e1).getOperator().toString())){
                    e1infix = "("+e1infix+")";
                }
                if (e2 instanceof OperatorExpression && infixBracketOperatorsDiv.containsKey(((OperatorExpression) e2).getOperator().toString())){
                    e2infix = "("+e2infix+")";
                }
                return e1infix+operator+e2infix;
            }
            @Override public Expression getDerivative(Expression f, Expression g){
                Expression fprime = f.getDerivative(), gprime = g.getDerivative();
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
                            f.getIntegral(), g);
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
            @Override
            public Expression evaluate(Expression e1, Expression e2) {
                double x = e1.valueOf(), y = e2.valueOf();
                if (x % 1 == 0 && y % 1 == 0)
                    return new NumberExpression(Combinatorics.choose((int)x, (int)y));
                return new InvalidExpression("Invalid Argument Error:  C expected integer arguments");
            }
        });
        operatorHashMap.put("P", new Operator("P", 4, false, true){
            @Override
            public Expression evaluate(Expression e1, Expression e2) {
                double x = e1.valueOf(), y = e2.valueOf();
                if (x % 1 == 0 && y % 1 == 0)
                    return new NumberExpression(Combinatorics.permute((int)x, (int)y));
                return new InvalidExpression("Invalid Argument Error:  P expected integer arguments");
            }
        });
        operatorHashMap.put("^", new Operator("^", 4, false, false){
            @Override
            public Expression evaluate(Expression e1, Expression e2) {
                double x = e1.valueOf(), y = e2.valueOf();
                return new NumberExpression((e1 instanceof VariableExpression && e1.toString().equals("e")) ?
                        Math.exp(y) : Math.pow(x, y));
            }
            @Override
            public String infix(Expression e1, Expression e2){
                String e1infix = e1.infix();
                if (e1 instanceof OperatorExpression && infixBracketOperatorsDiv.containsKey(((OperatorExpression) e1).getOperator().toString())){
                    e1infix = "("+e1infix+")";
                }
                if (e2 instanceof NumberExpression){
                    String superscript = Scripts.toSuperScript(_Number_.format(e2.valueOf()));
                    if (e1 instanceof UnaryExpression){
                        UnaryExpression ue = (UnaryExpression) e1;
                        if (!((UnaryExpression) e1).getFunction().getFunction().equals("abs")) {
                            if (ue.getParam() instanceof NumberExpression && ue.getParam().valueOf() > 0) {
                                return ue.getFunction().getFunction() + superscript + ue.getParam().infix();
                            }
                            return ue.getFunction().getFunction() + superscript + "(" + ue.getParam().infix() + ")";
                        }
                    }
                    return e1infix+superscript;
                }
                String e2infix = e2.infix();
                if (e2 instanceof OperatorExpression && infixBracketOperatorsDiv.containsKey(((OperatorExpression) e2).getOperator().toString())){
                    e2infix = "("+e2infix+")";
                }
                return e1infix+operator+e2infix;
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
                        if (ue.getFunction().getFunction().equals("sec") && ue.getParam() instanceof VariableExpression){
                            return new UnaryExpression(getUnaryFunction("tan"), ue.getParam());
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
            @Override
            public Expression evaluate(Expression e1) {
                double x = e1.valueOf();
                if (x%1 == 0) {
                    return new NumberExpression(~((int)x));
                }
                return new InvalidExpression("Invalid Argument Error:  ~ expects integer argument");
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
                                ((MatrixExpression)x).getMatrix().dot
                                        (((MatrixExpression)y).getMatrix()));
                    }
                    return new MatrixExpression(
                            ((MatrixExpression)x).getMatrix().mmul(y.valueOf()));
                }
                else if (yIsMatrix){
                    return new MatrixExpression(
                            ((MatrixExpression)y).getMatrix().mmul(x.valueOf()));
                }
                return new NumberExpression(x.valueOf()*y.valueOf());
            }
            @Override
            public String infix(Expression e1, Expression e2){
                String e1infix = e1.infix(), e2infix = e2.infix();
                if (e2 instanceof OperatorExpression){
                    OperatorExpression oe2 = (OperatorExpression) e2;
                    if (e1 instanceof OperatorExpression){
                        OperatorExpression oe1 = (OperatorExpression) e1;
                        boolean b1 = infixBracketOperators.containsKey(oe1.getOperator().toString());
                        boolean b2 = infixBracketOperators.containsKey(oe2.getOperator().toString());
                        if (b1 || b2) {
                            if (b1 && b2) return "("+e1infix+")("+e2infix+")";
                            if (b1) e1infix = "(" + e1infix + ")";
                            else    e2infix = "(" + e2infix + ")";
                        }
                        else if (oe1.getParam2() instanceof NumberExpression && oe2.getParam1() instanceof NumberExpression){
                            return e1infix+operator+e2infix;
                        }
                    }
                    else if (e1 instanceof NumberExpression){
                        if (oe2.getParam1() instanceof NumberExpression && !(oe2.getParam1() instanceof ConstantExpression)){
                            return e1infix+operator+e2infix;
                        }
                    }
                }
                else if (e2 instanceof UnaryExpression){
                    UnaryExpression ue = (UnaryExpression) e2;
                    if (ue.getFunction().getFunction().equals("neg")){
                        return e1infix+operator+e2infix;
                    }
                }
                else if (e2 instanceof NumberExpression && e1 instanceof NumberExpression){
                    return e1infix+operator+e2infix;
                }
                return e1infix+e2infix;
            }
            @Override public Expression getDerivative(Expression f, Expression g){
                Expression fprime = f.getDerivative(), gprime = g.getDerivative();
                return new OperatorExpression(Operators.getOperator("+"),
                        new OperatorExpression(Operators.getOperator("*"), f, gprime),
                        new OperatorExpression(Operators.getOperator("*"), g, fprime));
            }
            @Override public Expression getIntegral(Expression f, Expression g){
                if (f instanceof NumberExpression){
                    return new OperatorExpression(Operators.getOperator("*"),
                            f, g.getIntegral());
                }
                if (g instanceof NumberExpression){
                    return new OperatorExpression(Operators.getOperator("*"),
                            g, f.getIntegral());
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
        });
        operatorHashMap.put("∨", new Operator("∨", 3, false, true){
            @Override
            public Expression evaluate(Expression e1, Expression e2) {
                double x = e1.valueOf(), y = e2.valueOf();
                if (x%1 == 0 && y%1 == 0) {
                    return new NumberExpression(((int)x) | ((int)y));
                }
                return new InvalidExpression("Invalid Argument Error:  ∨ expected integer arguments");
            }
        });
        operatorHashMap.put("≪", new Operator("≪", 3, false, true){
            @Override
            public Expression evaluate(Expression e1, Expression e2) {
                double x = e1.valueOf(), y = e2.valueOf();
                if (x%1 == 0 && y%1 == 0) {
                    return new NumberExpression(((int)x) << ((int)y));
                }
                return new InvalidExpression("Invalid Argument Error:  ≪ expected integer arguments");
            }
        });
        operatorHashMap.put("≫", new Operator("≫", 3, false, true){
            @Override
            public Expression evaluate(Expression e1, Expression e2) {
                double x = e1.valueOf(), y = e2.valueOf();
                if (x%1 == 0 && y%1 == 0) {
                    return new NumberExpression(((int)x) >> ((int)y));
                }
                return new InvalidExpression("Invalid Argument Error:  ≫ expected integer arguments");
            }
        });
        operatorHashMap.put("⊻", new Operator("⊻", 3, false, true){
            @Override
            public Expression evaluate(Expression e1, Expression e2) {
                double x = e1.valueOf(), y = e2.valueOf();
                if (x%1 == 0 && y%1 == 0) {
                    return new NumberExpression(((int)x) ^ ((int)y));
                }
                return new InvalidExpression("Invalid Argument Error:  ⊻ expected integer arguments");
            }
        });
        return operatorHashMap;
    }

    public static final String INVALID = "NaN";

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
