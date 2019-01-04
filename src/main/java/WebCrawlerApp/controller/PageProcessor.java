package WebCrawlerApp.controller;

import WebCrawlerApp.controller.pattern.SentencePattern;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class PageProcessor {

    private SentencePattern positiveSentencePattern; // TODO: niepotrzebne
    private SentencePattern negativeSentencePattern; // TODO: niepotrzebne

    public PageProcessor(SentencePattern queryPositivePattern, SentencePattern queryNegativePattern){
        this.positiveSentencePattern = queryPositivePattern;
        this.negativeSentencePattern = queryNegativePattern;
    }

    // TODO: wyciągnąć SentencePattern'y w górę abstrakcji

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

        return PatternMatcher.matchAgainstPatterns(sentences, positiveSentencePattern, negativeSentencePattern);
    }
}
