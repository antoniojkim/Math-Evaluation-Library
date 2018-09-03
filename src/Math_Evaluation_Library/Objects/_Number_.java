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

    public static long floor2(long x){
//        if (x < 0)  return 0;
//        x |= x >> 1;
//        x |= x >> 2;
//        x |= x >> 4;
//        x |= x >> 8;
//        x |= x >> 16;
//        return (x+1) >> 1;
        return Long.highestOneBit(x);
    }
    public static double floor2(double x){
        return 1 << (int)Math.floor(Math.log(x)/Constants.ln2);
    }
    public static long log2(long x){
        long log = 0;
        if((x & 0xffff0000) != 0) { x >>= 16; log = 16; }
        if(x >= 256) { x >>= 8; log += 8; }
        if(x >= 16)  { x >>= 4; log += 4; }
        if(x >= 4)   { x >>= 2; log += 2; }
        return log + ( x >> 1 );
    }
    public static int binlog( int bits ) // returns 0 for bits=0
    {
        int log = 0;
        if( ( bits & 0xffff0000 ) != 0 ) { bits >>>= 16; log = 16; }
        if( bits >= 256 ) { bits >>>= 8; log += 8; }
        if( bits >= 16  ) { bits >>>= 4; log += 4; }
        if( bits >= 4   ) { bits >>>= 2; log += 2; }
        return log + ( bits >>> 1 );
    }
    public static String toBinary(long x){
        return toBinary(x, 0);
    }
    public static String toBinary(long x, long padding){
        x = Math.abs(x);
        String binary = Long.toBinaryString(x);
        if (padding > binary.length()){
            StringBuilder pad = new StringBuilder();
            for (int i = binary.length(); i<padding; ++i){
                pad.append("0");
            }
            return pad.toString()+binary;
        }
        return binary;
    }
    public static long fromBinary(String binary){
        long num = 0;
        char[] array = binary.toCharArray();
        if (array.length > 62) return -1;
        for (char b : array) {
            if (b == '1') {
                num = (num << 1) | 1;
            } else if (b == '0') {
                num <<= 1;
            } else {
                return -1;
            }
        }
        return num;
    }
    public static String toTwosComplement(long x){
        if (x >= 0){
            String binary = toBinary(x);
            if (x > 0 && binary.charAt(0) == '1'){
                return "0"+binary;
            }
            return binary;
        }
        x = -x;

        long f = floor2(x) << 1;

        return "1"+toBinary(f-x, log2(f));
    }
    public static String toBinary(double x){
        return toBinary(x, 0);
    }
    public static String toBinary(double x, long padding){
        String binary = padding == 32 ?
                Integer.toBinaryString(Float.floatToIntBits((float)x)):
                Long.toBinaryString(Double.doubleToLongBits(x));
        if (padding > binary.length()){
            StringBuilder pad = new StringBuilder();
            for (int i = binary.length(); i<padding; ++i){
                pad.append("0");
            }
            return pad.toString()+binary;
        }
        return binary;
    }

    public static long fromHex(String binary){
        long num = 0;
        char[] array = binary.toCharArray();
        if (array.length > 62) return -1;
        for (char b : array) {
            if (b == '1') {
                num = (num << 1) | 1;
            } else if (b == '0') {
                num <<= 1;
            } else {
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

    public static long isPerfectSquare(long num){
        long sqrt = (long) Math.sqrt(num);
        if (num == sqrt*sqrt){
            return sqrt;
        }
        return -1;
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
        else if (Constants.isConstant(str)){
            return true;
        }
        char[] array = str.toCharArray();
        boolean negativeCheck = false;
        boolean point = false;
        boolean isNumber = false;
        for (char c : array){
            if (c >= '0' && c <= '9') {
                isNumber = true;
            } else {
                if (!negativeCheck && c == '-'){
                    negativeCheck = true;
                    continue;
                }
                if (!point && c == '.'){
                    point = true;
                    continue;
                }
                return false;
            }
        }
        return isNumber;
    }
    public static boolean isNumber(char c){
        return isNumber(c, true);
//        return Constants.;
    }
    public static boolean isNumber(char c, boolean checkConstants){
        if (c >= 48 && c <= 57) {//c >= 0 && c <= 9
            return true;
        }
        return checkConstants && Constants.isConstant(c);
//        return Constants.;
    }
    public static boolean isInteger(double num){
        return isInteger(num, 1e-13);
    }
    public static boolean isInteger(double num, double error){
        return Math.abs(Math.round(num)-num) <= error;
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
