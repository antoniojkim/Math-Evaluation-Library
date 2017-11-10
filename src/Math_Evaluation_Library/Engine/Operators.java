package Math_Evaluation_Library.Engine;

import Math_Evaluation_Library.Objects.Operator;
import Math_Evaluation_Library.Objects._Number_;
import Math_Evaluation_Library.Search;
import Math_Evaluation_Library.Statistics.Combinatorics;

/**
 * Created by Antonio on 2017-10-15.
 */
public class Operators {

    public static final String INVALID = "NaN";

//    public static void main(String[] args){
////        Sort.quicksort(operators);
//        char[] array = {'^', '°', '·', 'ʳ', '√', '≪', '≫', '!', '%', '*', '+', '-', '/', 'C', 'P'};
//        String[] array2 = {"°", "·", "ʳ", "√", "≪", "≫", "!", "%", "*", "+", "-", "/", "C", "P", "^"};
//        Sort.quicksort(array);
//        Sort.quicksort(array2);
//        for (int i = 0; i<array.length; i++){
//            System.out.print(String.valueOf(array[i])+", ");
//        }
//        System.out.println();
//        for (int i = 0; i<array2.length; i++){
//            System.out.print(array2[i]+", ");
//        }
//        System.out.println();
//    }

    public static final Operator[] operators = {
            new Operator("!", 4, true, true){
                @Override
                public String evaluate(double x) {
                    return _Number_.format(Combinatorics.fact(x));
                }
            },
            new Operator("%", 3, false, true){
                @Override
                public String evaluate(double x, double y) {
                    if (y == Double.POSITIVE_INFINITY || y == Double.NEGATIVE_INFINITY){
                        if (x == Double.POSITIVE_INFINITY || x == Double.NEGATIVE_INFINITY)    return INVALID;
                        return "0";
                    }
                    else if (x == Double.POSITIVE_INFINITY){    return "∞";     }
                    else if (x == Double.NEGATIVE_INFINITY){    return "-∞";    }
                    return Search.replace(_Number_.format(x%y), "Infinity", "∞");
                }
            },
            new Operator("&", 3, false, true){
                @Override
                public String evaluate(double x, double y) {
                    if (x%1 == 0 && y%1 == 0) {
                        return _Number_.format((int)x & (int)y);
                    }
                    return INVALID;
                }
            },
            new Operator("*", 3, false, true){
                @Override
                public String evaluate(double x, double y) {
                    return Search.replace(_Number_.format(x*y), "Infinity", "∞");
                }
            },
            new Operator("+", 2, false, true){
                @Override
                public String evaluate(double x, double y) {
                    return Search.replace(_Number_.format(x+y), "Infinity", "∞");
                }
            },
            new Operator("-", 2, false, true){
                @Override
                public String evaluate(double x, double y) {
                    return Search.replace(_Number_.format(x-y), "Infinity", "∞");
                }
            },
            new Operator("/", 3, false, true){
                @Override
                public String evaluate(double x, double y) {
                    if (y == Double.POSITIVE_INFINITY || y == Double.NEGATIVE_INFINITY){
                        if (x == Double.POSITIVE_INFINITY || x == Double.NEGATIVE_INFINITY)    return INVALID;
                        return "0";
                    }
                    else if (y == 0){
                        if (x == 0)   return INVALID;
                        return (x > 0 ? "∞" : "-∞");
                    }
                    else if (x == Double.POSITIVE_INFINITY){    return "∞";     }
                    else if (x == Double.NEGATIVE_INFINITY){    return "-∞";    }
                    return Search.replace(_Number_.format(x/y), "Infinity", "∞");
                }
            },
            new Operator("C", 4, false, true){
                @Override
                public String evaluate(double x, double y) {
                    if (x % 1 == 0 && y % 1 == 0) {
                        int n = (int) x;
                        int r = (int) y;
                        if (r > n) {
                            return INVALID;
                        }
                        return _Number_.format(Combinatorics.choose(n, r));
                    }
                    return INVALID;
                }
            },
            new Operator("P", 4, false, true){
                @Override
                public String evaluate(double x, double y) {
                    if (x % 1 == 0 && y % 1 == 0) {
                        int n = (int) x;
                        int r = (int) y;
                        if (r > n) {
                            return INVALID;
                        }
                        return _Number_.format(Combinatorics.permute(n, r));
                    }
                    return INVALID;
                }
            },
            new Operator("^", 4, false, false){
                @Override
                public String evaluate(double x, double y) {
                    if (x == Math.E){
                        return Search.replace(_Number_.format(Math.exp(y)), "Infinity", "∞");
                    }
                    return Search.replace(_Number_.format(Math.pow(x, y)), "Infinity", "∞");
                }
            },
            new Operator("~", 4, true, false){
                @Override
                public String evaluate(double x) {
                    if (x%1 == 0) {
                        return _Number_.format(~((int)x));
                    }
                    return INVALID;
                }
            },
            new Operator("°", 4, true, false){
                @Override
                public String evaluate(double x) {
                    return _Number_.format(Math.toRadians(x));
                }
            },
            new Operator("·", 3, false, true){
                @Override
                public String evaluate(double x, double y) {
                    return Search.replace(_Number_.format(x*y), "Infinity", "∞");
                }
            },
            new Operator("ʳ", 4, true, false){
                @Override
                public String evaluate(double x) {
                    return _Number_.format(Math.toDegrees(x));
                }
            },
            new Operator("√", 4, true, false){
                @Override
                public String evaluate(double x) {
                    return _Number_.format(Math.sqrt(x));
                }
            },
            new Operator("∛", 4, true, false){
                @Override
                public String evaluate(double x) {
                    return _Number_.format(Math.cbrt(x));
                }
            },
            new Operator("∨", 3, false, true){
                @Override
                public String evaluate(double x, double y) {
                    if (x%1 == 0 && y%1 == 0) {
                        return _Number_.format((int)x | (int)y);
                    }
                    return INVALID;
                }
            },
            new Operator("≪", 3, false, true){
                @Override
                public String evaluate(double x, double y) {
                    if (x%1 == 0 && y%1 == 0) {
                        return _Number_.format((int)x << (int)y);
                    }
                    return INVALID;
                }
            },
            new Operator("≫", 3, false, true){
                @Override
                public String evaluate(double x, double y) {
                    if (x%1 == 0 && y%1 == 0) {
                        return _Number_.format((int)x >> (int)y);
                    }
                    return INVALID;
                }
            },
            new Operator("⊻", 3, false, true){
                @Override
                public String evaluate(double x, double y) {
                    if (x%1 == 0 && y%1 == 0) {
                        return _Number_.format((int)x ^ (int)y);
                    }
                    return INVALID;
                }
            }
    };

    public static int operatorIndex(String item){
        return (item.length() == 1 ? Search.binarySearch(operators, item) : -1);
    }
    public static int operatorIndex(char item){
        return Search.binarySearch(operators, String.valueOf(item));
    }
    public static boolean operatorContains(String item){
        return (item.length() == 1 ? Search.contains(operators, item) : false);
    }
    public static boolean operatorContains(char item){
        return Search.contains(operators, String.valueOf(item));
    }

}
