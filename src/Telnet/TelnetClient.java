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

    public void showMessage(String message) {
        System.out.println(message);
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
            name += (int) (Math.random() * 100);
            Login login = new Login(name);
            Message message = new Message(name + " Here to win the game!1!");
            Subscribe subscribe = new Subscribe("battleship");
            EventHandler eventHandler = new EventHandler();
            ResponseHandler responseHandler = new ResponseHandler(client, eventHandler);
            client.sendMessage(login.get());
            client.sendMessage(message.get());
            client.sendMessage(subscribe.get());
            // handle waiting for the game to start
            String response = client.in.readLine();
            while (response.contains("")) {
                client.showMessage("Received: " + response);
                responseHandler.handle(response);
                response = client.in.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}