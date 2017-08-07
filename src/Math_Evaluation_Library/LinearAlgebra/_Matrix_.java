package Math_Evaluation_Library.LinearAlgebra;

import Math_Evaluation_Library.Engine.Engine;
import Math_Evaluation_Library.Objects.Fraction;
import Math_Evaluation_Library.Objects.Matrix;
import Math_Evaluation_Library.Objects._Number_;
import Math_Evaluation_Library.Print;
import Math_Evaluation_Library.Search;
import org.jblas.Decompose;
import org.jblas.DoubleMatrix;
import org.jblas.Solve;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Double.NaN;

/**
 * Created by Antonio on 2017-07-22.
 */
public class _Matrix_ {


    public static List<String> rowReductionSteps;
    public static List<Matrix> rowReductionStates;
    public static List<DoubleMatrix> doubleRowReductionStates;

    public static Matrix rowReduce(Matrix matrix){
        if (matrix != null && !matrix.isRowReduced()){
            Matrix rowReduced = matrix.getCopy();
            rowReductionSteps = new ArrayList<>();
            rowReductionStates = new ArrayList<>();
            //printWolfram(matrix);
            addReductionStep("Given Matrix:", rowReduced);
            int firstNonZeroColumn = rowReduced.getFirstNonZeroColumn()-1;
            for (int row = 0; row<rowReduced.numRows(); row++){
                firstNonZeroColumn++;
                if (firstNonZeroColumn >= rowReduced.numColumns()){
                    break;
                }
                //  Check for Inconsistency
                //      boolean inconsistent = true;
                //      for (int column = 0; column < number_of_columns-1; column++){
                //       if (get(column, row) != 0){
                //           inconsistent = false;
                //           break;
                //       }
                //      }
                //      if (inconsistent && get(number_of_columns-1, row) != 0){
                //      if (show_work){
                //          print();
                //      }
                //      if (number_of_rows > number_of_columns-1){
                //        return "The System is Inconsistent - More Rows than Columns";
                //      }
                //      return "The System is Inconsistent";
                //      }
                Fraction factor = rowReduced.get(firstNonZeroColumn, row);
                if (factor.equals(0)){
                    for (int restof_rows = row+1; restof_rows<rowReduced.numRows(); restof_rows++){
                        if (!rowReduced.get(firstNonZeroColumn, restof_rows).equals(0)){
                            rowReduced.switchRows(row, restof_rows);
                            addReductionStep("Switch Row "+row+" with Row "+restof_rows, rowReduced);
                            factor = rowReduced.get(firstNonZeroColumn, row);
                            if (!factor.equals(0) || row == rowReduced.numRows()-1){
                                break;
                            }
                            else{
                                restof_rows = row;
                            }
                        }
                    }
                    factor = rowReduced.get(firstNonZeroColumn, row);
                    if (factor.equals(0)){
                        if (firstNonZeroColumn < rowReduced.numColumns()-1){
                            row--;
                        }
                        continue;
                    }
                }
                if (!factor.equals(1)){
                    Fraction reciprocal = factor.getReciprocal();
                    rowReduced.multiplyRow(row, reciprocal);
                    addReductionStep("Row "+(row+1)+" × ("+reciprocal.getString()+")", rowReduced);
                }
                for (int restof_row = 0; restof_row<rowReduced.numRows(); restof_row++){
                    if (row != restof_row){
                        Fraction multiple = rowReduced.get(firstNonZeroColumn, restof_row).getCopy();
                        if (!multiple.equals(0)){
                            multiple.multiply(-1);
                            rowReduced.addMultipleRow(restof_row, multiple, row);
                            addReductionStep("Row "+(restof_row+1)+" + ("+multiple.getString()+")×Row "+(row+1), rowReduced);
                        }
                    }
                }
            }
            addReductionStep("Reduced Row Echelon Form", rowReduced);
            rowReduced.setRowReduced(true);
            return rowReduced;
        }
        return matrix;
    }
    private static void addReductionStep(String step, Matrix matrix){
        rowReductionSteps.add(step);
        rowReductionStates.add(new Matrix(matrix));
//        System.out.println(step);
//        print(matrix);
    }

