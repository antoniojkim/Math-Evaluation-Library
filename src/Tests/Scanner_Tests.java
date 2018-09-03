package Tests;

import Math_Evaluation_Library.Engine.Scanner;

import java.util.List;

/**
 * Created by Antonio on 2017-10-01.
 */
public class Scanner_Tests extends Engine_Tests {

    public Scanner_Tests(String name) {
        super(name);
    }

    @Override
    public void run() {

        scannerTest("2x+3x", "2 * x + 3 * x");
        scannerTest("0.2x+0.3x", "0.2 * x + 0.3 * x");
        scannerTest("3x-2x", "3 * x - 2 * x");
        scannerTest("-5x²y+3y+8", "-5 * x ^ 2 * y + 3 * y + 8");
        scannerTest("x-+3", "x - 3");

        scannerTest("2sin(cosx)", "2 * sin ( cos x )");
        scannerTest("sin²(x)", "sin ² ( x )");
        scannerTest("(sinx)²", "( sin x ) ²");
        scannerTest("(sinx)^2", "( sin x ) ^ 2");
        scannerTest("1/sinx", "1 / sin x");
        scannerTest("sinx*-sinx", "sin x * - sin x");

        scannerTest("-1/(|x|√(x²-1))", "-1 / ( abs ( x ) * √ ( x ^ 2 - 1 ) )");
        scannerTest("1/(|x||x|)", "1 / ( abs ( x ) * abs ( x ) )");
        scannerTest("|-1|", "abs ( -1 )");

        scannerTest("e^x", "e ^ x");
        scannerTest("-e", "-e");
        scannerTest("5-e", "5 - e");
        scannerTest("3-exp(3)", "3 - exp ( 3 )");
        scannerTest("3*e^x", "3 * e ^ x");
        scannerTest("exp(sinxy)", "exp ( sin x * y )");
        scannerTest("ln(ln10/ln2)/ln3", "ln ( ln 10 / ln 2 ) / ln 3");
        scannerTest("frhex(4E)", "frhex ( 4 E )");

        scannerTest("{1, 2}*{3, 4}", "{ 1 , 2 } * { 3 , 4 }");
        scannerTest("[[1,2,3], [3,2,1], [1,2,3]]*[[4,5,6], [6,5,4], [4,6,5]]",
                "[ [ 1 , 2 , 3 ] , [ 3 , 2 , 1 ] , [ 1 , 2 , 3 ] ] * [ [ 4 , 5 , 6 ] , [ 6 , 5 , 4 ] , [ 4 , 6 , 5 ] ]");
        scannerTest("{1, 2}^-1", "{ 1 , 2 } ᴵ");
        scannerTest("{1, 2}*{{3}, {4}}", "{ 1 , 2 } * { { 3 } , { 4 } }");

        scannerTest("xx", "x * x");
        scannerTest("xy", "x * y");
        scannerTest("-(2(1+x^2+y^2)-8x^2)/(1+x^2+y^2)^3", "- ( 2 * ( 1 + x ^ 2 + y ^ 2 ) - 8 * x ^ 2 ) / ( 1 + x ^ 2 + y ^ 2 ) ^ 3");

        scannerTest("postfix(sinx)", "RPN ( sin x )");
        scannerTest("max(1, 2)", "max ( 1 , 2 )");
        scannerTest("3cos(xy)+8", "3 * cos ( x * y ) + 8");
        scannerTest("171C50", "171 C 50");
        scannerTest("171P50", "171 P 50");
        scannerTest("170!", "170 !");
        scannerTest("Γ(4)", "Γ ( 4 )");
        scannerTest("stndv_(2, 5, 23, 6, 3, 6, 43, 4)", "stndv_ ( 2 , 5 , 23 , 6 , 3 , 6 , 43 , 4 )");
        scannerTest("∑({j}!, j, 0, 2)", "∑ ( { j } ! , j , 0 , 2 )");

        scannerTest("pdf(hyp(52, 4, 5), 0)+PDF(hyp(52, 4, 5), 1)", "pdf ( hyp ( 52 , 4 , 5 ) , 0 ) + pdf ( hyp ( 52 , 4 , 5 ) , 1 )");
        scannerTest("Var(hyp(52, 4, 5))", "Var ( hyp ( 52 , 4 , 5 ) )");
        scannerTest("Exp(geo(0.2))", "Exp ( geo ( 0.2 ) )");
        scannerTest("norm.32", "norm 0.32");

        scannerTest("[1m/s->km/h]", "[ 1 m / s → km / h ]");
        scannerTest("[1m->km]", "[ 1 m → km ]");
        scannerTest("[30°C->°F]", "[ 30 °C → °F ]");
        scannerTest("unit(1, m/s, km/h)", "unit ( 1 , m / s , km / h )");
        scannerTest("unit(1, m, km)", "unit ( 1 , m , km )");
        scannerTest("√(7171/16)", "√ ( 7171 / 16 )");
        scannerTest("(2+3i)+(3+4i)", "( 2 + 3 i ) + ( 3 + 4 i )");
        scannerTest("sin(3", "sin ( 3 )");
        scannerTest("sin3)", "( sin 3 )");
        scannerTest("gcd(2, 3, 4)", "gcd ( 2 , 3 , 4 )");
        scannerTest("sin(1-i)", "sin ( 1 - i )");
        scannerTest("sin(pi/6)", "sin ( π / 6 )");
        scannerTest("sin(π/6)", "sin ( π / 6 )");
        scannerTest("randomint(1, 10)", "randint ( 1 , 10 )");

        scannerTest("frhex(tohex(10))", "frhex ( tohex ( 10 ) )");
        scannerTest("frhex(0x4e5ab)", "frhex ( 0x4e5ab )");


    }

    public void scannerTest(String input, String expected){
        String actual = tokensToString(Scanner.scan(input));
        if (!actual.equals(expected)){
            System.out.println("\nScanner Test Failed:");
            System.out.println("     Input:     "+input);
            System.out.println("     Actual:    "+actual);
            System.out.println("     Expected:  "+expected);
            System.exit(1);
        }
        incrementNumTests();
    }

    public static String tokensToString(List<Scanner.Token> tokens){
        StringBuilder sb = new StringBuilder();
        if (tokens.size() > 0) {
            sb.append(tokens.get(0).toString());
            for (int i = 1; i < tokens.size(); i++){
                sb.append(" ").append(tokens.get(i).toString());
            }
        }
        return sb.toString();
    }
}
