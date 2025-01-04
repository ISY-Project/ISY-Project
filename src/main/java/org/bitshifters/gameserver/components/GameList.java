package org.bitshifters.gameserver.components;

import java.util.ArrayList;
import java.util.List;

import org.bitshifters.games.components.Engine;

public class GameList {
    private static final List<Engine> games = new ArrayList<>();

    public static void addGame(final Engine game) {
        games.add(game);
    }

    public static List<Engine> getGames() {
        return games;
    }
}