    public static DoubleMatrix rowReduce(DoubleMatrix m){
        DoubleMatrix matrix = new DoubleMatrix(m.toArray2());
        int firstNonZeroColumn = getFirstNonZeroColumn(matrix)-1;
        for (int row = 0; row<matrix.rows; row++){
            firstNonZeroColumn++;
            if (firstNonZeroColumn >= matrix.columns){
                break;
            }

            double factor = matrix.get(row, firstNonZeroColumn);
            if (factor == 0){
                for (int restof_rows = row+1; restof_rows<matrix.rows; restof_rows++){
                    if (matrix.get(restof_rows, firstNonZeroColumn) != 0){
                        matrix.swapRows(row, restof_rows);
                        factor = matrix.get(row, firstNonZeroColumn);
                        if (factor != 0 || row == matrix.rows-1){
                            break;
                        }
                        else{
                            restof_rows = row;
                        }
                    }
                }
                factor = matrix.get(row, firstNonZeroColumn);
                if (factor == 0){
                    if (firstNonZeroColumn < matrix.columns-1){
                        row--;
                    }
                    continue;
                }
            }
            if (factor != 1){
                double reciprocal = 1.0/factor;
                matrix.mulRow(row, reciprocal);
            }
            for (int restof_row = 0; restof_row<matrix.rows; restof_row++){
                if (row != restof_row){
                    double multiple = matrix.get(restof_row, firstNonZeroColumn);
                    if (multiple != 0){
                        multiple *= -1;
                        matrix.putRow(restof_row, matrix.getRow(restof_row).add(matrix.getRow(row).mul(multiple)));
                    }
                }
            }
        }
        return matrix;
    }
    public static int getFirstNonZeroColumn(DoubleMatrix matrix){
        for (int c = 0; c<matrix.columns; c++){
            double[] column = matrix.getColumn(c).toArray();
            for (double elem : column){
                if (elem != 0){
                    return c;
                }
            }
        }
        return -1;
    }
//    private static void addReductionStep(String step, DoubleMatrix matrix){
//        rowReductionSteps.add(step);
//        doubleRowReductionStates.add(new DoubleMatrix(matrix.toArray2()));
//    }

    public static Fraction getDeterminant(Matrix matrix){
        if (matrix.numRows() == matrix.numColumns()){
            if (matrix.numRows() < 4){
                if (matrix.numRows() == 1){
                    return matrix.get(0, 0);
                }
                if (matrix.numRows() == 2){
                    return get2by2Determinant(matrix);
                }
                return get3by3Determinant(matrix);
            }
            return getNbyNDeterminant(matrix);
        }
        return null;
    }
    private static Fraction get2by2Determinant(Matrix matrix){
        Fraction determinant = matrix.get(0, 0).getCopy().multiply(matrix.get(1, 1));
        Fraction bc = matrix.get(0, 1).getCopy().multiply(matrix.get(1, 0));
        determinant.subtract(bc);
        return determinant;
    }
    private static Fraction get3by3Determinant(Matrix matrix){
        //determinant {{a_11, a_12, a_13}, {a_21, a_22, a_23}, {a_31, a_32, a_33}} =
        // a_12 a_23 a_31 + a_13 a_21 a_32 + a_11 a_22 a_33 - a_13 a_22 a_31 - a_11 a_23 a_32 - a_12 a_21 a_33
        Fraction determinant = matrix.get(0, 0).getCopy().multiply(matrix.get(1, 1)).multiply(matrix.get(2, 2));
        Fraction term2 = matrix.get(0, 1).getCopy().multiply(matrix.get(1, 2)).multiply(matrix.get(2, 0));
        Fraction term3 = matrix.get(0, 2).getCopy().multiply(matrix.get(1, 0)).multiply(matrix.get(2, 1));
        Fraction term4 = matrix.get(0, 2).getCopy().multiply(matrix.get(1, 1)).multiply(matrix.get(2, 0));
        Fraction term5 = matrix.get(0, 0).getCopy().multiply(matrix.get(1, 2)).multiply(matrix.get(2, 1));
        Fraction term6 = matrix.get(0, 1).getCopy().multiply(matrix.get(1, 0)).multiply(matrix.get(2, 2));
        determinant.add(term2);
        determinant.add(term3);
        determinant.subtract(term4);
        determinant.subtract(term5);
        determinant.subtract(term6);
        return determinant;
    }
    private static Fraction getNbyNDeterminant(Matrix m){
        if (m.numRows() == m.numColumns()){
            Matrix matrix = m.getCopy();
            for (int column = 0; column<(matrix.numColumns()-1); column++){
                for (int row = column+1; row<matrix.numRows(); row++){
                    Fraction fraction = matrix.get(column, row).getCopy();
                    fraction.divide(matrix.get(column, column));
                    fraction.multiply(-1);
                    matrix.addMultipleRow(row, fraction, column);
                }
            }
            Fraction determinant = new Fraction(1);
            for (int i = 0; i<matrix.numRows(); i++){
                determinant.multiply(matrix.get(i, i));
            }
            return determinant;
        }
        return null;
    }

