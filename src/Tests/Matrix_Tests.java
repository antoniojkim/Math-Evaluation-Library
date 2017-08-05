package Tests;

import Math_Evaluation_Library.Engine.Engine;
import Math_Evaluation_Library.LinearAlgebra.Eigenvalues;
import Math_Evaluation_Library.Miscellaneous._Random_;
import Math_Evaluation_Library.Objects.Fraction;
import Math_Evaluation_Library.Objects.Matrix;
import org.jblas.DoubleMatrix;

import java.util.List;

/**
 * Created by Antonio on 2017-07-22.
 */
public class Matrix_Tests extends _Tests_{

    public Matrix_Tests(String name) {
        super(name);
    }

    @Override
    public void run() {
//        eigenvalueTest1();
//·
        matrixOperationTest("{1, 2}*{3, 4}", "11");
        matrixOperationTest("{1, 2, 3}\\.{4, 5, 6}", "32");
        matrixOperationTest("{1, 2, 3}*{4, 5, 6}", "{-3, 6, -3}");
        matrixOperationTest("{1, 2}*{{3}, {4}}", "{11}");
        matrixOperationTest("[1,2,3; 3,2,1; 1,2,3]*[4,5,6; 6,5,4; 4,6,5]", "{{28, 33, 29}, {28, 31, 31}, {28, 33, 29}}");
        matrixOperationTest("{{1,2,3},{3,2,1},{1,2,3}}*{{4,5,6},{6,5,4},{4,6,5}}", "{{28, 33, 29}, {28, 31, 31}, {28, 33, 29}}");
        matrixOperationTest("{{1,2,3},{3,2,1},{1,2,3}}+{{4,5,6},{6,5,4},{4,6,5}}", "{{5, 7, 9}, {9, 7, 5}, {5, 8, 8}}");
        matrixOperationTest("{{1,2,3},{3,2,1},{1,2,3}}-{{4,5,6},{6,5,4},{4,6,5}}", "{{-3, -3, -3}, {-3, -3, -3}, {-3, -4, -2}}");
        matrixOperationTest("{{1,2,3},{3,2,1},{1,2,3}}/{{4,5,6},{6,5,4},{4,6,5}}", "{{1/4, 2/5, 1/2}, {1/2, 2/5, 1/4}, {1/4, 1/3, 3/5}}");

    }
    private void eigenvalueTest1(){
        int limit = 10;
        String[][] matrix1str = {
                {_Random_.randomint(-1*limit, limit)+"", _Random_.randomint(-1*limit, limit)+"", _Random_.randomint(-1*limit, limit)+""},
                {_Random_.randomint(-1*limit, limit)+"", _Random_.randomint(-1*limit, limit)+"", _Random_.randomint(-1*limit, limit)+""},
                {_Random_.randomint(-1*limit, limit)+"", _Random_.randomint(-1*limit, limit)+"", _Random_.randomint(-1*limit, limit)+""}};//{{"1", "-2"}, {"-2", "0"}};

        Matrix matrix1 = new Matrix(matrix1str);
        List<Fraction> eigenvalues = Eigenvalues.calculate(matrix1);
        System.out.print("\nThe Real Eigenvalues of ");
        matrix1.print();
        System.out.print(" are: ");
        for (int a = 0; a<eigenvalues.size(); a++){
            if (a != 0){
                System.out.print(", ");
            }
            System.out.print(eigenvalues.get(a).getString());
        }
        System.out.println("\n");
    }

    public void matrixOperationTest(String input, String expected){
        input = input.trim();
        String evaluated = Engine.evaluateString(input).trim();
        expected = expected.trim();
        if (!evaluated.equals(expected)){
            String syntax = Engine.fixSyntax(input).trim();
            String postfix = Engine.toPostfix(input);
            System.out.println("\nMatrix Operation Test Failed:");
            System.out.println("     Input:     "+input);
            System.out.println("     Syntax:    "+syntax);
            System.out.println("     Postfix:   "+postfix);
            System.out.println("     Actual:    "+evaluated);
            System.out.println("     Expected:  "+expected);
            System.exit(1);
        }
        incrementNumTests();
    }
}
