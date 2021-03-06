package Math_Evaluation_Library;

import Math_Evaluation_Library.Expressions.Expression;
import Math_Evaluation_Library.Objects._Number_;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by Antonio on 2017-07-22.
 */
public class Print {

    public static void print(String[] array){
        if (array.length > 0) {
            System.out.print("["+array[0]);
            for (int i = 1; i < array.length; i++){
                System.out.print(", "+array[i]);
            }
            System.out.print("]");
        }
    }
    public static void println(char[] array){
        if (array.length > 0) {
            System.out.print("["+array[0]);
            for (int i = 1; i < array.length; i++){
                System.out.print(", "+array[i]);
            }
            System.out.println("]");
        }
    }
    public static void println(byte[] array){
        if (array.length > 0) {
            System.out.print("["+array[0]);
            for (int i = 1; i < array.length; i++){
                System.out.print(", "+array[i]);
            }
            System.out.println("]");
        }
    }
    public static void println(boolean[] array){
        if (array.length > 0) {
            System.out.print("["+array[0]);
            for (int i = 1; i < array.length; i++){
                System.out.print(", "+array[i]);
            }
            System.out.println("]");
        }
    }
    public static void print(char[] array){
        if (array.length > 0) {
            System.out.print("["+array[0]);
            for (int i = 1; i < array.length; i++){
                System.out.print(", "+array[i]);
            }
            System.out.print("]");
        }
    }
    public static void println(Object[] array){
        if (array.length > 0) {
            System.out.print("["+array[0]);
            for (int i = 1; i < array.length; i++){
                System.out.print(", "+array[i]);
            }
            System.out.println("]");
        }
    }
    public static void println(String[] array){
        if (array.length > 0) {
            System.out.print("["+array[0]);
            for (int i = 1; i < array.length; i++){
                System.out.print(", "+array[i]);
            }
            System.out.println("]");
        }
    }
    public static void println(int[] array){
        if (array.length > 0) {
            System.out.print("["+array[0]);
            for (int i = 1; i < array.length; i++){
                System.out.print(", "+array[i]);
            }
            System.out.println("]");
        }
    }
    public static void print(double[] array){
        if (array.length > 0) {
            System.out.print("["+array[0]);
            for (int i = 1; i < array.length; i++){
                System.out.print(", "+array[i]);
            }
            System.out.print("]");
        }
    }
    public static void println(double[] array){
        if (array.length > 0) {
            System.out.print("["+array[0]);
            for (int i = 0; i < array.length-1; i++){
                System.out.print(", "+ _Number_.format(array[i]));
            }
            System.out.println("]");
        }
    }
    public static void println(double[][] array){
        if (array.length > 0) {
            System.out.print("[");
            if (array.length > 1){
                print(array[0]);
                System.out.println(",");
                for (int i = 1; i < array.length-1; i++){
                    System.out.print(" ");
                    print(array[i]);
                    System.out.println(",");
                }
                System.out.print(" ");
                print(array[array.length-1]);
            }
            else{
                print(array[0]);
            }
            System.out.println("]");
        }
    }


    public static<E> String toString(List<E> list){
        StringBuilder sb = new StringBuilder();
        if (list.size() > 0) {
            sb.append("[").append(list.get(0).toString());
            for (int i = 1; i < list.size(); i++){
                sb.append(", ").append(list.get(i).toString());
            }
            sb.append("]");
        }
        return sb.toString();
    }
    public static void println(List<String> list){
        if (list.size() > 0) {
            System.out.println(toString(list));
        }
    }

    public static void println(Stack<Expression> stack){
        Stack<Expression> copy = (Stack<Expression>)stack.clone();
        List<Expression> list = new ArrayList<>();
        while (!copy.empty()){
            list.add(copy.pop());
        }

        System.out.print("[");
        if (!list.isEmpty()){
            System.out.print(list.get(list.size()-1).infix());
            for (int i = list.size()-2; i>=0; --i){
                System.out.print(", "+list.get(i).infix());
            }
        }
        System.out.println("]");
    }

}
