package WebCrawlerApp.controller;

import WebCrawlerApp.model.Search;

import java.util.List;

public class Spider {

    List<String> URLs;
    Integer maxDepth;
    String queryPositive;
    String queryNegative;
    List<Search> searches;

    public Spider(){
    }

    private void makeSearch(){

        Search search =  new Search(URLs.get(0),queryPositive,queryNegative,maxDepth);
        searches.add(search);
    }
}
