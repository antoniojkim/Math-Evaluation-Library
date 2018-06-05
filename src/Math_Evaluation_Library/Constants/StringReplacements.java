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

            {"<->", "↔"}, {"d->", "↓"}, {"!^", "↓"}, {"->", "→"}, {"_=", "≡"}, {"\\|", separator}, {"|\\", separator},
            {"=s", "⩵"}, {"==", "⩵"}, {":=", "≔"}, {"xor", "⊕"}, {"floor_2", "floor₂"},
            {"\\and", "∧"}, {"\\or", "∨"}, {"\\iff", "↔"}, {"\\not", "¬"},
            {"<<", "≪"},  {">>", "≫"},  {"@", "C"},

            {"}I", "}ᴵ"}, {"}^-1", "}ᴵ"}, {"]I", "]ᴵ"}, {"]^-1", "]ᴵ"},
            {"}T", "}ᵀ"}, {"}^T", "}ᵀ"}, {"]T", "]ᵀ"}, {"]^T", "]ᵀ"},

            {"-+", "-"},  {"+-", "-"},    {"--", "+"},  {"++", "+"},
            {"−", "-"},   {"÷", "/"},    {"×", "*"},  {"**", "^"}, {"⋅", "·"},   {"\\.", "·"}, {"\\dot", "·"}, {"··", "^"},
            {"=_", "≡"},  {"=>", "⇒"},  {"\\=", "≈"},  {"!=", "≠"},
            {"°c", "°C"}, {"°f", "°F"},  {"\\em", "γ"}, {"\\gr", "ϕ"}
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

    public static final String[][] functionReplacements = {
            {"RREf", "RREF"}, {"RCEf", "RCEF"},
            {"ED", "∆"},  {"TeX", "$$"},  {"tex", "$$"},  {"hex", "b₁₆"},
            {"geo", "gnb"},

            {"ave", "avg"},      {"mean", "avg"},
            {"coslaw", "c_law"},
            {"1n1p", "lp"},      {"1og1p", "lp"},     {"1og1p", "lp"},     {"log(", "logab("},   {"dg", "deg"},       {"dt", "det"},
            {"elasy", "lasd"},   {"elasx", "lasd"},
            {"fibsum", "smfib"}, {"sumfib", "smfib"}, {"variance", "var"}, {"stdev", "stndv"},   {"stddev", "stndv"}, {"stndev", "stndv"},
            {"ceil", "up"},      {"nderiv", "dx"},    {"diff", "deriv"},   {"randomint", "randint"}, {"randomQ", "randq"}, {"randomq", "randq"},
            {"gcf", "gcd"},      {"erf", "gaussrf"},  {"gammaf", "Γ"},     {"postfix", "RPN"},
            {"len", "strln"},    {"strlen", "strln"}, {"count", "strln"},  {"median", "mdn"},    {"iqr", "raniq"},
            {"totwoc", "totwo"},

            {"axp", "exp"},     {"up", "ceil"},      {"gaussrf", "erf"},
            {"scnt", "sec"},    {"/sec", "cos"},     {"/csc", "sin"},     {"/cot", "tan"},
            {"acos", "arccos"}, {"cos⁻¹", "arccos"}, {"asin", "arcsin"},  {"sin⁻¹", "arcsin"},
            {"atan", "arctan"}, {"tan⁻¹", "arctan"}, {"asec", "arcsec"},  {"sec⁻¹", "arcsec"},
            {"acsc", "arccsc"}, {"csc⁻¹", "arccsc"}, {"acot", "arccot"},  {"cot⁻¹", "arccot"},

            {"nbd", "NB"},     {"bd", "Bin"},     {"gnb", "geo"},
            {"ngd", "ND"},     {"rvxp", "Exp"},      {"rvvar", "Var"},  {"Cdf", "cdf"},    {"Pdf", "pdf"},    {"pf", "pdf"},

            {"rowr", "RREF"},  {"rowc", "RCEF"},

            {"antideriv", "int"}, {"antidiff", "int"}, {"cmplx", "complex"},
            {"directF", "slopeF"}, {"perpendicular", "perp"}, {"plt", "plot"}, {"primf", "primef"}, {"projection", "proj"},
            {"df", "slopeF"}, {"sf", "slopeF"}, {"diff", "deriv"}, {"congruence", "≡"}, {"parse", "RPN"}, {"postfix", "RPN"}
    };
    public static int maxFunctionReplacementsLen = 13;
    public static int minFunctionReplacementsLen = 2;
    public static final HashMap<String, String> functionReplacementsMap = createFunctionReplacementsMap();
    public static HashMap<String, String> createFunctionReplacementsMap(){
        HashMap<String, String> map = new HashMap<>();
        for (String[] pair : functionReplacements){
            map.put(pair[0], pair[1]);
            if (pair[0].length() > maxFunctionReplacementsLen){
                maxFunctionReplacementsLen = pair[0].length();
            }
            else if (pair[0].length() < minFunctionReplacementsLen){
                minFunctionReplacementsLen = pair[0].length();
            }
        }
        return map;
    }

}
