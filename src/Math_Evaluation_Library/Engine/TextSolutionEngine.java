package Math_Evaluation_Library.Engine;

import Math_Evaluation_Library.Calculus.Derivative;
import Math_Evaluation_Library.Calculus.Integral;
import Math_Evaluation_Library.Calculus.Roots;
import Math_Evaluation_Library.Geometry.Formulas;
import Math_Evaluation_Library.GraphingTechnology.DirectionField;
import Math_Evaluation_Library.LinearAlgebra._Matrix_;
import Math_Evaluation_Library.LinearAlgebra._Vector_;
import Math_Evaluation_Library.Miscellaneous.MathRound;
import Math_Evaluation_Library.Miscellaneous.Mod;
import Math_Evaluation_Library.Miscellaneous._Random_;
import Math_Evaluation_Library.Objects.Fraction;
import Math_Evaluation_Library.Objects.Function;
import Math_Evaluation_Library.Objects.Matrix;
import Math_Evaluation_Library.Objects._Number_;
import Math_Evaluation_Library.Sort;
import Math_Evaluation_Library.Search;

import java.awt.*;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Double.NaN;

/**
 * Created by Antonio on 2017-07-23.
 */
public class TextSolutionEngine extends Engine{



    public static String[] solvableEquations = {
            "graph", "EEA", "LDE", "deriv", "diff", "prime", "primef", "primf", "randomf", "tangent", "quad", "cubic",
            "long", "scint", "sci", "mod", "line", "complex", "cmplx", "int", "antideriv", "antidiff", "simplify",
            "volume", "area", "congruence", "dot", "cross", "proj", "projection", "perp", "perpendicular",
            "RREF", "RREf", "RCEF", "RCEf", "Inv", "inv", "det", "slopeF", "directF", "sf", "df", "sort",
            "dict", "thes", "plot", "plt", "parse"
    };

    public static String solve(String string){
        for (int a = 0; a<variables.size(); a++){
            string = string.replaceAll(variables.get(a), values.get(a)+"");
        }
        int lb = string.indexOf("(");
        int rb = string.lastIndexOf(")");
        if (lb != -1 && rb != -1){
            String[] parameters = string.substring(lb+1, rb).split(",");
            for (int a = 0; a<parameters.length; a++){
                parameters[a] = parameters[a].trim();
            }
            if (parameters.length > 0){
                try{
                    String type = parameters[0];
                    if (indexOf(type) != -1){
                        // Extended Euclidean Algorithm and Linear Diophantine Equations
                        if ((type.equalsIgnoreCase("EEA") || type.equalsIgnoreCase("LDE"))
                                && (parameters.length == 3 || parameters.length == 4)){
                            return EEA_LDE (type, parameters);
                        }
                        // Differentiation
                        else if ((type.equalsIgnoreCase("deriv") || type.equalsIgnoreCase("diff"))
                                && (parameters.length == 2 || parameters.length == 3)){
                            return deriv(parameters);
                        }
                        // AntiDifferentiation
                        else if ((type.equalsIgnoreCase("int") || type.equalsIgnoreCase("antideriv") || type.equalsIgnoreCase("antidiff"))
                                && (parameters.length == 2 || parameters.length == 3)){
                            return antidifferentiation(parameters);
                        }
                        else if (type.equalsIgnoreCase("prime") && parameters.length == 2){
                            return isPrimeNumber(Integer.parseInt(parameters[1]));
                        }
                        else if ((type.equalsIgnoreCase("primef") || type.equalsIgnoreCase("primf"))
                                && parameters.length == 2){
                            return primeFactors(evaluate(parameters[1]));
                        }
                        else if (type.equalsIgnoreCase("randomf")){
                            return _Random_.randomf();
                        }
                        else if (type.equalsIgnoreCase("tangent") && parameters.length == 3){
                            return tangentLine(parameters);
                        }
                        else if (type.equalsIgnoreCase("quad") && parameters.length == 4){
                            return quadraticFormula(parameters);
                        }
                        else if (type.equalsIgnoreCase("cubic") && parameters.length == 5){
                            return cubicFormula(parameters);
                        }
                        else if (type.equalsIgnoreCase("long") && parameters.length == 2){
                            return evaluateString(parameters[1]);
                        }
                        else if ((type.equalsIgnoreCase("scint") ||
                                type.equalsIgnoreCase("sci")) && parameters.length == 2){
                            return evaluate(parameters[1])+"";
                        }
                        else if (type.equalsIgnoreCase("mod") && parameters.length == 3){
                            return mod(parameters);
                        }
                        else if (type.equalsIgnoreCase("congruence") && parameters.length == 4){
                            return congruence(parameters);
                        }
                        else if (type.equalsIgnoreCase("line") && (parameters.length == 4 || parameters.length == 5)){
                            return getLineEquation(parameters);
                        }
                        else if (type.equalsIgnoreCase("complex") || type.equalsIgnoreCase("cmplx")){
                            if (parameters.length == 6){
                                return complex(parameters);
                            }
                            if (parameters.length == 2){
                                return complexFormula(parameters[1]);
                            }
                        }
                        else if (type.equalsIgnoreCase("volume") && (parameters.length >= 2)){
                            return getVolume(parameters);
                        }
                        else if (type.equalsIgnoreCase("area") && (parameters.length >= 2)){
                            return getArea(parameters);
                        }
                        else if (type.equalsIgnoreCase("dot") && (parameters.length == 2 || (parameters.length > 4 && (parameters.length-1)%2 == 0))){
                            return getDotProduct(parameters);
                        }
                        else if (type.equalsIgnoreCase("cross") && (parameters.length == 2 || parameters.length == 7)){
                            return getCrossProduct(parameters);
                        }
                        else if ((type.equalsIgnoreCase("proj") || type.equalsIgnoreCase("projection"))
                                && (parameters.length == 2 || (parameters.length > 4 && (parameters.length-1)%2 == 0))){
                            return getProjection(parameters);
                        }
                        else if ((type.equalsIgnoreCase("perp") || type.equalsIgnoreCase("perpendicular"))
                                && (parameters.length == 2 || (parameters.length > 4 && (parameters.length-1)%2 == 0))){
                            return getPerpendicular(parameters);
                        }
                        else if (type.equalsIgnoreCase("RREF") && parameters.length > 1){
                            return getReducedRowEchelonForm(parameters);
                        }
                        else if (type.equalsIgnoreCase("RCEF") && parameters.length > 1){
                            return getReducedColumnEchelonForm(parameters);
                        }
                        else if (type.equalsIgnoreCase("Inv") && parameters.length > 1){
                            return getMatrixInverse(parameters);
                        }
                        else if (type.equalsIgnoreCase("det") && parameters.length > 1){
                            return getMatrixDeterminant(parameters);
                        }
                        else if ((type.equalsIgnoreCase("slopeF") || type.equalsIgnoreCase("directF")
                                || type.equalsIgnoreCase("sf") || type.equalsIgnoreCase("df")) && parameters.length == 2){
                            drawDirectionField(parameters[1]);
                            return "";
                        }
                        else if (type.equalsIgnoreCase("sort") && parameters.length > 2){
                            return sort(parameters);
                        }
                        else if (type.equalsIgnoreCase("dict") && parameters.length == 2){
                            if(Desktop.isDesktopSupported()){
                                try {
                                    Desktop.getDesktop().browse(new URI("http://www.dictionary.com/browse/"+parameters[1]));
                                } catch (IOException ex) {} catch (URISyntaxException ex) {}
                            }
                            return "Redirected to definitions of "+parameters[1];
                        }
                        else if (type.equalsIgnoreCase("thes") && parameters.length == 2){
                            if(Desktop.isDesktopSupported()){
                                try {
                                    Desktop.getDesktop().browse(new URI("http://www.thesaurus.com/browse/"+parameters[1]));
                                } catch (IOException ex) {} catch (URISyntaxException ex) {}
                            }
                            return "Redirected to synonyms of "+parameters[1];
                        }
                        else if (type.equalsIgnoreCase("parse") && parameters.length == 2){
                            return toPostfix(parameters[1]);
                        }
                    }
                }catch (NumberFormatException | StringIndexOutOfBoundsException e){}
            }
        }
        return "Invalid Input Error - Unrecognized Operation";
    }

