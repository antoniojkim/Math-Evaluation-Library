package Math_Evaluation_Library.Constants;

import Math_Evaluation_Library.Search;
import Math_Evaluation_Library.Sort;

/**
 * Created by Antonio on 2017-07-11.
 */
public class Scripts {

    private static boolean sorted = false;
    private static void sort(){
        Sort.quicksort(regularScripts, superScripts, subScripts);
//        Sort.quicksort(regularScripts, superScripts, subScripts);
        sorted = true;
    }

//    public static String[] superScripts =   {"⁰", "¹", "²", "³", "⁴",  "⁵", "⁶", "⁷",  "⁸", "⁹", "⁺", "⁻",  "⁼", "⁽", "⁾",  "ⁿ", "",  "",  "",  "",  "",  "",  "",  "",  "",  "",  "",  ""};
//    public static String[] regularScripts = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "+", "-", "=", "(", ")", "n", "a", "e", "o", "x", "h", "k", "l", "m", "n", "p", "s", "t"};
//    public static String[] subScripts =     {"₀", "₁", "₂",  "₃", "₄",  "₅", "₆", "₇",  "₈", "₉", "₊", "₋",  "₌", "₍", "₎",  "",  "ₐ", "ₑ", "ₒ", "ₓ",  "ₕ", "ₖ",  "ₗ",  "ₘ", "ₙ", "ₚ", "ₛ",  "ₜ"};

