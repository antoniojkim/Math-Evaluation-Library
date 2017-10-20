package Math_Evaluation_Library.Engine;

import Math_Evaluation_Library.Objects.UnaryFunction;
import Math_Evaluation_Library.Objects._Number_;
import Math_Evaluation_Library.Search;
import Math_Evaluation_Library.Trigonometry.Trig;

import static java.lang.Double.NaN;

/**
 * Created by Antonio on 2017-10-17.
 */
public class UnaryFunctions {

//    public static void main (String[] args){
//        String[] unary = {"cbrt", "arcsin", "arcsinh", "sinh", "sin", "arccos", "arccosh", "cosh", "cos",
//                "arctan", "arctanh", "tanh", "tan", "arccsc", "arccsch", "csch", "csc", "arcsec", "arcsech", "sech", "sec", "arccot", "arccoth", "coth", "cot", "ln", "lp", "log", "aexp",
//                "abs", "rad", "deg", "floor", "ceil", "prime", "fib", "smfib", "bin", "tobin"};
//        Sort.quicksort(unary);
//        for (String function :  unary){
//            System.out.println(
//                    "            new UnaryFunction(\""+function+"\"){\n" +
//                            "                @Override\n" +
//                            "                public double evaluate(double x) {\n" +
//                            "                    return NaN;\n" +
//                            "                }\n" +
//                            "            },");
//        }
//    }

