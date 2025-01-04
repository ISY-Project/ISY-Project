package org.bitshifters.gameserver.abc;

import org.bitshifters.gameserver.GameServer;
import org.bitshifters.gameserver.components.Score;
import org.bitshifters.gameserver.events.PlayerEvents;

public abstract class Player implements PlayerEvents {
    public final Score score = new Score();

    @Override
    public void OnJoin(org.bitshifters.games.components.Player player) {
        GameServer.players.add(player);
    }

    @Override
    public void OnLeave(org.bitshifters.games.components.Player player) {
        GameServer.players.remove(player);
    }
}
