/**
 * Created by kuba on 15/11/2018
 */

package WebCrawlerApp.model;


import WebCrawlerApp.controller.PageDownloader;
import WebCrawlerApp.controller.PageParser;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Search {

    private List<String> pagesToVisit;
    private HashMap<String,Page> pagesVisited;
    private Integer acctualDepth;
    private String queryPositive;
    private String queryNegative;

    private ObservableList<Result> results;
    private StringProperty name;


    public Search(String name, String queryPositive, String queryNegative, Integer acctualDepth) {

        this.name = new SimpleStringProperty(name);
        this.pagesToVisit = new LinkedList<>();
        this.pagesVisited = new HashMap<>();
        this.queryPositive = queryPositive;
        this.queryNegative = queryNegative;
        this.acctualDepth = acctualDepth;
        this.results = FXCollections.observableArrayList();
    }

    public ObservableList<Result> getResults() {
        return results;
    }

    public StringProperty getNameProperty() {
        return name;
    }

    public String getName() { return name.getValue(); }

    public void search(){

        while (!pagesToVisit.isEmpty()) {

            if (!pagesVisited.containsKey(pagesToVisit.get(0))){

                if (acctualDepth >= 0) {
                    PageDownloader pageDownloader = new PageDownloader();
                    Document doc = pageDownloader.downloadPage(pagesToVisit.get(0), acctualDepth);
                    String signature = pageDownloader.getSignature();
                    Page page = new Page(pagesToVisit.get(0), signature, acctualDepth);
                    pagesToVisit.remove(0);
                    pagesVisited.put(page.URL, page);
                    acctualDepth--;

                    Elements links = pageDownloader.getURLs();
                    PageParser pageParser = new PageParser(queryPositive, queryNegative, doc);
                    for (Element link : links) {
                        if (pageParser.validateUrl(link))
                            pagesToVisit.add(link.absUrl("href"));
                    }
                    List<String> sentences = pageParser.searchForWords(doc);
                    for (String sentence : sentences) {
                        Result result = new Result(page.URL, sentence);
                        results.add(result);
                    }
                } else break;
            }
        }
    }
}