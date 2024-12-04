package org.bitshifters;

import java.util.ArrayList;
import java.util.HashMap;

import org.bitshifters.games.GameTypes;
import org.bitshifters.games.components.GridEngine;
import org.bitshifters.games.components.Player;

public class GameServer {
    private final HashMap<GameTypes, ArrayList<GridEngine>> runningGames = new HashMap<>();
    private final ArrayList<Player> players = new ArrayList<>();
}
