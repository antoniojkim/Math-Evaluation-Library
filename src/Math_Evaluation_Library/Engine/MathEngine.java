package Math_Evaluation_Library.Engine;

import Math_Evaluation_Library.Constants.Scripts;
import Math_Evaluation_Library.LinearAlgebra._Matrix_;
import Math_Evaluation_Library.Miscellaneous.Simplify;
import Math_Evaluation_Library.Objects.Function;
import Math_Evaluation_Library.Objects._Number_;
import Math_Evaluation_Library.Trigonometry.Trig;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Double.NaN;

/**
 * Created by Antonio on 2017-07-23.
 */
public class MathEngine extends Engine{


    public static String evaluate(List<String> list){
        if ((list.contains(var) || list.contains(varOp)) && !list.contains("sum") && !list.contains("product")){
            return "Invalid Input Error - Unresolved variable";
        }
        List<String> outputs = new ArrayList<>();
        outputs.addAll(list);
        for (int a = 0; a < outputs.size(); a++) {
            if (!_Number_.isNumber(outputs.get(a))){
                try {
                    if (orderContains(outputs.get(a))) {
                        if (outputs.get(a).equals("strln")) {
                            outputs.set(a - 1, "" + (int)(outputs.get(a - 1).length()));
                        }
                        else{
                            String function = outputs.get(a);
                            int index = Scripts.getSuperScriptIndex(function.substring(function.length()-1));
                            int n = 1;
                            if (index != -1){
                                n = index;
                                function = function.substring(0, function.length()-1);
                            }
                            double value = getMathFunctionValue(outputs.get(a), _Number_.getNumber(outputs.get(a - 1)));
                            outputs.set(a-1, "" + Math.pow(value, n));
                        }
                        outputs.remove(a);
                        a = -1;
                    }
                    else if (operatorContains(outputs.get(a))) {
                        if (outputs.get(a).equals("√")) {
                            outputs.set(a - 1, "" + Math.sqrt(_Number_.getNumber(outputs.get(a - 1))));
                            outputs.remove(a);
                        } else if (a > 0 && outputs.get(a).equals("°")) {
                            outputs.set(a - 1, "" + Math.toRadians(_Number_.getNumber(outputs.get(a - 1))));
                            outputs.remove(a);
                        } else if (a > 0 && outputs.get(a).equals("ʳ")) {
                            outputs.set(a - 1, "" + Math.toDegrees(_Number_.getNumber(outputs.get(a - 1))));
                            outputs.remove(a);
                        } else if (outputs.get(a).equals("!")) {
                            try {
                                double x = _Number_.getNumber(outputs.get(a - 1));
                                if (x % 1 == 0 && x<=170) {
                                    outputs.set(a - 1, _Number_.format(_Number_.fact((int)x)));
                                    outputs.remove(a);
                                } else {
                                    return "NaN";
                                }
                            } catch (NumberFormatException num) {
                                return "NaN";
                            }
                        } else {
                            String param1 = outputs.get(a - 2);
                            String param2 = outputs.get(a - 1);
                            char param1char0 = param1.charAt(0);
                            char param2char0 = param2.charAt(0);
                            if (param1char0 == '{' || param2char0 == '{' || param1char0 == '[' || param2char0 == '['){
                                String newMatrix = _Matrix_.matrixArithmeticOperations(outputs.get(a).charAt(0), param1, param2);
                                outputs.set(a - 2, newMatrix);
                                Simplify.remove(outputs, a, a-1);
                            }
                            else{
                                String value = getArithmeticValue(outputs.get(a).charAt(0), param1, param2);
                                outputs.set(a - 2, value);
                                Simplify.remove(outputs, a, a-1);
                            }
                        }
                        a = -1;
                    }
                    else if (isMultiParamFunction(outputs.get(a))){
                        int index = multiParamFunctionNamesIndex(outputs.get(a));
                        if (numParameters[index] == -1){
                            int numInputs = Integer.parseInt(outputs.get(a - 1));
                            int start = a-numInputs-1;
                            String[] parameters = new String[numInputs];
                            for (int i = 0; i<parameters.length; i++){
                                parameters[i] = outputs.get(start+i);
                            }
                            String result = MultiParamFunctions.evaluate(outputs.get(a), parameters);
                            if (result.charAt(0) == '!'){
                                return "Invalid Input Error - Invalid Parameters";
                            }
                            outputs.set(start, result);
                            for (int b = 0; b <= numInputs; b++){
                                outputs.remove(a-b);
                            }
                        }
                        else{
                            int start = a-numParameters[index];
                            String[] parameters = new String[numParameters[index]];
                            for (int i = 0; i<parameters.length; i++){
                                parameters[i] = outputs.get(start+i);
                            }
                            String result = MultiParamFunctions.evaluate(outputs.get(a), parameters, false);
                            if (result.charAt(0) == MultiParamFunctions.INVALID.charAt(0)){
                                return "Invalid Input Error - Invalid Number of Parameters";
                            }
                            outputs.set(start, result);
                            for (int b = 0; b < numParameters[index]; b++){
                                outputs.remove(a-b);
                            }
                        }
                        a = -1;
                    }
                    else if (outputs.get(a).equals("eval")) {
                        String fx = outputs.get(a - 2);
                        double x = evaluate(outputs.get(a - 1));
                        double eval = evaluate(fx, x);
                        outputs.set(a-2, String.valueOf(eval));
                        Simplify.remove(outputs, a, a-1);
                        a = -1;
                    }
                    else if (outputs.get(a).equals("evalint")) {
                        Function f = new Function(outputs.get(a - 3));
                        double av = _Number_.getNumber(outputs.get(a - 2));
                        double bv = _Number_.getNumber(outputs.get(a - 1));
                        double eval = NaN;
                        if (f.isContainsVar() && f.isContainsVarOp()){
                            eval = f.of(av, bv);
                        }
                        else if (!f.isContainsVar() && f.isContainsVarOp()){
                            eval = f.of(bv)-f.of(av);
                        }
                        else{
                            eval = f.of(bv)-f.of(av);
                        }
                        outputs.set(a-3, String.valueOf(eval));
                        Simplify.remove(outputs, a, a-1, a-2);
                        a = -1;
                    }
                    else if (outputs.get(a).contains("∞")){ }
                    else if (outputs.get(a).charAt(0) == '{' || outputs.get(a).charAt(0) == '['){
                        char last = outputs.get(a).charAt(outputs.get(a).length()-1);
                        if (last == 'τ' || last == 'ι'){
                            outputs.set(a, _Matrix_.toStrMatrix(_Matrix_.toDoubleMatrix(outputs.get(a))));
                        }
                    }
                    else{
                        try {
                            if (outputs.get(a + 1).equals("unit") || outputs.get(a + 2).equals("unit")) {}
                            else if (outputs.get(a + 1).equals("eval") || outputs.get(a + 2).equals("eval")) {}
                            else if (outputs.get(a + 1).equals("evalint") || outputs.get(a + 2).equals("evalint") || outputs.get(a + 3).equals("evalint")) {}
                            else if (outputs.get(a + 3).equals("nint")) {}
                            else if (outputs.get(a + 3).equals("sum")) {}
                            else if (outputs.get(a + 3).equals("product")) {}
                            else {
                                return "Invalid Input Error - Unrecognized character(s):  "+outputs.get(a);
                            }
                        }catch (IndexOutOfBoundsException e){}
                    }
                } catch (ArrayIndexOutOfBoundsException | NumberFormatException | ArithmeticException e3) {
                    return "NaN";
                } catch (IndexOutOfBoundsException e4){
                    return "NaN";
                }
            }
        }
        if (outputs.size() == 1) {
            if (outputs.get(0).toLowerCase().contains("nan")) {
                return "NaN";
            }
            return outputs.get(0);
        }
        return "NaN";
    }

