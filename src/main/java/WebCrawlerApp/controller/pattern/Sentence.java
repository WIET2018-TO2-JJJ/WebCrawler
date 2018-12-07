package WebCrawlerApp.controller.pattern;

import java.util.ArrayList;
import java.util.List;

public class Sentence implements PatternComponent {
    private static final String endOfSentencePattern = "[\\,\\.\\!\\?\\~]*";
    private List<PatternComponent> patternComponentList = new ArrayList<>();

    public Sentence() {

    }

    public void add(PatternComponent patternComponent) {
        patternComponentList.add(patternComponent);
    }

    public void remove(PatternComponent patternComponent) {
        patternComponentList.remove(patternComponent);
    }

    @Override
    public String toRegexpPatternString() {
        StringBuilder patternStringBuilder = new StringBuilder();

        for (PatternComponent patternComponent : patternComponentList) {
            patternStringBuilder.append(patternComponent.toRegexpPatternString());
        }

        patternStringBuilder.append(endOfSentencePattern);

        return patternStringBuilder.toString();
    }

    public String toFullRegexpPatternString() {
        return "^" + this.toRegexpPatternString() + "$";
    }
}
