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

        evaluationTest("5!", 120);
        evaluationTest("170!", 7.257415615307994E306);
        evaluationTest("171!", "NaN");
        evaluationTest("171P50", 1.533200272194981E108);
        evaluationTest("171C50", 5.041084940545287E43);

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

        evaluationTest("738C10", 1.2422381175008683E22);
        evaluationTest("738C138", 1.0166709548451912E153);
        evaluationTest("910C460", 2.166504014733614E272);

        evaluationTest("Γ(4)", 6);
        evaluationTest("Γ5", "24");
        evaluationTest("(1.5)!", 1.329340388179138);


    }
}
