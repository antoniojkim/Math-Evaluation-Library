package Math_Evaluation_Library.Engine;

import Math_Evaluation_Library.Constants.Constants;
import Math_Evaluation_Library.Constants.GreekLetters;
import Math_Evaluation_Library.Constants.Scripts;
import Math_Evaluation_Library.Constants.StringReplacements;
import Math_Evaluation_Library.ExpressionObjects.*;
import Math_Evaluation_Library.Objects._Number_;
import Math_Evaluation_Library.Search;
import Math_Evaluation_Library.Statistics.Stats;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringJoiner;

import static Math_Evaluation_Library.Constants.GreekLetters.getGreekLetter;
import static Math_Evaluation_Library.Constants.GreekLetters.isGreekLetter;
import static Math_Evaluation_Library.ExpressionObjects.MultiParamFunctions.isMultiParamFunction;
import static Math_Evaluation_Library.ExpressionObjects.TextFunctions.isTextFunction;
import static Math_Evaluation_Library.ExpressionObjects.UnaryFunctions.isUnaryFunction;

public class Scanner {

    public enum TokenType {
        NUM, HEX, COMMA, SEMICOLON, STR,
        LPAREN, RPAREN, LSQUARE, RSQUARE, LCURLY, RCURLY, ABS,
        SUPERSCRIPT, SUBSCRIPT,
        OPERATOR, DOT, RIGHTARROW
    }
    public static TokenType toTokenType(String type){
        switch(type.toUpperCase()){
            case "NUM":         return TokenType.NUM;
            case "COMMA":       return TokenType.COMMA;
            case "SEMICOLON":   return TokenType.SEMICOLON;
            case "STR":         return TokenType.STR;
            case "LPAREN":      return TokenType.LPAREN;
            case "RPAREN":      return TokenType.RPAREN;
            case "LSQUARE":     return TokenType.LSQUARE;
            case "RSQUARE":     return TokenType.RSQUARE;
            case "LCURLY":      return TokenType.LCURLY;
            case "RCURLY":      return TokenType.RCURLY;
            case "SUPERSCRIPT": return TokenType.SUPERSCRIPT;
            case "SUBSCRIPT":   return TokenType.SUBSCRIPT;
//            case "VAR":         return TokenType.VAR;
            case "ABS":         return TokenType.ABS;
            case "DOT":         return TokenType.DOT;
            default :          return null;
        }
    }
    public static TokenType toTokenType(char c){
        if (Operators.isOperator(c))
            return TokenType.OPERATOR;
        if (c >= '0' && c <= '9')
            return TokenType.NUM;
        if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))
            return TokenType.STR;
        if (Scripts.isSuperScript(c))
            return TokenType.SUPERSCRIPT;
        if (Scripts.isSubScript(c))
            return TokenType.SUBSCRIPT;
        switch(c){
            case '(':   return TokenType.LPAREN;
            case ')':   return TokenType.RPAREN;
            case '[':   return TokenType.LSQUARE;
            case ']':   return TokenType.RSQUARE;
            case '{':   return TokenType.LCURLY;
            case '}':   return TokenType.RCURLY;
            case '|':   return TokenType.ABS;
            case ',':   return TokenType.COMMA;
            case ';':   return TokenType.SEMICOLON;
            case '.':   return TokenType.DOT;
            case '→':   return TokenType.RIGHTARROW;
            case 'ᴵ':    return TokenType.SUPERSCRIPT;
            case 'ᵀ':    return TokenType.SUPERSCRIPT;
            default :   return TokenType.STR;
        }
    }
    public static boolean isSingleTokenType(TokenType type){
        return type != null &&
                (type == TokenType.COMMA || type == TokenType.SEMICOLON ||
                type == TokenType.LPAREN || type == TokenType.RPAREN ||
                type == TokenType.LCURLY || type == TokenType.RCURLY ||
                type == TokenType.LSQUARE || type == TokenType.RSQUARE ||
                type == TokenType.OPERATOR || type == TokenType.ABS);
    }

    public static class Token {
        private TokenType type;
        private String lexeme;

        public Token(TokenType type, String lexeme){
            this.type = type;
            this.lexeme = lexeme;
        }
        public Token(TokenType type, char lexeme){
            this.type = type;
            this.lexeme = String.valueOf(lexeme);
        }
        public Token(String type, String lexeme){
            this.type = toTokenType(type);
            this.lexeme = lexeme;
        }
        public Token(String type, char lexeme){
            this.type = toTokenType(type);
            this.lexeme = String.valueOf(lexeme);
        }

        public void set(TokenType type, String lexeme) {
            this.type = type;
            this.lexeme = lexeme;
        }
        public TokenType getType() {
            return type;
        }
        public void setType(TokenType type) {
            this.type = type;
        }
        public String getLexeme() {
            return lexeme;
        }
        public void setLexeme(String lexeme) {
            this.lexeme = lexeme;
        }
        public String toString() {
            return getLexeme();
        }
        public char getLast() {
            return lexeme.charAt(lexeme.length()-1);
        }
        public int length(){
            return lexeme.length();
        }

        public boolean isImplicit() {
            return (lexeme.length() == 1 || _Number_.isNumber(lexeme)) && Engine.implicit.containsKey(getLast());
        }
        public boolean isCheckImplicit() {
            return lexeme.length() == 1 && Engine.checkImplicit.containsKey(getLast());
        }
        public boolean isFunction() {
            return type == TokenType.STR && (
                    UnaryFunctions.isUnaryFunction(lexeme) ||
                            MultiParamFunctions.isMultiParamFunction(lexeme) ||
                            TextFunctions.isTextFunction(lexeme)
                    );
        }
    }

    public static List<Token> scan(String function){
        function = function.trim();
        if (function.isEmpty()){
            return new ArrayList<>();
        }
        function = Search.replace(function, StringReplacements.dynamicInputReplacement);
        Stack<Token> stack = new Stack<>();
        StringBuilder token = new StringBuilder();
        TokenType currentType = null;
        int functionMax = Stats.max(UnaryFunction.maxStrLength, MultiParamFunction.maxStrLength,
                TextFunction.maxStrLength, GreekLetters.maxStrLength, StringReplacements.maxFunctionReplacementsLen);
        int functionMin = Stats.min(UnaryFunction.minStrLength, MultiParamFunction.minStrLength,
                TextFunction.minStrLength, GreekLetters.minStrLength, StringReplacements.minFunctionReplacementsLen);
        scan: for (int i = 0; i<function.length(); ++i){
            char c = function.charAt(i);
            if (c == ' ')    continue;
            TokenType type = toTokenType(c);
            if (type != currentType){
                if (token.length() > 0 &&
                        !(currentType == TokenType.NUM && type == TokenType.DOT) &&
                        !(currentType == TokenType.NUM && type == TokenType.STR &&
                                token.length() == 1 && token.charAt(0) == '0' && c == 'x') &&
                        !(currentType == TokenType.HEX && (type == TokenType.NUM || type == TokenType.STR) &&
                                ((c >= '0' && c <= '9') || (c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F'))) &&
                        !(currentType == TokenType.DOT && type == TokenType.NUM) &&
                        !(currentType == TokenType.OPERATOR && type == TokenType.STR &&
                                (token.charAt(0) == 'P' || token.charAt(0) == 'C' || c == 'F')) &&
                        !(currentType == TokenType.OPERATOR && type == TokenType.NUM &&
                                token.charAt(0) == '-' &&
                                (stack.isEmpty() || !stack.peek().isImplicit()))){
                    if (currentType == TokenType.SUPERSCRIPT && Scripts.isSuperScriptNum(token.toString()) &&
                            stack.peek().isImplicit()){
                        stack.add(new Token(TokenType.OPERATOR, "^"));
                        stack.add(new Token(TokenType.NUM, Scripts.toRegularScript(token.toString())));
                    }
                    else{
                        stack.add(new Token(currentType, token.toString()));
                    }
                    token.setLength(0);
                }
                if (!(currentType ==  TokenType.NUM && type == TokenType.DOT)){
                    if (type == TokenType.NUM && currentType == TokenType.DOT){
                        token.setLength(0);
                        token.append("0.");
                        currentType = TokenType.NUM;
                    }
                    else{
                        if (currentType == TokenType.NUM && type == TokenType.STR &&
                                token.length() == 1 && token.charAt(0) == '0' && c == 'x'){
                            currentType = TokenType.HEX;
                        }
                        else if (currentType == TokenType.HEX && (type == TokenType.NUM || type == TokenType.STR) &&
                                ((c >= '0' && c <= '9') || (c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F'))){
                            token.append(String.valueOf(c).toLowerCase());
                            continue scan;
                        }
                        else{
                            currentType = type;
                        }
                    }
                }
                if (currentType == TokenType.STR){
                    for (int j = Math.min(functionMax, function.length()-i); j >= functionMin; --j){
                        String f = function.substring(i, i+j);
                        if (token.length() == 1 && (token.charAt(0) == 'P' || token.charAt(0) == 'C')){
                            f = token.toString()+f;
                        }
                        if (StringReplacements.functionReplacementsMap.containsKey(f)){
                            f = StringReplacements.functionReplacementsMap.get(f);
                        }
                        boolean b = false;
                        if (isMultiParamFunction(f) || isUnaryFunction(f) || isTextFunction(f) ||
                                (b = isMultiParamFunction(f.toLowerCase())) ||
                                (b = isUnaryFunction(f.toLowerCase())) ||
                                (b = isTextFunction(f.toLowerCase()))){
                            stack.add(new Token(currentType, !b ? f : f.toLowerCase()));
                            i += j-1;
                            token.setLength(0);
                            continue scan;
                        }
                        else if (isGreekLetter(f) || (b = isGreekLetter(f.toLowerCase()))){
                            stack.add(new Token(currentType, !b ?
                                    String.valueOf(getGreekLetter(f)) :
                                    String.valueOf(getGreekLetter(f.toLowerCase()))));
                            i += j-1;
                            token.setLength(0);
                            continue scan;
                        }
                    }
                    if (Constants.isConstant(c)){
                        if (!stack.isEmpty() && stack.peek().getType() == TokenType.OPERATOR &&
                                stack.peek().getLast() == '-' &&
                                (stack.size() < 2 || !stack.get(stack.size()-2).isImplicit())){
                            stack.peek().set(TokenType.STR, stack.peek().getLexeme()+String.valueOf(c));
                            token.setLength(0);
                            continue scan;
                        }
                    }
                }
            }
            else if (isSingleTokenType(currentType) || (
                    currentType == TokenType.STR && token.length() == 1 &&
                            (token.charAt(0) == Engine.var() || token.charAt(0) == Engine.varOp()))){

                if (token.length() > 0 && currentType == TokenType.OPERATOR && token.charAt(0) == '°' &&
                        (c == 'C' || c == 'F')){
                    currentType = TokenType.STR;
                }
                else{
                    stack.add(new Token(currentType, token.toString()));
                    token.setLength(0);
                }
            }
            token.append(c);
        }
        if (currentType != null && token.length() > 0){
            stack.add(new Token(currentType, token.toString()));
        }
        List<Token> tokens = stack.subList(0, stack.size());
        boolean abs = false;
        int brackets = 0, hbrackets = 0, cbrackets = 0;
        for (int i = 0; i<tokens.size(); ++i){
            if (tokens.get(i).getType() == TokenType.ABS){
                if (!abs){
                    tokens.get(i).set(TokenType.STR, "abs");
                    tokens.add(i+1, new Token(TokenType.LPAREN, "("));
                    if (i > 1 && tokens.get(i-1).isImplicit()){
                        tokens.add(i, new Token(TokenType.OPERATOR, "*"));
                    }
                    ++i;
                }
                else{
                    tokens.get(i).set(TokenType.RPAREN, ")");
                }
                abs = !abs;
            }
//            else if (tokens.get(i-1).getType() == TokenType.OPERATOR && tokens.get(i).getType() == TokenType.OPERATOR &&
//                    (tokens.get(i-1).getLast() == '*' || tokens.get(i-1).getLast() == '·' ||
//                            tokens.get(i-1).getLast() == '/') &&
//                    tokens.get(i).getLast() == '-'){
//
//                tokens.get(i).set(TokenType.STR, "neg");
//            }
            switch (tokens.get(i).getType()){
                case LPAREN:   ++brackets;  break;
                case RPAREN:   --brackets;  break;
                case LSQUARE:  ++hbrackets; break;
                case RSQUARE:  --hbrackets; break;
                case LCURLY:   ++cbrackets; break;
                case RCURLY:   --cbrackets; break;
                default:                    break;
            }
            // Implicit Multiplication
            if (i+1 < tokens.size() && tokens.get(i).isImplicit() &&
                    (tokens.get(i+1).isCheckImplicit() ||
                            tokens.get(i+1).isFunction())) {
                tokens.add(i+1, new Token(TokenType.OPERATOR, "*"));
                ++i;
            }
        }
        // Add Missing Brackets
        for (int i = 0; i<brackets; ++i){
            tokens.add(new Token(TokenType.RPAREN, ")"));
        }
        for (int i = brackets; i<0; ++i){
            tokens.add(0, new Token(TokenType.LPAREN, "("));
        }
        for (int i = 0; i<hbrackets; ++i){
            tokens.add(new Token(TokenType.RSQUARE, "]"));
        }
        for (int i = hbrackets; i<0; ++i){
            tokens.add(0, new Token(TokenType.LSQUARE, "["));
        }
        for (int i = 0; i<cbrackets; ++i){
            tokens.add(new Token(TokenType.RCURLY, "}"));
        }
        for (int i = cbrackets; i<0; ++i){
            tokens.add(0, new Token(TokenType.LCURLY, "{"));
        }
        return tokens;
    }

    public static String join(List<Token> tokens){
        return join(tokens, "");
    }
    public static String join(List<Token> tokens, String delimeter){
        StringJoiner sj = new StringJoiner(delimeter);
        for (Token token : tokens){
            sj.add(token.getLexeme());
        }
        return sj.toString();
    }

}
