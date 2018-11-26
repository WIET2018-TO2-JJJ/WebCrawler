/**
 * Created by kuba on 14/11/2018
 */

package WebCrawlerApp.viewController;


import WebCrawlerApp.controller.Spider;
import WebCrawlerApp.model.Search;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AppController {

    private Stage primaryStage;
    private ObservableList<Search> searches;
    private Spider spider;


    public AppController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.spider = new Spider();
        this.searches = spider.getSearches();

    }

    public void initRootLayout() throws IOException {
        this.primaryStage.setTitle("WebCrawler");

        // load layout from FXML file
        FXMLLoader loader = new FXMLLoader();
        Parent rootLayout = loader.load(getClass().getResourceAsStream("/views/MainView.fxml"));

        //searches.add(new Search("Marsz niepodległości"));
        //searches.add(new Search("Premier"));
        MainViewController controller = loader.getController();
        controller.setAppController(this);
        controller.setData(searches);

        // add layout to a scene and show them all
        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public void showResult(Search search){
        FXMLLoader loader = new FXMLLoader();
        Parent resultsLayout = null;
        try {
            resultsLayout = loader.load(getClass().getResourceAsStream("/views/ResultsView.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage mainStage;
        mainStage = primaryStage;
        ResultsViewController controller = loader.getController();
        controller.setAppController(this);
        controller.setData(searches, search);
        mainStage.getScene().setRoot(resultsLayout);
    }

    public void showMainView(){
        FXMLLoader loader = new FXMLLoader();
        Parent resultsLayout = null;
        try {
            resultsLayout = loader.load(getClass().getResourceAsStream("/views/MainView.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage mainStage;
        mainStage = primaryStage;
        MainViewController controller = loader.getController();
        controller.setAppController(this);
        controller.setData(searches);
        mainStage.getScene().setRoot(resultsLayout);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}