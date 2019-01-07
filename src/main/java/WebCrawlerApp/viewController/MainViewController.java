/**
 * Created by kuba on 13/11/2018
 */

package WebCrawlerApp.viewController;



import WebCrawlerApp.model.Query;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


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
    private TextArea pagesTextArea;

    @FXML
    private  TextField depthTF;


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
        String urlsString = pagesTextArea.getText();
        Integer depth;
        if(depthTF.getText().equals("")){
            depth = 1;
        } else {
            depth =  Integer.parseInt(depthTF.getText());
        }
        List<String> tmpURLs = Arrays.asList(urlsString.split(", |\n"));
        List<String> URLs = new ArrayList<String>();
        for(String s : tmpURLs){
            System.out.println(s);
            URLs.add(s);
        }
        Query query = new Query(queryPositive,queryNegative);
        searches.add(new Search(searchName, query, depth, URLs));
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