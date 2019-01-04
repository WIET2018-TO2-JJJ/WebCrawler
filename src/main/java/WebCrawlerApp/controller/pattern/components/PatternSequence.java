package WebCrawlerApp.controller.pattern.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PatternSequence implements PatternComponent {

    private List<PatternComponent> patternComponentList = new ArrayList<>();

    public PatternSequence() {

    }

    public void add(PatternComponent patternComponent) {
        patternComponentList.add(patternComponent);
    }

    public void addAll(Collection<PatternComponent> patternComponents) {
        patternComponentList.addAll(patternComponents);
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

        return patternStringBuilder.toString();
    }

    public List<PatternComponent> getComponents() {
        return patternComponentList;
    }
}
