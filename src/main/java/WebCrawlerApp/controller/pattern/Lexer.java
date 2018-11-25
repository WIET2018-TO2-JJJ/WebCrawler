package WebCrawlerApp.controller.pattern;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Lexer {
    private Pattern lexPattern;
    private Pattern validationPattern;
    private Pattern validationErrorPositionPattern;

    public Lexer() {
        String tokenPattens =
                Arrays.asList(TokenType.values()).stream()
                        .map(tokenType -> String.format("(?<%s>%s)", tokenType.name(), tokenType.pattern))
                        .collect(Collectors.joining("|"));

        this.lexPattern = Pattern.compile(tokenPattens);

        this.validationPattern = Pattern.compile("^(" + tokenPattens + ")*$");
        this.validationErrorPositionPattern = Pattern.compile("^(" + tokenPattens + ")*");
    }

    private void validate(String input) throws LexicalErrorException {
        Matcher matcher = this.validationPattern.matcher(input);
        if (!matcher.find()) {
            matcher = this.validationErrorPositionPattern.matcher(input);
            if (matcher.find()) {
                throw new LexicalErrorException(matcher.group(0).length());
            } else {
                throw new RuntimeException("Internal error. Check token patterns.");
            }
        }
    }

    public ArrayList<Token> lex(String input) throws LexicalErrorException {
        validate(input);

        ArrayList<Token> tokens = new ArrayList<>();

        // Begin matching tokens
        Matcher matcher = this.lexPattern.matcher(input);
        while (matcher.find()) {
            if (matcher.group(TokenType.WHITESPACE.name()) != null)
                continue;
            else if (matcher.group(TokenType.WILDCARD.name()) != null) {
                tokens.add(new Token(TokenType.WILDCARD, matcher.group(TokenType.WILDCARD.name())));
                continue;
            } else if (matcher.group(TokenType.WORDNUMBERWILDCARD.name()) != null) {
                tokens.add(new Token(TokenType.WORDNUMBERWILDCARD, matcher.group(TokenType.WORDNUMBERWILDCARD.name())));
                continue;
            } else if (matcher.group(TokenType.WORD.name()) != null) {
                tokens.add(new Token(TokenType.WORD, matcher.group(TokenType.WORD.name())));
                continue;
            } else if (matcher.group(TokenType.PARENTLEFT.name()) != null) {
                tokens.add(new Token(TokenType.PARENTLEFT, matcher.group(TokenType.PARENTLEFT.name())));
                continue;
            } else if (matcher.group(TokenType.PARENTRIGHT.name()) != null) {
                tokens.add(new Token(TokenType.PARENTRIGHT, matcher.group(TokenType.PARENTRIGHT.name())));
                continue;
            } else if (matcher.group(TokenType.OR.name()) != null) {
                tokens.add(new Token(TokenType.OR, matcher.group(TokenType.OR.name())));
                continue;
            }
        }

        return tokens;
    }

    public enum TokenType {
        WHITESPACE("\\s"),
        WILDCARD("\\*"),
        WORDNUMBERWILDCARD("\\<\\s*\\d+\\s*\\>"),
        WORD("\\p{L}+"),
        PARENTLEFT("\\("),
        PARENTRIGHT("\\)"),
        OR("\\|");

        public final String pattern;

        TokenType(String pattern) {
            this.pattern = pattern;
        }
    }

    public static class Token {
        public final TokenType type;
        public final String data;

        public Token(TokenType type, String data) {
            this.type = type;
            if (type == TokenType.WORDNUMBERWILDCARD) {
                this.data = data.replaceAll("\\s", "");
            } else {
                this.data = data;
            }
        }

        @Override
        public String toString() {
            return String.format("(%s, %s)", type.name(), data);
        }
    }

    public static class LexicalErrorException extends RuntimeException {
        public final int errorPosition;
        public LexicalErrorException(int errorPosition) {
            super();
            this.errorPosition = errorPosition;
        }
    }
}
