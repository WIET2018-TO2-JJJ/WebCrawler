package WebCrawlerApp.controller.pattern.components;

public class WildcardPatternComponent implements PatternComponent {
    public WildcardPatternComponent() {
    }

    @Override
    public String toRegexpPatternString() {
        return ".*";
    }
}
