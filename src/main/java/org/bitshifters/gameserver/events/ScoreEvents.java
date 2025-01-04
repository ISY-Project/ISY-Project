package org.bitshifters.gameserver.events;

public interface ScoreEvents {
    void OnReset();
    void OnChange();
    void OnIncrement();
    void OnDecrement();
}
