package Math_Evaluation_Library;

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
            System.out.println("]");
        }
    }
    public static void print(int[] array){
        if (array.length > 0) {
            System.out.print("["+array[0]);
            for (int i = 1; i < array.length; i++){
                System.out.print(", "+array[i]);
            }
            System.out.println("]");
        }
    }

}