    public static double getDeterminant(DoubleMatrix matrix){
        if (matrix != null && matrix.rows == matrix.columns) {
            double[][] values = matrix.toArray2();
            switch (matrix.rows){
                case 1:     return values[0][0];
                case 2:     return get2by2Determinant(values);
                case 3:     return get3by3Determinant(values);
                case 4:     return get4by4Determinant(values);
                default:    return getNbyNDeterminant(values);
            }
        }
        return NaN;
    }
    private static double get2by2Determinant(double[][] matrix){
        double ad = matrix[0][0]*matrix[1][1];
        double bc = matrix[1][0]*matrix[0][1];
        return ad-bc;
    }
    private static double get3by3Determinant(double[][] matrix){
        //determinant {{a_11, a_12, a_13}, {a_21, a_22, a_23}, {a_31, a_32, a_33}} =
        // a_12 a_23 a_31 + a_13 a_21 a_32 + a_11 a_22 a_33 - a_13 a_22 a_31 - a_11 a_23 a_32 - a_12 a_21 a_33
        return matrix[0][1]*matrix[1][2]*matrix[2][0]+
                matrix[0][2]*matrix[1][0]*matrix[2][1]+
                matrix[0][0]*matrix[1][1]*matrix[2][2]-
                matrix[0][2]*matrix[1][1]*matrix[2][0]-
                matrix[0][0]*matrix[1][2]*matrix[2][1]-
                matrix[0][1]*matrix[1][0]*matrix[2][2];
    }
    private static double get4by4Determinant(double[][] matrix){
        //determinant {{a_11, a_12, a_13, a_14}, {a_21, a_22, a_23, a_24}, {a_31, a_32, a_33, a_34}, {a_41, a_42, a_43, a_44}}=
        //a_14 a_23 a_32 a_41 - a_13 a_24 a_32 a_41 - a_14 a_22 a_33 a_41 + a_12 a_24 a_33 a_41 + a_13 a_22 a_34 a_41 -
        // a_12 a_23 a_34 a_41 - a_14 a_23 a_31 a_42 + a_13 a_24 a_31 a_42 + a_14 a_21 a_33 a_42 - a_11 a_24 a_33 a_42 -
        // a_13 a_21 a_34 a_42 + a_11 a_23 a_34 a_42 + a_14 a_22 a_31 a_43 - a_12 a_24 a_31 a_43 - a_14 a_21 a_32 a_43 +
        // a_11 a_24 a_32 a_43 + a_12 a_21 a_34 a_43 - a_11 a_22 a_34 a_43 - a_13 a_22 a_31 a_44 + a_12 a_23 a_31 a_44 +
        // a_13 a_21 a_32 a_44 - a_11 a_23 a_32 a_44 - a_12 a_21 a_33 a_44 + a_11 a_22 a_33 a_44
        return matrix[0][3]*matrix[1][2]*matrix[2][1]*matrix[3][0]-
                matrix[0][2]*matrix[1][3]*matrix[2][1]*matrix[3][0]-
                matrix[0][3]*matrix[1][1]*matrix[2][2]*matrix[3][0]+
                matrix[0][1]*matrix[1][3]*matrix[2][2]*matrix[3][0]+
                matrix[0][2]*matrix[1][1]*matrix[2][3]*matrix[3][0]-
                matrix[0][1]*matrix[1][2]*matrix[2][3]*matrix[3][0]-
                matrix[0][3]*matrix[1][2]*matrix[2][0]*matrix[3][1]+
                matrix[0][2]*matrix[1][3]*matrix[2][0]*matrix[3][1]+
                matrix[0][3]*matrix[1][0]*matrix[2][2]*matrix[3][1]-
                matrix[0][0]*matrix[1][3]*matrix[2][2]*matrix[3][1]-
                matrix[0][2]*matrix[1][0]*matrix[2][3]*matrix[3][1]+
                matrix[0][0]*matrix[1][2]*matrix[2][3]*matrix[3][1]+
                matrix[0][3]*matrix[1][1]*matrix[2][0]*matrix[3][2]-
                matrix[0][1]*matrix[1][3]*matrix[2][0]*matrix[3][2]-
                matrix[0][3]*matrix[1][0]*matrix[2][1]*matrix[3][2]+
                matrix[0][0]*matrix[1][3]*matrix[2][1]*matrix[3][2]+
                matrix[0][1]*matrix[1][0]*matrix[2][3]*matrix[3][2]-
                matrix[0][0]*matrix[1][1]*matrix[2][3]*matrix[3][2]-
                matrix[0][2]*matrix[1][1]*matrix[2][0]*matrix[3][3]+
                matrix[0][1]*matrix[1][2]*matrix[2][0]*matrix[3][3]+
                matrix[0][2]*matrix[1][0]*matrix[2][1]*matrix[3][3]-
                matrix[0][0]*matrix[1][2]*matrix[2][1]*matrix[3][3]-
                matrix[0][1]*matrix[1][0]*matrix[2][2]*matrix[3][3]+
                matrix[0][0]*matrix[1][1]*matrix[2][2]*matrix[3][3];
    }
    private static double getNbyNDeterminant(double[][] m){
        DoubleMatrix matrix = new DoubleMatrix(m);
        for (int column = 0; column<(matrix.columns-1); column++){
            for (int row = column+1; row<matrix.rows; row++){
                double value = -1*matrix.get(row, column)*matrix.get(column, column);
                matrix.putRow(row, matrix.getRow(row).add(matrix.getRow(column).mul(value)));
            }
        }
        double determinant = 1;
        for (int i = 0; i<matrix.rows; i++){
            determinant *= matrix.get(i, i);
        }
        return determinant;
    }

