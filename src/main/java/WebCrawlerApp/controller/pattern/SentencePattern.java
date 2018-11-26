package WebCrawlerApp.controller.pattern;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SentencePattern {
    private static final String endOfSentencePattern = "[\\,\\.\\!\\?\\~]*";
    private static final String afterWordText = "\\s*[\\,\\-\\~]*\\s*";
    private static final String wildcardPattern = ".*";
    private static final String wordNumberWildcardExtractionPattern = "^.*(?<num>\\d+).*$";
    private Pattern compiledPattern;

    public SentencePattern(String patternString) {
        this(patternString, true);
    }

    public SentencePattern(String patternString, boolean caseInsensitive) {
        Lexer lexer = new Lexer();

        ArrayList<Token> patternTokens = lexer.lex(patternString);

        syntaxCheck(patternTokens);

        compiledPattern = compile(patternTokens, caseInsensitive);
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
        switch (token.type) {
            case WILDCARD:
                return wildcardPattern;
            case WORDNUMBERWILDCARD:
                Matcher matcher = Pattern.compile(wordNumberWildcardExtractionPattern).matcher(token.data);
                if (!matcher.find()) {
                    throw new InvalidSyntaxException("Number expected in word number wildcard");
                }
                int words = Integer.parseInt(matcher.group("num"));
                if (words < 1) {
                    throw new InvalidSyntaxException("Word number wildcard has to have positive number in pattern.");
                }
                return String.format("(\\b[\\p{L}_\\-\\/\\d]+%s){%d}", afterWordText, words);
            case WORD:
                return "\\b" + Pattern.quote(token.data) + afterWordText;
            default:
                throw new IllegalArgumentException("Unexpected token type.");
        }
    }

    private Pattern compile(ArrayList<Token> tokenList, boolean caseInsensitive) {
        StringBuilder patternStringBuilder = new StringBuilder();

        patternStringBuilder.append("^");

        for (Token token : tokenList) {
            switch (token.type) {
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

        patternStringBuilder.append(endOfSentencePattern + "$");

        return Pattern.compile(patternStringBuilder.toString(),
                caseInsensitive ? Pattern.CASE_INSENSITIVE : 0);
    }

    public static class InvalidSyntaxException extends RuntimeException {
        InvalidSyntaxException(String message) {
            super(message);
        }
    }
}
