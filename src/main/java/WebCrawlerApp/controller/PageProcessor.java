package WebCrawlerApp.controller;

import WebCrawlerApp.controller.pattern.SentencePattern;
import WebCrawlerApp.model.Query;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.awt.geom.QuadCurve2D;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class PageProcessor {

    private Query query;

    public PageProcessor(Query query){
        this.query = query;
    }

    public List<String> searchForWords(Document document){
        List<String> sentences = new ArrayList<>();
        Elements elements = document.body().select("p,li").append("\n");
        BreakIterator bi = BreakIterator.getSentenceInstance(new Locale("pl"));

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

        return PatternMatcher.matchAgainstPatterns(sentences, query.getPositivePattern(), query.getNegativePattern());
    }
}
