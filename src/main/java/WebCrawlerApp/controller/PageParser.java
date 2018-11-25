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
        Element body = document.body();
        String text = body.toString();
        List<String> sentences = Arrays.asList(text.split("."));
        for (String sentece : sentences){
            sentences.add(".");
        }
        PatternMatcher patternMatcher = new PatternMatcher();
        Pattern positivePattern = new Pattern(queryPositive);
        Pattern negativePattern = new Pattern(queryNegative);
        List<String> results = patternMatcher.matchAgainstPatterns(sentences,positivePattern,negativePattern);
        return results;
    }

    public Boolean validateUrl(Element element){
        return true;
    }

}
