package WebCrawlerApp.controller;

import WebCrawlerApp.controller.pattern.SentencePattern;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PatternMatcher {

    /**
     * Check every sentence if it passes positive pattern and does not pass negative one.
     *
     * @param sentences       List of sentences to check.
     * @param positiveSentencePattern Positive pattern
     */
    public static List<String> matchAgainstPatterns(List<String> sentences, SentencePattern positiveSentencePattern, SentencePattern negativeSentencePattern) {
        Stream<String> s = sentences.parallelStream();

        if (positiveSentencePattern != null) {
            s = s.filter(positiveSentencePattern::match);
        }

        if(negativeSentencePattern != null) {
            s = s.filter(sentence -> !negativeSentencePattern.match(sentence));
        }

        return s.collect(Collectors.toList());
    }
}
