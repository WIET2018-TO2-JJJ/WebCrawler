/**
 * Created by kuba on 14/11/2018
 */

package WebCrawlerApp.viewController;


import WebCrawlerApp.Main;
import WebCrawlerApp.model.Search;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;

public class AppController {

    private Stage primaryStage;
    private ObservableList<Search> searches;



    public AppController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void initRootLayout() throws IOException {
        this.primaryStage.setTitle("WebCrawler");

        // load layout from FXML file
        FXMLLoader loader = new FXMLLoader();
        Parent rootLayout = loader.load(getClass().getResourceAsStream("/view/MainView.fxml"));

        searches = FXCollections.observableArrayList();
        searches.add(new Search("Marsz niepodległości"));
        searches.add(new Search("Kaczyński"));
        MainViewController controller = loader.getController();
        controller.setAppController(this);
        controller.setData(searches);

        // add layout to a scene and show them all
        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}