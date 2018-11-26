package WebCrawlerApp.controller;

import WebCrawlerApp.controller.pattern.Pattern;
import WebCrawlerApp.model.Result;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.BreakIterator;
import java.util.ArrayList;
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

        List<String> sentences = new ArrayList<>();
        Elements elements = document.body().select("p,li").append("\n");
        BreakIterator bi = BreakIterator.getSentenceInstance();

        for(Element element : elements){
            bi.setText(element.text());
            int index = 0;
            while (bi.next() != BreakIterator.DONE) {
                String sentence = element.text().substring(index, bi.current());
                //System.out.println("Sentence: " + sentence);
                sentences.add(sentence);
                index = bi.current();
            }
        }

        //PatternMatcher patternMatcher = new PatternMatcher();
        Pattern positivePattern = new Pattern(queryPositive);
        Pattern negativePattern = new Pattern(queryNegative);
        return PatternMatcher.matchAgainstPatterns(sentences,positivePattern,negativePattern);
    }

    public Boolean validateUrl(Element element){
        return element.absUrl("href").contains(document.baseUri());
    }

}
