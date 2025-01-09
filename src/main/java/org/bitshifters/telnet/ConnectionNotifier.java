package org.bitshifters.telnet;

import java.util.ArrayList;

public class ConnectionNotifier{
    protected static ArrayList<OnConnectionSwitch> listeners = new ArrayList<OnConnectionSwitch>();

    public static void register(OnConnectionSwitch o) {
        listeners.add(o);
    }

    public static void unregister(OnConnectionSwitch o) {
        listeners.remove(o);
    }

    public static void notifyListeners(boolean isConnected) {
        for (OnConnectionSwitch listener : listeners) {
            listener.handle(isConnected);
        }
    }
}
