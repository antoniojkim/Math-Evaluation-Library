package Tests;

/**
 * Created by Antonio on 2017-10-01.
 */
public class Propositional_Logic_Tests extends Engine_Tests {

    public Propositional_Logic_Tests(String name) {
        super(name);
    }

    @Override
    public void run() {

        evaluationTest("prop(P∧R, t, t)", "T");
        evaluationTest("prop(((((P∧Q)→(¬R))∧(P→Q))→(P→(¬R))), t, t, t)", "T");
        evaluationTest("prop(((((P∧Q)→(¬R))∧(P→Q))→(P→(¬R))), t, t, f)", "T");
        evaluationTest("prop(((((P∧Q)→(¬R))∧(P→Q))→(P→(¬R))), t, f, t)", "T");
        evaluationTest("prop(((((P∧Q)→(¬R))∧(P→Q))→(P→(¬R))), t, f, f)", "T");
        evaluationTest("prop(((((P∧Q)→(¬R))∧(P→Q))→(P→(¬R))), f, t, t)", "T");
        evaluationTest("prop(((((P∧Q)→(¬R))∧(P→Q))→(P→(¬R))), f, t, f)", "T");
        evaluationTest("prop(((((P∧Q)→(¬R))∧(P→Q))→(P→(¬R))), f, f, t)", "T");
        evaluationTest("prop(((((P∧Q)→(¬R))∧(P→Q))→(P→(¬R))), f, f, f)", "T");
        evaluationTest("prop(((a→b)∧(c∨(¬b)))∨(c↔a), t, t, t)", "T");
        evaluationTest("prop(((a→b)∧(c∨(¬b)))∨(c↔a), t, f, f)", "F");
        evaluationTest("prop(t↓f)", "F");

    }
}
