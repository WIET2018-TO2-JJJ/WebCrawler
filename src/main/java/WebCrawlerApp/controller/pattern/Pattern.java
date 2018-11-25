package WebCrawlerApp.controller.pattern;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class Pattern {

    java.util.regex.Pattern compiledPattern;

    public Pattern(String patternString) {
        Lexer lexer = new Lexer();

        ArrayList<Token> patternTokens = lexer.lex(patternString);

        syntaxCheck(patternTokens);

        compiledPattern = compile(patternTokens);
    }

    public boolean match(String input) {
        Matcher m = compiledPattern.matcher(input);
        return m.matches();
    }

    private void syntaxCheck(List<Token> tokenList) {
        for (int i = 1; i < tokenList.size(); ++i) {
            TokenType previousTokenType = tokenList.get(i - 1).type;
            TokenType currentTokenType = tokenList.get(i).type;

            if (previousTokenType == TokenType.PARENTLEFT && currentTokenType == TokenType.OR) {
                // Check for `( |`
                throw new InvalidSyntaxException("Literal pattern expected. Got ` ... ( | ...`");
            } else if (previousTokenType == TokenType.OR && currentTokenType == TokenType.PARENTRIGHT) {
                // Check for `| )`
                throw new InvalidSyntaxException("Literal pattern expected. Got ` ... | ) ...`");
            } else if (previousTokenType == TokenType.PARENTLEFT && currentTokenType == TokenType.PARENTRIGHT) {
                // Check for `( )`
                throw new InvalidSyntaxException("Literal pattern expected. Got ` ... ( ) ...`");
            } else if (previousTokenType == TokenType.OR && currentTokenType == TokenType.OR) {
                // Check for `| |`
                throw new InvalidSyntaxException("Literal pattern expected. Got ` ... | | ...`");
            } else if (previousTokenType == TokenType.WILDCARD && currentTokenType == TokenType.WILDCARD) {
                // Check for `* *`
                throw new InvalidSyntaxException("Literal pattern expected. Got ` ... * * ...`");
            }
        }
    }

    private String regexPatternFromToken(Token token) {
        final String afterWordText = "\\s*[\\,\\-\\~]*\\s*";
        switch (token.type) {
            case WILDCARD:
                return ".*";
            case WORDNUMBERWILDCARD:
                Matcher matcher = java.util.regex.Pattern.compile("^.*(?<num>\\d+).*$").matcher(token.data);
                if (!matcher.find()) {
                    throw new InvalidSyntaxException("Number expected in word number wildcard");
                }
                int words = Integer.parseInt(matcher.group("num"));
                if (words < 1) {
                    throw new InvalidSyntaxException("Word number wildcard has to have positive number in pattern.");
                }
                return String.format("(\\b[\\p{L}_\\-\\/\\d]+" + afterWordText + "){%d}", words);
            case WORD:
                return "\\b" + java.util.regex.Pattern.quote(token.data) + afterWordText;
            default:
                throw new IllegalArgumentException("Unexpected token type.");
        }
    }

    private java.util.regex.Pattern compile(ArrayList<Token> tokenList) {
        StringBuilder patternStringBuilder = new StringBuilder();

        patternStringBuilder.append("^");

        for (int i = 0; i < tokenList.size(); ++i) {
            Token token = tokenList.get(i);
            switch(token.type) {
                case WILDCARD:
                case WORD:
                case WORDNUMBERWILDCARD:
                    patternStringBuilder.append(regexPatternFromToken(token));
                    break;
                case PARENTRIGHT:
                    throw new InvalidSyntaxException("Unexpected parentheses closing without opening.");
                case OR:
                    patternStringBuilder.append("|");
                    break;
                default:
                    throw new InvalidSyntaxException("Unexpected token type: " + token.type.name());
            }
        }

        patternStringBuilder.append("[\\,\\.\\!\\?\\~]*$");

        return java.util.regex.Pattern.compile(patternStringBuilder.toString());
    }

    public static class InvalidSyntaxException extends RuntimeException {
        public InvalidSyntaxException(String message) {
            super(message);
        }
    }
}
