package WebCrawlerApp.controller.pattern;

public class WildcardPatternComponent implements PatternComponent {
    public WildcardPatternComponent() {
    }

    @Override
    public String toRegexpPatternString() {
        return ".*";
    }
}
