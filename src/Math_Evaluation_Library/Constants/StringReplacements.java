package Math_Evaluation_Library.Constants;

import java.util.HashMap;

import static Math_Evaluation_Library.Engine.Engine.separator;

/**
 * Created by Antonio on 2017-10-17.
 */
public class StringReplacements {

    //https://en.wikipedia.org/wiki/Mathematical_operators_and_symbols_in_Unicode

    public static final String[][] dynamicInputReplacement = {
            {"sqrt", "√"}, {"cbrt", "∛"}, {"\\.", "·"}, {"\\dot", "·"},
            {"^deg", "°"}, {"^o", "°"}, {"^O", "°"}, {"`", "°"},
            {"〖", "("}, {"〗", ")"}, {"⁡", ""}, {"█", ""},
            {"_r", "ʳ"}, {"^r", "ʳ"}, {"goldr", "ϕ"}, {"E_M", "γ"},

            {"sum", "∑"}, {"product", "∏"},   {"nint", "∫"},  {"dist", "∆"},

            {"<->", "↔"}, {"d->", "↓"}, {"!^", "↓"}, {"->", "→"}, {"_=", "≡"}, {"\\|", separator}, {"|\\", separator}, {"||", separator},
            {"=s", "⩵"}, {"==", "⩵"}, {":=", "≔"}, {"xor", "⊕"}, {"floor_2", "floor₂"},
            {"\\and", "∧"}, {"\\or", "∨"}, {"\\iff", "↔"}, {"\\not", "¬"},
            {"<<", "≪"},  {">>", "≫"},  {"@", "C"}, {"}I", "}ᴵ"}, {"}^-1", "}ᴵ"}, {"]I", "]ᴵ"}, {"]^-1", "]ᴵ"}
    };
    public static int maxDynamicInputLen = 7;
    public static int minDynamicInputLen = 1;
    public static final HashMap<String, String> dynamicInputMap = createDynamicInputMap();
    private static HashMap<String, String> createDynamicInputMap(){
        HashMap<String, String> map = new HashMap<>();
        for (String[] pair : dynamicInputReplacement){
            map.put(pair[0], pair[1]);
            if (pair[0].length() > maxDynamicInputLen){
                maxDynamicInputLen = pair[0].length();
            }
            else if (pair[0].length() < minDynamicInputLen){
                minDynamicInputLen = pair[0].length();
            }
        }
        return map;
    }

    public static final String[][] capitalInstances = {
            {"RREF", "rowr"},{"RREf", "rowr"},{"RCEF", "rowc"},{"RCEf", "rowc"},
//            {"P", "npr"},   {"C", "@"},
            {"}t", "}ᵀ"},   {"}T", "}ᵀ"},    {"}⁻¹", "}ᴵ"},
            {"]t", "]ᵀ"},   {"]T", "]ᵀ"},    {"]⁻¹", "]ᴵ"},
//            {"ND", "ngd"},  {"Exp", "rvxp"}, {"Γ", "gammaf"},
//            {"Bin", "bd"},  {"NB", "nbd"},
            {"geo", "gnb"},
//            {"Var", "rvvar"},
//            {"E", "*10^"},
            {"ED", "∆"},  {"TeX", "$$"},  {"tex", "$$"},  {"hex", "b₁₆"}
    };
    public static int maxCapitalInstancesLen = 4;
    public static int minCapitalInstancesLen = 1;
    public static final HashMap<String, String> capitalInstancesMap = createCapitalInstancesMap();
    private static HashMap<String, String> createCapitalInstancesMap(){
        HashMap<String, String> map = new HashMap<>();
        for (String[] pair : capitalInstances){
            map.put(pair[0], pair[1]);
            if (pair[0].length() > maxCapitalInstancesLen){
                maxCapitalInstancesLen = pair[0].length();
            }
            else if (pair[0].length() < minCapitalInstancesLen){
                minCapitalInstancesLen = pair[0].length();
            }
        }
        return map;
    }

