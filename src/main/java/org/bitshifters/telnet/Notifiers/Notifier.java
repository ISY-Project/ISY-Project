package org.bitshifters.telnet.Notifiers;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Notifier<T extends Listener<G>, G>{
    private final List<T> listeners = new CopyOnWriteArrayList<>();

    public void register(T listener) {
        listeners.add(listener);
    }

    public void unregister(T listener) {
        listeners.remove(listener);
    }

    public void notifyListeners(G value) {
        for (T listener : listeners) {
            callback(listener, value);
        }
    }

    public void callback(T listener, G value) {
        listener.callback(value);
    }
}