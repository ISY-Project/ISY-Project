package org.bitshifters.telnet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.bitshifters.logging.BSLogger;
import org.bitshifters.telnet.Commands.SendableCommand;

/**
 * Client for connecting to a Telnet server.
 */
public class TelnetClient {
    private static final BSLogger logger = new BSLogger(TelnetClient.class);
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String server;
    private int port;

    /**
     * Constructs a TelnetClient.
     * @param server The server address.
     * @param port The server port.
     */
    public TelnetClient(final String server, final int port) {
        this.server = server;
        this.port = port;
    }

    /**
     * Connects to the server.
     * @throws Exception If an error occurs while connecting.
     */
    public void connect() throws Exception {
        logger.info("Connecting to server: " + this.server + ":" + this.port);
        socket = new Socket(this.server, this.port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    /**
     * Sends a message to the server.
     * @param message The message to send.
     * @throws IllegalStateException If the connection is not established.
     */
    public void send(final SendableCommand message) throws IllegalStateException {
        logger.debug("Sending message: " + message.get());
        if (this.out == null) {
            throw new IllegalStateException("Connection not established");
        }
        System.out.println("Sent: " + message);
        out.println(message);
    }

    /**
     * Receives a message from the server.
     * @return The latest received message.
     * @throws Exception If an error occurs while receiving.
     */
    public String receive() throws Exception {
        logger.debug("Receiving message");
        return in.readLine();
    }

    /**
     * Closes the connection to the server.
     * @throws Exception If an error occurs while closing the connection.
     */
    public void close() throws Exception {
        logger.info("Closing connection with server: " + this.server + ":" + this.port);
        in.close();
        out.close();
        socket.close();
    }
}