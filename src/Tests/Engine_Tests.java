package Tests;

import static Math_Evaluation_Library.Engine.Engine.separator;

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
        evaluationTest("randomint(1, 10)", 1, 10);
        evaluationTest("-1/(|-3|√(2²-1))", -0.19245008972987526);

        evaluationTest("|-1|",      1);
        evaluationTest("|-1|*|-2|", 2);

        evaluationTest("x^2"+separator+"2", 4);
//        evaluationTest("[x^2, 2]", 4);
        evaluationTest("x^2"+separator+"m", "NaN");
        evaluationTest("-4y+3x"+separator+"-0.7,-2.4", "7.5");
//        evaluationTest("[-4y+3x,-0.7,-2.4]", "7.5");
        evaluationTest("calcab(-4y+3x,-0.7,-2.4)", "7.5");
        evaluationTest("(n)^2"+separator+"n=3", "9");

        evaluationTest("gcd(12, 18, 36)", 6);

        evaluationTest("heron(6, 6, 6)", 15.588457268119896);

        evaluationTest("proj([1, 2], [3, 4])", "11/25·[3, 4] = [33/25, 44/25] equals the projection of [1, 2] onto [3, 4]");

        evaluationTest("-5x²y+3y+8"+separator+"1,2", 4);
        //

        evaluationTest("n≔3*8/4+5", "11");
        evaluationTest("(n)*{n}", 121);

        evaluationTest("f:=x^2", "x²");
//        InfixToPostfixTest("f(2)", "2 2 ^");
//        evaluationTest("f(2)", 4);

        evaluationTest("postfix(sinx)", "x sin");
        evaluationTest("len(100101010010101010101010101111011101111010101101010101)", 54);
    }


}