    public static String EEA_LDE (String type, String[] parameters){
        try{
            int a = (int)(evaluate(parameters[1]));
            int b = (int)(evaluate(parameters[2]));
            if (a != 0 && b != 0){
                double gcd = Mod.gcd(a, b);
                double d = gcd;
                if (type.equalsIgnoreCase("EEA") && parameters.length == 4){
                    d = (evaluate(parameters[3]));
                }
                if (type.equalsIgnoreCase("EEA") && d%gcd != 0){
                    return("No Integer Solutions");
                }
                int[] lineA = new int[4];
                int[] lineB = new int[4];
                int[] lineC = new int[4];
                if (Math.abs(a)>Math.abs(b)){
                    lineA[0] = 1;  lineA[1] = 0;  lineA[2] = Math.abs(a);  lineA[3] = 0;
                    lineB[0] = 0;  lineB[1] = 1;  lineB[2] = Math.abs(b);  lineB[3] = 0;
                }
                else{
                    lineA[0] = 0;  lineA[1] = 1;  lineA[2] = Math.abs(b);  lineA[3] = 0;
                    lineB[0] = 1;  lineB[1] = 0;  lineB[2] = Math.abs(a);  lineB[3] = 0;
                }
                lineC[0] = 1;  lineC[1] = 1;  lineC[2] = 1;  lineC[3] = 1;
                //System.out.println(lineA[0]+"    "+lineA[1]+"    "+lineA[2]+"    "+lineA[3]);
                //System.out.println(lineB[0]+"    "+lineB[1]+"    "+lineB[2]+"    "+lineB[3]);
                while(lineC[2] != 0){
                    lineC[3] = lineA[2]/lineB[2];
                    lineC[2] = lineA[2]%lineB[2];
                    lineC[1] = lineA[1]-lineB[1]*lineC[3];
                    lineC[0] = lineA[0]-lineB[0]*lineC[3];
                    //System.out.println(lineC[0]+"    "+lineC[1]+"    "+lineC[2]+"    "+lineC[3]);
                    if (lineC[2]==0){
                        break;
                    }
                    else{
                        for (int i = 0; i<lineA.length; i++){
                            lineA[i] = lineB[i];
                            lineB[i] = lineC[i];
                        }
                    }
                }
                if (a<0){
                    lineB[0] = -1*lineB[0];
                }
                if (b<0){
                    lineB[1] = -1*lineB[1];
                }
                int ratio = (int)(d/gcd);
                String particularX = Fraction.getFraction(lineB[0]*ratio);
                try{
                    double px = Double.parseDouble(particularX);
                    particularX = MathRound.roundf(lineB[0]*ratio, 13);
                }catch (NumberFormatException e){}
                String particularY = Fraction.getFraction(lineB[1]*ratio);
                try{
                    double py = Double.parseDouble(particularY);
                    particularY = MathRound.roundf(lineB[1]*ratio, 13);
                }catch (NumberFormatException e){}
                String result = "";
                if (b < 0){
                    result = (a+"("+particularX+") - "+Math.abs(b)+"("+particularY+") = "+d);
                }
                else{
                    result = (a+"("+particularX+") + "+b+"("+particularY+") = "+d);
                }
                if (type.equalsIgnoreCase("EEA")){
                    String bd = "";
                    if (b/gcd < 0){
                        bd = " - "+MathRound.roundf(Math.abs(b/gcd), 13);
                    }
                    else{
                        bd = " + "+MathRound.roundf(Math.abs(b/gcd), 13);
                    }
                    if (bd.endsWith(" 1")){
                        bd = bd.substring(0, bd.length()-1);
                    }
                    String ad = "";
                    if (a/gcd > 0){
                        ad = " - "+MathRound.roundf(Math.abs(a/gcd), 13);
                    }
                    else{
                        ad = " + "+MathRound.roundf(Math.abs(a/gcd), 13);
                    }
                    if (ad.endsWith(" 1")){
                        ad = ad.substring(0, ad.length()-1);
                    }
                    result += "             Complete Soln:  x = "+particularX+bd+"n,     y = "+particularY+ad+"n   For all integers n";
                }
                return (result);
            }
        }catch (NumberFormatException | StringIndexOutOfBoundsException e){}
        return "Invalid Input Error - Failed to compute EEA/LDE";
    }

