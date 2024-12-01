package org.bitshifters;

import javax.swing.JFrame;

import org.bitshifters.enums.GameTypes;
import org.bitshifters.games.components.Player;

public class GameClient extends JFrame {
    GameTypes gameType = null;
    Player player;

    public GameClient(String name) {
        this.player = new Player(name);
    }

    public GameTypes getGameType() {
        return gameType;
    }
}
