/**
 * Created by kuba on 07/01/2019
 */

package WebCrawlerApp.viewController;


import WebCrawlerApp.model.Page;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.SliderBuilder;
import javafx.util.Duration;

import java.util.*;

public class DiagramViewController {
    private AppController appController;
    private ObservableMap<String, Integer> stats;


    @FXML
    NumberAxis yAxis;

    @FXML
    CategoryAxis xAxis;

    @FXML
    BarChart<String,Integer> bc;

    @FXML
    public void initialize(){
        bc.setAnimated(false);
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    public void setData(HashMap dataForDiagram) {
        this.stats = FXCollections.observableMap(dataForDiagram);
        HashMap<String,XYChart.Series> seriesMap = new HashMap<>();

        for(Map.Entry<String, Integer> stat: stats.entrySet()){
            XYChart.Series series = new XYChart.Series();
            series.setName(stat.getKey());
            //System.out.format("%s, %d", entry.getKey().getURL(),entry.getValue());
            series.getData().add(new XYChart.Data(stat.getKey(),stat.getValue()));
            seriesMap.put(stat.getKey(),series);
            bc.getData().add(series);
        }
        Timeline Updater = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                for(Map.Entry<String, Integer> stat: stats.entrySet()){
                    XYChart.Series series = seriesMap.get(stat.getKey());
                    //series.getData().clear();
                    series.setName(stat.getKey());
                    //System.out.format("%s, %d", entry.getKey().getURL(),entry.getValue());
                    series.getData().add(new XYChart.Data(stat.getKey(),stat.getValue()));
                }
            }
        }));
        Updater.setCycleCount(Timeline.INDEFINITE);
        Updater.play();
    }
}