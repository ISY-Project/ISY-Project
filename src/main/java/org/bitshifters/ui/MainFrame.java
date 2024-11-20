package org.bitshifters.ui;

import java.io.IOException;
import java.util.Properties;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * This is the mainFrame class of the program. It will be the entry point of GUI of the program.
 * @param args The command line arguments
 */
public class MainFrame extends Application {
    @Override
    public void start(Stage stage) {
        GridPane root = new GridPane(5, 5);
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        Label l = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        root.add(l, 0, 0);
        try {
            final Properties properties = new Properties();
            properties.load(this.getClass().getClassLoader().getResourceAsStream("project.properties"));
            stage.setTitle("Bitshifters-" + properties.getProperty("version"));
            Label l2 = new Label("Version " + properties.getProperty("version") + ", artifactId " + properties.getProperty("artifactId") + ".");
            root.add(l2, 0, 1);
        } catch (IOException e) {
            stage.setTitle("Bitshifters");
        }

        Scene scene = new Scene(root, 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
