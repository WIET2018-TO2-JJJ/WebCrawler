package WebCrawlerApp.controller;

import WebCrawlerApp.controller.pattern.Pattern;

import java.util.List;
import java.util.stream.Collectors;

public class PatternMatcher {

//    public PatternMatcher() {
//    }

    /**
     * Check every sentence if it passes positive pattern and does not pass negative one.
     *
     * @param sentences       List of sentences to check.
     * @param positivePattern Positive pattern
     */
    public static List<String> matchAgainstPatterns(List<String> sentences, Pattern positivePattern, Pattern negativePattern) {
        return sentences.parallelStream()
                .filter(positivePattern::match)
                .filter(s -> !negativePattern.match(s))
                .collect(Collectors.toList());
    }
}
