/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Math_Evaluation_Library.Miscellaneous;

import Math_Evaluation_Library.Expressions.*;
import Math_Evaluation_Library.Expressions.NumberExpressions.ConstantExpression;
import Math_Evaluation_Library.Expressions.NumberExpressions.NumberExpression;
import Math_Evaluation_Library.Objects._Number_;

/**

 @author Antonio
 */
public class Fraction{

//    private int numerator = 0, denominator = 1;
//    private double value = 1;
//
//    public Fraction(){
//    }
//    public Fraction(int num){
//        numerator = num;
//    }
//    public Fraction(int numerator, int denominator){
//        this.numerator = numerator;
//        this.denominator = denominator;
//        this.value = (double)numerator/denominator;
//    }
//    public Fraction(double numerator, double denominator){
//        if (numerator%1 == 0){
//            this.numerator = (int)numerator;
//        }
//        else{
//            toFraction(getFraction(numerator));
//        }
//        if (denominator%1 == 0){
//            this.denominator = (int)denominator;
//        }
//        else{
//            multiply(new Fraction(denominator).getReciprocal());
//        }
//    }
//    public Fraction(double num){
//        if (!toFraction(getFraction(num))){
//            value = num;
//            numerator = 0;
//            denominator = 0;
//        }
//    }
//    public Fraction (String frac){
//        value = Engine.evaluate(frac);
//        if (!toFraction(frac)){
//            frac = getFraction(value);
//            if (!toFraction(frac)){
//                numerator = 0;
//                denominator = 0;
//            }
//        }
//    }
//    public Fraction (Fraction fraction){
//        if (fraction.getDenominator() != 0){
//            numerator = fraction.getNumerator();
//            denominator = fraction.getDenominator();
//        }
//        else{
//            denominator = 0;
//        }
//        value = fraction.getValue();
//    }
//
//    private boolean toFraction(double[] frac){
//        if (frac)
//        try{
//            numerator = Integer.parseInt(frac);
//            denominator = 1;
//            value = numerator;
//            return true;
//        }catch(NumberFormatException e1){
//            int index = frac.indexOf("/");
//            if (index != -1){
//                try{
//                    numerator = Integer.parseInt(frac.substring(0, index));
//                    denominator = Integer.parseInt(frac.substring(index+1));
//                    if (denominator != 0){
//                        value = (double)numerator/denominator;
//                    }
//                    return true;
//                }catch(NumberFormatException e2){}
//            }
//        }
//        return false;
//    }
//
//    public int getNumerator(){
//        return numerator;
//    }
//    public int getDenominator(){
//        return denominator;
//    }
//    public double getValue(){
//        if (isFraction()){
//            return (double)numerator/denominator;
//        }
//        return value;
//    }
//    public Fraction getCopy(){
//        return new Fraction(this);
//    }
//    public Fraction getReciprocal(){
//        if (numerator != 0 && denominator != 0){
//            return new Fraction(denominator, numerator);
//        }
//        return new Fraction(1.0/value);
//    }
//    public boolean isFraction(){
//        return denominator != 0;
//    }
//    public boolean isInteger(){
//        return denominator == 1;
//    }
//
//    public void reduce(){
//        if (isFraction() && !(numerator == 0 || denominator == 0)){
//            if ((numerator < 0 && denominator < 0) || (numerator > 0 && denominator < 0)){
//                numerator *= -1;
//                denominator *= -1;
//            }
//            int gcd = Mod.gcd(numerator, denominator);
//            numerator /= gcd;
//            denominator /= gcd;
//        }
//    }
//
//    public Fraction add(Fraction fraction){
//        //System.out.println(numerator+"*"+fraction.getDenominator()+" + "+fraction.getNumerator()+"*"+denominator);
//        if (isFraction() && fraction.isFraction()){
//            numerator = numerator*fraction.getDenominator() + fraction.getNumerator()*denominator;
//            denominator *= fraction.getDenominator();
//            reduce();
//        }
//        else{
//            denominator = 0;
//        }
//        value += fraction.getValue();
//        return this;
//    }
//    public Fraction subtract(Fraction fraction){
//        if (isFraction() && fraction.isFraction()){
//            numerator = numerator*fraction.getDenominator() - fraction.getNumerator()*denominator;
//            denominator *= fraction.getDenominator();
//            reduce();
//        }
//        else{
//            denominator = 0;
//        }
//        value -= fraction.getValue();
//        return this;
//    }
//    public Fraction multiply(Fraction fraction){
//        if (isFraction() && fraction.isFraction()){
//            numerator *= fraction.getNumerator();
//            denominator *= fraction.getDenominator();
//            if (denominator != 1){
//                reduce();
//            }
//        }
//        else{
//            denominator = 0;
//        }
//        value *= fraction.getValue();
//        return this;
//    }
//    public Fraction divide(Fraction fraction){
//        if (fraction.getValue() != 0){
//            if (isFraction() && fraction.isFraction()){
//                numerator *= fraction.getDenominator();
//                denominator *= fraction.getNumerator();
//                if (denominator != 1){
//                    reduce();
//                }
//            }
//            else{
//                denominator = 0;
//            }
//            value /= fraction.getValue();
//        }
//        return this;
//    }
//    public Fraction expt(Fraction fraction){
//        if (isFraction() && fraction.isFraction()){
//            if (fraction.getDenominator() == 1){
//                return expt(fraction.getNumerator());
//            }
//            else{
//                double numerator = Math.pow(this.numerator, fraction.getValue());
//                double denominator = Math.pow(this.denominator, fraction.getValue());
//                if (numerator%1 == 0 && denominator%1 == 0){
//                    this.numerator = (int)numerator;
//                    this.denominator = (int)denominator;
//                }
//                else{
//                    this.denominator = 0;
//                }
//            }
//        }
//        value = Math.pow(value, fraction.getValue());
//        return this;
//    }
//    public Fraction sqrt(){
//        return expt(new Fraction(1, 2));
//    }
//
//    public Fraction add(int num){
//        if (isFraction()){
//            numerator = numerator + num*denominator;
//            reduce();
//        }
//        else{
//            denominator = 0;
//        }
//        value += num;
//        return this;
//    }
//    public Fraction subtract(int num){
//        if (isFraction()){
//            numerator = numerator - num*denominator;
//            reduce();
//        }
//        else{
//            denominator = 0;
//        }
//        value -= num;
//        return this;
//    }
//    public Fraction multiply(int num){
//        if (isFraction()){
//            numerator *= num;
//            reduce();
//        }
//        else{
//            denominator = 0;
//        }
//        value *= num;
//        return this;
//    }
//    public Fraction divide(int num){
//        if (num != 0){
//            if (isFraction()){
//                denominator *= num;
//                reduce();
//            }
//            else{
//                denominator = 0;
//            }
//            value /= num;
//        }
//        return this;
//    }
//    public Fraction expt(int num){
//        if (isFraction()){
//            numerator = (int)Math.pow(numerator, num);
//            denominator = (int)Math.pow(denominator, num);
//        }
//        else{
//            denominator = 0;
//        }
//        value = Math.pow(value, num);
//        return this;
//    }
//
//    public Fraction add(double num){
//        if (num%1 == 0){
//            add((int)num);
//        }
//        else{
//            Fraction f = new Fraction(num);
//            add(f);
//        }
//        return this;
//    }
//    public Fraction subtract(double num){
//        if (num%1 == 0){
//            subtract((int)num);
//        }
//        else{
//            Fraction f = new Fraction(num);
//            subtract(f);
//        }
//        return this;
//    }
//    public Fraction multiply(double num){
//        if (num%1 == 0){
//            multiply((int)num);
//        }
//        else{
//            Fraction f = new Fraction(num);
//            multiply(f);
//        }
//        return this;
//    }
//    public Fraction divide(double num){
//        if (num%1 == 0){
//            divide((int)num);
//        }
//        else{
//            Fraction f = new Fraction(num);
//            divide(f);
//        }
//        return this;
//    }
//
//    public String piMultiple(){
//        return piMultiple(this);
//    }
//    public String piMultiple(Fraction fraction){
//        if (isFraction()){
//            fraction.reduce();
//            if (fraction.getDenominator() == 1){
//                return fraction.getNumerator()+"π";
//            }
//            return fraction.getNumerator()+"π/"+fraction.getDenominator();
//        }
//        return fraction.getValue()+"π";
//    }
//
//    public boolean equals(Fraction fraction){
//        if (getValue() == fraction.getValue()){
//            return true;
//        }
//        return false;
//    }
//    public boolean equals(double value){
//        if (getValue() == value){
//            return true;
//        }
//        return false;
//    }
//
//    public String getString(){
//        if (denominator != 0){
//            reduce();
//            if (denominator == 1){
//                return numerator+"";
//            }
//            if (numerator == 0){
//                return "0";
//            }
//            return numerator+"/"+denominator;
//        }
//        if ((value+"").endsWith(".0")){
//            return (int)value+"";
//        }
//        return value+"";
//    }
//    public void print(){
//        if (denominator != 1){
//            System.out.print(numerator+"/"+denominator);
//        }
//        else{
//            System.out.print(numerator);
//        }
//    }
//    public void println(){
//        print();
//        System.out.println();
//    }

