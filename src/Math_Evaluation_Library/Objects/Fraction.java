/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Math_Evaluation_Library.Objects;

import Math_Evaluation_Library.Engine.Engine;
import Math_Evaluation_Library.Miscellaneous.MathRound;
import Math_Evaluation_Library.Miscellaneous.Mod;
import Math_Evaluation_Library.Trigonometry.Trig;

import java.util.ArrayList;
import java.util.List;

/**

 @author Antonio
 */
public class Fraction{

    private int numerator = 0, denominator = 1;
    private double value = 1;

    public Fraction(){
    }
    public Fraction(int num){
        numerator = num;
    }
    public Fraction(int numerator, int denominator){
        this.numerator = numerator;
        this.denominator = denominator;
        this.value = (double)numerator/denominator;
    }
    public Fraction(double numerator, double denominator){
        if (numerator%1 == 0){
            this.numerator = (int)numerator;
        }
        else{
            convertString(getFraction(numerator));
        }
        if (denominator%1 == 0){
            this.denominator = (int)denominator;
        }
        else{
            multiply(new Fraction(denominator).getReciprocal());
        }
    }
    public Fraction(double num){
        String frac = getFraction(num);
        if (!convertString(frac)){
            value = num;
            numerator = 0;
            denominator = 0;
        }
    }
    public Fraction (String frac){
        if (!convertString(frac)){
            value = Engine.evaluate(frac);
            frac = getFraction(value);
            if (!convertString(frac)){
                numerator = 0;
                denominator = 0;
            }
        }
    }
    public Fraction (Fraction fraction){
        if (fraction.getDenominator() != 0){
            numerator = fraction.getNumerator();
            denominator = fraction.getDenominator();
        }
        else{
            denominator = 0;
        }
        value = fraction.getValue();
    }

    private boolean convertString(String frac){
        try{
            numerator = Integer.parseInt(frac);
            denominator = 1;
            value = numerator;
            return true;
        }catch(NumberFormatException e1){
            int index = frac.indexOf("/");
            if (index != -1){
                try{
                    numerator = Integer.parseInt(frac.substring(0, index));
                    denominator = Integer.parseInt(frac.substring(index+1));
                    if (denominator != 0){
                        value = (double)numerator/denominator;
                    }
                    return true;
                }catch(NumberFormatException e2){}
            }
        }
        return false;
    }

    public int getNumerator(){
        return numerator;
    }
    public int getDenominator(){
        return denominator;
    }
    public double getValue(){
        return value;
    }
    public Fraction getCopy(){
        return new Fraction(this);
    }
    public Fraction getReciprocal(){
        if (numerator != 0 && denominator != 0){
            return new Fraction(denominator, numerator);
        }
        return new Fraction(1.0/value);
    }
    public boolean isFraction(){
        return denominator != 0;
    }

    public void reduce(){
        if (isFraction() && !(numerator == 0 || denominator == 0)){
            if ((numerator < 0 && denominator < 0) || (numerator > 0 && denominator < 0)){
                numerator *= -1;
                denominator *= -1;
            }
            int gcd = Mod.gcd(numerator, denominator);
            numerator /= gcd;
            denominator /= gcd;
        }
    }

    public Fraction add(Fraction fraction){
        //System.out.println(numerator+"*"+fraction.getDenominator()+" + "+fraction.getNumerator()+"*"+denominator);
        if (isFraction() && fraction.isFraction()){
            numerator = numerator*fraction.getDenominator() + fraction.getNumerator()*denominator;
            denominator *= fraction.getDenominator();
            reduce();
        }
        else{
            denominator = 0;
        }
        value += fraction.getValue();
        return this;
    }
    public Fraction subtract(Fraction fraction){
        if (isFraction() && fraction.isFraction()){
            numerator = numerator*fraction.getDenominator() - fraction.getNumerator()*denominator;
            denominator *= fraction.getDenominator();
            reduce();
        }
        else{
            denominator = 0;
        }
        value -= fraction.getValue();
        return this;
    }
    public Fraction multiply(Fraction fraction){
        if (isFraction() && fraction.isFraction()){
            numerator *= fraction.getNumerator();
            denominator *= fraction.getDenominator();
            if (denominator != 1){
                reduce();
            }
        }
        else{
            denominator = 0;
        }
        value *= fraction.getValue();
        return this;
    }
    public Fraction divide(Fraction fraction){
        if (fraction.getValue() != 0){
            if (isFraction() && fraction.isFraction()){
                numerator *= fraction.getDenominator();
                denominator *= fraction.getNumerator();
                if (denominator != 1){
                    reduce();
                }
            }
            else{
                denominator = 0;
            }
            value /= fraction.getValue();
        }
        return this;
    }
    public Fraction expt(Fraction fraction){
        if (isFraction() && fraction.isFraction()){
            if (fraction.getDenominator() == 1){
                return expt(fraction.getNumerator());
            }
            else{
                double numerator = Math.pow(this.numerator, fraction.getValue());
                double denominator = Math.pow(this.denominator, fraction.getValue());
                if (numerator%1 == 0 && denominator%1 == 0){
                    this.numerator = (int)numerator;
                    this.denominator = (int)denominator;
                }
                else{
                    this.denominator = 0;
                }
            }
        }
        value = Math.pow(value, fraction.getValue());
        return this;
    }
    public Fraction sqrt(){
        return expt(new Fraction(1, 2));
    }

