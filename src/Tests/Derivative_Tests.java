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
        derivativeTests("x^3", "3x²");
        derivativeTests("3*x^3", "9x²");
        derivativeTests("(3x)^3", "9(3x)²");

        derivativeTests("sinx", "cosx");
        derivativeTests("2*sinx", "2cosx");
        derivativeTests("2sinx", "2cosx");
        derivativeTests("sin(2x)", "2cos(2x)");
        derivativeTests("2sin(3x)", "6cos(3x)");
        derivativeTests("cosx", "-sinx");
        derivativeTests("cos(x)", "-sinx");
        derivativeTests("arccscx", "-1/(|x|√(x²-1))");
        derivativeTests("sinx+cosx", "cosx-sinx");
        derivativeTests("cosx+sinx", "-sinx+cosx");
        derivativeTests("sinx+sin(cos(tanx))", "(sinx+sin(cos(tanx)))'");

//        derivativeTests("sinx*cosx", "");

        derivativeTests("ln(x)", "1/x");
        derivativeTests("|x|", "x/|x|");

        derivativeTests("e^x", "ℯ^x");
        derivativeTests("e^(2x)", "2ℯ^(2x)");
        derivativeTests("2^x", "2^x*ln2");

        // Unable to Differentiate
        derivativeTests("sin(cos(tanx))", "(sin(cos(tanx)))'");
    }

    public void derivativeTests(String input, String expected){
        input = input.trim();
        String derivative = Derivative.calculate(input).trim();
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
