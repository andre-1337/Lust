package com.andre1337.lust.token;

public class Token {
    public Type tokenType;
    public String lexeme;
    public Position position;

    public Token(Type tokenType, String lexeme, Position position) {
        this.tokenType = tokenType;
        this.lexeme = lexeme;
        this.position = position;
    }

    public Token(Token token, String lexeme) {
        this.tokenType = token.tokenType;
        this.lexeme = lexeme;
        this.position = token.position;
    }

    public static class Position {
        public int line;
        public int column;

        public Position(int line, int column) {
            this.line = line;
            this.column = column;
        }

        public String toString() {
            return "[" + this.line + ":" + this.column + "]";
        }
    }

    public enum Type {
        Colon, ColonColon, Semicolon, FatArrow, Comma, Dot, DotDot, DotDotEqual,
        LeftParen, RightParen, LeftBrace, RightBrace, LeftBracket, RightBracket,
        Minus, Plus, Slash, Modulus, Asterisk, Greater, GreaterEqual, Less,
        LessEqual, Bang, BangEqual, Equal, EqualEqual, PlusEqual, MinusEqual,
        AsteriskEqual, SlashEqual, ModulusEqual, LogicAnd, LogicOr, BitwiseAnd,
        BitwiseOr, BitwiseXor, BitwiseAndEqual, BitwiseOrEqual, BitwiseXorEqual,
        As, Identifier, String, NumberInt, NumberFloat, Num, Int, Float, Str, Bool,
        Null, Vec, Map, Any, Loop, While, For, In, Match, If, Else, Let, Mut, Const,
        Enum, Struct, Fn, Impl, Trait, Mod, Type, Pub, Return, Continue, Break, False,
        True, Self, StaticSelf, Eof
    }

    public static String value(Type tokenType) {
        return switch (tokenType) {
            case Colon -> ":";
            case ColonColon -> "::";
            case Semicolon -> ";";
            case FatArrow -> "=>";
            case Comma -> ",";
            case Dot -> ".";
            case DotDot -> "..";
            case DotDotEqual -> "..=";

            case LeftParen -> "(";
            case RightParen -> ")";
            case LeftBrace -> "{";
            case RightBrace -> "}";
            case LeftBracket -> "[";
            case RightBracket -> "]";

            case Minus -> "-";
            case Plus -> "+";
            case Slash -> "/";
            case Modulus -> "%";
            case Asterisk -> "*";

            case Greater -> ">";
            case GreaterEqual -> ">=";
            case Less -> "<";
            case LessEqual -> "<=";

            case Bang -> "!";
            case BangEqual -> "!=";
            case Equal -> "=";
            case EqualEqual -> "==";

            case PlusEqual -> "+=";
            case MinusEqual -> "-=";
            case AsteriskEqual -> "*=";
            case SlashEqual -> "/=";
            case ModulusEqual -> "%=";

            case LogicAnd -> "&&";
            case LogicOr -> "||";

            case BitwiseAnd -> "&";
            case BitwiseOr -> "|";
            case BitwiseXor -> "^";

            case BitwiseAndEqual -> "&=";
            case BitwiseOrEqual -> "|=";
            case BitwiseXorEqual -> "^=";

            case As -> "as";

            case Num -> "num";
            case Int -> "int";
            case Float -> "float";
            case Str -> "str";
            case Bool -> "bool";
            case Null -> "null";
            case Vec -> "Vec";
            case Map -> "Map";
            case Any -> "any";

            case Loop -> "loop";
            case While -> "while";
            case For -> "for";
            case In -> "in";

            case Match -> "match";
            case If -> "if";
            case Else -> "else";

            case Let -> "let";
            case Mut -> "mut";
            case Const -> "const";

            case Enum -> "enum";
            case Struct -> "struct";
            case Fn -> "fn";
            case Impl -> "impl";
            case Trait -> "trait";
            case Mod -> "mod";
            case Type -> "type";

            case Pub -> "pub";

            case Return -> "return";
            case Continue -> "continue";
            case Break -> "break";

            case False -> "false";
            case True -> "true";

            case Self -> "self";
            case StaticSelf -> "Self";

            default -> "";
        };
    }
}
