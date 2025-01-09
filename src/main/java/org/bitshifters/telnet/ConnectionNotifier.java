package org.bitshifters.telnet;

import java.util.ArrayList;

public class ConnectionNotifier{
    protected static ArrayList<OnConnectionSwitch> listeners = new ArrayList<OnConnectionSwitch>();

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
