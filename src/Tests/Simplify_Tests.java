package Tests;

import Math_Evaluation_Library.Engine.Engine;
import Math_Evaluation_Library.Miscellaneous.Simplify;

/**
 * Created by Antonio on 2017-07-14.
 */
public class Simplify_Tests extends _Tests_ {

    public Simplify_Tests(String name) {
        super(name);
    }

    @Override
    public void run(){
        runTests();
    }

    public void runTests(){
        simplificationTest("5+", "Invalid Syntax Parsing Error:  5 +");

        simplificationTest("2*3", "6");
        simplificationTest("2*x", "2x");
        simplificationTest("2*3*x", "6x");
        simplificationTest("x*2*3", "6x");
        simplificationTest("2*x*3", "6x");
        simplificationTest("2*x*3*2", "12x");
        simplificationTest("2*x*3*2*2", "24x");
        simplificationTest("2*3*x*2", "12x");
        simplificationTest("x^2*x^3", "x⁵");
        simplificationTest("x^2", "x²");

        simplificationTest("sinx/cosx", "tanx");
        simplificationTest("cosx/sinx", "cotx");
        simplificationTest("(sinx)^2", "sin²x");
        simplificationTest("(sinx)^2+(cosx)^2", "1");
        simplificationTest("2*(sinx)^2+2*(cosx)^2", "2");
        simplificationTest("1*sinx", "sinx");
        simplificationTest("-1*sinx", "-sinx");
        simplificationTest("cosx*cosx", "cos²x");
        simplificationTest("sinx*-sinx", "-sin²x");

        simplificationTest("-1/(|x|√(x²-1))", "-1/(|x|√(x²-1))");
        simplificationTest("-1/(|-3|√(2²-1))", "-1/(|-3|√3)");

        simplificationTest("abs(x)", "|x|");
        simplificationTest("1/(|x||x|)", "1/|x|²");

        simplificationTest("1/2*3", "3/2");

        simplificationTest("x!", "x!");

        simplificationTest("gcd(2, 3, 4)", "Invalid Syntax Parsing Error:  gcd 3 2 3 4");


    }

    public void simplificationTest(String input, String expected){
        input = input.trim();
        String simplified = Simplify.simplify(input).trim();
        expected = expected.trim();
        if (!simplified.equals(expected)){
            System.out.println("\nSimplification Test Failed:");
            System.out.println("     Input:         "+input);
            System.out.println("     Posfix:        "+ Engine.toPostfix(input));
            System.out.println("     Simplified:    "+simplified);
            System.out.println("     Expected:      "+expected);
            System.exit(1);
        }
        incrementNumTests();
    }

}
