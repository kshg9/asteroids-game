package com.asteroids.play;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class AsteroidsApplication extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        GameView gameView = new GameView();

        Scene scene = new Scene(gameView.getRoot(), 750,450);
        stage.setResizable(false);
        new Logic(gameView, scene);

        stage.setTitle("Asteroids!");

        stage.setScene(scene);
        stage.show();
    }
}