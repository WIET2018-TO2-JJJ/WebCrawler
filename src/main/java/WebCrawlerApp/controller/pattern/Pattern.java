package WebCrawlerApp.controller.pattern;

import java.util.ArrayList;
import java.util.List;

public class Pattern {

    java.util.regex.Pattern compiledPattern;

    public Pattern(String patternString) {
        Lexer lexer = new Lexer();

        ArrayList<Lexer.Token> patternTokens = lexer.lex(patternString);

        syntaxCheck(patternTokens);

        compiledPattern = compile(patternTokens);
    }

    public boolean match(String input) {
        return compiledPattern.matcher(input).find();
    }

    private void syntaxCheck(List<Lexer.Token> tokenList) {
        for (int i = 1; i < tokenList.size(); ++i) {
            Lexer.TokenType previousTokenType = tokenList.get(i - 1).type;
            Lexer.TokenType currentTokenType = tokenList.get(i).type;

            if (previousTokenType == Lexer.TokenType.PARENTLEFT && currentTokenType == Lexer.TokenType.OR) {
                // Check for `( |`
                throw new InvalidSyntaxException("Literal pattern expected. Got ` ... ( | ...`");
            } else if (previousTokenType == Lexer.TokenType.OR && currentTokenType == Lexer.TokenType.PARENTRIGHT) {
                // Check for `| )`
                throw new InvalidSyntaxException("Literal pattern expected. Got ` ... | ) ...`");
            } else if (previousTokenType == Lexer.TokenType.PARENTLEFT && currentTokenType == Lexer.TokenType.PARENTRIGHT) {
                // Check for `( )`
                throw new InvalidSyntaxException("Literal pattern expected. Got ` ... ( ) ...`");
            } else if (previousTokenType == Lexer.TokenType.OR && currentTokenType == Lexer.TokenType.OR) {
                // Check for `| |`
                throw new InvalidSyntaxException("Literal pattern expected. Got ` ... | | ...`");
            }
        }
    }

    private java.util.regex.Pattern compile(ArrayList<Lexer.Token> tokenList) {
        // TODO: Implement
        return java.util.regex.Pattern.compile(".*"); // ACCEPT EVERYTHING, FOR NOW
    }

    public static class InvalidSyntaxException extends RuntimeException {
        public InvalidSyntaxException(String message) {
            super(message);
        }
    }
}
