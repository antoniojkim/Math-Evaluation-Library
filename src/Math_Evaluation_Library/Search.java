/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Math_Evaluation_Library;

import Math_Evaluation_Library.Engine.Engine;
import Math_Evaluation_Library.Objects.MathObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**

 @author Antonio
 */
public class Search {


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
    public static int binarySearch(String[][] array, String item){
        return binarySearch(array, 0, item);
    }
    public static int binarySearch(String[][] array, int lead, String item){
        int low = 0;
        int high = array.length - 1;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            int cmp = array[mid][lead].compareTo(item);
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

    public static boolean contains(MathObject[] array, String item){
        return binarySearch(array, item) >= 0;
    }
    public static int binarySearch(MathObject[] array, String item){
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

    public static int linearSearch(int[] array, int num){
        for (int i = 0; i<array.length; i++){
            if (array[i] == num)    return i;
        }
        return -1;
    }

    public static int binarySearchIndex(List<String> list, String item){
        int low = 0;
        int high = list.size() - 1;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            int cmp = list.get(mid).compareTo(item);
            if (cmp < 0) {
                low = mid + 1;
            } else if (cmp > 0) {
                high = mid - 1;
            } else {
                return mid;
            }
        }
        return low;
    }

    public static int indexOf(String str, char search){
        return indexOf(str, search, 0);
    }
    public static int indexOf(String str, char search, int start){
        char[] values = str.toCharArray();
        int bracket = 0, cBracket = 0;
        for (int i = start; i<values.length; i++){
            char c = values[i];
            boolean brackets = bracket == 0 && cBracket == 0;
            if (brackets && c == search){
                return i;
            }
            if (c == '('){
                bracket++;
            }
            else if (c == ')'){
                bracket--;
                if (bracket == 0 && cBracket == 0){
                    return i;
                }
            }
            else if (c == '{'){
                cBracket++;
            }
            else if (c == '}'){
                cBracket--;
                if (bracket == 0 && cBracket == 0){
                    return i;
                }
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
        int bracket = 0, absBracket = 0, cBracket = 0;
//        int operator = 0;

        for (int i = 0; i<c.length; i++){
            boolean equalsAbsBracket = c[i] == '|';
            if(contains(split, c[i]) && (equalsAbsBracket || (bracket == 0 && cBracket == 0 && absBracket%2 == 0)) && !(c[i] == '-' && i == 0)){ //&& operator == 0
                indices.add(i);
            }
            if (equalsAbsBracket){
                absBracket++;
            }
            else if (c[i] == '{'){
                cBracket++;
            }
            else if (c[i] == '}'){
                cBracket--;
                if(contains(split, c[i]) && (equalsAbsBracket || (bracket == 0 && cBracket == 0 && absBracket%2 == 0)) && !(c[i] == '-' && i == 0)){ // && operator == 0
                    indices.add(i);
                }
            }
            else if (c[i] == '('){
                bracket++;
            }
            else if (c[i] == ')'){
                bracket--;
                if(contains(split, c[i]) && (equalsAbsBracket || (bracket == 0 && cBracket == 0 && absBracket%2 == 0)) && !(c[i] == '-' && i == 0)){ // && operator == 0
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
            int bracket = (c[0] == '(' ? 1 : 0), absBracket = (c[0] == '|' ? 1 : 0);
            for (int i = 1; i<c.length; i++){
                if(bracket == 0 && Engine.checkImplicitContains(c[i]) &&
                        (Engine.implicitContains(c[i-1]) || (c[i-1] == '|' && absBracket%2 == 0))){
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

    public static String replace(String text, String... searchReplace) {
        for (int i = 1; i<searchReplace.length; i+=2){
            text = replace(text, searchReplace[i-1], searchReplace[i]);
        }
        return text;
    }
    public static String replace(String text, String[]... searchReplace) {
        for (String[] search : searchReplace){
            text = replace(text, search[0], search[1]);
        }
        return text;
    }
    public static String replace(String text, String[] searchString, String[] replacement) {
        int length = Math.min(searchString.length, replacement.length);
        for (int i = 0; i<length; i++){
            text = replace(text, searchString[i], replacement[i]);
        }
        return text;
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

    public static void replace(List<String> list, String searchString, String replacement) {
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i<list.size(); i++){
            if (list.get(i).equals(searchString)){
                indices.add(i);
            }
        }
        replace(list, indices, replacement);
    }
    public static void replace(List<String> list, List<Integer> indices, String replacement) {
        for(int index : indices){
            list.set(index, replacement);
        }
    }

    public static String[] split(String text, String delim){
        return split(text, delim, true);
    }
    public static String[] split(String text, String delim, boolean withinBrackets){
        if (withinBrackets || delim.length() > 1) {
            while (text.endsWith(delim)) {
                text = text.substring(0, text.length() - delim.length());
            }
            List<String> split = new ArrayList<>();
            int previous = 0;
            int index = text.indexOf(delim);
            while (index != -1) {
                split.add(text.substring(previous, index));
                previous = index + delim.length();
                index = text.indexOf(delim, previous);
            }
            split.add(text.substring(previous, text.length()));
            String[] results = new String[split.size()];
            for (int i = 0; i < results.length; i++) {
                results[i] = split.get(i);
            }
            return results;
        }
        List<Integer> commas = getIndices(text, delim);
        if (commas.isEmpty()){
            return new String[]{text};
        }
        String[] results = new String[commas.size()+1];
        results[0] = text.substring(0, commas.get(0));
        for (int i = 1; i<commas.size(); i++){
            results[i] = text.substring(commas.get(i-1)+1, commas.get(i));
        }
        results[results.length-1] = text.substring(commas.get(commas.size()-1)+1);
        return results;
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
