package Telnet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TelnetClient {

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public void connect(String server, int port) throws Exception {
        socket = new Socket(server, port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public String receiveMessage() throws Exception {
        return in.readLine();
    }

    public void showMessage() {
        String msg;
        try {
            msg = in.readLine();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        };
        System.out.println(msg);
        return;
    }

    public void close() throws Exception {
        in.close();
        out.close();
        socket.close();
    }

    public static void main(String[] args) {
        // Test example
        // TODO Remove when it works
        TelnetClient client = new TelnetClient();
        try {
            client.connect("localhost", 7789); // Replace with your server and port
            String name = "SeaCarpetBomber";
            Login login = new Login(name);
            Message message = new Message(name + " Here to win the game!1!");
            Subscribe subscribe = new Subscribe("battleship");
            client.sendMessage(login.get());
            client.sendMessage(message.get());
            client.sendMessage(subscribe.get());
            // handle waiting for the game to start
            while (client.in.readLine().contains("")) {
                client.showMessage();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}