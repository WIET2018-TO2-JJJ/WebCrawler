package WebCrawlerApp.controller;

import WebCrawlerApp.controller.pattern.SentencePattern;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// TODO: zmienić na PageProcessor
public class PageParser {

    private String queryNegative; // TODO: niepotrzebne
    private String queryPositive; // TODO: niepotrzebne

    // TODO: przekazywać SentencePatterny
    public PageParser(String queryPositive, String queryNegative){
        this.queryPositive = queryPositive;
        this.queryNegative = queryNegative;
    }

    // TODO: wyciągnąć SentencePAttern'y w górę abstrakcji

    public List<String> searchForWords(Document document){
        // TODO: https://stackoverflow.com/a/2687929/3546683
        List<String> sentences = new ArrayList<>();
        Elements elements = document.body().select("p,li").append("\n");
        BreakIterator bi = BreakIterator.getSentenceInstance();

        for(Element element : elements) {
            bi.setText(element.text());
            int index = 0;
            while (bi.next() != BreakIterator.DONE) {
                String sentence = element.text().substring(index, bi.current());
                System.out.println("Sentence: " + sentence);
                sentences.add(sentence);
                index = bi.current();
            }
        }

        SentencePattern positiveSentencePattern = new SentencePattern(queryPositive);
        SentencePattern negativeSentencePattern = new SentencePattern(queryNegative);
        return PatternMatcher.matchAgainstPatterns(sentences, positiveSentencePattern, negativeSentencePattern);
    }
}