    public static long[] reduce(long[] fraction){
        if (fraction.length == 2){
            long gcd = Mod.gcd(fraction[0], fraction[1]);
            if (gcd != 1){
                fraction[0] /= gcd;
                fraction[1] /= gcd;
            }
        }
        return fraction;
    }

    private static final double error = 1e-13;
    private static final double crError = 1e-12;
    private static final int fractionLength = 12;
    public static Expression toExpression(double num){
        num = MathRound.round(num, 13);
        if (num%1 == 0) return new NumberExpression(num);

        double squared = num*num;
        if (_Number_.isInteger(squared, error)){
            return new NumberExpression((long)squared).sqrt();
        }
        double cubed = squared*num;
        if (_Number_.isInteger(cubed, error)){
            return new NumberExpression((long)cubed).cbrt();
        }

        long[] fraction = getFraction(num);
        if (fraction != null){
//            System.out.println("Found "+fraction[0]+"/"+fraction[1]+"  in "+fraction[3]+" iterations");
            if (String.valueOf(fraction[0]).length()+String.valueOf(fraction[1]).length() <= fractionLength){
                fraction = reduce(fraction);
                return new NumberExpression(fraction[0]).divide(fraction[1]).negate(fraction[2] < 0);
            }
        }
        boolean neg = num < 0;
        fraction = getFraction(squared);
        if (fraction != null){
//            System.out.println(fraction[0]+"/"+fraction[1]);
            fraction = reduce(fraction);
            long sqrt1 = _Number_.isPerfectSquare(fraction[1]);
            long sqrt0 = _Number_.isPerfectSquare(fraction[0]);
            if (sqrt0 != -1 && sqrt1 != -1){
                if (String.valueOf(sqrt0).length()+String.valueOf(sqrt1).length() <= fractionLength){
                    return new NumberExpression(sqrt0).divide(sqrt1).sqrt().negate(neg);
                }
            }
            else if (sqrt0 != -1){
//                    System.out.println("Found √"+fraction[0]+"/"+fraction[1]+"  in "+fraction[3]+" iterations");
                if (String.valueOf(sqrt0).length()+String.valueOf(fraction[1]).length() <= fractionLength){
                    return new NumberExpression(fraction[0]).divide(new OperatorExpression("√", sqrt1)).negate(neg);
                }
            }
            else if (sqrt1 != -1){
//                    System.out.println("Found √"+fraction[0]+"/"+fraction[1]+"  in "+fraction[3]+" iterations");
                if (String.valueOf(fraction[0]).length()+String.valueOf(sqrt1).length() <= fractionLength){
                    return  new NumberExpression(fraction[0]).sqrt().divide(sqrt1).negate(neg);
                }
            }
            else{
                if (String.valueOf(fraction[0]).length()+String.valueOf(fraction[1]).length() <= fractionLength){
                    return new NumberExpression(fraction[0]).divide(fraction[1]).sqrt().negate(neg);
                }
            }
        }

        ConstantExpression[] constants = {
                new ConstantExpression('π', Math.PI),
                new ConstantExpression('e', Math.E)
        };

        for (ConstantExpression c : constants){
            double constant = c.valueOf();
            if (Math.abs(num-constant) <= crError) return c;

            double val = num/constant;
            if (_Number_.isInteger(val, crError))   return new NumberExpression((long)val).multiply(c);
            val = constant/num;
            if (_Number_.isInteger(val, crError))   return c.divide(Math.round(val));
            val = constant*num;
            if (_Number_.isInteger(val, crError))   return new NumberExpression(Math.round(val)).divide(c);
            val = num-constant;
            if (_Number_.isInteger(val, crError))   return new NumberExpression(Math.round(val)).add(c);
            val = num+constant;
            if (_Number_.isInteger(val, crError))   return new NumberExpression(Math.round(val)).subtract(c);
        }

//        if (Math.asin(num)%1 == 0)  return new UnaryExpression("sin", new NumberExpression(Math.asin(num)));
//        if (Math.acos(num)%1 == 0)  return new UnaryExpression("cos", new NumberExpression(Math.acos(num)));
//        if (Math.atan(num)%1 == 0)  return new UnaryExpression("tan", new NumberExpression(Math.atan(num)));
//        if (Trig.acsc(num)%1 == 0)  return new UnaryExpression("csc", new NumberExpression(Trig.acsc(num)));
//        if (Trig.asec(num)%1 == 0)  return new UnaryExpression("sec", new NumberExpression(Trig.asec(num)));
//        if (Trig.acot(num)%1 == 0)  return new UnaryExpression("cot", new NumberExpression(Trig.acot(num)));

        double n = Math.exp(num);
        if (Math.abs(n) <= Long.MAX_VALUE && n%1 == 0)   return new UnaryExpression("ln", new NumberExpression(n));
        n = Math.pow(10, num);
        if (Math.abs(n) <= Long.MAX_VALUE && n%1 == 0)   return new UnaryExpression("log", new NumberExpression(n));

        if (Math.exp(1/num)%1 == 0)   return new OperatorExpression("/", new NumberExpression(1), new UnaryExpression("ln", new NumberExpression(Math.exp(1/num))));
        if (Math.pow(10, 1/num)%1 == 0)   return new OperatorExpression("/", new NumberExpression(1), new UnaryExpression("log", new NumberExpression(Math.pow(10, 1/num))));

        return new NumberExpression(num);
    }
    private static long[] getFraction(double x0){
        double sign = x0 < 0 ? -1 : 1;
        double g = Math.abs(x0);
        long a = 0, b = 1, c = 1, d = 0;
        long s;
        int iter = 0;
        long numerator = 0, denominator = 1;
        while (iter++ < 1e6){
            s = (long)Math.floor(g);
            numerator = a + s*c;
            denominator = b + s*d;
            a = c;
            b = d;
            c = numerator;
            d = denominator;
            g = 1.0/(g-s);
            if(error>Math.abs(sign*numerator/denominator-x0)){
                return new long[]{numerator, denominator, (long)sign, iter};
            }
        }
        return null;
    }

    public static Expression reduce(double numerator, double denominator){
        if (numerator%1 == 0 && denominator%1 == 0){
            double gcd = Mod.gcd(numerator, denominator);
            return new OperatorExpression("/", new NumberExpression(numerator/gcd), new NumberExpression(denominator/gcd));
        }
        return toExpression(numerator/denominator);
//        double[] fraction = getFraction(numerator/denominator);
//        if (fraction != null && String.valueOf(fraction[0]).length()+String.valueOf(fraction[1]).length() < 15){
////            System.out.println("Found "+fraction[0]+"/"+fraction[1]+"  in "+fraction[3]+" iterations");
//            if (fraction[2] < 0)
//                return new UnaryExpression("neg", new NumberExpression(fraction[0]).divide(fraction[1]));
//            else
//                return new NumberExpression(fraction[0]).divide(fraction[1]);
//        }
//        return new NumberExpression(numerator).divide(denominator);
    }

}