    public static String deriv (String[] parameters){
        try{
            if (parameters.length == 2){
                String derivative = Derivative.calculate(parameters[1]);
                return ("= "+derivative);
            }
            else{
                try{
                    String[] derivatives = new String[Integer.parseInt(parameters[2])];
                    derivatives[0] = Derivative.calculate(parameters[1]);
                    for (int a = 1; a<derivatives.length; a++){
                        derivatives[a] = Derivative.calculate(derivatives[a-1]);
                    }
                    return ("= "+derivatives[derivatives.length-1]);
                }catch (NumberFormatException | ArrayIndexOutOfBoundsException e){
                    String derivative = Derivative.calculate(parameters[1].replaceAll(parameters[2], var));
                    if (!derivative.equals("Not Differentiable")){
                        return "= "+derivative.replaceAll(var, parameters[2]);
                    }
                    return ("Invalid Degree");
                }
            }
        }catch(StringIndexOutOfBoundsException e){}
        return "Invalid Input Error - Failed to compute derivative";
    }

    public static String antidifferentiation(String[] parameters){
        String antiderivative = Integral.calculate(parameters[1]);
        if (!antiderivative.contains("Not Antidifferentiable")){
            if(parameters.length == 2){
                return "= "+antiderivative+" + C";
            }
            else if(parameters.length == 3){
                return "= "+evaluate(antiderivative, Double.parseDouble(parameters[2]));
            }
        }
        else if(parameters.length == 3){
            antiderivative = Integral.calculate(parameters[1].replaceAll(parameters[2], var));
            if (!antiderivative.contains("Not Antidifferentiable")){
                return "= "+antiderivative.replaceAll(var, parameters[2]);
            }
        }
        return "Invalid Input Error - Failed to compute integral";
    }

    public static String isPrimeNumber(int num){
        String[] prime = {"is composite", "is prime"};
        List<Integer> factors = new ArrayList<>();
        factors.add(1);
        int startPoint = 2;
        int ratio = 1;
        if (num%2 != 0){
            startPoint = 3;
            ratio = 2;
        }
        for (int a = startPoint; a<num; a+=ratio){
            if (num%a == 0){
                factors.add(a);
            }
        }
        factors.add(num);
        String result = factors.get(0)+"";
        for (int a = 1; a<factors.size(); a++){
            result += ", "+factors.get(a);
        }
        result += " are the factors of "+num;
        return num+" "+prime[_Number_.isPrime(num)]+". "+result;
    }

    public static String tangentLine (String[] parameters){
        //=(tangent, (e^x)*(cosx), 0)
        double x = evaluate(parameters[2]),
                y = evaluate(parameters[1], x),
                m = Derivative.nDeriv(parameters[1], x);
        String mf = Fraction.getFraction(m, true);
        if (m == 1){
            mf = "";
        }
        double b = y-m*x;
        String bf = Fraction.getFraction(b, true);
        if (bf.startsWith("-")){
            if (bf.startsWith("(")){
                bf = "- ("+bf.substring(2);
            }
            else{
                bf = "- "+bf.substring(1);
            }
        }
        else{
            bf = "+ "+bf;
        }
        return "y = "+mf+"x "+bf;
    }

    public static String quadraticFormula (String[] parameters){
        Fraction a = new Fraction(evaluate(parameters[1]));
        Fraction b = new Fraction(evaluate(parameters[2]));
        Fraction c = new Fraction(evaluate(parameters[3]));

        String function = " "+a.getString()+"x² + "+b.getString()+"x + "+c.getString();
        function = function.replaceAll("\\+ \\-", "\\- ");
        function = function.replaceAll(" 1x", " x");
        function = function.replaceAll(" \\+ 0x", "");
        function = function.replaceAll(" \\- 0x", "");
        if (function.endsWith(" 0")){
            function = function.substring(0, function.length()-4);
        }

        if (a.getValue() == 0){
            return "Invalid Input Error - Invalid Quadratic: "+function;
        }
        double discriminant = (b.getValue()*b.getValue()-4*a.getValue()*c.getValue());

        if (discriminant > 0){
            double sqrt = Math.sqrt(discriminant);
            if (sqrt%1 == 0){
                discriminant = sqrt;
                return "x = "+(new Fraction(-1*b.getValue()+discriminant, 2*a.getValue())).getString()+",  "+
                        (new Fraction(-1*b.getValue()-discriminant, 2*a.getValue())).getString()+"  are the roots of "+function;
            }
            else{
                b.multiply(-1);
                a.multiply(2);
                double gcd = 1;
                if (b.getValue() == 0){
                    gcd = Mod.gcd(discriminant*discriminant, a.getValue());
                }
                else{
                    gcd = Mod.gcd(b.getValue(), Mod.gcd(discriminant*discriminant, a.getValue()));
                }
                if (gcd != 0){
                    b.divide(gcd);
                    a.divide(gcd);
                    discriminant /= (gcd*gcd);
                }
                String roots = "x = ";
                String frac = Fraction.getFraction(discriminant, true);
                if (b.getValue() != 0){
                    if (a.getValue() != 1){
                        roots += "("+b.getString()+" + √"+frac+")/"+a.getString()+",  "+
                                "("+b.getString()+" - √"+frac+")/"+a.getString();
                    }
                    else{
                        roots += b.getString()+" ± √"+frac;
                    }
                    roots += "  are the roots of "+function+". "+
                            "Approximate Forms:  x = "+(b.getValue()+sqrt)/a.getValue()+",  "+(b.getValue()-sqrt)/a.getValue();
                }
                else{
                    if (a.getValue() != 1){
                        roots += "(±√"+frac+")/"+a.getString();
                    }
                    else{
                        roots += "±√"+frac;
                    }
                    roots += "  are the roots of "+function+". "+
                            "Approximate Forms:  x = ±"+(b.getValue()+sqrt)/a.getValue();
                }
                return roots;
            }
        }
        else if (discriminant == 0){
            return "x = "+(new Fraction(-1*b.getValue(), 2*a.getValue())).getString()+"  is the root of "+function;
        }
        return function+" has no real roots";
    }
    public static String cubicFormula(String[] parameters){
        String string = "";
        Fraction a = new Fraction(evaluate(parameters[1]));
        Fraction b = new Fraction(evaluate(parameters[2]));
        Fraction c = new Fraction(evaluate(parameters[3]));
        Fraction d = new Fraction(evaluate(parameters[4]));

        //cubic(1, 0, 3, -1)
        String function = " "+a.getString()+"x³ + "+b.getString()+"x² + "+c.getString()+"x + "+d.getString();
        function = function.replaceAll("\\+ \\-", "\\- ");
        function = function.replaceAll(" 1x", " x");
        function = function.replaceAll(" \\+ 0x²", "");
        function = function.replaceAll(" \\- 0x²", "");
        function = function.replaceAll(" \\+ 0x", "");
        function = function.replaceAll(" \\- 0x", "");
        if (function.endsWith(" 0")){
            function = function.substring(0, function.length()-4);
        }

        if (a.getValue() == 0 || a.getValue() == NaN || b.getValue() == NaN ||
                c.getValue() == NaN || d.getValue() == NaN){
            return "Invalid Input Error - Invalid Cubic Function:  "+function;
        }


        List<Double> roots = Roots.cubicFormula(a.getValue(), b.getValue(), c.getValue(), d.getValue());
        double[] zeros = new double[roots.size()];
        for (int i = 0; i<zeros.length; i++){
            zeros[i] = roots.get(i);
        }
        Sort.quicksort(zeros);
        if (!roots.isEmpty()){
            boolean simplified = false;
            string = "x = ";
            for (int i = 0; i<zeros.length; i++){
                if (i != 0){
                    string += ", ";
                }
                String frac = Fraction.getFraction(zeros[i]);
                if (!frac.equals(zeros[i]+"")){
                    simplified = true;
                }
                string += frac;//_Number_.removeEnding0(roots.get(i)+"");
            }
            if (roots.size() == 1){
                string += "  is the only real root found for "+function;
            }
            else{
                string += "  are the real roots of "+function;
            }
            if (!simplified){
                string += ". The Approximate Forms:  x = ";
                for (int i = 0; i<zeros.length; i++){
                    if (i != 0){
                        string += ", ";
                    }
                    string += zeros[i];//_Number_.removeEnding0(roots.get(i)+"");
                }
            }
            return string;
        }
        return "Could not find any real roots for "+function;
    }

