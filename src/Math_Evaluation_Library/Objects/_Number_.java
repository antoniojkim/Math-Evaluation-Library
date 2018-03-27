package Math_Evaluation_Library.Objects;

import Math_Evaluation_Library.Constants.Constants;
import Math_Evaluation_Library.IO;
import Math_Evaluation_Library.Search;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static Math_Evaluation_Library.Engine.Engine.toExpression;

/**
 * Created by Antonio on 2017-07-11.
 */
public class _Number_ {

    public static HashMap<Integer, Integer> primes = new HashMap<>();
    public static int largestPrimeInMap = 0;

    public static void loadPrimes(){
        BufferedReader br = IO.filereader("./src/Math_Evaluation_Engine/Primes.txt");
        try {
            String[] primes = br.readLine().split(",");
            for (String prime : primes) {
                int n = Integer.parseInt(prime);
                _Number_.primes.put(n, n);
            }
            largestPrimeInMap = Integer.parseInt(primes[primes.length-1]);
        } catch (IOException ex) {        }
    }
    public static int isPrime(int num){
        if (primes.isEmpty())    loadPrimes();
        if (primes.containsKey(num))    return 1;
        if (num < largestPrimeInMap)    return 0;
        if (num < Math.pow(largestPrimeInMap, 2)){
            for (int i : primes.values()){
                if (num%i == 0)    return 0;
            }
            primes.put(num, num);
            return 1;
        }
        return -1;
    }
    public static List<Integer> getPrimes() {
        return new ArrayList<>(primes.values());
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

    public static int floor2(int x){
        if (x < 0)  return 0;
        x |= x >> 1;
        x |= x >> 2;
        x |= x >> 4;
        x |= x >> 8;
        x |= x >> 16;
        return (x+1) >> 1;
    }
    public static double floor2(double x){
        return 1 << (int)Math.floor(Math.log(x)/Constants.ln2);
    }
    public static String toBinary(int x){
        int m = floor2(x);
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

    private static long fXOR(long a){
        long[] r = {a, 1, a+1, 0};
        return r[(int)(a % 4)];
    }
    public static long getXorRange(long a, long b){
        return fXOR(b)^fXOR(a-1);
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
        String lstr = str.toLowerCase().trim();
        if (lstr.contains("error") || lstr.contains("nan") || lstr.contains("infinity")){
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
        if (!returnNaN && _Number_.format(constant).equals("NaN")){
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
        return convertInfinity(removeEnding0(number));
    }

    private static String removeEnding0(String number){
        if (number.endsWith(".0")){
            return number.substring(0, number.length()-2);
        }
        return number;
    }
    private static String convertInfinity(String number){
        return Search.replace(number, "Infinity", "∞");
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
        return toExpression(string).getNumbers();
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
