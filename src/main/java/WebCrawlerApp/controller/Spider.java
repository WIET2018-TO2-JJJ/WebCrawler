package WebCrawlerApp.controller;

import WebCrawlerApp.model.Search;

import java.util.List;

public class Spider {

    List<String> URLs;
    Integer maxDepth;
    String query;
    List<Search> searches;

    private Search makeSearch(){
        return new Search(URLs.get(0));
    }
}
