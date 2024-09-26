package com.asteroids.play;

public class GameState {
    public enum State { READY, RUNNING, PAUSED, GAME_OVER }

    private State currentState;
    private int points;

    public GameState() {
        currentState = State.READY;
        points = 0;
    }

    public void setState(State state) {
        this.currentState = state;
    }

    public State getState() {
        return currentState;
    }

    public boolean isRunning() {
        return currentState == State.RUNNING;
    }
}