    public static String line (double slope, double x, double y){
        String slopef = Fraction.getFraction(slope, true);
        double yint = (y-x*slope);
        if (yint < 0){
            return "y = "+slopef+"x - "+Fraction.getFraction(Math.abs(yint));
        }
        return "y = "+slopef+"x + "+Fraction.getFraction(yint);
    }

    public static String getLineEquation(String[] parameters){
        if (parameters.length == 4){
            double slope = evaluate(parameters[1]);
            double x1 = evaluate(parameters[2]);
            double y1 = evaluate(parameters[3]);
            return line (slope, x1, y1);
        }
        double x1 = evaluate(parameters[1]);
        double y1 = evaluate(parameters[2]);
        double x2 = evaluate(parameters[3]);
        double y2 = evaluate(parameters[4]);
        double slope = ((y2-y1)/(x2-x1));
        return line (slope, x1, y1);
    }

    public static String complex (String[] parameters){
        double realU = evaluate(parameters[1]);
        String realUf = Fraction.getFraction(realU);
        double imU = evaluate(parameters[2]);
        String[] signs = {" - ", "", " + "};
        String imUf = "";
        if (imU != 0){
            imUf = signs[_Number_.getSign(imU)+1]+Fraction.getFraction(Math.abs(imU), true)+"i";
        }
        String operation = parameters[3];
        double realV = evaluate(parameters[4]);
        String realVf = Fraction.getFraction(realV);
        double imV = evaluate(parameters[5]);
        String imVf = "";
        if (imV != 0){
            imVf = signs[_Number_.getSign(imV)+1]+Fraction.getFraction(Math.abs(imV), true)+"i";
        }
        String computation = "("+realUf+imUf+")"+operation+"("+realVf+imVf+")";
        if (operation.equals("+")){
            String realX = Fraction.getFraction(realU+realV);
            String imX = "";
            if (imU+imV != 0){
                imX = signs[_Number_.getSign(imU+imV)+1]+Fraction.getFraction(Math.abs(imU+imV), true)+"i";
            }
            return computation+" = "+realX+imX;
        }
        else if (operation.equals("-")){
            String realX = Fraction.getFraction(realU-realV);
            String imX = "";
            if (imU-imV != 0){
                imX = signs[_Number_.getSign(imU-imV)+1]+Fraction.getFraction(Math.abs(imU-imV), true)+"i";
            }
            return computation+" = "+realX+imX;
        }
        else if (operation.equals("*")){
            String realX = Fraction.getFraction(realU*realV-imU*imV);
            String imX = "";
            if (realU*imV+imU*realV != 0){
                imX = signs[_Number_.getSign(realU*imV+imU*realV)+1]+Fraction.getFraction(Math.abs(realU*imV+imU*realV), true)+"i";
            }
            return computation+" = "+realX+imX;
        }
        else if (operation.equals("/")){
            double real = (realU*realV + imU*imV)/(realV*realV + imV*imV);
            double im = (imU*realV - realU*imV)/(realV*realV + imV*imV);
            String realX = Fraction.getFraction(real);
            String imX = "";
            if (im != 0){
                imX = signs[_Number_.getSign(im)+1]+Fraction.getFraction(Math.abs(im), true)+"i";
            }
            return computation+" = "+realX+imX;
        }
        return "Invalid Input Error - Unrecognized Operation";
    }

    public static String complexFormula(String type){
        if (type.equals("+")){
            return "(a+bi) + (c+di) = (a+c) + (b+d)i";
        }
        else if (type.equals("-")){
            return "(a+bi) - (c+di) = (a-c) + (b-d)i";
        }
        else if (type.equals("*")){
            return "(a+bi)(c+di) = (ac-bd) + (ad+bc)i";
        }
        else if (type.equals("/")){
            return "(a+bi)/(c+di) = (ac+bd)/(c²+d²) + [(bc-ad)/(c²+d²)]i";
        }
        return "Invalid Input Error - Unrecognized Operation";
    }

