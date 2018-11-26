package WebCrawlerApp.controller;

import WebCrawlerApp.model.Page;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.print.Doc;
import java.io.IOException;
import static com.sun.activation.registries.LogSupport.log;

public class PageDownloader {

    private Connection connection;
    private Document document;

    public void setDocument(Document document) {
        this.document = document;
    }

    public Document downloadPage(String URL){
        connection = Jsoup.connect(URL);
        try {
            document = connection.get();
        } catch (IOException e) {
            System.out.println("connection error");
        }
        //System.out.println(document.text());
        return document;
    }

    public String getSignature(){
        return connection.data().toString();
    }

    public Elements getURLs(){
        return document.select("a[href]");
    }
}
