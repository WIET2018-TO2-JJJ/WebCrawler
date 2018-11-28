import WebCrawlerApp.controller.PatternMatcher;
import WebCrawlerApp.controller.pattern.SentencePattern;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SentencePatternMatcherTest {
    @Test
    public void wordsNumberAndOrTest() {
        List<String> list = new ArrayList<>();
        list.add("123123");
        list.add("fdads fdassdf");
        list.add("asd sdf dfg");
        list.add("asd sdf dfg fdg");

        SentencePattern p = new SentencePattern("<1> | <3>");
        int len = PatternMatcher.matchAgainstPatterns(list, p, null).size();
        assertEquals(len, 2);
    }

    @Test
    public void basicTest() {
        List<String> list = new ArrayList<>();
        list.add("123123");
        list.add("fdads fdassdf");
        list.add("asd sdf dfg");
        list.add("asd sdf dfg fdg");

        SentencePattern p = new SentencePattern("fdads <1>");
        int len = PatternMatcher.matchAgainstPatterns(list, p, null).size();
        assertEquals(len, 1);
    }
}
