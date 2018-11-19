package WebCrawlerApp.controller;

import WebCrawlerApp.model.Result;

public class PageParser {

    String query;

    public PageParser(String query){
        this.query = query;
    }

    private Result searchForWords(){
        return new Result();
    }

}
