import GUI.BattleshipGUI;
import GUI.MainFrame;
import GUI.TickTackToe;
import Telnet.Login;
import Telnet.Message;
import Telnet.ResponseHandler;
import Telnet.Subscribe;
import Telnet.TelnetClient;
import java.util.Random;

public class Main {
    private final String NAME = genName();
    private static final String HOST = "localhost";
    private static final int PORT = 7789;
    private final Login login = new Login(NAME);
    private static final MainFrame GUI_Frame = new MainFrame();
    private final BattleshipGUI battleshipGUI = GUI_Frame.getBattleshipGUI();
    private final TickTackToe tickTackToeGUI = GUI_Frame.getTickTackToe();
    private static final TelnetClient client = new TelnetClient();
    private final Handler eventHandler = new Handler(client, battleshipGUI);
    private final ResponseHandler responseHandler = new ResponseHandler(client, eventHandler);
    // private static final GameEngine;
    // private static final Algorithm;


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


    @SuppressWarnings({ "CallToPrintStackTrace", "unused" })
    public void main(String[] args) {
        Message message = new Message(NAME + " Here to win the game!1!"); // TODO: add more messages
        Subscribe subscribe = new Subscribe("battleship");

        battleshipGUI.getChatBox().getChatArea().addActionListener((java.awt.event.ActionEvent e) -> {
            String msg = battleshipGUI.getChatBox().getChatArea().getText();
            client.sendMessage(new Message(msg).get());
        });

        tickTackToeGUI.getChatBox().getChatArea().addActionListener((java.awt.event.ActionEvent e) -> {
            String msg = tickTackToeGUI.getChatBox().getChatArea().getText();
            client.sendMessage(new Message(msg).get());
        });

        try {
            client.connect(HOST, PORT);
            // TODO: replace these calls with GUI buttons or menu's
            client.sendMessage(login.get());
            client.sendMessage(message.get());
            client.sendMessage(subscribe.get());
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
