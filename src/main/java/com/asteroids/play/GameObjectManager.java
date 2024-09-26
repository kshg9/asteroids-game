package com.asteroids.play;

import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameObjectManager {
    private final List<GameObject> gameObjects = new ArrayList<>();
    private final Pane gamePane;
    private Ship ship;

    public GameObjectManager(Pane gamePane) {
        this.gamePane = gamePane;
    }

    public Ship createShip() {
        System.out.println(Config.CANVAS_HEIGHT + " " + Config.CANVAS_WIDTH);

        /* Ship checker guard: if not check with null it will render one non-moveable ship and other moveable ship! */

        if (ship == null){
            ship = new Ship(Config.CANVAS_WIDTH / 2,Config.CANVAS_HEIGHT / 2);
            addGameObject(ship);
            ship.setPosition(ship.getPosition());
            ship.getCharacter().setFill(Config.SHIP_COLOR);
        }
        return ship;
    }

    public void createAsteroids() {
        Random rnd = new Random();
        if (rnd.nextDouble() < 0.003 && maxAsteroidLessThan(Config.MAX_ASTEROID)) {
            double x = 0, y = 0;

            // Randomly select an edge: 0 = top, 1 = bottom, 2 = left, 3 = right
            int edge = rnd.nextInt(4);
            switch (edge) {
                case 0: // Top edge
                    x = rnd.nextDouble() * Config.CANVAS_WIDTH;
                    y = 0;
                    break;
                case 1: // Bottom edge
                    x = rnd.nextDouble() * Config.CANVAS_WIDTH;
                    y = Config.CANVAS_HEIGHT;
                    break;
                case 2: // Left edge
                    x = 0;
                    y = rnd.nextDouble() * Config.CANVAS_HEIGHT;
                    break;
                case 3: // Right edge
                    x = Config.CANVAS_WIDTH;
                    y = rnd.nextDouble() * Config.CANVAS_HEIGHT;
                    break;
            }
            Asteroid asteroid = new Asteroid(x, y);
            addGameObject(asteroid);
        }
    }

    public void addGameObject(GameObject obj) {
        gameObjects.add(obj);
        gamePane.getChildren().add(obj.getCharacter());
    }

    public void removeGameObject(GameObject obj) {
        gamePane.getChildren().remove(obj.getCharacter());
        gameObjects.remove(obj);

    }

    public void updateObjects(double dt) {
        for (GameObject obj : gameObjects) {
            obj.update(dt);
        }
        removeDeadObjects();
    }

    private void removeDeadObjects() {
        gameObjects.removeIf(obj ->
                {
                    if (!obj.isAlive()) {
                        gamePane.getChildren().remove(obj.getCharacter());
                        return true;
                    }
                    return false;
                }
        );
    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    public void resetObjects() {
        for (GameObject obj : new ArrayList<>(gameObjects)) {
            removeGameObject(obj);
        }
        gameObjects.clear();
        ship = null;
    }

    public boolean maxAsteroidLessThan(int max) {
        return (int) gameObjects.stream().filter(
                gameObject -> gameObject instanceof Asteroid
        ).count() < max;
    }

    // TODO refactor

    public void saveState() {
        for (GameObject obj : gameObjects) {
            obj.saveState();
        }
    }

    public void restoreState() {
        for (GameObject obj : gameObjects) {
            obj.restoreState();
        }
    }

    public void hideObjects() {
        for (GameObject obj : gameObjects) {
            obj.getCharacter().setVisible(false);
        }
    }

    public void showObjects() {
        for (GameObject obj : gameObjects) {
            obj.getCharacter().setVisible(true);
        }
    }
}
