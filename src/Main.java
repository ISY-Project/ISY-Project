package src;

import java.util.Random;
import src.GUI.BattleshipGUI;
import src.GUI.MainFrame;
import src.GUI.TickTackToe;
import src.Telnet.Login;
import src.Telnet.Message;
import src.Telnet.Move;
import src.Telnet.ResponseHandler;
import src.Telnet.Subscribe;
import src.Telnet.TelnetClient;

public class Main {
    private final String NAME = genName();
    private static final String HOST = "localhost";
    private static final int PORT = 7789;
    private final Login login = new Login(NAME);
    private final MainFrame GUI_Frame = new MainFrame(this);
    private final BattleshipGUI battleshipGUI = GUI_Frame.getBattleshipGUI();
    private final TickTackToe tickTackToeGUI = GUI_Frame.getTickTackToe();
    private static final TelnetClient client = new TelnetClient();
    private final Handler eventHandler = new Handler(client, battleshipGUI, tickTackToeGUI.getTickTackToeGrid());
    private final ResponseHandler responseHandler = new ResponseHandler(client, eventHandler);
    // private static final GameEngine;
    // private static final Algorithm;

    public String getPlayerName() {
        return NAME;
    }

    private String genName() {
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

    public void selectGame(Subscribe game) {
        client.sendMessage(game.get());
    }

    public void sendMove(Move move) {
        client.sendMessage(move.get());
    }


    @SuppressWarnings({ "CallToPrintStackTrace", "unused" })
    public void main(String[] args) {
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
