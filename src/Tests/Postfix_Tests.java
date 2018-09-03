package Tests;

import Math_Evaluation_Library.Engine.Engine;
import Math_Evaluation_Library.Expressions.Expression;

import static Math_Evaluation_Library.Engine.Engine.toExpression;

public class Postfix_Tests extends Engine_Tests {

    public Postfix_Tests(String name) {
        super(name);
    }

    @Override
    public void run() {

        postfixTest("2x+3x", "2 x * 3 x * +");
        postfixTest("3x-2x", "3 x * 2 x * -");
        postfixTest("(2/3)x^(3/2)", "2 3 / x 3 2 / ^ *");

        postfixTest("2sin(cosx)", "2 x cos sin *");
        postfixTest("sin²(x)", "x sin 2 ^");
        postfixTest("sec²x", "x sec 2 ^");
        postfixTest("(sinx)²", "x sin 2 ^");
        postfixTest("(sinx)^2", "x sin 2 ^");
        postfixTest("1/sinx", "1 x sin /");
        postfixTest("sinx*-sinx", "x sin x sin neg *");
        postfixTest("sin(cosx)", "x cos sin");
        postfixTest("sin(cos(tanx))", "x tan cos sin");

        postfixTest("e^x", "e x ^");
        postfixTest("-e", "e neg");
        postfixTest("5-e", "5 e -");
        postfixTest("3-exp(3)", "3 3 exp -");
        postfixTest("3*e^x", "3 e x ^ *");
        postfixTest("exp(sinxy)", "x sin y * exp");
        postfixTest("ln(ln10/ln2)/ln3", "10 ln 2 ln / ln 3 ln /");
        postfixTest("frhex(4E)", "frhex 4e");

        postfixTest("-1/(|x|√(x²-1))", "-1 x abs x 2 ^ 1 - √ * /");
        postfixTest("1/(|x||x|)", "1 x abs x abs * /");
        postfixTest("(1, 2)*(3, 4)", "{1, 2} {3, 4} *");
        postfixTest("{1, 2}*{3, 4}", "{1, 2} {3, 4} *");

        postfixTest("[[1,2,3], [3,2,1], [1,2,3]]*[[4,5,6], [6,5,4], [4,6,5]]",
                "{{1, 2, 3}, {3, 2, 1}, {1, 2, 3}} {{4, 5, 6}, {6, 5, 4}, {4, 6, 5}} *");
        postfixTest("{1, 2}^-1", "{{1/5}, {2/5}}");
        postfixTest("{1, 2}*{{3}, {4}}", "{1, 2} {{3}, {4}} *");

        postfixTest("xx", "x x *");
        postfixTest("xy", "x y *");
        postfixTest("-(2(1+x^2+y^2)-8x^2)/(1+x^2+y^2)^3", "2 1 x 2 ^ + y 2 ^ + * 8 x 2 ^ * - 1 x 2 ^ + y 2 ^ + 3 ^ / neg");

        postfixTest("max(1, 2)", "max 1 2");
        postfixTest("3cos(xy)+8", "3 x y * cos * 8 +");
        postfixTest("171C50", "171 50 C");
        postfixTest("171P50", "171 50 P");
        postfixTest("170!", "170 !");
        postfixTest("Γ(4)", "4 Γ");
        postfixTest("stndv_(2, 5, 23, 6, 3, 6, 43, 4)", "stndv_ 2 5 23 6 3 6 43 4");
        postfixTest("∑({j}!, j, 0, 2)", "∑ j ! j 0 2");
        postfixTest("Exp(hyp())", "hyp Exp");

        postfixTest("pdf(hyp(52, 4, 5), 0)+PDF(hyp(52, 4, 5), 1)", "pdf hyp 52 4 5 0 pdf hyp 52 4 5 1 +");
        postfixTest("Exp(geo(0.2))", "geo 0.2 Exp");

        postfixTest("[1m/s->km/h]", "unit 1 m/s km/h");
        postfixTest("[1m->km]", "unit 1 m km");
        postfixTest("[30°C->°F]", "unit 30 °C °F");
        postfixTest("unit(1, m/s, km/h)", "unit 1 m/s km/h");
        postfixTest("unit(1, m, km)", "unit 1 m km");
        postfixTest("√(7171/16)", "7171 16 / √");
        postfixTest("(2+3i)+(3+4i)", "2 3i + 3 4i + +");
        postfixTest("sin(3", "3 sin");

        postfixTest("gcd(2, 3, 4)", "gcd 2 3 4");
        postfixTest("heron(6, 6, 6)", "heron 6 6 6");

        postfixTest("sin(1-i)", "1 i - sin");
        postfixTest("sin{i}", "i sin");



    }

    public void postfixTest(String input, String expected){
        Expression e = toExpression(input);
        if (!e.postfix().equals(expected)){
            System.out.println("\nPostfix Test Failed:");
            System.out.println("     Input:     "+input);
            System.out.println("     Syntax:    "+Engine.fixSyntax(input));
            System.out.println("     Actual:    "+e.postfix());
            System.out.println("     Expected:  "+expected);
            System.exit(1);
        }
        incrementNumTests();
    }

}
