package org.bitshifters.gameclient.telnet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.bitshifters.gameclient.telnet.Commands.SendableCommand;


public class TelnetClient {

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public void connect(String server, int port) throws Exception {
        socket = new Socket(server, port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void sendMessage(SendableCommand message) {
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

    public void close() throws Exception {
        in.close();
        out.close();
        socket.close();
    }
}