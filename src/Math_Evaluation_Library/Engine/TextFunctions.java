package Math_Evaluation_Library.Engine;

import Math_Evaluation_Library.Calculus.Derivative;
import Math_Evaluation_Library.Calculus.Roots;
import Math_Evaluation_Library.Constants.Scripts;
import Math_Evaluation_Library.Constants.StringReplacements;
import Math_Evaluation_Library.Expressions.Expression;
import Math_Evaluation_Library.Expressions.NumberExpression;
import Math_Evaluation_Library.Expressions.VariableExpression;
import Math_Evaluation_Library.Geometry.ShapeFormulas;
import Math_Evaluation_Library.GraphingTechnology.DirectionField;
import Math_Evaluation_Library.LinearAlgebra._Vector_;
import Math_Evaluation_Library.Miscellaneous.Fraction;
import Math_Evaluation_Library.Miscellaneous.MathRound;
import Math_Evaluation_Library.Miscellaneous.Mod;
import Math_Evaluation_Library.Miscellaneous._Random_;
import Math_Evaluation_Library.Objects.TextFunction;
import Math_Evaluation_Library.Objects._Number_;
import Math_Evaluation_Library.Search;
import Math_Evaluation_Library.Sort;

import java.awt.*;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static Math_Evaluation_Library.Engine.Engine.toExpression;
import static Math_Evaluation_Library.Engine.Engine.x;

/**
 * Created by Antonio on 2017-10-19.
 */
public class TextFunctions {

//    public static void main (String[] args){
//        String[] solvableEquations = {
//                "graph", "EEA", "LDE", "deriv", "diff", "prime", "primef", "primf", "randomf", "tangent", "quad", "cubic",
//                "long", "scint", "sci", "mod",  "line", "complex", "cmplx", "int", "antideriv", "antidiff", "simplify",
//                "volume", "area", "congruence", "≡",    "dot", "cross", "proj", "projection", "perp", "perpendicular",
//                "RREF", "RREf", "RCEF", "RCEf", "det", "slopeF", "directF", "sf", "df", "sort",
//                "dict", "thes", "plot", "plt", "parse", "postfix" //, "latex"
//        };
//        Sort.quicksort(solvableEquations);
//        for (String equation :  solvableEquations){
//            System.out.println(
//                    "            new TextFunction(\""+equation+"\")\n{\n" +
//                            "                @Override\n" +
//                            "                public String Engine.evaluateString(String[] parameters, boolean df) {\n" +
//                            "                    if (validNumParameters(parameters.length)){\n\n}\n" +
//                            "                    return INVALID;\n" +
//                            "                }\n" +
//                            "            },");
//        }
//    }

    public static final String INVALID = "Invalid Text Function Parameters";

