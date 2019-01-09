package WebCrawlerApp.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int resultID;

    private String resultURL;
    private String resultBaseURL;
    private String resultSentence;

    @Transient
    StringProperty URL;
    @Transient
    StringProperty sentence;

    public Result(){}

    public Result(String URL, String baseURL, String sentence){
        this.URL = new SimpleStringProperty(URL);
        this.sentence = new SimpleStringProperty(sentence);
        this.resultURL = URL;
        this.resultSentence = sentence;
        this.resultBaseURL = baseURL;
    }

    public String getSentence() {
        return sentence.getValue();
    }

    public StringProperty getSentenceProperty() {
        return sentence;
    }

    public String getURL() {
        return URL.getValue();
    }

    public StringProperty getURLProperty() {
        return URL;
    }
}
