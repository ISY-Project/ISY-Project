package org.bitshifters.telnet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.bitshifters.telnet.Commands.SendableCommand;


public class TelnetClient {

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String server;
    private int port;

    public TelnetClient(final String server, final int port) {
        this.server = server;
        this.port = port;
    }

    public void connect() throws Exception {
        socket = new Socket(this.server, this.port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void send(final SendableCommand message) throws IllegalStateException {
        logger.debug("Sending message: " + message.get());
        if (this.out == null) {
            throw new IllegalStateException("Connection not established");
        }
        System.out.println("Sent: " + message);
        out.println(message);
    }

    public String receiveMessage() throws Exception {
        return in.readLine();
    }

    public void showMessage(final String message) {
        System.out.println(message);
    }

    public void close() throws Exception {
        in.close();
        out.close();
        socket.close();
    }
}