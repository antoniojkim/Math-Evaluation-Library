package Math_Evaluation_Library.LinearAlgebra;

import Math_Evaluation_Library.Engine.Engine;
import Math_Evaluation_Library.Objects.Fraction;
import Math_Evaluation_Library.Objects.Matrix;
import Math_Evaluation_Library.Objects._Number_;
import Math_Evaluation_Library.Print;
import Math_Evaluation_Library.Search;
import org.jblas.DoubleMatrix;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Antonio on 2017-07-22.
 */
public class _Matrix_ {

    public static List<String> rowReductionSteps;
    public static List<Matrix> rowReductionStates;

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
        //a_11 a_22 a_33 + a_12 a_23 a_31 + a_13 a_21 a_32 - a_13 a_22 a_31 - a_11 a_23 a_32 - a_12 a_21 a_33
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
                        if ((doubleMatrix1.rows > 1 && doubleMatrix1.columns == 1) || (doubleMatrix1.rows == 1 && doubleMatrix1.columns > 1)){
                            return String.valueOf(doubleMatrix1.dot(doubleMatrix2));
                        }
                        else {
                            return toStrMatrix(doubleMatrix1.mul(doubleMatrix2));
                        }
                    }
                }
                else if (equalNumRows && equalNumColumns){
                    if (operator == '·' && doubleMatrix1.columns == 1){
                        return _Number_.format(doubleMatrix1.dot(doubleMatrix2));
                    }
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

    public static DoubleMatrix toDoubleMatrix(String strMatrix){
        try{
            if (_Number_.isNumber(strMatrix)){
                return null;
            }
        } catch (NumberFormatException e){}
        String[] parameters = strMatrix.split(",");
        List<List<String>> matrix = new ArrayList<>();
        List<String> row = new ArrayList<>();
        for (int a = 0 ; a<parameters.length; a++){
            if (parameters[a].contains("}")){
                row.add(Search.replace(parameters[a], "{", "",  "}", "",  " ", ""));
                if (matrix.isEmpty() || row.size() == matrix.get(0).size()){
                    matrix.add(new ArrayList<>(row));
                    row.clear();
                }
                else if (row.size() != matrix.get(0).size()){
                    return null;
                }
            }
            else if (parameters[a].contains("{")){
                row.add(Search.replace(parameters[a], "{", "",  " ", ""));
            }
            else if (!row.isEmpty()){
                row.add(parameters[a].replaceAll(" ", ""));
            }
        }
        if (!matrix.isEmpty()) {
            double[][] values = new double[matrix.size()][matrix.get(0).size()];
            for (int i = 0; i<values.length; i++){
                for (int j = 0; j<values[i].length; j++){
                    values[i][j] = Engine.evaluate(matrix.get(i).get(j));
                }
            }
            return new DoubleMatrix(values);
        }
        return null;
    }
    public static String toStrMatrix(DoubleMatrix matrix){
        double[][] values = matrix.toArray2();
        if (values.length == 1){
            String strMatrix = "{";
            for (int i = 0; i<values[0].length; i++){
                if (i != 0){
                    strMatrix += ", "+ Fraction.calculateFraction(values[0][i], false, true);
                }
                else{
                    strMatrix += Fraction.calculateFraction(values[0][i], false, true);
                }
            }
            return strMatrix+"}";
        }
        else{
            String strMatrix = "{";
            for (int i = 0; i<values.length; i++){
                if (i != 0){
                    strMatrix += ", ";
                }
                strMatrix += "{";
                for (int j = 0; j<values.length; j++){
                    if (j != 0){
                        strMatrix += ", "+ Fraction.calculateFraction(values[i][j], false, true);
                    }
                    else{
                        strMatrix += Fraction.calculateFraction(values[i][j], false, true);
                    }
                }
                strMatrix += "}";
            }
            return strMatrix+"}";
        }
    }

}
