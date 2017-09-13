package Math_Evaluation_Library.Constants;

import Math_Evaluation_Library.Search;
import Math_Evaluation_Library.Sort;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Antonio on 2017-08-19.
 */
public class GreekLetters {

    private static boolean sorted = false;

    public static final int NUM_GREEK_CAPITAL = 24;
    public static final int NUM_GREEK_LOWERCASE = 24;
    public static final int NUM_GREEK_VARIANTS = 6;

    public static String[][] greekLetterPairs = {
        {"alpha", "α"},   {"Alpha", "Α"},   {"beta", "β"}, {"Beta", "Β"},   {"gamma", "γ"},  {"Gamma", "Γ"}, {"delta", "δ"}, {"Delta", "Δ"},    {"epsilon", "ε"}, {"Epsilon", "Ε"}, {"varepsilon", "ϵ"},
        {"zeta", "ζ"},    {"Zeta", "Ζ"},    {"eta", "η"},  {"Eta", "Η"},    {"iota", "ι"},   {"Iota", "Ι"},  {"kappa", "κ"}, {"Kappa", "Κ"},    {"theta", "θ"},   {"Theta", "Θ"},   {"vartheta", "ϑ"},  
        {"lambda", "λ"},  {"Lambda", "Λ"},  {"mu", "μ"},   {"Mu", "Μ"},     {"nu", "ν"},     {"Nu", "Ν"},    {"xi", "ξ"},    {"Xi", "Ξ"},       {"pi", "π"},      {"Pi", "Π"},      {"varpi", "ϖ"}, 
        {"omicron", "ο"}, {"Omicron", "Ο"}, {"rho", "ρ"},  {"Rho", "Ρ"},    {"varrho", "ϱ"}, {"sigma", "σ"}, {"Sigma", "Σ"}, {"varsigma", "ς"}, {"tau", "τ"},     {"Tau", "Τ"},     {"upsilon", "υ"}, 
        {"Upsilon", "Υ"}, {"phi", "φ"},     {"Phi", "Φ"},  {"varphi", "ϕ"}, {"chi", "χ"},    {"Chi", "Χ"},   {"psi", "ψ"},   {"Psi", "Ψ"},      {"omega", "ω"},   {"Omega", "Ω"}};

    public static char getGreekLetter(String letterStr){
        if (!sorted){
            Sort.pairQuicksort(greekLetterPairs);
            sorted = true;
        }
        int index = Search.binarySearch(greekLetterPairs, letterStr);
        if (index != -1){
            return greekLetterPairs[index][1].charAt(0);
        }
        return '\0';
    }

    private static List<String[][]> replacementGreekLetters = new ArrayList<>();
    public static String replaceWithGreekLetters (String str){
        if (replacementGreekLetters.isEmpty()){
            List<List<String[]>> tempReplacements = new ArrayList<>();
            for (int i = 2; i<10; i++){
                tempReplacements.add(new ArrayList<>());
            }
            for (String[] pair : greekLetterPairs){
                tempReplacements.get(pair[0].length()-2).add(pair);
            }
            for (List<String[]> listPair : tempReplacements){
                String[][] array = new String[listPair.size()][0];
                for (int i = 0; i<array.length; i++){
                    array[i] = listPair.get(i);
                }
                replacementGreekLetters.add(array);
            }
            for (String[][] array : replacementGreekLetters){
                Sort.pairQuicksort(array);
            }
        }
        for (int i = 0; i<str.length()-1; i++){
            for (String[][] set : replacementGreekLetters){
                int index = Search.binarySearch(set, str.substring(i, i+set[0][0].length()));
                if (index != -1){
                    str = str.substring(0, i)+set[index][1]+str.substring(i+set[0][0].length());
                    break;
                }
            }
        }
        return str;
    }

    public static char[] greekCapitalLetters = null;
    public static char[] getCapitalGreekLetters(){
        if (greekCapitalLetters == null){
            greekCapitalLetters = new char[NUM_GREEK_CAPITAL];
            int i = 0;
            for (String[] pair : greekLetterPairs){
                if (Character.isUpperCase(pair[0].charAt(0))){
                    greekCapitalLetters[i++] = pair[1].charAt(0);
                }
            }
            //Sort.quicksort(greekCapitalLetters);
        }
        return greekCapitalLetters;
    }

    public static char[] greekLowerCaseLetters = null;
    public static char[] getLowerCaseGreekLetters(){
        if (greekLowerCaseLetters == null){
            greekLowerCaseLetters = new char[NUM_GREEK_LOWERCASE+NUM_GREEK_VARIANTS];
            int i = 0;
            for (String[] pair : greekLetterPairs){
                if (Character.isLowerCase(pair[0].charAt(0))){
                    greekLowerCaseLetters[i++] = pair[1].charAt(0);
                }
            }
            //Sort.quicksort(greekLowerCaseLetters);
        }
        return greekLowerCaseLetters;
    }

}
