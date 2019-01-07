/**
 * Created by kuba on 15/11/2018
 */

package WebCrawlerApp.model;

import WebCrawlerApp.controller.Crawler;
import WebCrawlerApp.controller.pattern.SentencePattern;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Search {
    private List<String> pagesToVisit;
    private Integer depth;
    private Query query;
    private ObservableList<Result> results;
    private StringProperty name;
    private ExecutorService service;

    private String pattern  = "dd-M-yyyy hh:mm:ss";
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, new Locale("pl", "PL"));
    private String date;

    public Search(String name, Query query, Integer acctualDepth, List<String> pagesToVisit) {
        this.name = new SimpleStringProperty(name);
        this.pagesToVisit = pagesToVisit;
        this.query = query;
        this.depth = acctualDepth;
        this.results = FXCollections.observableArrayList();
        this.service = Executors.newCachedThreadPool();
        this.date = simpleDateFormat.format(new Date());
    }

    public ObservableList<Result> getResults() {
        return results;
    }

    public StringProperty getNameProperty() {
        return name;
    }

    public String getName() { return name.getValue(); }

    public void search(){
        for(int i=0; i<pagesToVisit.size(); i++){
            Page page = new Page(pagesToVisit.get(i),depth);
            service.submit(new Crawler(page, query, results));
        }
    }
    public void shutdown(){
        service.shutdownNow();
    }
}