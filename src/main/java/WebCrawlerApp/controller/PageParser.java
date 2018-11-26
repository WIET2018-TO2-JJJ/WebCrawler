package WebCrawlerApp.controller;

import WebCrawlerApp.controller.pattern.SentencePattern;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.Arrays;
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
        SentencePattern positiveSentencePattern = new SentencePattern(queryPositive);
        SentencePattern negativeSentencePattern = new SentencePattern(queryNegative);
        return PatternMatcher.matchAgainstPatterns(sentences, positiveSentencePattern, negativeSentencePattern);
    }

    public Boolean validateUrl(Element element){
        return element.absUrl("href").contains(document.baseUri());
    }

}
