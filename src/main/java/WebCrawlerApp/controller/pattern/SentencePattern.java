package WebCrawlerApp.controller.pattern;


import WebCrawlerApp.controller.pattern.components.*;
import WebCrawlerApp.controller.pattern.lexer.Lexer;
import WebCrawlerApp.controller.pattern.lexer.Token;
import WebCrawlerApp.controller.pattern.lexer.TokenType;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SentencePattern {
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

    @Override
    public String toString() {
        return compiledPattern.pattern();
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

    private List<PatternComponent> compileTokens(List<Token> tokenList, int begin, int end) {
        assert (0 <= begin);
        assert (begin < end);

        List<PatternComponent> components = new LinkedList<>();

        for (int i = begin; i < end; ++i) {
            Token token = tokenList.get(i);
            switch (token.type) {
                case WILDCARD:
                    components.add(new WildcardPatternComponent());
                    break;
                case WORDNUMBERWILDCARD:
                    Matcher matcher = Pattern.compile(wordNumberWildcardExtractionPattern).matcher(token.data);
                    if (!matcher.find()) {
                        throw new InvalidSyntaxException("Number expected in word number wildcard");
                    }
                    int words = Integer.parseInt(matcher.group("num"));
                    if (words < 1) {
                        throw new InvalidSyntaxException("Word number wildcard has to have positive number in pattern.");
                    }
                    components.add(new AnyWordsPatternComponent(words));
                    break;
                case WORD:
                    components.add(new LiteralWord(token.data));
                    break;
                case PARENTLEFT:
                    int newEnd = begin;
                    int depth = 1;
                    for(int j = i+1; j < end && depth > 0; ++j) {
                        TokenType tt = tokenList.get(j).type;
                        if(tt == TokenType.PARENTLEFT) {
                            depth += 1;
                        } else if (tt == TokenType.PARENTRIGHT) {
                            depth -= 1;
                        }

                        newEnd = j;
                    }
                    newEnd += 1;
                    // TODO: zmenić kompilację na uwzględnianie alternatywy dla ciągów tokenów (split by `|`)
                    AlternativeParentheses alternativeParentheses = new AlternativeParentheses();
                    alternativeParentheses.addAll(compileTokens(tokenList, i+1, newEnd));
                    components.add(alternativeParentheses);
                    i = newEnd;
                    break;
                default:
                    // omit OR, WHITESPACE, RIGHTPAR
                    break;
            }
        }

        return components;
    }

    private Pattern compile(ArrayList<Token> tokenList, boolean caseInsensitive) {
        Sentence s = new Sentence();
        s.addAll(compileTokens(tokenList, 0, tokenList.size()));

        return Pattern.compile(s.toFullRegexpPatternString(),

                caseInsensitive ? Pattern.CASE_INSENSITIVE : 0);
    }

    public static class InvalidSyntaxException extends RuntimeException {
        InvalidSyntaxException(String message) {
            super(message);
        }
    }
}
