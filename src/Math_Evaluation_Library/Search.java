/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Math_Evaluation_Library;

import Math_Evaluation_Library.Engine.Engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**

 @author Antonio
 */
public class Search {
    
    /*
    public static void main (String[] args){
        String[] array = Evaluator.common;
        Sort.quicksort(array);
        for (int a = 0; a<array.length; a++){
            System.out.println(a+"  "+array[a]+"  ");
        }
        System.out.println("\n");
        long start = System.nanoTime();
        int index = binarySearch(array, "sin");
        long end = System.nanoTime();
        System.out.println("\n\nTook "+((end-start)/(100000.0))+" Milliseconds to find index: "+index+"       Performed "+count+" operations on array size "+array.length);
    }
    /*
    */

    public static boolean contains(int[] array, int item){
        return binarySearch(array, item) != -1;
    }
    public static int binarySearch(int[] array, int item){
        int i = Arrays.binarySearch(array, item);
        try {
            return (array[i] == item ? i : -1);
        }catch (ArrayIndexOutOfBoundsException e){} return -1;
    }

    public static boolean contains(char[] array, char item){
        return binarySearch(array, item) != -1;
    }
    public static int binarySearch(char[] array, char item){
        int low = 0;
        int high = array.length - 1;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            int cmp = array[mid]-item;
            if (cmp < 0) {
                low = mid + 1;
            } else if (cmp > 0) {
                high = mid - 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    public static boolean contains(String[] array, String item){
        return binarySearch(array, item) >= 0;
    }
    public static int binarySearch(String[] array, String item){
        int low = 0;
        int high = array.length - 1;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            int cmp = array[mid].compareTo(item);
            if (cmp < 0) {
                low = mid + 1;
            } else if (cmp > 0) {
                high = mid - 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    public static List<Integer> getIndices(String function, String split){
        return getIndices(function, split.toCharArray());
    }
    public static List<Integer> getIndices(String function, char... split){
        Sort.quicksort(split);
        char[] c = function.toCharArray();
        List<Integer> indices = new ArrayList<>();
        int bracket = 0, absBracket = 0;
//        int operator = 0;

        for (int i = 0; i<c.length; i++){
            if(contains(split, c[i]) && ((bracket == 0 && absBracket%2 == 0) || c[i] == '|') && !(c[i] == '-' && i == 0)){ //&& operator == 0
                indices.add(i);
            }
            if (c[i] == '|'){
                absBracket++;
            }
            else if (c[i] == '('){
                bracket++;
            }
            else if (c[i] == ')'){
                bracket--;
                if(contains(split, c[i]) && ((bracket == 0 && absBracket%2 == 0) || c[i] == '|') && !(c[i] == '-' && i == 0)){ // && operator == 0
                    indices.add(i);
                }
            }
        }
        return indices;
    }
    public static boolean functionContains(String function, String split){
        return functionContains(function, split.toCharArray());
    }
    public static boolean functionContains(String function, char... split){
        List<Integer> indices = getIndices(function, split);
        if (indices.size() == 0){
            char[] c = function.toCharArray();
            int bracket = 0, absBracket = (c[0] == '|' ? 1 : 0);
            for (int i = 1; i<c.length; i++){
                if((Engine.checkImplicitContains(c[i])) && (Engine.implicitContains(c[i-1]) ||
                        (c[i-1] == '|' && absBracket%2 == 0))){
                    indices.add(i);
                }
                else if (c[i-1] == '|' && c[i] == '|'){
                    indices.add(i);
                }
                else if (c[i] == '('){
                    bracket++;
                }
                else if (c[i] == ')'){
                    bracket--;
                }
                else if (c[i] == '|'){
                    absBracket++;
                }
            }
            return indices.size() != 0;
        }
        return true;
    }

    public static String replace(String text, String searchString, String replacement) {
        return replace(text, searchString, replacement, -1);
    }

    public static String replace(String text, String searchString, String replacement, int max) {
        if (text.length() == 0 || searchString.length() == 0 || replacement == null || max == 0) {
            return text;
        }
        int start = 0;
        int end = text.indexOf(searchString, start);
        if (end == -1) {
            return text;
        }
        int replLength = searchString.length();
        int increase = replacement.length() - replLength;
        increase = (increase < 0 ? 0 : increase);
        increase *= (max < 0 ? 16 : (max > 64 ? 64 : max));
        StringBuffer buf = new StringBuffer(text.length() + increase);
        while (end != -1) {
            buf.append(text.substring(start, end)).append(replacement);
            start = end + replLength;
            if (--max == 0) {
                break;
            }
            end = text.indexOf(searchString, start);
        }
        buf.append(text.substring(start));
        return buf.toString();
    }

//    private static int binarySearch(int[] array, int low, int high, int item, boolean ascending){
//        //count++;
//        if (high < low){
//            return -1;
//        }
//        int mid = low+(high-low)/2;
//        if (array[mid] == item){
//            return mid;
//        }
//        else if (array[mid] > item){
//            if (ascending){
//                return binarySearch(array, mid+1, high, item, true);
//            }
//            else{
//                return binarySearch(array, low, mid-1, item, false);
//            }
//        }
//        else{
//            if (ascending){
//                return binarySearch(array, low, mid-1, item, true);
//            }
//            else{
//                return binarySearch(array, mid+1, high, item, false);
//            }
//        }
//    }
//    private static int binarySearch(String[] array, int low, int high, String item, boolean ascending){
////        count++;
//        if (high < low){
//            return -1;
//        }
//        int mid = low+(high-low)/2;
//        if (array[mid].equals(item)){
//            return mid;
//        }
//        else if (array[mid].compareTo(item) < 0){
//            if (ascending){
//                return binarySearch(array, mid+1, high, item, true);
//            }
//            else{
//                return binarySearch(array, low, mid-1, item, false);
//            }
//        }
//        else{
//            if (ascending){
//                return binarySearch(array, low, mid-1, item, true);
//            }
//            else{
//                return binarySearch(array, mid+1, high, item, false);
//            }
//        }
//    }

}
