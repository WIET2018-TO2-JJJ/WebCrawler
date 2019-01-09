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
    private String resultSentence;

    @Transient
    StringProperty URL;
    @Transient
    StringProperty sentence;

    public Result(){}

    public Result(String URL, String sentence){
        this.URL = new SimpleStringProperty(URL);
        this.sentence = new SimpleStringProperty(sentence);
        this.resultURL = URL;
        this.resultSentence = sentence;
    }

    public void setSentence(){
        this.sentence = new SimpleStringProperty(resultSentence);
        this.URL = new SimpleStringProperty(resultURL);
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
