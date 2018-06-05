package Tests;

import Math_Evaluation_Library.Engine.Engine;
import Math_Evaluation_Library.Expressions.Expression;

/**
 * Created by Antonio on 2017-07-11.
 */
public class _Math_Evaluation_Library_Tests_ {

    // mvn install:install-file -Dfile="Math Evaluation Library.jar" -DgroupId=jhk-math-library -DartifactId=jhk-math-library -Dversion=1.0.1 -Dpackaging=jar

    static boolean inSeconds = true;

    public static void main (String[] args){

        runTests();
//        runTrial();
    }

    public static void runTrial(){
//        new Scanner_Tests("Scanner").run();
//        new Postfix_Tests("Postfix").run();
//        new Fraction_Tests("Fraction").run();
//        new Random_Variables_Tests("RV").evaluationTest("Exp(hyp())", "Exp(X ~ Hypergeometric(N, r, n)) = n×r/N");
//        new Engine_Tests("Engine").evaluationTest("exp2", 7.38905609893065);
//        new Simplify_Tests("Simplify").simplificationTest("sin(1-i)", "sin(1-i)");
//        String input = "5⁰²(sin3)";
//        System.out.println(Engine.fixSyntax(input));
//        Expression e = Engine.toExpression(input);
//        System.out.println(e.toString());
//        System.out.println(e.toTeX());
    }

    public static void speedTest(){

        /*
        0.05770045938362367
        6.554458
        0.05770045938362367
        0.300642
        3 3 1 2 + * sin * 4 5 8 + / ln +
         */

        String function = "sin(cos(tan(x)))";
        Expression e = Engine.toExpression(function);
        System.out.println(e.infix());
        System.out.println(e.postfix());
//        System.out.println(pre.evaluate(4));
//        System.out.println(Engine.evaluate(function, 4));
        int trials = 1;
        double avg = 0;
        long start = 0, end = 0;
        double sum = 0;
        for (int i = 0; i<trials; ++i){
            start = System.nanoTime();
            Expression evaluated = e.calculateDerivative();
//            Expression integral = e.calculateIntegral();
            end = System.nanoTime();
            avg += (end-start);
            System.out.println(evaluated.infix());
//            System.out.println(n1);
//            if (n2 instanceof MatrixExpression){
//                System.out.println(((MatrixExpression)n2.evaluateExpression()).strMatrix());
//            }
//            else{
//                System.out.println(n2.evaluate());
//            }
        }
        System.out.println(trials+" trials");
        System.out.println("Average:   "+(avg/(trials*1000000.0)));
        System.out.println("Total:     "+(avg/(1000000.0)));

    }

    public static void runTest(){
        //new Engine_Tests("Engine").InfixToPostfixTest("sinx*-sinx", "");
        new Simplify_Tests("Simplify").simplificationTest("-1/(|x|√(x²-1))", "-1/(|x|√(x²-1))");
        //System.out.println(Engine.toExpression("2*3").infix());
    }

    public static void runTests(){

        _Tests_[] tests = {
                new Sort_Tests("Sort"),
                new Search_Tests("Search"),
                new Script_Tests("Script"),

                new Scanner_Tests("Scanner"),
                new Postfix_Tests("Postfix"),
                new Simplify_Tests("Simplify"),
                new Engine_Tests("Engine"),
                new LaTeX_Tests("LaTeX"),
                new Complex_Number_Tests("Complex Numbers"),
                new Unit_Conversion_Tests("Unit Conversion"),
                new Summation_Tests("Summation"),
                new Combinatorics_Tests("Combinatorics"),
                new Random_Variables_Tests("Random Variables"),
                new Stats_Tests("Statistics"),
                //new Propositional_Logic_Tests("Propositional Logic"),
                new Binary_Tests("Binary"),

                new Derivative_Tests("Derivative"),
                new Integral_Tests("Integral"),

                new Matrix_Tests("Matrix"),

                new Fraction_Tests("Fraction")
        };

        long overallStart = System.currentTimeMillis();
        int groupTests = 3;
        for (int i = 0; i<tests.length; i+=groupTests){
            _Tests_[] group = new _Tests_[groupTests];
            for (int j = 0; j<groupTests && i+j<tests.length; j++){
                group[j] = tests[i+j];
            }
            long[] startTimes = new long[groupTests];
            long[] endTimes = new long[groupTests];

            for (int j = 0; j<groupTests && i+j<tests.length; j++){
                startTimes[j] = System.currentTimeMillis();
                group[j].run();
                endTimes[j] = System.currentTimeMillis();
            }
            for (int j = 0; j<groupTests && i+j<tests.length; j++){
                if (group[j].getName().length() > 0 && group[j].getNum_tests() > 0){
                    if (j != 0){
                        for (int k = 0; k<(30-group[j-1].getName().length()); k++){
                            System.out.print(" ");
                        }
                    }
                    System.out.print(group[j].getName()+" Test Suite complete.");
                }
            }
            System.out.println();
            System.out.print("       ");
            for (int j = 0; j<groupTests && i+j<tests.length; j++){
                if (group[j].getName().length()
                        > 0 && group[j].getNum_tests() > 0){
                    if (j != 0){
                        System.out.print("                   ");
                    }
                    System.out.print("Took "+time(startTimes[j], endTimes[j])+" to run "+group[j].getNum_tests()+(group[j].getNum_tests() > 1 ? " tests" : " test"));
                }
            }
            System.out.println();
        }
        long overallEnd = System.currentTimeMillis();

        System.out.println("\nA total of "+_Tests_.getTotal_tests()+" tests have passed!\n");
        System.out.println("Took "+time(overallStart, overallEnd)+" to complete testing\n");
    }

    public static String time(long start, long end){
        return (inSeconds ? ((end-start)/1000.0)+" Seconds" : (end-start)+" Milliseconds");
    }

}
