/**
 * Created by kuba on 15/11/2018
 */

package WebCrawlerApp;
import WebCrawlerApp.viewController.AppController;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private Stage primaryStage;

    private AppController appController;

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("WebCrawler");

        this.appController = new AppController(primaryStage);
        this.appController.initRootLayout();

    }

    public static void main(String[] args) {
        launch(args);
    }


}
