package Tests;

import Math_Evaluation_Library.Engine.Engine;
import Math_Evaluation_Library.Expressions.Expression;
import Math_Evaluation_Library.Miscellaneous.Fraction;

/**
 * Created by Antonio on 2017-10-01.
 */
public class Fraction_Tests extends Engine_Tests {

    public Fraction_Tests(String name) {
        super(name);
    }

    @Override
    public void run() {

        fractionTest("0.75", "3/4");
        fractionTest("√(7171/16)", "√7171/4");
        fractionTest("ln32/ln2", "5");
        fractionTest("ln32/ln2", "5");
        fractionTest("arcsin(0.70710678118655)", "π/4");

    }

    public void fractionTest(String input, String expected){
        Expression e = Engine.toExpression(input);
        double n = e.valueOf();
        Expression f = Fraction.toExpression(n);
        double fn = f.valueOf();
        if (!(f.toString().equals(expected))){
            String syntax = Engine.fixSyntax(input).trim();
            System.out.println("\nFraction Test Failed:");
            System.out.println("     Input:     "+input);
            System.out.println("     Value:     "+n);
            System.out.println("     Actual:    "+f.toString()+" = "+f.valueOf());
            System.out.println("     Expected:  "+expected);
            System.exit(1);
        }
        incrementNumTests();
    }
}
