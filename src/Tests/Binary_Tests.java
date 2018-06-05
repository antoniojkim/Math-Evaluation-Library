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
            evaluationTest("frbin(tobin("+test+"))", test);
            evaluationTest("frhex(tohex("+test+"))", test);
            evaluationTest("frtwo(totwo("+test+"))", test);
        }
        evaluationTest("frbin(1000011)", "67");
        evaluationTest("frbin(1000011001)", "537");
        evaluationTest("frbin(100101010010101010101010101111011101111010101101010101)", 1.049667108704546E16);


        evaluationTest("frtwo(1101111111)", "-129");
        evaluationTest("totwo(-129)", "101111111");
        evaluationTest("totwo(-129, 8)", "101111111");
        evaluationTest("totwo(-129, 10)", "1101111111");
        evaluationTest("totwo(129)", "010000001");



    }
}
