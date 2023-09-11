package com.andre1337.lust.lexer;

import com.andre1337.lust.token.Token;
import com.andre1337.lust.utils.Tuple;
import static com.andre1337.lust.token.Token.Type.*;

import java.util.*;

public class Lexer {
    public String source;
    public List<Token> tokens;
    public int start;
    public int current;
    public int line;
    public int pos;
    public HashMap<String, Token.Type> keywords;
    public List<String> errors;

    public Lexer(String source) {
        HashMap<String, Token.Type> keywords = new HashMap<>();

        List<Token.Type> kws = new ArrayList<>() {
            {
                add(Enum);
                add(Struct);
                add(Fn);
                add(Impl);
                add(Trait);
                add(Mod);
                add(Const);
                add(Type);
                add(Pub);
                add(As);
                add(Null);
                add(Str);
                add(Num);
                add(Int);
                add(Float);
                add(Any);
                add(Vec);
                add(Bool);
                add(Map);
                add(False);
                add(True);
                add(Continue);
                add(Break);
                add(Return);
                add(Self);
                add(StaticSelf);
                add(Let);
                add(Mut);
                add(Loop);
                add(While);
                add(For);
                add(In);
                add(Match);
                add(If);
                add(Else);
            }
        };

        for (Token.Type keyword: kws) {
            keywords.put(Token.value(keyword), keyword);
        }

        this.source = source;
        this.tokens = new ArrayList<>();
        this.start = 0;
        this.current = 0;
        this.line = 1;
        this.pos = 1;
        this.keywords = keywords;
        this.errors = new ArrayList<>();
    }

    public Tuple<List<Token>, List<String>> tokenize() {
        while (!this.isAtEnd()) {
            this.start = this.current;
            this.token();
        }

        this.addTokenWithLexeme(Eof, "");

        return new Tuple<>(this.tokens, this.errors);
    }

    private void token() {
        char ch = this.advance();

        switch (ch) {
            case '{' -> this.addToken(LeftBrace);
            case '}' -> this.addToken(RightBrace);
            case '(' -> this.addToken(LeftParen);
            case ')' -> this.addToken(RightParen);
            case '[' -> this.addToken(LeftBracket);
            case ']' -> this.addToken(RightBracket);
            case ',' -> this.addToken(Comma);
            case ';' -> this.addToken(Semicolon);
            case ':' -> this.addToken(this.matchChar(':') ? ColonColon : Colon);
            case '-' -> this.addToken(this.matchChar('=') ? MinusEqual : Minus);
            case '+' -> this.addToken(this.matchChar('=') ? PlusEqual : Plus);
            case '*' -> this.addToken(this.matchChar('=') ? AsteriskEqual : Asterisk);
            case '%' -> this.addToken(this.matchChar('=') ? ModulusEqual : Modulus);
            case '!' -> this.addToken(this.matchChar('=') ? BangEqual : Bang);
            case '^' -> this.addToken(this.matchChar('=') ? BitwiseXorEqual : BitwiseXor);
            case '<' -> this.addToken(this.matchChar('=') ? LessEqual : Less);
            case '>' -> this.addToken(this.matchChar('=') ? GreaterEqual : Greater);

            case '&' -> {
                if (this.matchChar('&')) this.addToken(LogicAnd);
                else if (this.matchChar('=')) this.addToken(BitwiseAndEqual);
                else this.addToken(BitwiseAnd);
            }

            case '|' -> {
                if (this.matchChar('|')) this.addToken(LogicOr);
                else if (this.matchChar('=')) this.addToken(BitwiseOrEqual);
                else this.addToken(BitwiseOr);
            }

            case '.' -> {
                if (this.matchChar('.')) {
                    if (this.matchChar('=')) {
                        this.addToken(DotDotEqual);
                    } else {
                        this.addToken(DotDot);
                    }
                } else {
                    this.addToken(Dot);
                }
            }

            case '=' -> {
                if (this.matchChar('=')) this.addToken(EqualEqual);
                else if (this.matchChar('>')) this.addToken(FatArrow);
                else this.addToken(Equal);
            }

            case '/' -> {
                if (this.matchChar('/')) {
                    while (this.peek() != '\n' && !this.isAtEnd()) {
                        this.advance();
                    }
                } else if (this.matchChar('*')) {
                    int count = 1;

                    while (true) {
                        if (this.peek() == '/' && this.peekNext() == '*') count++;
                        if (this.peek() == '*' && this.peekNext() == '/') count--;
                        if (count == 0 && this.isAtEnd()) break;
                        else if (0 != count && this.isAtEnd()) {
                            this.errors.add("Unclosed comment.");
                            return;
                        }

                        this.advance();
                    }

                    this.advance();
                    this.advance();
                } else if (this.matchChar('=')) this.addToken(SlashEqual);
                else this.addToken(Slash);
            }

            case ' ', '\r', '\t' -> this.pos++;

            case '\n' -> {
                this.line++;
                this.pos = 0;
            }

            case '"' -> this.string();

            default -> {
                if (this.isDigit(ch)) this.number();
                else if (this.isAlphabetic(ch)) this.identifier();
                else {
                    this.pos++;
                    this.error("Unexpected character \"" + this.source.charAt(current) + "\".");
                }
            }
        }
    }

