package Math_Evaluation_Library.Objects;

/**
 * Created by Antonio on 2017-10-14.
 */
public class MathObject {

    protected String function = null;

    public int compareTo(String function){
        return this.getName().compareTo(function);
    }
    public int compareTo(MathObject m){
        return compareTo(m.getName());
    }

    public static void swap(MathObject m1, MathObject m2){
        MathObject temp = m1;
        m1 = m2;
        m2 = temp;
    }
    public static void swap(MathObject[] m, int pos1, int pos2){
        MathObject temp = m[pos1];
        m[pos1] = m[pos2];
        m[pos2] = temp;
    }

    public String getName(){
        return function;
    }
    public int length(){
        return function.length();
    }

}
