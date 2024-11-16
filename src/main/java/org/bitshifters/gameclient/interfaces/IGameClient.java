package org.bitshifters.gameclient.interfaces;

import org.bitshifters.gameclient.games.GameTypes;
import org.bitshifters.gameclient.telnet.ResponseHandler;
import org.bitshifters.gameclient.telnet.TelnetClient;

public interface IGameClient {
    GameTypes gameType;
    TelnetClient telnetClient;
    ResponseHandler responseHandler;
}