    public static final String[][] formReplacements = {
            {"npr", "P"}, {"@", "C"},    {"-+", "-"},  {"+-", "-"},    {"--", "+"},  {"++", "+"},
            {"−", "-"},   {"÷", "/"},    {"×", "*"},  {"**", "^"}, {"⋅", "·"},   {"\\.", "·"}, {"\\dot", "·"}, {"··", "^"},
            {"=_", "≡"},
//                {"=>", "⇒"},  {"\\=", "≈"},  {"!=", "≠"},
            {"°c", "°C"}, {"°f", "°F"},

            {"ave", "avg"},      {"mean", "avg"},
//            {"sec", "scnt"},
//            {"aexp", "axp"},
//            {"exp", "axp"},
            {"coslaw", "c_law"},
            {"1n1p", "lp"},      {"1og1p", "lp"},     {"1og1p", "lp"},     {"log(", "logab("},   {"deg", "dg"},       {"det", "dt"},
            {"elasy", "lasd"},   {"elasx", "lasd"},   {"elasd", "lasd"},   {"prime", "nconst"},  {"heron", "hron"},   {"newton", "nwton"},
            {"fibsum", "smfib"}, {"sumfib", "smfib"}, {"variance", "var"}, {"stdev", "stndv"},   {"stddev", "stndv"}, {"stndev", "stndv"},
            {"ceil", "up"},      {"nderiv", "dx"},    {"deriv", "diff"},   {"riemann", "riman"}, {"randomint", "randint"}, {"randomQ", "randq"}, {"randomq", "randq"},
            {"gcf", "gcd"},      {"erf", "gaussrf"},  {"gammaf", "Γ"},     {"postfix", "RPN"},
            {"len", "strln"},    {"strlen", "strln"}, {"count", "strln"},  {"median", "mdn"},    {"iqr", "raniq"},

            {"pi", "π"}, {"e_m", "γ"}, {"e", "ℯ"}, {"gr", "ϕ"}
    };
    public static int maxFormReplacementsLen = 8;
    public static int minFormReplacementsLen = 1;
    public static final HashMap<String, String> formReplacementsMap = createFormReplacementsMap();
    public static HashMap<String, String> createFormReplacementsMap(){
        HashMap<String, String> map = new HashMap<>();
        for (String[] pair : formReplacements){
            map.put(pair[0], pair[1]);
            if (pair[0].length() > maxFormReplacementsLen){
                maxFormReplacementsLen = pair[0].length();
            }
            else if (pair[0].length() < minFormReplacementsLen){
                minFormReplacementsLen = pair[0].length();
            }
        }
        return map;
    }

    public static final String[][] formReplacements2 = {
            {"intmax", "2147483647"}, {"intmin", "-2147483648"}, {"hparg", "graph"}, {"dg", "deg"}, {"dt", "det"},

            {"axp", "exp"},     {"up", "ceil"},      {"gaussrf", "erf"},
            {"scnt", "sec"},    {"/sec", "cos"},     {"/csc", "sin"},     {"/cot", "tan"},
            {"acos", "arccos"}, {"cos⁻¹", "arccos"}, {"asin", "arcsin"},  {"sin⁻¹", "arcsin"},
            {"atan", "arctan"}, {"tan⁻¹", "arctan"}, {"asec", "arcsec"},  {"sec⁻¹", "arcsec"},
            {"acsc", "arccsc"}, {"csc⁻¹", "arccsc"}, {"acot", "arccot"},  {"cot⁻¹", "arccot"},

            {"diff", "deriv"}, {"riman", "riemann"}, {"max\\*", "max"}, {"lasd", "elasd"}, {"nconst", "prime"},
            {"hron", "heron"}, {"nwton", "newton"},  {"nbd", "NB"},     {"bd", "Bin"},     {"gnb", "geo"},
            {"ngd", "ND"},     {"rvxp", "Exp"},      {"rvvar", "Var"},  {"Cdf", "cdf"},    {"Pdf", "pdf"},    {"pf", "pdf"},

            {"rowr", "RREF"},  {"rowc", "RCEF"}
    };
    public static int maxFormReplacements2Len = 7;
    public static int minFormReplacements2Len = 2;
    public static final HashMap<String, String> formReplacements2Map = createFormReplacements2Map();
    private static HashMap<String, String> createFormReplacements2Map(){
        HashMap<String, String> map = new HashMap<>();
        for (String[] pair : formReplacements2){
            map.put(pair[0], pair[1]);
            if (pair[0].length() > maxFormReplacements2Len){
                maxFormReplacements2Len = pair[0].length();
            }
            else if (pair[0].length() < minFormReplacements2Len){
                minFormReplacements2Len = pair[0].length();
            }
        }
        return map;
    }

    public static final String[][] textFunctionReplacements = {
            {"antideriv", "int"}, {"antidiff", "int"}, {"cmplx", "complex"},
            {"directF", "slopeF"}, {"perpendicular", "perp"}, {"plt", "plot"}, {"primf", "primef"}, {"projection", "proj"},
            {"df", "slopeF"}, {"sf", "slopeF"}, {"diff", "deriv"}, {"congruence", "≡"}, {"parse", "RPN"}, {"postfix", "RPN"}
    };
    public static int maxTextFunctionReplacementsLen = 7;
    public static int minTextFunctionReplacementsLen = 2;
    public static final HashMap<String, String> textFunctionReplacementsMap = createTextFunctionReplacementsMap();
    private static HashMap<String, String> createTextFunctionReplacementsMap(){
        HashMap<String, String> map = new HashMap<>();
        for (String[] pair : textFunctionReplacements){
            map.put(pair[0], pair[1]);
            if (pair[0].length() > maxTextFunctionReplacementsLen){
                maxTextFunctionReplacementsLen = pair[0].length();
            }
            else if (pair[0].length() < minTextFunctionReplacementsLen){
                minTextFunctionReplacementsLen = pair[0].length();
            }
        }
        return map;
    }

}
