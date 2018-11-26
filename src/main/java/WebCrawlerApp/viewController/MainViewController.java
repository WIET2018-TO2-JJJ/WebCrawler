/**
 * Created by kuba on 13/11/2018
 */

package WebCrawlerApp.viewController;



import WebCrawlerApp.model.Search;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
    private TextField queryPositiveTF;

    @FXML
    private TextField queryNegativeTF;


    @FXML
    public void initialize(){
        searchesTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        searchesColumn.setCellValueFactory(dataValue -> dataValue.getValue().getNameProperty());
        BooleanBinding searchButtonBinding = (queryPositiveTF.textProperty().isEmpty().and(queryNegativeTF.textProperty().isEmpty())).or(queryName.textProperty().isEmpty());
        queryEnteredButton.disableProperty().bind(searchButtonBinding);
        searchesTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Search>() {
            @Override
            public void changed(ObservableValue<? extends Search> observable, Search oldValue, Search newValue) {
                appController.showResult(newValue);
            }
        });
    }

    @FXML
    private void handleQueryEnteredAction(ActionEvent event) throws IOException {
        String queryPositive = queryPositiveTF.getText();
        String queryNegative = queryNegativeTF.getText();
        String searchName = queryName.getText();
        searches.add(new Search(searchName,queryPositive, queryNegative,0));
        appController.showResult(searches.get(searches.size()-1));
        //System.out.println(queryTaken);
    }


    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    public void setData(ObservableList<Search> searches){
        this.searches = searches;
        searchesTable.setItems(searches);
    }
}