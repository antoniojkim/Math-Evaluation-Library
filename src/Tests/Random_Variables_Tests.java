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
        evaluationTest("pdf(hyp(52, 4, 5), 0)+PDF(hyp(52, 4, 5), 1)", 0.958315633945886);
        evaluationTest("Exp(hyp())", "Exp(X ~ Hypergeometric(N, r, n)) = n×r/N");
        evaluationTest("Exp(hyp(52, 4, 5))", 0.38461538461538464);
        evaluationTest("Var(hyp(52, 4, 5))", 0.32718412808910546);

        evaluationTest("∑(Bin(20, 0.5, {i}), 0, 2)", 2.0122528076171875E-4);
        evaluationTest("CDF(Bin(20, 0.5), 2)", 2.0122528076171875E-4);
        evaluationTest("rv:=Bin(20, 0.5)", "Bin(20, 0.5)");
        evaluationTest("Exp(rv)", 10);
        evaluationTest("Var(rv)", 5);

        evaluationTest("pf(NB(2, 0.2), 6)", 0.07340032000000003);
        evaluationTest("rv:=NB(2, 0.2)", "NB(2, 0.2)");
        evaluationTest("Exp(rv)", 8);
        evaluationTest("Var(rv)", 40);
        evaluationTest("Var(NB())", "Var(X ~ NegativeBinomial(k, p)) = k×(1-p)/p²");

        evaluationTest("geo(0.2, 6)", 1.6777216000000013E-5);
        evaluationTest("Exp(geo(0.2))", 4);
        evaluationTest("Var(geo(0.2))", 20);

        evaluationTest("poi(10, 6)", 0.06305545800345118);
        evaluationTest("rv:=poi(10)", "poi(10)");
        evaluationTest("Exp(rv)", 10);
        evaluationTest("Var(rv)", 10);
        evaluationTest("PDF(poi(10))", "P(X=x) = e^(-λ)*λˣ/x!; X ~ Poisson(λ)");

        evaluationTest("erf0", 0);
        evaluationTest("erf(0.5)", 0.5205);

        evaluationTest("rv:=exprv(0.7)", "exprv(0.7)");
        evaluationTest("1-exprv(1/0.7, 3)", 0.12245642825298186);
        evaluationTest("Exp(rv)", 0.7);
        evaluationTest("Var(rv)", 0.48999999999999994);

        evaluationTest("norm(0)", 0.5);
        evaluationTest("norm.32", 0.625516);
        evaluationTest("norm1.24", 0.892512);
        evaluationTest("norm(-1.29)", 0.098525);
        evaluationTest("cdf(ND(0, 1), 1.24)", 0.892512);
        evaluationTest("ND(0, 1, 1.24)", 0.892512);
        evaluationTest("1-CDF(ND(81, 6^2), 75)", 0.841345);
        evaluationTest("1-ND(81, 6^2, 75)", 0.841345);
        evaluationTest("1-CDF(ND(0, 2*0.1^2/25), -0.05, 0.05)", 0.0771);

        evaluationTest("norminv(0.5)", 0);
        evaluationTest("norminv0.7", 0.5244);
        evaluationTest("norminv0.63", 0.3319);

        evaluationTest("mnd(5, 1/4, 1/4, 1/4, 1/4, 2, 2, 1, 0)", 0.029296875);
        evaluationTest("mnd(5, 0.25, 0.25, 0.25, 0.25, 2, 2, 1, 0)", 0.029296875);

    }
}
