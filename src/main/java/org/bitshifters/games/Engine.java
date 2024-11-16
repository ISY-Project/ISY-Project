package org.bitshifters.games;

public class Engine {
    protected Player activePlayer;
    
    public Player getActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(final Player activePlayer) {
        this.activePlayer = activePlayer;
    }
}
