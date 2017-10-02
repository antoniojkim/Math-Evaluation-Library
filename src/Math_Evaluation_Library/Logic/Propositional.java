package Math_Evaluation_Library.Logic;

import Math_Evaluation_Library.Miscellaneous.Simplify;
import Math_Evaluation_Library.Print;
import Math_Evaluation_Library.Search;
import Math_Evaluation_Library.Sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * Created by Antonio on 2017-09-19.
 */
public class Propositional {

//    public static void main (String[] args){
//        String[] propositional = {"P∧Q∨R",
//                "((((P∧Q)→T)∧(P→Q))→(P→T))",
//                ""};
//        for (String proposition : propositional) {
//            System.out.println(fixSyntax(proposition));
//            System.out.println(valuate(proposition, true, true, true));
//        }
//    }

    public static String error = "Parse Error";
    public static boolean sorted = false;

    public static String fixSyntax(String function){
        function = function.toLowerCase();
        return Search.replace(function, new String[][]{
                {" ", ""}, {"not", "¬"}, {"and", "∧"}, {"or", "∨"},
                {"iff", "↔"}, {"<->", "↔"}, {"->", "→"}, {"down", "↓"}, {"d->", "↓"}, {"!^", "↓"}
        });
    }

    public static void sortFunctions(){
        Sort.quicksort(operators, singleOperator);
        sorted = true;
    }



    public static String valuate(String proposition, boolean... values){
        proposition = fixSyntax(proposition);
        String postfixStr = toPostfix(proposition);
        List<String> postfix = new ArrayList<>(Arrays.asList(Search.split(postfixStr, " ")));
//        Print.println(postfix);
        List<String> variables = new ArrayList<>();
        for(String term : postfix){
            if (!term.equalsIgnoreCase("T") && !term.equalsIgnoreCase("F") && !operatorContains(term)){
                if (variables.isEmpty()){
                    variables.add(term);
                }
                else{
                    int index = Search.binarySearchIndex(variables, term);
                    if (index == variables.size()){
                        variables.add(term);
                    }
                    else if (!variables.get(index).equals(term)){
                        variables.add(index, term);
                    }
                }
            }
        }
        if (variables.size() > values.length){
            return "Missing Propositional Variable Values";
        }
        for (int i = 0; i<variables.size(); i++){
            postfixStr = Search.replace(postfixStr, variables.get(i), (values[i] ? "T" : "F"));
        }
        postfix.clear();
        postfix.addAll(Arrays.asList(Search.split(postfixStr, " ")));
        for (int i = 0; i<postfix.size(); i++){
            int index = operatorIndex(postfix.get(i));
            if (index != -1){
                if (singleOperator[index] && i > 0){
                    boolean term = postfix.get(i-1).equalsIgnoreCase("T");
                    switch(postfix.get(i)){
                        case "¬":
                            postfix.set(i-1, !term ? "T" : "F");
                            Simplify.remove(postfix, i);    i -= 1;
                            break;
                        default:    break;
                    }
                }
                else if (!singleOperator[index] && i > 1){
                    boolean term1 = postfix.get(i-2).equalsIgnoreCase("T");
                    boolean term2 = postfix.get(i-1).equalsIgnoreCase("T");
                    switch(postfix.get(i)){
                        case "∧":
                            postfix.set(i-2, (term1 && term2) ? "T" : "F");
                            Simplify.remove(postfix, i, i-1);    i -= 2;
                            break;
                        case "∨":
                            postfix.set(i-2, (term1 || term2) ? "T" : "F");
                            Simplify.remove(postfix, i, i-1);    i -= 2;
                            break;
                        case "→":
                            postfix.set(i-2, (!term1 || term2) ? "T" : "F");
                            Simplify.remove(postfix, i, i-1);    i -= 2;
                            break;
                        case "↔":
                            postfix.set(i-2, ((!term1 || term2) && (!term2 || term1)) ? "T" : "F");
                            Simplify.remove(postfix, i, i-1);    i -= 2;
                            break;
                        case "↓":
                            postfix.set(i-2, (!term1 && !term2) ? "T" : "F");
                            Simplify.remove(postfix, i, i-1);    i -= 2;
                            break;
                        default:    break;
                    }
                }
                else{
                    return "Operator Error:  "+postfixStr;
                }
            }
        }
        if (postfix.size() == 1){
            return postfix.get(0);
        }
        else{
            System.out.println("Postfix size > 0");
            Print.println(postfix);
        }
        return "";
    }

