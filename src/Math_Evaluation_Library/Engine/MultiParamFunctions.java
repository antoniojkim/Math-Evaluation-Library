package Math_Evaluation_Library.Engine;

import Math_Evaluation_Library.Calculus.Derivative;
import Math_Evaluation_Library.Calculus.Integral;
import Math_Evaluation_Library.Calculus.Roots;
import Math_Evaluation_Library.Geometry.Geometric;
import Math_Evaluation_Library.Logic.Propositional;
import Math_Evaluation_Library.Miscellaneous.Mod;
import Math_Evaluation_Library.Miscellaneous._Random_;
import Math_Evaluation_Library.Objects.Function;
import Math_Evaluation_Library.Objects.MultiParamFunction;
import Math_Evaluation_Library.Objects._Number_;
import Math_Evaluation_Library.Search;
import Math_Evaluation_Library.Sort;
import Math_Evaluation_Library.Statistics.RandomVariables;
import Math_Evaluation_Library.Statistics.Stats;
import Math_Evaluation_Library.UnitConversion._UnitConversion_;

import java.util.ArrayList;
import java.util.List;

import static Math_Evaluation_Library.Engine.Engine.var;
import static Math_Evaluation_Library.Engine.Engine.x;

/**
 * Created by Antonio on 2017-07-22.
 */
public class MultiParamFunctions{

//    public static void main (String[] args){
//        Sort.quicksort(multiParamFunctions);
//        for (MultiParamFunction m : multiParamFunctions){
//            System.out.println(m.getName());
//        }
//    }

    public static final String INVALID = "!";

