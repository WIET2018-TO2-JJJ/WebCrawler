import WebCrawlerApp.controller.patternparser.Lexer;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class PatternParserTest {

    private Lexer lexer;

    @Before
    public void setup() {
        lexer = new Lexer();
    }

    @Test
    public void validPatternTest() {
        String input = "* wordA wordB ( wordC | wordD ) < 12 >";
        ArrayList<Lexer.Token> tokens = lexer.lex(input);
        assertEquals(tokens.size(), 9);
    }

    @Test(expected = Lexer.InvalidSyntaxException.class)
    public void invalidPatternTest() {
        String input = "* wordA wordB (wordC|wordD) < <12>";
        lexer.lex(input);
    }

    @Test
    public void invalidPatternErrorPositionTest() {
        String input = "* wordA wordB (wordC|wordD) < <12>";
        try {
            lexer.lex(input);
        } catch(final Lexer.InvalidSyntaxException exception) {
            assertEquals(exception.errorPosition, 28);
        }
    }
}
