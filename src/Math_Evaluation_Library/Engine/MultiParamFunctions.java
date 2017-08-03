package Math_Evaluation_Library.Engine;

import Math_Evaluation_Library.Calculus.Derivative;
import Math_Evaluation_Library.Calculus.Integral;
import Math_Evaluation_Library.Calculus.Roots;
import Math_Evaluation_Library.Geometry.Geometric;
import Math_Evaluation_Library.Miscellaneous.Mod;
import Math_Evaluation_Library.Miscellaneous._Random_;
import Math_Evaluation_Library.Objects.Function;
import Math_Evaluation_Library.Objects._Number_;
import Math_Evaluation_Library.Statistics.Stats;
import Math_Evaluation_Library.UnitConversion._UnitConversion_;

/**
 * Created by Antonio on 2017-07-22.
 */
public class MultiParamFunctions extends Engine{

    public static final String INVALID = "!";

    public static String evaluate(String function, String[] parameters) throws NumberFormatException{
        return evaluate(function, parameters, true);
    }
    public static String evaluate(String function, String[] parameters, boolean flexible) throws NumberFormatException{
        if (flexible){
            switch (function){
                case "logab":   return log(parameters);
                case "max":     return max(parameters);
                case "min":     return min(parameters);
                case "gcd":     return gcd(parameters);
                case "lcm":     return lcm(parameters);
                case "avg":     return average(parameters);
                case "ED":      return euclideanDistance(parameters);
                case "var":     return variance(parameters);
                case "stndv":   return standardDeviation(parameters);
                case "heron":   return heron(parameters);
                default:        break;
            }
        }
        else{
            switch (function){
                case "random":     return random(parameters);
                case "randint":    return randint(parameters);
                case "unit":       return unitConversion(parameters);
                case "nint":       return nint(parameters);
                case "dx":         return dx(parameters);
                case "dxn":        return dxn(parameters);
                case "sum":        return sum(parameters);
                case "product":    return product(parameters);
                case "newton":     return newton(parameters);
                case "c_law":      return cosineLaw(parameters);
                case "riemann":    return riemann(parameters);
                case "elasd":      return elasd(parameters);
                default:           break;
            }
        }
        return INVALID;
    }

