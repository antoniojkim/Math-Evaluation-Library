package Math_Evaluation_Library.Objects;

import Math_Evaluation_Library.Constants.Constants;
import Math_Evaluation_Library.Engine.Engine;
import Math_Evaluation_Library.IO;
import Math_Evaluation_Library.Search;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Double.NaN;

/**
 * Created by Antonio on 2017-07-11.
 */
public class _Number_ {


    public static List<Integer> primes = new ArrayList<>();

    public static void loadPrimes(){
        BufferedReader br = IO.filereader("./src/Math_Evaluation_Engine/Primes.txt");
        try {
            String[] primes = br.readLine().split(",");
            for (int a = 0; a<primes.length; a++){
                _Number_.primes.add(Integer.parseInt(primes[a]));
            }
        } catch (IOException ex) {        }
    }
    public static int isPrime(int num){
        if (primes.isEmpty()){
            loadPrimes();
        }
        if (primes.contains(num)){
            return 1;
        }
        else if (num < primes.get(primes.size()-1)){
            return 0;
        }
        else if (num < Math.pow(primes.get(primes.size()-1), 2)){
            for (int a = 0; a<primes.size(); a++){
                if (num%primes.get(a) == 0){
                    return 0;
                }
                else if (a == primes.size()-1){
                    return 1;
                }
            }
        }
        return -1;
    }
    public static List<Integer> getPrimes() {
        return primes;
    }

    private static List<Double> factorials = new ArrayList<>(Arrays.asList(1.0, 1.0, 2.0, 6.0, 24.0, 120.0, 720.0, 5040.0));
    public static double fact(int num){
        try{
            if (num < 0 || num > 170){
                return NaN;
            }
            if (num < 2){
                return 1;
            }
            if (num < factorials.size()){
                return factorials.get(num);
            }
            double factorial = factorials.get(factorials.size()-1);
            for (double i = factorials.size(); i<=num; i++){
                factorial *= i;
                factorials.add(factorial);
            }
            return factorial;
        }catch(NumberFormatException e){
            return NaN;
        }
    }
    public static double getFact(double num, int stop, double base){
        try{
            if (num < stop){
                double next = num*base;
                factorials.add(next);
                return num*(getFact(num+1, stop, next));
            }
            return num;
        }catch(NumberFormatException e){
            return NaN;
        }
    }

    public static double getFibonnaci(int n){
        double fib = ((Math.pow(Constants.gr, n) - Math.pow(-1*Constants.gr, -1*n))/Math.sqrt(5));
        if (Math.abs(Math.floor(fib)-fib) <= Math.abs(Math.ceil(fib)-fib)){
            fib = Math.floor(fib);
        }
        else{
            fib = Math.ceil(fib);
        }
        return fib;
    }
    public static double getFibonnaciSum(int n){
        return getFibonnaciSum(n+2)-1;
    }

    public static int pow2(int x){
        if (x < 0)
            return 0;
        --x;
        x |= x >> 1;
        x |= x >> 2;
        x |= x >> 4;
        x |= x >> 8;
        x |= x >> 16;
        return x+1;
    }
    public static String toBinary(int x){
        int m = pow2(x);
        String binary = "";
        for (int a = m; a>=0; a--){
            binary += String.valueOf((int)x/(int)Math.pow(2, a));
            x = (int)x%(int)Math.pow(2, a);
        }
        return binary;
    }
    public static double fromBinary(String binary){
        double num = 0;
        char[] array = binary.toCharArray();
        for (int a = 0; a<array.length; a++){
            if (array[a] == '1'){
                num += (1 << array.length-a-1);
            }
            else if (array[a] != '0'){
                return -1;
            }
        }
        return num;
    }


    public static int getSign (double num){
        return (num > 0 ? 1 : (num < 0 ? -1 : 0));
    }
    public static String getSignStr (double num){
        return (num > 0 ? "+" : (num < 0 ? "-" : ""));
    }


