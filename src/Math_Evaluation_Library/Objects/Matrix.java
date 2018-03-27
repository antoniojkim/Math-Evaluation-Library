/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Math_Evaluation_Library.Objects;

/**

@author Antonio
*/

public class Matrix{
    
//    private boolean isRowReduced = false;
//    private int augmented = 0;
//    private Fraction[][] elements;
//    private int numRows = 0,  numColumns = 0;
//
//    public Matrix(int numRows, int numColumns){
//        this(numRows, numColumns, 1);
//    }
//    public Matrix(int numRows, int numColumns, int defaultValue){
//        this.numRows = numRows;
//        this.numColumns = numColumns;
//        elements = new Fraction[numRows][numColumns];
//        for (int a = 0; a<elements.length; a++){
//            for (int b = 0; b<elements[a].length; b++){
//                if (a == b){
//                    elements[a][b] = new Fraction(defaultValue);
//                }
//                else{
//                    elements[a][b] = new Fraction();
//                }
//            }
//        }
//    }
//
//    public Matrix(Fraction[][] elements){
//        this.elements = new Fraction[elements.length][1];
//        for (int a = 0; a<elements.length; a++){
//            System.arraycopy(elements[a], 0, this.elements[a], 0, elements[a].length);
//        }
//        numColumns = elements[0].length;
//        numRows = elements.length;
//    }
//    public Matrix(Fraction[][] elements, boolean augmented, boolean isRowReduced){
//        if (elements.length > 0){
//            this.elements = new Fraction[elements.length][1];
//            for (int a = 0; a<elements.length; a++){
//                this.elements[a] = new Fraction[elements[a].length];
//                System.arraycopy(elements[a], 0, this.elements[a], 0, elements[a].length);
//            }
//            numColumns = elements[0].length;
//            numRows = elements.length;
//            this.isRowReduced = isRowReduced;
//            this.augmented = 0;
//        }
//    }
//    public Matrix (Matrix matrix){
//        this(matrix.getElements(), matrix.isAugmented(), matrix.isRowReduced);
//    }
//
//    public Matrix(String[][] fracElements){
//        if (fracElements.length > 0){
//            elements = new Fraction[fracElements.length][fracElements[0].length];
//            for (int a = 0; a<elements.length; a++){
//                for (int b = 0; b<elements[a].length; b++){
//                    elements[a][b] = new Fraction(fracElements[a][b]);
//                }
//            }
//            numColumns = elements[0].length;
//            numRows = elements.length;
//        }
//    }
//    public Matrix(String[][] fracElements, boolean augmented){
//        this(fracElements);
//        setAugmented(augmented);
//    }
//
//    // Elementary Row Operations
//    public Fraction get(int column, int row){
//        try{
//            return elements[row][column];
//        }catch(IndexOutOfBoundsException e){}
//        return null;
//    }
//    private void set(int column, int row, Fraction value){
//        try{
//            elements[row][column] = value;
//        }catch(IndexOutOfBoundsException e){}
//    }
//    public Fraction[] getRow(int row){
//        try{
//            return elements[row];
//        }catch(IndexOutOfBoundsException e){}
//        return null;
//    }
//    public void setRow(int row_number, Fraction[] row){
//        for (int column = 0; column<elements[row_number].length; column++){
//            set(column, row_number, row[column]);
//        }
//    }
//    public void multiplyRow(int row, double c){
//        for (int a = 0; a<numColumns; a++){
//            elements[row][a].multiply(c);
//        }
//    }
//    public void multiplyRow(int row, Fraction c){
//        for (int a = 0; a<numColumns; a++){
//            elements[row][a].multiply(c);
//        }
//    }
//    public void addMultipleRow(int row1, double c, int row2){
//        for (int a = 0; a<numColumns; a++){
//            Fraction f = elements[row2][a].getCopy();
//            f.multiply(c);
//            elements[row1][a].add(f);
//        }
//    }
//    public void addMultipleRow(int row1, Fraction c, int row2){
//        for (int a = 0; a<numColumns; a++){
//            Fraction f = elements[row2][a].getCopy();
//            f.multiply(c);
//            elements[row1][a].add(f);
//        }
//    }
//    public void switchRows (int row1, int row2){
//        Fraction[] row = new Fraction[numColumns];
//        for (int a = 0; a<row.length; a++){
//            row[a] = elements[row1][a].getCopy();
//        }
//        for (int a = 0; a<numColumns; a++){
//            elements[row1][a] = elements[row2][a];
//        }
//        for (int a = 0; a<numColumns; a++){
//            elements[row2][a] = row[a];
//        }
//    }
//
//    public Fraction[] getColumn(int columnNum){
//        try{
//            Fraction[] column = new Fraction[numRows];
//            for (int row = 0; row<column.length; row++){
//                column[row] = get(columnNum, row);
//            }
//            return column;
//        }catch(IndexOutOfBoundsException e){}
//        return null;
//    }
//    public void addMultipleColumn(int column1, Fraction c, int column2){
//        for (int row = 0; row<numColumns; row++){
//            elements[column1][row].add(elements[column2][row].getCopy().multiply(c));
//        }
//    }
//
//
//    public void transpose(){
//        Fraction[][] transposed = new Fraction[numColumns][numRows];
//        for (int a = 0; a<transposed.length; a++){
//            for (int b = 0; b<transposed[a].length; b++){
//                transposed[a][b] = elements[b][a];
//            }
//        }
//        this.elements = transposed;
//        numColumns = transposed[0].length;
//        numRows = transposed.length;
//        isRowReduced = false;
//    }
//
//    public void add(Matrix m){
//        if (m.numRows == numRows && m.numColumns == numColumns){
//            for (int row = 0; row<elements.length; row++){
//                for (int column = 0; column<elements[row].length; column++){
//                    elements[row][column].add(m.get(column, row));
//                }
//            }
//        }
//    }
//    public void subtract(Matrix m){
//        if (m.numRows == numRows && m.numColumns == numColumns){
//            for (int row = 0; row<elements.length; row++){
//                for (int column = 0; column<elements[row].length; column++){
//                    elements[row][column].subtract(m.get(column, row));
//                }
//            }
//        }
//    }
//    public void multiply(Fraction f){
//        for (int row = 0; row<elements.length; row++){
//            for (int column = 0; column<elements[row].length; column++){
//                elements[row][column].multiply(f);
//            }
//        }
//    }
//    public void multiply(Fraction[] f){
//        if (numColumns == f.length){
//            Fraction[][] product = new Fraction[f.length][1];
//            for (int row = 0; row<f.length; row++){
//                product[row][0] = _Vector_.dotProduct(f, getRow(row));
//            }
//            this.elements = product;
//            numColumns = product[0].length;
//            numRows = product.length;
//            isRowReduced = false;
//        }
//    }
//    public void multiply(Matrix m){
//        if (numColumns == m.numRows){
//            Fraction[][] product = new Fraction[numRows][m.numColumns];
//            for (int row = 0; row<product.length; row++){
//                for (int column = 0; column<product[row].length; column++){
//                    product[row][column] = _Vector_.dotProduct(m.getColumn(column), getRow(row));
//                }
//            }
//            this.elements = product;
//            numColumns = product[0].length;
//            numRows = product.length;
//            isRowReduced = false;
//        }
//    }
//
//    public int getFirstNonZeroColumn(){
//        for (int column = 0; column<numColumns; column++){
//            for (int row = 0; row<numRows; row++){
//                if (!elements[row][column].equals(0)){
//                    return column;
//                }
//            }
//        }
//        return -1;
//    }
//
//    public boolean isRowReduced(){
//        return isRowReduced;
//    }
//    public void setRowReduced(boolean isRowReduced){
//        this.isRowReduced = isRowReduced;
//    }
//    public boolean isAugmented(){
//        return augmented > 0;
//    }
//    public void setAugmented(boolean augmented){
//        if (augmented){
//            this.augmented = 1;
//        }
//        else{
//            this.augmented = 0;
//        }
//    }
//    public void setAugmented(int augmented){
//        this.augmented = augmented;
//    }
//    public int numRows(){
//        return numRows;
//    }
//    public int numColumns(){
//        return numColumns;
//    }
//
//    public Fraction[][] getElements(){
//        return elements;
//    }
//
//    public void print(){
//        System.out.print("[");
//        for (int row = 0; row<numRows; row++){
//            if (row != 0){
//                System.out.print(", ");
//            }
//            System.out.print("[");
//            for (int column = 0; column<numColumns; column++){
//                if (column != 0){
//                    System.out.print(", ");
//                }
//                System.out.print(elements[row][column].getString());
//            }
//            System.out.print("]");
//        }
//        System.out.print("]");
//    }
//    public void println(){
//        print();
//        System.out.println();
//    }
//
//    public Matrix getCopy(){
//        return new Matrix(elements, isAugmented(), isRowReduced);
//    }

    
}
