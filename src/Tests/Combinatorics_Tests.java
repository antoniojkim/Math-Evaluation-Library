package Tests;

/**
 * Created by Antonio on 2017-10-01.
 */
public class Combinatorics_Tests extends Engine_Tests {

    public Combinatorics_Tests(String name) {
        super(name);
    }

    @Override
    public void run() {

        evaluationTest("3*4!", 72);
        evaluationTest("3*4P2", 36);
        evaluationTest("3*4C2", 18);
        evaluationTest("72/4!", 3);
        evaluationTest("36/4P2", 3);
        evaluationTest("18/4C2", 3);
        evaluationTest("3*4P2+3*4C2", 54);
        evaluationTest("3*4P2-3*4C2", 18);
        evaluationTest("3*4P2*3*4C2", 648);
        evaluationTest("3*4P2/(3*4C2)", 2);

    }
}
