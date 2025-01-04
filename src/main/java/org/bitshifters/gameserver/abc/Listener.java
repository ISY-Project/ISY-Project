package org.bitshifters.gameserver.abc;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public class Listener implements org.bitshifters.gameserver.components.Listener {
    private List<Consumer<Listener>> listeners = new CopyOnWriteArrayList<>();

    public void register(Consumer<Listener> callback) {
        this.listeners.add(callback);
    }

    public void unregister(Consumer<Listener> callback) {
        this.listeners.remove(callback);
    }

    public void callback() {
        for (Consumer<Listener> listener : listeners) {
            try {
                listener.accept(this);
            } catch (Exception e) {
                // Handle exception or log it
                e.printStackTrace();
            }
        }
    }
}
