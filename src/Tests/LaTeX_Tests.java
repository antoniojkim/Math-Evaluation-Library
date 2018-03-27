package Tests;

import Math_Evaluation_Library.Expressions.Expression;

import static Math_Evaluation_Library.Engine.Engine.toExpression;

/**
 * Created by Antonio on 2017-07-11.
 */
public class LaTeX_Tests extends _Tests_{

    public LaTeX_Tests(String name){
        super(name);
    }

    @Override
    public void run(){
        LaTeX_Test("2*3-2", "2\\times 3-2");
        LaTeX_Test("-e", "-e");

        LaTeX_Test("-1/(|-3|√(2²-1))", "\\dfrac{-1}{\\left|-3\\right|\\sqrt{{2}^{2}-1}}");

        LaTeX_Test("|-1|",      "\\left|-1\\right|");
        LaTeX_Test("|-1|*|-2|", "\\left|-1\\right|\\left|-2\\right|");

        LaTeX_Test("gcd(12, 18, 36)", "\\operatorname{gcd}\\left(12, 18, 36\\right)");
        LaTeX_Test("heron(6, 6, 6)", "\\operatorname{heron}\\left(6, 6, 6\\right)");

        LaTeX_Test("sum(x^2+2x, 1, 3)", "\\sum_{x=1}^{3} {x}^{2}+2x");
        LaTeX_Test("product(2/x, 1, 3)", "\\prod_{x=1}^{3} \\dfrac{2}{x}");
        LaTeX_Test("nint(sin(cosx), 1, 3)", "\\int_{1}^{3} \\sin\\left(\\cos\\left(x\\right)\\right) dx");

        LaTeX_Test("{{1,2,3},{3,2,1},{1,2,3}}*{{4,5,6},{6,5,4},{4,6,5}}", "\\begin{bmatrix} 1 & 2 & 3 \\\\ 3 & 2 & 1 \\\\ 1 & 2 & 3 \\end{bmatrix}\\begin{bmatrix} 4 & 5 & 6 \\\\ 6 & 5 & 4 \\\\ 4 & 6 & 5 \\end{bmatrix}");
        LaTeX_Test("{{1,2,3},{3,2,1},{1,2,3}}^-1", "\\begin{bmatrix} -\\dfrac{1}{12} & \\dfrac{1}{3} & -\\dfrac{1}{12} \\\\ \\dfrac{1}{24} & \\dfrac{1}{12} & \\dfrac{1}{24} \\\\ \\dfrac{1}{6} & -\\dfrac{1}{6} & \\dfrac{1}{6} \\end{bmatrix}");
        LaTeX_Test("RREF({{1,2,3},{3,2,1},{1,2,3}})", "\\operatorname{RREF}\\begin{bmatrix} 1 & 2 & 3 \\\\ 3 & 2 & 1 \\\\ 1 & 2 & 3 \\end{bmatrix}");
        LaTeX_Test("RREF({{1,2,3},{3,2,1},{1,2,3}}*{{4,5,6},{6,5,4},{4,6,5}})", "\\operatorname{RREF}\\left(\\begin{bmatrix} 1 & 2 & 3 \\\\ 3 & 2 & 1 \\\\ 1 & 2 & 3 \\end{bmatrix}\\begin{bmatrix} 4 & 5 & 6 \\\\ 6 & 5 & 4 \\\\ 4 & 6 & 5 \\end{bmatrix}\\right)");

        LaTeX_Test("arcsech(sinh(x+3))", "\\operatorname{arcsech}\\left(\\sinh\\left(x+3\\right)\\right)");
        LaTeX_Test("sin(π/6)", "\\sin\\left(\\dfrac{\\pi}{6}\\right)");
    }

    public void LaTeX_Test(String input, String expected){
        input = input.trim();
        Expression e = toExpression(input);
        expected = expected.trim();
        if (!e.toTeX().equals(expected)){
            System.out.println("\nLaTeX Test Failed:");
            System.out.println("     Input:     "+input);
//            System.out.println("     Input:     "+e.hardcode("     "));
            System.out.println("     Output:    $"+e.toTeX()+"$");
            System.out.println("     Expected:  $"+expected+"$");
            System.exit(1);
        }
        incrementNumTests();
    }


}