    public static double getMathFunctionValue(String function, double x){
        try {
            switch (function){
                case "cbrt":     return Math.cbrt(x);

                //Trig Functions
                case "sin":     return Math.sin(x);
                case "cos":     return Math.cos(x);
                case "tan":     return Math.tan(x);
                case "csc":     return Trig.csc(x);
                case "sec":     return Trig.sec(x);
                case "cot":     return Trig.cot(x);
                case "arcsin":  return Math.asin(x);
                case "arccos":  return Math.acos(x);
                case "arctan":  return Math.atan(x);
                case "arccsc":  return Trig.acsc(x);
                case "arcsec":  return Trig.asec(x);
                case "arccot":  return Trig.acot(x);
                case "sinh":    return Math.sinh(x);
                case "cosh":    return Math.cosh(x);
                case "tanh":    return Math.tanh(x);
                case "csch":    return Trig.csch(x);
                case "sech":    return Trig.sech(x);
                case "coth":    return Trig.coth(x);
                case "arcsinh": return Trig.asinh(x);
                case "arccosh": return Trig.acosh(x);
                case "arctanh": return Trig.atanh(x);
                case "arccsch": return Trig.acsch(x);
                case "arcsech": return Trig.asech(x);
                case "arccoth": return Trig.acoth(x);

                case "ln":      return Math.log(x);
                case "lp":      return Math.log1p(x);
                case "log":     return Math.log10(x);
                case "aexp":    return Math.exp(x);

                case "abs":     return Math.abs(x);
                case "rad":     return Math.toRadians(x);
                case "deg":     return Math.toDegrees(x);
                case "floor":   return Math.floor(x);
                case "ceil":    return Math.ceil(x);
                case "prime":   if (x % 1 == 0)  return _Number_.isPrime((int) x);  break;
                case "fib":     if (x % 1 == 0 && x >= 0 && x <= 1472)  return _Number_.getFibonnaci((int) x);     break;
                case "smfib":   if (x % 1 == 0 && x >= 0 && x <= 1472)  return _Number_.getFibonnaci((int) (x+2))-1;  break;
                case "tobin":   if (x % 1 == 0){ return _Number_.getNumber(_Number_.toBinary((int)x));}     break;
                case "bin":     if (x % 1 == 0){ double num = _Number_.fromBinary(String.valueOf(x)); if (num != -1){return num;}}   break;
                default:        return x;
            }
        } catch (NumberFormatException | ArithmeticException e) {
        }
        return NaN;
    }

