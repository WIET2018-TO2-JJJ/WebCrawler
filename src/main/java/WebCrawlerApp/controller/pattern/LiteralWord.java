package WebCrawlerApp.controller.pattern;

import java.util.regex.Pattern;

public class LiteralWord implements PatternComponent {
    private final String word;

    public static final String afterWordText = "\\s*[\\,\\-\\~]*\\s*";
    public static final String patternFormat = "\\b%s%s";

    public LiteralWord(String word) {
        if(word.length() == 0) {
            throw new IllegalArgumentException("Word must contain at least one character.");
        }
        this.word = word;
    }

    @Override
    public String toRegexpPatternString() {
        return String.format (patternFormat, Pattern.quote(word), afterWordText);
    }
}
