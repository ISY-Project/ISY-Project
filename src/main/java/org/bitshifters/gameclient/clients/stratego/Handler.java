package org.bitshifters.gameclient.clients.stratego;

import org.bitshifters.gameclient.clients.GameClient;
import org.bitshifters.gameclient.telnet.EventHandler;
import org.bitshifters.gameclient.telnet.ResponseHandler;

public class Handler extends ResponseHandler {
    Handler(final EventHandler eventHandler, final GameClient gameClient) {
        super(eventHandler, gameClient);
    }
}
