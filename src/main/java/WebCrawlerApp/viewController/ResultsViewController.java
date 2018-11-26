/**
 * Created by kuba on 16/11/2018
 */

package WebCrawlerApp.viewController;


import WebCrawlerApp.model.Result;
import WebCrawlerApp.model.Search;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;
import java.io.IOException;

public class ResultsViewController {
    private AppController appController;
    private String searchName;

    @FXML
    private Label searchNameLabel;

    @FXML
    private Button newSearchButton;

    @FXML
    private TableView<Search> searchesTable;

    @FXML
    private TableColumn<Search, String> searchesColumn;

    @FXML
    private TableView<Result> resultsTable;

    @FXML
    private TableColumn<Result, String> urlColumn;

    @FXML
    private TableColumn<Result, String> sentenceColumn;


    @FXML
    public void initialize(){
        searchesTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        searchesColumn.setCellValueFactory(dataValue -> dataValue.getValue().getNameProperty());
        searchesTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Search>() {
            @Override
            public void changed(ObservableValue<? extends Search> observable, Search oldValue, Search newValue) {
                appController.showResult(newValue);
            }
        });
        urlColumn.setCellValueFactory(result -> result.getValue().getURLProperty());
        sentenceColumn.setCellValueFactory(result -> result.getValue().getSentenceProperty());

    }

    @FXML
    private void handleNewSearchButton(ActionEvent event) throws IOException {
        appController.showMainView();
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    public void setData(ObservableList<Search> searches, Search mySearch){
        this.searchName = mySearch.getName();
        searchesTable.setItems(searches);
        searchNameLabel.setText(searchName);
        resultsTable.setItems(mySearch.getResults());
    }

}