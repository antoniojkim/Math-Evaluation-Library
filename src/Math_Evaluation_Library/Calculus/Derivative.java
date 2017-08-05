package Math_Evaluation_Library.Calculus;

import Math_Evaluation_Library.Engine.Engine;
import Math_Evaluation_Library.Miscellaneous.Format;
import Math_Evaluation_Library.Miscellaneous.MathRound;
import Math_Evaluation_Library.Miscellaneous.Simplify;
import Math_Evaluation_Library.Objects.Function;
import Math_Evaluation_Library.Objects._Number_;
import Math_Evaluation_Library.Search;

import java.util.List;

/**
 * Created by Antonio on 2017-07-13.
 */
public class Derivative {

    public static double value(String function, double num){
        String derivative = calculate(function);
        if (!derivative.endsWith("'")){
            return Engine.evaluate(derivative, num);
        }
        return nDeriv(function, num);
    }

    public static double nDeriv(String function, double num){
        Function f = new Function(function);
        double h = 7.5050268766E-6;                 //0.00007505021;0.000001;//
        double nderiv = MathRound.round((f.of(num+h)-f.of(num-h))/(2*h), 9);
        return nderiv;
    }

    public static String calculate(String function){
        String simplified = Simplify.simplify(function);
        if (!simplified.contains("Invalid")) {
            String derivative = differentiate(simplified);
            if (derivative.charAt(0) != '\'') {
                return derivative;
            }
            derivative = additiveRule(simplified);
            if (derivative.charAt(0) != '\'') {
                return derivative;
            }
        }
        return "("+function+")'";
    }

    public static String differentiate(String function){
        String derivative = findDerivative(function);
        if (derivative.charAt(0) != '\''){
            return Simplify.simplify(derivative);
        }
        return "'";
    }

    public static String additiveRule(String function){
        List<Integer> indices = Search.getIndices(function, "+-");
        if (indices.size() > 0){
            try {
                String derivative = "";
                indices.add(0, 0);
                indices.add(function.length());
                for (int i = 1; i < indices.size(); i++) {
                    String part = function.substring(indices.get(i - 1), indices.get(i));
                    if (part.charAt(0) == '+') {
                        part = differentiate(part.substring(1));
                    }
                    else {
                        part = differentiate(part);
                    }
                    char start = part.charAt(0);
                    if (start == '\''){  throw new StringIndexOutOfBoundsException();    }
                    part = Simplify.simplify(part);
                    if (start != '-' && i > 1){
                        derivative += "+" + part;
                    }
                    else{
                        derivative += part;
                    }
                }
                return derivative;
            } catch (StringIndexOutOfBoundsException e){}
        }
        return "'";
    }

    public static String findDerivative(String function){
        String parsed = Engine.toPostfix(function);
        return findDerivative(parsed, _Number_.extractNumbers(parsed.split(" ")));
    }
    public static String findDerivative(String parsed, List<Double> numbers){
        if (numbers.size() == 0){
            for (int i = 0; i<database0.length; i+=2){
                if (parsed.equals(Format.format(database0[i]))){
                    return Format.format(database0[i+1]);
                }
            }
        }
        else if (numbers.size() == 1){
            String formattedNumber = _Number_.format(numbers.get(0));
            for (int i = 0; i<database1.length; i+=2){
                if (parsed.equals(Format.format(database1[i], "{0}:"+formattedNumber))){
                    return Format.format(database1[i+1], "{0}:"+formattedNumber);
                }
            }
            for (int i = 0; i<database0.length; i+=2){
                if (parsed.equals(formattedNumber+" "+Format.format(database0[i])+" *")){
                    return formattedNumber+"*"+Format.format(database0[i+1]);
                }
            }
            for (int i = 0; i<database0.length; i+=2){
                if (parsed.equals(Format.format(database0[i], "{x}:"+formattedNumber+" x *"))){
                    return formattedNumber+"*"+Format.format(database0[i+1], "{x}:("+formattedNumber+"x)");
                }
            }
        }
        else if (numbers.size() == 2){
            String formattedNumber0 = _Number_.format(numbers.get(0));
            String formattedNumber1 = _Number_.format(numbers.get(1));
            for (int i = 0; i<database0.length; i+=2){
                if (parsed.equals(formattedNumber0+" "+Format.format(database0[i], "{x}:"+formattedNumber1+" x *")+" *")){
                    return _Number_.format(numbers.get(0)*numbers.get(1))+"*"+
                            Format.format(database0[i+1], "{x}:("+formattedNumber1+"x)");
                }
            }
            for (int i = 0; i<database1.length; i+=2){
                if (parsed.equals(formattedNumber0+" "+Format.format(database1[i], "{0}:"+formattedNumber1)+" *")){
                    return formattedNumber0+"*"+Format.format(database1[i+1], "{0}:"+formattedNumber1);
                }
            }
            for (int i = 0; i<database1.length; i+=2){
                if (parsed.equals(Format.format(database1[i], "{0}:"+formattedNumber0, "{x}:"+formattedNumber1+" x *"))){
                    return formattedNumber1+"*"+Format.format(database1[i+1], "{0}:"+formattedNumber0, "{x}:("+formattedNumber1+"x)");
                }
                if (parsed.equals(Format.format(database1[i], "{0}:"+formattedNumber1, "{x}:"+formattedNumber0+" x *"))){
                    return formattedNumber0+"*"+Format.format(database1[i+1], "{0}:"+formattedNumber1, "{x}:("+formattedNumber0+"x)");
                }
            }
        }
        return "'";
    }

    public static final String[] database0 = {
            "{x}",          "1",
            "{x} √",        "1/(2√{x})",
            "{x} sin",      "cos{x}",
            "{x} cos",      "-1*sin{x}",
            "{x} tan",      "sec²{x}",
            "{x} csc",      "-1*csc{x}cot{x}",
            "{x} sec",      "sec{x}tan{x}",
            "{x} cot",      "-1*csc²{x}",
            "{x} arcsin",   "1/√(1-x²)",
            "{x} arccos",   "-1/√(1-x²)",
            "{x} arctan",   "1/(1+x²)",
            "{x} arccsc",   "-1/(|{x}|√({x}²-1))",
            "{x} arcsec",   "1/(|{x}|√({x}²-1))",
            "{x} arccot",   "-1/(1+{x}²)",
            "{x} sinh",     "cosh{x}",
            "{x} cosh",     "sinh{x}",
            "{x} tanh",     "sech²{x}",
            "{x} csch",     "-1*csch{x}coth{x}",
            "{x} sech",     "-1*sech{x}tanh{x}",
            "{x} coth",     "csch²{x}",
            "{x} ln",       "1/{x}",
            "{x} log",      "1/({x}*ln10)",
            "{x} abs",      "{x}/|{x}|",
            "{x} cos sin",  "-1*cos(cos{x})*sin{x}",
            "{x} tan cos",  "sin(tan{x})sec²{x}",
            "{x} sin ln",   "cot{x}",
            "{x} cos ln",   "-1*tan{x}"
    };
    public static final String[] database1 = {
            "{0}",          "0",
            "{x} {0} ^",    "{0}*{x}^[{0}-1]",
            "e {x} ^",      "e^{x}",
            "{0} {x} ^",    "{0}^{x}*ln{0}",
    };

}