    public static String primeFactors (double num){
        double number = num;
        if (num%1 == 0 && Math.abs(num)<Integer.MAX_VALUE){
            if (_Number_.isPrime((int)num) == 1){
                return (int)num+" is prime and so it is its own prime factorization";
            }
            List<List<Integer>> factors = new ArrayList<>();
            double limit = Math.sqrt(num);
            int a;
            for (a = 0; _Number_.getPrimes().get(a)<limit; a++){
                if (num%_Number_.getPrimes().get(a) == 0){
                    if (factors.isEmpty() || factors.get(factors.size()-1).get(0).equals(_Number_.getPrimes().get(a))){
                        factors.add(new ArrayList<>());
                        factors.get(factors.size()-1).add(_Number_.getPrimes().get(a));
                    }
                    else {
                        factors.get(factors.size()-1).add(_Number_.getPrimes().get(a));
                    }
                    num /= _Number_.getPrimes().get(a);
                    a--;
                }
            }
            a++;
            while(true){
                if (num%_Number_.getPrimes().get(a) == 0){
                    if (factors.isEmpty() || factors.get(factors.size()-1).get(0).equals(_Number_.getPrimes().get(a))){
                        factors.get(factors.size()-1).add(_Number_.getPrimes().get(a));
                    }
                    else {
                        factors.add(new ArrayList<>());
                        factors.get(factors.size()-1).add(_Number_.getPrimes().get(a));
                    }
                    num /= _Number_.getPrimes().get(a);
                }
                else{
                    break;
                }
            }
            String primeFactors = factors.get(0).get(0)+"";
            if (factors.get(0).size() > 1){
                primeFactors += "^"+factors.get(0).size();
            }
            for (a = 1; a<factors.size(); a++){
                primeFactors += ", "+factors.get(a).get(0);
                if (factors.get(a).size() > 1){
                    primeFactors += "^"+factors.get(a).size();
                }
            }
            return primeFactors+" are the prime factors of "+(int)number;
        }
        return "Non-integer so no prime factors";
    }

    public static String getVolume(String[] parameters){
        try{
            if (parameters.length == 2){
                return Formulas.getVolumeFormula(parameters[1].trim());
            }
            else{
                double[] arguments = new double[parameters.length-2];
                for (int a = 0; a<arguments.length; a++){
                    arguments[a] = Double.parseDouble(parameters[a+2]);
                }
                return Formulas.calculateVolume(parameters[1].trim(), arguments);
            }
        }catch (NumberFormatException e){}
        return "Invalid Input Error - Could not find Volume";
    }

    public static String getArea(String[] parameters){
        try{
            if (parameters.length == 2){
                return Formulas.getAreaFormula(parameters[1].trim());
            }
            else{
                double[] arguments = new double[parameters.length-2];
                for (int a = 0; a<arguments.length; a++){
                    arguments[a] = Double.parseDouble(parameters[a+2]);
                }
                return Formulas.calculateArea(parameters[1].trim(), arguments);
            }
        }catch (NumberFormatException e){}
        return "Invalid Input Error - Could not find Area";
    }

    public static String mod (String[] parameters){
        BigInteger a;
        if (parameters[1].contains("^")){
            try{
                BigInteger b = new BigInteger(parameters[1].substring(0, parameters[1].indexOf("^")));
                a = b.pow(Integer.parseInt(parameters[1].substring(parameters[1].indexOf("^")+1)));
            }catch(NumberFormatException e){
                a = new BigInteger(MathRound.roundf(evaluate(parameters[1]), 15, false));
            }
        }
        else{
            a = new BigInteger(MathRound.roundf(evaluate(parameters[1]), 15, false));
        }
        BigInteger m;
        if (parameters[2].contains("^")){
            try{
                BigInteger b = new BigInteger(parameters[2].substring(0, parameters[2].indexOf("^")));
                m = b.pow(Integer.parseInt(parameters[2].substring(parameters[2].indexOf("^")+1)));
            }catch(NumberFormatException e){
                m = new BigInteger(MathRound.roundf(evaluate(parameters[2]), 15, false));
            }
        }
        else{
            m = new BigInteger(MathRound.roundf(evaluate(parameters[2]), 15, false));
        }
        BigInteger[] qr = a.divideAndRemainder(m);
        return (parameters[1]+" ≡ "+qr[1].toString()+" (mod "+parameters[2]+")"
                + "        since "+parameters[1]+" = "+qr[0].toString()+"*"+parameters[2]+" + "+qr[1].toString()+"");
    }

    public static String congruence (String[] parameters){
        try{
            int mod = Integer.parseInt(parameters[3].trim());
            int rs = Integer.parseInt(parameters[2].trim());
            while (rs < 0){
                rs += mod;
            }
            while (rs > mod){
                rs -= mod;
            }
            Function f = new Function(parameters[1]);
            List<Integer> congruent = new ArrayList<>();
            for (int a = 0; a<mod; a++){
                if (f.of(a)%mod == rs){
                    congruent.add(a);
                }
            }
            String equation =  parameters[1]+" ≡ "+rs+" (mod "+mod+")";
            if (congruent.size() > 0){
                String output = "x ≡ "+congruent.get(0);
                if (congruent.size() == 1){
                    return output+" is the solutions to "+equation;
                }
                for (int a = 1; a<congruent.size(); a++){
                    output += ", "+congruent.get(a);
                }
                return output+" are the solutions to "+equation;
            }
            else{
                return equation+" has no solutions";
            }
        }catch (NumberFormatException e){}
        return "Invalid Input Error - Invalid Congruence";
    }

    public static String getDotProduct(String[] parameters){
        if (parameters.length == 2){
            return "[v₁, ... , vⱼ]·[w₁, ... , wⱼ] = v₁×w₁ + ... + vⱼ×wⱼ";
        }
        int vector_size = (parameters.length-1)/2;
        double[] v = new double[vector_size];
        double[] w = new double[vector_size];
        for (int a = 1; a-1<v.length; a++){
            v[a-1] = evaluate(parameters[a].replaceAll(" ", "").replaceAll("\\[", "").replaceAll("\\]", ""));
        }
        for (int a = 1+v.length; a-1-v.length<v.length; a++){
            w[a-1-v.length] = evaluate(parameters[a].replaceAll(" ", "").replaceAll("\\[", "").replaceAll("\\]", ""));
        }
        String vVector = "["+_Number_.removeEnding0(v[0]+"");
        for (int a = 1; a<v.length; a++){
            vVector += ", "+_Number_.removeEnding0(v[a]+"");
        }
        vVector += "]";
        String wVector = "["+_Number_.removeEnding0(w[0]+"");
        for (int a = 1; a<v.length; a++){
            wVector += ", "+_Number_.removeEnding0(w[a]+"");
        }
        wVector += "]";
        double dot = _Vector_.dotProduct(v, w);
        return _Number_.removeEnding0(dot+"")+" = "+vVector+"·"+wVector;
    }

