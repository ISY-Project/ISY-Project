package org.bitshifters.ui.views;

import org.bitshifters.logging.BSLogger;
import org.bitshifters.ui.MainFrame;
import org.bitshifters.ui.components.CustomBorderPane;
import org.bitshifters.ui.components.CustomButton;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class PopupView extends CustomBorderPane {
    private static final BSLogger logger = new BSLogger(TicTacToeView.class);
    private final CustomButton closeButton;
    private final String styleString = "-fx-background-color: #00000000";
    private final StackPane stackPane = new StackPane();
    private final Label label;


    /** 
     * Constructor for the TicTacToeView
     * @param mainFrame the main frame object
     */
    public PopupView(MainFrame mainFrame) {
        super(mainFrame);
        logger.debug("Creating PopupView");
        setStyle(styleString);

        this.label = new Label("This is a popup");
        this.label.setStyle("-fx-font-size: 1.5em; -fx-text-fill: #000000; -fx-background-color:#ddfcfb;");
        this.label.setMinSize(200, 100);
        this.label.setMaxSize(200, 100);
        this.label.setAlignment(javafx.geometry.Pos.CENTER);
        this.closeButton = new CustomButton("", 1300, 800, "-fx-background-color: #00000000;");
        this.closeButton.setAlignment(javafx.geometry.Pos.CENTER);

        this.stackPane.getChildren().addAll(this.label, this.closeButton);

        this.setCenter(this.stackPane);
    }

    public CustomButton getCloseButton() {
        return this.closeButton;
    }

    public Label getLabel() {
        return this.label;
    }

}
