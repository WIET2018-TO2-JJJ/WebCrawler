package WebCrawlerApp.model;

import javafx.scene.input.DataFormat;

public class Page {
    String URL;
    String signature;
    Integer depth;

    public Page(String URL, String signature,Integer depth){
        this.URL = URL;
        this.signature = signature;
        this.depth = depth;
    }
}
