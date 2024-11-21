package org.bitshifters.games.components;

public abstract class Engine {
    protected Player activePlayer;
    
    /**
     * Deze speler is nu aan de beurt.
     * @return
     */
    public Player getActivePlayer() {
        return activePlayer;
    }

    protected void setActivePlayer(final Player activePlayer) {
        this.activePlayer = activePlayer;
    }

    public abstract boolean validateMove(final int row, final int col, final Player player);
    public abstract boolean isGameOver();
    public abstract Player getWinner();
}
