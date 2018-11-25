package WebCrawlerApp.controller;

import WebCrawlerApp.model.Result;
import WebCrawlerApp.model.Search;
import javafx.collections.FXCollections;
import java.io.IOException;
import java.util.List;

public class Spider {

    List<Search> searches;
    List<Result> results;

    public Spider(){
        this.searches = FXCollections.observableArrayList();
        this.results = FXCollections.observableArrayList();
    }


    public void makeSearch() throws IOException{
        while (!searches.isEmpty()) {
            for (Search search : searches) {
                results = search.search();
            }
        }
    }
}
