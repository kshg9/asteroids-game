package com.asteroids.play;

import javafx.geometry.Insets;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.List;

public class StatisticsView extends Pane {
    private final LineChart<Number, Number> lineChart;

    public StatisticsView() {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Score Over Time");

        xAxis.setLabel("Attempts ➡");
        yAxis.setLabel("Score ➡");

        getChildren().add(lineChart);

        lineChart.prefWidthProperty().bind(widthProperty());
        lineChart.prefHeightProperty().bind(heightProperty());

        lineChart.setPadding(new Insets(10, 20, 10, 10));
        setBorder(new Border(new BorderStroke(
                Color.BLACK, // Border color
                BorderStrokeStyle.SOLID, // Border style
                new CornerRadii(5), // Rounded corners
                new BorderWidths(2) // Border width (2 pixels)
        )));
    }

    public void updateChart(List<ScoreTracker.ScoreEntry> scoreHistory) {
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Score");

        for (ScoreTracker.ScoreEntry entry : scoreHistory) {
            series.getData().add(new XYChart.Data<>(entry.timestamp(), entry.score()));
        }

        lineChart.getData().clear();
        lineChart.getData().add(series);
    }
}
