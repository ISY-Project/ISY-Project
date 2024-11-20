package org.bitshifters.gameclient;

import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.Arrays;

import org.bitshifters.gameclient.arguments.ArgParser;
import org.bitshifters.gameclient.arguments.Flags;
import org.bitshifters.gameclient.enums.VerboseLevel;

import com.beust.jcommander.JCommander;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class Main extends Application{
    private static Config config;
    private static JCommander arguments;
    private static Flags flags;

    @Override
    public void start(Stage stage) {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        Label l = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        Scene scene = new Scene(new StackPane(l), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    /** 
     * This is the main method of the program. It will parse the command line arguments and run the program.
     * If the help flag is set, it will print the help menu and exit the program.
     * It will run the run method once the command line arguments are parsed.
     * @param args The command line arguments
     * @return void
     */
    public static void main(final String[] args){
        final Main main = new Main();
        final ArgParser argParser = new ArgParser(args);
        arguments = argParser.getJc();
        flags = argParser.getFlags();
        config = new Config(Path.of("config.ini"));
        config.write(); // instantly write the config, with default values.
        try {
            setConfigArgs();
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
            System.exit(1);
        }
        main.run(args);
    }

    /**
     * This method will set the values from the command line arguments into the config without editing the config file.
     * @return void
     */
    private static void setConfigArgs() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        final var fields = arguments.getFields();
        for (final var field : fields.entrySet()) {
            final var key = field.getKey();
            final var value = field.getValue();
            if (!value.isAssigned()) {
                continue;
            }
            final var obj = value.getObject();
            if (obj == null) {
                continue;
            }
            if (config.containsKey(key.getName())) {
                Field varField;
                varField = obj.getClass().getDeclaredField(key.getName());
                varField.setAccessible(true);
                final var varValue = varField.get(obj);
                config.setValue(key.getName(), varValue.toString());
            }
        }
    }

    /**
     * @param args The command line arguments
     */
    public void run(final String[] args) {
        if (flags.DEBUG) {
            System.out.println("\n=== Debug mode enabled ===\n");
            System.out.println("args = " + Arrays.toString(args));
        } if (flags.VERBOSE == VerboseLevel.MEDIUM || flags.VERBOSE == VerboseLevel.HIGH) {
            System.out.println("\n==== Verbose level: " + flags.VERBOSE + " ====\n");
        } if (flags.VERBOSE == VerboseLevel.MEDIUM || flags.VERBOSE == VerboseLevel.HIGH || flags.DEBUG) {
            System.out.println("Client name: " + config.getValue("username"));
            System.out.println("Server Host: " + config.getValue("host"));
            System.out.println("Server Port: " + config.getValue("port"));
        }
        launch();
    }
}
