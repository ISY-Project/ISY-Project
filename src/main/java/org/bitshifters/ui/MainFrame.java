package org.bitshifters.ui;

import org.bitshifters.logging.BsLogger;
import org.bitshifters.ui.enums.Screens;
import org.bitshifters.ui.views.BattleshipsView;
import org.bitshifters.ui.views.StartView;
import org.bitshifters.ui.views.StrategoView;
import org.bitshifters.ui.views.TicTacToeView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * This is the mainFrame class of the program. It will be the entry point of GUI of the program.
 * @param args The command line arguments
 */
public class MainFrame extends Application {
    private static final BsLogger logger = new BsLogger(MainFrame.class);
    private final StartView startView = new StartView(this);
    private final BattleshipsView battleshipsView = new BattleshipsView(this);
    private final TicTacToeView ticTacToeView = new TicTacToeView(this);
    private final StrategoView strategoView = new StrategoView(this);
    private Stage stage;

    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        root.getChildren().addAll(startView, battleshipsView, ticTacToeView, strategoView);

        this.stage = primaryStage;

        // stage.initStyle(StageStyle.DECORATED); // normal view with status bar and close button
        stage.initStyle(StageStyle.UTILITY); // No status bar and only close button

        Scene scene = new Scene(root, 1200, 800);
        this.stage.setScene(scene);
        this.stage.show();
        // this.stage.setFullScreen(true);

        stage.addEventHandler(KeyEvent.KEY_PRESSED,  (event) -> {
            logger.debug("Key pressed: " + event.getCode());

            switch(event.getCode().getCode()) {
                case 27 ->  { // 27 = ESC key
                    stage.close();
                }
                default -> {
                    // System.out.println("Unrecognized key");
                }
            }
        });
        showScreen(Screens.START_SCREEN);
    }

    public static void run(String[] args) {
        logger.info("Starting GUI");
        launch();
    }

    public void showScreen(Screens screen) {
        logger.info("Showing screen: " + screen);
        startView.setVisible(false);
        battleshipsView.setVisible(false);
        ticTacToeView.setVisible(false);
        strategoView.setVisible(false);

        switch (screen) {
            case START_SCREEN -> startView.setVisible(true);
            case BATTLESHIP -> battleshipsView.setVisible(true);
            case TICTACTOE -> ticTacToeView.setVisible(true);
            case STRATEGO -> strategoView.setVisible(true);
            default -> throw new AssertionError();
        }
    }

    public StartView getStartView() {
        return startView;
    }

    public BattleshipsView getBattleshipsView() {
        return battleshipsView;
    }

    public TicTacToeView getTicTacToeView() {
        return ticTacToeView;
    }

    public StrategoView getStrategoView() {
        return strategoView;
    }
}
