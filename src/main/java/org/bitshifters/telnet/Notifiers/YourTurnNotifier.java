package org.bitshifters.telnet.Notifiers;

import java.util.ArrayList;

import org.bitshifters.telnet.Notifiers.interfaces.OnYourTurnSwitch;


public class YourTurnNotifier{
    protected static ArrayList<OnYourTurnSwitch> listeners = new ArrayList<>();

    /**
     * Register a listener
     * @param o
     */
    public static void register(OnYourTurnSwitch o) {
        listeners.add(o);
    }

    /**
     * Unregister a listener
     * @param o
     */
    public static void unregister(OnYourTurnSwitch o) {
        listeners.remove(o);
    }

    /**
     * Notify all listeners
     * @param isConnected
     */
    public static void notifyListeners(boolean yourTurn) {
        for (OnYourTurnSwitch listener : listeners) {
            listener.OnYourTurnSwitch(yourTurn);
        }
    }

    public static void notifyListeners(String yourTurnText) {
        for (OnYourTurnSwitch listener : listeners) {
            listener.OnYourTurnSwitch(yourTurnText);
        }
    }   
}
