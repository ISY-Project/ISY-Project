package src.Telnet;

import java.io.BufferedReader;
import java.io.IOException;
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
        if (this.out == null) {
            System.out.println("Connection not established");
            return;
        }
        System.out.println("Sent: " + message);
        out.println(message);
    }

    public String receiveMessage() throws Exception {
        return in.readLine();
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public void showMessage() {
        String msg;
        try {
            msg = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        System.out.println(msg);
    }

    public void close() throws Exception {
        in.close();
        out.close();
        socket.close();
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public static void main(String[] args) {
        TelnetClient client = new TelnetClient();
        try {
            client.connect("localhost", 7789); // Replace with your server and port
            String name = "SeaCarpetBomber";
            name += (int) (Math.random() * 10);
            Login login = new Login(name);
            Message message = new Message(name + " Here to win the game!1!");
            // Subscribe subscribe = new Subscribe("battleship");
            EventHandler eventHandler = new EventHandler(client);
            ResponseHandler responseHandler = new ResponseHandler(client, eventHandler);
            client.sendMessage(login.get());
            client.sendMessage(message.get());
            // client.sendMessage(subscribe.get());
            // handle waiting for the game to start
            String response = client.in.readLine();
            while (response.contains("")) {
                client.showMessage("Received: " + response);
                responseHandler.handle(response);
                response = client.in.readLine();
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