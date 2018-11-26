package WebCrawlerApp.controller;

import WebCrawlerApp.controller.pattern.Pattern;
import WebCrawlerApp.model.Result;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class PageParser {

    String queryNegative;
    String queryPositive;
    Document document;

    public PageParser(String queryPositive, String queryNegative, Document document){
        this.queryPositive = queryPositive;
        this.queryNegative = queryNegative;
        this.document = document;
    }

    public List<String> searchForWords(Document document){
        String body = document.body().text();
        List<String> sentences = Arrays.asList(body.split("\\. | • "));
        PatternMatcher patternMatcher = new PatternMatcher();
        Pattern positivePattern = new Pattern(queryPositive);
        Pattern negativePattern = new Pattern(queryNegative);
        return PatternMatcher.matchAgainstPatterns(sentences,positivePattern,negativePattern);
    }

    public Boolean validateUrl(Element element){
        return element.absUrl("href").contains(document.baseUri());
    }

}
