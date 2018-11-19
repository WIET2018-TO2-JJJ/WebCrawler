package WebCrawlerApp.viewController;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import WebCrawlerApp.model.Search;

public class MainViewController {


    private AppController appController;

    @FXML
    private TableView<Search> searchesTable;

    @FXML
    private TableColumn<Search, String> searchesColumn;

    @FXML
    private Button queryEnteredButton;

    @FXML
    private TextField query;


    @FXML
    public void initialize(){
        searchesTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        searchesColumn.setCellValueFactory(dataValue -> dataValue.getValue().getNameProperty());

    }
    

    @FXML
    private void handleQueryEnteredAction(ActionEvent event) {
        System.out.println("button clicked");
    }


    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    public void setData(ObservableList<Search> searches){
        searchesTable.setItems(searches);
    }
}