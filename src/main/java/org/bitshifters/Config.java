package org.bitshifters;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.bitshifters.interfaces.IConfig;
import org.bitshifters.logging.BsLogger;

public class Config implements IConfig {
    private static final BsLogger logger = new BsLogger(Config.class);
    private final Path path;
    private final Map<String, String> config = new HashMap<>();
    private final Map<String, Function<String, ?>> converters = new HashMap<>();

    public Config(final Path of) {
        this.path = of;
        initializeConverters();
        setDefaultValues();
        read();
    }

    @Override
    public void write() {
        logger.info("Writing configuration file");
        try (FileWriter writer = new FileWriter(path.toFile())) {
            for (final Map.Entry<String, String> entry : config.entrySet()) {
                final String k = entry.getKey();
                final String v = entry.getValue();
                writer.append(k).append("=").append(v).append("\n");
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    public String[] getNames() {
        logger.debug("Getting configuration names");
        final List<String> names = new ArrayList<>();
        for (final String k : config.keySet()) {
            names.add(k);
        }
        return names.toArray(String[]::new);
    }

    public String[] getValues() {
        logger.debug("Getting configuration values");
        final List<String> values = new ArrayList<>();
        for (final String v : config.values()) {
            values.add(v);
        }
        return values.toArray(String[]::new);
    }

    @Override
    public void setValue(final String key, final String value) {
        logger.debug("Setting value for key: " + key + " to: " + value);
        config.put(key, value);
    }

    @Override
    public String getValue(final String key) {
        return getValue(key, String.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getValue(final String key, final T target) {
        return (T) getValue(key, target.getClass());
    }

    @Override
    public <T> T getValue(final String key, final Class<T> target) {
        logger.debug("Getting value for key: " + key);
        final String value = config.get(key);
        if (value == null) {
            throw new IllegalArgumentException("Key not found %s".formatted(key));
        }
        if (target == String.class) {
            return target.cast(value);
        }
        return convert(value, target);
    }

    @Override
    public void addConverter(final Class<?> target, final Function<String, ?> converter) {
        logger.debug("Adding converter for target: " + target.getName());
        converters.put(target.getName(), converter);
    }

    private void initializeConverters() {
        logger.debug("Initializing default converters");
        addConverter(Byte.class, (final String i) -> {return Byte.valueOf(i);});
        addConverter(Short.class, (final String i) -> {return Short.valueOf(i);});
        addConverter(Integer.class, (final String i) -> {return Integer.valueOf(i);});
        addConverter(Long.class, (final String i) -> {return Long.valueOf(i);});
        addConverter(Float.class, (final String i) -> {return Float.valueOf(i);});
        addConverter(Double.class, (final String i) -> {return Double.valueOf(i);});
        addConverter(Boolean.class, (final String i) -> {return Boolean.valueOf(i);});
    }

    private <T> T convert(final String value, final Class<T> target) {
        logger.debug("Converting value: " + value + " to target: " + target.getName());
        final Function<String, ?> f = converters.get(target.getName());
        final var result = f.apply(value);
        return target.cast(result);
    }

    private void read() {
        logger.info("Reading configuration file");
        final File file = path.toFile();
        if (!file.exists()) {
            generateDefaultConfigFile();
        }
        if (!file.canRead()) {
            throw new IllegalArgumentException("File is not readable");
        }
        try (FileReader writer = new FileReader(path.toFile())) {
            final BufferedReader reader = new BufferedReader(writer);
            String line;
            while ((line = reader.readLine()) != null) {
                final String[] parts = line.split("=");
                final String key = parts[0];
                final String value = parts[1];
                config.put(key, value);
            }
        } catch (final ArrayIndexOutOfBoundsException e ) {
            System.err.println("Invalid configuration file");
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    private void setDefaultValues() {
        logger.info("Setting default config values");
        config.put("host", "65.21.191.106");
        config.put("port", "7789");
        config.put("username", "Klas2Groep4");
    }

    private void generateDefaultConfigFile() {
        logger.info("Generating default configuration file");
        try {
            final File file = path.toFile();
            if (file.createNewFile()) {
                for (final String key : config.keySet()) {
                    setValue(key, config.get(key));
                }
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    public boolean containsKey(String key) {
        logger.debug("Checking if key exists: " + key);
        return config.containsKey(key);
    }
}