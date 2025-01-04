package org.bitshifters.gameserver.events;

import org.bitshifters.games.components.Player;

public interface PlayerEvents {
    void OnJoin(Player player);
    void OnLeave(Player player);
}
