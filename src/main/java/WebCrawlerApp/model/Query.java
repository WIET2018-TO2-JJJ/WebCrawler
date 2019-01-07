package WebCrawlerApp.model;

import WebCrawlerApp.controller.pattern.SentencePattern;

public class Query {

    private SentencePattern positivePattern;
    private SentencePattern negativePattern;

    public Query(String queryPositive, String queryNegative){
        negativePattern = new SentencePattern(queryNegative);
        positivePattern = new SentencePattern(queryPositive);
    }

    public SentencePattern getPositivePattern(){
        return positivePattern;
    }

    public SentencePattern getNegativePattern(){
        return negativePattern;
    }

}