    public static char[] superScripts =   {'⁰', '¹', '²', '³', '⁴',  '⁵', '⁶', '⁷',  '⁸', '⁹', '⁺', '⁻',  '⁼', '⁽', '⁾',  'ⁿ',  '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0'};
    public static char[] regularScripts = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '-', '=', '(', ')', 'n',   'a',  'e',  'o', 'x',  'h',  'k',  'l',  'm',  'n',  'p',  's',  't'};
    public static char[] subScripts =     {'₀', '₁',  '₂', '₃', '₄',  '₅', '₆', '₇',  '₈', '₉', '₊', '₋',  '₌', '₍', '₎',  '\0',  'ₐ',  'ₑ',   'ₒ', 'ₓ',   'ₕ',  'ₖ',  'ₗ',   'ₘ',  'ₙ',  'ₚ',  'ₛ',   'ₜ'};

    public static String getSuperScript(String regular){
        if (!sorted){   sort(); }
        int index = getSuperScriptIndex(regular);
        if (index > 0){
            return String.valueOf(superScripts[index] );
        }
        return "";
    }
    public static char getSuperScript(char regular){
        if (!sorted){   sort(); }
        int index = Search.binarySearch(regularScripts, regular);
        if (index > 0){
            return superScripts[index];
        }
        return '\0';
    }
    public static String getSubScript(String regular){
        if (!sorted){   sort(); }
        int index = getSubScriptIndex(regular);
        if (index > 0){
            return String.valueOf(subScripts[index]);
        }
        return "";
    }
    public static char getSubScript(char regular){
        if (!sorted){   sort(); }
        int index = Search.binarySearch(regularScripts, regular);
        if (index > 0){
            return subScripts[index];
        }
        return '\0';
    }

    public static boolean isSuperScript(String superScript){
        if (superScript.length() == 1){
            return isSuperScript(superScript.charAt(0));
        }
        char[] array = superScript.toCharArray();
        for (char c : array){
            if (!isSuperScript(c)){
                return false;
            }
        }
        return true;
    }
    public static boolean isSuperScript(char superScript){
        for (char script : superScripts){
            if (script == superScript){
                return true;
            }
        }
        return false;
    }
    public static boolean isSuperScriptNum(String superScript){
        if (superScript.length() == 1){
            return isSuperScriptNum(superScript.charAt(0));
        }
        return false;
    }
    public static boolean isSuperScriptNum(char superScript){
        return getSuperScriptNumIndex(superScript) != -1;
    }

    public static boolean isSubScript(String subScript){
        if (subScript.length() == 0){
            return isSubScript(subScript.charAt(0));
        }
        return false;
    }
    public static boolean isSubScript(char subScript){
        for (char script : subScripts){
            if (script == subScript){
                return true;
            }
        }
        return false;
    }
    public static boolean isSubScriptNum(String subScript){
        if (subScript.length() == 1){
            return isSubScriptNum(subScript.charAt(0));
        }
        return false;
    }
    public static boolean isSubScriptNum(char subScript){
        return getSubScriptNumIndex(subScript) != -1;
    }

    public static int getSuperScriptIndex(String superScript){
        if (superScript.length() == 1) {
            return getSuperScriptIndex(superScript.charAt(0));
        }
        return -1;
    }
    public static int getSuperScriptIndex(char superScript){
        if (!sorted){   sort(); }
        for (int i = 0; i< superScripts.length; i++){
            if (superScripts[i] == superScript){
                return i;
            }
        }
        return -1;
    }
    public static int getSuperScriptNumIndex(String superScript){
        if (superScript.length() == 1) {
            return getSuperScriptNumIndex(superScript.charAt(0));
        }
        return -1;
    }
    public static int getSuperScriptNumIndex(char superScript) {
        for (int i = 0; i < subScriptNums.length; i++) {
            if (superScriptNums[i] == superScript) {
                return i;
            }
        }
        return -1;
    }

    public static int getRegularScriptIndex(String regular){
        if (regular.length() == 1) {
            return getRegularScriptIndex(regular.charAt(0));
        }
        return -1;
    }
    public static int getRegularScriptIndex(char regular){
        if (!sorted){   sort(); }
        return Search.binarySearch(regularScripts, regular);
    }
    public static int getRegularScriptNumIndex(String regular){
        if (regular.length() == 1) {
            return getRegularScriptNumIndex(regular.charAt(0));
        }
        return -1;
    }
    public static int getRegularScriptNumIndex(char regular){
        return Search.binarySearch(regularScriptNums, regular);
    }

    public static int getSubScriptIndex(String subScript){
        if (subScript.length() == 1) {
            return getSubScriptIndex(subScript.charAt(0));
        }
        return -1;
    }
    public static int getSubScriptIndex(char subScript){
        if (!sorted){   sort(); }
        for (int i = 0; i< subScripts.length; i++){
            if (subScripts[i] == subScript){
                return i;
            }
        }
        return -1;
    }
    public static int getSubScriptNumIndex(String subScript){
        if (subScript.length() == 1) {
            return getSubScriptNumIndex(subScript.charAt(0));
        }
        return -1;
    }
    public static int getSubScriptNumIndex(char subScript) {
        for (int i = 0; i < subScriptNums.length; i++) {
            if (subScriptNums[i] == subScript) {
                return i;
            }
        }
        return -1;
    }

    public static String toSuperScript(String regular){
        char[] regularArray = regular.toCharArray();
        String superScript = "";
        for (int i = 0; i<regularArray.length; i++){
            int index = getRegularScriptIndex(regularArray[i]);
            if (index != -1){
                superScript += superScripts[index];
            }
        }
        return superScript;
    }
    public static String toRegularScript(String script){
        char[] scriptArray = script.toCharArray();
        char[] regular = new char[scriptArray.length];
        for (int i = 0; i<regular.length; i++){
            int index = getSuperScriptIndex(scriptArray[i]);
            if (index != -1){   regular[i] = regularScripts[index];   }
            else{
                index = getSubScriptIndex(scriptArray[i]);
                if (index != -1){   regular[i] = regularScripts[index];   }
                else{
                    regular[i] = scriptArray[i];
                }
            }
        }
        return new String(regular);
    }
    public static String toSubScript(String regular){
        char[] regularArray = regular.toCharArray();
        char[] subScript = new char[regularArray.length];
        for (int i = 0; i<subScript.length; i++){
            int index = getRegularScriptIndex(regularArray[i]);
            if (index != -1){
                subScript[i] = subScripts[index];
            }
        }
        return new String(subScript);
    }

    public static char[] superScriptNums =   {'⁰', '¹', '²', '³', '⁴',  '⁵', '⁶', '⁷',  '⁸', '⁹'};
    public static char[] regularScriptNums = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    public static char[] subScriptNums =     {'₀', '₁', '₂',  '₃', '₄',  '₅', '₆', '₇',  '₈', '₉'};


}
