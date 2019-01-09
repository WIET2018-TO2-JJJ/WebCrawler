package WebCrawlerApp.model;

import WebCrawlerApp.controller.pattern.SentencePattern;
import javax.persistence.Entity;
import javax.persistence.*;

@Entity
public class Query {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int queryID;

    private String queryPositive;
    private String queryNegative;

    @OneToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "SEARCH_FK")
    private Search search;

    @Transient
    private SentencePattern positivePattern;
    @Transient
    private SentencePattern negativePattern;

    public Query(){}

    public Query(String queryPositive, String queryNegative){
        this.queryPositive = queryPositive;
        this.queryNegative = queryNegative;
        this.negativePattern = new SentencePattern(queryNegative);
        this.positivePattern = new SentencePattern(queryPositive);
    }

    public SentencePattern getPositivePattern(){
        return positivePattern;
    }

    public SentencePattern getNegativePattern(){
        return negativePattern;
    }

}
