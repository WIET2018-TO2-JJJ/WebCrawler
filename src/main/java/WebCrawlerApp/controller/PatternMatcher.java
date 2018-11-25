package WebCrawlerApp.controller;

import WebCrawlerApp.controller.pattern.Pattern;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PatternMatcher {

    /**
     * Check every sentence if it passes positive pattern and does not pass negative one.
     *
     * @param sentences       List of sentences to check.
     * @param positivePattern Positive pattern
     */
    public static List<String> matchAgainstPatterns(List<String> sentences, Pattern positivePattern, Pattern negativePattern) {
        Stream<String> s = sentences.parallelStream();

        if (positivePattern != null) {
            s = s.filter(positivePattern::match);
        }

        if(negativePattern != null) {
            s = s.filter(s -> !negativePattern.match(s));
        }

        return s.collect(Collectors.toList());
    }
}
