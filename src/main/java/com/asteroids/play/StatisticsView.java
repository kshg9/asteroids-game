package com.asteroids.play;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;

import java.util.List;

public class StatisticsView extends Pane {
    private LineChart<Number, Number> lineChart;

    public StatisticsView() {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Score Over Time");

        xAxis.setLabel("Attempts ➡");
        yAxis.setLabel("Score ➡");

        getChildren().add(lineChart);
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