    public static final TextFunction[] textFunctions = {
            new TextFunction("EEA", "EEA(a, b) computes the Extended Euclidean algorithm on a and b", 3, 4)
            {
                @Override
                public String evaluate(String[] parameters, boolean df) {
                    if (validNumParameters(parameters.length))    return EEA_LDE(true, parameters);
                    return INVALID;
                }
            },
            new TextFunction("LDE", "LDE(a, b) computes the Linear Diophantine Equation of a and b", 3)
            {
                @Override
                public String evaluate(String[] parameters, boolean df) {
                    if (validNumParameters(parameters.length))    return EEA_LDE(false, parameters);
                    return INVALID;
                }
            },
            new TextFunction("area", "area(\uD835\uDC60) gives the area formula for shape \uD835\uDC60. Providing arguments produces a calculated area", -1)
            {
                @Override
                public String evaluate(String[] parameters, boolean df) {
                    try{
                        if (parameters.length == 1)    return ShapeFormulas.getAreaFormula(parameters[0].trim());
                        else{
                            double[] arguments = new double[parameters.length-1];
                            for (int a = 0; a<arguments.length; a++){
                                arguments[a] = Double.parseDouble(parameters[a+1]);
                            }
                            return ShapeFormulas.getArea(parameters[0].trim(), arguments);
                        }
                    }catch (NumberFormatException e){}
                    return "Invalid Input Error - Could not find Area";
                }
            },
//            new TextFunction("complex", "complex([+-*/]) produces the complex operation formula for the specified operator", -1)
//            {
//                @Override
//                public String evaluate(String[] parameters, boolean df) {
//                    if (parameters.length == 5)    return complex(parameters);
//                    if (parameters.length == 1 && parameters[0].length() == 1){
//                        switch (parameters[0].charAt(0)){
//                            case '+':   return "(a+bi) + (c+di) = (a+c) + (b+d)i";
//                            case '-':   return "(a+bi) - (c+di) = (a-c) + (b-d)i";
//                            case '*':   return "(a+bi)(c+di) = (ac-bd) + (ad+bc)i";
//                            case '/':   return "(a+bi)/(c+di) = (ac+bd)/(c²+d²) + [(bc-ad)/(c²+d²)]i";
//                            default:    return "Invalid Input Error - Unrecognized Operation";
//                        }
//                    }
//                    return INVALID;
//                }
//            },
            new TextFunction("cubic", "cubic(a, b, c, d) calculates the roots of ax³+bx²+cx+d", 4)
            {
                @Override
                public String evaluate(String[] parameters, boolean df) {
                    if (validNumParameters(parameters.length)){
                        Expression a = Engine.toExpression(parameters[0]).simplify();
                        Expression b = Engine.toExpression(parameters[1]).simplify();
                        Expression c = Engine.toExpression(parameters[2]).simplify();
                        Expression d = Engine.toExpression(parameters[3]).simplify();

                        //cubic(1, 0, 3, -1)
                        Expression function = a.multiply(new VariableExpression("x").pow(3))
                                .add(b.multiply(new VariableExpression("x").pow(2)))
                                .add(c.multiply(new VariableExpression("x"))).add(d).simplify();

                        if (a.valueOf() == 0 || a.isValid() || b.isValid() || c.isValid() || d.isValid()){
                            return "Invalid Input Error - Invalid Cubic Function:  "+function.infix();
                        }

                        StringBuilder string = new StringBuilder();
                        List<Double> roots = Roots.cubicFormula(a.valueOf(), b.valueOf(), c.valueOf(), d.valueOf());
                        roots.sort(Comparator.naturalOrder());
                        if (!roots.isEmpty()){
                            boolean simplified = false;
                            string.append("x = ");
                            for (int i = 0; i<roots.size(); i++){
                                if (i != 0)    string.append(", ");
                                Expression frac = Fraction.toExpression(roots.get(i));
                                if (!(frac instanceof NumberExpression))    simplified = true;
                                string.append(frac.infix());//_Number_.format(roots.get(i)+"");
                            }
                            if (roots.size() == 1){
                                string.append("  equals the only real root found for ").append(function.infix());
                            }
                            else{
                                string.append("  are the real roots of ").append(function.infix());
                            }
                            if (!simplified){
                                string.append(". The Approximate Forms:  x = ");
                                for (int i = 0; i<roots.size(); i++){
                                    if (i!= 0) string.append(", ");
                                    string.append(roots.get(i).doubleValue());
                                }
                            }
                            return string.toString();
                        }
                        return "Could not find any real roots for "+function.infix();
                    }
                    return INVALID;
                }
            },
            new TextFunction("deriv", "deriv(\uD835\uDC53) computes the derivative of \uD835\uDC53", 1, 2)
            {
                @Override
                public String evaluate(String[] parameters, boolean df) {
                    if (validNumParameters(parameters.length)){
                        try{
                            if (parameters.length == 1){
                                String derivative = Derivative.calculate(parameters[0]).infix();
                                return ((df ? "= " : "")+derivative);
                            }
                            else{
                                Expression e = toExpression(parameters[0]);
                                Expression n = toExpression(parameters[1]);
                                if (n instanceof NumberExpression && n.valueOf() > 1) {
                                    Expression derivative = Derivative.calculate(e);
                                    for (int i = 1; i<n.valueOf(); ++i){
                                        derivative = Derivative.calculate(derivative);
                                    }
                                    if (derivative.isValid()){
                                        return (df ? "= " : "")+derivative.infix();
                                    }
                                }
                                return ("Invalid Degree");
                            }
                        }catch(StringIndexOutOfBoundsException e){}
                        return "Invalid Input Error - Failed to compute derivative";
                    }
                    return INVALID;
                }
            },
            new TextFunction("dict", "dict(\uD835\uDC64) opens up the browser and searches for the definition to the word \uD835\uDC64")
            {
                @Override
                public String evaluate(String parameter, boolean df) {
                    if(Desktop.isDesktopSupported()){
                        try {
                            Desktop.getDesktop().browse(new URI("http://www.dictionary.com/browse/"+parameter));
                            return "Redirected to definitions of "+parameter;
                        } catch (IOException ex) {} catch (URISyntaxException ex) {}
                    }
                    return INVALID;
                }
            },
//            new TextFunction("graph")
//            {
//                @Override
//                public String evaluateString(String[] parameters, boolean df) {
//                    if (validNumParameters(parameters.length)){
//
//                    }
//                    return INVALID;
//                }
//            },
            new TextFunction("int", "int(\uD835\uDC53("+x+"))=∫(\uD835\uDC53("+x+")d\uD835\uDC65) calculates the indefinite integral of \uD835\uDC53("+x+")", 1)
            {
                @Override
                public String evaluate(String[] parameters, boolean df) {
                    if (validNumParameters(parameters.length)){
                        String antiderivative = toExpression(parameters[0]).calculateIntegral().infix();
                        if (!antiderivative.contains("Not Antidifferentiable")){
                            if(parameters.length == 1){
                                return (df ? "= " : "")+antiderivative+" + C";
                            }
                            else if(parameters.length == 2){
                                return (df ? "= " : "")+Engine.evaluate(antiderivative, Double.parseDouble(parameters[1]));
                            }
                        }
//                        else if(parameters.length == 2){
//                            antiderivative = Integral.calculate(parameters[0].replaceAll(parameters[1], Engine.var));
//                            if (!antiderivative.contains("Not Antidifferentiable")){
//                                return (df ? "= " : "")+antiderivative.replaceAll(Engine.var, parameters[1]);
//                            }
//                        }
                        return "Invalid Input Error - Failed to compute integral";
                    }
                    return INVALID;
                }
            },
            new TextFunction("line", "line(m, x, y) calculates the line with slope m going through point (x, y)", 3, 4)
            {
                @Override
                public String evaluate(String[] parameters, boolean df) {
                    if (validNumParameters(parameters.length)){
                        if (parameters.length == 3){
                            double slope = Engine.evaluate(parameters[0]);
                            double x1 = Engine.evaluate(parameters[1]);
                            double y1 = Engine.evaluate(parameters[2]);
                            return line (slope, x1, y1);
                        }
                        double x1 = Engine.evaluate(parameters[0]);
                        double y1 = Engine.evaluate(parameters[1]);
                        double x2 = Engine.evaluate(parameters[2]);
                        double y2 = Engine.evaluate(parameters[3]);
                        double slope = ((y2-y1)/(x2-x1));
                        return line (slope, x1, y1);
                    }
                    return INVALID;
                }
            },
            new TextFunction("mod", "mod(x, m) computes x (mod m)", 2)
            {
                @Override
                public String evaluate(String[] parameters, boolean df) {
                    if (validNumParameters(parameters.length)){
                        BigInteger a;
                        if (parameters[0].contains("^")){
                            try{
                                BigInteger b = new BigInteger(parameters[0].substring(0, parameters[0].indexOf("^")));
                                a = b.pow(Integer.parseInt(parameters[0].substring(parameters[0].indexOf("^")+1)));
                            }catch(NumberFormatException e){
                                a = new BigInteger(MathRound.roundf(Engine.evaluate(parameters[0]), 15, false));
                            }
                        }
                        else{
                            a = new BigInteger(MathRound.roundf(Engine.evaluate(parameters[0]), 15, false));
                        }
                        BigInteger m;
                        if (parameters[1].contains("^")){
                            try{
                                BigInteger b = new BigInteger(parameters[1].substring(0, parameters[1].indexOf("^")));
                                m = b.pow(Integer.parseInt(parameters[1].substring(parameters[1].indexOf("^")+1)));
                            }catch(NumberFormatException e){
                                m = new BigInteger(MathRound.roundf(Engine.evaluate(parameters[1]), 15, false));
                            }
                        }
                        else{
                            m = new BigInteger(MathRound.roundf(Engine.evaluate(parameters[1]), 15, false));
                        }
                        BigInteger[] qr = a.divideAndRemainder(m);
                        return (parameters[0]+" ≡ "+qr[1].toString()+" (mod "+parameters[1]+")"
                                + "        since "+parameters[0]+" = "+qr[0].toString()+"*"+parameters[1]+" + "+qr[1].toString()+"");
                    }
                    return INVALID;
                }
            },
            new TextFunction("perp", "perp(u, v) = perpᵥ(u) = u - proj(u, v) = u - (u·v)v/‖v‖²  equals the perpendicular of u onto v", -1)
            {
                @Override
                public String evaluate(String[] parameters, boolean df) {
                    if (parameters.length == 0){
                        return "perpᵥ(u) = u - proj(u, v) = u - (u·v)v/‖v‖²  equals the perpendicular of u onto v";
                    }
                    else if (parameters.length > 3 && parameters.length%2 == 0){

                        int vector_size = (parameters.length-1)/2;
                        double[] u = new double[vector_size];
                        double[] v = new double[vector_size];
                        for (int a = 0; a<u.length; a++){
                            u[a] = Engine.evaluate(parameters[a].replaceAll(" ", "").replaceAll("\\[", "").replaceAll("\\]", ""));
                        }
                        boolean v_is_zero_vector = true;
                        for (int a = u.length; a-u.length<u.length; a++){
                            v[a-u.length] = Engine.evaluate(parameters[a].replaceAll(" ", "").replaceAll("\\[", "").replaceAll("\\]", ""));
                            if (v[a-u.length] != 0)    v_is_zero_vector = false;
                        }
                        if (v_is_zero_vector)    return "v cannot be the zero vector";
                        String uVector = "["+_Number_.format(u[0]+"");
                        for (int a = 1; a<u.length; a++){
                            uVector += ", "+_Number_.format(u[a]+"");
                        }
                        uVector += "]";
                        String vVector = "["+_Number_.format(v[0]+"");
                        for (int a = 1; a<v.length; a++){
                            vVector += ", "+_Number_.format(v[a]+"");
                        }
                        vVector += "]";

                        double numerator = _Vector_.dotProduct(u, v);
                        double denominator = _Vector_.dotProduct(v, v);
                        double gcd = Mod.gcd(numerator, denominator);
                        numerator /= gcd;
                        denominator /= gcd;

                        String perpendicular = "(1/"+_Number_.format(denominator+"")+")·["+_Number_.format((u[0]*denominator-v[0]*numerator)+"");
                        for (int a = 1; a<vector_size; a++){
                            perpendicular += ", "+_Number_.format((u[a]*denominator-v[a]*numerator)+"");
                        }
                        perpendicular += "] = ["+_Number_.format((u[0]*denominator-v[0]*numerator)+"")+"/"+_Number_.format(denominator+"");
                        for (int a = 1; a<vector_size; a++){
                            perpendicular += ", "+_Number_.format((u[a]*denominator-v[a]*numerator)+"")+"/"+_Number_.format(denominator+"");
                        }
                        perpendicular += "]";

                        return perpendicular+" equals the perpendicular of "+uVector+" onto "+vVector;
                    }
                    return INVALID;
                }
            },
//            new TextFunction("plot")
//            {
//                @Override
//                public String evaluateString(String[] parameters, boolean df) {
//                    if (validNumParameters(parameters.length)){
//
//                    }
//                    return INVALID;
//                }
//            },
            new TextFunction("postfix", "postfix(\uD835\uDC53) converts \uD835\uDC53 from infix to postfix notation")
            {
                @Override
                public String evaluate(String parameter, boolean df) {
                    return toExpression(parameter).postfix();
                }
            },
            new TextFunction("prime", "prime(\uD835\uDC65) determines whether \uD835\uDC65 equals prime or composite")
            {
                @Override
                public String evaluate(String parameter, boolean df) {
                    try {
                        int num = Integer.parseInt(parameter);
                        String[] prime = {"equals composite", "equals prime"};
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
                    } catch (NumberFormatException e){}
                    return INVALID;
                }
            },
            new TextFunction("primef", "primef(\uD835\uDC65) calculates the prime factors of \uD835\uDC65")
            {
                @Override
                public String evaluate(String parameter, boolean df) {
                    double num = Engine.evaluate(parameter);
                    double number = num;
                    if (num%1 == 0 && Math.abs(num)<Integer.MAX_VALUE){
                        if (_Number_.isPrime((int)num) == 1)    return (int)num+" equals prime and so it equals its own prime factorization";
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
                                primeFactors += Scripts.toSuperScript(_Number_.format(factors.get(a).size()));
                            }
                        }
                        return primeFactors+" are the prime factors of "+(int)number;
                    }
                    return "Non-integer so no prime factors";
                }
            },
            new TextFunction("proj", "proj(u, v) = projᵥ(u) = (u·v)v/‖v‖²  equals the projection of u onto v", -1)
            {
                @Override
                public String evaluate(String[] parameters, boolean df) {
                    if (parameters.length == 2){
                        return "projᵥ(u) = (u·v)v/‖v‖²  equals the projection of u onto v";
                    }
                    else if (parameters.length > 3 && parameters.length%2 == 0){
                        int vector_size = parameters.length/2;
                        double[] u = new double[vector_size];
                        double[] v = new double[vector_size];
                        for (int a = 0; a<u.length; a++){
                            u[a] = Engine.evaluate(parameters[a].replaceAll(" ", "").replaceAll("\\[", "").replaceAll("\\]", ""));
                        }
                        boolean v_is_zero_vector = true;
                        for (int a = u.length; a-u.length<u.length; a++){
                            v[a-u.length] = Engine.evaluate(parameters[a].replaceAll(" ", "").replaceAll("\\[", "").replaceAll("\\]", ""));
                            if (v[a-u.length] != 0){
                                v_is_zero_vector = false;
                            }
                        }
                        if (v_is_zero_vector){
                            return "v cannot be the zero vector";
                        }
                        String uVector = "["+_Number_.format(u[0]+"");
                        for (int a = 1; a<u.length; a++){
                            uVector += ", "+_Number_.format(u[a]+"");
                        }
                        uVector += "]";
                        String vVector = "["+_Number_.format(v[0]+"");
                        for (int a = 1; a<v.length; a++){
                            vVector += ", "+_Number_.format(v[a]+"");
                        }
                        vVector += "]";

                        StringBuilder projection = new StringBuilder();
                        double dotUV = _Vector_.dotProduct(u, v);
                        double dotVV = _Vector_.dotProduct(v, v);
                        if (dotUV == 0){
                            projection.append("0·").append(vVector).append(" = [0");
                            for (int a = 1; a<v.length; a++){
                                projection.append(", 0");
                            }
                        }
                        else{
                            Expression coefficient = Fraction.reduce(dotUV, dotVV);
                            projection.append(coefficient.infix()).append("·");
                            projection.append(vVector).append(" = [").append(Fraction.toExpression(coefficient.valueOf()*v[0]).infix());
                            for (int a = 1; a<v.length; a++){
                                projection.append(", ").append(Fraction.toExpression(coefficient.valueOf()*v[a]).toRational().infix());
                            }
                        }
                        projection.append("]");
                        return  projection+" equals the projection of "+uVector+" onto "+vVector;
                    }
                    return INVALID;
                }
            },
            new TextFunction("quad", "quad(a, b, c) calculates the roots of ax²+bx+c", 3)
            {
                @Override
                public String evaluate(String[] parameters, boolean df) {
                    if (validNumParameters(parameters.length)){
                        Expression a = Engine.toExpression(parameters[0]).toNumberExpression();
                        Expression b = Engine.toExpression(parameters[1]).toNumberExpression();
                        Expression c = Engine.toExpression(parameters[2]).toNumberExpression();

                        //cubic(1, 0, 3, -1)
                        Expression function = a.multiply(new VariableExpression("x").pow(2))
                                .add(b.multiply(new VariableExpression("x"))).add(c).simplify();

                        if (a.valueOf() == 0){
                            return "Invalid Input Error - Invalid Quadratic: "+function;
                        }
                        double discriminant = (b.valueOf()*b.valueOf()-4*a.valueOf()*c.valueOf());

                        if (discriminant > 0){
                            double sqrt = Math.sqrt(discriminant);
                            if (sqrt%1 == 0){
                                discriminant = sqrt;
                                return "x = "+Fraction.reduce(-1*b.valueOf()+discriminant, 2*a.valueOf()).infix()+",  "+
                                        Fraction.reduce(-1*b.valueOf()-discriminant, 2*a.valueOf()).infix()+"  are the roots of "+function.infix();
                            }
                            else{
                                b = b.multiply(-1);
                                a = a.multiply(2);
                                if (a.isInteger() && b.isInteger() && c.isInteger()) {
                                    double gcd = Mod.gcd(discriminant * discriminant, a.valueOf());
                                    if (b.valueOf() != 0) {
                                        gcd = Mod.gcd(b.valueOf(), gcd);
                                    }
                                    if (gcd != 0) {
                                        b = b.divide(gcd);
                                        a = a.divide(gcd);
                                        discriminant /= (gcd * gcd);
                                    }
                                }
                                StringBuilder roots = new StringBuilder("x = ");
                                Expression frac = Fraction.toExpression(discriminant);
                                if (b.valueOf() != 0){
                                    Expression x1 = Fraction.reduce(b.valueOf()+sqrt, a.valueOf());
                                    Expression x2 = Fraction.reduce(b.valueOf()-sqrt, a.valueOf());
                                    if (!x1.isRational() && !x2.isRational()){
                                        roots.append(b.infix()).append(" ± √").append(frac);
                                        if (a.valueOf() != 1){
                                            roots = new StringBuilder("("+roots.toString()+")/"+a.infix());
                                        }
                                    }
                                    else{
                                        if (x1.isRational()){    roots.append(x1.infix()).append(", ");   }
                                        else{
                                            String root = b.infix()+"+√"+frac;
                                            if (a.valueOf() != 1){    root = "("+root+")/"+a.infix();   }
                                            roots.append(root).append(", ");
                                        }
                                        if (x2.isRational()){    roots.append(x2.infix());   }
                                        else{
                                            String root = b.infix()+"-√"+frac;
                                            if (a.valueOf() != 1){    root = "("+root+")/"+a.infix();   }
                                            roots.append(root).append(", ");
                                        }
                                    }
                                    roots.append(" are the roots of ").append(function).append(" = 0. ")
                                            .append("Approximate Forms:  x = ")
                                            .append(x1.valueOf()).append(", ")
                                            .append(x2.valueOf());
                                }
                                else{
                                    if (a.valueOf() != 1){
                                        roots.append("(±√").append(frac).append(")/").append(a.infix());
                                    }
                                    else{
                                        roots.append("±√").append(frac);
                                    }
                                    roots.append("  are the roots of ").append(function).append(". ")
                                            .append("Approximate Forms:  x = ±")
                                            .append((b.valueOf() + sqrt) / a.valueOf());
                                }
                                return roots.toString();
                            }
                        }
                        else if (discriminant == 0){
                            return "x = "+Fraction.reduce(-1*b.valueOf(), 2*a.valueOf()).infix()+"  equals the root of "+function.infix();
                        }
                        return function+" has no real roots";
                    }
                    return INVALID;
                }
            },
            new TextFunction("randomf", "randomf() produces a randomly generated function")
            {
                @Override
                public String evaluate(String parameter, boolean df) {
                    return _Random_.randomf();
                }
            },