    // Flexible number of arguments
    private static String log(String[] parameters){
        if (parameters.length == 1){
            return _Number_.removeEnding0(  Math.log10(evaluate(parameters[0])) );
        }
        else if (parameters.length == 2){
            return _Number_.removeEnding0(  Math.log10(evaluate(parameters[0]))/Math.log10(evaluate(parameters[1])));
        }
        return INVALID;
    }
    private static String max(String[] parameters){
        if (parameters.length > 0){
            double largest = evaluate(parameters[0]);
            for (int b = 1; b<parameters.length; b++){
                largest = Math.max(largest, evaluate(parameters[b]));
            }
            return _Number_.removeEnding0(largest);
        }
        return INVALID;
    }
    private static String min(String[] parameters){
        if (parameters.length > 0){
            double smallest = evaluate(parameters[0]);
            for (int b = 1; b<parameters.length; b++){
                smallest = Math.min(smallest, evaluate(parameters[b]));
            }
            return _Number_.removeEnding0(smallest);
        }
        return INVALID;
    }
    private static String gcd(String[] parameters){
        if (parameters.length == 1){
            return parameters[0];
        }
        else if (parameters.length > 1){
            double temp1 = evaluate(parameters[0]);
            if (temp1%1 == 0){
                double gcd = temp1;
                for (int b = 1; b<parameters.length; b++){
                    double temp = evaluate(parameters[b]);
                    if (temp%1 == 0){
                        gcd = Mod.gcd(gcd, temp);
                    }
                    else{
                        return INVALID;
                    }
                }
                return _Number_.removeEnding0(gcd);
            }
        }
        return INVALID;
    }
    private static String lcm(String[] parameters){
        if (parameters.length == 1){
            return parameters[0];
        }
        else if (parameters.length > 1){
            double temp1 = evaluate(parameters[0]);
            if (temp1%1 == 0){
                int lcm = (int)temp1;
                for (int b = 1; b<parameters.length; b++){
                    double temp = evaluate(parameters[b]);
                    if (temp%1 == 0){
                        lcm = (int) Mod.lcm(lcm, (int)temp);
                    }
                    else{
                        return INVALID;
                    }
                }
                return _Number_.removeEnding0(lcm);
            }
        }
        return INVALID;
    }
    private static String heron(String[] parameters){
        if (parameters.length == 3){
            double A = evaluate(parameters[0]);
            double B = evaluate(parameters[1]);
            double C = evaluate(parameters[2]);
            double s = (A+B+C) / 2;
            return _Number_.removeEnding0(Math.sqrt(s*(s-A)*(s-B)*(s-C)));
        }
        else if (parameters.length == 6){
            double x1 = evaluate(parameters[0]);
            double y1 = evaluate(parameters[1]);
            double x2 = evaluate(parameters[2]);
            double y2 = evaluate(parameters[3]);
            double x3 = evaluate(parameters[4]);
            double y3 = evaluate(parameters[5]);
            double A = Geometric.pointDistance(x1, y1, x2, y2);
            double B = Geometric.pointDistance(x1, y1, x3, y3);
            double C = Geometric.pointDistance(x2, y2, x3, y3);
            double s = (A+B+C) / 2;
            return _Number_.removeEnding0(Math.sqrt(s*(s-A)*(s-B)*(s-C)));
        }
        return INVALID;
    }
    private static String euclideanDistance(String[] parameters){
        if (parameters.length == 1){
            return parameters[0];
        }
        else if (parameters.length > 1){
            double ED = 0;
            for (String parameter : parameters){
                double temp = evaluate(parameter);
                ED += temp*temp;
            }
            return _Number_.removeEnding0(Math.sqrt(ED));
        }
        return INVALID;
    }
    private static String average(String[] parameters){
        if (parameters.length > 0){
            double sum = 0;
            for (String parameter : parameters){
                sum += evaluate(parameter);
            }
            sum /= parameters.length;
            return _Number_.removeEnding0(sum);
        }
        return INVALID;
    }
    private static String variance(String[] parameters){
        if (parameters.length > 1){
            double[] data = new double[parameters.length];
            for (int b = 0; b<data.length; b++){
                data[b] = evaluate(parameters[b]);
            }
            return _Number_.removeEnding0(Stats.variance(data));
        }
        return INVALID;
    }
    private static String standardDeviation(String[] parameters){
        if (parameters.length > 1){
            double[] data = new double[parameters.length];
            for (int b = 0; b<data.length; b++){
                data[b] = evaluate(parameters[b]);
            }
            return _Number_.removeEnding0(Stats.stnDev(data));
        }
        return INVALID;
    }
    private static String riemann(String[] parameters){
        if (parameters.length == 4 || parameters.length == 5){
            Function f = new Function(parameters[0]);
            int start = Integer.parseInt(parameters[1]);
            int end = Integer.parseInt(parameters[2]);
            int n = Integer.parseInt(parameters[3]);
            String riemann = (parameters.length == 5 ? parameters[4] : "m");
            double h = (end-start)/(double)n;

            double sum = 0;
            if (riemann.equals("l")){
                for (int b = 0; b < n; b++) {
                    sum += f.of(start+h*b);
                }
            }
            else if (riemann.equals("r")){
                for (int b = 1; b <= n; b++) {
                    sum += f.of(start+h*b);
                }
            }
            else {
                for (int b = 0; b <= n-1; b++) {
                    sum += f.of(start+h*(2*b+1)/2.0);
                }
            }
            return _Number_.removeEnding0(sum*h);
        }
        return INVALID;
    }

