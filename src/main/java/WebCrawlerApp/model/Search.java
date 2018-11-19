/**
 * Created by kuba on 15/11/2018
 */

package WebCrawlerApp.model;


import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.HashMap;
import java.util.List;

public class Search {

    List<Page> pagesToVisit;
    HashMap<String,Page> pagesVisited;
    Integer acctualDepth;
    private StringProperty name;

    public Search(String name) {
        this.name = new SimpleStringProperty(name);
    }

    public final StringProperty getNameProperty() {
        return name;
    }

    private void search(){

    }
}