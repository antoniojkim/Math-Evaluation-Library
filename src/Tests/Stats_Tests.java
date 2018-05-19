package Tests;

/**
 * Created by Antonio on 2017-10-01.
 */
public class Stats_Tests extends Engine_Tests {

    public Stats_Tests(String name) {
        super(name);
    }

    @Override
    public void run() {

//        evaluationTest("max(2, 5, 23, 6, 3, 6, 43, 4)", 43); // need to fix syntax
        evaluationTest("min(2, 5, 23, 6, 3, 6, 43, 4)", 2);
        evaluationTest("median(2, 5, 23, 6, 3, 6, 43, 4)", 5.5);
        evaluationTest("spread(2, 5, 23, 6, 3, 6, 43, 4)", "2, 3.5, 5.5, 14.5, 43");
        evaluationTest("spread(1, 2, 3, 4, 5, 6, 7)", "1, 2, 4, 6, 7");
        evaluationTest("IQR(2, 5, 23, 6, 3, 6, 43, 4)", 11);
        evaluationTest("stndv(2, 5, 23, 6, 3, 6, 43, 4)", 14.37259296617797);
        evaluationTest("stndv_(2, 5, 23, 6, 3, 6, 43, 4)", 14.37259296617797);
        evaluationTest("var(2, 5, 23, 6, 3, 6, 43, 4)", 206.5714285714286);
        evaluationTest("var_(2, 5, 23, 6, 3, 6, 43, 4)", 206.5714285714286);
        evaluationTest("kurt(2, 5, 23, 6, 3, 6, 43, 4)", 3.91099212019995);
        evaluationTest("skew(2, 5, 23, 6, 3, 6, 43, 4)", 1.55736729654775);
        evaluationTest("corr(2, 5, 23, 6, 3, 6, 43, 4, 14, 7)", -0.51906920912694);

    }
}
