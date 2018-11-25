import WebCrawlerApp.controller.PageDownloader;
import WebCrawlerApp.controller.PageParser;
import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;
import org.jsoup.nodes.Document;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

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
        String queryPositive = "aktor";
        String queryNegative = "wojna";
        pageParser = new PageParser(queryPositive,queryNegative,doc);
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
        List<String> results = pageParser.searchForWords(doc);
        assertEquals(results.size(),4);
    }

}
