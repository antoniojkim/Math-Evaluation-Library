package Tests;

import Math_Evaluation_Library.Engine.Engine;
import Math_Evaluation_Library.Expressions.Expression;

import static Math_Evaluation_Library.Engine.Engine.separator;
import static Math_Evaluation_Library.Engine.Engine.toExpression;

/**
 * Created by Antonio on 2017-07-11.
 */
public class Engine_Tests extends _Tests_{

    public Engine_Tests(String name){
        super(name);
    }

    @Override
    public void run(){
        evaluationTest("-1", -1);
        evaluationTest("2*3-2", 4);
        evaluationTest("-e", -2.71828182845904523536);
        evaluationTest("sin(pi/6)", 0.5);
        evaluationTest("exp2", 7.38905609893065);
        evaluationTest("64∨2", 66);
        evaluationTest("floor_2(63)", 32);
        evaluationTest("floor_2(64)", 64);
        evaluationTest("floor_2(65)", 64);
        evaluationTest("ln(ln10/ln2)/ln3", 1704233/1559534.0);

        InfixToPostfixTest("sin{i}", "i sin");
        InfixToPostfixTest("sec²x", "x sec 2 ^");

        InfixToPostfixTest("-1/(|x|√(x²-1))", "-1 x abs x 2 ^ 1 - √ * /");
        evaluationTest("-1/(|-3|√(2²-1))", -0.19245008972987526);

        InfixToPostfixTest("(2/3)x^(3/2)", "2 3 / x 3 2 / ^ *");

        syntaxTest("|-1|",   "(abs(-1))");
        evaluationTest("|-1|",      1);
        evaluationTest("|-1|*|-2|", 2);

        evaluationTest("x^2"+separator+"2", 4);
        evaluationTest("[x^2, 2]", 4);
        evaluationTest("x^2"+separator+"m", "NaN");
        evaluationTest("-4y+3x"+separator+"-0.7,-2.4", "7.5");
        evaluationTest("[-4y+3x,-0.7,-2.4]", "7.5");
        evaluationTest("calcab(-4y+3x,-0.7,-2.4)", "7.5");
        evaluationTest("(n)^2"+separator+"n=3", "9");

        InfixToPostfixTest("sin(cosx)", "x cos sin");
        InfixToPostfixTest("sin(cos(tanx))", "x tan cos sin");

        evaluationTest("gcd(12, 18, 36)", 6);

        InfixToPostfixTest("heron(6, 6, 6)", "heron 6 6 6");
        evaluationTest("heron(6, 6, 6)", 15.588457268119896);

        evaluationTest("proj([1, 2], [3, 4])", "11/25·[3, 4] = [33/25, 44/25] equals the projection of [1, 2] onto [3, 4]");

        syntaxTest("-5x²y+3y+8", "-5*x²*y+3*y+8");
        InfixToPostfixTest("-5x²y+3y+8", "-5 x 2 ^ * y * 3 y * + 8 +");
        evaluationTest("-5x²y+3y+8"+separator+"1,2", 4);
        //
        syntaxTest("x-+3", "x-3");

        evaluationTest("n≔3*8/4+5", "11");
        evaluationTest("(n)*{n}", 121);

        evaluationTest("f:=x^2", "x²");
        InfixToPostfixTest("f(2)", "2 2 ^");
        evaluationTest("f(2)", 4);

        evaluationTest("postfix(sinx)", "x sin");
        evaluationTest("len(100101010010101010101010101111011101111010101101010101)", 54);
    }

    public void syntaxTest(String input, String expected){
        input = input.trim();
        String fixed = Engine.fixSyntax(input).trim();
        expected = expected.trim();
        if (!fixed.equals(expected)){
            System.out.println("\nSyntax Test Failed:");
            System.out.println("     Input:     "+input);
            System.out.println("     Fixed:     "+fixed);
            System.out.println("     Expected:  "+expected);
            System.exit(1);
        }
        incrementNumTests();
    }
    public void InfixToPostfixTest(String input, String expected){
        input = input.trim();
        Expression e = toExpression(input);
        String postfix = e.postfix();
        if (!postfix.equals(expected)){
            System.out.println("\nInfix to Postfix Test Failed:");
            System.out.println("     Input:     "+input);
            System.out.println("     Syntax:    "+Engine.fixSyntax(input));
            System.out.println("     Infix:     "+e.infix());
            System.out.println("     Postfix:   "+postfix);
            System.out.println("     Expected:  "+expected);
            System.exit(1);
        }
        incrementNumTests();
    }


}
