package Math_Evaluation_Library.Calculus;

import Math_Evaluation_Library.Engine.Engine;
import Math_Evaluation_Library.Miscellaneous.Format;
import Math_Evaluation_Library.Miscellaneous.MathRound;
import Math_Evaluation_Library.Miscellaneous.Simplify;
import Math_Evaluation_Library.Objects.Function;
import Math_Evaluation_Library.Objects._Number_;
import Math_Evaluation_Library.Search;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Antonio on 2017-07-13.
 */
public class Integral {

    public static double nint(String function, double a, double b){
        return value(function, a, b);
    }
    public static double value(String function, double a, double b){
        String integral = calculate(function);
        if (integral.charAt(0) != 'ʃ'){
            Function f = new Function(integral);
            return f.of(b)-f.of(a);
        }
        return simpsons(function, a, b);
    }

    private static double simpsons(String fx, double a, double b){
        Function f = new Function (fx);
        double n = 50;//Math.max(Math.min((int)((b-a)*1634), 10000), 1500)
        double range = b-a;
        double h = range/n;
        double range_180 = range/180;
        double error = range_180*Math.pow(h, 4);
        while (error > Math.pow(10, -15) && n < 10000){ //nint(sin(cosx), 1, 3)
            n += 50;
            h = range/n;
            error = range_180*Math.pow(h, 4);
        }
        //System.out.println(n+"     "+error);
        double total = 0;
        long start = System.currentTimeMillis();
        List<Slice> slices = new ArrayList<>();
        List<Thread> threads = new ArrayList<>();
        for (int x = 1; x<n; x++){
            Slice slice = new Slice(f, a, x, h);
            slices.add(slice);
            Thread thread = new Thread(slice);
            thread.start();
            threads.add(thread);
        }
        total += f.of(a);
        total += f.of(b);
        for (int x = 0; x<threads.size(); x++){
            try{
                threads.get(x).join();
                total += slices.get(x).getNum();
            }catch(InterruptedException e){}
        }
        int index = (error+"").indexOf("E");
        if (index != -1){
            return MathRound.round(total*(h/3.0), (int)Math.abs(Double.parseDouble((error+"").substring(index+1))));
        }
        return MathRound.round(total*(h/3.0), 16);
    }

    private static class Slice implements Runnable{

        double num = 0;
        int[] multiply = {2, 4};
        Function f;
        int x;
        double a = 0, h = 0;

        public Slice(Function function, double a, int x, double h){
            f = function;
            this.a = a;
            this.x = x;
            this.h = h;
        }

        @Override
        public void run() {
            num = multiply[x%2]*f.of(a+(x*h));
            //+multiply[(x+1)%2]*f.of(a+((x+1)*h))
        }

        public double getNum(){
            return num;
        }
    }

    public static String calculate(String function){
        String simplified = Simplify.simplify(function);
        if (!simplified.contains("Invalid")) {
            String integral = integrate(simplified);
            if (integral.charAt(0) != '\'') {
                return integral;
            }
            integral = additiveRule(simplified);
            if (integral.charAt(0) != '\'') {
                return integral;
            }
        }
        return "ʃ("+function+")";
    }

    public static String integrate(String function){
        String integral = findIntegral(function);
        if (integral.charAt(0) != '\''){
            return Simplify.simplify(integral);
        }
        return "'";
    }

    public static String additiveRule(String function){
        List<Integer> indices = Search.getIndices(function, "+-");
        if (indices.size() > 0){
            try {
                String integral = "";
                indices.add(0, 0);
                indices.add(function.length());
                for (int i = 1; i < indices.size(); i++) {
                    char operator = function.charAt(indices.get(i - 1));
                    String part = function.substring(indices.get(i - 1), indices.get(i));
                    if (operator == '+') {
                        part = integrate(part.substring(1));
                    }
                    else {
                        part = integrate(part);
                    }
                    char start = part.charAt(0);
                    if (start == '\''){  throw new StringIndexOutOfBoundsException();    }
                    part = Simplify.simplify(part);
                    if (start == '-'){
                        if (operator == '-'){
                            integral += "+" + part;
                        }
                        else{
                            integral += part;
                        }
                    }
                    else{
                        integral += "+"+part;
                    }
                }
                return integral;
            } catch (StringIndexOutOfBoundsException e){}
        }
        return "'";
    }

    public static String findIntegral(String function){
        String parsed = Engine.toPostfix(function);
        return findIntegral(parsed, _Number_.extractNumbers(parsed.split(" ")));
    }
    public static String findIntegral(String parsed, List<Double> numbers){
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
            "{x}",                 "(1/2)x^2",
            "{x} √",               "(2/3)x^(3/2)",
            "{x} cos",             "sin{x}",
            "{x} sin",             "-1*cos{x}",
            "{x} tan",             "-ln(abs(cos{x}))",
            "{x} cot",             "ln(abs(sin{x}))",
            "{x} ln",              "{x}(ln{x}-1)",
            "{x} log",             "({x}/ln10)(ln{x}-1)",
            "{x} csc {x} cot *",   "-1*csc{x}",
            "{x} cot {x} csc *",   "-1*csc{x}",
            "{x} sec {x} tan *",   "sec{x}",
            "{x} tan {x} sec *",   "sec{x}",
            "{x} sinh",            "cosh{x}",
            "{x} cosh",            "sinh{x}",
            "{x} csch {x} coth *", "-1*csch{x}",
            "{x} coth {x} csch *", "-1*csch{x}",
            "{x} sech {x} tanh *", "-1*sech{x}",
            "{x} tanh {x} sech *", "-1*sech{x}",
            "{x} {x} abs /",       "|{x}|",
//            "{x} cos sin",        "-1*cos(cos{x})*sin{x}",
//            "{x} tan cos",        "sin(tan{x})sec²{x}"
//            "{x} arcsin",   "1/√(1-x²)",
//            "{x} arccos",   "-1/√(1-x²)",
//            "{x} arctan",   "1/(1+x²)",
//            "{x} arccsc",   "-1/(|{x}|√({x}²-1))",
//            "{x} arcsec",   "1/(|{x}|√({x}²-1))",
//            "{x} arccot",   "-1/(1+{x}²)",
//            "{x} ln",             "1/{x}",
//            "{x} log",            "1/({x}*ln10)",
    };
    public static final String[] database1 = {
            "{0}",          "{0}{x}",
            "{x} {0} ^",    "(1/[{0}+1])*{x}^[{0}+1]",
            "e {x} ^",      "e^{x}",
            "{0} {x} * ln", "{x}(ln({0}{x})-1)",
            "{0} {x} /",      "{0}ln(abs({x}))",
            "{0} {x} ^",    "{0}^{x}*ln{0}",
            "{x} sec 2 ^",         "tan{x}",
            "{x} csc 2 ^",         "-1*cot{x}",
            "{x} sech 2 ^",        "tanh{x}",
            "{x} csch 2 ^",        "coth{x}",
    };

}
