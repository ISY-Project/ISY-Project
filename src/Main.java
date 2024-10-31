package src;

import java.util.Random;

import src.GUI.BattleshipGUI;
import src.GUI.MainFrame;
import src.GUI.TickTackToe;
import src.Telnet.Login;
import src.Telnet.Message;
import src.Telnet.ResponseHandler;
import src.Telnet.Subscribe;
import src.Telnet.TelnetClient;

public class Main {
    private final static String NAME = genName();
    private static final String HOST = "localhost";
    private static final int PORT = 7789;
    private static final Login login = new Login(NAME);
    private static final MainFrame GUI_Frame = new MainFrame();
    private static final BattleshipGUI battleshipGUI = GUI_Frame.getBattleshipGUI();
    private static final TickTackToe tickTackToeGUI = GUI_Frame.getTickTackToe();
    private static final TelnetClient client = new TelnetClient();
    private static final BattleshipHandler battleshipHandler = new BattleshipHandler(client, GUI_Frame, battleshipGUI);
    private static final TTTHandler tttHandler = new TTTHandler(client, tickTackToeGUI);
    private static final ResponseHandler responseHandler = new ResponseHandler(client, battleshipHandler);
    // private static final GameEngine;
    // private static final Algorithm;


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

    public static void selectGame(Subscribe game) {
        client.sendMessage(game.get());
    }


    @SuppressWarnings({ "CallToPrintStackTrace", "unused" })
    public static void main(String[] args) {
        Message message = new Message(NAME + " Here to win the game!1!"); // TODO: add more messages

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

        connectToServer(message);
    }

    private static void connectToServer(Message message) {
        // TODO: replace these calls with GUI buttons or menu's. Niet meer nodig???
        try {
            client.connect(HOST, PORT);
            client.sendMessage(login.get());
            client.sendMessage(message.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void runTicTacToeComp(Message message) {
        
    }

    private static void runBattleshipComp(Message message) {
        // TODO: Replace this with GUI buttons or menu's.
        // TODO: Currently this blocks the gui. It could easily be integrated.
        try {
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