    public static String getCrossProduct (String[] parameters){
        if (parameters.length == 2){
            return "[v₁, v₂, v₃]×[w₁, w₂, w₃] = [v₂·w₃ - w₂·v₃, v₃·w₁ - w₃·v₁, v₁·w₂ - w₁·v₂]";
        }
        double[] v = {evaluate(parameters[1].replaceAll(" ", "").replaceAll("\\[", "").replaceAll("\\]", "")),
                evaluate(parameters[2].replaceAll(" ", "").replaceAll("\\[", "").replaceAll("\\]", "")),
                evaluate(parameters[3].replaceAll(" ", "").replaceAll("\\[", "").replaceAll("\\]", ""))};
        double[] w = {evaluate(parameters[4].replaceAll(" ", "").replaceAll("\\[", "").replaceAll("\\]", "")),
                evaluate(parameters[5].replaceAll(" ", "").replaceAll("\\[", "").replaceAll("\\]", "")),
                evaluate(parameters[6].replaceAll(" ", "").replaceAll("\\[", "").replaceAll("\\]", ""))};
        double[] cross = crossProduct(v, w);
        return "["+_Number_.removeEnding0(cross[0]+"")+", "+_Number_.removeEnding0(cross[1]+"")+", "+_Number_.removeEnding0(cross[2]+"")+"] = "
                + "["+_Number_.removeEnding0(v[0]+"")+", "+_Number_.removeEnding0(v[1]+"")+", "+_Number_.removeEnding0(v[2]+"")+"]"
                + "×["+_Number_.removeEnding0(w[0]+"")+", "+_Number_.removeEnding0(w[1]+"")+", "+_Number_.removeEnding0(w[2]+"")+"]";
    }
    public static double[] crossProduct(double[] v, double[] w){
        double[] cross = {
                v[1]*w[2]-w[1]*v[2],
                v[2]*w[0]-w[2]*v[0],
                v[0]*w[1]-w[0]*v[1]};
        return cross;
        //return "["+_Number_.removeEnding0(()+"")+", "+_Number_.removeEnding0(()+"")+", "+_Number_.removeEnding0(()+"")+"]";
    }

    public static String getProjection(String[] parameters){
        if (parameters.length == 2){
            return "projᵥ(u) = (u·v)v/‖v‖²  is the projection of u onto v";
        }
        int vector_size = (parameters.length-1)/2;
        double[] u = new double[vector_size];
        double[] v = new double[vector_size];
        for (int a = 1; a-1<u.length; a++){
            u[a-1] = evaluate(parameters[a].replaceAll(" ", "").replaceAll("\\[", "").replaceAll("\\]", ""));
        }
        boolean v_is_zero_vector = true;
        for (int a = 1+u.length; a-1-u.length<u.length; a++){
            v[a-1-u.length] = evaluate(parameters[a].replaceAll(" ", "").replaceAll("\\[", "").replaceAll("\\]", ""));
            if (v[a-1-u.length] != 0){
                v_is_zero_vector = false;
            }
        }
        if (v_is_zero_vector){
            return "v cannot be the zero vector";
        }
        String uVector = "["+_Number_.removeEnding0(u[0]+"");
        for (int a = 1; a<u.length; a++){
            uVector += ", "+_Number_.removeEnding0(u[a]+"");
        }
        uVector += "]";
        String vVector = "["+_Number_.removeEnding0(v[0]+"");
        for (int a = 1; a<v.length; a++){
            vVector += ", "+_Number_.removeEnding0(v[a]+"");
        }
        vVector += "]";

        String projection = "";
        double dotUV = _Vector_.dotProduct(u, v);
        double dotVV = _Vector_.dotProduct(v, v);
        if (dotUV == 0){
            projection = "0·"+vVector+" = [0";
            for (int a = 1; a<v.length; a++){
                projection += ", 0";
            }
        }
        else{
            Fraction coefficient = new Fraction(dotUV, dotVV);
            coefficient.reduce();
            projection = coefficient.getString()+"·"+vVector+" = ["+coefficient.multiply(v[0]).getString();
            for (int a = 1; a<v.length; a++){
                projection += ", "+coefficient.multiply(v[a]).getString();
            }
        }
        projection += "]";
        return  projection+" is the projection of "+uVector+" onto "+vVector;
    }
    public static String getPerpendicular(String[] parameters){
        if (parameters.length == 2){
            return "perpᵥ(u) = u - proj(u, v) = u - (u·v)v/‖v‖²  is the perpendicular of u onto v";
        }
        int vector_size = (parameters.length-1)/2;
        double[] u = new double[vector_size];
        double[] v = new double[vector_size];
        for (int a = 1; a-1<u.length; a++){
            u[a-1] = evaluate(parameters[a].replaceAll(" ", "").replaceAll("\\[", "").replaceAll("\\]", ""));
        }
        boolean v_is_zero_vector = true;
        for (int a = 1+u.length; a-1-u.length<u.length; a++){
            v[a-1-u.length] = evaluate(parameters[a].replaceAll(" ", "").replaceAll("\\[", "").replaceAll("\\]", ""));
            if (v[a-1-u.length] != 0){
                v_is_zero_vector = false;
            }
        }
        if (v_is_zero_vector){
            return "v cannot be the zero vector";
        }
        String uVector = "["+_Number_.removeEnding0(u[0]+"");
        for (int a = 1; a<u.length; a++){
            uVector += ", "+_Number_.removeEnding0(u[a]+"");
        }
        uVector += "]";
        String vVector = "["+_Number_.removeEnding0(v[0]+"");
        for (int a = 1; a<v.length; a++){
            vVector += ", "+_Number_.removeEnding0(v[a]+"");
        }
        vVector += "]";

        double numerator = _Vector_.dotProduct(u, v);
        double denominator = _Vector_.dotProduct(v, v);
        double gcd = Mod.gcd(numerator, denominator);
        numerator /= gcd;
        denominator /= gcd;

        String perpendicular = "(1/"+_Number_.removeEnding0(denominator+"")+")·["+_Number_.removeEnding0((u[0]*denominator-v[0]*numerator)+"");
        for (int a = 1; a<vector_size; a++){
            perpendicular += ", "+_Number_.removeEnding0((u[a]*denominator-v[a]*numerator)+"");
        }
        perpendicular += "] = ["+_Number_.removeEnding0((u[0]*denominator-v[0]*numerator)+"")+"/"+_Number_.removeEnding0(denominator+"");
        for (int a = 1; a<vector_size; a++){
            perpendicular += ", "+_Number_.removeEnding0((u[a]*denominator-v[a]*numerator)+"")+"/"+_Number_.removeEnding0(denominator+"");
        }
        perpendicular += "]";

        return perpendicular+" is the perpendicular of "+uVector+" onto "+vVector;
    }

