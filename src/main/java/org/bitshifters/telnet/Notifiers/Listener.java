package org.bitshifters.telnet.Notifiers;

public interface Listener<T> {
    void callback(T value);
}
