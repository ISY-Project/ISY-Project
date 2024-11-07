package src;

import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import src.Engines.BaseEngine;
import src.Enums.PlayingGameState;
import src.GUI.Views.BattleshipGUI;
import src.GUI.Views.MainFrame;
import src.GUI.Views.TickTackToeGui;
import src.Helpers.StringGenerator;
import src.Telnet.EventHandler;
import src.Telnet.ResponseHandler;
import src.Telnet.TelnetClient;
import src.Telnet.Commands.Login;
import src.Telnet.Commands.Message;
import src.Telnet.Commands.Move;

public class GameClient implements Runnable {
    private String name;
    private String host;
    private int port;
    private Login login;
    private final Map<String, EventHandler> eventHandlers = new HashMap<>();
    private final Map<String, ResponseHandler> responseHandlers = new HashMap<>();
    private final Map<String, JPanel> GUIs = new HashMap<>();
    private final Map<String, BaseEngine> engines = new HashMap<>();
    private final MainFrame GUI_Frame; // TODO: move into GUIs.
    private final BattleshipGUI battleshipGUI; // TODO move into GUIs.
    private final TickTackToeGui tickTackToeGUI; // TODO move into GUIs.
    private final TelnetClient telnetClient = new TelnetClient();
    private PlayingGameState playingState = PlayingGameState.PRE_GAME_SETUP;

    GameClient() {
        this(getUserName());
    }

    GameClient(String host, int port) {
        this(getUserName(), host, 7789);
    }

    GameClient(String name){
        this(name, "65.21.191.106", 7789);
    }

    GameClient(String name, String host, int port) {
        this.name = getUserName();
        this.host = host;
        this.port = port;
        this.login = new Login(name);
        GUI_Frame = new MainFrame();
        battleshipGUI = GUI_Frame.getBattleshipGUI();
        tickTackToeGUI = GUI_Frame.getTickTackToe();

        TTTHandler tttHandler = new TTTHandler(tickTackToeGUI.getTickTackToeGrid());
        BattleshipHandler battleshipHandler = new BattleshipHandler();
        addEventHandler("TTT", tttHandler);
        addEventHandler("Battleship", tttHandler);
        addResponseHandler("TTT", new ResponseHandler(tttHandler));
        addResponseHandler("Battleship", new ResponseHandler(battleshipHandler));
    }

    public static String getUserName() {
        String name = JOptionPane.showInputDialog("What is your game name?");
        // TODO: store the name in a file? also create a config file.
        if (name == null || name.isEmpty()) {
            return StringGenerator.randomString(10);
        }
        return name;
    }

    public void run() {
        // TODO refactor
        GUI_Frame.setTitle(name); // Set the title of the window

        Message message = new Message(Messages.getRandomMessage().getValue());

        battleshipGUI.getChatBox().getChatArea().addActionListener((java.awt.event.ActionEvent e) -> {
            String msg = battleshipGUI.getChatBox().getChatArea().getText();
            if (!msg.isEmpty()) {
                telnetClient.sendMessage(new Message(msg).get());
            }
        });

        tickTackToeGUI.getChatBox().getChatArea().addActionListener((java.awt.event.ActionEvent e) -> {
            String msg = tickTackToeGUI.getChatBox().getChatArea().getText();
            if (!msg.isEmpty()) {
                telnetClient.sendMessage(new Message(msg).get());
            }
        });

        try {
            telnetClient.connect(host, port);
            telnetClient.sendMessage(login.get());
            telnetClient.sendMessage(message.get());
            String response = telnetClient.receiveMessage();
            while (response.contains("")) {
                telnetClient.showMessage("Received: " + response);
                ResponseHandler tttResponseHandler = getResponseHandler("TTT");
                ResponseHandler battleshipResponseHandler = getResponseHandler("Battleship");
                tttResponseHandler.handle(response);
                battleshipResponseHandler.handle(response);
                if (playingState == PlayingGameState.ENDGAME) {
                    playingState = PlayingGameState.NONE;
                } // Reset the gameType after every handler ran.
                response = telnetClient.receiveMessage();
            }
            JOptionPane.showMessageDialog(GUI_Frame, "Connection lost");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeClient();
        }
    }

    public PlayingGameState getPlayingState() {
        return playingState;
    }

    public void setPlayingState(PlayingGameState gameType) {
        playingState = gameType;
    }

    public void toggleBattleshipGrid() {
        // TODO: move to its own class.
        if (playingState == PlayingGameState.BATTLESHIP) {
            GUI_Frame.getBattleshipGUI().getPlayerGrid().enableGrid();
            GUI_Frame.getBattleshipGUI().getOpponentGrid().enableGrid();
        } else {
            GUI_Frame.getBattleshipGUI().getPlayerGrid().disableGrid();
            GUI_Frame.getBattleshipGUI().getOpponentGrid().disableGrid();
        }
    }

    public void toggleTTTGrid() {
        // TODO: move to its own class.
        if (playingState == PlayingGameState.TTT) {
            GUI_Frame.getTickTackToe().getTickTackToeGrid().enableGrid();
        } else {
            GUI_Frame.getTickTackToe().getTickTackToeGrid().disableGrid();
        }
    }

    public JPanel getGui(String key) {
        return GUIs.get(key);
    }

    public String getPlayerName() {
        return name;
    }

    public void sendMove(Move move) {
        telnetClient.sendMessage(move.get());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.login = new Login(name);
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public MainFrame getGUI_Frame() {
        return GUI_Frame;
    }

    public BattleshipGUI getBattleshipGUI() {
        return battleshipGUI;
    }

    public TickTackToeGui getTickTackToeGUI() {
        return tickTackToeGUI;
    }

    public TelnetClient getTelnetClient() {
        return telnetClient;
    }

    public void addEventHandler(String key, EventHandler eventHandler) {
        eventHandlers.put(key, eventHandler);
    }

    public void addResponseHandler(String key, ResponseHandler responseHandler) {
        responseHandlers.put(key, responseHandler);
    }

    public void addGUI(String key, JPanel gui) {
        GUIs.put(key, gui);
    }

    public EventHandler getEventHandler(String key) {
        return eventHandlers.get(key);
    }

    public ResponseHandler getResponseHandler(String key) {
        return responseHandlers.get(key);
    }

    public JPanel getGUI(String key) {
        return GUIs.get(key);
    }

    public void removeEventHandler(String key) {
        eventHandlers.remove(key);
    }

    public void removeResponseHandler(String key) {
        responseHandlers.remove(key);
    }

    public void removeGUI(String key) {
        GUIs.remove(key);
    }

    public void addEngine(String key, BaseEngine engine) {
        engines.put(key, engine);
    }

    public BaseEngine getEngine(String key) {
        return engines.get(key);
    }

    public void removeEngine(String key) {
        engines.remove(key);
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    private void closeClient() {
        try {
            telnetClient.close();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
