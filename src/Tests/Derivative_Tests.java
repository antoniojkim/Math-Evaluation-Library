package Tests;

import Math_Evaluation_Library.Calculus.Derivative;

/**
 * Created by Antonio on 2017-07-13.
 */
public class Derivative_Tests extends _Tests_ {

    public Derivative_Tests(String name){
        super(name);
    }

    @Override
    public void run(){
        derivativeTests("23", "0");

        derivativeTests("x", "1");
        derivativeTests("√x", "1/(2√x)");
        derivativeTests("x^3", "3x²");
        derivativeTests("3*x^3", "9x²");
        derivativeTests("(3x)^3", "9(3x)²");

        derivativeTests("sinx", "cos(x)");
        derivativeTests("2*sinx", "2cos(x)");
        derivativeTests("2sinx", "2cos(x)");
        derivativeTests("sin(2x)", "2cos(2x)");
        derivativeTests("2sin(3x)", "6cos(3x)");
        derivativeTests("cosx", "-sin(x)");
        derivativeTests("cos(x)", "-sin(x)");
        derivativeTests("arccscx", "-1/(|x|√(x²-1))");
        derivativeTests("sinx+cosx", "cos(x)-sin(x)");
        derivativeTests("cosx+sinx", "-sin(x)+cos(x)");
        derivativeTests("sinx+sin(cos(tanx))", "cos(x)-sin(tan(x))sec²(x)*cos(cos(tan(x)))");

//        derivativeTests("sinx*cosx", "");

        derivativeTests("ln(x)", "1/x");
        derivativeTests("|x|", "|x|/x");

        derivativeTests("e^x", "ℯ^x");
        derivativeTests("e^(2x)", "2ℯ^(2x)");
        derivativeTests("2^x", "2^x*ln2");

        derivativeTests("sin(cos(tanx))", "-sin(tan(x))sec²(x)*cos(cos(tan(x)))");

        evaluationTest("deriv(x^3, 2)", "6x");
    }

    public void derivativeTests(String input, String expected){
        input = input.trim();
        String derivative = Derivative.calculate(input).toString();
        expected = expected.trim();
        if (!derivative.equals(expected)){
            System.out.println("\nDerivative Test Failed:");
            System.out.println("     Input:         "+input);
            System.out.println("     Derivative:    "+derivative);
            System.out.println("     Expected:      "+expected);
            System.exit(1);
        }
        incrementNumTests();
    }

}
