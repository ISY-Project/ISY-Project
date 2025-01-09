package org.bitshifters.telnet.Notifiers;

import java.util.ArrayList;

import org.bitshifters.telnet.Notifiers.interfaces.OnConnectionSwitch;


public class ConnectionNotifier{
    protected static ArrayList<OnConnectionSwitch> listeners = new ArrayList<>();

    /**
     * Register a listener
     * @param o
     */
    public static void register(OnConnectionSwitch o) {
        listeners.add(o);
    }

    /**
     * Unregister a listener
     * @param o
     */
    public static void unregister(OnConnectionSwitch o) {
        listeners.remove(o);
    }

    /**
     * Notify all listeners
     * @param isConnected
     */
    public static void notifyListeners(boolean isConnected) {
        for (OnConnectionSwitch listener : listeners) {
            listener.OnConnectionSwitch(isConnected);
        }
    }
}