    public Fraction add(int num){
        if (isFraction()){
            numerator = numerator + num*denominator;
            reduce();
        }
        else{
            denominator = 0;
        }
        value += num;
        return this;
    }
    public Fraction subtract(int num){
        if (isFraction()){
            numerator = numerator - num*denominator;
            reduce();
        }
        else{
            denominator = 0;
        }
        value -= num;
        return this;
    }
    public Fraction multiply(int num){
        if (isFraction()){
            numerator *= num;
            reduce();
        }
        else{
            denominator = 0;
        }
        value *= num;
        return this;
    }
    public Fraction divide(int num){
        if (num != 0){
            if (isFraction()){
                denominator *= num;
                reduce();
            }
            else{
                denominator = 0;
            }
            value /= num;
        }
        return this;
    }
    public Fraction expt(int num){
        if (isFraction()){
            numerator = (int)Math.pow(numerator, num);
            denominator = (int)Math.pow(denominator, num);
        }
        else{
            denominator = 0;
        }
        value = Math.pow(value, num);
        return this;
    }

    public Fraction add(double num){
        if (num%1 == 0){
            add((int)num);
        }
        else{
            Fraction f = new Fraction(num);
            add(f);
        }
        return this;
    }
    public Fraction subtract(double num){
        if (num%1 == 0){
            subtract((int)num);
        }
        else{
            Fraction f = new Fraction(num);
            subtract(f);
        }
        return this;
    }
    public Fraction multiply(double num){
        if (num%1 == 0){
            multiply((int)num);
        }
        else{
            Fraction f = new Fraction(num);
            multiply(f);
        }
        return this;
    }
    public Fraction divide(double num){
        if (num%1 == 0){
            divide((int)num);
        }
        else{
            Fraction f = new Fraction(num);
            divide(f);
        }
        return this;
    }

    public boolean equals(Fraction fraction){
        if (getValue() == fraction.getValue()){
            return true;
        }
        return false;
    }
    public boolean equals(double value){
        if (getValue() == value){
            return true;
        }
        return false;
    }

    public String getString(){
        if (denominator != 0){
            reduce();
            if (denominator == 1){
                return numerator+"";
            }
            if (numerator == 0){
                return "0";
            }
            return numerator+"/"+denominator;
        }
        if ((value+"").endsWith(".0")){
            return (int)value+"";
        }
        return value+"";
    }
    public void print(){
        if (denominator != 1){
            System.out.print(numerator+"/"+denominator);
        }
        else{
            System.out.print(numerator);
        }
    }
    public void println(){
        print();
        System.out.println();
    }

