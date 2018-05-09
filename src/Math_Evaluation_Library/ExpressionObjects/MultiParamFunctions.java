package Math_Evaluation_Library.ExpressionObjects;

import Math_Evaluation_Library.Calculus.Integral;
import Math_Evaluation_Library.Calculus.Roots;
import Math_Evaluation_Library.Engine.Engine;
import Math_Evaluation_Library.Expressions.*;
import Math_Evaluation_Library.Geometry.Geometric;
import Math_Evaluation_Library.Miscellaneous.Mod;
import Math_Evaluation_Library.Miscellaneous._Random_;
import Math_Evaluation_Library.Objects._Number_;
import Math_Evaluation_Library.Search;
import Math_Evaluation_Library.Sort;
import Math_Evaluation_Library.Statistics.RandomVariables;
import Math_Evaluation_Library.Statistics.Stats;
import Math_Evaluation_Library.UnitConversion._UnitConversion_;

import java.util.HashMap;
import java.util.List;

import static Math_Evaluation_Library.Engine.Engine.*;
import static Math_Evaluation_Library.ExpressionObjects.Operators.getOperator;

/**
 * Created by Antonio on 2017-07-22.
 */
public class MultiParamFunctions{

//    public static void main (String[] args){
//        Sort.quicksort(multiParamFunctions);
//        for (MultiParamFunction m : multiParamFunctions){
//            System.out.println(m.getVar());
//        }
//    }

    static Operator subtract = getOperator("-");

    public static final HashMap<String, MultiParamFunction> multiParamMap = createMultiParamMap();

