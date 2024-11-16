package org.bitshifters.gameclient.clients;

import org.bitshifters.gameclient.games.GameTypes;
import org.bitshifters.gameclient.interfaces.IGameClient;
import org.bitshifters.gameclient.telnet.ResponseHandler;
import org.bitshifters.gameclient.telnet.TelnetClient;

public abstract class GameClient implements Runnable, IGameClient {
    protected GameTypes gameType;
    protected TelnetClient telnetClient;
    protected ResponseHandler responseHandler;

    public abstract GameTypes getGameType();
    @Override
    public abstract void run();
}
