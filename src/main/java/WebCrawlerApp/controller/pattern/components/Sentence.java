package WebCrawlerApp.controller.pattern.components;

import java.util.List;

public class Sentence extends PatternSequence {
    private static final String endOfSentencePattern = "[\\,\\.\\!\\?\\~]*";

    public Sentence() {

    }

    @Override
    public String toRegexpPatternString() {
        return super.toRegexpPatternString() + endOfSentencePattern;
    }

    public String toFullRegexpPatternString() {
        return "^" + this.toRegexpPatternString() + "$";
    }
}
