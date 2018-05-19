package Math_Evaluation_Library.Constants;

import Math_Evaluation_Library.Search;
import Math_Evaluation_Library.Sort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Antonio on 2017-08-19.
 */
public class GreekLetters {

    private static boolean sorted = false;

    public static final int NUM_GREEK_CAPITAL = 24;
    public static final int NUM_GREEK_LOWERCASE = 24;
    public static final int NUM_GREEK_VARIANTS = 6;

    public static final int maxStrLength = 10;
    public static final int minStrLength = 2;

    public static HashMap<String, String> greekLetterMap = createGreekLetterMap();

    public static HashMap<String, String> createGreekLetterMap(){
        HashMap<String, String> map = new HashMap<>();
        map.put("alpha", "α");
        map.put("Alpha", "Α");
        map.put("beta", "β");
        map.put("Beta", "Β");
        map.put("gamma", "γ");
        map.put("Gamma", "Γ");
        map.put("delta", "δ");
        map.put("Delta", "Δ");
        map.put("epsilon", "ε");
        map.put("Epsilon", "Ε");
        map.put("vareps", "ϵ");
        map.put("varepsilon", "ϵ");
        map.put("zeta", "ζ");
        map.put("Zeta", "Ζ");
        map.put("eta", "η");
        map.put("Eta", "Η");
        map.put("iota", "ι");
        map.put("Iota", "Ι");
        map.put("kappa", "κ");
        map.put("Kappa", "Κ");
        map.put("theta", "θ");
        map.put("Theta", "Θ");
        map.put("vartheta", "ϑ");
        map.put("lambda", "λ");
        map.put("Lambda", "Λ");
        map.put("mu", "μ");
        map.put("Mu", "Μ");
        map.put("nu", "ν");
        map.put("Nu", "Ν");
        map.put("xi", "ξ");
        map.put("Xi", "Ξ");
        map.put("pi", "π");
        map.put("Pi", "Π");
        map.put("varpi", "ϖ");
        map.put("omicron", "ο");
        map.put("Omicron", "Ο");
        map.put("rho", "ρ");
        map.put("Rho", "Ρ");
        map.put("varrho", "ϱ");
        map.put("sigma", "σ");
        map.put("Sigma", "Σ");
        map.put("varsigma", "ς");
        map.put("tau", "τ");
        map.put("Tau", "Τ");
        map.put("upsilon", "υ");
        map.put("Upsilon", "Υ");
        map.put("phi", "φ");
        map.put("Phi", "Φ");
        map.put("varphi", "ϕ");
        map.put("chi", "χ");
        map.put("Chi", "Χ");
        map.put("psi", "ψ");
        map.put("Psi", "Ψ");
        map.put("omega", "ω");
        map.put("Omega", "Ω");
        return map;
    }
    public static boolean isGreekLetter(String letterStr){
        return greekLetterMap.containsKey(letterStr);
    }

    public static String[][] greekLetterPairs = {
        {"alpha", "α"},   {"Alpha", "Α"},   {"beta", "β"}, {"Beta", "Β"},   {"gamma", "γ"},  {"Gamma", "Γ"}, {"delta", "δ"}, {"Delta", "Δ"},    {"epsilon", "ε"}, {"Epsilon", "Ε"}, {"varepsilon", "ϵ"},
        {"zeta", "ζ"},    {"Zeta", "Ζ"},    {"eta", "η"},  {"Eta", "Η"},    {"iota", "ι"},   {"Iota", "Ι"},  {"kappa", "κ"}, {"Kappa", "Κ"},    {"theta", "θ"},   {"Theta", "Θ"},   {"vartheta", "ϑ"},  
        {"lambda", "λ"},  {"Lambda", "Λ"},  {"mu", "μ"},   {"Mu", "Μ"},     {"nu", "ν"},     {"Nu", "Ν"},    {"xi", "ξ"},    {"Xi", "Ξ"},       {"pi", "π"},      {"Pi", "Π"},      {"varpi", "ϖ"}, 
        {"omicron", "ο"}, {"Omicron", "Ο"}, {"rho", "ρ"},  {"Rho", "Ρ"},    {"varrho", "ϱ"}, {"sigma", "σ"}, {"Sigma", "Σ"}, {"varsigma", "ς"}, {"tau", "τ"},     {"Tau", "Τ"},     {"upsilon", "υ"}, 
        {"Upsilon", "Υ"}, {"phi", "φ"},     {"Phi", "Φ"},  {"varphi", "ϕ"}, {"chi", "χ"},    {"Chi", "Χ"},   {"psi", "ψ"},   {"Psi", "Ψ"},      {"omega", "ω"},   {"Omega", "Ω"}};

    public static char getGreekLetter(String letterStr){
        if (greekLetterMap.containsKey(letterStr)){
            return greekLetterMap.get(letterStr).charAt(0);
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
