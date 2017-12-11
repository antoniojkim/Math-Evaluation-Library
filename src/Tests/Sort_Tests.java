package Tests;

import Math_Evaluation_Library.Constants.Constants;
import Math_Evaluation_Library.Engine.TextFunctions;
import Math_Evaluation_Library.Geometry.ShapeFormulas;
import Math_Evaluation_Library.Objects.MathObject;
import Math_Evaluation_Library.Sort;

/**
 * Created by Antonio on 2017-07-11.
 */
public class Sort_Tests extends _Tests_ {

    public Sort_Tests(String name){
        super(name);
    }

    @Override
    public void run(){
        mathObjectSortTest("Text Functions", TextFunctions.textFunctions);
        mathObjectSortTest("Volume Formulas", ShapeFormulas.shapes3D);
        mathObjectSortTest("Area Formulas", ShapeFormulas.shapes2D);

        charArraySortTest("Constant characters", Constants.constantChar);
    }


    public void mathObjectSortTest(String mathObjectName, MathObject[] mathObject){
        boolean sorted = true;
        for (int i = 1; i<mathObject.length; i++){
            if (mathObject[i-1].compareTo(mathObject[i]) > 0){
                sorted = false;
                break;
            }
        }
        if (!sorted){
            Sort.quicksort(mathObject);
            String[] sortedArray = new String[mathObject.length];
            for (int i = 0; i<sortedArray.length; i++){
                sortedArray[i] = mathObject[i].getName();
            }
            System.out.println(mathObjectName+" not correctly sorted\n");
            System.out.println("Correct order:");
            for (String str : sortedArray){
                System.out.println("     "+str);
            }
            System.exit(1);
        }
        incrementNumTests();
    }

    public void charArraySortTest(String arrayName, char[] array){
        boolean sorted = true;
        for (int i = 1; i<array.length; i++){
            if (array[i-1] > array[i]){
                sorted = false;
                break;
            }
        }
        if (!sorted){
            Sort.quicksort(array);
            System.out.println(arrayName+" not correctly sorted\n");
            System.out.println("Correct order:");
            for (char c : array){
                System.out.print("'"+c+"', ");
            }
            System.exit(1);
        }
        incrementNumTests();
    }

//    public void test2(){
//        char[] array = {'π', 'e', 'η', 'ϕ', 'γ', '∞'};
//        Sort.quicksort(array);
//
//        char[] expected = {'π', 'e', 'η', 'ϕ', 'γ', '∞'};
//
//        for (int i = 0; i<array.length; i++){
//            if (array[i] != expected[i]){
//                System.out.println("Sort Test Failed:");
//                System.out.println("     Output:    "+arrayToString(array));
//                System.out.println("     Expected:  "+arrayToString(expected));
//                System.exit(1);
//            }
//        }
//    }
//
//    public String arrayToString(char[] array){
//        String str = "";
//        for (char c : array){
//            str += String.evaluate(c)+" ";
//        }
//        return str.trim();
//    }

}
