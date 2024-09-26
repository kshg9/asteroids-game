package com.asteroids.play;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;

import java.util.concurrent.atomic.AtomicInteger;

public class Logic {
    private final GameView view;
    private final GameState gameState;
    private final InputHandler inputHandler;
    private final CollisionDetector collisionDetector;
    private final GameObjectManager gameObjectManager;
    private AnimationTimer gameLoop;
    private Ship ship;

    private double totalGameTime = 0.0;
    private final AtomicInteger points;

    private final ScoreTracker scoreTracker;
    private final StatisticsView statisticsView;
    private boolean statisticsShown;
    private GameState.State previousState;

    public Logic(GameView view, Scene scene) {
        this.view = view;
        this.gameState = new GameState();
        this.inputHandler = new InputHandler(scene);
        this.collisionDetector = new CollisionDetector(gameState, this::handleStartRestart);
        this.gameObjectManager = new GameObjectManager(view.getGamePane());
        this.points = new AtomicInteger(0);

        this.scoreTracker = new ScoreTracker();
        this.statisticsView = new StatisticsView();
        this.statisticsShown = false;

        setupEventHandlers();
        Platform.runLater(this::initializeGame);
    }

    private void toggleStatistics() {
        this.statisticsView.prefWidthProperty().bind(this.view.getGamePane().widthProperty());
        this.statisticsView.prefHeightProperty().bind(this.view.getGamePane().heightProperty());
        if (gameState.getState() == GameState.State.RUNNING) {
            pauseGame();
        }
        if (!statisticsShown) {
            showStatistics();
            view.getButton2().setDisable(true);
            view.getPoints().setVisible(false);
            view.getGamePane().setBackground(Background.fill(Config.STATS_BACKGROUND));
        } else {
            hideStatistics();
            view.getButton2().setDisable(false);
            view.getPoints().setVisible(true);
            view.getGamePane().setBackground(Background.fill(Config.GAME_BACKGROUND));
        }
    }

    private void showStatistics() {
        previousState = gameState.getState();
        if (previousState == GameState.State.RUNNING) {
            pauseGame();
        }

        gameObjectManager.saveState();
        gameObjectManager.hideObjects();

        view.getGamePane().getChildren().add(statisticsView);
        statisticsShown = true;
    }

    private void hideStatistics() {
        view.getGamePane().getChildren().remove(statisticsView);

        gameObjectManager.showObjects();
        gameObjectManager.restoreState();

        statisticsShown = false;

        if (previousState == GameState.State.RUNNING) {
            resumeGame();
        }
    }

    private void setupEventHandlers() {
        inputHandler.setOnStartRestart(this::handleStartRestart);
        inputHandler.setOnPauseResume(this::handlePauseResume);
        inputHandler.setOnStats(this::toggleStatistics);
    }

    private void initializeGame() {
        view.getButton1().setOnAction(event -> inputHandler.handleStartRestart());
        view.getButton1().requestFocus();
        view.getButton2().setOnAction(event -> inputHandler.handlePauseResume());
        view.getButton3().setOnAction(event -> inputHandler.handleStats());

        Config.CANVAS_WIDTH = (int) view.getGamePane().getWidth();
        Config.CANVAS_HEIGHT = (int) view.getGamePane().getHeight();

        ship = gameObjectManager.createShip();

        collisionDetector.linkTextPoints(this.view.getPoints(), points);
    }

    private void handleStartRestart() {
        System.out.println("Start/Restart button pressed");
        switch (gameState.getState()) {
            case GAME_OVER:
                scoreTracker.addScore(points.get());
                statisticsView.updateChart(scoreTracker.getScoreHistory());
            case READY:
                view.getButton1().requestFocus();
                resetGame();
                reStartGame();
                break;
            default:
                showRestartPrompt();
        }
    }

    private void handlePauseResume() {
        if (gameState.getState() == GameState.State.PAUSED) {
            resumeGame();
        } else {
            pauseGame();
        }
    }

    private void reStartGame() {
        gameState.setState(GameState.State.RUNNING);
        view.getButton1().setText("Restart");
        view.getButton2().setDisable(false);
        view.setTextPoints(0);
        view.getGamePane().setBackground(Background.fill(Config.GAME_BACKGROUND));
        ship.getCharacter().setFill(Config.SHIP_COLOR);
        initializeGame();
        startGameLoop();
        view.getGamePane().requestFocus();
    }

    private void pauseGame() {
        gameState.setState(GameState.State.PAUSED);
        view.getButton2().setText("Resume");
        stopGameLoop();
    }

    private void resumeGame() {
        gameState.setState(GameState.State.RUNNING);
        view.getButton2().setText("Pause");
        view.getGamePane().requestFocus();
        startGameLoop();
    }

    private void startGameLoop() {
        gameLoop = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (lastUpdate == 0) {
                    lastUpdate = now;
                    return;
                }
                double deltaTime = (now - lastUpdate) * 1e-9;
                totalGameTime += deltaTime;

                updateGame(deltaTime);
                lastUpdate = now;
            }
        };
        gameLoop.start();
        System.out.println("Game loop started");
    }

    private void stopGameLoop() {
        if (gameLoop != null) {
            gameLoop.stop();
        }
    }

    private void updateGame(double deltaTime) {
        if (gameState.isRunning()) {
            System.out.println("Updating game. Delta time: " + deltaTime);

            gameObjectManager.createAsteroids();

            gameObjectManager.updateObjects(deltaTime);
            collisionDetector.checkCollisions(gameObjectManager.getGameObjects());
            handleInput(deltaTime);
            checkGameOver();
        }
    }

    private void handleInput(double deltaTime) {
        if (inputHandler.isKeyPressed(KeyCode.LEFT)) {
            ship.turnLeft(deltaTime);
        }
        if (inputHandler.isKeyPressed(KeyCode.RIGHT)) {
            ship.turnRight(deltaTime);
        }
        if (inputHandler.isKeyPressed(KeyCode.UP)) {
            ship.accelerate(deltaTime);
        }
        if (inputHandler.isKeyPressed(KeyCode.SPACE)) {
            Projectile bullet = Projectile.shoot(totalGameTime, ship);
            if (bullet != null) {
                gameObjectManager.addGameObject(bullet);
            }
        }
    }

    private void checkGameOver() {
        if (!ship.isAlive()) {
            gameState.setState(GameState.State.GAME_OVER);
            stopGameLoop();
            view.getButton1().setText("Restart");
            view.getButton2().setDisable(false);
        }
    }

    private void showRestartPrompt() {
        boolean shouldRestart = view.showConfirmDialog("Restart Game?", "Do you want to restart the game?");
        if (shouldRestart) {
            resetGame();
            reStartGame();
        }
    }

    private void resetGame() {
        stopGameLoop();
        gameObjectManager.resetObjects();
        ship = gameObjectManager.createShip();
        totalGameTime = 0.0;
        points.set(0);
        gameState.setState(GameState.State.READY);
        Projectile.reset();

        hideStatistics();
        points.set(0);
    }
}