    private static String getReducedRowEchelonForm(String[] parameters){
        List<List<String>> matrix = new ArrayList<>();
        List<String> row = new ArrayList<>();
        for (int a = 1; a<parameters.length; a++){
            if (parameters[a].contains("[")){
                row.add(parameters[a].replaceAll("\\[", "").replaceAll(" ", ""));
            }
            else if (parameters[a].contains("]")){
                row.add(parameters[a].replaceAll("\\]", "").replaceAll(" ", ""));
                if (matrix.isEmpty() || row.size() == matrix.get(0).size()){
                    matrix.add(new ArrayList<>(row));
                    row.clear();
                }
                else if (row.size() != matrix.get(0).size()){
                    return "Invalid Input Error - All Rows must be of the same size";
                }
            }
            else if (!row.isEmpty()){
                row.add(parameters[a].replaceAll(" ", ""));
            }
        }
        if (!row.isEmpty()){
            if (matrix.isEmpty() || row.size() == matrix.get(0).size()){
                matrix.add(row);
                row.clear();
            }
            else if (row.size() != matrix.get(0).size()){
                return "Invalid Input Error - All Rows must be of the same size";
            }
        }
        if (!matrix.isEmpty()){
//            Main.w.minimizeHeight();
//            Main.w.setSize((int)(p.getScreenWidth()/2), Main.w.getHeight());
//            Main.w.centerAlign();
//            Main.w.setLocation(Main.w.getX(), Main.w.getHeight()/2);
            String[][] elements = new String[matrix.size()][matrix.get(0).size()];
            for (int a = 0; a<elements.length; a++){
                for (int b = 0; b<elements[a].length; b++){
                    elements[a][b] = matrix.get(a).get(b);
                }
            }
            _Matrix_.rowReduce(new Matrix(elements));
//            MatrixCalculator matrixCalculator = new MatrixCalculator(elements);
//            matrixCalculator.rowReduce();
            return "";
        }
        return "Invalid Matrix";
    }
    private static String getMatrixInverse(String[] parameters){
        List<List<String>> matrixStr = new ArrayList<>();
        List<String> rows = new ArrayList<>();
        for (int a = 1; a<parameters.length; a++){
            if (parameters[a].contains("[")){
                rows.add(parameters[a].replaceAll("\\[", "").replaceAll(" ", ""));
            }
            else if (parameters[a].contains("]")){
                rows.add(parameters[a].replaceAll("\\]", "").replaceAll(" ", ""));
                if (matrixStr.isEmpty() || rows.size() == matrixStr.get(0).size()){
                    matrixStr.add(new ArrayList<>(rows));
                    rows.clear();
                }
                else if (rows.size() != matrixStr.get(0).size()){
                    return "Invalid Input Error - All Rows must be of the same size";
                }
            }
            else if (!rows.isEmpty()){
                rows.add(parameters[a].replaceAll(" ", ""));
            }
        }
        if (!rows.isEmpty()){
            if (matrixStr.isEmpty() || rows.size() == matrixStr.get(0).size()){
                matrixStr.add(rows);
                rows.clear();
            }
            else if (rows.size() != matrixStr.get(0).size()){
                return "Invalid Input Error - All Rows must be of the same size";
            }
        }
        if (!matrixStr.isEmpty()){
            if (matrixStr.size() != matrixStr.get(0).size()){
                return "Invalid Input Error - Matrix must be Square";
            }
//            Main.w.minimizeHeight();
//            Main.w.setSize((int)(p.getScreenWidth()/2), Main.w.getHeight());
//            Main.w.centerAlign();
//            Main.w.setLocation(Main.w.getX(), Main.w.getHeight()/2);
            String[][] elements = new String[matrixStr.size()][2*matrixStr.size()];
            for (int a = 0; a<matrixStr.size(); a++){
                for (int b = 0; b<matrixStr.size(); b++){
                    elements[a][b] = matrixStr.get(a).get(b);
                }
                for (int b = matrixStr.size(); b<2*matrixStr.size(); b++){
                    if ((b-matrixStr.size()) == a){
                        elements[a][b] = "1";
                    }
                    else{
                        elements[a][b] = "0";
                    }
                }
            }
            Matrix matrix = new Matrix(elements);

            Fraction[][] originalFractions = new Fraction[matrix.numRows()][matrix.numColumns()/2];
            Fraction[][] inverseFractions = new Fraction[matrix.numRows()][matrix.numColumns()/2];
            for (int row = 0; row<originalFractions.length; row++){
                for (int column = 0; column<originalFractions[row].length; column++){
                    originalFractions[row][column] = matrix.get(column, row).getCopy();
                    inverseFractions[row][column] = matrix.get(column+matrix.numColumns()/2, row).getCopy();
                }
            }
//            MatrixCalculator matrixCalculator = new MatrixCalculator(elements);
//            matrixCalculator.rowReduce(false);
//            matrixCalculator.drawInverse();
            return "";
        }
        return "Invalid Matrix";
    }
    private static String getMatrixDeterminant(String[] parameters){
        List<List<String>> matrix = new ArrayList<>();
        List<String> row = new ArrayList<>();
        for (int a = 1; a<parameters.length; a++){
            if (parameters[a].contains("[")){
                row.add(parameters[a].replaceAll("\\[", "").replaceAll(" ", ""));
            }
            else if (parameters[a].contains("]")){
                row.add(parameters[a].replaceAll("\\]", "").replaceAll(" ", ""));
                if (matrix.isEmpty() || row.size() == matrix.get(0).size()){
                    matrix.add(new ArrayList<>(row));
                    row.clear();
                }
                else if (row.size() != matrix.get(0).size()){
                    return "Invalid Input Error - All Rows must be of the same size";
                }
            }
            else if (!row.isEmpty()){
                row.add(parameters[a].replaceAll(" ", ""));
            }
        }
        if (!row.isEmpty()){
            if (matrix.isEmpty() || row.size() == matrix.get(0).size()){
                matrix.add(row);
                row.clear();
            }
            else if (row.size() != matrix.get(0).size()){
                return "Invalid Input Error - All Rows must be of the same size";
            }
        }
        if (!matrix.isEmpty()){
            if (matrix.size() != matrix.get(0).size()){
                return "Invalid Input Error - Matrix must be Square";
            }
//            Main.w.minimizeHeight();
//            Main.w.setSize((int)(p.getScreenWidth()/2), Main.w.getHeight());
//            Main.w.centerAlign();
//            Main.w.setLocation(Main.w.getX(), Main.w.getHeight()/2);
            String[][] elements = new String[matrix.size()][matrix.get(0).size()];
            for (int a = 0; a<elements.length; a++){
                for (int b = 0; b<elements[a].length; b++){
                    elements[a][b] = matrix.get(a).get(b);
                }
            }
//            MatrixCalculator matrixCalculator = new MatrixCalculator(elements);
            return "= "+_Matrix_.getDeterminant(new Matrix(elements));
        }
        return "Invalid Matrix";
    }

