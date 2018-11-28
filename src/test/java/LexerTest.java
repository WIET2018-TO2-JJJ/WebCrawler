import WebCrawlerApp.controller.pattern.Lexer;
import WebCrawlerApp.controller.pattern.Token;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class LexerTest {

    private Lexer lexer;

    @Before
    public void setup() {
        lexer = new Lexer();
    }

    @Test
    public void validPatternTest() {
        String input = "* wordA wordB ( wordC | wordD ) < 12 >";
        ArrayList<Token> tokens = lexer.lex(input);
        assertEquals(tokens.size(), 9);
    }

    @Test(expected = Lexer.LexicalErrorException.class)
    public void invalidPatternTest() {
        String input = "* wordA wordB (wordC|wordD) < <12>";
        lexer.lex(input);
    }

    @Test
    public void invalidPatternErrorPositionTest() {
        String input = "* wordA wordB (wordC|wordD) < <12>";
        try {
            lexer.lex(input);
        } catch(final Lexer.LexicalErrorException exception) {
            assertEquals(exception.errorPosition, 28);
        }
    }
}
