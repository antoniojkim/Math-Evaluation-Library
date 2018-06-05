package Math_Evaluation_Library.Objects;

public class Triple<A, B, C> {

    A a;
    B b;
    C c;

    public Triple(A a, B b, C c){
        this.a = a;
        this.b = b;
        this.c = c;
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
    public C getC(){
        return c;
    }
    public void setC(C c){
        this.c = c;
    }

    public String toString(){
        return a.toString()+", "+b.toString()+", "+c.toString();
    }

}
