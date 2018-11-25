/**
 * Created by kuba on 13/11/2018
 */

package WebCrawlerApp.viewController;



import WebCrawlerApp.model.Search;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;


public class MainViewController {


    private AppController appController;
    ObservableList<Search> searches;

    @FXML
    private TableView<Search> searchesTable;

    @FXML
    private TableColumn<Search, String> searchesColumn;

    @FXML
    private Button queryEnteredButton;

    @FXML
    private TextField queryName;

    @FXML
    private TextField query;


    @FXML
    public void initialize(){
        searchesTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        searchesColumn.setCellValueFactory(dataValue -> dataValue.getValue().getNameProperty());
        BooleanBinding searchButtonBinding = query.textProperty().isEmpty().or(queryName.textProperty().isEmpty());
        queryEnteredButton.disableProperty().bind(searchButtonBinding);
    }

    @FXML
    private void handleQueryEnteredAction(ActionEvent event) throws IOException {
        String queryTaken = query.getText();
        String searchName = queryName.getText();
        //searches.add(new Search(searchName));
        //TODO: pass query from textField
        System.out.println(queryTaken);
        FXMLLoader loader = new FXMLLoader();
        Parent resultsLayout = loader.load(getClass().getResourceAsStream("/views/ResultsView.fxml"));
        Stage mainStage;
        mainStage = appController.getPrimaryStage();
        ResultsViewController controller = loader.getController();
        controller.setAppController(appController);
        controller.setData(searches, searchName);
        mainStage.getScene().setRoot(resultsLayout);
    }


    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    public void setData(ObservableList<Search> searches){
        this.searches = searches;
        searchesTable.setItems(searches);
    }
}