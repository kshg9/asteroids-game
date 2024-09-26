package com.asteroids.play;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

import java.util.HashMap;
import java.util.Map;

public class InputHandler {
    private final Map<KeyCode, Boolean> pressedKeys = new HashMap<>();
    private Runnable onStartRestart;
    private Runnable onPauseResume;
    private Runnable onStats;

    public InputHandler(Scene scene) {
        scene.setOnKeyPressed(event -> {
            pressedKeys.put(event.getCode(), true);
            event.consume();
        });

        scene.setOnKeyReleased(event -> {
            pressedKeys.put(event.getCode(), false);
            event.consume();
        });
    }

    public boolean isKeyPressed(KeyCode keyCode) {
        boolean keyPressed = pressedKeys.getOrDefault(keyCode, false);
        if (keyPressed) {
            System.out.println(keyCode + " is pressed");
        }
        return keyPressed;
    }

    public void setOnStartRestart(Runnable onStartRestart) {
        this.onStartRestart = onStartRestart;
    }

    public void setOnPauseResume(Runnable onPauseResume) {
        this.onPauseResume = onPauseResume;
    }

    public void setOnStats(Runnable onStats) {
        this.onStats = onStats;
    }

    public void handleStartRestart() {
        if (onStartRestart != null) {
            onStartRestart.run();
        }
    }

    public void handlePauseResume() {
        if (onPauseResume != null) {
            onPauseResume.run();
        }
    }

    public void handleStats() {
        if (onStats != null) {
            onStats.run();
        }
    }
}
