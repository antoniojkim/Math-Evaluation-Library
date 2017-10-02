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

        evaluationTest("Σ(4({i}-2)+5, 1, 1)", "1");
        evaluationTest("Σ({i}, 1, 10)", "55");
        evaluationTest("Σ(x, 1, n)", "n(n+1)/2");
        evaluationTest("Σ(i, 1, n)", "n(n+1)/2");
        evaluationTest("Σ(x+2, 1, n)", "n(n+5)/2");
        evaluationTest("Σ(x-2, 1, n)", "n(n-3)/2");
        evaluationTest("Σ(x+-3, 1, n)", "n(n-5)/2");
        evaluationTest("Σ({i}!, 0, 2)", 4);
        evaluationTest("Σ({j}!, j, 0, 2)", 4);
        evaluationTest("Σ(Σ({j}, j, 0, 5), 0, 5)", 90);

    }
}
