package com.asteroids.play;

import java.util.ArrayList;
import java.util.List;

public class ScoreTracker {
    private final List<ScoreEntry> scoreHistory;
    private int attemptNo;

    public ScoreTracker() {
        this.scoreHistory = new ArrayList<>();
        this.attemptNo = 0;
    }

    public void addScore(int score) {
        scoreHistory.add(new ScoreEntry(++attemptNo, score));
    }

    public List<ScoreEntry> getScoreHistory() {
        return new ArrayList<>(scoreHistory);
    }

    public record ScoreEntry(double timestamp, int score) {} // cool
}
