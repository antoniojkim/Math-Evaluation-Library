package Tests;

import Math_Evaluation_Library.Engine.Engine;
import Math_Evaluation_Library.Objects._Number_;
import org.jblas.DoubleMatrix;

/**
 * Created by Antonio on 2017-07-11.
 */
public class _Math_Evaluation_Library_Tests_ {

    static boolean inSeconds = true;

    public static void main (String[] args){

        runTests();

    }

    public static void runTests(){
        _Tests_[] tests = {
                new Sort_Tests("Sort"),
                new Search_Tests("Search"),
                new Script_Tests("Script"),

                new Engine_Tests("Engine"),
                new Simplify_Tests("Simplify"),

                new Derivative_Tests("Derivative"),
                new Integral_Tests("Integral"),

                new Matrix_Tests("Matrix")
        };

        long overallStart = System.currentTimeMillis();
        for (_Tests_ test : tests){
            long start = System.currentTimeMillis();
            test.run();
            long end = System.currentTimeMillis();
            if (test.getName().length() > 0 && test.getNum_tests() > 0){
                System.out.println(test.getName()+" module tests complete. Took "+time(start, end)+" to run "+test.getNum_tests()+(test.getNum_tests() > 1 ? " tests" : " test"));
            }
        }
        long overallEnd = System.currentTimeMillis();

        System.out.println("\nA total of "+_Tests_.getTotal_tests()+" tests have passed!\n");
        System.out.println("Took "+time(overallStart, overallEnd)+" to complete testing\n");
    }

    public static String time(long start, long end){
        return (inSeconds ? ((end-start)/1000.0)+" Seconds" : (end-start)+" Milliseconds");
    }

}
