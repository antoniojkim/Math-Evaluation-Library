package Math_Evaluation_Library.Miscellaneous;

import Math_Evaluation_Library.Constants.Scripts;
import Math_Evaluation_Library.Engine.Engine;
import Math_Evaluation_Library.Objects._Number_;
import Math_Evaluation_Library.Search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Antonio on 2017-07-11.
 */
public class Simplify {

    public static String simplify(String function){
        List<String> postfix = new ArrayList<>(Arrays.asList(Engine.toPostfix(function).split(" ")));
        try {
            simplifyPostfix(postfix);
            if (postfix.size() == 1) {
                return Search.replace(postfix.get(0), "η", "e");
            }
        } catch (IndexOutOfBoundsException | NullPointerException e){}
        String invalid = "";
        for (String str : postfix){
            invalid += str+" ";
        }
        postfix.clear();
        return "Invalid Syntax Parsing Error:  "+invalid;
    }

    public static void simplifyPostfix(List<String> postfix) throws ArrayIndexOutOfBoundsException, NullPointerException{
        if (postfix.size() == 1){
            return;
        }
        if (!rulesAdded){   addAllRules();  }
        String finalOperation = postfix.get(postfix.size()-1);
        if (Engine.startsWithOrder(finalOperation)){
            List<String> subExpression = new ArrayList<>(postfix.subList(0, postfix.size()-1));
            simplifyPostfix(subExpression);
            if (subExpression.size() == 1){
                postfix.clear();
                String term = subExpression.get(0);
                if (finalOperation.startsWith("abs")){
                    postfix.add("|"+term+"|");
                }
                else{
                    term = addBrackets(term);
                    if (term.charAt(0) != '(' && Engine.startsWithOrder(term)){
                        postfix.add(finalOperation+"("+term+")");
                    }
                    else {
                        postfix.add(finalOperation + term);
                    }
                }
                return;
            }
        }
        else if (finalOperation.length() == 1){
            if (applySimplificationRule(postfix)){
                simplifyPostfix(postfix);
                return;
            }

            int postfixLastIndex = postfix.size()-1;
            char operator = postfix.get(postfixLastIndex).charAt(0);
            int index = Engine.operatorIndex(operator);
            //  {a} {b} * {c} {d} / +           ab+c/d
            if (index != -1){
                int parameter1 = postfixLastIndex-1;
                for (int i = parameter1; parameter1 <= i && i >= 0; i--){
                    if (Engine.startsWithOrder(postfix.get(i))){
                        parameter1--;
                    }
                    else {
                        int index1 = Engine.operatorIndex(postfix.get(i));
                        if (index1 != -1){
                            parameter1 -= (Engine.singleOperator[index1] ? 1 : 2);
                        }
                    }
                }
                if (!Engine.singleOperator[index]){
                    int parameter2 = parameter1-1;
                    for (int i = parameter2; parameter2 <= i && i >= 0; i--){
                        if (Engine.startsWithOrder(postfix.get(i))){
                            parameter2--;
                        }
                        else {
                            int index1 = Engine.operatorIndex(postfix.get(i));
                            if (index1 != -1){
                                parameter2 -= (Engine.singleOperator[index1] ? 1 : 2);
                            }
                        }
                    }
//                    System.out.println(postfix+"  "+parameter2);
                    List<String> subExpression1 = new ArrayList<>(postfix.subList(parameter2, parameter1));
                    List<String> subExpression2 = new ArrayList<>(postfix.subList(parameter1, postfixLastIndex));

                    if (applySimplificationRule(subExpression1) || applySimplificationRule(subExpression2)){
                        postfix.clear();
                        postfix.addAll(subExpression1);
                        postfix.addAll(subExpression2);
                        postfix.add(String.valueOf(operator));
                        simplifyPostfix(postfix);
                        return;
                    }

                    simplifyPostfix(subExpression1);
                    simplifyPostfix(subExpression2);

                    if (subExpression1.size() == 1 && subExpression2.size() == 1){
                        postfix.clear();
                        String term1 = subExpression1.get(0);
                        String term2 = subExpression2.get(0);
                        switch(operator){
                            case '^':   postfix.add(exponentReassembleCases(term1, term2));                 return;
                            case 'P':   postfix.add(operatorReassembleCases("P", term1, term2));    return;
                            case 'C':   postfix.add(operatorReassembleCases("C", term1, term2));    return;
                            case '*':   postfix.add(multiplicationReassembleCases(term1, term2));           return;
                            case '/':   postfix.add(divisionReassembleCases(term1, term2));                 return;
                            case '%':   postfix.add(operatorReassembleCases("%", term1, term2));    return;
                            case '+':   postfix.add(additionReassembleCases(term1, term2));                 return;
                            case '-':   postfix.add(subtractionReassembleCases(term1, term2));              return;
                        }
                    }
                    if (subExpression1.size() == 1 && subExpression2.size() == 1){
                        postfix.clear();
                        String term1 = subExpression1.get(0), term2 = subExpression2.get(0);
                    }
                }
                else{
                    List<String> subExpression = postfix.subList(parameter1, postfix.size()-1);

                    simplifyPostfix(subExpression);

                    if (subExpression.size() == 1){
                        postfix.clear();
                        String term = subExpression.get(0);
                        switch(operator){
                            case '!':   postfix.add(postOperatorReassembleCases("!", term));       return;
                            case '°':   postfix.add(postOperatorReassembleCases("°", term));       return;
                            case 'ʳ':   postfix.add(postOperatorReassembleCases("ʳ", term));       return;
                        }
                    }
                }
            }
        }
    }

