package org.bitshifters.gameclient.games;

import org.bitshifters.gameclient.interfaces.IGameMaster;

public abstract class GameMaster implements IGameMaster {
    protected GameTypes GameType;

    @Override
    public abstract GameTypes getGameType();
}
