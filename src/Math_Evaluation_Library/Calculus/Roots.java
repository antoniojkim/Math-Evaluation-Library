package Math_Evaluation_Library.Calculus;

import Math_Evaluation_Library.Miscellaneous.MathRound;
import Math_Evaluation_Library.Objects.Fraction;
import Math_Evaluation_Library.Objects.Function;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Double.NaN;

/**
 * Created by Antonio on 2017-07-12.
 */
public class Roots {

    public static double NewtonsMethod (String function, double x){
        double xn = x;
        int decimalplaces = 15;
        Function f = new Function(function);
        Function fprime = new Function(Derivative.calculate(function));
        System.out.println(f.function());
        System.out.println(fprime.function());
//        newton(cosx-x*sinx, 1) 0.860333589019
//        newton(sin(cosx)*tanx, 3) 3.141592653589793
        if (fprime.isFunction()){
            for (int a = 0; a<300; a++){
                xn = x-(f.of(x)/fprime.of(x));
                if (f.of(xn) == 0){
                    return xn;
                }
                else if (MathRound.roundf(xn, decimalplaces).equals(MathRound.roundf(x, decimalplaces)) || MathRound.roundf(f.of(xn), decimalplaces).equals("0")){
                    return xn;
                }
                x = xn;
            }
        }
        else{
            for (int a = 0; a<150; a++){
                xn = x-(f.of(x)/Derivative.nDeriv(function, x));
                if (f.of(xn) == 0){
                    return xn;
                }
                else if (MathRound.roundf(xn, decimalplaces).equals(MathRound.roundf(x, decimalplaces)) || MathRound.roundf(f.of(xn), decimalplaces).equals("0")){
                    return xn;
                }
                x = xn;
            }
        }
        return NaN;
    }

    public static List<Fraction> quadraticFormula(Fraction a, Fraction b, Fraction c){
        List<Fraction> roots = new ArrayList<>();
        Fraction discriminant = b.getCopy().expt(2).subtract(a.getCopy().multiply(c).multiply(4));
        if (discriminant.getValue() == 0){
            roots.add(b.getCopy().multiply(-1).divide(a.getCopy().multiply(2)));
        }
        else if (discriminant.getValue() > 0){
            discriminant.sqrt();
            roots.add(b.getCopy().multiply(-1).add(discriminant).divide(a.getCopy().multiply(2)));
            roots.add(b.getCopy().multiply(-1).subtract(discriminant).divide(a.getCopy().multiply(2)));
        }
        return roots;
    }
    public static List<Double> quadraticFormula(double a, double b, double c){
        List<Double> roots = new ArrayList<>();
        if (a != 0){
            double discriminant = b*b-4*a*c;
            if (discriminant == 0){
                roots.add(-1*b/(2*a));
            }
            else if (discriminant > 0){
                discriminant = Math.sqrt(discriminant);
                roots.add((-1*b+discriminant)/(2*a));
                roots.add((-1*b-discriminant)/(2*a));
            }
        }
        return roots;
    }
    public static List<Double> cubicFormula(double a, double b, double c, double d){
        List<Double> roots = new ArrayList<>();
        if (d == 0){
            //System.out.println("Used Rational Root Test\n");
            roots.add(0.0);
            roots.addAll(quadraticFormula(a, b, c));
            return roots;
        }
        if (a%1 == 0 && b%1 == 0 && c%1 == 0 && d%1 == 0){
            //System.out.println("Used Rational Root Test\n");
            for (int m = 1; m <= Math.abs(d); m++){
                for (int n = 1; n <= Math.abs(a); n++){
                    double e = (double)m/n;
                    if (Math.abs(((a*e+b)*e+c)*e+d) < 1.0E-15){
                        roots.add(e);
                        roots.addAll(quadraticFormula(a, b+a*e, c+e*(b+a*e)));
                        return roots;
                    }
                    if (Math.abs(((-a*e+b)*e-c)*e+d) < 1.0E-15){
                        e *= -1;
                        roots.add(e);
                        roots.addAll(quadraticFormula(a, b+a*e, c+e*(b+a*e)));
                        return roots;
                    }
                }
            }
        }
        double Q = (3*a*c-b*b)/(9*a*a);
        double R = ((9*b*c-27*a*d)*a-2*b*b*b)/(54*a*a*a);
        double Q3_R2 = Q*Q*Q+R*R;
        if (Q3_R2 >= 0){
            //System.out.println("Used Cardano's Formula\n");
            double e = Math.cbrt(R+Math.sqrt(Q3_R2))+Math.cbrt(R-Math.sqrt(Q3_R2))-b/(3*a);
            roots.add(e);
            if (Math.abs(((a*e+b)*e+c)*e+d) < 1.0E-15){
                roots.addAll(quadraticFormula(a, b+a*e, c+e*(b+a*e)));
            }
            return roots;
        }
        double xn = -1*b/(3*a);
        List<Double> local = quadraticFormula(3*a, 2*b, c);
        if (!local.isEmpty()){
            double x1 = local.get(0);
            x1 = ((a*x1+b)*x1+c)*x1+d;
            if (local.size() == 2){
                double x2 = local.get(1);
                x2 = ((a*x2+b)*x2+c)*x2+d;
                if (x1 > 0 && x2 > 0){
                    xn = local.get(0)-1;
                }
                else if (x1 < 0 && x2 < 0){
                    xn = local.get(1)+1;
                }
                else{
                    xn = MathRound.round(local.get(0)+(local.get(1)-local.get(0))/2, 5);
                }
            }
            else{
                if (x1 > 0){
                    xn = local.get(0)-1;
                }
                else{
                    xn = local.get(1)+1;
                }
            }
        }
        double x = xn;
        int count = 0;
        //System.out.println("Starting point:  "+xn);
        for (; Math.abs(((a*xn+b)*xn+c)*xn+d) > 1.0E-13 && count<150; count++){
            x = xn;
            xn = x-(((a*x+b)*x+c)*x+d)/((3*a*x+2*b)*x+c);
        }
        //System.out.println(xn+"    Took "+count+" iterations to find");
        if (count < 150){
            //System.out.println("Used Newton's Method\n");
            roots.add((double)xn);
            roots.addAll(quadraticFormula(a, b+a*xn, c+xn*(b+a*xn)));
            for (int i = 0; i<roots.size(); i++){
                roots.set(i, MathRound.round(roots.get(i), 13));
            }
            return roots;
        }
        return roots;
    }

}
