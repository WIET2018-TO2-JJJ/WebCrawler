package WebCrawlerApp.controller;

import WebCrawlerApp.controller.pattern.SentencePattern;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class PageParser {

    private String queryNegative;
    private String queryPositive;

    public PageParser(String queryPositive, String queryNegative){
        this.queryPositive = queryPositive;
        this.queryNegative = queryNegative;
    }

    public List<String> searchForWords(Document document){
        String body = document.body().text();
        List<String> sentences = Arrays.asList(body.split("\\. | • "));
        SentencePattern positiveSentencePattern = new SentencePattern(queryPositive);
        SentencePattern negativeSentencePattern = new SentencePattern(queryNegative);
        return PatternMatcher.matchAgainstPatterns(sentences, positiveSentencePattern, negativeSentencePattern);
    }
}
