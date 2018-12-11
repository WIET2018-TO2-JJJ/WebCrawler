package WebCrawlerApp.controller.pattern.components;

public class AnyWordsPatternComponent implements PatternComponent {
    private final int words;

    public static final String afterWordText = "\\s*[\\,\\-\\~]*\\s*";
    public static final String patternFormat = "(\\b[\\p{L}_\\-\\/\\d]+%s){%d}";

    public AnyWordsPatternComponent(int words) {
        if (words < 1) {
            throw new IllegalArgumentException("Number of words must be positive and greater than 1.");
        }
        this.words = words;
    }

    @Override
    public String toRegexpPatternString() {
        return String.format(patternFormat, afterWordText, words);
    }
}
