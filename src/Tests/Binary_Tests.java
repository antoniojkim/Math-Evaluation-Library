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

        evaluationTest("tobin(10)", "1010");
        evaluationTest("bin(1010)", "10");

    }
}
