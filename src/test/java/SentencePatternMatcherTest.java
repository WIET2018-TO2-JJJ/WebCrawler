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

        SentencePattern p = new SentencePattern("(<1> | <3>");
        List<String> res = PatternMatcher.matchAgainstPatterns(list, p, null);
        assertEquals(2, res.size());
    }

    @Test
    public void basicTest() {
        List<String> list = new ArrayList<>();
        list.add("123123");
        list.add("fdads fdassdf");
        list.add("asd sdf dfg");
        list.add("asd sdf dfg fdg");

        SentencePattern p = new SentencePattern("fdads <1>");
        List<String> res =  PatternMatcher.matchAgainstPatterns(list, p, null);
        assertEquals(1, res.size());
    }

    @Test
    public void parenthesesAndAlternativeTest() {
        List<String> list = new ArrayList<>();
        list.add("dom");
        list.add("złom");
        list.add("www dom");
        list.add("www złom");
        list.add("www www dom");
        list.add("www www złom");

        SentencePattern p = new SentencePattern("<1> (dom|złom)");
        List<String> res = PatternMatcher.matchAgainstPatterns(list, p, null);

        assertEquals(2, res.size());
    }
}