    public static boolean applySimplificationRule(List<String> postfix) throws ArrayIndexOutOfBoundsException, NullPointerException{
        int postfixLastIndex = postfix.size()-1;
        return sqrtSimplifyCases(postfix, postfixLastIndex) ||
                exponentSimplifyCases(postfix, postfixLastIndex) ||
                multiplicationSimplifyCases(postfix, postfixLastIndex) ||
                divisionSimplifyCases(postfix, postfixLastIndex) ||
                modulusSimplifyCases(postfix, postfixLastIndex) ||
                additionSimplifyCases(postfix, postfixLastIndex) ||
                subtractionSimplifyCases(postfix, postfixLastIndex) ||
                applyRules(postfix);
    }

    public static boolean sqrtSimplifyCases(List<String> postfix, int sIndex) throws ArrayIndexOutOfBoundsException, NullPointerException{
        if (postfix.get(sIndex).equals("√")) {
            postfix.set(sIndex, "^");
            postfix.add(sIndex, "0.5");
            return true;
        }
        return false;
    }
    public static boolean exponentSimplifyCases(List<String> postfix, int eIndex) throws ArrayIndexOutOfBoundsException, NullPointerException{
        if (postfix.get(eIndex).equals("^")){
            double num1 = _Number_.getNumber(postfix.get(eIndex-2), true), num2 = _Number_.getNumber(postfix.get(eIndex-1), true);
            if (_Number_.isNumber(num2)){
                if (_Number_.isNumber(num1)) {
                    // Case:  Both Numbers
                    double pow = Math.pow(num1, num2);
                    if (pow % 1 == 0) {
                        postfix.set(eIndex - 2, _Number_.format(pow));
                        remove(postfix, eIndex, eIndex - 1);
                        return true;
                    }
                }
                if (Engine.orderContains(postfix.get(eIndex-2)) && !postfix.get(eIndex-2).equals("abs")){
                    String superScript = Scripts.toSuperScript(postfix.get(eIndex-1));
                    if(superScript.length() == postfix.get(eIndex-1).length()){
                        postfix.set(eIndex-2, postfix.get(eIndex-2)+superScript);
                        remove(postfix, eIndex, eIndex-1);
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public static boolean multiplicationSimplifyCases(List<String> postfix, int mIndex) throws ArrayIndexOutOfBoundsException, NullPointerException{
        if (postfix.get(mIndex).equals("*")){
            double num1 = _Number_.getNumber(postfix.get(mIndex-2), true), num2 = _Number_.getNumber(postfix.get(mIndex-1), true);

            if (_Number_.isNumber(num2)) {
                if (_Number_.isNumber(num1)) {
                    postfix.set(mIndex - 2, _Number_.format(num1 * num2));
                    remove(postfix, mIndex, mIndex - 1);
                    return true;
                }
                postfix.add(0, postfix.get(mIndex-1));
                remove(postfix, mIndex);
                return true;
            }
            int numMultiplication = 0;
            for (int i = mIndex; i>=0; i--){
                if (postfix.get(i).equals("*")){
                    numMultiplication++;
                }
                else{
                    break;
                }
            }
            List<Double> numbers = new ArrayList<>();
            for (int i = 0; i<mIndex; i++){
                double num = _Number_.getNumber(postfix.get(i), true);
                if (_Number_.isNumber(num)){
                    numbers.add(num);
                }
                else{
                    break;
                }
            }
            int size = Math.min(numbers.size(), numMultiplication);
            if (size > 1){
                double product = 1;
                for (int i = 0; i<size; i++){
                    product *= numbers.get(i);
                }
                postfix.set(0, _Number_.format(product));
                for (int i = 1; i<size; i++){
                    postfix.remove(1);
                }
                int at = postfix.size()-size+1;
                for (int i = at; i<postfix.size();){
                    postfix.remove(at);
                }
                return true;
            }
        }
        return false;
    }
    public static boolean divisionSimplifyCases(List<String> postfix, int dIndex) throws ArrayIndexOutOfBoundsException, NullPointerException{
        if (postfix.get(dIndex).equals("/")){
            double num1 = _Number_.getNumber(postfix.get(dIndex-2), true), num2 = _Number_.getNumber(postfix.get(dIndex-1), true);
            // Case:  Both Numbers
            if (_Number_.isNumber(num1) && _Number_.isNumber(num2) && num1%num2 == 0){
                postfix.set(dIndex-2, _Number_.format(num1/num2));
                remove(postfix, dIndex, dIndex-1);
                return true;
            }
        }
        return false;
    }
    public static boolean modulusSimplifyCases(List<String> postfix, int dIndex) throws ArrayIndexOutOfBoundsException, NullPointerException{
        if (postfix.get(dIndex).equals("%")){
            double num1 = _Number_.getNumber(postfix.get(dIndex-2), true), num2 = _Number_.getNumber(postfix.get(dIndex-1), true);
            // Case:  Both Numbers
            if (_Number_.isNumber(num1) && _Number_.isNumber(num2)){
                postfix.set(dIndex-2, _Number_.format(num1%num2));
                remove(postfix, dIndex, dIndex-1);
                return true;
            }
        }
        return false;
    }
    public static boolean additionSimplifyCases(List<String> postfix, int aIndex) throws ArrayIndexOutOfBoundsException, NullPointerException{
        if (postfix.get(aIndex).equals("+")){
            double num1 = _Number_.getNumber(postfix.get(aIndex-2), true), num2 = _Number_.getNumber(postfix.get(aIndex-1), true);
            // Case:  Both Numbers
            if (_Number_.isNumber(num1) && _Number_.isNumber(num2)){
                postfix.set(aIndex-2, _Number_.format(num1+num2));
                remove(postfix, aIndex, aIndex-1);
                return true;
            }
        }
        return false;
    }
    public static boolean subtractionSimplifyCases(List<String> postfix, int sIndex) throws ArrayIndexOutOfBoundsException, NullPointerException{
        if (postfix.get(sIndex).equals("-")){
            double num1 = _Number_.getNumber(postfix.get(sIndex-2), true), num2 = _Number_.getNumber(postfix.get(sIndex-1), true);
            // Case:  Both Numbers
            if (_Number_.isNumber(num1) && _Number_.isNumber(num2)){
                postfix.set(sIndex-2, _Number_.format(num1-num2));
                remove(postfix, sIndex, sIndex-1); return true;
            }
        }
        return false;
    }

    public static String exponentReassembleCases(String term1, String term2) throws IndexOutOfBoundsException{
        double num = _Number_.getNumber(term2, true);
        if (_Number_.isNumber(num)) {
            if (num == 0.5) {
                return "√" + addBrackets(term1, '^');
            } else {
                String superScript = Scripts.toSuperScript(term2);
                if (superScript.length() == term2.length()) {
                    return addBrackets(term1, '^') + superScript;
                }
            }
        }
        return addBrackets(term1, '^')+"^"+addBrackets(term2, '^');
    }
    public static String multiplicationReassembleCases(String term1, String term2) throws IndexOutOfBoundsException{
        double num1 = _Number_.getNumber(term1, true);
        double num2 = _Number_.getNumber(term2, true);
        boolean isNum1 = _Number_.isNumber(num1);
        boolean isNum2 = _Number_.isNumber(num2);
        if (isNum1){
            if (isNum2){
                return _Number_.format(num1*num2);
            }
            if (num1 == 1){
                return term2;
            }
            if (num1 == -1){
                return "-"+term2;
            }
            return term1+addBrackets(term2, '*');
        }
        if (isNum2){
            if (num2 == 1){
                return term1;
            }
            if (num2 == -1){
                return "-"+term1;
            }
            return term2+addBrackets(term1, '*');
        }
        term1 = addBrackets(term1, '*');
        term2 = addBrackets(term2, '*');
        if (Engine.checkImplicitContains(term2) || (term1.charAt(term1.length()-1) == '|')){
            return term1+term2;
        }
        return term1+"*"+term2;
    }
    public static String divisionReassembleCases(String term1, String term2) throws IndexOutOfBoundsException{
        return addBrackets(term1, '/')+"/"+addBrackets(term2, '/');
    }
    public static String additionReassembleCases(String term1, String term2) throws IndexOutOfBoundsException{
        if (term2.charAt(0) == '-'){
            return term1+"-"+term2.substring(1);
        }
        else if (term1.charAt(0) == '-'){
            return term2+"-"+term1.substring(1);
        }
        return term1+"+"+term2;
    }
    public static String subtractionReassembleCases(String term1, String term2) throws IndexOutOfBoundsException{
        if (term2.charAt(0) == '-'){
            if (term1.charAt(0) == '-'){
                return term2.substring(1)+"-"+term1.substring(1);
            }
            return term1+"+"+term2.substring(1);
        }
        return term1+"-"+term2;
    }
    public static String operatorReassembleCases(String operator, String term1, String term2) throws IndexOutOfBoundsException{
        return addBrackets(term1) + operator + addBrackets(term2);
    }
    //    public static void prefixOperatorReassembleCases(String operator, List<String> postfix, int oIndex) throws IndexOutOfBoundsException{
//        String term = postfix.get(oIndex-1);
//        term  = (_Number_.isNumber(term ) || term .equals(Engine.var) || term .equals(Engine.varOp)
//                || Search.getIndices(term , "+-*/%PC").size() == 0 ? term  : "("+term +")");
//        postfix.set(oIndex - 2, operator + term );
//        remove(postfix, oIndex, oIndex - 1);
//    }
    public static String postOperatorReassembleCases(String operator, String term) throws IndexOutOfBoundsException{
        return addBrackets(term) + operator;
    }

    public static String addBrackets(String term){
        return addBrackets(term, '\0');
    }
    public static String addBrackets(String term, char operator){
        switch(operator){
            case '*':    return ((_Number_.isNumber(term) || term.equals(Engine.var) || term.equals(Engine.varOp) || !Search.functionContains(term, "+-/%PC"))  ? term : "("+term+")");
            case '+':    return term;
            case '-':    return term;
            default:     return ((_Number_.isNumber(term) || term.equals(Engine.var) || term.equals(Engine.varOp) || !Search.functionContains(term, "+-*/%PC")) ? term : "("+term+")");
        }
    }

    public static void remove(List<String> list, Integer... remove){
        for(Integer i : remove){
            try{
                list.remove(i.intValue());
            }catch (IndexOutOfBoundsException e){}
        }
    }


    private static boolean rulesAdded = false;

    private static final int EXPONENT = 0;
    private static final int MULTIPLICATION = 1;
    private static final int DIVISION = 2;
    private static final int ADDITION = 3;
    private static final int SUBTRACTION = 4;
    private static final int LN = 5;
    private static final int LOG = 6;

    private static final int numSets = 7;

    private static List<List<String[]>> rules;
    private static List<List<String[]>> replacements;
    private static List<List<List<String>>> keys;
    private static List<List<List<Integer>>> values;

    public static void addAllRules(){
        rulesAdded = true;
        for (int i = 0; i<exponentRules.length; i+=2){
            addRule(exponentRules[i], exponentRules[i+1]);
        }
        for (int i = 0; i<multiplicationRules.length; i+=2){
            addRule(multiplicationRules[i], multiplicationRules[i+1]);
        }
        for (int i = 0; i<divisionRules.length; i+=2){
            addRule(divisionRules[i], divisionRules[i+1]);
        }
        for (int i = 0; i<additionRules.length; i+=2){
            addRule(additionRules[i], additionRules[i+1]);
        }
        for (int i = 0; i<subtractionRules.length; i+=2){
            addRule(subtractionRules[i], subtractionRules[i+1]);
        }
        for (int i = 0; i<lnCases.length; i+=2){
            addRule(lnCases[i], lnCases[i+1]);
        }
        for (int i = 0; i<logCases.length; i+=2){
            addRule(logCases[i], logCases[i+1]);
        }
    }
    public static void addRule(String rule, String replace){
        if (rules == null || replacements == null || keys == null || values == null){
            rules = new ArrayList<>();
            replacements = new ArrayList<>();
            keys = new ArrayList<>();
            values = new ArrayList<>();
            for (int i = 0; i<numSets; i++){
                rules.add(new ArrayList<>());
                replacements.add(new ArrayList<>());
                keys.add(new ArrayList<>());
                values.add(new ArrayList<>());
            }
        }
        String[] ruleSet = rule.split(" ");
        String finalOperation = ruleSet[ruleSet.length-1];
        int index = -1;
        switch(finalOperation){
            case "^":   index = EXPONENT;           break;
            case "*":   index = MULTIPLICATION;     break;
            case "/":   index = DIVISION;           break;
            case "+":   index = ADDITION;           break;
            case "-":   index = SUBTRACTION;        break;
            case "ln":  index = LN;                 break;
            case "log": index = LOG;                break;
            default:    break;
        }
        if (index != -1){
            String[] replacementSet = replace.split(" ");
            List<String> keySet = new ArrayList<>();
            List<Integer> valueSet = new ArrayList<>();
            for (int i = 0; i<ruleSet.length; i++){
                try {
                    if (ruleSet[i].charAt(0) == '{' && ruleSet[i].charAt(ruleSet[i].length()-1) == '}' && !keySet.contains(ruleSet[i])){
                        keySet.add(ruleSet[i]);
                        valueSet.add(i-ruleSet.length);
                    }
                } catch (IndexOutOfBoundsException e){}
            }
            rules.get(index).add(ruleSet);
            replacements.get(index).add(replacementSet);
            keys.get(index).add(keySet);
            values.get(index).add(valueSet);
        }
    }

    public static boolean applyRules(List<String> postfix) throws IndexOutOfBoundsException{
        String finalOperation = postfix.get(postfix.size()-1);
        int index = -1;
        switch(finalOperation){
            case "^":   index = EXPONENT;           break;
            case "*":   index = MULTIPLICATION;     break;
            case "/":   index = DIVISION;           break;
            case "+":   index = ADDITION;           break;
            case "-":   index = SUBTRACTION;        break;
            case "ln":  index = LN;                 break;
            case "log": index = LOG;                break;
            default:    break;
        }
        if (index != -1){
            List<String[]> ruleSet = rules.get(index);
            List<String[]> replacementSet = replacements.get(index);
            List<List<String>> keySet = keys.get(index);
            List<List<Integer>> valueSet = values.get(index);

            RULES:for (int i = 0; i<ruleSet.size(); i++){

                if (postfix.size() >= ruleSet.get(i).length){

                    String[] rule = Arrays.copyOf(ruleSet.get(i), ruleSet.get(i).length);
                    String[] replacement = Arrays.copyOf(replacementSet.get(i), replacementSet.get(i).length);
                    List<String> ruleKeys = keySet.get(i);
                    List<Integer> ruleValues = valueSet.get(i);

                    for (int j = 0; j<ruleKeys.size(); j++){
                        String value = postfix.get(postfix.size()+ruleValues.get(j));
                        if ((_Number_.isNumber(ruleKeys.get(j).charAt(1)) && !_Number_.isNumber(value)) ||
                                 Engine.operatorContains(value) ||
                                (Engine.orderContains(value) && ruleKeys.get(j).charAt(1) != 'f')){
                            continue RULES;
                        }
                        else{
                            replaceAll(rule, ruleKeys.get(j), value);
                            replaceAll(replacement, ruleKeys.get(j), value);
                        }
                    }

                    int difference = postfix.size()-rule.length;

                    boolean equals = true;
                    for (int j = 0; j<rule.length; j++){
                        if (!postfix.get(difference+j).equals(rule[j])){
                            equals = false;
                            break;
                        }
                    }

                    if (equals){
                        for (int j = 0; j<replacement.length; j++){
                            postfix.set(j+difference, replacement[j]);
                        }
                        for (int j = postfix.size()-1; j>=(difference+replacement.length); j--){
                            postfix.remove(j);
                        }
                        return true;
                    }

                }
            }
        }
        return false;
    }

    public static String[] replaceAll(String[] array, String regex, String replace){
        for (int i = 0; i<array.length; i++){
            if (array[i].equals(regex)){
                array[i] = replace;
            }
        }
        return array;
    }

    public static String[] exponentRules = {
            "{x} 0 ^", 					"1",
            "0 {x} ^", 					"0",
            "{x} 1 ^", 					"{x}",
            "{x} -1 ^", 				"1 {x} /",
            "1 {x} ^", 					"1",
            "{x} {0} ^ {1} ^", 			"{x} {0} {1} * ^"
    };
    public static String[] multiplicationRules = {
            "1 {x} *", 					"{x}",
            "{x} 1 *", 					"{x}",
            "{x} {0} *", 				"{0} {x} *",
            "{x} {f} {x} {f} *", 		"{x} {f} 2 ^",
            "{0} {x} {f} * {x} {f} *", 	"{0} {x} {f} 2 ^ *",
            "{x} {x} *", 				"{x} 2 ^",
            "{x} {x} *", 				"{x} 2 ^",
            "{x} {0} * {1} *", 			"{0} {1} * {x} *",
            "{0} {x} * {1} *", 			"{0} {1} * {x} *",
            "{0} x {1} * *", 			"{0} {1} * x *",
            "* {0} * {1} *", 			"* {0} {1} * *",
            "{x} {y} / {z} *", 			"{z} {x} * {y} /",
            "{x} {y} {z} / *",	        "{x} {y} * {z} /",
            "{x} √ {y} √ *", 			"{x} {y} * √",
            "{x} tan {x} cos *",		"{x} sin",
            "{x} cos {x} tan *",		"{x} sin",
            "{x} cot {x} sin *",		"{x} cos",
            "{x} sin {x} cot *",		"{x} cos",
            "{x} {1} ^ {x} {2} ^ *",	"{x} {1} {2} + ^"
    };
    public static String[] divisionRules = {
            "{x} {x} /", 			"1",
            "{x} sin {x} cos /",	"{x} tan",
            "{x} cos {x} sin /",	"{x} cot",
            "{0} {x} sin /",		"{0} {x} csc *",
            "{0} {x} cos /",		"{0} {x} sec *",
            "{0} {x} tan /",		"{0} {x} cot *",
            "{0} {x} csc /",		"{0} {x} sin *",
            "{0} {x} sec /",		"{0} {x} cos *",
            "{0} {x} cot /",		"{0} {x} tan *"
    };
    public static String[] additionRules = {
            "0 {x} +", 						            "{x}",
            "{x} 0 +", 						            "{x}",
            "{x} {x} +", 					            "2 {x} *",
            "{0} {x} +", 					            "{x} {0} +",
            "{0} {x} * {1} {x} +", 			            "{0} {1} + {x} *",
            "{0} ln {1} ln +", 				            "{0} {1} * ln",
            "{x} sin 2 ^ {x} cos 2 ^ +",	            "1",
            "{0} {x} sin 2 ^ * {0} {x} cos 2 ^ * +",	"{0}"
    };
    public static String[] subtractionRules = {
            "{x} {x} -",	        "0",
            "{x} 0 -", 		        "{x}",
            "0 {x} -", 		        "-1 {x} *",
            "{0} {x} * {1} {x} +",  "{0} {1} + {x} *"
    };
    public static String[] lnCases = {
            "e ln", 			"1",
            "{0} {1} ^ ln",		"{1} {0} ln *",
    };
    public static String[] logCases = {
            "10 log", 			"1",
            "{0} {1} ^ log",	"{1} {0} log *",
    };

}
