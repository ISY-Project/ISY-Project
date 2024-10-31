import GUI.BattleshipGUI;
import GUI.MainFrame;
import GUI.TickTackToe;
import Telnet.Login;
import Telnet.Message;
import Telnet.ResponseHandler;
import Telnet.Subscribe;
import Telnet.TelnetClient;

public class Main {
    private static final String name = "SeaCarpetBomber";
    private static final String host = "localhost";
    private static final int port = 7789;
    private static final Login login = new Login(name);
    private static final MainFrame GUI_Frame = new MainFrame();
    private static final BattleshipGUI battleshipGUI = GUI_Frame.getBattleshipGUI();
    private static final TickTackToe tickTackToeGUI = GUI_Frame.getTickTackToe();
    private static final TelnetClient client = new TelnetClient();
    private static final Handler eventHandler = new Handler(client, battleshipGUI);
    private static final ResponseHandler responseHandler = new ResponseHandler(client, eventHandler);
    // private static final GameEngine;
    // private static final Algorithm;


    @SuppressWarnings("CallToPrintStackTrace")
    public static void main(String[] args) {
        Message message = new Message(name + " Here to win the game!1!"); // TODO: add more messages
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
            client.connect(host, port);
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
