package WebCrawlerApp.controller.pattern.lexer;

public class Token {
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
