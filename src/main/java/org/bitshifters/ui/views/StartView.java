package org.bitshifters.ui.views;

import java.util.Collections;

import org.bitshifters.logging.BSLogger;
import org.bitshifters.ui.MainFrame;
import org.bitshifters.ui.componenets.NavigationButtons;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;


public class StartView extends BorderPane {
    private static final BSLogger logger = new BSLogger(StartView.class);
    private final NavigationButtons hButtonBox;
    public StartView(MainFrame mainFrame) {
        logger.debug("created the StartView");
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        hButtonBox = new NavigationButtons(mainFrame, false, true);

        hButtonBox.setButtonStyle(" -fx-font-size: 4em; -fx-text-fill: #000000;");
        hButtonBox.getStrategoTenButton().setStyle(" -fx-font-size: 2.5em; -fx-text-fill: #000000;");
        hButtonBox.getStrategoEightButton().setStyle(" -fx-font-size: 3em; -fx-text-fill: #000000;");
        hButtonBox.setButtonSize(500, 100);

        grid.setAlignment(javafx.geometry.Pos.BOTTOM_CENTER);
        grid.add(hButtonBox, 0, 0);

        Image bgImageFile = new Image("file:src\\main\\resources\\images\\background.jpg");
        BackgroundImage bgImage = new BackgroundImage(
            bgImageFile,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.DEFAULT,
            new BackgroundSize(1.0, 1.0, true, true, false, false)
        );

        BackgroundFill bgFill = new BackgroundFill(
            Color.WHITE,
            new CornerRadii(0),
            new Insets(0)
        );

        Background bg = new Background(
            Collections.singletonList(bgFill),
            Collections.singletonList(bgImage)
        );

        setBackground(bg);

        this.setCenter(grid);
    }
}
