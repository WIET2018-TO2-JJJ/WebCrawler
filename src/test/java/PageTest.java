import WebCrawlerApp.controller.PageParser;
import WebCrawlerApp.controller.PatternMatcher;
import WebCrawlerApp.controller.pattern.SentencePattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class PageTest {

    private Document doc;
    private PageParser pageParser;
    private Elements elements;

    @Before
    public void setup() throws IOException {
        File input = new File("./pageForTest/Wikipedia:Strona główna.html");
        doc = Jsoup.parse(input, "UTF-8", "https://pl.wikipedia.org");
        elements = doc.select("a[href]");
    }

    @Test
    public void getURLsTest() {
        List<String> urls = new LinkedList<>();
        for (Element element : elements) {
            urls.add(element.absUrl("href"));
        }
        assertEquals(elements.size(), urls.size());
    }

    @Test
    public void searchForWordsTest() {
        List<String> sentences;
        String positive = "* artykuł *";
        String negative = "* dobry *";
        pageParser = new PageParser(positive,negative);
        sentences = pageParser.searchForWords(doc);
        assertEquals(8, sentences.size());
    }

    @Test
    public void matchTest() {
        List<String> sentences = new ArrayList<>();
        sentences.add("Kapitan Franklin był na morzu.");
        sentences.add("Jego statek rozbił się");

        SentencePattern positive = new SentencePattern("kapitan *");
        List<String> results = PatternMatcher.matchAgainstPatterns(sentences, positive, null);
        assertEquals(1, results.size());
    }

    @Test
    public void searchForWordsMatchEverythingTest() {
        String body = doc.body().text();
        List<String> sentences = Arrays.asList(body.split("\\. | • "));

        SentencePattern positive = new SentencePattern("*");
        List<String> results = PatternMatcher.matchAgainstPatterns(sentences, positive, null);
        assertEquals(results.size(), sentences.size());
    }

    @Test
    public void searchForWordsMatchNothingTest() {
        String body = doc.body().text();
        List<String> sentences = Arrays.asList(body.split("\\. | • "));

        SentencePattern negative = new SentencePattern("*");
        List<String> results = PatternMatcher.matchAgainstPatterns(sentences, null, negative);
        assertEquals(0, results.size());
    }

}
