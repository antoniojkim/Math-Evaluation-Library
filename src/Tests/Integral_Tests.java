package Tests;

import Math_Evaluation_Library.Calculus.Integral;
import Math_Evaluation_Library.Engine.Engine;

/**
 * Created by Antonio on 2017-07-21.
 */
public class Integral_Tests extends  _Tests_{

    public Integral_Tests(String name) {
        super(name);
    }

    @Override
    public void run(){
        integralTests("2", "2x");
        integralTests("x^3", "x⁴/4");
        integralTests("3x²", "x³");
        integralTests("2cosx", "2sin(x)");
        integralTests("3sinx", "-3cos(x)");
        integralTests("cos(3x)", "sin(3x)/3");
        integralTests("6cos(2x)", "3sin(2x)");
        integralTests("tanx+3x²", "-ln|cos(x)|+x³");
        integralTests("1/x", "ln|x|");
        integralTests("2/x", "2ln|x|");
        integralTests("e^x", "e^x");
        integralTests("ln(2x)", "x(ln(2x)-1)");
        integralTests("2*logx", "2x(ln(x)-1)/ln10");
        integralTests("3*(secx)^2", "3tan(x)");
        integralTests("√x", "(2/3)x^(3/2)");

        integralTests("sin(cosx)", "ʃ(sin(cos(x)))");

        evaluationTest("int(cosx)", "sin(x) + C");

        numericalIntegralTests("sin(cos(x))", 2, 4, -1.4628367214483);
        numericalIntegralTests("sinx", 1, 3, 1.53029480246859);
        numericalIntegralTests("x", 1, 3, 4);
//        numericalIntegralTests("sin(cosx)", 1, 5, -1.5910876205726525);
    }

    public void integralTests(String input, String expected){
        input = input.trim();
        String integral = Integral.calculate(input).infix().trim();
        expected = expected.trim();
        if (!integral.equals(expected)){
            System.out.println("\nIntegration Test Failed:");
            System.out.println("     Input:         "+input);
            System.out.println("     Integral:      "+integral);
            System.out.println("     Expected:      "+expected);
            System.exit(1);
        }
        incrementNumTests();
    }
    public void numericalIntegralTests(String input, double a, double b, double expected){
        input = input.trim();
        double nint = Engine.evaluate("∫("+input+", "+a+", "+b+")");
        if (nint != expected){
            System.out.println("\nIntegration Test Failed:");
            System.out.println("     Input:         ʃ["+input+", "+a+", "+b+"]");
            System.out.println("     Nint:          "+nint);
            System.out.println("     Expected:      "+expected);
            System.exit(1);
        }
        incrementNumTests();
    }
}
