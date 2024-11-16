package org.bitshifters.gameclient.interfaces;

import java.util.function.Function;

public interface IConfig {

    void write();

    void setValue(String key, String value);

    String getValue(String key);

    <T> T getValue(String key, T target);

    <T> T getValue(String key, Class<T> target);

    void addConverter(final Class<?> target, final Function<String, ?> converter);
}