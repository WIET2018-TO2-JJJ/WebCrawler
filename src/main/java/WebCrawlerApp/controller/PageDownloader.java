package WebCrawlerApp.controller;

import WebCrawlerApp.model.Page;

import javax.swing.text.Document;

public class PageDownloader {
    public Page pageDownloader(String URL){
        Page page = new Page("https://www.onet.pl");
        return page;
    }
}
