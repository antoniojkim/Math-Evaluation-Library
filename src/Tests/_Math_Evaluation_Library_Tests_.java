package Tests;

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

                new Simplify_Tests("Simplify"),
                new Engine_Tests("Engine"),
                new Unit_Conversion_Tests("Unit Conversion"),
                new Summation_Tests("Summation"),
                new Combinatorics_Tests("Combinatorics"),
                new Propositional_Logic_Tests("Propositional Logic"),
                new Binary_Tests("Binary"),

                new Derivative_Tests("Derivative"),
                new Integral_Tests("Integral"),

                new Matrix_Tests("Matrix")
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
