package com.andre1337.lust;

import com.andre1337.lust.parser.lexer.Lexer;
import com.andre1337.lust.parser.token.Token;
import com.andre1337.lust.utils.Tuple;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        String source = """
        struct Entry<K, V> {
            pub key: K,
            pub value: V,
            pub next: Entry<K, V>,
        }
        
        impl Entry<K, V> {
            pub fn new(key: K, value: V, next: Self): Self {
                return Self {
                    key: key,
                    value: value,
                    next: next,
                };
            }
            
            pub fn getKey(self): K {
                return self.key;
            }
            
            pub fn getValue(self): V {
                return self.value;
            }
            
            pub fn getNextEntry(self): Self {
                return self.next;
            }
        }
        """;

        Lexer lexer = new Lexer(source);
        Tuple<List<Token>, List<String>> lexerResult = lexer.tokenize();
        List<Token> tokens = lexerResult.getFirst();
        List<String> errors = lexerResult.getSecond();

        for (Token token : tokens) {
            System.out.println(token.tokenType + ": " + token.lexeme + " @ " + token.position);
        }

        for (String error : errors) {
            System.out.println(error);
        }
    }
}