    public static final UnaryFunction[] unaryFunctions = {
            new UnaryFunction("abs", "abs(x) = |x|")
            {
                @Override
                public double evaluate(double x) {
                    return Math.abs(x);
                }
            },
            new UnaryFunction("aexp", "aexp(x) = eˣ")
            {
                @Override
                public double evaluate(double x) {
                    return Math.exp(x);
                }
            },
            new UnaryFunction("arccos", "arccos(x) is the Inverse Cosine function")
            {
                @Override
                public double evaluate(double x) {
                    return Math.acos(x);
                }
            },
            new UnaryFunction("arccosh", "arccosh(x) is the Inverse Hyperbolic Cosine function")
            {
                @Override
                public double evaluate(double x) {
                    return Trig.acosh(x);
                }
            },
            new UnaryFunction("arccot", "arccot(x) is the Inverse Cotangent function")
            {
                @Override
                public double evaluate(double x) {
                    return Trig.acot(x);
                }
            },
            new UnaryFunction("arccoth", "arccoth(x) is the Inverse Hyperbolic Cotangent function")
            {
                @Override
                public double evaluate(double x) {
                    return Trig.acoth(x);
                }
            },
            new UnaryFunction("arccsc", "arccsc(x) is the Inverse Cosecant function")
            {
                @Override
                public double evaluate(double x) {
                    return Trig.acsc(x);
                }
            },
            new UnaryFunction("arccsch", "arccsch(x) is the Inverse Hyperbolic Cosecant function")
            {
                @Override
                public double evaluate(double x) {
                    return Trig.acsch(x);
                }
            },
            new UnaryFunction("arcsec", "arcsec(x) is the Inverse Secant function")
            {
                @Override
                public double evaluate(double x) {
                    return Trig.asec(x);
                }
            },
            new UnaryFunction("arcsech", "arcsech(x) is the Inverse Hyperbolic Secant function")
            {
                @Override
                public double evaluate(double x) {
                    return Trig.asech(x);
                }
            },
            new UnaryFunction("arcsin", "arcsin(x) is the Inverse Sine function")
            {
                @Override
                public double evaluate(double x) {
                    return Math.asin(x);
                }
            },
            new UnaryFunction("arcsinh", "arcsinh(x) is the Inverse Hyperbolic Sine function")
            {
                @Override
                public double evaluate(double x) {
                    return Trig.asinh(x);
                }
            },
            new UnaryFunction("arctan", "arctan(x) is the Inverse Tangent function")
            {
                @Override
                public double evaluate(double x) {
                    return Math.atan(x);
                }
            },
            new UnaryFunction("arctanh", "arctanh(x) is the Inverse Hyperbolic Tangent function")
            {
                @Override
                public double evaluate(double x) {
                    return Trig.atanh(x);
                }
            },
            new UnaryFunction("bin", "bin(x) produces the binary form of x")
            {
                @Override
                public double evaluate(double x) {
                    if (x % 1 == 0){
                        double num = _Number_.fromBinary(_Number_.format(x));
                        if (num != -1){
                            return num;
                        }
                    }
                    return NaN;
                }
            },
            new UnaryFunction("ceil", "ceil(x) = ⌈x⌉,  smallest integer ≥ x")
            {
                @Override
                public double evaluate(double x) {
                    return Math.ceil(x);
                }
            },
            new UnaryFunction("cos", "cos(x) is the Cosine function")
            {
                @Override
                public double evaluate(double x) {
                    return Math.cos(x);
                }
            },
            new UnaryFunction("cosh", "cosh(x) is the Hyperbolic Cosine function")
            {
                @Override
                public double evaluate(double x) {
                    return Math.cosh(x);
                }
            },
            new UnaryFunction("cot", "cot(x) is the Cotangent function")
            {
                @Override
                public double evaluate(double x) {
                    return Trig.cot(x);
                }
            },
            new UnaryFunction("coth", "coth(x) is the Hyperbolic Cotangent function")
            {
                @Override
                public double evaluate(double x) {
                    return Trig.coth(x);
                }
            },
            new UnaryFunction("csc", "csc(x) is the Cosecant function")
            {
                @Override
                public double evaluate(double x) {
                    return Trig.csc(x);
                }
            },
            new UnaryFunction("csch", "csch(x) is the Hyperbolic Cosecant function")
            {
                @Override
                public double evaluate(double x) {
                    return Trig.csch(x);
                }
            },
            new UnaryFunction("deg", "deg(x) = x°, x in angular degrees")
            {
                @Override
                public double evaluate(double x) {
                    return Math.toDegrees(x);
                }
            },
            new UnaryFunction("fib", "fib(n), nᵗʰ term in the fibonacci sequence")
            {
                @Override
                public double evaluate(double x) {
                    if (x % 1 == 0 && x >= 0 && x <= 1472) {
                        return _Number_.getFibonnaci((int) x);
                    }
                    return NaN;
                }
            },
            new UnaryFunction("floor", "floor(x) = ⌊x⌋, greatest integer ≤ x")
            {
                @Override
                public double evaluate(double x) {
                    return Math.floor(x);
                }
            },
            new UnaryFunction("floor₂", "floor₂(x) calculates the lowest power of 2 ≤ x")
            {
                @Override
                public double evaluate(double x) {
                    if (x % 1 == 0) {
                        return _Number_.floor2((int) x);
                    }
                    return _Number_.floor2(x);
                }
            },
            new UnaryFunction("ln", "ln(x) is the Natural Logarithm")
            {
                @Override
                public double evaluate(double x) {
                    return Math.log(x);
                }
            },
            new UnaryFunction("log", "log(x) = log₁₀(x), is the Base 10 Logarithm")
            {
                @Override
                public double evaluate(double x) {
                    return Math.log10(x);
                }
            },
            new UnaryFunction("lp", "lp(x) = ln(x+1), is the Natural Logarithm of (x+1)")
            {
                @Override
                public double evaluate(double x) {
                    return Math.log1p(x);
                }
            },
            new UnaryFunction("prime", "prime(x), determines whether x is prime")
            {
                @Override
                public double evaluate(double x) {
                    if (x % 1 == 0) {
                        return _Number_.isPrime((int) x);
                    }
                    return NaN;
                }
            },
            new UnaryFunction("rad", "rad(x) = xʳ, x in angular radians")
            {
                @Override
                public double evaluate(double x) {
                    return Math.toRadians(x);
                }
            },
            new UnaryFunction("sec", "sec(x) is the Secant function")
            {
                @Override
                public double evaluate(double x) {
                    return Trig.sec(x);
                }
            },
            new UnaryFunction("sech", "sech(x) is the Hyperbolic Secant function")
            {
                @Override
                public double evaluate(double x) {
                    return Trig.sech(x);
                }
            },
            new UnaryFunction("sin", "sin(x) is the Sine function")
            {
                @Override
                public double evaluate(double x) {
                    return Math.sin(x);
                }
            },
            new UnaryFunction("sinh", "sinh(x) is the Hyperbolic Sine function")
            {
                @Override
                public double evaluate(double x) {
                    return Math.sinh(x);
                }
            },
            new UnaryFunction("smfib", "smfib(n) = fib(0)+fib(1)+⋯+f(n)")
            {
                @Override
                public double evaluate(double x) {
                    if (x % 1 == 0 && x >= 0 && x <= 1470){
                        return _Number_.getFibonnaci((int) (x+2))-1;
                    }
                    return NaN;
                }
            },
            new UnaryFunction("tan", "tan(x) is the Tangent function")
            {
                @Override
                public double evaluate(double x) {
                    return Math.tan(x);
                }
            },
            new UnaryFunction("tanh", "tanh(x) is the Hyperbolic Tangent function")
            {
                @Override
                public double evaluate(double x) {
                    return Math.tanh(x);
                }
            },
            new UnaryFunction("tobin", "tobin(x) transforms a binary string into a decimal value")
            {
                @Override
                public double evaluate(double x) {
                    if (x % 1 == 0){
                        return _Number_.getNumber(_Number_.toBinary((int)x));
                    }
                    return NaN;
                }
            }
    };

    public static boolean unaryFunctionsContains(String item){
        return Search.contains(unaryFunctions, item);
    }
    public static int unaryFunctionsIndex(String item){
        return Search.binarySearch(unaryFunctions, item);
    }
    public static boolean startsWithUnaryFunction(String item){
//        int max = Math.max(item.length(), UnaryFunction.maxStrLength);
//        for (int i = max; i >= UnaryFunction.minStrLength; i--){
//            if (unaryFunctionsContains(item.substring(0, i)))   return true;
//        }
        for (UnaryFunction function : unaryFunctions){
            if (item.startsWith(function.getName())){
                return true;
            }
        }
        return false;
    }

}
