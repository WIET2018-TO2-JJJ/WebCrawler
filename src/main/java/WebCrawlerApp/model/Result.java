package WebCrawlerApp.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Result {
    StringProperty URL;
    StringProperty sentence;


    public Result(String URL, String sentence){
        this.URL = new SimpleStringProperty(URL);
        this.sentence = new SimpleStringProperty(sentence);
    }
}