    public static HashMap<String, MultiParamFunction> createMultiParamMap() {
        HashMap<String, MultiParamFunction> map = new HashMap<>();
        map.put("Bin", new RandomVariable("Bin",     -1, "X ~ Bin(n, p); n trials, probability p; X ~ Binomial Distribution")
        {
            @Override public boolean isContinuous(){  return false;   };
            @Override
            public Expression evaluate(Expression[] parameters) {
                if (parameters.length == 3) return getPDF(parameters, parameters[2]);
                return new MultiParamExpression(this, parameters);
            }
            @Override
            public Expression getExpectedValue(Expression[] parameters) {
                //"Binexp(n, p) = n×p; n trials, probability p; calculates Expected Value for Binomial Distribution"
                if (parameters.length >= 2){
                    double n = parameters[0].valueOf();
                    double p = parameters[1].valueOf();
                    if (n>=0 && p>=0 && p<=1){
                        return new NumberExpression(RandomVariables.binomialDistribution_ExpectedValue((int)n, p));
                    }
                }
                return new StringExpression("Exp(X ~ Binomial(n, p)) = n×p");
            }
            @Override
            public Expression getVariance(Expression[] parameters) {
                //"Binvar(n, p) = n×p(1-p); n trials, probability p; calculates Variance for Binomial Distribution"
                if (parameters.length >= 2){
                    try{
                        double n = parameters[0].valueOf();
                        double p = parameters[1].valueOf();
                        if (n>=0 && p>=0 && p<=1){
                            return new NumberExpression(RandomVariables.binomialDistribution_Variance((int)n, p));
                        }
                    } catch(NumberFormatException e){}
                }
                return new StringExpression("Var(X ~ Binomial(n, p)) = n×p(1-p)");
            }
            @Override
            public Expression getPDF(Expression[] parameters, Expression xe) {
                //"Bin(n, p, "+x+"); n trials, probability p, "+x+" successes; P(X="+x+") for X ~ Binomial Distribution"
                if (parameters.length >= 2){
                    double n = parameters[0].valueOf();
                    double p = parameters[1].valueOf();
                    double x = xe.valueOf();
                    if (n>=0 && p>=0 && p<=1 && x>=0 && x<=n){
                        return new NumberExpression(RandomVariables.binomialDistribution((int)n, p, (int)x));
                    }
                }
                return new InvalidExpression("Invalid Arguments Error:  P(X=x), X ~ Binomial(n, p)");
            }
            @Override
            public Expression getCDF(Expression[] parameters, Expression xe) {
                //"Bin(n, p, "+x+"); n trials, probability p, "+x+" successes; P(X≤"+x+") for X ~ Binomial Distribution"
                if (parameters.length >= 2){
                    double n = parameters[0].valueOf();
                    double p = parameters[1].valueOf();
                    double x = xe.valueOf();
                    if (n>=0 && p>=0 && p<=1 && x>=0 && x<=n){
                        double cdf = 0;
                        for (int i = 0; i<=x; ++i){
                            cdf += RandomVariables.binomialDistribution((int)n, p, i);
                        }
                        return new NumberExpression(cdf);
                    }
                }
                return new InvalidExpression("Invalid Arguments Error:  P(X≤x), X ~ Binomial(n, p)");
            }

            @Override
            public Expression getPDF() {
                return new StringExpression("P(X=x) = (nCx)pˣ(1-p)ⁿ⁻ˣ; X ~ Binomial(n, p)");
            }
        });
        map.put("NB",  new RandomVariable("NB",      -1, "NB(k, p); k successes, probability p; X ~ Negative Binomial Distribution")
        {
            @Override public boolean isContinuous(){  return false;   };
            @Override
            public Expression evaluate(Expression[] parameters) {
                if (parameters.length == 3) return getPDF(parameters, parameters[2]);
                return new MultiParamExpression(this, parameters);
            }
            @Override
            public Expression getExpectedValue(Expression[] parameters) {
                //"NBexp(k, p) = k×(1-p)/p; k successes, probability p; calculates Expected Value for Negative Binomial Distribution"
                if (parameters.length >= 2){
                    double k = parameters[0].valueOf();
                    double p = parameters[1].valueOf();
                    if (k>=0 && p>=0 && p<=1){
                        return new NumberExpression(RandomVariables.negativeBinomialDistribution_ExpectedValue((int)k, p));
                    }
                }
                return new StringExpression("Exp(X ~ NegativeBinomial(k, p)) = k×(1-p)/p");
            }
            @Override
            public Expression getVariance(Expression[] parameters) {
                //"NBvar(k, p) = k×(1-p)/p²; k successes, probability p; calculates Variance for Negative Binomial Distribution"
                if (parameters.length >= 2){
                    double k = parameters[0].valueOf();
                    double p = parameters[1].valueOf();
                    if (k>=0 && p>=0 && p<=1){
                        return new NumberExpression(RandomVariables.negativeBinomialDistribution_Variance((int)k, p));
                    }
                }
                return new StringExpression("Var(X ~ NegativeBinomial(k, p)) = k×(1-p)/p²");
            }
            @Override
            public Expression getPDF(Expression[] parameters, Expression xe) {
                if (parameters.length >= 2){
                    double k = parameters[0].valueOf();
                    double p = parameters[1].valueOf();
                    double x = xe.valueOf();
                    if (k>=0 && p>=0 && p<=1 && x>=0){
                        return new NumberExpression(RandomVariables.negativeBinomialDistribution((int)k, p, (int)x));
                    }
                }
                return new InvalidExpression("Invalid Arguments Error:  P(X=x), X ~ NegativeBinomial(k, p)");
            }
            @Override
            public Expression getCDF(Expression[] parameters, Expression xe) {
                if (parameters.length >= 2){
                    double k = parameters[0].valueOf();
                    double p = parameters[1].valueOf();
                    double x = xe.valueOf();
                    if (k>=0 && p>=0 && p<=1 && x>=0){
                        double cdf = 0;
                        for (int i = 0; i<=x; ++i){
                            cdf += RandomVariables.negativeBinomialDistribution((int)k, p, i);
                        }
                        return new NumberExpression(cdf);
                    }
                }
                return new InvalidExpression("Invalid Arguments Error:  P(X≤x), X ~ NegativeBinomial(k, p)");
            }

            @Override
            public Expression getPDF() {
                return new StringExpression("P(X=x) = ((x+k-1)Cx)pᵏ(1-p)ˣ; X ~ NegativeBinomial(k, p)");
            }
        });
        map.put("ND",  new RandomVariable("ND",      -1, "ND(μ, σ²); mean μ, variance σ²; X ~ Normal Distribution")
        {
            @Override public boolean isContinuous(){  return true;   };
            @Override
            public Expression evaluate(Expression[] parameters) {
                if (parameters.length == 1){
                    return getCDF(parameters, parameters[0]);
                }
                else if (parameters.length == 3){
                    return getCDF(parameters, parameters[2]);
                }
                return new MultiParamExpression(this, parameters);
            }

            @Override
            public Expression getExpectedValue(Expression[] parameters) {
                if (parameters.length >= 2)    return new NumberExpression(parameters[0].valueOf());
                return new StringExpression("Exp(X ~ NormalDistribution(μ, σ²)) = μ");
            }

            @Override
            public Expression getVariance(Expression[] parameters) {
                if (parameters.length >= 2)    return new NumberExpression(parameters[1].valueOf());
                return new StringExpression("Var(X ~ NormalDistribution(μ, σ²)) = σ²");
            }

            @Override
            public Expression getPDF(Expression[] parameters, Expression xe) {
                //"Npdf(μ, σ², "+x+"); mean μ, variance σ²; P(X="+x+") for X ~ Normal Distribution"
                double mean = 0, var = 1, x = xe.valueOf();
                if (parameters.length >= 2){
                    mean = parameters[0].valueOf();
                    var = parameters[1].valueOf();
                }
                if (Math.abs(mean) >= 0 && var>=0){
                    return new NumberExpression(RandomVariables.normalDistribution_pdf(mean, var, x));
                }
                return new InvalidExpression("Invalid Arguments Error:  P(X=x), X ~ NormalDistribution(μ, σ²)");
            }

            @Override
            public Expression getCDF(Expression[] parameters, Expression xe) {
                //"Ncdf(μ, σ², "+x+"); mean μ, variance σ²; P(X≤"+x+") for X ~ Normal Distribution"
                double mean = 0, var = 1, x = xe.valueOf();
                if (parameters.length >= 2){
                    mean = parameters[0].valueOf();
                    var = parameters[1].valueOf();
                }
                if (Math.abs(mean) >= 0 && var>=0){
                    return new NumberExpression(RandomVariables.normalDistribution_cdf(mean, var, x));
                }
                return new InvalidExpression("Invalid Arguments Error:  P(X≤x), X ~ NormalDistribution(μ, σ²)");
            }

            @Override
            public Expression getPDF() {
                return new StringExpression("P(X=x) = e^(-(x-μ)²/(2σ²))/√(2πσ²); X ~ NormalDistribution(μ, σ²)");
            }
        });
        map.put("avg",  new MultiParamFunction("avg",     -1, "avg(a₁,…,aₙ) calculates the average of a₁,…,aₙ")
        {
            @Override
            public Expression evaluate(Expression[] parameters) {
                if (parameters.length > 0){
                    double sum = 0;
                    for (Expression e : parameters){
                        sum += e.valueOf();
                    }
                    sum /= parameters.length;
                    return new NumberExpression(sum);
                }
                return new InvalidExpression("Invalid Arguments Error:  avg(a₁,…,aₙ)");
            }
        });
        map.put("c_law",  new MultiParamFunction("c_law",    3, "c_law(b, c, θ) calculates the cosine law:  √(b²+c²-2×b×c×cos(θ))")
        {
            @Override
            public Expression evaluate(Expression[] parameters) {
                if (parameters.length == 3){
                    double b = parameters[0].valueOf();
                    double c = parameters[1].valueOf();
                    double theta = parameters[2].valueOf();
                    if (b > 0 && c > 0 && Math.abs(theta) > 0) {
                        return new NumberExpression(Math.sqrt(b * b + c * c - 2 * b * c * Math.cos(theta)));
                    }
                }
                return new InvalidExpression("Invalid Arguments Error:  c_law(b, c, θ)");
            }
        });
        map.put("calc",  new MultiParamFunction("calc",     2, "calc(ƒ("+x+"), a) calculates ƒ(a)")
        {
            @Override
            public Expression evaluate(Expression[] parameters) {
                if (parameters.length == 2){
                    return parameters[0].evaluate(parameters[1].valueOf());
                }
                return new InvalidExpression("Invalid Arguments Error:  calc(ƒ("+x+"), a)");
            }
        });
        map.put("calcab",  new MultiParamFunction("calcab",   3, "calcab(ƒ("+x+"), a, b) calculates ƒ("+x+", "+Engine.varOp+") if ƒ has parameter "+Engine.varOp+", else ƒ(b)-ƒ(a)")
        {
            @Override
            public Expression evaluate(Expression[] parameters) {
                if (parameters.length == 3){
                    if (parameters[0].containsVar(Engine.varOp)){
                        parameters[0].set(Engine.var, parameters[1]);
                        parameters[0].set(Engine.varOp, parameters[2]);
                        Expression e = parameters[0].evaluate();
                        parameters[0].unset(Engine.var);
                        parameters[0].unset(Engine.varOp);
                        return e;
                    }
                    Expression a = parameters[0].evaluate(parameters[1]);
                    Expression b = parameters[0].evaluate(parameters[2]);
                    return new OperatorExpression(subtract, b, a).evaluate();
                }
                return new InvalidExpression("Invalid Arguments Error:  calcab(ƒ("+x+"), a, b)");
            }
        });
        map.put("cdf",  new MultiParamFunction("cdf",   -1, "cdf(X, "+x+") calculates P(X≤"+x+") for a random variable X")
        {
            @Override
            public Expression evaluate(Expression[] parameters) {
                if (parameters[0] instanceof MultiParamExpression) {
                    MultiParamExpression mpe = (MultiParamExpression)parameters[0];
                    if (parameters.length >= 2) {
                        if (mpe.function() instanceof RandomVariable) {
                            if (parameters.length >= 3){
                                return new OperatorExpression("-",
                                        ((RandomVariable) mpe.function()).getCDF(mpe.parameters(), parameters[2]),
                                        ((RandomVariable) mpe.function()).getCDF(mpe.parameters(), parameters[1]));
                            }
                            return ((RandomVariable) mpe.function()).getCDF(mpe.parameters(), parameters[1]);
                        }
                    }
                    return ((RandomVariable) mpe.function()).getCDF();
                }
                return new InvalidExpression("Invalid Argument Error:  cdf expected a random variable as input");
            }
        });
//      map.put("dxn", new MultiParamFunction("dxn",      3, "dxn(ƒ, "+x+", n) calculates the value of the nth derivative of ƒ at "+x)
//            {
//                @Override
//                public Expression evaluate(Expression[] parameters) {
//                    if (parameters.length == 3){
//                        String fx = parameters[0];
//                        double x = parameters[1].valueOf();
//                        int n = Integer.parseInt(parameters[2].trim());
//                        String[] derivatives = new String[n];
//                        derivatives[0] = Derivative.calculate(fx);
//                        for (int b = 1; b<derivatives.length; b++){
//                            derivatives[b] = Derivative.calculate(derivatives[b-1]);
//                        }
//                        String finalDerivative = derivatives[derivatives.length-1];
//                        if (finalDerivative.charAt(finalDerivative.length()-1) != '\''){
//                            return _Number_.format(Engine.evaluate(derivatives[derivatives.length-1], x));
//                        }
//                    }
//                    return INVALID;
//                }
//            });
        map.put("frbin", new MultiParamFunction("frbin", 1, "frbin(x) transforms a binary string into a decimal value")
        {
            @Override
            public Expression evaluate(Expression[] parameters) {
                long num = _Number_.fromBinary(parameters[0].evaluate().toString());
                if (num != -1){
                    return new NumberExpression(num, false);
                }
                return new InvalidExpression("Invalid Argument Error:  frbin expected integer");
            }

            @Override
            public Expression[] convert(String[] parameters) {
                char[] array = parameters[0].toCharArray();
                for (char c : array){
                    if (c != '0' && c != '1'){
                        return new Expression[]{toExpression(parameters[0])};
                    }
                }
                return new Expression[]{new StringExpression(parameters[0])};
            }
        });
        map.put("frb₁₆", new MultiParamFunction("frhex", 1, "frhex(x) transforms a hex string into a decimal value")
        {
            @Override
            public Expression evaluate(Expression[] parameters) {
                try{
                    return new NumberExpression(Long.parseLong(parameters[0].evaluate().toString(), 16), false);
                } catch (NumberFormatException ignored){}
                return new InvalidExpression("Invalid Argument Error:  invalid hexadecimal number - "+parameters[0].evaluate().toString());
            }

            char[] hexArray = "0123456789abcdef".toCharArray();
            @Override
            public Expression[] convert(String[] parameters) {
                char[] array = parameters[0].trim().toLowerCase().toCharArray();
                for (char c : array){
                    if (Search.linearSearch(hexArray, c) == -1){
                        return new Expression[]{toExpression(parameters[0])};
                    }
                }
                return new Expression[]{new StringExpression(parameters[0])};
            }
        });
        map.put("tob₁₆", new MultiParamFunction("tohex", 1, "tohex(x) produces the hex form of x")
        {
            @Override
            public Expression evaluate(Expression[] parameters) {
                double num = parameters[0].valueOf();
                if (num%1 == 0){
                    return new StringExpression(Long.toHexString((long)num));
                }
                return new InvalidExpression("Invalid Argument Error:  tohex expected integer");
            }
        });
        map.put("elasd",  new MultiParamFunction("elasd",    4, "elasd(q₁, q₂, p₁, p₂) calculates the elasticity of demand:  ((q₂-q₁)/(q₂+q₁))/((p₂-p₁)/(p₂+p₁))")
        {
            @Override
            public Expression evaluate(Expression[] parameters) {
                if (parameters.length == 4){
                    double q1 = parameters[0].valueOf();
                    double q2 = parameters[1].valueOf();
                    double p1 = parameters[2].valueOf();
                    double p2 = parameters[3].valueOf();
                    return new NumberExpression(((q2 - q1) / (q2 + q1)) / ((p2 - p1) / (p2 + p1)));
                }
                return new InvalidExpression("Invalid Arguments Error:  elasd(q₁, q₂, p₁, p₂)");
            }
        });
        map.put("exprv",  new RandomVariable("exprv",       -1, "exprv(θ); X ~ Exponential Distribution")
        {
            @Override public boolean isContinuous(){  return true;   };
            @Override
            public Expression evaluate(Expression[] parameters) {
                if (parameters.length == 2) return getCDF(parameters, parameters[1]);
                return new MultiParamExpression(this, parameters);
            }
            @Override
            public Expression getExpectedValue(Expression[] parameters) {
                //"expmean(θ) = θ; calculates Expected Value for Exponential Distribution"
                if (parameters.length >= 1){
                    double theta = parameters[0].valueOf();
                    if (theta>0){
                        return new NumberExpression(RandomVariables.exponentialDistribution_ExpectedValue(theta));
                    }
                }
                return new InvalidExpression("Exp(X ~ Exponential(θ)) = θ");
            }
            @Override
            public Expression getVariance(Expression[] parameters) {
                //"expvar(θ) = θ²; calculates Variance for Exponential Distribution"
                if (parameters.length >= 1){
                    double theta = parameters[0].valueOf();
                    if (theta>0){
                        return new NumberExpression(RandomVariables.exponentialDistribution_Variance(theta));
                    }
                }
                return new InvalidExpression("Var(X ~ Exponential(θ)) = θ²");
            }
            @Override
            public Expression getPDF(Expression[] parameters, Expression xe) {
                //"exppdf(θ, "+x+"); P(X="+x+") for X ~ Exponential Distribution"
                if (parameters.length >= 1){
                    double theta = parameters[0].valueOf();
                    double x = xe.valueOf();
                    if (theta>0 && x>=0){
                        return new NumberExpression(RandomVariables.exponentialDistribution(theta, x));
                    }
                }
                return new InvalidExpression("Invalid Arguments Error:  P(X=x), X ~ Exponential(θ)");
            }
            @Override
            public Expression getCDF(Expression[] parameters, Expression xe) {
                //"expcdf",   2, "expcdf(θ, "+x+"); P(X≤"+x+") for X ~ Exponential Distribution"
                if (parameters.length >= 1){
                    double theta = parameters[0].valueOf();
                    double x = xe.valueOf();
                    if (theta>0 && x>=0){
                        return new NumberExpression(RandomVariables.exponentialDistributionCDF(theta, x));
                    }
                }
                return new InvalidExpression("Invalid Arguments Error:  P(X≤x), X ~ Exponential(θ)");
            }
            @Override
            public Expression getCDF() {
                return new StringExpression("P(X≤x) = 1-e^(-x/θ) for X ~ Exponential(θ)");
            }

            @Override
            public Expression getPDF() {
                return new StringExpression("P(X=x) = e^(-x/θ)/θ; X ~ Exponential(θ)");
            }
        });
        map.put("gcd",  new MultiParamFunction("gcd",     -1, "gcd(a₁,…,aₙ) calculates the greatest common denominator of a₁,…,aₙ")
        {
            @Override
            public Expression evaluate(Expression[] parameters) {
                if (parameters.length == 1)    return parameters[0];
                if (parameters.length > 1){
                    double temp1 = parameters[0].valueOf();
                    if (temp1%1 == 0){
                        double gcd = temp1;
                        for (int b = 1; b<parameters.length; b++){
                            double temp = parameters[b].valueOf();
                            if (temp%1 == 0){
                                gcd = Mod.gcd(gcd, temp);
                            }
                            else{
                                return new InvalidExpression("Invalid Argument Error:  GCD requires integer arguments");
                            }
                        }
                        return new NumberExpression(gcd);
                    }
                }
                return new InvalidExpression("Invalid Argument Error:  GCD requires integer arguments");
            }
        });
        map.put("geo",  new RandomVariable("geo",         -1, "geo(p); X ~ Geometric Distribution")
        {
            @Override public boolean isContinuous(){  return false;   };
            @Override
            public Expression evaluate(Expression[] parameters) {
                if (parameters.length == 2) return getPDF(parameters, parameters[1]);
                return new MultiParamExpression(this, parameters);
            }
            @Override
            public Expression getExpectedValue(Expression[] parameters) {
                //"geoexp(p) = (1-p)/p; probability p; calculates Expected Value for Geometric Distribution"
                if (parameters.length >= 1){
                    double p = parameters[0].valueOf();
                    if (p>=0 && p<=1){
                        return new NumberExpression(RandomVariables.geometricDistribution_ExpectedValue(p));
                    }
                }
                return new StringExpression("Exp(X ~ Geometric(p)) = (1-p)/p");
            }
            @Override
            public Expression getVariance(Expression[] parameters) {
                //"geovar(p) = (1-p)/p²; probability p; calculates Variance for Geometric Distribution"
                if (parameters.length >= 1){
                    double p = parameters[0].valueOf();
                    if (p>=0 && p<=1){
                        return new NumberExpression(RandomVariables.geometricDistribution_Variance(p));
                    }
                }
                return new StringExpression("Var(X ~ Geometric(p)) = (1-p)/p²");
            }
            @Override
            public Expression getPDF(Expression[] parameters, Expression xe) {
                //"geo(p, "+x+"); probability p, "+x+" failures; P(X="+x+") for X ~ Geometric Distribution"
                if (parameters.length >= 2){
                    double p = parameters[0].valueOf();
                    double x = xe.valueOf();
                    if (p>=0 && p<=1 && x%1 == 0 && x>=0){
                        return new NumberExpression(RandomVariables.geometricDistribution(p, (int)x));
                    }
                }
                return new InvalidExpression("Invalid Arguments Error:  P(X=x), X ~ Geometric(p)");
            }
            @Override
            public Expression getCDF(Expression[] parameters, Expression xe) {
                //"expcdf",   2, "expcdf(θ, "+x+"); P(X≤"+x+") for X ~ Exponential Distribution"
                if (parameters.length >= 2){
                    double p = parameters[0].valueOf();
                    double x = xe.valueOf();
                    if (p>=0 && p<=1 && x%1 == 0 && x>=0){
                        double cdf = 0;
                        for (int i = 0; i<=x; ++i){
                            cdf += RandomVariables.geometricDistribution(p, i);
                        }
                        return new NumberExpression(cdf);
                    }
                }
                return new InvalidExpression("Invalid Arguments Error:  P(X≤x), X ~ Geometric(p)");
            }
            @Override
            public Expression getPDF() {
                return new StringExpression("P(X=x) = p(1-p)ˣ; X ~ Geometric(p)");
            }
        });
        map.put("heron",  new MultiParamFunction("heron",   -1, "heron(A, B, C) calculates the area of a triangle of side lengths A, B, C. heron("+x+"₁, y₁, "+x+"₂, y₂, "+x+"₃, y₃) calculates the area of a triangle with points (x₁, y₁), (x₂, y₂), (x₃, y₃)")
        {
            @Override
            public Expression evaluate(Expression[] parameters) {
                if (parameters.length == 3){
                    double A = parameters[0].valueOf();
                    double B = parameters[1].valueOf();
                    double C = parameters[2].valueOf();
                    double s = (A+B+C) / 2;
                    return new NumberExpression(Math.sqrt(s*(s-A)*(s-B)*(s-C)));
                }
                else if (parameters.length == 6){
                    double x1 = parameters[0].valueOf();
                    double y1 = parameters[1].valueOf();
                    double x2 = parameters[2].valueOf();
                    double y2 = parameters[3].valueOf();
                    double x3 = parameters[4].valueOf();
                    double y3 = parameters[5].valueOf();
                    double A = Geometric.pointDistance(x1, y1, x2, y2);
                    double B = Geometric.pointDistance(x1, y1, x3, y3);
                    double C = Geometric.pointDistance(x2, y2, x3, y3);
                    double s = (A+B+C) / 2;
                    return new NumberExpression(Math.sqrt(s*(s-A)*(s-B)*(s-C)));
                }
                return new InvalidExpression("Invalid Number of Arguments:  heron");
            }
        });
        map.put("hyp",  new RandomVariable("hyp",         -1, "hyp(N, r, n); X ~ Hypergeometric Distribution")
        {
            @Override public boolean isContinuous(){  return false;   };
            @Override
            public Expression evaluate(Expression[] parameters) {
                if (parameters.length == 4) return getPDF(parameters, parameters[3]);
                return new MultiParamExpression(this, parameters);
            }
            @Override
            public Expression getExpectedValue(Expression[] parameters) {
                //"hypexp(N, r, n) = n×r/N; N objects, r ways to succeed, n trials; calculates Expected Value of the Hypergeometric Distribution"
                if (parameters.length >= 3){
                    double N = parameters[0].valueOf();
                    double r = parameters[1].valueOf();
                    double n = parameters[2].valueOf();
                    if (N%1 == 0 && r%1 == 0 && n%1 == 0){
                        return new NumberExpression(RandomVariables.hyperGeometricDistribution_ExpectedValue(N, (int)r, (int)n));
                    }
                }
                return new StringExpression("Exp(X ~ Hypergeometric(N, r, n)) = n×r/N");
            }
            @Override
            public Expression getVariance(Expression[] parameters) {
                //"hypvar(N, r, n) = (n×r×(N-r)×(N-n))/(N²×(N-1)); N objects, r ways to succeed, n trials; calculates Variance of the Hypergeometric Distribution"
                if (parameters.length >= 3){
                    double N = parameters[0].valueOf();
                    double r = parameters[1].valueOf();
                    double n = parameters[2].valueOf();
                    if (N%1 == 0 && r%1 == 0 && n%1 == 0){
                        return new NumberExpression(RandomVariables.hyperGeometricDistribution_Variance(N, (int)r, (int)n));
                    }
                }
                return new StringExpression("Var(X ~ Hypergeometric(N, r, n)) = (n×r×(N-r)×(N-n))/(N²×(N-1))");
            }
            @Override
            public Expression getPDF(Expression[] parameters, Expression xe) {
                //"hyp(N, r, n, "+x+"); N objects, r ways to succeed, n trials, "+x+" successes; P(X="+x+") for X ~ Hypergeometric Distribution"
                if (parameters.length >= 3){
                    double N = parameters[0].valueOf();
                    double r = parameters[1].valueOf();
                    double n = parameters[2].valueOf();
                    double x = xe.valueOf();
                    if (N%1 == 0 && r%1 == 0 && n%1 == 0 && x%1 == 0){
                        return new NumberExpression(RandomVariables.hyperGeometricDistribution((int)N, (int)r, (int)n, (int)x));
                    }
                }
                return new InvalidExpression("Invalid Arguments Error:  P(X=x), X ~ Hypergeometric(N, r, n)");
            }
            @Override
            public Expression getCDF(Expression[] parameters, Expression xe) {
                //"expcdf",   2, "expcdf(θ, "+x+"); P(X≤"+x+") for X ~ Exponential Distribution"
                if (parameters.length >= 3){
                    double N = parameters[0].valueOf();
                    double r = parameters[1].valueOf();
                    double n = parameters[2].valueOf();
                    double x = xe.valueOf();
                    if (N%1 == 0 && r%1 == 0 && n%1 == 0 && x%1 == 0){
                        double cdf = 0;
                        for (int i = 0; i<=x; ++i){
                            cdf += RandomVariables.hyperGeometricDistribution((int)N, (int)r, (int)n, i);
                        }
                        return new NumberExpression(cdf);
                    }
                }
                return new InvalidExpression("Invalid Arguments Error:  P(X≤x), X ~ Hypergeometric(N, r, n)");
            }
            @Override
            public Expression getPDF() {
                return new StringExpression("P(X=x) = (rCx)((N-r)C(n-x))/(NCn); X ~ Hypergeometric(N, r, n)");
            }
        });
        map.put("lcm",  new MultiParamFunction("lcm",     -1, "lcm(a₁,…,aₙ) calculates the lowest common multiple of a₁,…,aₙ")
        {
            @Override
            public Expression evaluate(Expression[] parameters) {
                if (parameters.length == 1){
                    return parameters[0];
                }
                else if (parameters.length > 1){
                    double temp1 = parameters[0].valueOf();
                    if (temp1%1 == 0){
                        double lcm = (int)temp1;
                        for (int b = 1; b<parameters.length; b++){
                            double temp = parameters[b].valueOf();
                            if (temp%1 == 0){
                                lcm = Mod.lcm(lcm, temp);
                            }
                            else{
                                return new InvalidExpression("Invalid Argument Error:  LCM requires integer arguments");
                            }
                        }
                        return new NumberExpression(lcm);
                    }
                }
                return new InvalidExpression("Invalid Argument Error:  LCM requires integer arguments");
            }
        });
        map.put("logab",  new MultiParamFunction("logab",   -1, "logab(a) calculates log₁₀(a). logab(a, b) calculates logₐ(b)")
        {
            @Override
            public Expression evaluate(Expression[] parameters) {
                if (parameters.length == 1){
                    return new NumberExpression(Math.log10(parameters[0].valueOf()));
                }
                else if (parameters.length == 2){
                    return new NumberExpression(Math.log(parameters[0].valueOf())/Math.log(parameters[1].valueOf()));
                }
                return new InvalidExpression("Invalid Number of Argument Error:  logab(a, b)");
            }
        });
        map.put("max",  new MultiParamFunction("max",     -1, "max(a₁,…,aₙ) calculates the highest of a₁,…,aₙ")
        {
            @Override
            public Expression evaluate(Expression[] parameters) {
                if (parameters.length > 0){
                    double largest = parameters[0].valueOf();
                    for (int b = 1; b<parameters.length; b++){
                        largest = Math.max(largest, parameters[b].valueOf());
                    }
                    return new NumberExpression(largest);
                }
                return new InvalidExpression("Invalid Number of Argument Error:  max(a₁,…,aₙ)");
            }
        });
        map.put("min",  new MultiParamFunction("min",     -1, "min(a₁,…,aₙ) calculates the lowest of a₁,…,aₙ")
        {
            @Override
            public Expression evaluate(Expression[] parameters) {
                if (parameters.length > 0){
                    double smallest = parameters[0].valueOf();
                    for (int b = 1; b<parameters.length; b++){
                        smallest = Math.min(smallest, parameters[b].valueOf());
                    }
                    return new NumberExpression(smallest);
                }
                return new InvalidExpression("Invalid Number of Argument Error:  min(a₁,…,aₙ)");
            }
        });
        map.put("mnd",  new MultiParamFunction("mnd",     -1, "mnd(n,p₁,…,pₙ,"+x+"₁,…,"+x+"ₙ); n trials, probabilities p; calculates P(x₁,…,xₙ) for (x₁,…,xₙ) ~ Multinomial Distribution")
        {
            @Override
            public Expression evaluate(Expression[] parameters) {
                if (parameters.length%2 != 0 && parameters.length >= 3){
                    double v = parameters[0].valueOf();
                    if (v%1 != 0)   return new InvalidExpression("Invalid Number of trials:  "+v);
                    int n = (int)v;
                    double[] p = new double[(parameters.length-1)/2];
                    int[] x = new int[p.length];
                    for (int i = 0; i<p.length; i++){
                        p[i] = parameters[i+1].valueOf();
                        if (p[i] > 1 || p[i] < 0)   return new InvalidExpression("Invalid probability:  "+p[i]);
                    }
                    int sum = 0;
                    for (int i = 0; i<x.length; i++){
                        v = parameters[i+p.length+1].valueOf();
                        if (v%1 != 0)   return new InvalidExpression("Invalid "+x+"ₙ:  "+x[i]);
                        x[i] = (int)v;
                        sum += x[i];
                    }
                    if (sum != n)   return new InvalidExpression("Inconsistent number of trials:  "+n+" != "+sum);
                    return new NumberExpression(RandomVariables.multinomialDistribution(n, p, x));
                }
                return new InvalidExpression("Invalid Number of Argument Error:   mnd,  Multinomial Distribution");
            }
        });
        map.put("newton",  new MultiParamFunction("newton",   2, "newton(ƒ, a) calculates the newton approximation of the root of ƒ starting from "+x+"=a")
        {
            @Override
            public Expression evaluate(Expression[] parameters) {
                if (parameters.length == 2){
                    return new NumberExpression(Roots.NewtonsMethod(parameters[0], parameters[1].valueOf()));
                }
                return new InvalidExpression("Invalid Number of Arguments Error:  newton(ƒ, a)");
            }
        });
        map.put("pdf",  new MultiParamFunction("pdf",   -1, "pdf(X, "+x+") calculates P(X="+x+") for a random variable X")
        {
            @Override
            public Expression evaluate(Expression[] parameters) {
                if (parameters.length >= 1) {
                    if (parameters[0] instanceof MultiParamExpression) {
                        MultiParamExpression mpe = (MultiParamExpression)parameters[0];
                        if (mpe.function() instanceof RandomVariable) {
                            if (parameters.length >= 2) {
                                return ((RandomVariable) mpe.function()).getPDF(mpe.parameters(), parameters[1]);
                            }
                            return ((RandomVariable) mpe.function()).getPDF();
                        }
                    }
                }
                return new InvalidExpression("Invalid Argument Error:  cdf expected a random variable as input");
            }
        });
        map.put("poi",  new RandomVariable("poi",         -1, "poi(λ); X ~ Poisson Distribution")
        {
            @Override public boolean isContinuous(){  return false;   };
            @Override
            public Expression evaluate(Expression[] parameters) {
                if (parameters.length == 2) return getPDF(parameters, parameters[1]);
                return new MultiParamExpression(this, parameters);
            }
            @Override
            public Expression getExpectedValue(Expression[] parameters) {
                //"poiexp(λ) = λ; λ=np for n trials, probability p;  calculates Expected Value of the Poisson Distribution"
                if (parameters.length >= 1){
                    double lambda = parameters[0].valueOf();
                    if (lambda > 0) {
                        return new NumberExpression(RandomVariables.poissonDistribution_ExpectedValue(lambda));
                    }
                }
                return new StringExpression("Exp(X ~ Poisson(λ)) = λ");
            }
            @Override
            public Expression getVariance(Expression[] parameters) {
                //"poivar(λ) = λ; λ=np for n trials, probability p;  calculates Variance of the Poisson Distribution"
                if (parameters.length >= 1){
                    double lambda = parameters[0].valueOf();
                    if (lambda > 0) {
                        return new NumberExpression(RandomVariables.poissonDistribution_Variance(lambda));
                    }
                }
                return new StringExpression("Var(X ~ Poisson(λ)) = λ");
            }
            @Override
            public Expression getPDF(Expression[] parameters, Expression xe) {
                //"poi(λ, "+x+"); λ=np for n trials, probability p;  P(X="+x+") for X ~ Poisson Distribution"
                if (parameters.length >= 1){
                    double lambda = parameters[0].valueOf();
                    double x = xe.valueOf();
                    if (lambda > 0 && x > 0) {
                        return new NumberExpression(RandomVariables.poissonDistribution(lambda, (int) x));
                    }
                }
                return new InvalidExpression("Invalid Arguments Error:  P(X=x), X ~ Poisson(λ)");
            }
            @Override
            public Expression getCDF(Expression[] parameters, Expression xe) {
                //"expcdf",   2, "expcdf(θ, "+x+"); P(X≤"+x+") for X ~ Exponential Distribution"
                if (parameters.length >= 2){
                    double p = parameters[0].valueOf();
                    double x = xe.valueOf();
                    if (p>=0 && p<=1 && x%1 == 0 && x>=0){
                        double cdf = 0;
                        for (int i = 0; i<=x; ++i){
                            cdf += RandomVariables.geometricDistribution(p, i);
                        }
                        return new NumberExpression(cdf);
                    }
                }
                return new InvalidExpression("Invalid Arguments Error:  P(X≤x), X ~ Poisson(λ)");
            }
            @Override
            public Expression getPDF() {
                return new StringExpression("P(X=x) = e^(-λ)*λˣ/x!; X ~ Poisson(λ)");
            }
        });
//        map.put("prop",  new MultiParamFunction("prop",    -1, "prop(s, t₁,…,tₙ) calculates the truth valuation of s with truth values t₁,…,tₙ")
//            {
//                @Override
//                public Expression evaluate(Expression[] parameters) {
//                    if (parameters.length > 0){
//                        boolean[] values = new boolean[parameters.length-1];
//                        for (int i = 0; i<values.length; i++){
//                            values[i] = parameters[i+1].equalsIgnoreCase("T") || parameters[i+1].equalsIgnoreCase("True");
//                        }
//                        return Propositional.valuate(parameters[0], values);
//                    }
//                    return INVALID;
//                }
//            });
        map.put("randint",  new MultiParamFunction("randint",  2, "randint(l, h) calculates a random integer ∈ [l, h]")
        {
            @Override
            public Expression evaluate(Expression[] parameters) {
                if (parameters.length == 2){
                    double low = parameters[0].valueOf();
                    double high = parameters[1].valueOf();
                    return new NumberExpression(_Random_.randomint((int)low, (int)high));
                }
                return new InvalidExpression("Invalid Number of Arguments:  randint(l, h)");
            }
        });
        map.put("random",  new MultiParamFunction("random",   2, "random(l, h) calculates a random real number ∈ [l, h]")
        {
            @Override
            public Expression evaluate(Expression[] parameters) {
                if (parameters.length == 2){
                    double low = parameters[0].valueOf();
                    double high = parameters[1].valueOf();
                    return new NumberExpression(_Random_.random(low, high));
                }
                return new InvalidExpression("Invalid Number of Arguments:  random(l, h)");
            }
        });
        map.put("randq",  new MultiParamFunction("randq",    2, "randq(l, h) calculates a random rational of the form p/q where p,q ∈ [l, h]")
        {
            @Override
            public Expression evaluate(Expression[] parameters) {
                if (parameters.length == 2){
                    double low = parameters[0].valueOf();
                    double high = parameters[1].valueOf();
                    return new NumberExpression(_Random_.randomRational((int)low, (int)high));
                }
                return new InvalidExpression("Invalid Number of Arguments:  randq(l, h)");

            }
        });
        map.put("riemann",  new MultiParamFunction("riemann", -1, "riemann(ƒ, a, b, n, {l, r, m}) calculates the {l, r, m} riemann sum of fƒ from a to b with n intervals")
        {
            @Override
            public Expression evaluate(Expression[] parameters) {
                if (parameters.length == 4 || parameters.length == 5){
                    int start = (int)parameters[1].valueOf();
                    int end = (int)parameters[2].valueOf();
                    int n = (int)parameters[3].valueOf();
                    String riemann = parameters.length == 5 ? parameters[4].toString() : "m";
                    double h = (end-start)/(double)n;

                    double sum = 0;
                    if (riemann.equals("l") || riemann.equals("r")){
                        int low = 0, high = n;
                        if (riemann.equals("r")){    low = 1; high = n+1;   }
                        for (int b = low; b < high; b++) {
                            sum += parameters[0].valueAt(start+h*b);
                        }
                    }
                    else {
                        for (int b = 0; b <= n-1; b++) {
                            sum += parameters[0].valueAt(start+h*(2*b+1)/2.0);
                        }
                    }
                    return new NumberExpression(sum*h);
                }
                return new InvalidExpression("Invalid Number of Arguments:  riemann(ƒ, a, b, n, {l, r, m})");
            }
            @Override
            public Expression[] convert(String[] parameters){
                if (parameters.length == 5){
                    return new Expression[]{
                            toExpression(parameters[0]), toExpression(parameters[1]),
                            toExpression(parameters[2]), toExpression(parameters[3]),
                            new StringExpression(parameters[4])
                    };
                }
                return super.convert(parameters);
            }
        });
        map.put("stndv",  new MultiParamFunction("stndv",   -1, "stndv(a₁,…,aₙ) calculates the standard deviation of a₁,…,aₙ")
        {
            @Override
            public Expression evaluate(Expression[] parameters) {
                if (parameters.length > 1){
                    double[] data = new double[parameters.length];
                    for (int b = 0; b<data.length; b++){
                        data[b] = parameters[b].valueOf();
                    }
                    return new NumberExpression(Stats.stnDev(data));
                }
                return new InvalidExpression("Invalid Number of Arguments:  stndv(a₁,…,aₙ)");
            }
        });
        map.put("strln", new MultiParamFunction("strln", 1, "strln(str) calculates the length of string str") {
            @Override
            public Expression evaluate(Expression[] parameters) {
                return new NumberExpression(parameters[0].toString().length());
            }

            @Override
            public Expression[] convert(String[] parameters) {
                return new Expression[]{new StringExpression(parameters[0])};
            }
        });
        map.put("unif",  new MultiParamFunction("unif",     3, "unif(a, b, x) calculates the uniform distribution from a to b at x")
        {
            @Override
            public Expression evaluate(Expression[] parameters) {
                if (parameters.length == 3){
                    int a = (int)parameters[0].valueOf();
                    int b = (int)parameters[1].valueOf();
                    int x = (int)parameters[2].valueOf();
                    return new NumberExpression(RandomVariables.uniformDistribution(a, b, x));
                }
                return new InvalidExpression("Invalid Number of Argument Error:  unif(a, b, x)");
            }
        });
        map.put("unit",  new MultiParamFunction("unit",     3, "unit(m, a, b) converts m from unit a to unit b")
        {
            @Override
            public Expression evaluate(Expression[] parameters) {
                if (parameters.length == 3){
                    double measure = parameters[0].valueOf();
                    String unit1 = parameters[1].toString();
                    String unit2 = parameters[2].toString();
                    return new NumberExpression(_UnitConversion_.convert(measure, unit1, unit2));
                }
                return new InvalidExpression("Invalid Number of Arguments Error:  unit");
            }
            @Override
            public Expression[] convert(String[] parameters){
                if (parameters.length == 3){
                    return new Expression[]{
                            toExpression(parameters[0]),
                            new StringExpression(parameters[1]),
                            new StringExpression(parameters[2])
                    };
                }
                return super.convert(parameters);
            }
        });
        map.put("var",  new MultiParamFunction("var",     -1, "var(a₁,…,aₙ) calculates the variance of a₁,…,aₙ")
        {
            @Override
            public Expression evaluate(Expression[] parameters) {
                if (parameters.length > 1){
                    double[] data = new double[parameters.length];
                    for (int b = 0; b<data.length; b++){
                        data[b] = parameters[b].valueOf();
                    }
                    return new NumberExpression(Stats.variance(data));
                }
                return new InvalidExpression("Invalid Number of Arguments Error:  var(a₁,…,aₙ)");
            }
        });
        map.put("⊕",  new MultiParamFunction("⊕",   2, "⊕(a, b) calculates a⊕(a+1)⊕⋯⊕(b-1)⊕b")
        {
            @Override
            public Expression evaluate(Expression[] parameters) {
                if (parameters.length == 2){
                    double[] data = new double[parameters.length];
                    for (int b = 0; b<data.length; b++){
                        data[b] = parameters[b].valueOf();
                    }
                    if (data[0] < data[1])
                        return new NumberExpression(_Number_.getXorRange((long) data[0], (long) data[1]));
                }
                return new InvalidExpression("Invalid Number of Arguments Error:  var(a₁,…,aₙ)");
            }
            @Override
            public String getTeX(Expression[] parameters) {
                StringBuilder TeX = new StringBuilder("\\oplus\\left(");
                boolean first = true;
                for (Expression e : parameters){
                    if (first){
                        first = false;
                    }
                    else{
                        TeX.append(", ");
                    }
                    TeX.append(e.toTeX());
                }
                TeX.append("\\right)");
                return TeX.toString();
            }
        });
        map.put("∆",  new MultiParamFunction("∆",       -1, "∆(a₁,…,aₙ) calculates the euclidean distance between a₁,…,aₙ")
        {
            @Override
            public Expression evaluate(Expression[] parameters) {
                if (parameters.length == 1){
                    return new NumberExpression(parameters[0].valueOf());
                }
                else if (parameters.length > 1){
                    double ED = 0;
                    for (Expression parameter : parameters){
                        double temp = parameter.valueOf();
                        ED += temp*temp;
                    }
                    return new NumberExpression(Math.sqrt(ED));
                }
                return new InvalidExpression("Invalid Number of Arguments Error:  ∆(a₁,…,aₙ)");
            }
        });
        map.put("∏",  new MultiParamFunction("∏",       -1, "∏(ƒ, i, n) calculates the product ƒ(i)×⋯×ƒ(n)")
        {
            @Override
            public Expression evaluate(Expression[] parameters) {
                if (parameters.length == 3 || parameters.length == 4){
                    int start = parameters.length == 4 ? (int)parameters[2].valueOf() : (int)parameters[1].valueOf();
                    int end = parameters.length == 4 ? (int)parameters[3].valueOf() : (int)parameters[2].valueOf();
                    String variable = "i";
                    if (parameters.length == 4 && parameters[1] instanceof StringExpression){
                        variable = parameters[1].toString();
                    }
                    double product = 1;
                    for (int b = start; b <= end; b++) {
                        parameters[0].set(variable, b);
                        product *= parameters[0].valueOf();
                    }
                    parameters[0].unset(variable);
                    return new NumberExpression(product);
                }
                return new InvalidExpression("Invalid Number of Arguments Error:  ∏(ƒ, i, n)");
            }
            @Override
            public Expression[] convert(String[] parameters){
                if (parameters.length == 4){
                    return new Expression[]{
                            toExpression(parameters[0]), new StringExpression(parameters[1]),
                            toExpression(parameters[2]), toExpression(parameters[3])
                    };
                }
                return super.convert(parameters);
            }
            @Override
            public String getTeX(Expression[] parameters) {
                return "\\prod_{x="+parameters[1].toTeX()+"}^{"+parameters[2].toTeX()+"} "+parameters[0].toTeX();
            }
        });
        map.put("∑",  new MultiParamFunction("∑",       -1, "∑(ƒ, i, n) calculates the sum ƒ(i)+⋯+ƒ(n)")
        {
            @Override
            public Expression evaluate(Expression[] parameters) {
                if (parameters.length == 3 || parameters.length == 4){
                    List<Double> numbers = parameters[0].getNumbers();
                    Expression start = parameters[parameters.length == 4 ? 2 : 1].evaluate();
                    Expression end = parameters[parameters.length == 4 ? 3 : 2].evaluate();
                    String variable = "i";
                    if (parameters.length == 4 && parameters[1] instanceof StringExpression){
                        variable = parameters[1].toString();
                    }
                    String param0string = parameters[0].toString();
                    if (start instanceof NumberExpression){
                        double m = start.valueOf();
                        if (end instanceof NumberExpression){
                            double n = end.valueOf();
                            if (param0string.equals(var) || param0string.equals("i")){
                                return new NumberExpression((n*(n+1)-m*(m-1))/2.0);
                            }
                            if (param0string.equals(var+" 2 ^")){
                                return new NumberExpression((n+1-m)*(2*m*m+2*m*n-m+2*n*n+n)/6.0);
                            }
                            if(!(parameters[0] instanceof InvalidExpression)) {
                                for (double num : numbers) {
                                    String v = _Number_.format(num);
                                    if (parameters[0].postfix().equals(var+" " + v + " -")) {
                                        return new NumberExpression(-0.5 * (m - n - 1) * (m + n - 2 * num));
                                    }
                                    if (parameters[0].postfix().equals(var+" " + v + " +")) {
                                        return new NumberExpression(-0.5 * (m - n - 1) * (m + n + 2 * num));
                                    }
                                }
                            }
                            double sum = 0;
                            for (double b = m; b <= n; b++) {
                                parameters[0].set(variable, b);
                                sum += parameters[0].valueOf();
                            }
                            return new NumberExpression(sum);
                        }
                        else {
                            double[] values = null;
                            if (param0string.equals(var) || param0string.equals("i")){
                                values = new double[]{1-m, m};
                            }
                            else{
                                for (double num : numbers){
                                    String v = _Number_.format(num);
                                    if (parameters[0].postfix().equals(var+" "+v+" -")){
                                        values = new double[]{1-m, m-2*num};    break;
                                    }
                                    if (parameters[0].postfix().equals(var+" "+v+" +")){
                                        values = new double[]{1-m, m+2*num};    break;
                                    }
                                }
                            }
                            if (values != null){
                                if (values[0] != 0 && values[0] > values[1]){     Sort.swap(values, 0, 1);     }
                                String param2string = parameters[2].toString();
                                if (values[0] == values[1]){
                                    return values[0] == 0 ? new StringExpression(param2string+"²/2") :
                                            new StringExpression("("+param2string+(values[0] > 0 ? "+" : "")+_Number_.format(values[0])+")²/2");
                                }
                                return  new StringExpression(
                                        (values[0] == 0 ? param2string :
                                                "("+param2string+(values[0] > 0 ? "+" : "")+_Number_.format(values[0])+")")+
                                                (values[1] == 0 ? param2string :
                                                        "("+param2string+(values[1] > 0 ? "+" : "")+_Number_.format(values[1])+")")+"/2");
                            }
                            //sum_(i=m)^n i^2 = -1/6 (m - n - 1) (2 m^2 + 2 m n - m + 2 n^2 + n)
                        }
                    }
                    else if (param0string.equals(var) || param0string.equals("i")){
                        String param1string = parameters[1].toString(),
                                param2string = parameters[2].toString();
                        return new StringExpression(param2string+"("+param2string+"+1)/2-"+param1string+"("+param1string+"-1)/2");
                    }
                }
                return new InvalidExpression("Invalid Arguments Error:  ∑(ƒ, i, n)");
            }
            @Override
            public Expression[] convert(String[] parameters){
                if (parameters.length == 3){
                    return new Expression[]{
                            (parameters[0].equals("i") ? new StringExpression("i") : toExpression(parameters[0])),
                            toExpression(parameters[1]),
                            (parameters[2].equals("n") ? new StringExpression("n") : toExpression(parameters[2]))
                    };
                }
                if (parameters.length == 4){
                    return new Expression[]{
                            (parameters[0].equals("i") ? new StringExpression("i") : toExpression(parameters[0])),
                            new StringExpression(parameters[1]), toExpression(parameters[2]),
                            (parameters[3].equals("n") ? new StringExpression("n") : toExpression(parameters[3]))
                    };
                }
                return super.convert(parameters);
            }
            @Override
            public String getTeX(Expression[] parameters) {
                return "\\sum_{x="+parameters[1].toTeX()+"}^{"+parameters[2].toTeX()+"} "+parameters[0].toTeX();
            }
        });
        map.put("∫",  new MultiParamFunction("∫",        3, "∫(ƒ, a, b) calculates the definite integral of ƒ from a to b")
        {
            @Override
            public Expression evaluate(Expression[] parameters) {
                if (parameters.length == 3){
                    double lower = parameters[1].valueOf();
                    double higher = parameters[2].valueOf();
                    return new NumberExpression(Integral.nint(parameters[0], lower, higher));
                }
                return new InvalidExpression("Invalid Number of Arguments Error:  ∫");
            }
            @Override
            public String getTeX(Expression[] parameters) {
                return "\\int_{"+parameters[1].toTeX()+"}^{"+parameters[2].toTeX()+"} "+parameters[0].toTeX()+" dx";
            }
        });
        return map;
    }


    public static boolean isMultiParamFunction(String item){
        return multiParamMap.containsKey(item);
    }
    public static MultiParamFunction getMultiParamFunction(String item){
        return multiParamMap.get(item);
    }
}