    public static final MultiParamFunction[] multiParamFunctions = {
            new MultiParamFunction("Bin",      3, "Bin(n, p, "+x+"); n trials, probability p, "+x+" successes; P(X="+x+") for X ~ Binomial Distribution")
            {
                @Override
                public String evaluate(String[] parameters) {
                    if (parameters.length == 3){
                        try{
                            double n = Engine.evaluate(parameters[0]);
                            double p = Engine.evaluate(parameters[1]);
                            double x = Engine.evaluate(parameters[2]);
                            if (n>=0 && p>=0 && p<=1 && x>=0 && x<=n){
                                return _Number_.format(RandomVariables.binomialDistribution((int)n, p, (int)x));
                            }
                        } catch(NumberFormatException e){}
                    }
                    return INVALID;
                }
            },
            new MultiParamFunction("Binexp",   2, "Binexp(n, p) = n×p; n trials, probability p; calculates Expected Value for Binomial Distribution")
            {
                @Override
                public String evaluate(String[] parameters) {
                    if (parameters.length == 2){
                        try{
                            double n = Engine.evaluate(parameters[0]);
                            double p = Engine.evaluate(parameters[1]);
                            if (n>=0 && p>=0 && p<=1){
                                return _Number_.format(RandomVariables.binomialDistribution_ExpectedValue((int)n, p));
                            }
                        } catch(NumberFormatException e){}
                    }
                    return INVALID;
                }
            },
            new MultiParamFunction("Binvar",   2, "Binvar(n, p) = n×p(1-p); n trials, probability p; calculates Variance for Binomial Distribution")
            {
                @Override
                public String evaluate(String[] parameters) {
                    if (parameters.length == 2){
                        try{
                            double n = Engine.evaluate(parameters[0]);
                            double p = Engine.evaluate(parameters[1]);
                            if (n>=0 && p>=0 && p<=1){
                                return _Number_.format(RandomVariables.binomialDistribution_Variance((int)n, p));
                            }
                        } catch(NumberFormatException e){}
                    }
                    return INVALID;
                }
            },
            new MultiParamFunction("NB",       3, "NB(k, p, "+x+"); k successes, probability p, "+x+" failures; P(X="+x+") for X ~ Negative Binomial Distribution")
            {
                @Override
                public String evaluate(String[] parameters) {
                    if (parameters.length == 3){
                        try{
                            double k = Engine.evaluate(parameters[0]);
                            double p = Engine.evaluate(parameters[1]);
                            double x = Engine.evaluate(parameters[2]);
                            if (k>=0 && p>=0 && p<=1 && x>=0){
                                return _Number_.format(RandomVariables.negativeBinomialDistribution((int)k, p, (int)x));
                            }
                        } catch(NumberFormatException e){}
                    }
                    return INVALID;
                }
            },
            new MultiParamFunction("NBexp",    2, "NBexp(k, p) = k×(1-p)/p; k successes, probability p; calculates Expected Value for Negative Binomial Distribution")
            {
                @Override
                public String evaluate(String[] parameters) {
                    if (parameters.length == 2){
                        try{
                            double k = Engine.evaluate(parameters[0]);
                            double p = Engine.evaluate(parameters[1]);
                            if (k>=0 && p>=0 && p<=1){
                                return _Number_.format(RandomVariables.negativeBinomialDistribution_ExpectedValue((int)k, p));
                            }
                        } catch(NumberFormatException e){}
                    }
                    return INVALID;
                }
            },
            new MultiParamFunction("NBvar",    2, "NBvar(k, p) = k×(1-p)/p²; k successes, probability p; calculates Variance for Negative Binomial Distribution")
            {
                @Override
                public String evaluate(String[] parameters) {
                    if (parameters.length == 2){
                        try{
                            double k = Engine.evaluate(parameters[0]);
                            double p = Engine.evaluate(parameters[1]);
                            if (k>=0 && p>=0 && p<=1){
                                return _Number_.format(RandomVariables.negativeBinomialDistribution_Variance((int)k, p));
                            }
                        } catch(NumberFormatException e){}
                    }
                    return INVALID;
                }
            },
            new MultiParamFunction("ND",      -1, "ND(μ, σ², "+x+"); mean μ, standard deviation σ²; P(X="+x+") for X ~ Normal Distribution")
            {
                @Override
                public String evaluate(String[] parameters) {
                    try{
                        double mean = 0, stdv = 1, x = 0;
                        if (parameters.length == 1){
                            x = Engine.evaluate(parameters[0]);
                        }
                        else if (parameters.length == 3){
                            mean = Engine.evaluate(parameters[0]);
                            stdv = Engine.evaluate(parameters[1]);
                            x = Engine.evaluate(parameters[2]);
                        }
                        else{
                            return INVALID;
                        }
                        if (stdv>=0){
                            return _Number_.format(RandomVariables.normalDistribution(mean, stdv, x));
                        }
                    } catch(NumberFormatException e){}
                    return INVALID;
                }
            },
            new MultiParamFunction("avg",     -1, "avg(a₁,…,aₙ) calculates the average of a₁,…,aₙ")
            {
                @Override
                public String evaluate(String[] parameters) {
                    if (parameters.length > 0){
                        double sum = 0;
                        for (String parameter : parameters){
                            sum += Engine.evaluate(parameter);
                        }
                        sum /= parameters.length;
                        return _Number_.format(sum);
                    }
                    return INVALID;
                }
            },
            new MultiParamFunction("c_law",    3, "c_law(b, c, θ) calculates the cosine law:  √(b²+c²-2×b×c×cos(θ))")
            {
                @Override
                public String evaluate(String[] parameters) {
                    if (parameters.length == 3){
                        double b = Engine.evaluate(parameters[0]);
                        double c = Engine.evaluate(parameters[1]);
                        double theta = Engine.evaluate(parameters[2]);
                        return _Number_.format(Math.sqrt(b*b+c*c-2*b*c*Math.cos(theta)));
                    }
                    return INVALID;
                }
            },
            new MultiParamFunction("dxn",      3, "dxn(ƒ, "+x+", n) calculates the value of the nth derivative of ƒ at "+x)
            {
                @Override
                public String evaluate(String[] parameters) {
                    if (parameters.length == 3){
                        String fx = parameters[0];
                        double x = Engine.evaluate(parameters[1]);
                        int n = Integer.parseInt(parameters[2].trim());
                        String[] derivatives = new String[n];
                        derivatives[0] = Derivative.calculate(fx);
                        for (int b = 1; b<derivatives.length; b++){
                            derivatives[b] = Derivative.calculate(derivatives[b-1]);
                        }
                        String finalDerivative = derivatives[derivatives.length-1];
                        if (finalDerivative.charAt(finalDerivative.length()-1) != '\''){
                            return _Number_.format(Engine.evaluate(derivatives[derivatives.length-1], x));
                        }
                    }
                    return INVALID;
                }
            },
            new MultiParamFunction("elasd",    4, "elasd(q₁, q₂, p₁, p₂) calculates the elasticity of demand:  ((q₂-q₁)/(q₂+q₁))/((p₂-p₁)/(p₂+p₁))")
            {
                @Override
                public String evaluate(String[] parameters) {
                    if (parameters.length == 4){
                        double q1 = Engine.evaluate(parameters[0]);
                        double q2 = Engine.evaluate(parameters[1]);
                        double p1 = Engine.evaluate(parameters[2]);
                        double p2 = Engine.evaluate(parameters[3]);
                        return _Number_.format((((q2 - q1) / (q2 + q1)) / ((p2 - p1) / (p2 + p1))));
                    }
                    return INVALID;
                }
            },
            new MultiParamFunction("gcd",     -1, "gcd(a₁,…,aₙ) calculates the greatest common denominator of a₁,…,aₙ")
            {
                @Override
                public String evaluate(String[] parameters) {
                    if (parameters.length == 1){
                        return parameters[0];
                    }
                    else if (parameters.length > 1){
                        double temp1 = Engine.evaluate(parameters[0]);
                        if (temp1%1 == 0){
                            double gcd = temp1;
                            for (int b = 1; b<parameters.length; b++){
                                double temp = Engine.evaluate(parameters[b]);
                                if (temp%1 == 0){
                                    gcd = Mod.gcd(gcd, temp);
                                }
                                else{
                                    return INVALID;
                                }
                            }
                            return _Number_.format(gcd);
                        }
                    }
                    return INVALID;
                }
            },
            new MultiParamFunction("geo",      2, "geo(p, "+x+"); probability p, "+x+" failures; P(X="+x+") for X ~ Geometric Distribution")
            {
                @Override
                public String evaluate(String[] parameters) {
                    if (parameters.length == 2){
                        try{
                            double p = Engine.evaluate(parameters[0]);
                            double x = Engine.evaluate(parameters[1]);
                            if (p>=0 && p<=1 && x%1 == 0 && x>=0){
                                return String.valueOf(RandomVariables.geometricDistribution(p, (int)x));
                            }
                        } catch(NumberFormatException e){}
                    }
                    return INVALID;
                }
            },
            new MultiParamFunction("geoexp",   1, "geoexp(p) = (1-p)/p; probability p; calculates Expected Value for Geometric Distribution")
            {
                @Override
                public String evaluate(String[] parameters) {
                    if (parameters.length == 1){
                        try{
                            double p = Engine.evaluate(parameters[0]);
                            if (p>=0 && p<=1){
                                return String.valueOf(RandomVariables.geometricDistribution_ExpectedValue(p));
                            }
                        } catch(NumberFormatException e){}
                    }
                    return INVALID;
                }
            },
            new MultiParamFunction("geovar",   1, "geovar(p) = (1-p)/p²; probability p; calculates Variance for Geometric Distribution")
            {
                @Override
                public String evaluate(String[] parameters) {
                    if (parameters.length == 1){
                        try{
                            double p = Engine.evaluate(parameters[0]);
                            if (p>=0 && p<=1){
                                return String.valueOf(RandomVariables.geometricDistribution_Variance(p));
                            }
                        } catch(NumberFormatException e){}
                    }
                    return INVALID;
                }
            },
            new MultiParamFunction("heron",   -1, "heron(A, B, C) calculates the area of a triangle of side lengths A, B, C. heron("+x+"₁, y₁, "+x+"₂, y₂, "+x+"₃, y₃) calculates the area of a triangle with points (x₁, y₁), (x₂, y₂), (x₃, y₃)")
            {
                @Override
                public String evaluate(String[] parameters) {
                    if (parameters.length == 3){
                        double A = Engine.evaluate(parameters[0]);
                        double B = Engine.evaluate(parameters[1]);
                        double C = Engine.evaluate(parameters[2]);
                        double s = (A+B+C) / 2;
                        return _Number_.format(Math.sqrt(s*(s-A)*(s-B)*(s-C)));
                    }
                    else if (parameters.length == 6){
                        double x1 = Engine.evaluate(parameters[0]);
                        double y1 = Engine.evaluate(parameters[1]);
                        double x2 = Engine.evaluate(parameters[2]);
                        double y2 = Engine.evaluate(parameters[3]);
                        double x3 = Engine.evaluate(parameters[4]);
                        double y3 = Engine.evaluate(parameters[5]);
                        double A = Geometric.pointDistance(x1, y1, x2, y2);
                        double B = Geometric.pointDistance(x1, y1, x3, y3);
                        double C = Geometric.pointDistance(x2, y2, x3, y3);
                        double s = (A+B+C) / 2;
                        return _Number_.format(Math.sqrt(s*(s-A)*(s-B)*(s-C)));
                    }
                    return INVALID;
                }
            },
            new MultiParamFunction("hyp",      4, "hyp(N, r, n, "+x+"); N objects, r ways to succeed, n trials, "+x+" successes; P(X="+x+") for X ~ Hypergeometric Distribution")
            {
                @Override
                public String evaluate(String[] parameters) {
                    if (parameters.length == 4){
                        try{
                            double N = Engine.evaluate(parameters[0]);
                            double r = Engine.evaluate(parameters[1]);
                            double n = Engine.evaluate(parameters[2]);
                            double x = Engine.evaluate(parameters[3]);
                            if (N%1 == 0 && r%1 == 0 && n%1 == 0 && x%1 == 0){
                                return _Number_.format(RandomVariables.hyperGeometricDistribution((int)N, (int)r, (int)n, (int)x));
                            }
                        } catch(NumberFormatException e){}
                    }
                    return INVALID;
                }
            },
            new MultiParamFunction("hypexp",   3, "hypexp(N, r, n) = n×r/N; N objects, r ways to succeed, n trials; calculates Expected Value of the Hypergeometric Distribution")
            {
                @Override
                public String evaluate(String[] parameters) {
                    if (parameters.length == 3){
                        try{
                            double N = Engine.evaluate(parameters[0]);
                            double r = Engine.evaluate(parameters[1]);
                            double n = Engine.evaluate(parameters[2]);
                            if (N%1 == 0 && r%1 == 0 && n%1 == 0){
                                return _Number_.format(RandomVariables.hyperGeometricDistribution_ExpectedValue(N, (int)r, (int)n));
                            }
                        } catch(NumberFormatException e){}
                    }
                    return INVALID;
                }
            },
            new MultiParamFunction("hypvar",   3, "hypvar(N, r, n) = (n×r×(N-r)×(N-n))/(N²×(N-1)); N objects, r ways to succeed, n trials; calculates Variance of the Hypergeometric Distribution")
            {
                @Override
                public String evaluate(String[] parameters) {
                    if (parameters.length == 3){
                        try{
                            double N = Engine.evaluate(parameters[0]);
                            double r = Engine.evaluate(parameters[1]);
                            double n = Engine.evaluate(parameters[2]);
                            if (N%1 == 0 && r%1 == 0 && n%1 == 0){
                                return _Number_.format(RandomVariables.hyperGeometricDistribution_Variance(N, (int)r, (int)n));
                            }
                        } catch(NumberFormatException e){}
                    }
                    return INVALID;
                }
            },
            new MultiParamFunction("lcm",     -1, "lcm(a₁,…,aₙ) calculates the lowest common multiple of a₁,…,aₙ")
            {
                @Override
                public String evaluate(String[] parameters) {
                    if (parameters.length == 1){
                        return parameters[0];
                    }
                    else if (parameters.length > 1){
                        double temp1 = Engine.evaluate(parameters[0]);
                        if (temp1%1 == 0){
                            int lcm = (int)temp1;
                            for (int b = 1; b<parameters.length; b++){
                                double temp = Engine.evaluate(parameters[b]);
                                if (temp%1 == 0){
                                    lcm = (int) Mod.lcm(lcm, (int)temp);
                                }
                                else{
                                    return INVALID;
                                }
                            }
                            return _Number_.format(lcm);
                        }
                    }
                    return INVALID;
                }
            },
            new MultiParamFunction("logab",   -1, "logab(a) calculated log₁₀(a). logab(a, b) calculates logₐ(b)")
            {
                @Override
                public String evaluate(String[] parameters) {
                    if (parameters.length == 1){
                        return _Number_.format(  Math.log10(Engine.evaluate(parameters[0])) );
                    }
                    else if (parameters.length == 2){
                        return _Number_.format(  Math.log10(Engine.evaluate(parameters[0]))/Math.log10(Engine.evaluate(parameters[1])));
                    }
                    return INVALID;
                }
            },
            new MultiParamFunction("max",     -1, "max(a₁,…,aₙ) calculates the highest of a₁,…,aₙ")
            {
                @Override
                public String evaluate(String[] parameters) {
                    if (parameters.length > 0){
                        double largest = Engine.evaluate(parameters[0]);
                        for (int b = 1; b<parameters.length; b++){
                            largest = Math.max(largest, Engine.evaluate(parameters[b]));
                        }
                        return _Number_.format(largest);
                    }
                    return INVALID;
                }
            },
            new MultiParamFunction("min",     -1, "min(a₁,…,aₙ) calculates the lowest of a₁,…,aₙ")
            {
                @Override
                public String evaluate(String[] parameters) {
                    if (parameters.length > 0){
                        double smallest = Engine.evaluate(parameters[0]);
                        for (int b = 1; b<parameters.length; b++){
                            smallest = Math.min(smallest, Engine.evaluate(parameters[b]));
                        }
                        return _Number_.format(smallest);
                    }
                    return INVALID;
                }
            },
            new MultiParamFunction("newton",   2, "newton(ƒ, a) calculates the newton approximation of the root of ƒ starting from "+x+"=a")
            {
                @Override
                public String evaluate(String[] parameters) {
                    if (parameters.length == 2){
                        String fx = parameters[0];
                        double x = Engine.evaluate(parameters[1]);
                        return _Number_.format(Roots.NewtonsMethod(fx, x));
                    }
                    return INVALID;
                }
            },
            new MultiParamFunction("poi",      2, "poi(λ, "+x+"); λ=np for n trials, probability p;  P(X="+x+") for X ~ Poisson Distribution")
            {
                @Override
                public String evaluate(String[] parameters) {
                    if (parameters.length == 2){
                        try{
                            double lambda = Engine.evaluate(parameters[0]);
                            double x = Engine.evaluate(parameters[1]);
                            return _Number_.format(RandomVariables.poissonDistribution(lambda, (int)x));
                        } catch(NumberFormatException e){}
                    }
                    return INVALID;
                }
            },
            new MultiParamFunction("poiexp",   1, "poiexp(λ) = λ; λ=np for n trials, probability p;  calculates Expected Value of the Poisson Distribution")
            {
                @Override
                public String evaluate(String[] parameters) {
                    if (parameters.length == 1){
                        try{
                            double lambda = Engine.evaluate(parameters[0]);
                            return _Number_.format(RandomVariables.poissonDistribution_ExpectedValue(lambda));
                        } catch(NumberFormatException e){}
                    }
                    return INVALID;
                }
            },
            new MultiParamFunction("poivar",   1, "poivar(λ) = λ; λ=np for n trials, probability p;  calculates Variance of the Poisson Distribution")
            {
                @Override
                public String evaluate(String[] parameters) {
                    if (parameters.length == 1){
                        try{
                            double lambda = Engine.evaluate(parameters[0]);
                            return _Number_.format(RandomVariables.poissonDistribution_Variance(lambda));
                        } catch(NumberFormatException e){}
                    }
                    return INVALID;
                }
            },
            new MultiParamFunction("prop",    -1, "prop(s, t₁,…,tₙ) calculates the truth valuation of s with truth values t₁,…,tₙ")
            {
                @Override
                public String evaluate(String[] parameters) {
                    if (parameters.length > 0){
                        boolean[] values = new boolean[parameters.length-1];
                        for (int i = 0; i<values.length; i++){
                            values[i] = parameters[i+1].equalsIgnoreCase("T") || parameters[i+1].equalsIgnoreCase("True");
                        }
                        return Propositional.valuate(parameters[0], values);
                    }
                    return INVALID;
                }
            },
            new MultiParamFunction("randint",  2, "randint(l, h) calculates a random integer ∈ [l, h]")
            {
                @Override
                public String evaluate(String[] parameters) {
                    if (parameters.length == 2){
                        int low = Integer.parseInt(parameters[0]);
                        int high = Integer.parseInt(parameters[1]);
                        return _Number_.format(_Random_.randomint(low, high));
                    }
                    return INVALID;
                }
            },
            new MultiParamFunction("random",   2, "random(l, h) calculates a random real number ∈ [l, h]")
            {
                @Override
                public String evaluate(String[] parameters) {
                    if (parameters.length == 2){
                        double low = Engine.evaluate(parameters[0]);
                        double high = Engine.evaluate(parameters[1]);
                        return _Number_.format(_Random_.random(low, high));
                    }
                    return INVALID;
                }
            },
            new MultiParamFunction("randq",    2, "randint(l, h) calculates a random rational of the form p/q where p,q ∈ [l, h]")
            {
                @Override
                public String evaluate(String[] parameters) {
                    if (parameters.length == 2){
                        int low = Integer.parseInt(parameters[0]);
                        int high = Integer.parseInt(parameters[1]);
                        return _Number_.format(_Random_.randomRational(low, high));
                    }
                    return INVALID;
                }
            },
            new MultiParamFunction("riemann", -1, "riemann(ƒ, a, b, n, {l, r, m}) calculates the {l, r, m} riemann sum of fƒ from a to b with n intervals")
            {
                @Override
                public String evaluate(String[] parameters) {
                    if (parameters.length == 4 || parameters.length == 5){
                        Function f = new Function(parameters[0]);
                        int start = Integer.parseInt(parameters[1]);
                        int end = Integer.parseInt(parameters[2]);
                        int n = Integer.parseInt(parameters[3]);
                        String riemann = (parameters.length == 5 ? parameters[4] : "m");
                        double h = (end-start)/(double)n;

                        double sum = 0;
                        if (riemann.equals("l")){
                            for (int b = 0; b < n; b++) {
                                sum += f.of(start+h*b);
                            }
                        }
                        else if (riemann.equals("r")){
                            for (int b = 1; b <= n; b++) {
                                sum += f.of(start+h*b);
                            }
                        }
                        else {
                            for (int b = 0; b <= n-1; b++) {
                                sum += f.of(start+h*(2*b+1)/2.0);
                            }
                        }
                        return _Number_.format(sum*h);
                    }
                    return INVALID;
                }
            },
            new MultiParamFunction("stndv",   -1, "stndv(a₁,…,aₙ) calculates the standard deviation of a₁,…,aₙ")
            {
                @Override
                public String evaluate(String[] parameters) {
                    if (parameters.length > 1){
                        double[] data = new double[parameters.length];
                        for (int b = 0; b<data.length; b++){
                            data[b] = Engine.evaluate(parameters[b]);
                        }
                        return _Number_.format(Stats.stnDev(data));
                    }
                    return INVALID;
                }
            },
            new MultiParamFunction("unif",     3, "unif(a, b, x) calculates the uniform distribution from a to b at x")
            {
                @Override
                public String evaluate(String[] parameters) {
                    if (parameters.length == 3){
                        try{
                            int a = Integer.parseInt(parameters[0]);
                            int b = Integer.parseInt(parameters[1]);
                            int x = Integer.parseInt(parameters[2]);
                            return _Number_.format(RandomVariables.uniformDistribution(a, b, x));
                        }catch (NumberFormatException e){}
                    }
                    return INVALID;
                }
            },
            new MultiParamFunction("unit",     3, "unit(m, a, b) converts m from unit a to unit b")
            {
                @Override
                public String evaluate(String[] parameters) {
                    if (parameters.length == 3){
                        double measure = _Number_.getNumber(parameters[0]);
                        String unit1 = parameters[1];
                        String unit2 = parameters[2];
                        double converted = _UnitConversion_.convert(measure, unit1, unit2);
                        if (_Number_.isNumber(converted)){
                            return String.valueOf(converted);
                        }
                    }
                    return INVALID;
                }
            },
            new MultiParamFunction("var",     -1, "var(a₁,…,aₙ) calculates the variance of a₁,…,aₙ")
            {
                @Override
                public String evaluate(String[] parameters) {
                    if (parameters.length > 1){
                        double[] data = new double[parameters.length];
                        for (int b = 0; b<data.length; b++){
                            data[b] = Engine.evaluate(parameters[b]);
                        }
                        return _Number_.format(Stats.variance(data));
                    }
                    return INVALID;
                }
            },
            new MultiParamFunction("∆",       -1, "∆(a₁,…,aₙ) calculates the euclidean distance between a₁,…,aₙ")
            {
                @Override
                public String evaluate(String[] parameters) {
                    if (parameters.length == 1){
                        return parameters[0];
                    }
                    else if (parameters.length > 1){
                        double ED = 0;
                        for (String parameter : parameters){
                            double temp = Engine.evaluate(parameter);
                            ED += temp*temp;
                        }
                        return _Number_.format(Math.sqrt(ED));
                    }
                    return INVALID;
                }
            },
            new MultiParamFunction("∏",       -1, "∏(ƒ, i, n) calculates the product ƒ(i)×⋯×ƒ(n)")
            {
                @Override
                public String evaluate(String[] parameters) {
                    if (parameters.length == 3 || parameters.length == 4){
                        int start = parameters.length == 4 ? Integer.parseInt(parameters[2]) : Integer.parseInt(parameters[1]);
                        int end = parameters.length == 4 ? Integer.parseInt(parameters[3]) : Integer.parseInt(parameters[2]);
                        String variable = parameters.length == 4 ? parameters[1] : "i";
                        double product = 1;
                        for (int b = start; b <= end; b++) {
                            product *= Engine.evaluate(Search.replace(parameters[0], new String[][]{
                                    {"{"+variable+"}", "("+b+")"}, {"$"+variable, "("+b+")"}, {"("+variable+")", "("+b+")"}
                            }));
                        }
                        return _Number_.format(product);
                    }
                    return INVALID;
                }
            },
            new MultiParamFunction("∑",       -1, "∑(ƒ, i, n) calculates the sum ƒ(i)+⋯+ƒ(n)")
            {
                @Override
                public String evaluate(String[] parameters) {
                    if (parameters.length == 3 || parameters.length == 4){
                        Function f = new Function(parameters[0]);
                        List<Double> numbers = f.isFunction() ? _Number_.extractNumbers(f.function()) : new ArrayList<>();
                        boolean isStartNumber = parameters.length == 4 ? _Number_.isNumber(parameters[2]) : _Number_.isNumber(parameters[1]);
                        boolean isEndNumber = parameters.length == 4 ? _Number_.isNumber(parameters[3]) : _Number_.isNumber(parameters[2]);
                        String variable = parameters.length == 4 ? parameters[1] : "i";
                        if (isStartNumber){
                            double m = parameters.length == 3 ? Double.parseDouble(parameters[1]) : Double.parseDouble(parameters[2]);
                            if (isEndNumber){
                                double n = parameters.length == 3 ? Double.parseDouble(parameters[2]) : Double.parseDouble(parameters[3]);
                                if (parameters[0].equals(var) || parameters[0].equals("i")){
                                    return _Number_.format((n*(n+1)-m*(m-1))/2.0);
                                }
                                if (Engine.toPostfix(parameters[0]).equals(var+" 2 ^")){
                                    return _Number_.format((n+1-m)*(2*m*m+2*m*n-m+2*n*n+n)/6.0);
                                }
                                if(f.isFunction()) {
                                    for (double num : numbers) {
                                        String v = _Number_.format(num);
                                        if (f.postfix().equals(var+" " + v + " -")) {
                                            return _Number_.format(-0.5 * (m - n - 1) * (m + n - 2 * num));
                                        }
                                        if (f.postfix().equals(var+" " + v + " +")) {
                                            return _Number_.format(-0.5 * (m - n - 1) * (m + n + 2 * num));
                                        }
                                    }
                                }
                                double sum = 0;
                                for (double b = m; b <= n; b++) {
                                    sum += Engine.evaluate(Search.replace(parameters[0], new String[][]{
                                            {"("+variable+")", "("+b+")"}, {"{"+variable+"}", "("+b+")"}, {"$"+variable, "("+b+")"}
                                    }));
                                }
                                return _Number_.format(sum);
                            }
                            else {
                                double[] values = null;
                                if (f.function().equals(var) || parameters[0].equals("i")){
                                    values = new double[]{1-m, m};
                                }
                                else{
                                    for (double num : numbers){
                                        String v = _Number_.format(num);
                                        if (f.postfix().equals(var+" "+v+" -")){
                                            values = new double[]{1-m, m-2*num};    break;
                                        }
                                        if (f.postfix().equals(var+" "+v+" +")){
                                            values = new double[]{1-m, m+2*num};    break;
                                        }
                                    }
                                }
                                if (values != null){
                                    if (values[0] != 0 && values[0] > values[1]){     Sort.swap(values, 0, 1);     }
                                    if (values[0] == values[1]){
                                        return values[0] == 0 ? parameters[2]+"²/2" : "("+parameters[2]+(values[0] > 0 ? "+" : "")+_Number_.format(values[0])+")²/2";
                                    }
                                    return  (values[0] == 0 ? parameters[2] : "("+parameters[2]+(values[0] > 0 ? "+" : "")+_Number_.format(values[0])+")")+
                                            (values[1] == 0 ? parameters[2] : "("+parameters[2]+(values[1] > 0 ? "+" : "")+_Number_.format(values[1])+")")+"/2";
                                }
                                //sum_(i=m)^n i^2 = -1/6 (m - n - 1) (2 m^2 + 2 m n - m + 2 n^2 + n)
                            }
                        }
                        else {
                            if (f.function().equals(var) || parameters[0].equals("i")){
                                return ""+parameters[2]+"("+parameters[2]+"+1)/2-"+parameters[1]+"("+parameters[1]+"-1)/2";
                            }
                        }
                    }
                    return INVALID;
                }
            },
            new MultiParamFunction("∫",        3, "∫(ƒ, a, b) calculates the definite integral of ƒ from a to b")
            {
                @Override
                public String evaluate(String[] parameters) {
                    if (parameters.length == 3){
                        String fx = parameters[0];
                        double lower = Engine.evaluate(parameters[1]);
                        double higher = Engine.evaluate(parameters[2]);
                        return _Number_.format(Integral.nint(fx, lower, higher));
                    }
                    return INVALID;
                }
            }
    };


    public static boolean isMultiParamFunction(String item){
        return Search.contains(multiParamFunctions, item);
    }
    public static int multiParamFunctionNamesIndex(String item){
        if (item.length() > MultiParamFunction.maxStrLength)    return -1;
        return Search.binarySearch(multiParamFunctions, item);
    }
}
