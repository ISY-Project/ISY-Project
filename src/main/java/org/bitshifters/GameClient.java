package org.bitshifters;

import javax.swing.JFrame;

import org.bitshifters.gameclient.games.GameTypes;

public class GameClient extends JFrame {
    GameTypes gameType = null;

    public GameClient() {
        
    }

    public GameTypes getGameType() {
        return gameType;
    }
}
