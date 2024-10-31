import java.awt.event.ActionListener;

import GUI.BattleshipGUI;
import Telnet.Login;
import Telnet.Message;
import Telnet.ResponseHandler;
import Telnet.Subscribe;
import Telnet.TelnetClient;

public class Main {
    private static final String name = "SeaCarpetBomber";
    private static String host = "localhost";
    private static int port = 7789;
    private static final Login login = new Login(name);
    private static final BattleshipGUI GUI = new BattleshipGUI();
    private static final TelnetClient client = new TelnetClient();
    private static final Handler eventHandler = new Handler(client, GUI);
    private static final ResponseHandler responseHandler = new ResponseHandler(client, eventHandler);


    public static void main(String[] args) {
        Message message = new Message(name + " Here to win the game!1!"); // TODO: add more messages
        Subscribe subscribe = new Subscribe("battleship");

        GUI.getChatBox().getChatArea().addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                String msg = GUI.getChatBox().getChatArea().getText();
                client.sendMessage(new Message(msg).get());
            }
        });

        try {
            client.connect("localhost", 7789);
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
