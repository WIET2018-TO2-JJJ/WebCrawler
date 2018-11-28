package WebCrawlerApp.controller;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class PageDownloader {

    private Connection connection;
    private Document document;

    public void setDocument(Document document) {
        this.document = document;
    }

    public Document downloadPage(String URL,Integer depth){
        connection = Jsoup.connect(URL);
        try {
            document = connection.get();
        } catch (IOException e) {
            System.out.println("connection error"); //TODO: connection error handler
        }
        return document;
    }

    public String getSignature(){
        return connection.data().toString();
    }

    public Elements getURLs(){
        return document.select("a[href]");
    }
}
