package WebCrawlerApp.controller.pattern;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Alternative implements PatternComponent {
    private List<PatternComponent> patternComponentList = new ArrayList<>();

    public Alternative() {

    }

    public void add(PatternComponent patternComponent) {
        patternComponentList.add(patternComponent);
    }

    public void remove(PatternComponent patternComponent) {
        patternComponentList.remove(patternComponent);
    }

    @Override
    public String toRegexpPatternString() {
        if (patternComponentList.size() == 0) {
            return "";
        }
        if (patternComponentList.size() == 1) {
            return patternComponentList.get(0).toRegexpPatternString();
        }

        return "(" +
                patternComponentList.stream()
                        .map(PatternComponent::toRegexpPatternString)
                        .collect(Collectors.joining("|")) +
                ")";
    }
}