    public static String matrixArithmeticOperations(char operator, String matrix1, String matrix2){
        DoubleMatrix doubleMatrix1 = toDoubleMatrix(matrix1);
        if (doubleMatrix1 != null){
            DoubleMatrix doubleMatrix2 = toDoubleMatrix(matrix2);
            if (doubleMatrix2 != null){
                boolean equalNumRows = doubleMatrix1.rows == doubleMatrix2.rows;
                boolean equalNumColumns = doubleMatrix1.columns == doubleMatrix2.columns;
                if (operator == '*'){
                    if (doubleMatrix1.rows == doubleMatrix2.columns){
                        return toStrMatrix(doubleMatrix1.mmul(doubleMatrix2));
                    }
                    else if (equalNumRows && equalNumColumns){
                        if (doubleMatrix1.rows > 1 && doubleMatrix1.columns == 1){
                            if (doubleMatrix1.rows == 3){
                                return toStrMatrix(_Vector_.crossProduct(doubleMatrix1.toArray(), doubleMatrix2.toArray()));
                            }
                            return _Number_.format(doubleMatrix1.dot(doubleMatrix2));
                        }
                        else if (doubleMatrix1.rows == 1 && doubleMatrix1.columns > 1){
                            if (doubleMatrix1.columns == 3) {
                                return toStrMatrix(_Vector_.crossProduct(doubleMatrix1.toArray(), doubleMatrix2.toArray()));
                            }
                            return _Number_.format(doubleMatrix1.dot(doubleMatrix2));
                        }
                        else {
                            return toStrMatrix(doubleMatrix1.mul(doubleMatrix2));
                        }
                    }
                }
                else if (operator == '·' && (doubleMatrix1.rows == 1 || doubleMatrix1.columns == 1)){
                    return _Number_.format(doubleMatrix1.dot(doubleMatrix2));
                }
                else if (equalNumRows && equalNumColumns){
                    switch (operator){
                        case '+':   return toStrMatrix(doubleMatrix1.add(doubleMatrix2));
                        case '-':   return toStrMatrix(doubleMatrix1.sub(doubleMatrix2));
                        case '/':   return toStrMatrix(doubleMatrix1.div(doubleMatrix2));
                        default:    break;
                    }
                }
            }
            else if (_Number_.isNumber(matrix2)){
                double num = _Number_.getNumber(matrix2);
                if (operator == '^' && num == -1 && doubleMatrix1.rows == doubleMatrix1.columns){
                    return toStrMatrix(Solve.pinv(doubleMatrix1));
                }
                switch (operator){
                    case '+':   return toStrMatrix(doubleMatrix1.add(num));
                    case '-':   return toStrMatrix(doubleMatrix1.sub(num));
                    case '*':   return toStrMatrix(doubleMatrix1.mul(num));
                    case '·':   return toStrMatrix(doubleMatrix1.mul(num));
                    case '/':   return toStrMatrix(doubleMatrix1.div(num));
                    default:    break;
                }
            }
        }
        else if (_Number_.isNumber(matrix1)){
            double num = _Number_.getNumber(matrix1);
            DoubleMatrix doubleMatrix2 = toDoubleMatrix(matrix2);
            if (doubleMatrix2 != null){
                switch (operator){
                    case '+':   return toStrMatrix(doubleMatrix2.add(num));
                    case '-':   return toStrMatrix(doubleMatrix2.neg().add(num));
                    case '*':   return toStrMatrix(doubleMatrix2.mul(num));
                    case '·':   return toStrMatrix(doubleMatrix2.mul(num));
                    case '/':   return toStrMatrix(doubleMatrix2.rdiv(num));
                    default:    break;
                }
            }
        }
        return "NaN";
    }

