package org.bitshifters.gameserver.components;

import java.util.ArrayList;
import java.util.List;

import org.bitshifters.gameserver.events.ScoreEvents;

public class Score implements ScoreEvents {
    private int score;
    private List<Listener> listeners = new ArrayList<>();

    public int get() {
        return score;
    }

    public void set(int score) {
        this.score = score;
        OnChange();
    }

    public void increment() {
        score++;
        OnIncrement();
    }

    public void decrement() {
        score--;
        OnDecrement();
    }

    public void increment(int amount) {
        score += amount;
        OnIncrement();
    }

    public void decrement(int amount) {
        score -= amount;
        OnDecrement();
    }

    @Override
    public void OnReset() {
        OnChange();
    }

    @Override
    public void OnChange() {
    }

    @Override
    public void OnIncrement() {
        OnChange();
    }

    @Override
    public void OnDecrement() {
        OnChange();
    }
}
