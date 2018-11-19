/**
 * Created by kuba on 16/11/2018
 */

package WebCrawlerApp.viewController;


import WebCrawlerApp.model.Search;
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
    private Button showDiagramButton;

    @FXML
    private TableView<Search> searchesTable;

    @FXML
    private TableColumn<Search, String> searchesColumn;

    @FXML
    private TableView<Search> resultsTable;

    @FXML
    private TableColumn<Search, String> resultColumn;


    @FXML
    public void initialize(){
        searchesTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        searchesColumn.setCellValueFactory(dataValue -> dataValue.getValue().getNameProperty());
    }

    @FXML
    private void handleShowDiagramButton(ActionEvent event) throws IOException {
        System.out.print("btn");
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    public void setData(ObservableList<Search> searches, String searchName){
        this.searchName = searchName;
        searchesTable.setItems(searches);
        searchNameLabel.setText(searchName);
    }
}