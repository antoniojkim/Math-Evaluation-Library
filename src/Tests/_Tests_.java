package Tests;

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
}
