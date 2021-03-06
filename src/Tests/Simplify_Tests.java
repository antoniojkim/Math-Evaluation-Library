package Tests;

import Math_Evaluation_Library.Engine.Engine;
import Math_Evaluation_Library.Expressions.Expression;
import Math_Evaluation_Library.Miscellaneous.Simplify;

import static Math_Evaluation_Library.Engine.Engine.toExpression;

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
        simplificationTest("5+", "5");//"Invalid Syntax Parsing Error:  5 +"

        simplificationTest("2*3", "6");
        simplificationTest("2*x", "2x");
        simplificationTest("2*3*x", "6x");
        simplificationTest("x*2*3", "6x");
        simplificationTest("2*x*3", "6x");
        simplificationTest("2*x*3*2", "12x");
        simplificationTest("2*x*3*2*2", "24x");
        simplificationTest("2*3*x*2", "12x");
        simplificationTest("2x+3x", "5x");
        simplificationTest("2x+x", "3x");
        simplificationTest("x+2x", "3x");
        simplificationTest("3x-2x", "x");
        simplificationTest("3x-x", "2x");
        simplificationTest("x-3x", "-2x");
        simplificationTest("x^2*x^3", "x⁵");
        simplificationTest("x^2", "x²");

        simplificationTest("--sinx", "sin(x)");
        simplificationTest("sinx/cosx", "tan(x)");
        simplificationTest("cosx/sinx", "cot(x)");
        simplificationTest("1/sinx", "csc(x)");
        simplificationTest("(sinx)^2", "sin²(x)");
        simplificationTest("(sinx)^2+(cosx)^2", "1");
        simplificationTest("2*(sinx)^2+2*(cosx)^2", "2");
        simplificationTest("1*sinx", "sin(x)");
        simplificationTest("-1*sinx", "-sin(x)");
        simplificationTest("cosx*cosx", "cos²(x)");
        simplificationTest("sinx*-sinx", "-sin²(x)");

        simplificationTest("-1/(|x|√(x²-1))", "-1/(|x|√(x²-1))");
        simplificationTest("-1/(|-3|√(2²-1))", "-1/(|-3|√3)");

        simplificationTest("abs(x)", "|x|");
        simplificationTest("1/(|x||x|)", "1/|x|²");

        simplificationTest("1/2*3", "3/2");

        simplificationTest("x!", "x!");

        simplificationTest("gcd(2, 3, 4)", "gcd(2, 3, 4)");

        simplificationTest("sin(1-i)", "sin(1-i)");


    }

    public void simplificationTest(String input, String expected){
        input = input.trim();
        Expression simplified = Simplify.simplify(input);
        expected = expected.trim();
        if (!simplified.infix().trim().equals(expected)){
            System.out.println("\nSimplification Test Failed:");
            System.out.println("     Input:         "+input);
            System.out.println("     Syntax:        "+Engine.fixSyntax(input));
            System.out.println("     Postfix:       "+toExpression(input).postfix());
            System.out.println("     Simplified:    "+simplified.postfix());
            System.out.println("     Simplified:    "+simplified.infix().trim());
            System.out.println("     Expected:      "+expected);
            System.exit(1);
        }
        incrementNumTests();
    }

}
