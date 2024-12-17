package org.bitshifters.ui;

import java.util.logging.Level;

import org.bitshifters.ClientController;
import org.bitshifters.Config;
import org.bitshifters.gameclient.TicTacToeClient;
import org.bitshifters.games.components.Player;
import org.bitshifters.logging.BSLogger;
import org.bitshifters.ui.enums.Screens;
import org.bitshifters.ui.views.BattleshipsView;
import org.bitshifters.ui.views.StartView;
import org.bitshifters.ui.views.StrategoView;
import org.bitshifters.ui.views.TicTacToeView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle; 

/**
 * This is the mainFrame class of the program. It will be the entry point of GUI of the program.
 * @param args The command line arguments
 */
public class MainFrame extends Application {
    private static final BSLogger logger = new BSLogger(MainFrame.class);
    private final StartView startView = new StartView(this);
    private final BattleshipsView battleshipsView = new BattleshipsView(this);
    private final TicTacToeView ticTacToeView = new TicTacToeView(this);
    private final StrategoView strategoViewTen = new StrategoView(this, false);
    private final StrategoView strategoViewEight = new StrategoView(this, true);
    private Stage stage;
    private Popup popup;
    private boolean tests = false;
    private static final Config config = Config.getInstance();
    private static final Player player = new Player(config.getValue("username"));
    private ClientController clientController;

    /**
     * This is the main method of the program. this will be run by JavaFX
     * @param primaryStage the primary stage of the program (given via JavaFX)
     */
    @Override
    public void start(Stage primaryStage) {
        if (tests) {
            this.stage = primaryStage;
            return;
        }

        StackPane root = new StackPane();
        root.getChildren().addAll(startView, battleshipsView, ticTacToeView, strategoViewTen, strategoViewEight);
        new TicTacToeClient(ticTacToeView, player, new Player("Opponent"));
        // new BattleshipClient(battleshipsView, this.player, new Player("Opponent"));
        // new StrategoClient(strategoView, this.player, new Player("Opponent"));

        this.stage = primaryStage;

        stage.initStyle(StageStyle.DECORATED); // normal view with status bar and close button
        // stage.initStyle(StageStyle.UTILITY); // No status bar and only close button

        Scene scene = new Scene(root, 1300, 800);
        this.stage.setScene(scene);
        this.stage.show();
        // this.stage.setFullScreen(true);

        stage.addEventHandler(KeyEvent.KEY_PRESSED,  (event) -> {
            logger.debug("Key pressed: " + event.getCode());

            switch(event.getCode().getCode()) {
                case 27 ->  { // 27 = ESC key
                    stage.close();
                    System.exit(0);
                }
                default -> {
                    // System.out.println("Unrecognized key");
                }
            }
        });
        
        setUpPopup();
        // showPopup("test"); // show popup when the program starts

        showScreen(Screens.START_SCREEN);
        this.clientController = ClientController.getInstance(this);
    }

    /**
     * This method will run the GUI and start javaFX
     * @param args The command line arguments
     */
    public static void run(String[] args) {
        logger.info("Starting GUI");
        launch();
    }

    /**
     * This method will run the GUI and start javaFX
     * @param tests boolean to indicate if the GUI is started for tests and should not show any windows
     */
    public void start(boolean tests) {
        logger.info("Starting GUI for tests");
        this.tests = tests;
        launch();
    }

    /**
     * This method will set up the popup window
     */
    public void setUpPopup() {
        if (this.popup != null) {
            return;
        }
        this.popup = new Popup();
        this.popup.setX(300);
        this.popup.setY(200);
        this.popup.setWidth(200);
        this.popup.setHeight(100);
        this.popup.setAutoHide(true); // close popup when clicked outside

        Label label = new Label("This is a popup");
        label.setStyle("-fx-background-color: white; -fx-padding: 10px;");
        this.popup.getContent().add(label);
    }

    /**
     * This method will show a popup with the given message
     * @param message the message to show in the popup
     */
    public void showPopup(String message) {
        Label label = (Label) this.popup.getContent().get(0);
        label.setText(message);
        this.popup.show(stage);
    }

    /**
     * This method will show the given screen based on the enum
     * @param screen the screen to show
     */
    public void showScreen(Screens screen) {
        logger.log(Level.INFO, "Showing screen: {0}", screen);
        startView.setVisible(false);
        battleshipsView.setVisible(false);
        ticTacToeView.setVisible(false);
        strategoViewTen.setVisible(false);
        strategoViewEight.setVisible(false);

        switch (screen) {
            case START_SCREEN -> startView.setVisible(true);
            case BATTLESHIP -> battleshipsView.setVisible(true);
            case TICTACTOE -> ticTacToeView.setVisible(true);
            case STRATEGOTEN -> strategoViewTen.setVisible(true);
            case STRATEGOEIGHT -> strategoViewEight.setVisible(true);
            default -> throw new AssertionError();
        }
    }

    /**
     * This method will return the startView
     * @return the startView
     */
    public StartView getStartView() {
        return startView;
    }

    /**
     * This method will return the battleshipsView
     * @return the battleshipsView
     */
    public BattleshipsView getBattleshipsView() {
        return battleshipsView;
    }

    /**
     * This method will return the ticTacToeView
     * @return the ticTacToeView
     */
    public TicTacToeView getTicTacToeView() {
        return ticTacToeView;
    }

    /**
     * This method will return the 10x10 version of the strategoView
     * @return the strategoView
     */
    public StrategoView getStrategoViewTen() {
        return strategoViewTen;
    }

    /**
     * This method will return the 8x8 version of the strategoView
     * @return the strategoView
     */
    public StrategoView getStrategoViewEight() {
        return strategoViewEight;
    }

    public ClientController getClientController() {
        return clientController;
    }
}
