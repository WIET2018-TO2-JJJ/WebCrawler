package WebCrawlerApp.model;

import javafx.scene.input.DataFormat;

public class Page {
    private String URL;
    private Integer depth;

    public Page(String URL, Integer depth){
        this.URL = URL;
        this.depth = depth;
    }

    public String getURL() {
        return URL;
    }
}
