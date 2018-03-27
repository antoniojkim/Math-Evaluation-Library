package Tests;

import Math_Evaluation_Library.Engine.Engine;
import Math_Evaluation_Library.Expressions.Expression;
import Math_Evaluation_Library.LinearAlgebra.Eigenvalues;
import Math_Evaluation_Library.LinearAlgebra._Matrix_;
import Math_Evaluation_Library.Miscellaneous._Random_;
import org.jblas.DoubleMatrix;

import java.util.List;

import static Math_Evaluation_Library.Engine.Engine.toExpression;

/**
 * Created by Antonio on 2017-07-22.
 */
public class Matrix_Tests extends _Tests_{

    public static void main(String[] args){
        DoubleMatrix m = new DoubleMatrix(new double[][]{{1.56, 2, 3}, {4, 5, 6}, {7, 8, 9}});
        System.out.println(m.toString("%.16s", "{{", "}}", ", ", "}, {"));
    }

    public Matrix_Tests(String name) {
        super(name);
    }

    @Override
    public void run() {

        matrixOperationTest("{1, 2}*{3, 4}", "11");
        matrixOperationTest("{1, 2, 3}\\.{4, 5, 6}", "32");
        matrixOperationTest("{1, 2, 3}*{4, 5, 6}", "{-3, 6, -3}");
        matrixOperationTest("{1, 2}*{{3}, {4}}", "{11}");
        matrixOperationTest("[1,2,3; 3,2,1; 1,2,3]*[4,5,6; 6,5,4; 4,6,5]", "{{28, 33, 29}, {28, 31, 31}, {28, 33, 29}}");
        matrixOperationTest("{{1,2,3},{3,2,1},{1,2,3}}*{{4,5,6},{6,5,4},{4,6,5}}", "{{28, 33, 29}, {28, 31, 31}, {28, 33, 29}}");
        matrixOperationTest("{{1,2,3},{3,2,1},{1,2,3}}+{{4,5,6},{6,5,4},{4,6,5}}", "{{5, 7, 9}, {9, 7, 5}, {5, 8, 8}}");
        matrixOperationTest("{{1,2,3},{3,2,1},{1,2,3}}-{{4,5,6},{6,5,4},{4,6,5}}", "{{-3, -3, -3}, {-3, -3, -3}, {-3, -4, -2}}");
        matrixOperationTest("{{1,2,3},{3,2,1},{1,2,3}}/{{4,5,6},{6,5,4},{4,6,5}}", "{{1/4, 2/5, 1/2}, {1/2, 2/5, 1/4}, {1/4, 1/3, 3/5}}");

        matrixOperationTest("4-{3, 4}×3", "{-5, -8}");
        matrixOperationTest("{{1, 2, 3}, {3, 4, 5}}T", "{{1, 3}, {2, 4}, {3, 5}}");
        matrixOperationTest("{{2,3},{4,5}}I", "{{-5/2, 3/2}, {2, -1}}");
        matrixOperationTest("{{2,3},{4,5}}^-1", "{{-5/2, 3/2}, {2, -1}}");

        matrixOperationTest("det([[-1, -2, 0], [-2, 4, 1], [1, 0, 1]])", "-10");
        matrixOperationTest("det({{2, -4, 0}, {3, 2, -3}, {1, -5, 0}})", "-18");
        matrixOperationTest("det([[1, -1, 2, 1], [2, 3, 3, 1], [1, -1, 3, 1], [2, 1, 2, 1]])", "-2");

        matrixOperationTest("RREF({{3, 1, 2}, {2, 1, 3}})", "{{1, 0, -1}, {0, 1, 5}}");
        matrixOperationTest("RREF([[1, 3, 5, 9], [1, 3, 1, 7], [4, 3, 9, 7], [5, 2, 0, 9]])", "{{1, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, 1, 0}, {0, 0, 0, 1}}");
        matrixOperationTest("RCEF({{1, 1, 4, 5}, {3, 3, 3, 2}, {5, 1, 9, 0}, {9, 7, 7, 9}})", "{{1, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, 1, 0}, {0, 0, 0, 1}}");

    }
    private void eigenvalueTest1(){
        int limit = 10;
        String[][] matrix1str = {
                {_Random_.randomint(-1*limit, limit)+"", _Random_.randomint(-1*limit, limit)+"", _Random_.randomint(-1*limit, limit)+""},
                {_Random_.randomint(-1*limit, limit)+"", _Random_.randomint(-1*limit, limit)+"", _Random_.randomint(-1*limit, limit)+""},
                {_Random_.randomint(-1*limit, limit)+"", _Random_.randomint(-1*limit, limit)+"", _Random_.randomint(-1*limit, limit)+""}};//{{"1", "-2"}, {"-2", "0"}};

        DoubleMatrix matrix1 = new DoubleMatrix(new double[][]{
                {_Random_.randomint(-limit, limit), _Random_.randomint(-limit, limit), _Random_.randomint(-limit, limit)},
                {_Random_.randomint(-limit, limit), _Random_.randomint(-limit, limit), _Random_.randomint(-limit, limit)},
                {_Random_.randomint(-limit, limit), _Random_.randomint(-limit, limit), _Random_.randomint(-limit, limit)}});//{{"1", "-2"}, {"-2", "0"}};

        List<Double> eigenvalues = Eigenvalues.calculate(matrix1);
        System.out.print("\nThe Real Eigenvalues of ");
        System.out.println(_Matrix_.toStrMatrix(matrix1));
        System.out.print(" are: ");
        for (int a = 0; a<eigenvalues.size(); a++){
            if (a != 0){
                System.out.print(", ");
            }
            System.out.print(eigenvalues.get(a));
        }
        System.out.println("\n");
    }

    public void matrixOperationTest(String input, String expected){
        input = input.trim();
        Expression e = toExpression(input);
        if (!e.evaluate().toString().equals(expected)){
            String syntax = Engine.fixSyntax(input).trim();
            System.out.println("\nMatrix Operation Test Failed:");
            System.out.println("     Input:     "+input);
            System.out.println("     Syntax:    "+syntax);
            System.out.println("     Postfix:   "+e.postfix());
            System.out.println("     Actual:    "+e.evaluate().toString());
            System.out.println("     Expected:  "+expected);
            System.exit(1);
        }
        incrementNumTests();
    }
}