    public static boolean isNumber(double num){
        String str = String.valueOf(num).toLowerCase();
        if (str.contains("nan") || str.contains("infinity") || str.contains("error")){
            return false;
        }
        return true;
    }
    public static boolean isNumber(String str){
        str = str.toLowerCase().trim();
        if (str.contains("error") || str.contains("nan") || str.contains("infinity")){
            return false;
        }
        try{
            double num = Double.parseDouble(str);
            return true;
        }catch(NumberFormatException e){}
        return isNumber(Constants.getConstant(str));
    }
    public static boolean isNumber(char c){
        if (c >= 48 && c <= 57) {//c >= 0 && c <= 9
            return true;
        }
        return Constants.isConstant(c);
//        return Constants.;
    }


    public static double getNumber(String str) throws NumberFormatException{
        return getNumber(str, false);
    }
    public static double getNumber(String str, boolean returnNaN) throws NumberFormatException{
        try{
            return Double.parseDouble(str);
        }catch(NumberFormatException e){
            try{
                return Double.parseDouble(Search.replace(str, "−", "-"));
            }catch(NumberFormatException e2){}
        }
        double constant = Constants.getConstant(str);
        if (!returnNaN && String.valueOf(constant).equals("NaN")){
            throw new NumberFormatException(str);
        }
        return constant;
    }


    public static String format(double num){
        if (num == -0.0){
            return "0";
        }
        return format(String.valueOf(num));
    }
    public static String format(String number){
        return removeEnding0(number);
    }

    private static String removeEnding0(String number){
        if (number.endsWith(".0")){
            return number.substring(0, number.length()-2);
        }
        return number;
    }

    public static String convertToStandard(String num){
        int index = num.indexOf("E");
        if (index != -1 && num.substring(0, index).length() < 17){
            String standard = "";
            int exponent = Integer.parseInt(num.substring(index+1));
            if (exponent > 0){
                standard = num.substring(0, index).replaceAll("\\.", "");
                if (standard.length() > (exponent+1)){
                    standard = standard.substring(0, exponent+1)+"."+standard.substring(exponent+1);
                }
                else{
                    for (int a = (exponent+1)-standard.length(); a>0; a--){
                        standard += "0";
                    }
                }
            }
            else{
                standard = "0.";
                for (int a = exponent; a<-1; a++){
                    standard += "0";
                }
                standard += num.substring(0, index).replaceAll("\\.", "");
            }
            return standard;
        }
        return num;
    }

    public static String commaFormat (double number){
        return displayFormat(number+"", ",");
    }
    public static String commaFormat (String number){
        return displayFormat(number, ",");
    }
    public static String displayFormat (double number, String separator){
        return displayFormat(number+"", ",");
    }
    public static String displayFormat (String number, String separator){
        double num = Double.parseDouble(number);
        if (num%1 == 0 && num>1000 && !number.contains("E")){
            int space = number.length()%3;
            List<String> split = new ArrayList<>();
            if (space > 0){
                split.add(number.substring(0, space));
            }
            for (int a = space; a+3 <= number.length(); a+=3){
                split.add(number.substring(a, a+3));
            }
            number = "";
            for (int a = 0; a<split.size(); a++){
                number+= split.get(a);
                if (a < split.size()-1){
                    number+=",";
                }
            }
        }
        if (number.endsWith(".0")){
            number = number.substring(0, number.length()-2);
        }
        return number;
    }

    public static List<Double> extractNumbers(String string){
        String parsed = Engine.toPostfix(string);
        return extractNumbers(parsed.split(" "));
    }
    public static List<Double> extractNumbers(String[] split){
        List<Double> numbers = new ArrayList<>();
        for (String term : split){
            try{
                numbers.add(_Number_.getNumber(term));
            } catch (NumberFormatException e){}
        }
        return numbers;
    }

}