    private static String getReducedColumnEchelonForm(String[] parameters){
        List<List<String>> matrix = new ArrayList<>();
        List<String> row = new ArrayList<>();
        for (int a = 1; a<parameters.length; a++){
            if (parameters[a].contains("[")){
                row.add(parameters[a].replaceAll("\\[", "").replaceAll(" ", ""));
            }
            else if (parameters[a].contains("]")){
                row.add(parameters[a].replaceAll("\\]", "").replaceAll(" ", ""));
                if (matrix.isEmpty() || row.size() == matrix.get(0).size()){
                    matrix.add(new ArrayList<>(row));
                    row.clear();
                }
                else if (row.size() != matrix.get(0).size()){
                    return "Invalid Input Error - All Columns must be of the same size";
                }
            }
            else if (!row.isEmpty()){
                row.add(parameters[a].replaceAll(" ", ""));
            }
        }
        if (!row.isEmpty()){
            if (matrix.isEmpty() || row.size() == matrix.get(0).size()){
                matrix.add(row);
                row.clear();
            }
            else if (row.size() != matrix.get(0).size()){
                return "Invalid Input Error - All Columns must be of the same size";
            }
        }
        if (!matrix.isEmpty()){
//            Main.w.minimizeHeight();
//            Main.w.setSize((int)(p.getScreenWidth()/2), Main.w.getHeight());
//            Main.w.centerAlign();
//            Main.w.setLocation(Main.w.getX(), Main.w.getHeight()/2);
            String[][] elements = new String[matrix.size()][matrix.get(0).size()];
            for (int a = 0; a<elements.length; a++){
                for (int b = 0; b<elements[a].length; b++){
                    elements[a][b] = matrix.get(a).get(b);
                }
            }
            _Matrix_.rowReduce(new Matrix(elements));
//            MatrixCalculator matrixCalculator = new MatrixCalculator(elements);
//            matrixCalculator.transpose();
//            matrixCalculator.rowReduce();
            return "";
        }
        return "Invalid Matrix";
    }

    private static DirectionField field;
    private static void drawDirectionField(String function){
//        Main.w.minimizeHeight();
//        Main.w.setSize((int)(p.getScreenWidth()/2), Main.w.getHeight());
//        Main.w.centerAlign();
//        Main.w.setLocation(Main.w.getX(), Main.w.getHeight()/2);
        if (field != null && field.isVisible()){
            field.dispose();
        }
        Function f = new Function(function);
        field = new DirectionField("Direction Fields by Antonio Kim", f);
        field.Open();
    }

    private static String sort(String[] parameters){
        //sort(<, 8, 6, 2, 10, 4)
        boolean ascending = true;
        if (parameters[1].startsWith(">")){
            ascending = false;
        }
        int operator_included = 1;
        if (parameters[1].startsWith("<") || parameters[1].startsWith(">") || parameters[1].startsWith("\"")){
            operator_included = 2;
        }
        String[] elements = new String[parameters.length-operator_included];
        for (int a = operator_included; a<parameters.length; a++){
            elements[a-operator_included] = parameters[a];
        }
        String str = "{";
        try{
            if (parameters[1].endsWith("\"")){
                Integer.parseInt("sort string");
            }
            int[] int_elements = new int[elements.length];
            for (int a = 0; a<int_elements.length; a++){
                int_elements[a] = Integer.parseInt(elements[a]);
            }
            if (ascending){
                Sort.quicksort(int_elements);
            }
            else{
                Sort.quicksort(int_elements, false);
            }
            for (int a = 0; a<int_elements.length; a++){
                if (a != 0){
                    str += ", ";
                }
                str += ""+int_elements[a];
            }
            str += "} is sorted in ";
            if (ascending){
                str += "ascending";
            }
            else {
                str += "descending";
            }
            str += " numerical order";
        }catch(NumberFormatException e){
            if (ascending){
                Sort.quicksort(elements);
            }
            else {
                Sort.quicksort(elements, false);
            }
            for (int a = 0; a<elements.length; a++){
                if (a != 0){
                    str += ", ";
                }
                str += ""+elements[a];
            }
            str += "} is sorted in ";
            if (ascending){
                str += "ascending";
            }
            else {
                str += "descending";
            }
            str += " lexicographical order";
        }
        return str;
    }

    private static boolean sorted = false;
    public static int indexOf(String str){
        if (!sorted){
            Sort.quicksort(solvableEquations);
            sorted = true;
        }
        return Search.binarySearch(solvableEquations, str);
    }

}
