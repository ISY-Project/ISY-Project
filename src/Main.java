package src;

import java.util.Random;
import javax.swing.JOptionPane;
import src.GUI.BattleshipGUI;
import src.GUI.MainFrame;
import src.GUI.TickTackToe;
import src.Telnet.Login;
import src.Telnet.Message;
import src.Telnet.Move;
import src.Telnet.ResponseHandler;
import src.Telnet.TelnetClient;

public class Main {
    private static final String NAME = showGetName();
    private static final String HOST = "172.201.112.199"; // <- official IP // "localhost"; // "65.21.191.106"; 
    private static final int PORT = 7789;
    private static final Login login = new Login(NAME);
    private static final MainFrame GUI_Frame = new MainFrame();
    private static final BattleshipGUI battleshipGUI = GUI_Frame.getBattleshipGUI();
    private static final TickTackToe tickTackToeGUI = GUI_Frame.getTickTackToe();
    private static final TelnetClient client = new TelnetClient();
    private static final TTTHandler tttHandler = new TTTHandler(tickTackToeGUI.getTickTackToeGrid());
    private static final ResponseHandler tttResponseHandler = new ResponseHandler(tttHandler);
    // private static final GameEngine;
    // private static final Algorithm;
    // private static final BattleshipEngine battleshipEngine = new
    // BattleshipEngine(8, "test", "test2");
    private static final BattleshipHandler battleshipHandler = new BattleshipHandler();
    private static final ResponseHandler battleshipResponseHandler = new ResponseHandler(battleshipHandler);
    private static GameType gameType = GameType.NONE;

    public static GameType getGameType() {
        return gameType;
    }

    public static void setGameType(GameType gameType) {
        Main.gameType = gameType;
    }

    public static void toggleBattleshipGrid() {
        if (gameType == GameType.BATTLESHIP) {
            GUI_Frame.getBattleshipGUI().getPlayerGrid().enableGrid();
            GUI_Frame.getBattleshipGUI().getOpponentGrid().enableGrid();
        } else {
            GUI_Frame.getBattleshipGUI().getPlayerGrid().disableGrid();
            GUI_Frame.getBattleshipGUI().getOpponentGrid().disableGrid();
        }
    }

    public static void toggleTTTGrid() {
        if (gameType == GameType.TTT) {
            GUI_Frame.getTickTackToe().getTickTackToeGrid().enableGrid();
        } else {
            GUI_Frame.getTickTackToe().getTickTackToeGrid().disableGrid();
        }
    }

    public static TelnetClient getTelnetClient() {
        return client;
    }

    public static MainFrame getMainFrame() {
        return GUI_Frame;
    }

    // public static BattleshipEngine getBattleshipEngine() {
    // return battleshipEngine;
    // }

    public static String getPlayerName() {
        return NAME;
    }

    public static String showGetName() {
        String name = JOptionPane.showInputDialog("What is your game name?");
        if (name == null || name.isEmpty()) {
            return genName(); // "Klas2Groep4"; // genName();
        }
        return name;
    }

    private static String genName() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();
        return generatedString;
    }

    public void sendMove(Move move) {
        client.sendMessage(move.get());
    }

    @SuppressWarnings({"unused", "CallToPrintStackTrace"})
    public static void main(String[] args) throws Exception {
        GUI_Frame.setTitle(NAME); // Set the title of the window

        Message message = new Message(Messages.getRandomMessage().getValue());

        battleshipGUI.getChatBox().getChatArea().addActionListener((java.awt.event.ActionEvent e) -> {
            String msg = battleshipGUI.getChatBox().getChatArea().getText();
            if (!msg.isEmpty()) {
                client.sendMessage(new Message(msg).get());
            }
        });

        tickTackToeGUI.getChatBox().getChatArea().addActionListener((java.awt.event.ActionEvent e) -> {
            String msg = tickTackToeGUI.getChatBox().getChatArea().getText();
            if (!msg.isEmpty()) {
                client.sendMessage(new Message(msg).get());
            }
        });

        try {
            client.connect(HOST, PORT);
            client.sendMessage(login.get());
            client.sendMessage(message.get());
            String response = client.receiveMessage();
            long time = System.currentTimeMillis();
            long alive_counter = time + 100000;
            while (response.contains("")) {
                if (System.currentTimeMillis() > alive_counter) {
                    client.sendMessage(new Message("keep-alive").get());
                    alive_counter = System.currentTimeMillis() + 100000;
                }
                client.showMessage("Received: " + response);
                if (response.contains("Tic-tac-toe")) {
                    // System.out.println("Tic-tac-toe");
                    tttResponseHandler.handle(response);
                } else if (response.contains("Battleship")) {
                    // System.out.println("Battleship");
                    battleshipResponseHandler.handle(response);
                } else {
                    // System.out.println("Unknown game");
                    // let the tic tac toe handler handle the message
                    tttResponseHandler.handle(response);
                    battleshipResponseHandler.handle(response);
                }
                if (gameType == GameType.ENDGAME) {gameType = GameType.NONE;} // Reset the gameType after every handler ran.
                response = client.receiveMessage();
            }
            JOptionPane.showMessageDialog(GUI_Frame, "Connection lost");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client.close();
        }
    }
}
