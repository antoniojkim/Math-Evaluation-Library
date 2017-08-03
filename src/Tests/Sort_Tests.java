package Tests;

import Math_Evaluation_Library.Sort;

import java.util.Arrays;

/**
 * Created by Antonio on 2017-07-11.
 */
public class Sort_Tests extends _Tests_ {

    public Sort_Tests(String name){
        super(name);
    }

    @Override
    public void run(){
        test1();
//        test2();
    }

    public void test1(){
        String[] array = {"/", "\\", ":", "*", "?", "\"", "<", ">", "|", "#"};
        String[] sorted = {"\"", "#", "*", "/", ":", "<", ">", "?", "\\", "|"};

        Sort.quicksort(array);

        assertionTest(Arrays.equals(array, sorted));
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