    public static String getArithmeticValue(char operator, String xString, String yString){
        try {
            double x;
            if (xString.equals("∞")){
                x = Double.POSITIVE_INFINITY;
            }
            else if (xString.equals("-∞")){
                x = Double.NEGATIVE_INFINITY;
            }
            else{
                x = _Number_.getNumber(xString);
            }
            double y;
            if (yString.equals("∞")){
                y = Double.POSITIVE_INFINITY;
            }
            else if (yString.equals("-∞")){
                y = Double.NEGATIVE_INFINITY;
            }
            else{
                y = _Number_.getNumber(yString);
            }
            if (operator == '^') {
                if (x == Math.E){
                    return (Math.exp(y)+"").replaceAll("Infinity", "∞");
                }
                return (Math.pow(x, y)+"").replaceAll("Infinity", "∞");
            } else if (operator == '*' || operator == '·') {
                return ((x * y)+"").replaceAll("Infinity", "∞");
            } else if (operator == '/') {
                if (y == Double.POSITIVE_INFINITY || y == Double.NEGATIVE_INFINITY){
                    if (x == Double.POSITIVE_INFINITY || x == Double.NEGATIVE_INFINITY){
                        return "NaN";
                    }
                    return "0";
                }
                else if (y == 0){
                    if (x == 0){
                        return "NaN";
                    }
                    if (x > 0){
                        return "∞";
                    }
                    return "-∞";
                }
                else if (x == Double.POSITIVE_INFINITY){
                    return "∞";
                }
                else if (x == Double.NEGATIVE_INFINITY){
                    return "-∞";
                }
                return ((x / y)+"").replaceAll("Infinity", "∞");
            } else if (operator == '%') {
                if (y == Double.POSITIVE_INFINITY || y == Double.NEGATIVE_INFINITY){
                    if (x == Double.POSITIVE_INFINITY || x == Double.NEGATIVE_INFINITY){
                        return "NaN";
                    }
                    return "0";
                }
                else if (x == Double.POSITIVE_INFINITY){
                    return "∞";
                }
                else if (x == Double.NEGATIVE_INFINITY){
                    return "-∞";
                }
                return ((x % y)+"").replaceAll("Infinity", "∞");
            } else if (operator == 'P') {
                if (x % 1 == 0 && y % 1 == 0) {
                    int n = (int) x;
                    int r = (int) y;
                    if (r > n || n > 170) {
                        return "NaN";
                    }
                    return (_Number_.fact(n) / _Number_.fact(n - r))+"";
                }
            } else if (operator == 'C') {
                if (x % 1 == 0 && y % 1 == 0) {
                    int n = (int) x;
                    int r = (int) y;
                    if (r > n || n > 170 || r > 170) {
                        return "NaN";
                    }
                    return (_Number_.fact(n) / (_Number_.fact(n - r) * _Number_.fact(r)))+"";
                }
            } else if (operator == '-') {
                return ((x - y)+"").replaceAll("Infinity", "∞");
            } else if (operator == '+') {
                return ((x + y)+"").replaceAll("Infinity", "∞");
            }
        } catch (NumberFormatException | ArithmeticException e) {}
        return "NaN";
    }

}
