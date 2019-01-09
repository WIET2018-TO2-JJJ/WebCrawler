package WebCrawlerApp.controller;

import WebCrawlerApp.model.Result;
import WebCrawlerApp.model.Search;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.List;

public class Spider {

    private ObservableList<Search> searches;

    public Spider(){
        //this.searches = FXCollections.observableArrayList();
    }
    public ObservableList<Search> getSearches() {
        return searches;
    }

    public void setSearches(ObservableList<Search> searches) {
        this.searches = searches;
        searches.addListener(new ListChangeListener<Search>() {
            @Override
            public void onChanged(Change<? extends Search> c) {
                //System.out.println(searches.get(searches.size()-1).getName());
                //makeSearch(searches.size()-1);
                searches.get(searches.size()-1).search();
            }
        });
    }
}
