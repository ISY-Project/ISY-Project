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
    private static final String HOST = "65.21.191.106";
    private static final int PORT = 7789;
    private static final Login login = new Login(NAME);
    private static final MainFrame GUI_Frame = new MainFrame();
    private static final BattleshipGUI battleshipGUI = GUI_Frame.getBattleshipGUI();
    private static final TickTackToe tickTackToeGUI = GUI_Frame.getTickTackToe();
    private static final TelnetClient client = new TelnetClient();
    private static final TTTHandler eventHandler = new TTTHandler(client, GUI_Frame, battleshipGUI, tickTackToeGUI.getTickTackToeGrid());
    private static final ResponseHandler responseHandler = new ResponseHandler(client, eventHandler);
    // private static final GameEngine;
    // private static final Algorithm;
    // private static final BattleshipEngine battleshipEngine = new BattleshipEngine(8, "test", "test2");
    private static boolean inMatch = false;

    public static boolean isInMatch() {
        return inMatch;
    }

    public static void setInMatch(boolean value) {
        inMatch = value;
    }

    public static void toggleBattleshipGrid() {
        if (isInMatch()) {
            GUI_Frame.getBattleshipGUI().getPlayerGrid().enableGrid();
            GUI_Frame.getBattleshipGUI().getOpponentGrid().enableGrid();
        } else {
            GUI_Frame.getBattleshipGUI().getPlayerGrid().disableGrid();
            GUI_Frame.getBattleshipGUI().getOpponentGrid().disableGrid();
        }
    }

    public static void toggleTTTGrid() {
        if (isInMatch()) {
            GUI_Frame.getTickTackToe().getTickTackToeGrid().enableGrid();
        } else {
            GUI_Frame.getTickTackToe().getTickTackToeGrid().disableGrid();
        }
    }


    public static TelnetClient getClient() {
        return client;
    }

    public static MainFrame getMainFrame() {
        return GUI_Frame;
    }

    // public static BattleshipEngine getBattleshipEngine() {
    //     return battleshipEngine;
    // }

    public static String getPlayerName() {
        return NAME;
    }

    public static String showGetName() {
        String name = JOptionPane.showInputDialog("What is your game name?");
        if (name == null || name.isEmpty()) {
            return genName();
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
            int randomLimitedInt = leftLimit + (int) 
            (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();
        return generatedString;
    }

    public void sendMove(Move move) {
        client.sendMessage(move.get());
    }

    @SuppressWarnings({ "CallToPrintStackTrace" })
    public static void main(String[] args) {
        GUI_Frame.setTitle(NAME); // Set the title of the window

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

        try {
            client.connect(HOST, PORT);
            // TODO: replace these calls with GUI buttons or menu's
            client.sendMessage(login.get());
            client.sendMessage(message.get());
            // handle waiting for the game to start
            String response = client.receiveMessage();
            while (response.contains("")) {
                client.showMessage("Received: " + response);
                responseHandler.handle(response);
                response = client.receiveMessage();
                if (response == null) {break;}
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                client.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
