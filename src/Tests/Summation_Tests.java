package Tests;

/**
 * Created by Antonio on 2017-10-01.
 */
public class Summation_Tests extends Engine_Tests {

    public Summation_Tests(String name) {
        super(name);
    }

    @Override
    public void run(){

        evaluationTest("∑(4({i}-2)+5, 1, 1)", "1");
        evaluationTest("∑({i}, 1, 10)", "55");
        evaluationTest("∑(x, 1, n)", "n(n+1)/2");
        evaluationTest("∑(i, 1, n)", "n(n+1)/2");
        evaluationTest("∑(x+2, 1, n)", "n(n+5)/2");
        evaluationTest("∑(x-2, 1, n)", "n(n-3)/2");
        evaluationTest("∑(x+-3, 1, n)", "n(n-5)/2");
        evaluationTest("∑({i}!, 0, 2)", 4);
        evaluationTest("∑({j}!, j, 0, 2)", 4);
        evaluationTest("∑(∑({j}, j, 0, 1), 0, 1)", 2);

        evaluationTest("∏({i}, 1, 3)", 6);

    }
}
