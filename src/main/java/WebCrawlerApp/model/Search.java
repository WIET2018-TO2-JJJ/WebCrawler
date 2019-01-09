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
import javafx.collections.ObservableMap;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Entity
public class Search {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int searchID;

    private String searchName;
    @Transient
    private List<String> pagesToVisit;
    private Integer depth;
    @OneToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "QUERY_FK")
    private Query query;

    @OneToMany(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "SEARCH_FK")
    private Set<Result> resultSet;

    @Transient
    private ObservableList<Result> results;
    @Transient
    private StringProperty name;
    @Transient
    private ExecutorService service;
    @Transient
    private HashMap<Page, Integer> stats;

    @Transient
    private String pattern  = "dd-M-yyyy hh:mm:ss";
    @Transient
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, new Locale("pl", "PL"));
    private String date;

    public Search(){}

    public Search(String name, Query query, Integer acctualDepth, List<String> pagesToVisit) {
        this.name = new SimpleStringProperty(name);
        this.pagesToVisit = pagesToVisit;
        this.query = query;
        this.depth = acctualDepth;
        this.results = FXCollections.observableArrayList();
        this.service = Executors.newCachedThreadPool();
        this.date = simpleDateFormat.format(new Date());
        this.stats = new HashMap<>();
        this.searchName = name;
        resultSet = new HashSet<>();
    }

    public void addResultsToSet(){
        for (Result result : results){
            if (resultSet.stream().noneMatch((r) -> r.sentence == result.sentence && r.URL == result.URL)){
                resultSet.add(result);
            }
        }
    }

    public void addResultsToObservableList(){
        for (Result result : resultSet){
            result.setSentence();
            results.add(result);
        }
    }

    public void setName(){
        name = new SimpleStringProperty(searchName);
    }

    public ObservableList<Result> getResults() {
        return results;
    }

    public StringProperty getNameProperty() {
        return name;
    }

    public String getName() { return searchName; }

    public int getSearchID(){ return searchID; }

    public Query getQuery() { return query; }

    public Set<Result> getResultSet() {
        return resultSet;
    }

    public void search(){
        for(int i=0; i<pagesToVisit.size(); i++){
            Page page = new Page(pagesToVisit.get(i),depth);
            service.submit(new Crawler(page, query, results, stats));
        }
    }
    public void shutdown(){
        service.shutdownNow();
    }

    public HashMap<Page, Integer> getDataForDiagram(){
        return stats;
    }
}