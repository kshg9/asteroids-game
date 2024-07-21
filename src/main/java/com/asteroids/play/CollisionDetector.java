package com.asteroids.play;

import javafx.scene.text.Text;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CollisionDetector {

    private GameState gameState;
    private Runnable handleStartRestart;
    private Text text;
    private AtomicInteger points;

    public CollisionDetector(GameState gameState, Runnable handleStartRestart) {
        this.gameState = gameState;
        this.handleStartRestart = handleStartRestart;
    }

    public void linkTextPoints(Text text, AtomicInteger points) {
        this.text = text;
        this.points = points;
    }

    public void checkCollisions(List<GameObject> gameObjects) {
        for (int i = 0; i < gameObjects.size(); i++) {
            for (int j = 0; j < gameObjects.size(); j++) {
                GameObject obj1 = gameObjects.get(i);
                GameObject obj2 = gameObjects.get(j);
                if (obj1.collidesWith(obj2)) {
                    handleCollision(obj1, obj2);
                }
            }
        }
    }

    public void handleCollision(GameObject obj1, GameObject obj2) {
        if (obj1 instanceof Ship && obj2 instanceof Asteroid) {
            obj1.setAlive(false);
            obj2.setAlive(false);
            this.gameState.setState(GameState.State.GAME_OVER);
            this.handleStartRestart.run();
        }

        if (obj1 instanceof Projectile && obj2 instanceof Asteroid) {
            obj1.setAlive(false);
            obj2.setAlive(false);
            text.setText("Points: " + points.addAndGet(50));
        }
    }
}