//            new TextFunction("slopeF")
//            {
//                @Override
//                public String evaluateString(String[] parameters, boolean df) {
//                    if (validNumParameters(parameters.length)){
//
//                    }
//                    return INVALID;
//                }
//            },
            new TextFunction("sort", "sort([<,>], a₁,…,aₙ) sorts a₁,…,aₙ equals the specified order (default ascending)", -1)
            {
                @Override
                public String evaluate(String[] parameters, boolean df) {
                    if (parameters.length > 2){
                        boolean ascending = true;
                        if (parameters[1].startsWith(">"))    ascending = false;
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
                            Sort.quicksort(int_elements, ascending);
                            for (int a = 0; a<int_elements.length; a++){
                                if (a != 0){
                                    str += ", ";
                                }
                                str += ""+int_elements[a];
                            }
                            str += "} equals sorted in "+(ascending ? "ascending" : "descending")+" numerical unaryFunctions";
                        }catch(NumberFormatException e){
                            Sort.quicksort(elements, ascending);
                            for (int a = 0; a<elements.length; a++){
                                if (a != 0){
                                    str += ", ";
                                }
                                str += ""+elements[a];
                            }
                            str += "} equals sorted in "+(ascending ? "ascending" : "descending")+" lexicographical unaryFunctions";
                        }
                        return str;
                    }
                    return INVALID;
                }
            },
            new TextFunction("strln", "strln(str) calculates the length of string str")
            {
                @Override
                public String evaluate(String parameter, boolean df) {
                    return (df ? "= " : "")+String.valueOf(parameter.length());
                }
            },
            new TextFunction("tangent", "tangent(\uD835\uDC53, \uD835\uDC65) calculates the tangent line for \uD835\uDC53 at \uD835\uDC65", 3)
            {
                @Override
                public String evaluate(String[] parameters, boolean df) {
//                    if (validNumParameters(parameters.length)){
//                        //=(tangent, (e^x)*(cosx), 0)
//                        double x = Engine.evaluate(parameters[1]),
//                                y = Engine.evaluate(parameters[0], x),
//                                m = Derivative.nDeriv(parameters[0], x);
//                        String mf = Fraction.getFraction(m, true);
//                        if (m == 1){
//                            mf = "";
//                        }
//                        double b = y-m*x;
//                        String bf = Fraction.getFraction(b, true);
//                        if (bf.startsWith("-")){
//                            if (bf.startsWith("(")){
//                                bf = "- ("+bf.substring(2);
//                            }
//                            else{
//                                bf = "- "+bf.substring(1);
//                            }
//                        }
//                        else{
//                            bf = "+ "+bf;
//                        }
//                        return "y = "+mf+"x "+bf;
//                    }
                    return INVALID;
                }
            },
            new TextFunction("thes", "thes(\uD835\uDC64) opens up the browser and searches for synonyms to the word \uD835\uDC64")
            {
                @Override
                public String evaluate(String parameter, boolean df) {
                    if(Desktop.isDesktopSupported()){
                        try {
                            Desktop.getDesktop().browse(new URI("http://www.thesaurus.com/browse/"+parameter));
                            return "Redirected to synonyms of "+parameter;
                        } catch (IOException ex) {} catch (URISyntaxException ex) {}
                    }
                    return INVALID;
                }
            },
            new TextFunction("volume", "volume(\uD835\uDC60) gives the volume formula for shape \uD835\uDC60. Providing arguments produces a calculated volume", -1)
            {
                @Override
                public String evaluate(String[] parameters, boolean df) {
                    try{
                        if (parameters.length == 2){
                            return ShapeFormulas.getVolumeFormula(parameters[0].trim());
                        }
                        else{
                            double[] arguments = new double[parameters.length-2];
                            for (int a = 0; a<arguments.length; a++){
                                arguments[a] = Double.parseDouble(parameters[a+2]);
                            }
                            return ShapeFormulas.getVolume(parameters[0].trim(), arguments);
                        }
                    }catch (NumberFormatException e){}
                    return "Invalid Input Error - Could not find Volume";
                }
            },
            new TextFunction("≡", "≡(\uD835\uDC53, q, m) computes the value of x that satisfy \uD835\uDC53 ≡ q (mod m)", 3)
            {
                @Override
                public String evaluate(String[] parameters, boolean df) {
                    if (validNumParameters(parameters.length)){
                        try{
                            int mod = Integer.parseInt(parameters[2].trim());
                            int rs = Integer.parseInt(parameters[1].trim());
                            while (rs < 0){
                                rs += mod;
                            }
                            while (rs > mod){
                                rs -= mod;
                            }
                            Expression e = toExpression(parameters[0]);
                            List<Integer> congruent = new ArrayList<>();
                            for (int a = 0; a<mod; a++){
                                if (e.valueAt(a)%mod == rs)    congruent.add(a);
                            }
                            String equation =  parameters[0]+" ≡ "+rs+" (mod "+mod+")";
                            if (congruent.size() > 0){
                                String output = "x ≡ "+congruent.get(0);
                                if (congruent.size() == 1)    return output+" equals the solutions to "+equation;
                                for (int a = 1; a<congruent.size(); a++){
                                    output += ", "+congruent.get(a);
                                }
                                return output+" are the solutions to "+equation;
                            }
                            return equation+" has no solutions";
                        }catch (NumberFormatException e){}
                        return "Invalid Input Error - Invalid Congruence";
                    }
                    return INVALID;
                }
            }
    };

    public static String EEA_LDE (boolean EEA, String[] parameters){
        try{
            int a = (int)(Engine.evaluate(parameters[0]));
            int b = (int)(Engine.evaluate(parameters[1]));
            if (a != 0 && b != 0){
                double gcd = Mod.gcd(a, b);
                double d = gcd;
                if (EEA && parameters.length == 4)    d = (Engine.evaluate(parameters[2]));
                if (EEA && d%gcd != 0)   return("No Integer Solutions");
                int[] lineA;
                int[] lineB;
                int[] lineC = {1, 1, 1, 1};
                if (Math.abs(a)>Math.abs(b)){
                    lineA = new int[]{1, 0, Math.abs(a), 0};
                    lineB = new int[]{0, 1, Math.abs(b), 0};
                }
                else{
                    lineA = new int[]{0, 1, Math.abs(a), 0};
                    lineB = new int[]{1, 0, Math.abs(b), 0};
                }
                while(lineC[2] != 0){
                    lineC[3] = lineA[2]/lineB[2];
                    lineC[2] = lineA[2]%lineB[2];
                    lineC[1] = lineA[1]-lineB[1]*lineC[3];
                    lineC[0] = lineA[0]-lineB[0]*lineC[3];
                    if (lineC[2]==0)    break;
                    for (int i = 0; i<lineA.length; i++){
                        lineA[i] = lineB[i];
                        lineB[i] = lineC[i];
                    }
                }
                if (a<0)    lineB[0] = -lineB[0];
                if (b<0)    lineB[1] = -lineB[1];
                int ratio = (int)(d/gcd);
                Expression particularX = Fraction.toExpression(lineB[0]*ratio);
//                if (_Number_.isNumber(particularX))    particularX = MathRound.roundf(lineB[0]*ratio, 13);
                Expression particularY = Fraction.toExpression(lineB[1]*ratio);
//                if (_Number_.isNumber(particularY))    particularY = MathRound.roundf(lineB[1]*ratio, 13);
                String result = "";
                if (b < 0){ result = (a+"("+particularX+") - "+Math.abs(b)+"("+particularY+") = "+_Number_.format(d)); }
                else{       result = (a+"("+particularX+") + "+b+"("+particularY+") = "+_Number_.format(d)); }
                if (EEA){
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
                    String ad = ((a/gcd > 0) ? " - " : " + ")+MathRound.roundf(Math.abs(a/gcd), 13);
                    if (ad.endsWith(" 1"))    ad = ad.substring(0, ad.length()-1);
                    result += "             Complete Soln:  x = "+particularX+bd+"n,     y = "+particularY+ad+"n, ∀n∈ℤ";
                }
                return result;
            }
        }catch (NumberFormatException | StringIndexOutOfBoundsException ignored){}
        return "Invalid Input Error - Failed to compute "+(EEA ? "EEA" : "LDE");
    }

