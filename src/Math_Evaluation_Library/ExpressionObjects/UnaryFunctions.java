package Math_Evaluation_Library.ExpressionObjects;

import Math_Evaluation_Library.Engine.Engine;
import Math_Evaluation_Library.Expressions.*;
import Math_Evaluation_Library.LinearAlgebra._Matrix_;
import Math_Evaluation_Library.Miscellaneous.Special;
import Math_Evaluation_Library.Objects._Number_;
import Math_Evaluation_Library.Statistics.RandomVariables;
import Math_Evaluation_Library.Trigonometry.Trig;

import java.util.HashMap;

import static Math_Evaluation_Library.Engine.Engine.x;
import static Math_Evaluation_Library.ExpressionObjects.Operators.getOperator;
import static Math_Evaluation_Library.ExpressionObjects.Operators.infixBracketOperatorsR;

/**
 * Created by Antonio on 2017-10-17.
 */
public class UnaryFunctions {

//    public static void main (String[] args){
//        for (UnaryFunction function :  unaryFunctionMap.values()){
//            System.out.println("map.put(\""+function.toString()+"\", \""+function.getDescription()+"\");");
//
//        }
//    }

    private static final HashMap<String, UnaryFunction> unaryFunctionMap = createMap();

    private static HashMap<String, UnaryFunction> createMap(){
        HashMap<String, UnaryFunction> map  = new HashMap<>();
        map.put("abs", new UnaryFunction("abs", "abs(x) = |x|")
        {
            @Override
            public Expression evaluate(Expression x) {
                return new NumberExpression(Math.abs(x.valueOf()));
            }
            @Override
            public String infix(Expression x) {
                return "|"+x.infix()+"|";
            }
            @Override
            public String toTeX(Expression x){
                return "\\left|"+x.infix()+"\\right|";
            }
            @Override
            public Expression getDerivative(Expression x){
                return new OperatorExpression(getOperator("*"),
                        new OperatorExpression(getOperator("/"),
                                new UnaryExpression(this, x), x),
                        x.calculateDerivative());
            }
            @Override
            public Expression getIntegral(Expression x){
                // Technically, it equals 1/2 x^2 sgn(x).
                return x.calculateIntegral();
            }
        });
        map.put("arccos", new UnaryFunction("arccos", "arccos(x) equals the Inverse Cosine function")
        {
            @Override
            public Expression evaluate(Expression x) {
                return new NumberExpression(Math.acos(x.valueOf()));
            }
            @Override
            public Expression getDerivative(Expression x){
                return new OperatorExpression(getOperator("/"),
                        new UnaryExpression(getUnaryFunction("neg"),
                                x.calculateDerivative()),
                        new OperatorExpression(getOperator("√"),
                                new OperatorExpression(getOperator("-"),
                                        new NumberExpression(1),
                                        new OperatorExpression(getOperator("^"),
                                                x, new NumberExpression(2)))));
            }
            @Override
            public Expression getIntegral(Expression x){
                if (x instanceof VariableExpression) {
                    return new OperatorExpression(getOperator("-"),
                            new OperatorExpression(getOperator("*"),
                                    new VariableExpression(Engine.var),
                                    new UnaryExpression(this, x)),
                            new OperatorExpression(getOperator("√"),
                                    new  OperatorExpression(getOperator("-"),
                                            new NumberExpression(1),
                                            new OperatorExpression(getOperator("^"),
                                                    new VariableExpression(Engine.var),
                                                    new NumberExpression(2)))));
                }
                return super.getIntegral(x);
            }
        });
        map.put("arccosh", new UnaryFunction("arccosh", "arccosh(x) equals the Inverse Hyperbolic Cosine function")
        {
            @Override
            public Expression evaluate(Expression x) {
                return new NumberExpression(Trig.acosh(x.valueOf()));
            }
            @Override
            public Expression getDerivative(Expression x){
                return new OperatorExpression(getOperator("/"),
                        x.calculateDerivative(),
                        new OperatorExpression(getOperator("*"),
                                new OperatorExpression(getOperator("√"),
                                        new OperatorExpression(getOperator("-"),
                                                x, new NumberExpression(1))),
                                new OperatorExpression(getOperator("√"),
                                        new OperatorExpression(getOperator("+"),
                                                x, new NumberExpression(1)))));
            }
            @Override
            public Expression getIntegral(Expression x){
                if (x instanceof VariableExpression) {
                    return new OperatorExpression(getOperator("-"),
                            new OperatorExpression(getOperator("*"),
                                    new VariableExpression(Engine.var),
                                    new UnaryExpression(this, x)),
                            new OperatorExpression(getOperator("*"),
                                    new OperatorExpression(getOperator("√"),
                                            new OperatorExpression(getOperator("-"),
                                                    new VariableExpression(Engine.var),
                                                    new NumberExpression(1))),
                                    new OperatorExpression(getOperator("√"),
                                            new OperatorExpression(getOperator("+"),
                                                    new VariableExpression(Engine.var),
                                                    new NumberExpression(1)))));
                }
                return super.getIntegral(x);
            }
        });
        map.put("arccot", new UnaryFunction("arccot", "arccot(x) equals the Inverse Cotangent function")
        {
            @Override
            public Expression evaluate(Expression x) {
                return new NumberExpression(Trig.acot(x.valueOf()));
            }
            @Override
            public Expression getDerivative(Expression x){
                return new OperatorExpression(getOperator("/"),
                        new UnaryExpression(getUnaryFunction("neg"),
                                x.calculateDerivative()),
                        new OperatorExpression(getOperator("+"),
                                new OperatorExpression(getOperator("^"),
                                        x, new NumberExpression(2)),
                                new NumberExpression(1)));
            }
            @Override
            public Expression getIntegral(Expression x){
                if (x instanceof VariableExpression) {
                    return new OperatorExpression(getOperator("+"),
                            new OperatorExpression(getOperator("*"),
                                    new VariableExpression(Engine.var),
                                    new UnaryExpression(this, x)),
                            new OperatorExpression(getOperator("*"),
                                    new NumberExpression(0.5),
                                    new UnaryExpression(getUnaryFunction("ln"),
                                            new OperatorExpression(getOperator("+"),
                                                    new OperatorExpression(getOperator("^"),
                                                            new VariableExpression(Engine.var),
                                                            new NumberExpression(2)),
                                                    new NumberExpression(1)))));
                }
                return super.getIntegral(x);
            }
        });
        map.put("arccoth", new UnaryFunction("arccoth", "arccoth(x) equals the Inverse Hyperbolic Cotangent function")
        {
            @Override
            public Expression evaluate(Expression x) {
                return new NumberExpression(Trig.acoth(x.valueOf()));
            }
            @Override
            public Expression getDerivative(Expression x){
                return new OperatorExpression(getOperator("/"),
                        x.calculateDerivative(),
                        new OperatorExpression(getOperator("-"),
                                new NumberExpression(1),
                                new OperatorExpression(getOperator("^"),
                                        x, new NumberExpression(2))));
            }
            @Override
            public Expression getIntegral(Expression x){
                if (x instanceof VariableExpression) {
                    return new OperatorExpression(getOperator("+"),
                            new OperatorExpression(getOperator("*"),
                                    new VariableExpression(Engine.var),
                                    new UnaryExpression(this, x)),
                            new OperatorExpression(getOperator("*"),
                                    new NumberExpression(0.5),
                                    new UnaryExpression(getUnaryFunction("ln"),
                                            new OperatorExpression(getOperator("-"),
                                                    new NumberExpression(1),
                                                    new OperatorExpression(getOperator("^"),
                                                            new VariableExpression(Engine.var),
                                                            new NumberExpression(2))))));
                }
                return super.getIntegral(x);
            }
        });
        map.put("arccsc", new UnaryFunction("arccsc", "arccsc(x) equals the Inverse Cosecant function")
        {
            @Override
            public Expression evaluate(Expression x) {
                return new NumberExpression(Trig.acsc(x.valueOf()));
            }
            @Override
            public Expression getDerivative(Expression x){
                return new OperatorExpression(getOperator("/"),
                        new UnaryExpression(getUnaryFunction("neg"),
                                x.calculateDerivative()),
                        new OperatorExpression(getOperator("*"),
                                new UnaryExpression(getUnaryFunction("abs"), x),
                                new OperatorExpression(getOperator("√"),
                                        new OperatorExpression(getOperator("-"),
                                                new OperatorExpression(getOperator("^"),
                                                        x, new NumberExpression(2)),
                                                new NumberExpression(1)))));
            }
            @Override
            public Expression getIntegral(Expression x){
                if (x instanceof VariableExpression) {
                    Operator multiply = getOperator("*");
                    return new OperatorExpression(getOperator("+"),
                            new OperatorExpression(multiply,
                                    new VariableExpression(Engine.var),
                                    new UnaryExpression(this, x)),
                            new OperatorExpression(multiply,
                                    new OperatorExpression(getOperator("/"),
                                            new VariableExpression(Engine.var),
                                            new UnaryExpression(getUnaryFunction("abs"), x)),
                                    new UnaryExpression(getUnaryFunction("ln"),
                                            new OperatorExpression(getOperator("+"),
                                                    new OperatorExpression(getOperator("√"),
                                                            new OperatorExpression(getOperator("-"),
                                                                    new OperatorExpression(getOperator("^"),
                                                                            new VariableExpression(Engine.var),
                                                                            new NumberExpression(2)),
                                                                    new NumberExpression(1))),
                                                    new VariableExpression(Engine.var)))));
                }
                return super.getIntegral(x);
            }
        });
        map.put("arccsch", new UnaryFunction("arccsch", "arccsch(x) equals the Inverse Hyperbolic Cosecant function")
        {
            @Override
            public Expression evaluate(Expression x) {
                return new NumberExpression(Trig.acsch(x.valueOf()));
            }
            @Override
            public Expression getDerivative(Expression x){
                return new OperatorExpression(getOperator("/"),
                        new UnaryExpression(getUnaryFunction("neg"),
                                x.calculateDerivative()),
                        new OperatorExpression(getOperator("*"),
                                new UnaryExpression(getUnaryFunction("abs"), x),
                                new OperatorExpression(getOperator("√"),
                                        new OperatorExpression(getOperator("+"),
                                                new OperatorExpression(getOperator("^"),
                                                        x, new NumberExpression(2)),
                                                new NumberExpression(1)))));
            }
            @Override
            public Expression getIntegral(Expression x){
                if (x instanceof VariableExpression) {
                    Operator multiply = getOperator("*");
                    return new OperatorExpression(getOperator("+"),
                            new OperatorExpression(multiply,
                                    new VariableExpression(Engine.var),
                                    new UnaryExpression(this, x)),
                            new OperatorExpression(multiply,
                                    new OperatorExpression(getOperator("/"),
                                            new VariableExpression(Engine.var),
                                            new UnaryExpression(getUnaryFunction("abs"), x)),
                                    new UnaryExpression(getUnaryFunction("arcsinh"), x)));
                }
                return super.getIntegral(x);
            }
        });
        map.put("arcsec", new UnaryFunction("arcsec", "arcsec(x) equals the Inverse Secant function")
        {
            @Override
            public Expression evaluate(Expression x) {
                return new NumberExpression(Trig.asec(x.valueOf()));
            }
            @Override
            public Expression getDerivative(Expression x){
                return new OperatorExpression(getOperator("/"),
                        x.calculateDerivative(),
                        new OperatorExpression(getOperator("*"),
                                new UnaryExpression(getUnaryFunction("abs"), x),
                                new OperatorExpression(getOperator("√"),
                                        new OperatorExpression(getOperator("-"),
                                                new OperatorExpression(getOperator("^"),
                                                        x, new NumberExpression(2)),
                                                new NumberExpression(1)))));
            }
            @Override
            public Expression getIntegral(Expression x){
                if (x instanceof VariableExpression) {
                    Operator multiply = getOperator("*");
                    return new OperatorExpression(getOperator("-"),
                            new OperatorExpression(multiply,
                                    new VariableExpression(Engine.var),
                                    new UnaryExpression(this, x)),
                            new OperatorExpression(multiply,
                                    new OperatorExpression(getOperator("/"),
                                            new VariableExpression(Engine.var),
                                            new UnaryExpression(getUnaryFunction("abs"), x)),
                                    new UnaryExpression(getUnaryFunction("ln"),
                                            new OperatorExpression(getOperator("+"),
                                                    new OperatorExpression(getOperator("√"),
                                                            new OperatorExpression(getOperator("-"),
                                                                    new OperatorExpression(getOperator("^"),
                                                                            new VariableExpression(Engine.var),
                                                                            new NumberExpression(2)),
                                                                    new NumberExpression(1))),
                                                    new VariableExpression(Engine.var)))));
                }
                return super.getIntegral(x);
            }
        });
        map.put("arcsech", new UnaryFunction("arcsech", "arcsech(x) equals the Inverse Hyperbolic Secant function")
        {
            @Override
            public Expression evaluate(Expression x) {
                return new NumberExpression(Trig.asech(x.valueOf()));
            }
            @Override
            public Expression getDerivative(Expression x){
                return new OperatorExpression(getOperator("*"),
                        new OperatorExpression(getOperator("/"),
                                new OperatorExpression(getOperator("√"),
                                        new OperatorExpression(getOperator("/"),
                                                new OperatorExpression(getOperator("-"),
                                                        new NumberExpression(1), x),
                                                new OperatorExpression(getOperator("+"),
                                                        new NumberExpression(1), x))),
                                new OperatorExpression(getOperator("*"),
                                        x, new OperatorExpression(getOperator("-"),
                                                x, new NumberExpression(1)))),
                        x.calculateDerivative());
            }
            @Override
            public Expression getIntegral(Expression x){
                if (x instanceof VariableExpression) {
                    Operator multiply = getOperator("*");
                    return new OperatorExpression(getOperator("-"),
                            new OperatorExpression(multiply,
                                    new VariableExpression(Engine.var),
                                    new UnaryExpression(this, x)),
                            new OperatorExpression(multiply,
                                    new OperatorExpression(multiply,
                                            new NumberExpression(2),
                                            new OperatorExpression(getOperator("/"),
                                                    new UnaryExpression(getUnaryFunction("abs"),
                                                            new OperatorExpression(getOperator("-"),
                                                                    new NumberExpression(1), x)),
                                                    new OperatorExpression(getOperator("-"),
                                                            x, new NumberExpression(1)))),
                                    new UnaryExpression(getUnaryFunction("arcsin"),
                                            new OperatorExpression(getOperator("√"),
                                                    new OperatorExpression(getOperator("/"),
                                                            new OperatorExpression(getOperator("+"),
                                                                    x, new NumberExpression(1)),
                                                            new NumberExpression(2))))));
                }
                return super.getIntegral(x);
            }
        });
        map.put("arcsin", new UnaryFunction("arcsin", "arcsin(x) equals the Inverse Sine function")
        {
            @Override
            public Expression evaluate(Expression x) {
                return new NumberExpression(Math.asin(x.valueOf()));
            }
            @Override
            public Expression getDerivative(Expression x){
                return new OperatorExpression(getOperator("/"),
                        x.calculateDerivative(),
                        new OperatorExpression(getOperator("√"),
                                new OperatorExpression(getOperator("-"),
                                        new NumberExpression(1),
                                        new OperatorExpression(getOperator("^"),
                                                x, new NumberExpression(2)))));
            }
            @Override
            public Expression getIntegral(Expression x){
                if (x instanceof VariableExpression) {
                    return new OperatorExpression(getOperator("+"),
                            new OperatorExpression(getOperator("*"),
                                    new VariableExpression(Engine.var),
                                    new UnaryExpression(this, x)),
                            new OperatorExpression(getOperator("√"),
                                    new  OperatorExpression(getOperator("-"),
                                            new NumberExpression(1),
                                            new OperatorExpression(getOperator("^"),
                                                    new VariableExpression(Engine.var),
                                                    new NumberExpression(2)))));
                }
                return super.getIntegral(x);
            }
        });
        map.put("arcsinh", new UnaryFunction("arcsinh", "arcsinh(x) equals the Inverse Hyperbolic Sine function")
        {
            @Override
            public Expression evaluate(Expression x) {
                return new NumberExpression(Trig.asinh(x.valueOf()));
            }
            @Override
            public Expression getDerivative(Expression x){
                return new OperatorExpression(getOperator("/"),
                        x.calculateDerivative(),
                        new OperatorExpression(getOperator("√"),
                                new OperatorExpression(getOperator("+"),
                                        new OperatorExpression(getOperator("^"),
                                                new VariableExpression(Engine.var),
                                                new NumberExpression(2)),
                                        new NumberExpression(1))));
            }
            @Override
            public Expression getIntegral(Expression x){
                if (x instanceof VariableExpression) {
                    return new OperatorExpression(getOperator("-"),
                            new OperatorExpression(getOperator("*"),
                                    new VariableExpression(Engine.var),
                                    new UnaryExpression(this, x)),
                            new OperatorExpression(getOperator("√"),
                                    new OperatorExpression(getOperator("+"),
                                            new OperatorExpression(getOperator("^"),
                                                    new VariableExpression(Engine.var),
                                                    new NumberExpression(2)),
                                            new NumberExpression(1))));
                }
                return super.getIntegral(x);
            }
        });
        map.put("arctan", new UnaryFunction("arctan", "arctan(x) equals the Inverse Tangent function")
        {
            @Override
            public Expression evaluate(Expression x) {
                return new NumberExpression(Math.atan(x.valueOf()));
            }
            @Override
            public Expression getDerivative(Expression x){
                return new OperatorExpression(getOperator("/"),
                        x.calculateDerivative(),
                        new OperatorExpression(getOperator("+"),
                                new OperatorExpression(getOperator("^"),
                                        x, new NumberExpression(2)),
                                new NumberExpression(1)));
            }
            @Override
            public Expression getIntegral(Expression x){
                if (x instanceof VariableExpression) {
                    return new OperatorExpression(getOperator("-"),
                            new OperatorExpression(getOperator("*"),
                                    new VariableExpression(Engine.var),
                                    new UnaryExpression(this, x)),
                            new OperatorExpression(getOperator("*"),
                                    new NumberExpression(0.5),
                                    new UnaryExpression(getUnaryFunction("ln"),
                                            new OperatorExpression(getOperator("+"),
                                                    new OperatorExpression(getOperator("^"),
                                                            new VariableExpression(Engine.var),
                                                            new NumberExpression(2)),
                                                    new NumberExpression(1)))));
                }
                return super.getIntegral(x);
            }
        });
        map.put("arctanh", new UnaryFunction("arctanh", "arctanh(x) equals the Inverse Hyperbolic Tangent function")
        {
            @Override
            public Expression evaluate(Expression x) {
                return new NumberExpression(Trig.atanh(x.valueOf()));
            }
            @Override
            public Expression getDerivative(Expression x){
                return new OperatorExpression(getOperator("/"),
                        x.calculateDerivative(),
                        new OperatorExpression(getOperator("-"),
                                new NumberExpression(1),
                                new OperatorExpression(getOperator("^"),
                                        x, new NumberExpression(2))));
            }
            @Override
            public Expression getIntegral(Expression x){
                if (x instanceof VariableExpression) {
                    return new OperatorExpression(getOperator("-"),
                            new OperatorExpression(getOperator("*"),
                                    new VariableExpression(Engine.var),
                                    new UnaryExpression(this, x)),
                            new OperatorExpression(getOperator("*"),
                                    new NumberExpression(0.5),
                                    new UnaryExpression(getUnaryFunction("ln"),
                                            new OperatorExpression(getOperator("-"),
                                                    new NumberExpression(1),
                                                    new OperatorExpression(getOperator("^"),
                                                            new VariableExpression(Engine.var),
                                                            new NumberExpression(2))))));
                }
                return super.getIntegral(x);
            }
        });
        map.put("ceil", new UnaryFunction("ceil", "ceil(x) = ⌈x⌉,  smallest integer ≥ x")
        {
            @Override
            public Expression evaluate(Expression x) {
                return new NumberExpression(Math.ceil(x.valueOf()));
            }
        });
        map.put("cos", new UnaryFunction("cos", "cos(x) equals the Cosine function")
        {
            @Override
            public Expression evaluate(Expression x) {
                return new NumberExpression(Math.cos(x.valueOf()));
            }
            @Override
            public Expression getDerivative(Expression x){
                return new UnaryExpression(getUnaryFunction("neg"),
                        new OperatorExpression(getOperator("*"),
                                new UnaryExpression(getUnaryFunction("sin"), x),
                                x.calculateDerivative()));
            }
            @Override
            public Expression getIntegral(Expression x){
                if (x instanceof VariableExpression) {
                    return new UnaryExpression(getUnaryFunction("sin"), x);
                }
                if (x instanceof OperatorExpression) {
                    OperatorExpression oe = (OperatorExpression) x;
                    if (oe.operator().toString().equals("*") &&
                            oe.param1() instanceof NumberExpression &&
                            oe.param2() instanceof VariableExpression){
                        return new OperatorExpression(getOperator("/"),
                                new UnaryExpression(getUnaryFunction("sin"), x),
                                oe.param1());
                    }
                    return new UnaryExpression(getUnaryFunction("sin"), x);
                }
                return super.getIntegral(x);
            }
        });
        map.put("cosh", new UnaryFunction("cosh", "cosh(x) equals the Hyperbolic Cosine function")
        {
            @Override
            public Expression evaluate(Expression x) {
                return new NumberExpression(Math.cosh(x.valueOf()));
            }
            @Override
            public Expression getDerivative(Expression x){
                return new OperatorExpression(getOperator("*"),
                        new UnaryExpression(getUnaryFunction("sinh"), x),
                        x.calculateDerivative());
            }
            @Override
            public Expression getIntegral(Expression x){
                if (x instanceof VariableExpression) {
                    return new UnaryExpression(getUnaryFunction("sinh"), x);
                }
                return super.getIntegral(x);
            }
        });
        map.put("cot", new UnaryFunction("cot", "cot(x) equals the Cotangent function")
        {
            @Override
            public Expression evaluate(Expression x) {
                return new NumberExpression(Trig.cot(x.valueOf()));
            }
            @Override
            public Expression getDerivative(Expression x){
                return new UnaryExpression(getUnaryFunction("neg"),
                        new OperatorExpression(getOperator("*"),
                                new OperatorExpression(getOperator("^"),
                                        new UnaryExpression(getUnaryFunction("csc"), x),
                                        new NumberExpression(2)),
                                x.calculateDerivative()));
            }
            @Override
            public Expression getIntegral(Expression x){
                if (x instanceof VariableExpression) {
                    return new UnaryExpression(getUnaryFunction("ln"),
                            new UnaryExpression(getUnaryFunction("sin"), x));
                }
                return super.getIntegral(x);
            }
        });
        map.put("coth", new UnaryFunction("coth", "coth(x) equals the Hyperbolic Cotangent function")
        {
            @Override
            public Expression evaluate(Expression x) {
                return new NumberExpression(Trig.coth(x.valueOf()));
            }
            @Override
            public Expression getDerivative(Expression x){
                return new UnaryExpression(getUnaryFunction("neg"),
                        new OperatorExpression(getOperator("*"),
                                new OperatorExpression(getOperator("^"),
                                        new UnaryExpression(getUnaryFunction("csch"), x),
                                        new NumberExpression(2)),
                                x.calculateDerivative()));
            }
            @Override
            public Expression getIntegral(Expression x){
                if (x instanceof VariableExpression) {
                    return new UnaryExpression(getUnaryFunction("ln"),
                            new UnaryExpression(getUnaryFunction("sinh"), x));
                }
                return super.getIntegral(x);
            }
        });
        map.put("csc", new UnaryFunction("csc", "csc(x) equals the Cosecant function")
        {
            @Override
            public Expression evaluate(Expression x) {
                return new NumberExpression(Trig.csc(x.valueOf()));
            }
            @Override
            public Expression getDerivative(Expression x){
                return new UnaryExpression(getUnaryFunction("neg"),
                        new OperatorExpression(getOperator("*"),
                                new OperatorExpression(getOperator("*"),
                                        new UnaryExpression(getUnaryFunction("cot"), x),
                                        new UnaryExpression(getUnaryFunction("csc"), x)),
                                x.calculateDerivative()));
            }
            @Override
            public Expression getIntegral(Expression x){
                if (x instanceof VariableExpression) {
                    return new UnaryExpression(getUnaryFunction("neg"),
                            new UnaryExpression(getUnaryFunction("ln"),
                                    new OperatorExpression(getOperator("+"),
                                            new UnaryExpression(getUnaryFunction("cot"), x),
                                            new UnaryExpression(getUnaryFunction("csc"), x))));
                }
                return super.getIntegral(x);
            }
        });
        map.put("csch", new UnaryFunction("csch", "csch(x) equals the Hyperbolic Cosecant function")
        {
            @Override
            public Expression evaluate(Expression x) {
                return new NumberExpression(Trig.csch(x.valueOf()));
            }
            @Override
            public Expression getDerivative(Expression x){
                return new UnaryExpression(getUnaryFunction("neg"),
                        new OperatorExpression(getOperator("*"),
                                new OperatorExpression(getOperator("*"),
                                        new UnaryExpression(getUnaryFunction("coth"), x),
                                        new UnaryExpression(getUnaryFunction("csch"), x)),
                                x.calculateDerivative()));
            }
            @Override
            public Expression getIntegral(Expression x){
                if (x instanceof VariableExpression) {
                    return new UnaryExpression(getUnaryFunction("ln"),
                            new UnaryExpression(getUnaryFunction("tanh"),
                                    new OperatorExpression(getOperator("/"),
                                            x, new NumberExpression(2))));
                }
                return super.getIntegral(x);
            }
        });
        map.put("deg", new UnaryFunction("deg", "deg(x) = x°, x in angular degrees")
        {
            @Override
            public Expression evaluate(Expression x) {
                return new NumberExpression(Math.toDegrees(x.valueOf()));
            }
        });
        map.put("det", new UnaryFunction("det", "det(M) computes determinant of matrix M")
        {
            @Override
            public Expression evaluate(Expression x) {
                if (x instanceof MatrixExpression)
                    return new NumberExpression(_Matrix_.getDeterminant(((MatrixExpression) x).doubleMatrix()));
                return new InvalidExpression("Invalid Argument Error:  det expected matrix as input");
            }
        });
        map.put("erf", new UnaryFunction("erf", "erf(x) = (2/√π)∫(e^(-x²), 0, x), the Gauss Error Function")
        {
            @Override
            public Expression evaluate(Expression x) {
                return new NumberExpression(Special.errorFunction(x.valueOf()));
            }
        });
        map.put("exp", new UnaryFunction("exp", "exp(x) = eˣ")
        {
            @Override
            public Expression evaluate(Expression x) {
                return new NumberExpression(Math.exp(x.valueOf()));
            }
            @Override
            public String toTeX(Expression x){
                return "e^{"+x.toTeX()+"}";
            }
            @Override
            public Expression getDerivative(Expression x){
                return new OperatorExpression(getOperator("*"),
                        new UnaryExpression(this, x),
                        x.calculateDerivative());
            }
            @Override
            public Expression getIntegral(Expression x){
                if (x instanceof VariableExpression) {
                    return new UnaryExpression(this, x);
                }
                return super.getIntegral(x);
            }
        });
        map.put("Exp", new UnaryFunction("Exp", "Exp(X) calculates expectation of X")
        {
            @Override
            public Expression evaluate(Expression x) {
                if (x instanceof MultiParamExpression){
                    MultiParamExpression mpe = (MultiParamExpression)x;
                    if (mpe.function() instanceof RandomVariable){
                        return ((RandomVariable)mpe.function()).getExpectedValue(mpe.parameters());
                    }
                }
                return new InvalidExpression("Invalid Argument Error:  Exp expected a random variable as input");
            }
        });
        map.put("fib", new UnaryFunction("fib", "fib(n), nᵗʰ term in the fibonacci sequence")
        {
            @Override
            public Expression evaluate(Expression x) {
                double n = x.valueOf();
                if (n % 1 == 0 && n >= 0 && n <= 1472){
                    return new NumberExpression(_Number_.getFibonnaci((int) n));
                }
                return new InvalidExpression("Invalid Argument Error:  Expected n∈ℤ⁺, n∈[0, 1472]");
            }
        });
        map.put("floor", new UnaryFunction("floor", "floor(x) = ⌊x⌋, greatest integer ≤ x")
        {
            @Override
            public Expression evaluate(Expression x) {
                return new NumberExpression(Math.floor(x.valueOf()));
            }
        });
        map.put("floor₂", new UnaryFunction("floor₂", "floor₂(x) calculates the lowest power of 2 ≤ x")
        {
            @Override
            public Expression evaluate(Expression x) {
                double n = x.valueOf();
                if (n % 1 == 0) return new NumberExpression(_Number_.floor2((int)n));
                return new NumberExpression(_Number_.floor2(n));
            }
        });
        map.put("ln", new UnaryFunction("ln", "ln(x) equals the Natural Logarithm")
        {
            @Override
            public Expression evaluate(Expression x) {
                return new NumberExpression(Math.log(x.valueOf()));
            }
            @Override
            public Expression getDerivative(Expression x){
                return new OperatorExpression(getOperator("/"), x.calculateDerivative(), x);
            }
            @Override
            public Expression getIntegral(Expression x){
                if (x instanceof VariableExpression ||
                        (x instanceof OperatorExpression &&
                                ((OperatorExpression) x).operator().toString().equals("*") &&
                                ((OperatorExpression) x).param1() instanceof NumberExpression &&
                                ((OperatorExpression) x).param2() instanceof VariableExpression)) {
                    return new OperatorExpression(getOperator("-"),
                            new OperatorExpression(getOperator("*"),
                                    new VariableExpression(Engine.var),
                                    new UnaryExpression(getUnaryFunction("ln"), x)),
                            new VariableExpression(Engine.var));
                }
                return super.getIntegral(x);
            }
        });
        map.put("log", new UnaryFunction("log", "log(x) = log₁₀(x), equals the Base 10 Logarithm")
        {
            @Override
            public Expression evaluate(Expression x) {
                return new NumberExpression(Math.log10(x.valueOf()));
            }
            @Override
            public Expression getDerivative(Expression x){
                return new OperatorExpression(getOperator("/"), x.calculateDerivative(),
                        new OperatorExpression(getOperator("*"),
                                x, new UnaryExpression(getUnaryFunction("ln"), new NumberExpression(10))));
            }
            @Override
            public Expression getIntegral(Expression x){
                if (x instanceof VariableExpression) {
                    return new OperatorExpression(getOperator("/"),
                            new OperatorExpression(getOperator("-"),
                                    new OperatorExpression(getOperator("*"),
                                            new VariableExpression(Engine.var),
                                            new UnaryExpression(getUnaryFunction("ln"), x)),
                                    new VariableExpression(Engine.var)),
                            new UnaryExpression(getUnaryFunction("ln"), new NumberExpression(10)));
                }
                return super.getIntegral(x);
            }
        });
        map.put("lp", new UnaryFunction("lp", "lp(x) = ln(x+1), equals the Natural Logarithm of (x+1)")
        {
            @Override
            public Expression evaluate(Expression x) {
                return new NumberExpression(Math.log1p(x.valueOf()));
            }
            @Override
            public Expression getDerivative(Expression x){
                return new OperatorExpression(getOperator("/"), x.calculateDerivative(),
                        new OperatorExpression(getOperator("+"), x, new NumberExpression(1)));
            }
            @Override
            public Expression getIntegral(Expression x){
                if (x instanceof VariableExpression) {
                    return new OperatorExpression(getOperator("-"),
                            new OperatorExpression(getOperator("*"),
                                    new OperatorExpression(getOperator("+"),
                                            new VariableExpression(Engine.var),
                                            new NumberExpression(1)),
                                    new UnaryExpression(getUnaryFunction("ln"),
                                            new OperatorExpression(getOperator("+"),
                                                    new VariableExpression(Engine.var),
                                                    new NumberExpression(1)))),
                            new VariableExpression(Engine.var));
                }
                return super.getIntegral(x);
            }
        });
        map.put("neg", new UnaryFunction("neg", "neg("+x+") = -x")
        {
            @Override
            public Expression evaluate(Expression x) {
                return new NumberExpression(-x.valueOf());
            }
            @Override
            public String infix(Expression x){
                String xinfix = x.infix();
                if (x instanceof OperatorExpression &&
                        infixBracketOperatorsR.containsKey(((OperatorExpression) x).operator().toString())){
                    xinfix = "("+xinfix+")";
                }
                return "-"+xinfix;
            }
            @Override
            public String toTeX(Expression x){
                String xTeX = x.toTeX();
                if (x instanceof OperatorExpression &&
                        infixBracketOperatorsR.containsKey(((OperatorExpression) x).operator().toString())){
                    xTeX = "\\left("+xTeX+"\\right)";
                }
                return "-"+xTeX;
            }
            @Override
            public Expression getDerivative(Expression x){
                return new UnaryExpression(this, x.calculateDerivative());
            }
            @Override
            public Expression getIntegral(Expression x){
                return new UnaryExpression(this, x.calculateIntegral());
            }
            @Override
            public Expression simplify(Expression x){
                if (x instanceof UnaryExpression){
                    UnaryExpression ue = (UnaryExpression) x;
                    if (ue.function().toString().equals("neg")){
                        return ue.param().simplify();
                    }
                }
                return super.simplify(x);
            }
        });
        map.put("norm", new UnaryFunction("norm", "norm("+x+") calculates P(X≤"+x+") for X ~ Standard Normal Distribution")
        {
            @Override
            public Expression evaluate(Expression x) {
                return new NumberExpression(RandomVariables.standardNormalDistributionCDF(x.valueOf()));
            }
        });
        map.put("norminv", new UnaryFunction("norminv", "norminv("+x+") calculates P(X≤"+x+") for X ~ Standard Normal Distribution Inverse")
        {
            @Override
            public Expression evaluate(Expression x) {
                return new NumberExpression(RandomVariables.standardNormalDistributionCDFInverse(x.valueOf()));
            }
        });
        map.put("prime", new UnaryFunction("prime", "prime(x), determines whether x equals prime")
        {
            @Override
            public Expression evaluate(Expression x) {
                double n = x.valueOf();
                if (n % 1 == 0) return new NumberExpression(_Number_.isPrime((int) n));
                return new InvalidExpression("Invalid Argument Error:  prime expected integer");
            }
        });
        map.put("rad", new UnaryFunction("rad", "rad(x) = xʳ, x in angular radians")
        {
            @Override
            public Expression evaluate(Expression x) {
                return new NumberExpression(Math.toRadians(x.valueOf()));
            }
        });
        map.put("RCEF", new UnaryFunction("RCEF", "RCEF(M) gets the row reduced echelon form of the transpose of matrix M")
        {
            @Override
            public Expression evaluate(Expression x) {
                if (x instanceof MatrixExpression){
                    return new MatrixExpression(_Matrix_.rowReduce(((MatrixExpression) x).doubleMatrix().transpose()));
                }
                return new InvalidExpression("Invalid Argument Error:  RCEF expected matrix as input");
            }
        });
        map.put("RREF", new UnaryFunction("RREF", "RREF(M) gets the row reduced echelon form of matrix M")
        {
            @Override
            public Expression evaluate(Expression x) {
                if (x instanceof MatrixExpression){
                    return new MatrixExpression(_Matrix_.rowReduce(((MatrixExpression) x).doubleMatrix()));
                }
                return new InvalidExpression("Invalid Argument Error:  RREF expected matrix as input");
            }
            @Override
            public String toTeX(Expression x){
                if (x instanceof MatrixExpression){
                    return "\\operatorname{RREF}"+x.toTeX();
                }
                return super.toTeX(x);
            }
        });
        map.put("sec", new UnaryFunction("sec", "sec(x) equals the Secant function")
        {
            @Override
            public Expression evaluate(Expression x) {
                return new NumberExpression(Trig.sec(x.valueOf()));
            }
            @Override
            public Expression getDerivative(Expression x){
                return new OperatorExpression(getOperator("*"),
                        new OperatorExpression(getOperator("*"),
                                new UnaryExpression(getUnaryFunction("tan"), x),
                                new UnaryExpression(getUnaryFunction("sec"), x)),
                        x.calculateDerivative());
            }
            @Override
            public Expression getIntegral(Expression x){
                if (x instanceof VariableExpression) {
                    return new UnaryExpression(getUnaryFunction("ln"),
                            new OperatorExpression(getOperator("+"),
                                    new UnaryExpression(getUnaryFunction("tan"), x),
                                    new UnaryExpression(getUnaryFunction("sec"), x)));
                }
                return super.getIntegral(x);
            }
        });
        map.put("sech", new UnaryFunction("sech", "sech(x) equals the Hyperbolic Secant function")
        {
            @Override
            public Expression evaluate(Expression x) {
                return new NumberExpression(Trig.sech(x.valueOf()));
            }
            @Override
            public Expression getDerivative(Expression x){
                return new UnaryExpression(getUnaryFunction("neg"),
                        new OperatorExpression(getOperator("*"),
                                new OperatorExpression(getOperator("*"),
                                        new UnaryExpression(getUnaryFunction("tanh"), x),
                                        new UnaryExpression(getUnaryFunction("sech"), x)),
                                x.calculateDerivative()));
            }
            @Override
            public Expression getIntegral(Expression x){
                if (x instanceof VariableExpression) {
                    return new OperatorExpression(getOperator("*"),
                            new NumberExpression(2),
                            new UnaryExpression(getUnaryFunction("arctan"),
                                    new UnaryExpression(getUnaryFunction("tanh"),
                                            new OperatorExpression(getOperator("/"),
                                                    x, new NumberExpression(2)))));
                }
                return super.getIntegral(x);
            }
        });
        map.put("sin", new UnaryFunction("sin", "sin(x) equals the Sine function")
        {
            @Override
            public Expression evaluate(Expression x) {
                return new NumberExpression(Math.sin(x.valueOf()));
            }
            @Override
            public Expression getDerivative(Expression x){
                return new OperatorExpression(getOperator("*"),
                        new UnaryExpression(getUnaryFunction("cos"), x),
                        x.calculateDerivative());
            }
            @Override
            public Expression getIntegral(Expression x){
                if (x instanceof VariableExpression) {
                    return new UnaryExpression(getUnaryFunction("neg"),
                            new UnaryExpression(getUnaryFunction("cos"), x));
                }
                return super.getIntegral(x);
            }
        });
        map.put("sinh", new UnaryFunction("sinh", "sinh(x) equals the Hyperbolic Sine function")
        {
            @Override
            public Expression evaluate(Expression x) {
                return new NumberExpression(Math.sinh(x.valueOf()));
            }
            @Override
            public Expression getDerivative(Expression x){
                return new OperatorExpression(getOperator("*"),
                        new UnaryExpression(getUnaryFunction("cosh"), x),
                        x.calculateDerivative());
            }
            @Override
            public Expression getIntegral(Expression x){
                if (x instanceof VariableExpression) {
                    return new UnaryExpression(getUnaryFunction("cosh"), x);
                }
                return super.getIntegral(x);
            }
        });
        map.put("smfib", new UnaryFunction("smfib", "smfib(n) = fib(0)+fib(1)+⋯+fib(n)")
        {
            @Override
            public Expression evaluate(Expression x) {
                double n = x.valueOf();
                if (n % 1 == 0 && n >= 0 && n <= 1470)  return new NumberExpression(_Number_.getFibonnaci((int) (n+2))-1);
                return new InvalidExpression("Invalid Argument Error:  Expected n∈ℤ⁺, n∈[0, 1470]");
            }
        });
        map.put("tan", new UnaryFunction("tan", "tan(x) equals the Tangent function")
        {
            @Override
            public Expression evaluate(Expression x) {
                return new NumberExpression(Math.tan(x.valueOf()));
            }
            @Override
            public Expression getDerivative(Expression x){
                return new OperatorExpression(getOperator("*"),
                        new OperatorExpression(getOperator("^"),
                                new UnaryExpression(getUnaryFunction("sec"), x),
                                new NumberExpression(2)),
                        x.calculateDerivative());
            }
            @Override
            public Expression getIntegral(Expression x){
                if (x instanceof VariableExpression) {
                    return new UnaryExpression(getUnaryFunction("neg"),
                            new UnaryExpression(getUnaryFunction("ln"),
                                    new UnaryExpression(getUnaryFunction("abs"),
                                            new UnaryExpression(getUnaryFunction("cos"), x))));
                }
                return super.getIntegral(x);
            }
        });
        map.put("tanh", new UnaryFunction("tanh", "tanh(x) equals the Hyperbolic Tangent function")
        {
            @Override
            public Expression evaluate(Expression x) {
                return new NumberExpression(Math.tanh(x.valueOf()));
            }
            @Override
            public Expression getDerivative(Expression x){
                return new OperatorExpression(getOperator("*"),
                        new OperatorExpression(getOperator("^"),
                                new UnaryExpression(getUnaryFunction("sech"), x),
                                new NumberExpression(2)),
                        x.calculateDerivative());
            }
            @Override
            public Expression getIntegral(Expression x){
                if (x instanceof VariableExpression) {
                    return new UnaryExpression(getUnaryFunction("ln"),
                            new UnaryExpression(getUnaryFunction("cosh"), x));
                }
                return super.getIntegral(x);
            }
        });
        map.put("tobin", new UnaryFunction("tobin", "tobin(x) produces the binary form of x")
        {
            @Override
            public Expression evaluate(Expression x) {
                double n = x.valueOf();
                if (n % 1 == 0) return new StringExpression(_Number_.toBinary((long)n));
                return new InvalidExpression("Invalid Argument Error:  tobin expected integer");
            }
        });
        map.put("Var", new UnaryFunction("Var", "Var(X) calculates variance of X")
        {
            @Override
            public Expression evaluate(Expression x) {
                if (x instanceof MultiParamExpression){
                    MultiParamExpression mpe = (MultiParamExpression)x;
                    if (mpe.function() instanceof RandomVariable){
                        return ((RandomVariable)mpe.function()).getVariance(mpe.parameters());
                    }
                }
                return new InvalidExpression("Invalid Argument Error:  Exp expected a random variable as input");
            }
        });
        map.put("Γ", new UnaryFunction("Γ", "Γ(α) computes the gamma function at α"){
            @Override
            public Expression evaluate(Expression x) {
                return new NumberExpression(Special.gammaFunction(x.valueOf()));
            }
        });
        return map;
    }


    public static boolean isUnaryFunction(String item){
        return unaryFunctionMap.containsKey(item);
    }
    public static UnaryFunction getUnaryFunction(String item){
        return unaryFunctionMap.get(item);
    }
    public static boolean startsWithUnaryFunction(String item){
//        int max = Math.max(item.length(), UnaryFunction.maxStrLength);
//        for (int i = max; i >= UnaryFunction.minStrLength; i--){
//            if (isUnaryFunction(item.substring(0, i)))   return true;
//        }
        for (UnaryFunction function : unaryFunctionMap.values()){
            if (item.startsWith(function.toString())){
                return true;
            }
        }
        return false;
    }



}
