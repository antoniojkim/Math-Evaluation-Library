package Tests;

import Math_Evaluation_Library.LinearAlgebra.Eigenvalues;
import Math_Evaluation_Library.Miscellaneous._Random_;
import Math_Evaluation_Library.Objects.Fraction;
import Math_Evaluation_Library.Objects.Matrix;

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
}