    // Non-flexible number of arguments
    private static String random(String[] parameters){
        if (parameters.length == 2){
            double low = evaluate(parameters[0]);
            double high = evaluate(parameters[1]);
            return _Number_.removeEnding0(_Random_.random(low, high));
        }
        return INVALID;
    }
    private static String randint(String[] parameters) throws NumberFormatException{
        if (parameters.length == 2){
            int low = Integer.parseInt(parameters[0]);
            int high = Integer.parseInt(parameters[1]);
            return _Number_.removeEnding0(_Random_.randomint(low, high));
        }
        return INVALID;
    }
    private static String unitConversion(String[] parameters) {
        if (parameters.length == 3){
            double measure = _Number_.getNumber(parameters[0]);
            String unit1 = parameters[1];
            String unit2 = parameters[2];
            double converted = _UnitConversion_.convert(measure, unit1, unit2);
            if (_Number_.isNumber(converted)){
                return String.valueOf(converted);
            }
        }
        return INVALID;
    }
    private static String elasd(String[] parameters){
        if (parameters.length == 4){
            double q1 = evaluate(parameters[0]);
            double q2 = evaluate(parameters[1]);
            double p1 = evaluate(parameters[2]);
            double p2 = evaluate(parameters[3]);
            return _Number_.removeEnding0((((q2 - q1) / (q2 + q1)) / ((p2 - p1) / (p2 + p1))));
        }
        return INVALID;
    }
    private static String nint(String[] parameters){
        if (parameters.length == 3){
            String fx = parameters[0];
            double lower = evaluate(parameters[1]);
            double higher = evaluate(parameters[2]);
            return _Number_.removeEnding0(Integral.nint(fx, lower, higher));
        }
        return INVALID;
    }
    private static String dx(String[] parameters){
        if (parameters.length == 2){
            String fx = parameters[0];
            double x = evaluate(parameters[1]);
            return _Number_.removeEnding0(Derivative.value(fx, x));
        }
        return INVALID;
    }
    private static String dxn(String[] parameters) throws NumberFormatException{
        if (parameters.length == 3){
            String fx = parameters[0];
            double x = evaluate(parameters[1]);
            int n = Integer.parseInt(parameters[2].trim());
            String[] derivatives = new String[n];
            derivatives[0] = Derivative.calculate(fx);
            for (int b = 1; b<derivatives.length; b++){
                derivatives[b] = Derivative.calculate(derivatives[b-1]);
            }
            String finalDerivative = derivatives[derivatives.length-1];
            if (finalDerivative.charAt(finalDerivative.length()-1) != '\''){
                return _Number_.removeEnding0(evaluate(derivatives[derivatives.length-1], x));
            }
        }
        return INVALID;
    }
    private static String sum (String[] parameters){
        if (parameters.length == 3){
            Function f = new Function(parameters[0]);
            int start = Integer.parseInt(parameters[1]);
            int end = Integer.parseInt(parameters[2]);
            double sum = 0;
            for (int b = start; b <= end; b++) {
                sum += f.of(b);
            }
            return _Number_.removeEnding0(sum);
        }
        return INVALID;
    }
    private static String product(String[] parameters){
        if (parameters.length == 3){
            Function f = new Function(parameters[0]);
            int start = Integer.parseInt(parameters[1]);
            int end = Integer.parseInt(parameters[2]);
            double product = 1;
            for (int b = start; b <= end; b++) {
                product *= f.of(b);
            }
            return _Number_.removeEnding0(product);
        }
        return INVALID;
    }
    private static String newton(String[] parameters){
        if (parameters.length == 2){
            String fx = parameters[0];
            double x = evaluate(parameters[1]);
            return _Number_.removeEnding0(Roots.NewtonsMethod(fx, x));
        }
        return INVALID;
    }
    private static String cosineLaw(String[] parameters){
        if (parameters.length == 3){
            double b = evaluate(parameters[0]);
            double c = evaluate(parameters[1]);
            double theta = evaluate(parameters[2]);
            return _Number_.removeEnding0(Math.sqrt(b*b+c*c-2*b*c*Math.cos(theta)));
        }
        return INVALID;
    }



}
