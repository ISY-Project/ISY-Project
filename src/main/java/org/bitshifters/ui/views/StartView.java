package org.bitshifters.ui.views;

import java.util.Collections;

import org.bitshifters.logging.BSLogger;
import org.bitshifters.ui.MainFrame;
import org.bitshifters.ui.components.CustomBorderPane;
import org.bitshifters.ui.components.NavigationButtons;

import javafx.geometry.Insets;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;


public class StartView extends CustomBorderPane {
    private static final BSLogger logger = new BSLogger(StartView.class);
    private final NavigationButtons hButtonBox;
    
    public NavigationButtons getNavigationButtons() {
        return hButtonBox;
    }

    /**
     * Constructor for the StartView
     * @param mainFrame the main frame object
     */
    public StartView(MainFrame mainFrame) {
        super(mainFrame);
        logger.debug("creating StartView");
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        hButtonBox = new NavigationButtons(mainFrame, false, false);

        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(1);
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);
        dropShadow.setColor(Color.web("#bb240d"));
        hButtonBox.setEffectProperty(dropShadow);

        // TODO: Chose a font family from the list of available fonts
        // List<String> fontFamilies = Font.getFamilies();
        // for(String item : fontFamilies) {
        //     System.out.println(item);
        // }

        String      fontFamily  = "Cascadia Code";
        double      fontSize    = 35;
        FontWeight  fontWeight  = FontWeight.BOLD;
        FontPosture fontPosture = FontPosture.REGULAR;
        
        Font buttonFont = Font.font(fontFamily, fontWeight , fontPosture, fontSize);

        hButtonBox.setButtonFont(buttonFont);
        hButtonBox.setButtonAlignment(javafx.geometry.Pos.BOTTOM_LEFT);

        hButtonBox.setButtonStyle(" -fx-text-fill: #ffffff; -fx-background-color:#ffffff00;");
        hButtonBox.getStrategoTenButton().setStyle(" -fx-text-fill: #ffffff; -fx-background-color:#ffffff00;");
        hButtonBox.getStrategoEightButton().setStyle(" -fx-text-fill: #ffffff; -fx-background-color:#ffffff00;");
        hButtonBox.setButtonSize(500, 10);


        grid.setAlignment(javafx.geometry.Pos.BOTTOM_LEFT);
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
