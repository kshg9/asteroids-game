package com.asteroids.play;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class GameView {
    private BorderPane root;
    private Pane gamePane;
    private Button button1;
    private Button button2;
    private Button button3;
    private Text text;

    public GameView(){
        createUI();
    }

    public void createUI(){
        root = new BorderPane();
        root.setPadding(new Insets(20));

        gamePane = new Pane();
        gamePane.setFocusTraversable(true);

        Rectangle clipRect = new Rectangle();
        clipRect.widthProperty().bind(gamePane.widthProperty());
        clipRect.heightProperty().bind(gamePane.heightProperty());
        gamePane.setClip(clipRect);

        root.setCenter(gamePane);

        text = new Text(10,20,"Points: 0");
        text.toFront();
        gamePane.getChildren().add(text);

        VBox buttonBox = new VBox(10);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        buttonBox.setPadding(new Insets(20));

        button1 = new Button("Start!");
        button2 = new Button("Pause");
        button3 = new Button("Stats");

        button1.setPrefWidth(120);
        button2.setPrefWidth(120);
        button3.setPrefWidth(120);
        button2.setDisable(true);
        buttonBox.getChildren().addAll(button1, button2, button3);
        root.setRight(buttonBox);

        text.setFill(Config.POINTS_COLOR);
        gamePane.setBackground(Background.fill(Config.GAME_BACKGROUND));

        gamePane.setBorder(
                new Border(
                        new BorderStroke(
                            Color.GRAY, // Border color
                            BorderStrokeStyle.SOLID, // Border style
                            new CornerRadii(0), // Rounded corners
                            new BorderWidths(2) // Border width (2 pixels)
                        )
                )
        );
    }

    public BorderPane getRoot() {
        return root;
    }

    public Pane getGamePane() {
        return gamePane;
    }

    public Button getButton1() {
        return button1;
    }

    public Button getButton2() {
        return button2;
    }

    public Text getPoints() {
        return text;
    }

    public Button getButton3() {
        return button3;
    }

    public void setTextPoints(int points) {
        text.setText("Points: " + points);
    }

    public boolean showConfirmDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
        return alert.showAndWait().orElse(buttonTypeNo) == buttonTypeYes;
    }
}
