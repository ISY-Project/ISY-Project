package src;

import java.util.Random;

import src.GUI.BattleshipGUI;
import src.GUI.MainFrame;
import src.GUI.TickTackToeGui;
import src.GameEngine.BattleshipEngine;
import src.GameEngine.TTTEngine;
import src.Telnet.Login;
import src.Telnet.Message;
import src.Telnet.ResponseHandler;
import src.Telnet.Subscribe;
import src.Telnet.TelnetClient;

public class Main {
    private static final String NAME = genName();
    private static final String HOST = "65.21.191.106";
    private static final int PORT = 7789;
    private static final Login login = new Login(NAME);
    private static final TelnetClient client = new TelnetClient();
    // Engines
    private static final BattleshipEngine battleshipEngine = new BattleshipEngine(10, "Player1", "Player2");
    private static final TTTEngine tttEngine = new TTTEngine(3, "PlayerX", "PlayerO");
    // GUI
    private static final MainFrame GUI_Frame = new MainFrame();
    // Battleship
    private static final BattleshipGUI battleshipGUI = GUI_Frame.getBattleshipGUI();
    private static final BattleshipHandler battleshipHandler = new BattleshipHandler(client, battleshipGUI, battleshipEngine);
    private static final ResponseHandler responseHandler = new ResponseHandler(client, battleshipHandler);
    /// TicTacToe
    private static final TickTackToeGui tickTackToeGUI = GUI_Frame.getTickTackToe();
    private static final TTTHandler tttHandler = new TTTHandler(client, tickTackToeGUI, NAME, tttEngine);
    private static final ResponseHandler TTTResponseHandler = new ResponseHandler(client, tttHandler);

    public static void main(String[] args) {
        Message message = new Message(NAME + " Here to win the game!1!"); // TODO: add more messages

        initializeChatBoxes();
        connectToServer(message);
    }

    public static TTTEngine getTTTEngine() {
        return tttEngine;
    }

    public static BattleshipEngine getBattleshipEngine() {
        return battleshipEngine;
    }

    public static TelnetClient getClient() {
        return client;
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

    public static void joinGameLobby(Subscribe game) {
        client.sendMessage(game.get());
    }

    private static void initializeChatBoxes() {
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
    }

    private static void connectToServer(Message message) {
        try {
            client.connect(HOST, PORT);
            client.sendMessage(login.get());
            client.sendMessage(message.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void runTicTacToeComp() {
        try {
            String response = client.receiveMessage();
            while (response.contains("")) { // replace while loop with calls from the translation layer and GUI
                client.showMessage("Received: " + response);
                TTTResponseHandler.handle(response);
                response = client.receiveMessage();
                if (response == null) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void runBattleshipComp() {
        try {
            // TODO: Place ships.
            // handle waiting for the game to start
            String response = client.receiveMessage();
            while (response.contains("")) { // replace while loop with calls from the translation layer and GUI
                client.showMessage("Received: " + response);
                responseHandler.handle(response);
                response = client.receiveMessage();
                if (response == null) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
