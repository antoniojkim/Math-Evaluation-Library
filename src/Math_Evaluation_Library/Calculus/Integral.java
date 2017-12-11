package Math_Evaluation_Library.Calculus;

import Math_Evaluation_Library.Expressions.*;
import Math_Evaluation_Library.Miscellaneous.MathRound;

import static Math_Evaluation_Library.Engine.Engine.toExpression;

/**
 * Created by Antonio on 2017-07-13.
 */
public class Integral {

    public static double nint(String function, double a, double b){
        return value(toExpression(function), a, b);
    }
    public static double nint(Expression function, double a, double b){
        return value(function, a, b);
    }
    public static double value(Expression f, double a, double b){
        Expression integral = f.getIntegral();
        if (integral.isValid()){
            return integral.valueAt(b)-integral.valueAt(a);
        }
        return simpsons(f, a, b);
    }

    private static final double errorMargin = Math.pow(10, -16);
    private static double simpsons(Expression f, double a, double b){
        //double n = 50;//Math.max(Math.min((int)((b-a)*1634), 10000), 1500)
        double range = b-a;
        double n = (int)Math.pow(Math.pow(range, 5)/(2880*errorMargin), 0.25);
        //System.out.println("n = "+n+"       error = "+(range/180*Math.pow(range/n, 4)));
        //n = Math.min(n, 1500);
        double h = range/n;
        //System.out.println("n = "+n+"       h = "+h);
        double total = 0;
        boolean coefficient2 = false;
        total += f.valueAt(a);
        total += f.valueAt(b);
        b -= h;
        for (double i = a+h; i<=b; i += h){
            total += (coefficient2 ? 2 : 4)*f.valueAt(i);
            coefficient2 = !coefficient2;
        }
//        int index = (error+"").indexOf("E");
//        if (index != -1){
//            return MathRound.round(total*(h/3.0), (int)Math.abs(Double.parseDouble((error+"").substring(index+1))));
//        }
        return MathRound.round(total*(h/3.0), 13);
    }


    public static Expression calculate(String function){
        //String simplified = Simplify.simplify(function);
        Expression f = toExpression(function);
        if (f.isValid()) {
            return f.getIntegral().simplify();
        }
        return new InvalidExpression("ʃ("+function+")'");
    }

    public static Expression sqrtIntegral(Expression x){
        if (x instanceof VariableExpression){
            return new OperatorExpression("*",
                    new OperatorExpression("/",
                            new NumberExpression(2),
                            new NumberExpression(3)),
                    new OperatorExpression("^",
                            x, new OperatorExpression("/",
                            new NumberExpression(3),
                            new NumberExpression(2))));
        }
        return new InvalidExpression("");
    }

}