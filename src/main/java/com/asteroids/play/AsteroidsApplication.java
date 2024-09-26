package com.asteroids.play;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AsteroidsApplication extends Application {

    @Override
    public void start(Stage stage) {
        GameView gameView = new GameView();

        Scene scene = new Scene(gameView.getRoot(), 750,450);
//        stage.setResizable(false);
        new Logic(gameView, scene);

        stage.setTitle("Asteroids!");

        stage.setScene(scene);
        stage.show();
    }
}