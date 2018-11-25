import WebCrawlerApp.controller.PatternMatcher;
import WebCrawlerApp.controller.pattern.Pattern;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PatternMatcherTest {
    @Test
    public void matchTest() {
        List<String> list = new ArrayList<>();
        list.add("123123");
        list.add("fdads fdassdf");
        list.add("asd sdf dfg");
        list.add("asd sdf dfg fdg");

        Pattern p = new Pattern("<1> | <3>");
        int len = PatternMatcher.matchAgainstPatterns(list, p, null).size();
        assertEquals(len, 2);

    }
}
