package Math_Evaluation_Library.Objects;

public class Pair<A, B> {

    A a;
    B b;

    public Pair(A a, B b){
        this.a = a;
        this.b = b;
    }

    public A getA(){
        return a;
    }
    public void setA(A a){
        this.a = a;
    }
    public B getB(){
        return b;
    }
    public void setB(B b){
        this.b = b;
    }

    public String toString(){
        return a.toString()+", "+b.toString();
    }

}
