package WebCrawlerApp.controller.pattern.lexer;

public enum TokenType {
    WHITESPACE("\\s"),
    WILDCARD("\\*"),
    WORDNUMBERWILDCARD("\\<\\s*\\d+\\s*\\>"),
    WORD("(\\p{L}|[\\_\\/\\d])+"),
    PARENTLEFT("\\("),
    PARENTRIGHT("\\)"),
    OR("\\|");

    public final String pattern;

    TokenType(String pattern) {
        this.pattern = pattern;
    }
}