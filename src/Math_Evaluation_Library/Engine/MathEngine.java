package Math_Evaluation_Library.Engine;

import Math_Evaluation_Library.Constants.Scripts;
import Math_Evaluation_Library.LinearAlgebra._Matrix_;
import Math_Evaluation_Library.Miscellaneous.Simplify;
import Math_Evaluation_Library.Objects.*;

import java.util.ArrayList;
import java.util.List;

import static Math_Evaluation_Library.Engine.MultiParamFunctions.*;
import static Math_Evaluation_Library.Engine.Operators.*;

/**
 * Created by Antonio on 2017-07-23.
 */
public class MathEngine extends Engine{


    public static String evaluate(List<String> list){
        if ((list.contains(var) || list.contains(varOp)) && !list.contains("∑") && !list.contains("∏") && !list.contains("∫")){
            return "Invalid Input Error - Unresolved variable";
        }
        List<String> outputs = new ArrayList<>();
        outputs.addAll(list);
        ITERATOR: for (int a = 0; a < outputs.size(); a++) {
            if (!_Number_.isNumber(outputs.get(a))){
                try {
                    for (VarFunction variableFunction : variableFunctions) {
                        if (outputs.get(a).equals(variableFunction.getName())) {
                            String arg = outputs.get(a+1);
                            outputs.set(a, variableFunction.evaluate(arg));
                            outputs.remove(a+1);
                            a = -1;
                            continue ITERATOR;
                        }
                    }
                    if (UnaryFunctions.unaryFunctionsContains(outputs.get(a))) {
                        String function = outputs.get(a);
                        int index = Scripts.getSuperScriptIndex(function.substring(function.length()-1));
                        int n = 1;
                        if (index != -1){
                            n = index;
                            function = function.substring(0, function.length()-1);
                        }
                        UnaryFunction unary = UnaryFunctions.unaryFunctions[UnaryFunctions.unaryFunctionsIndex(function)];
                        double value = unary.evaluate( _Number_.getNumber(outputs.get(a - 1)));
                        outputs.set(a-1, "" + Math.pow(value, n));
                        outputs.remove(a);
                        a = -1;
                    }
                    else if (operatorContains(outputs.get(a))) {
                        Operator operator = operators[operatorIndex(outputs.get(a))];
                        if (operator.isSingleOperator()){
                            outputs.set(a - 1, operator.evaluate(_Number_.getNumber(outputs.get(a - 1))));
                            outputs.remove(a);
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
                                outputs.set(a - 2, operator.evaluate(_Number_.getNumber(outputs.get(a-2)), _Number_.getNumber(outputs.get(a-1))));
                                Simplify.remove(outputs, a, a-1);
                            }
                        }
                        a = -1;
                    }
                    else if (isMultiParamFunction(outputs.get(a))){
                        MultiParamFunction multiParamFunction = multiParamFunctions[multiParamFunctionNamesIndex(outputs.get(a))];
                        if (multiParamFunction.isFlexible()){
                            int numInputs = Integer.parseInt(outputs.get(a + 1));
                            String[] parameters = new String[numInputs];
                            for (int i = 0; i<parameters.length; i++){
                                parameters[i] = outputs.get(a+2+i);
                            }
                            String result = multiParamFunction.evaluate(parameters);
                            if (result.charAt(0) == MultiParamFunctions.INVALID.charAt(0)){
                                return "Invalid Input Error - Invalid Parameters for "+multiParamFunction.getName();
                            }
                            outputs.set(a, result);
                            for (int b = 0; b <= numInputs; b++){
                                outputs.remove(a+1);
                            }
                        }
                        else{
                            String[] parameters = new String[multiParamFunction.getNumParameters()];
                            for (int i = 0; i<parameters.length; i++){
                                parameters[i] = outputs.get(a+1+i);
                            }
                            String result = multiParamFunction.evaluate(parameters);
                            if (result.charAt(0) == MultiParamFunctions.INVALID.charAt(0)){
                                return "Invalid Input Error - Invalid Parameters for "+multiParamFunction.getName();
                            }
                            outputs.set(a, result);
                            for (int b = 0; b < multiParamFunction.getNumParameters(); b++){
                                outputs.remove(a+1);
                            }
                        }
                        a = -1;
                    }
                    else if (outputs.get(a).contains("∞")){ }
                    else if (outputs.get(a).charAt(0) == '{' || outputs.get(a).charAt(0) == '['){
                        char last = outputs.get(a).charAt(outputs.get(a).length()-1);
                        if (last == 'ᵀ' || last == 'ᴵ'){
                            outputs.set(a, _Matrix_.toStrMatrix(_Matrix_.toDoubleMatrix(outputs.get(a))));
                        }
                    }
                    else if (outputs.size() > 1){
                        return "Invalid Input Error - Unrecognized character(s):  "+outputs.get(a);
                    }
                } catch (NumberFormatException | ArithmeticException | IndexOutOfBoundsException e3) {
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

}
