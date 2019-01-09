import WebCrawlerApp.model.Query;
import WebCrawlerApp.model.Search;
import WebCrawlerApp.session.SessionService;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class DatabaseTest {

    private SessionService sessionService;

    @Before
    public void setup(){
        sessionService = new SessionService();
    }

    @Test
    public void saveTest() {
        List<String> pages = new ArrayList<>();
        pages.add("https://www.onet.pl");
        Query query = new Query("* prezydent *", "* koniec *");
        Search search = new Search("MySearch", query, 1, pages);
        sessionService.save(search);
        List<String> searchesNames = sessionService.getAllSearches().stream().map((s) -> s.getName()).collect(Collectors.toList());
        assertTrue(searchesNames.contains(search.getName()));
    }

}