    private void error(String message) {
        this.errors.add(message);
    }

    private boolean isAtEnd() {
        return this.current >= this.source.length();
    }

    private char advance() {
        return this.source.charAt(this.current++);
    }

    private void addToken(Token.Type tokenType) {
        String lexeme = this.src_substr();
        this.addTokenWithLexeme(tokenType, lexeme);
    }

    private void addTokenWithLexeme(Token.Type tokenType, String lexeme) {
        int len = lexeme.length();
        this.tokens.add(new Token(tokenType, lexeme, this.pos()));
        this.pos += len;
    }

    private boolean matchChar(char expected) {
        if (this.isAtEnd()) return false;
        if (this.source.charAt(this.current) != expected) return false;

        this.current++;
        return true;
    }

    private char peek() {
        if (this.isAtEnd()) return '\0';
        return this.source.charAt(this.current);
    }

    private char peekNext() {
        if (this.current + 1 >= this.source.length()) return '\0';
        return this.source.charAt(this.current++);
    }

    private String src_substr() {
        return this.source.substring(this.start + 1, this.current - 1);
    }

    private boolean isDigit(char ch) {
        return ch >= '0' && ch <= '9';
    }

    private boolean isAlphabetic(char ch) {
        return (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || ch == '_';
    }

    private boolean isAlphanumeric(char ch) {
        return this.isDigit(ch) || this.isAlphabetic(ch) || ch == '_';
    }

    private void string() {
        while (this.peek() != '"' && !this.isAtEnd()) {
            if (this.peek() == '\n') {
                this.line++;
                this.pos = 0;
            }

            this.advance();
        }

        if (this.isAtEnd()) {
            this.errors.add("Unterminated string literal.");
            return;
        }

        this.advance();

        String val = this.src_substr();
        this.addTokenWithLexeme(String, val);
    }

    private void number() {
        boolean isFloat = false;

        while (this.isDigit(this.peek())) {
            this.advance();
        }

        if (this.peek() == '.' && this.isDigit(this.peekNext())) {
            isFloat = true;
            this.advance();

            while (this.isDigit(this.peek())) {
                this.advance();
            }
        }

        this.addToken(isFloat ? NumberFloat : NumberInt);
    }

    private void identifier() {
        while (this.isAlphanumeric(this.peek())) {
            this.advance();
        }

        String text = this.src_substr();
        Token.Type tokenType = this.keywords.getOrDefault(text, Identifier);

        this.addToken(tokenType);
    }

    private Token.Position pos() {
        return new Token.Position(this.line, this.pos);
    }
}