    static char[] operators = {'¬', '∧', '∨', '→', '↔', '↓'};
    public static boolean[] singleOperator = {true, false, false, false, false, false};

    public static String toPostfix(String infixFunction) {
//        if (!sorted){
//            sortFunctions();
//        }
        infixFunction = fixSyntax(infixFunction);
        Stack<String> output = new Stack<>();
        Stack<String> stack = new Stack<>();
        String token = "";
        PARSE:  for (int a = 0; a < infixFunction.length(); a++) {
            char c = infixFunction.charAt(a);
            try {
                if (c == '(') {
                    if (token.length() > 0){
                        output.push(token);
                        token = "";
                    }
                    stack.push("(");
                    if (infixFunction.indexOf(")", a + 1) == -1) {
                        error = "Bracket Count Error:  Missing ')' Bracket";
                        return error;
                    }
                    continue PARSE;
                } else if (c == ')') {
                    if (token.length() > 0){
                        output.push(token);
                        token = "";
                    }
                    if (stack.contains("(")) {
                        while (!stack.isEmpty()) {
                            String str = stack.pop();
                            if (str.equals("(")) {
                                break;
                            }
                            output.push(str);
                        }
                    } else {
                        error = "Bracket Count Error:  Missing '(' Bracket";
                        return error;
                    }
                    continue PARSE;
                }
                int o1 = -1;
                if (a+1 <= infixFunction.length()){
                    o1 = operatorIndex(c);
                }
                if (o1 != -1){
                    if (token.length() > 0){
                        output.push(token);
                        token = "";
                    }
                    while (!stack.isEmpty() && operatorIndex(stack.peek()) == -1 && !stack.peek().equals("(")) {
                        output.push(stack.pop());
                    }
                    if (stack.isEmpty() || operatorIndex(stack.peek()) == -1) {
                        stack.push(String.valueOf(operators[o1]));
                    } else {
                        while (true) {
                            if (stack.empty()) {
                                stack.push(String.valueOf(operators[o1]));
                                break;
                            }
//                                int o2 = operatorIndex(stack.peek());
//                                if (o2 >= 0 && ((associability[o1] && precedence[o1] <= precedence[o2]) || (!associability[o1] && precedence[o1] < precedence[o2]))) {
//                                    output.push(stack.pop());
//                                } else {
                            stack.push(String.valueOf(operators[o1]));
                            break;
//                                }
                        }
                    }
                }
                else{
                    token += String.valueOf(c);
                }
            } catch (NumberFormatException | StringIndexOutOfBoundsException e2) {
                error = "Invalid Input Error 2";
                return error;
            }
        }
        if (token.length() > 0) {
            output.push(token);
            token = "";
        }
        while (!stack.empty()) {
            output.push(stack.pop());
        }
        for (int a = 0; a < output.size(); a++) {
            token += output.get(a) + " ";
        }
        return token.trim();
    }



    public static int operatorIndex(String item){
        if (!sorted){
            sortFunctions();
        }
        return (item.length() == 1 ? Search.binarySearch(operators, item.charAt(0)) : -1);
    }
    public static int operatorIndex(char item){
        if (!sorted){
            sortFunctions();
        }
        return Search.binarySearch(operators, item);
    }
    public static boolean operatorContains(String item){
        if (!sorted){
            sortFunctions();
        }
        return (item.length() == 1 ? Search.contains(operators, item.charAt(0)) : false);
    }
    public static boolean operatorContains(char item){
        if (!sorted){
            sortFunctions();
        }
        return Search.contains(operators, item);
    }

}
