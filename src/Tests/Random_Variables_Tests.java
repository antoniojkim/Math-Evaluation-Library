package Tests;

/**
 * Created by Antonio on 2017-10-01.
 */
public class Random_Variables_Tests extends Engine_Tests {

    public Random_Variables_Tests(String name) {
        super(name);
    }

    @Override
    public void run() {

        evaluationTest("hyp(52, 4, 5, 3)+hyp(52, 4, 5, 4)", 0.001754547973035368);
        evaluationTest("hyp(52, 4, 5, 0)+hyp(52, 4, 5, 1)", 0.958315633945886);
        evaluationTest("hypexp(52, 4, 5)", 0.38461538461538464);
        evaluationTest("hypvar(52, 4, 5)", 0.32718412808910546);

        evaluationTest("∑(Bin(20, 0.5, {i}), 0, 2)", 2.0122528076171875E-4);
        evaluationTest("Binexp(20, 0.5)", 10);
        evaluationTest("Binvar(20, 0.5)", 5);

        evaluationTest("NB(2, 0.2, 6)", 0.07340032000000003);
        evaluationTest("NBexp(2, 0.2)", 8);
        evaluationTest("NBvar(2, 0.2)", 40);

        evaluationTest("geo(0.2, 6)", 1.6777216000000013E-5);
        evaluationTest("geoexp(0.2)", 4);
        evaluationTest("geovar(0.2)", 20);

        evaluationTest("poi(10, 6)", 0.06305545800345118);
        evaluationTest("poiexp(10)", 10);
        evaluationTest("poivar(10)", 10);

        evaluationTest("erf0", 0);
        evaluationTest("erf(0.5)", 0.5205);

        evaluationTest("norm(0)", 0.5);
        evaluationTest("norm.32", 0.625516);
        evaluationTest("norm1.24", 0.892512);

        evaluationTest("norminv(0.5)", 0);
        evaluationTest("norminv0.7", 0.5244);
        evaluationTest("norminv0.63", 0.3319);

    }
}