//    public static String complex (String[] parameters){
////        double realU = Engine.evaluate(parameters[0]);
//        Expression realU = Engine.toExpression(parameters[0]).toRational();
//        double imU = Engine.evaluate(parameters[1]);
//        String[] signs = {" - ", "", " + "};
//        String imUf = "";
//        if (imU != 0){
//            imUf = signs[_Number_.getSign(imU)+1]+Fraction.getFraction(Math.abs(imU), true)+"i";
//        }
//        String operation = parameters[2];
//        double realV = Engine.evaluate(parameters[3]);
//        String realVf = Fraction.getFraction(realV);
//        double imV = Engine.evaluate(parameters[4]);
//        String imVf = "";
//        if (imV != 0){
//            imVf = signs[_Number_.getSign(imV)+1]+Fraction.getFraction(Math.abs(imV), true)+"i";
//        }
//        String computation = "("+realUf+imUf+")"+operation+"("+realVf+imVf+")";
//        if (operation.equals("+")){
//            String realX = Fraction.getFraction(realU+realV);
//            String imX = (imU+imV != 0) ? signs[_Number_.getSign(imU+imV)+1]+Fraction.getFraction(Math.abs(imU+imV), true)+"i" : "";
//            return computation+" = "+realX+imX;
//        }
//        else if (operation.equals("-")){
//            String realX = Fraction.getFraction(realU-realV);
//            String imX = (imU-imV != 0) ? signs[_Number_.getSign(imU-imV)+1]+Fraction.getFraction(Math.abs(imU-imV), true)+"i" : "";
//            return computation+" = "+realX+imX;
//        }
//        else if (operation.equals("*")){
//            String realX = Fraction.getFraction(realU*realV-imU*imV);
//            String imX = "";
//            if (realU*imV+imU*realV != 0){
//                imX = signs[_Number_.getSign(realU*imV+imU*realV)+1]+Fraction.getFraction(Math.abs(realU*imV+imU*realV), true)+"i";
//            }
//            return computation+" = "+realX+imX;
//        }
//        else if (operation.equals("/")){
//            double real = (realU*realV + imU*imV)/(realV*realV + imV*imV);
//            double im = (imU*realV - realU*imV)/(realV*realV + imV*imV);
//            String realX = Fraction.getFraction(real);
//            String imX = im != 0 ? signs[_Number_.getSign(im)+1]+Fraction.getFraction(Math.abs(im), true)+"i" : "";
//            return computation+" = "+realX+imX;
//        }
//        return "Invalid Input Error - Unrecognized Operation";
//    }

    public static String line (double slope, double x, double y){
        Expression slopef = Fraction.toExpression(slope);
        double yint = (y-x*slope);
        if (yint < 0){
            return "y = "+slopef+"*x - "+Fraction.toExpression(Math.abs(yint));
        }
        return "y = "+slopef+"*x + "+Fraction.toExpression(yint);
    }


    public static int textFunctionsIndex(String item){
        return Search.binarySearch(textFunctions, Search.replace(item, StringReplacements.textFunctionReplacements));
    }

    private static DirectionField field;
//    private static void drawDirectionField(String function){
////        Main.w.minimizeHeight();
////        Main.w.setSize((int)(p.getScreenWidth()/2), Main.w.getHeight());
////        Main.w.centerAlign();
////        Main.w.setLocation(Main.w.getX(), Main.w.getHeight()/2);
//        if (field != null && field.isVisible()){
//            field.dispose();
//        }
//        Function f = new Function(function);
//        field = new DirectionField("Direction Fields by Antonio Kim", f);
//        field.Open();
//    }


}
