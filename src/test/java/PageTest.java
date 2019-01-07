import WebCrawlerApp.controller.PageProcessor;
import WebCrawlerApp.controller.PatternMatcher;
import WebCrawlerApp.controller.pattern.SentencePattern;
import WebCrawlerApp.model.Query;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.text.BreakIterator;
import java.util.*;

import static junit.framework.TestCase.assertEquals;

public class PageTest {

    private Document doc;
    private PageProcessor pageProcessor;
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
        Query query = new Query("* artykuł *", "* dobry *");
        pageProcessor = new PageProcessor(query);
        sentences = pageProcessor.searchForWords(doc);
        assertEquals(5, sentences.size());
    }

    @Test
    public void matchTest() {
        List<String> sentences = new ArrayList<>();
        sentences.add("Kapitan Franklin był na morzu.");
        sentences.add("Jego statek rozbił się.");
        SentencePattern positive = new SentencePattern("kapitan *");
        List<String> results = PatternMatcher.matchAgainstPatterns(sentences, positive, null);
        assertEquals(1, results.size());
    }

    @Test
    public void searchForWordsMatchEverythingTest() {
        SentencePattern positivePattern = new SentencePattern("*");
        List<String> sentences = new ArrayList<>();
        Elements elements = doc.body().select("p,li").append("\n");
        BreakIterator bi = BreakIterator.getSentenceInstance(new Locale("pl"));

        for(Element element : elements) {
            bi.setText(element.text());
            int index = 0;
            while (bi.next() != BreakIterator.DONE) {
                String sentence = element.text().substring(index, bi.current());
                System.out.println("Sentence: " + sentence);
                sentences.add(sentence);
                index = bi.current();
            }
        }
        List<String> results = PatternMatcher.matchAgainstPatterns(sentences, positivePattern, null);
        assertEquals(sentences.size(), results.size());
    }

    @Test
    public void searchForWordsMatchNothingTest() {
        SentencePattern negativePattern = new SentencePattern("*");
        List<String> sentences = new ArrayList<>();
        Elements elements = doc.body().select("p,li").append("\n");
        BreakIterator bi = BreakIterator.getSentenceInstance(new Locale("pl"));

        for(Element element : elements) {
            bi.setText(element.text());
            int index = 0;
            while (bi.next() != BreakIterator.DONE) {
                String sentence = element.text().substring(index, bi.current());
                System.out.println("Sentence: " + sentence);
                sentences.add(sentence);
                index = bi.current();
            }
        }
        List<String> results = PatternMatcher.matchAgainstPatterns(sentences, null, negativePattern);
        assertEquals(0, results.size());
    }

}