package org.bitshifters;

import javax.swing.JFrame;

public class GameClient extends JFrame {
    GameTypes gameType = null;

    public GameClient() {
        
    }

    public GameTypes getGameType() {
        return gameType;
    }
}
