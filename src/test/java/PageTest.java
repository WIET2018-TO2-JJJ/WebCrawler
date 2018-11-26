import WebCrawlerApp.controller.PageDownloader;
import WebCrawlerApp.controller.PageParser;
import WebCrawlerApp.controller.PatternMatcher;
import WebCrawlerApp.controller.pattern.Pattern;
import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;
import org.jsoup.nodes.Document;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.format;

public class PageTest {

    private PageDownloader pageDownloader;
    private Document doc;
    private PageParser pageParser;

    @Before
    public void setup() throws IOException{
        pageDownloader = new PageDownloader();
        File input = new File("./pageForTest/Wikipedia:Strona główna.html");
        doc = Jsoup.parse(input, "UTF-8", "https://pl.wikipedia.org");
        pageDownloader.setDocument(doc);
    }

    @Test
    public void getURLsTest(){
        Elements elements = pageDownloader.getURLs();
        List<String> urls = new LinkedList<>();
        for (Element element : elements){
            urls.add(element.absUrl("href"));
        }
        assertEquals(elements.size(),urls.size());
    }

    @Test
    public void searchForWordsTest(){
        String body = doc.body().text();
        List<String> sentences = new ArrayList<>();

        Pattern positive = new Pattern("kapitan");
        List<String> results = PatternMatcher.matchAgainstPatterns(sentences,positive,null);
        assertEquals(results.size(),5);
    }

    @Test
    public void matchTest(){
        List<String> sentences = new ArrayList<>();
        sentences.add("Kapitan Franklin był na morzu.");
        sentences.add("Jego statek rozbił się");

        Pattern positive = new Pattern("kapitan");
        List<String> results = PatternMatcher.matchAgainstPatterns(sentences,positive,null);
        assertEquals(results.size(),1);
    }

    @Test
    public void searchForWordsMatchEverythingTest(){
        String body = doc.body().text();
        List<String> sentences = Arrays.asList(body.split("\\. | • "));

        Pattern positive = new Pattern("*");
        List<String> results = PatternMatcher.matchAgainstPatterns(sentences,positive,null);
        assertEquals(results.size(),sentences.size());
    }

    @Test
    public void searchForWordsMatchNothingTest(){
        String body = doc.body().text();
        List<String> sentences = Arrays.asList(body.split("\\. | • "));

        Pattern negative = new Pattern("*");
        List<String> results = PatternMatcher.matchAgainstPatterns(sentences,null,negative);
        assertEquals(results.size(),0);
    }

}