import java.awt.event.ActionListener;

import GUI.BattleshipGUI;
import Telnet.Login;
import Telnet.Message;
import Telnet.ResponseHandler;
import Telnet.Subscribe;
import Telnet.TelnetClient;

public class Main {
    public static void main(String[] args) {
        String name = "SeaCarpetBomber";
        name += (int) (Math.random() * 10);

        var client = new TelnetClient();
        var GUI = new BattleshipGUI();
        Login login = new Login(name);
        Message message = new Message(name + " Here to win the game!1!"); // TODO: add more messages
        Subscribe subscribe = new Subscribe("battleship");
        Handler eventHandler = new Handler(client, GUI);
        ResponseHandler responseHandler = new ResponseHandler(client, eventHandler);

        GUI.getChatBox().getChatArea().addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                String msg = GUI.getChatBox().getChatArea().getText();
                client.sendMessage(new Message(msg).get());
            }
        });

        try {
            client.connect("localhost", 7789);
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