    // The following formats are valid:
    //      [1 2 3; 3 2 1; 1 2 3]
    //      {1 2 3; 3 2 1; 1 2 3}
    //      [[1,2,3],[3,2,1],[1,2,3]]*[[4,5,6],[6,5,4],[4,6,5]]
    //      {{1,2,3},{3,2,1},{1,2,3}}*{{4,5,6},{6,5,4},{4,6,5}}
    public static DoubleMatrix toDoubleMatrix(String strMatrix){
        try{
            if (_Number_.isNumber(strMatrix)){
                return null;
            }
        } catch (NumberFormatException e){}
        int lengthSub1 = strMatrix.length()-1;
        int doubleSpace = strMatrix.indexOf("  ");
        while (doubleSpace != -1){
            strMatrix = strMatrix.substring(0, doubleSpace)+strMatrix.substring(doubleSpace+1);
            doubleSpace = strMatrix.indexOf("  ");
        }
        char first = strMatrix.charAt(0);
        char last = strMatrix.charAt(strMatrix.length()-1);
        if (first == '{' && last == '}'){
            return toDoubleMatrixSemicolon(Search.replace(strMatrix, "}, {", ";",   "},{", ";",   "}; {", ";",   "};{", ";",   "{", "",  "}", ""));
        }
        if (first == '[' && last == ']'){
            return toDoubleMatrixSemicolon(Search.replace(strMatrix, "], [", ";",   "],[", ";",   "]; [", ";",   "];[", ";",   "[", "",  "]", ""));
        }
        char lastSub1 = strMatrix.charAt(strMatrix.length()-2);
        if (first == '{' && lastSub1 == '}' && last == 'τ'){
            return toDoubleMatrixSemicolon(Search.replace(strMatrix.substring(0, strMatrix.length()-1)
                    , "}, {", ";",   "},{", ";",   "}; {", ";",   "};{", ";",   "{", "",  "}", "")).transpose();
        }
        if (first == '{' && lastSub1 == '}' && last == 'ι'){
            DoubleMatrix matrix = toDoubleMatrixSemicolon(Search.replace(strMatrix.substring(0, strMatrix.length()-1)
                    , "}, {", ";",   "},{", ";",   "}; {", ";",   "};{", ";",   "{", "",  "}", ""));
            if (matrix.rows == matrix.columns){
                return Solve.pinv(matrix);
            }
        }
        if (first == '[' && lastSub1 == ']' && last == 'τ'){
            return toDoubleMatrixSemicolon(Search.replace(strMatrix.substring(0, strMatrix.length()-1),
                    "], [", ";",   "],[", ";",   "]; [", ";",   "];[", ";",   "[", "",   "]", "")).transpose();
        }
        if (first == '[' && lastSub1 == ']' && last == 'ι'){
            DoubleMatrix matrix = toDoubleMatrixSemicolon(Search.replace(strMatrix.substring(0, strMatrix.length()-1),
                    "], [", ";",   "],[", ";",   "]; [", ";",   "];[", ";",   "[", "",  "]", ""));
            if (matrix.rows == matrix.columns){
                return Solve.pinv(matrix);
            }
        }
        return null;
    }
    public static DoubleMatrix toDoubleMatrixSemicolon(String strMatrix){
        String[] rows = strMatrix.split(";");
        int rowLength = -1;
        double[][] values = new double[rows.length][0];
        for(int i = 0; i<values.length; i++){
            String[] strRow = Search.replace(rows[i], " ", "").split(",");
            double[] row = new double[strRow.length];
            if (rowLength == -1){
                rowLength = row.length;
                for (int j = 0; j<rowLength; j++){
                    row[j] = _Number_.getNumber(strRow[j]);
                }
                values[i] = row;
            }
            else if (row.length == rowLength){
                for (int j = 0; j<rowLength; j++){
                    row[j] = _Number_.getNumber(strRow[j]);
                }
                values[i] = row;
            }
            else{
                return null;
            }
        }
        return new DoubleMatrix(values);
    }

    public static String toStrMatrix(DoubleMatrix matrix){
        return toStrMatrix(matrix.toArray2());
    }
    public static String toStrMatrix(double[] values){
        String strMatrix = "{";
        for (int i = 0; i<values.length; i++){
            if (i != 0){
                strMatrix += ", "+ Fraction.calculateFraction(values[i], false, true);
            }
            else{
                strMatrix += Fraction.calculateFraction(values[i], false, true);
            }
        }
        return strMatrix+"}";
    }
    public static String toStrMatrix(double[][] values){
        if (values.length == 1){
            return toStrMatrix(values[0]);
        }
        else{
            String strMatrix = "{";
            for (int i = 0; i<values.length; i++){
                if (i != 0){
                    strMatrix += ", ";
                }
                strMatrix += "{";
                for (int j = 0; j<values[i].length; j++){
                    if (j != 0){
                        strMatrix += ", "+ Fraction.calculateFraction(values[i][j], false, false);
                    }
                    else{
                        strMatrix += Fraction.calculateFraction(values[i][j], false, false);
                    }
                }
                strMatrix += "}";
            }
            return strMatrix+"}";
        }
    }

}