    public static String getFraction(double num){
        String fraction = calculateFraction(num, false, false);
        if (fraction.endsWith(".0")){
            return fraction.substring(0, fraction.length()-2);
        }
        return fraction;
    }
    public static String getFraction(double num, boolean brackets){
        String fraction = calculateFraction(num, brackets, false);
        if (fraction.endsWith(".0")){
            return fraction.substring(0, fraction.length()-2);
        }
        return fraction;
    }
    static String exp = "";
    static final int time = 50;
    public static String calculateFraction(double num, boolean brackets, boolean checkOther){
        return calculateFraction(num, brackets, checkOther, time);
    }
    public static String calculateFraction(double num, boolean brackets, boolean checkOther, int time){
        if (num%1 == 0 || num == 0){
            return _Number_.format(num);
        }
        if (num == Math.PI){        return String.valueOf(num);                            }
        if (num == Math.E){         return String.valueOf(num);                            }
        if (num%Math.PI == 0){      return _Number_.format(num/Math.PI+"")+"π";     }
        if ((Math.PI/num)%1 == 0){  return ("π/"+_Number_.format(Math.PI/num));       }
        if (num%Math.E == 0){       return _Number_.format(num/Math.E+"")+"e";      }
        if ((Math.E/num)%1 == 0){   return "e"+_Number_.format(Math.E/num);           }
        long start = System.currentTimeMillis();
        exp = "";
        List<Thread> threads = new ArrayList<>();
        threads.add(new Thread(new Runnable() {
            @Override
            public void run() {
                int accuracy = 16;
                double a = 1;
                double number = MathRound.round(num, accuracy);
                while((System.currentTimeMillis()-start <= time) && exp.equals("")){
                    a++;
                    double num = MathRound.round(number*a, accuracy);
                    if (num%1 == 0){
                        double gcd = Mod.gcd(num, a);
                        a /= gcd;
                        if (a == 1){
                            exp = _Number_.format(num/gcd);
                        }
                        else if (a == -1){
                            exp = _Number_.format(-num/gcd);
                        }
                        else{
                            exp = _Number_.format(num/gcd)+"/"+_Number_.format(a);
                        }
                        break;
                    }
                }
            }
        }));
        threads.add(new Thread(new Runnable() {
            @Override
            public void run() {
                int accuracy = 14;
                double a = 1;
                double number = MathRound.round(num, accuracy);
                double limit = 1E10;
                while((System.currentTimeMillis()-start <= time) && exp.equals("") && a<limit){
                    a *= 10;
                    double num = MathRound.round(number*a, accuracy);
                    if (num%1 == 0){
                        double gcd = Mod.gcd(num, a);
                        a /= gcd;
                        if (a == 1){
                            exp = _Number_.format(num/gcd);
                        }
                        else if (a == -1){
                            exp = _Number_.format(-num/gcd);
                        }
                        else{
                            exp = _Number_.format(num/gcd)+"/"+_Number_.format(a);
                        }
                        break;
                    }
                }
            }
        }));
        if (checkOther){
            threads.add(new Thread(new Runnable() {
                @Override
                public void run() {
                    int accuracy = 16;
                    double number = MathRound.round(num, accuracy);
                    OUTER:  for (int a = 1; (System.currentTimeMillis()-start <= time) && exp.equals(""); a++){
                        if (number == MathRound.round(Math.PI-a, accuracy)){            exp = "π-"+a;    break OUTER;   }
                        if (number == MathRound.round(Math.PI+a, accuracy)){            exp = "π+"+a;    break OUTER;   }
                        if (number == MathRound.round(Math.E-a, accuracy)){             exp = "e-"+a;    break OUTER;   }
                        if (number == MathRound.round(Math.E+a, accuracy)){             exp = "e+"+a;    break OUTER;   }
                        if (number == MathRound.round(Math.sin(a), accuracy)){          exp = "sin"+a;   break OUTER;   }
                        if (number == MathRound.round(Math.cos(a), accuracy)){          exp = "cos"+a;   break OUTER;   }
                        if (number == MathRound.round(Math.tan(a), accuracy)){          exp = "tan"+a;   break OUTER;   }
                        if (number == MathRound.round(Trig.sec(a), accuracy)){          exp = "sec"+a;   break OUTER;   }
                        if (number == MathRound.round(Trig.csc(a), accuracy)){          exp = "csc"+a;   break OUTER;   }
                        if (number == MathRound.round(Trig.cot(a), accuracy)){          exp = "cot"+a;   break OUTER;   }
                        if (number == MathRound.round(Math.log(a), accuracy)){          exp = "ln"+a;    break OUTER;   }
                        if (number == MathRound.round(1.0/Math.log(a), accuracy)){      exp = "1/ln"+a;  break OUTER;   }
                        if (number == MathRound.round(Math.log10(a), accuracy)){        exp = "log"+a;   break OUTER;   }
                        if (number == MathRound.round(1.0/Math.log10(a), accuracy)){    exp = "1/log"+a; break OUTER;   }
                    }
                }
            }));
            threads.add(new Thread(new Runnable() {
                @Override
                public void run() {
                    int accuracy = 16;
                    double number = MathRound.round(num, accuracy);
                    int sign = _Number_.getSign(num);
                    String[] stringSign = {"-", "", ""};
                    String[] stringSign2 = {"-", "", "+"};
                    OUTER:  for (int a = 2;  exp.equals("") && (System.currentTimeMillis()-start <= time); a++){
                        if (number == MathRound.round(sign*Math.sqrt(a), accuracy)){                exp = stringSign[sign+1]+"√"+a;                                break;  };
                        if (number == MathRound.round(sign*1.0/Math.sqrt(a), accuracy)){            exp = stringSign[sign+1]+"1/√"+a;                              break;  };
                        if (number == MathRound.round(sign*Math.abs(1-Math.sqrt(a)), accuracy)){    exp = stringSign[-1*sign+1]+1+stringSign2[sign+1]+"√"+a;       break;  };
                        if (number == MathRound.round(sign*Math.abs(1+Math.sqrt(a)), accuracy)){    exp = stringSign[sign+1]+1+stringSign2[sign+1]+"√"+a;          break;  };
                        for (int b = 2; b<=a && exp.equals("") && (System.currentTimeMillis()-start <= time); b++){
                            if (number == MathRound.round(sign*a*Math.sqrt(b), accuracy)){              exp = stringSign[sign+1]+a+"√"+b;                              break;  };
                            if (number == MathRound.round(sign*a/Math.sqrt(b), accuracy)){              exp = stringSign[sign+1]+a+"/√"+b;                             break;  };
                            if (number == MathRound.round(sign*Math.abs(a-Math.sqrt(b)), accuracy)){    exp = stringSign[-1*sign+1]+a+stringSign2[sign+1]+"√"+b;       break;  };
                            if (number == MathRound.round(sign*Math.abs(a+Math.sqrt(b)), accuracy)){    exp = stringSign[sign+1]+b+stringSign2[sign+1]+"√"+a;          break;  };
                        }
                    }
                }
            }));
        }
        try{
            for (Thread thread : threads){
                thread.start();
            }
            for (Thread thread : threads){
                thread.join();
            }
            if (!exp.trim().equals("")){
                if (brackets){
                    return "("+exp+")";
                }
                return exp;
            }
        }catch(InterruptedException e){}
        return _Number_.format(MathRound.round(num, 14));
    }

}
