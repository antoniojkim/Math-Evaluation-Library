package Tests;

/**
 * Created by Antonio on 2017-07-11.
 */
public class Complex_Number_Tests extends _Tests_{

    public Complex_Number_Tests(String name){
        super(name);
    }

    @Override
    public void run(){
        evaluationTest("2+3i", "2+3i");
        evaluationTest("(2+3i)+(3+4i)", "5+7i");
        evaluationTest("(2+3i)-(3+4i)", "-1-i");
        evaluationTest("(2+3i)*(3+4i)", "-6+17i");
        evaluationTest("(2+3i)/(3+4i)", "0.72+0.04i");
        evaluationTest("sin(2+3i)", "9.15449914691143-4.168906959966565i");
    }

}
