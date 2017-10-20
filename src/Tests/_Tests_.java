package Tests;

import Math_Evaluation_Library.Engine.Engine;

/**
 * Created by Antonio on 2017-07-11.
 */
public class _Tests_ {

    public _Tests_(String name){
        this.test_name = name;
    }

    public void run(){}

    private String test_name = "";

    public String getName() {
        return test_name;
    }

    public void setTest_name(String test_name) {
        this.test_name = test_name;
    }

    protected int num_tests = 0;
    protected static int total_tests = 0;

    public int getNum_tests() {
        return num_tests;
    }
    public static int getTotal_tests() {
        return total_tests;
    }
    
    public void incrementNumTests(){
        num_tests++;
        total_tests++;
    }

    public void assertionTest(String output, String expected){
        output = output.trim();
        expected = expected.trim();
        if (!output.equals(expected)){
            System.out.println(test_name+" Test Failed:");
            System.out.println("     Output:    "+output);
            System.out.println("     Expected:  "+expected);
            System.exit(1);
        }
        incrementNumTests();
    }
    public void assertionTest(int output, int expected){
        if (output != expected){
            System.out.println(test_name+" Test Failed:");
            System.out.println("     Output:     "+output);
            System.out.println("     Expected:  "+expected);
            System.exit(1);
        }
        incrementNumTests();
    }
    public void assertionTest(double output, double expected){
        if (output != expected){
            System.out.println(test_name+" Test Failed:");
            System.out.println("     Output:     "+output);
            System.out.println("     Expected:  "+expected);
            System.exit(1);
        }
        incrementNumTests();
    }

    public void assertionTest(boolean test){
        if (!test){
            System.out.println(test_name+" Test Failed");
            System.exit(1);
        }
        incrementNumTests();
    }
    public void assertionTest(boolean test, String message){
        if (!test){
            System.out.println(test_name+" Test Failed:   "+message);
            System.exit(1);
        }
        incrementNumTests();
    }
    public void assertionTest(boolean test, String input, String expected){
        if (!test){
            System.out.println(test_name+" Test Failed:");
            System.out.println("     Input:     "+input);
            System.out.println("     Expected:  "+expected);
            System.exit(1);
        }
        incrementNumTests();
    }
    public void assertionTest(boolean test, String input, String expected, String actual){
        if (!test){
            System.out.println(test_name+" Test Failed:");
            System.out.println("     Input:     "+input);
            System.out.println("     Expected:  "+expected);
            System.out.println("     Actual  :  "+actual);
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
            System.out.println("\n"+test_name+" Test Failed:");
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
            System.out.println("\n"+test_name+" Test Failed:");
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
