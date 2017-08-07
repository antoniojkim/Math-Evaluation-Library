package Tests;

import Math_Evaluation_Library.Engine.Engine;

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

        InfixToPostfixTest("sin{x}", "{x} sin");
        InfixToPostfixTest("sec²x", "x sec 2 ^");

        InfixToPostfixTest("-1/(|x|√(x²-1))", "-1 x abs x 2 ^ 1 - √ * /");
        evaluationTest("-1/(|-3|√(2²-1))", -0.19245008972987526);

        InfixToPostfixTest("(2/3)x^(3/2)", "2 3 / x 3 2 / ^ *");

        syntaxTest("|-1|",   "(abs(-1))");
        evaluationTest("|-1|",      1);
        evaluationTest("|-1|*|-2|", 2);

        evaluationTest("[x^2, 2]", 4);
        evaluationTest("[x^2, m]", "NaN");

        InfixToPostfixTest("sin(cosx)", "x cos sin");
        InfixToPostfixTest("sin(cos(tanx))", "x tan cos sin");

        evaluationTest("gcd(12, 18, 36)", 6);

        InfixToPostfixTest("heron(6, 6, 6)", "6 6 6 3 heron");
        evaluationTest("heron(6, 6, 6)", 15.588457268119896);

        evaluationTest("[1mi->km]", 1.609344);
        evaluationTest("[2.356mth->s]", 6195862.90751904);
        evaluationTest("[2.356km->s]", "NaN");
        evaluationTest("[1m/s->km/h]", 3.6);
        evaluationTest("unit(1, m/s, km/h)", 3.6);

        evaluationTest("proj([1, 2], [3, 4])", "11/25·[3, 4] = [33/25, 132/25] is the projection of [1, 2] onto [3, 4]");

        syntaxTest("-5x²y+3y+8", "-5*x²*y+3*y+8");
        InfixToPostfixTest("-5x²y+3y+8", "-5 x 2 ^ * y * 3 y * + 8 +");
        evaluationTest("[-5x²y+3y+8, 1, 2]", 4);
        //
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
        String parsed = Engine.toPostfix(input).trim();
        expected = expected.trim();
        if (!parsed.equals(expected)){
            System.out.println("\nInfix to Postfix Test Failed:");
            System.out.println("     Input:     "+input);
            System.out.println("     Parsed:    "+parsed);
            System.out.println("     Expected:  "+expected);
            System.exit(1);
        }
        incrementNumTests();
    }
    public void evaluationTest(String input, String expected){
        input = input.trim();
        String evaluated = Engine.evaluateString(input).trim();
        expected = expected.trim();
        if (!evaluated.equals(expected)){
            String syntax = Engine.fixSyntax(input).trim();
            String postfix = Engine.toPostfix(input);
            System.out.println("\nEvaluation Test Failed:");
            System.out.println("     Input:     "+input);
            System.out.println("     Syntax:    "+syntax);
            System.out.println("     Postfix:   "+postfix);
            System.out.println("     Actual:    "+evaluated);
            System.out.println("     Expected:  "+expected);
            System.exit(1);
        }
        incrementNumTests();
    }
    public void evaluationTest(String input, double expected){
        input = input.trim();
        double evaluated = Engine.evaluate(input);
        if (evaluated != expected){
            String syntax = Engine.fixSyntax(input).trim();
            String postfix = Engine.toPostfix(input);
            System.out.println("\nEvaluation Test Failed:");
            System.out.println("     Input:     "+input);
            System.out.println("     Syntax:    "+syntax);
            System.out.println("     Postfix:   "+postfix);
            System.out.println("     Actual:    "+evaluated);
            System.out.println("     Expected:  "+expected);
            System.exit(1);
        }
        incrementNumTests();
    }

}
