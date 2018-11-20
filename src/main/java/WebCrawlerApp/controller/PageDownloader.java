package WebCrawlerApp.controller;

import WebCrawlerApp.model.Page;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;
import static com.sun.activation.registries.LogSupport.log;

public class PageDownloader {

    private Page downloadPage(String URL,Integer depth) throws IOException{
        Document doc = Jsoup.connect(URL).get();
        Page page = new Page(URL,"",depth);
        return page;
    }

    private void getURLs(){

    }
}
