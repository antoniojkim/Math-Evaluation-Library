package Math_Evaluation_Library.Constants;

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
            {"=s", "⩵"}, {"==", "⩵"}, {":=", "≔"}, {"xor", "⊻"},{"floor_2", "floor₂"},
            {"\\and", "∧"}, {"\\or", "∨"}, {"\\iff", "↔"}, {"\\not", "¬"},
            {"<<", "≪"},  {">>", "≫"},  {"@", "C"}, {"}I", "}⁻¹"}, {"}^-1", "}⁻¹"}
    };

    public static final String[][] capitalInstances = {
            {"P", "npr"},   {"C", "@"},
            {"}t", "}τ"},   {"}T", "}τ"}, {"}⁻¹", "}ι"},
            {"E", "*10^"},  {"ED", "∆"},
            {"Bin", "bd"},  {"NB", "nbd"}, {"geo", "gnb"}, {"ND", "ncd"}
    };

    public static final String[][] formReplacements = {
            {"npr", "P"}, {"@", "C"},    {"-+", "-"},  {"+-", "-"},    {"--", "+"},  {"++", "+"},
            {"−", "-"},   {"÷", "/"},    {"×", "*"},  {"**", "^"}, {"⋅", "·"},   {"\\.", "·"}, {"\\dot", "·"}, {"··", "^"},
            {"=_", "≡"},
//                {"=>", "⇒"},  {"\\=", "≈"},  {"!=", "≠"},
            {"°c", "°C"}, {"°f", "°F"},

            {"ave", "avg"},      {"mean", "avg"},     {"sec", "scnt"},     {"aexp", "axp"},      {"exp", "axp"},      {"coslaw", "c_law"},
            {"1n1p", "lp"},      {"1og1p", "lp"},     {"1og1p", "lp"},     {"log(", "logab("},   {"deg", "dg"},       {"det", "dt"},
            {"elasy", "lasd"},   {"elasx", "lasd"},   {"elasd", "lasd"},   {"prime", "nconst"},  {"heron", "hron"},   {"newton", "nwton"},
            {"fibsum", "smfib"}, {"sumfib", "smfib"}, {"variance", "var"}, {"stdev", "stndv"},   {"stddev", "stndv"}, {"stndev", "stndv"},
            {"ceil", "up"},      {"nderiv", "dx"},    {"deriv", "diff"},   {"riemann", "riman"}, {"randomint", "randint"}, {"randomQ", "randq"}, {"randomq", "randq"},
            {"gcf", "gcd"},      {"erf", "gaussrf"},

            {"pi", "π"}, {"e_m", "γ"}, {"e", "ℯ"}, {"gr", "ϕ"}
    };

    public static final String[][] formReplacements2 = {
            {"intmax", "2147483647"}, {"intmin", "-2147483648"}, {"hparg", "graph"}, {"dg", "deg"}, {"dt", "det"},

            {"axp", "exp"},     {"up", "ceil"},      {"gaussrf", "erf"},
            {"scnt", "sec"},    {"/sec", "cos"},     {"/csc", "sin"},     {"/cot", "tan"},
            {"acos", "arccos"}, {"cos⁻¹", "arccos"}, {"asin", "arcsin"},  {"sin⁻¹", "arcsin"},
            {"atan", "arctan"}, {"tan⁻¹", "arctan"}, {"asec", "arcsec"},  {"sec⁻¹", "arcsec"},
            {"acsc", "arccsc"}, {"csc⁻¹", "arccsc"}, {"acot", "arccot"},  {"cot⁻¹", "arccot"},

            {"diff", "deriv"}, {"riman", "riemann"}, {"max\\*", "max"}, {"lasd", "elasd"}, {"nconst", "prime"},
            {"hron", "heron"}, {"nwton", "newton"},  {"nbd", "NB"},     {"bd", "Bin"},     {"gnb", "geo"},     {"ncd", "ND"}
    };
    public static final String[][] textFunctionReplacements = {
            {"RCEf", "RCEF"}, {"RREf", "RREF"}, {"antideriv", "int"}, {"antidiff", "int"}, {"cmplx", "complex"},
            {"directF", "slopeF"}, {"perpendicular", "perp"}, {"plt", "plot"}, {"primf", "primef"}, {"projection", "proj"},
            {"df", "slopeF"}, {"sf", "slopeF"}, {"diff", "deriv"}, {"congruence", "≡"}, {"parse", "postfix"},
            {"len", "strln"}, {"strlen", "strln"}, {"count", "strln"}
    };

}
