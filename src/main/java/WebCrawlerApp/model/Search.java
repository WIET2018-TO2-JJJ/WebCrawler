/**
 * Created by kuba on 15/11/2018
 */

package WebCrawlerApp.model;


import WebCrawlerApp.controller.Crawler;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Search {

    private List<String> pagesToVisit;
    private Integer depth;
    private String queryPositive;
    private String queryNegative;

    private ObservableList<Result> results;
    private StringProperty name;
    private ExecutorService service;



    public Search(String name, String queryPositive, String queryNegative, Integer acctualDepth, List<String> pagesToVisit) {
        this.name = new SimpleStringProperty(name);
        this.pagesToVisit = pagesToVisit;
        this.queryPositive = queryPositive;
        this.queryNegative = queryNegative;
        this.depth = acctualDepth;
        this.results = FXCollections.observableArrayList();
        this.service = Executors.newCachedThreadPool();
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
            service.submit(new Crawler(pagesToVisit.get(i), depth, queryNegative, queryPositive, results));
        }
    }
    public void shutdown(){
        service.shutdownNow();
    }
}