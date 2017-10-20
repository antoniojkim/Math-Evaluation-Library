package Tests;

/**
 * Created by Antonio on 2017-10-01.
 */
public class Binary_Tests extends Engine_Tests {

    public Binary_Tests(String name) {
        super(name);
    }

    @Override
    public void run() {

        String[] tests = {"10", "11", "100", "101", "110", "111"};
        for (String test : tests){
            evaluationTest("bin(tobin("+test+"))", test);
        }

    }
}
