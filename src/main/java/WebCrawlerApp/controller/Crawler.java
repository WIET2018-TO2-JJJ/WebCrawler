/**
 * Created by kuba on 10/12/2018
 */

package WebCrawlerApp.controller;


import WebCrawlerApp.model.Page;
import WebCrawlerApp.model.Result;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Crawler implements Runnable{
    private String baseURL;
    private Integer depth;
    private ObservableList<Result> results;
    private List<String> pagesToVisit = new ArrayList<>();
    private HashMap<String,Page> pagesVisited = new HashMap<>();
    private String queryPositive;
    private String queryNegative;

    public Crawler(String baseURL, Integer depth, String queryNegative, String queryPositive, ObservableList<Result> results) {
        this.queryNegative = queryNegative;
        this.queryPositive = queryPositive;
        this.baseURL = baseURL;
        this.depth = depth;
        this.results = results;
        pagesToVisit.add(baseURL);
    }

    private Document downloadPage(String URL){
        Connection connection = Jsoup.connect(URL);
        Document document = null;
        try {
            document = connection.get();
        } catch (IOException e) {
            System.out.println("connection error");
        }
        //System.out.println(document.text());
        return document;
    }

    @Override
    public void run(){
        List<String> levelURLs = new ArrayList<>();
        for(int i=depth; i>0; i--){
            while(!pagesToVisit.isEmpty()) {
                if (!pagesVisited.containsKey(pagesToVisit.get(0))) {
                    Page page = new Page(pagesToVisit.get(0), i);
                    pagesToVisit.remove(0);
                    pagesVisited.put(page.getURL(), page);
                    Document doc = downloadPage(page.getURL());
                    Elements links = doc.select("a[href]");     // getURLs()

                    PageParser pageParser = new PageParser(queryPositive, queryNegative);

                    for (Element link : links) {
                        levelURLs.add(link.absUrl("href"));
                    }

                    List<String> sentences = pageParser.searchForWords(doc);
                    for (String sentence : sentences) {
                        Result result = new Result(page.getURL(), sentence);
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