/**
 * Created by kuba on 14/11/2018
 */

package WebCrawlerApp.viewController;


import WebCrawlerApp.controller.Spider;
import WebCrawlerApp.model.Page;
import WebCrawlerApp.model.Search;
import WebCrawlerApp.session.SessionService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class AppController {

    private Stage primaryStage;
    private ObservableList<Search> searches;
    private Spider spider;


    public AppController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.spider = new Spider();
        SessionService sessionService = new SessionService();
        List<Search> oldSearches = sessionService.getAllSearches();
        searches = FXCollections.observableArrayList(oldSearches);
        searches.forEach(s -> s.reloadStats());
        oldSearches.forEach(s -> s.search());
        spider.setSearches(searches);
        //this.searches = spider.getSearches();

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
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Platform.exit();
                for(Search search : searches){
                    search.shutdown();
                }
                System.exit(0);
            }
        });
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

    public void showDiagram(HashMap dataForDiagram){
        try {
            // Load the fxml file and create a new stage for the dialog
            FXMLLoader loader = new FXMLLoader();

            Parent page = loader.load(getClass().getResourceAsStream("/views/DiagramView.fxml"));

            // Create the dialog Stage.
            Stage diagramStage = new Stage();
            diagramStage.setTitle("Diagram");
            diagramStage.initModality(Modality.WINDOW_MODAL);
            //diagramStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            diagramStage.setScene(scene);

            DiagramViewController controller = loader.getController();
            controller.setAppController(this);
            controller.setData(dataForDiagram);
            diagramStage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}