package org.bitshifters.telnet.Events;

public interface Placed {
    String MESSAGE = Game.MESSAGE + "Opponent Placed ";
    void onPlaced(int index);
}