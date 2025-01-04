package org.bitshifters.gameserver.events;

import org.bitshifters.games.components.Player;

public interface GameEvents {
    void OnStart();
    void OnEnd();
    void OnWin(Player player);
    void OnLose(Player player);
    void OnDraw(Player player);
}