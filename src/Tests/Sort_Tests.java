package Tests;

import Math_Evaluation_Library.Engine.MultiParamFunctions;
import Math_Evaluation_Library.Engine.Operators;
import Math_Evaluation_Library.Engine.TextFunctions;
import Math_Evaluation_Library.Engine.UnaryFunctions;
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
        mathObjectSortTest("Multi Param Functions", MultiParamFunctions.multiParamFunctions);
        mathObjectSortTest("Unary Functions", UnaryFunctions.unaryFunctions);
        mathObjectSortTest("Operators", Operators.operators);
        mathObjectSortTest("Text Functions", TextFunctions.textFunctions);
        mathObjectSortTest("Volume Formulas", ShapeFormulas.shapes3D);
        mathObjectSortTest("Area Formulas", ShapeFormulas.shapes2D);
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
//            str += String.valueOf(c)+" ";
//        }
//        return str.trim();
//    }

}
