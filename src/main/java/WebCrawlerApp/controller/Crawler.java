/**
 * Created by kuba on 10/12/2018
 */

package WebCrawlerApp.controller;


import WebCrawlerApp.controller.pattern.SentencePattern;
import WebCrawlerApp.model.Page;
import WebCrawlerApp.model.Query;
import WebCrawlerApp.model.Result;
import javafx.collections.ObservableList;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Crawler implements Runnable {
    private Page startPage;
    private ObservableList<Result> results;
    private List<String> pagesToVisit = new ArrayList<>();
    private HashMap<String, Page> pagesVisited = new HashMap<>();
    private Query query;

    public Crawler(Page startPage, Query query, ObservableList<Result> results) {
        this.query = query;
        this.startPage = startPage;
        this.results = results;
        pagesToVisit.add(startPage.getURL());
    }

    private Document downloadPage(String URL) {
        Connection connection = Jsoup.connect(URL);
        Document document = null;
        try {
            document = connection.get();
        } catch (IOException e) {
            System.out.println("connection error");
        }
        return document;
    }

    @Override
    public void run() {
        List<String> levelURLs = new ArrayList<>();

        for (int i = startPage.getDepth(); i > 0; i--) {
            while (!pagesToVisit.isEmpty()) {
                if (!pagesVisited.containsKey(pagesToVisit.get(0))) {
                    Page page = new Page(pagesToVisit.get(0), i);
                    pagesToVisit.remove(0);
                    pagesVisited.put(page.getURL(), page);
                    Document doc = downloadPage(page.getURL());
                    Elements links = doc.select("a[href]");

                    PageProcessor pageProcessor = new PageProcessor(query);

                    for (Element link : links) {
                        levelURLs.add(link.absUrl("href"));
                    }

                    List<String> sentences = pageProcessor.searchForWords(doc);
                    for (String sentence : sentences) {
                        Result result = new Result(page.getURL(),startPage.getURL(), sentence);
                        results.add(result);
                    }
                } else {
                    pagesToVisit.remove(0);
                }
            }
            pagesToVisit.addAll(levelURLs);
            levelURLs.clear();
        }
    }
}