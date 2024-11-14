package org.bitshifters.gameclient;

import java.util.Arrays;

import org.bitshifters.gameclient.arguments.Flags;
import org.bitshifters.gameclient.enums.VerboseLevel;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;

//import java.util.Random;
//import javax.swing.JOptionPane;
//import org.bitshifters.GUI.BattleshipGUI;
//import org.bitshifters.GUI.MainFrame;
//import org.bitshifters.GUI.TickTackToe;
//import org.bitshifters.Telnet.Login;
//import org.bitshifters.Telnet.Message;
//import org.bitshifters.Telnet.Move;
//import org.bitshifters.Telnet.ResponseHandler;
//import org.bitshifters.Telnet.TelnetClient;

@SuppressWarnings("FieldMayBeFinal")
public class Main {
    // command line variables
    @Parameter(names = {"-n", "--name"}, description = "Set the name of the player", order=1)
    private static String NAME = "Klas2Groep4";
    @Parameter(names = {"-h", "--host"}, description = "Set the host of the server", order=2)
    private static String HOST = "172.201.112.199"; // <- official IP // "localhost"; // "65.21.191.106";
    @Parameter(names = {"-p", "--port"}, description = "Set the port of the server", order=3, validateWith = org.bitshifters.gameclient.arguments.IntValidator.class)
    private static Integer PORT = 7789;

    // command line flags
    private static Flags FLAGS = new Flags();


    /* 
     * This is the main method of the program. It will parse the command line arguments and run the program.
     * If the help flag is set, it will print the help menu and exit the program.
     * It will run the run method once the command line arguments are parsed.
     * @param args The command line arguments
     * @return void
     */
    public static void main(String[] args){
        Main main = new Main();
        JCommander jc = JCommander.newBuilder()
                    .addObject(Main.FLAGS)
                    .addObject(main)
                    .build();
        jc.setProgramName("BitShifters.jar");
        try {
            jc.parse(args);
        } catch (ParameterException e) {
            jc.usage();
            System.err.println(e.getMessage());
            System.exit(1);
        }
        if (Flags.HELP) {
            jc.usage();
            System.exit(0);
        }
        main.run(args);
    }

    public void run(String[] args) {
        if (Flags.DEBUG) {
            System.out.println("\n=== Debug mode enabled ===\n");
            System.out.println("args = " + Arrays.toString(args));
        } if (Flags.VERBOSE == VerboseLevel.MEDIUM || Flags.VERBOSE == VerboseLevel.HIGH) {
            System.out.println("\n==== Verbose level: " + Flags.VERBOSE + " ====\n");
        } if (Flags.VERBOSE == VerboseLevel.MEDIUM || Flags.VERBOSE == VerboseLevel.HIGH || Flags.DEBUG) {
            System.out.println("Client name: " + Main.NAME);
            System.out.println("Server Host: " + Main.HOST);
            System.out.println("Server Port: " + Main.PORT);
        }
    }
//    private static final MainFrame GUI_Frame = new MainFrame();
//    private static final BattleshipGUI battleshipGUI = GUI_Frame.getBattleshipGUI();
//    private static final TickTackToe tickTackToeGUI = GUI_Frame.getTickTackToe();
//    private static final TelnetClient client = new TelnetClient();
//    private static final TTTHandler tttHandler = new TTTHandler(tickTackToeGUI.getTickTackToeGrid());
//    private static final ResponseHandler tttResponseHandler = new ResponseHandler(tttHandler);
//    // private static final GameEngine;
//    // private static final Algorithm;
//    // private static final BattleshipEngine battleshipEngine = new
//    // BattleshipEngine(8, "test", "test2");
//    private static final BattleshipHandler battleshipHandler = new BattleshipHandler();
//    private static final ResponseHandler battleshipResponseHandler = new ResponseHandler(battleshipHandler);
//    private static GameType gameType = GameType.FIRSTBOOT;
//
//    public static String[] parse_args(String[] args) throws Exception {
//        String[] out = new String[3];
//        out[0] = "";
//        out[1] = "172.201.112.199"; // <- official IP // "localhost"; // "65.21.191.106";
//        out[2] = "7789";
//        if (args.length == 0) {
//            out[0] = showGetName();
//            return out;
//        }
//        for (int i = 0; i < args.length; i++) {
//            if (args[i].contains("-n") || args[i].contains("--name")) {
//                out[0] = args[i + 1];
//                i += 1;
//            } else if (args[i].equals("-h")  || args[i].contains("--host")) {
//                out[1] = args[i + 1];
//                i += 1;
//            } else if (args[i].equals("-p")  || args[i].contains("--port")) {
//                if (Integer.parseInt(args[i + 1]) < 0 || Integer.parseInt(args[i + 1]) > 65535) {
//                    throw new Exception("Invalid port number");
//                }
//                out[2] = args[i + 1];
//                i += 1;
//            } else if (args[i].contains("-help") || args[i].contains("--help")) {
//                printHelpMenu();
//                System.exit(0);
//            } else {
//                throw new Exception("Invalid argument");
//            }
//        }
//        if (out[0].equals("")) {
//            System.out.println("Getting name from player");
//            out[0] = showGetName();
//        }
//        return out;
//    }
//
//    @SuppressWarnings("CallToPrintStackTrace")
//    public Main(String[] args) {
//        try {
//            String[] out = parse_args(args);
//            Main.NAME = out[0];
//            Main.HOST = out[1];
//            Main.PORT = Integer.parseInt(out[2]);
//            Main.login = new Login(Main.NAME);
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("One or more invalid argument(s): " + Arrays.toString(args));
//            printHelpMenu();
//            System.exit(1);
//        }
//    }
//
//    public static void printHelpMenu() {
//        System.out.println("Usage: java -jar <jarfile> [options]");
//        System.out.println("Options:");
//        System.out.println("  -n, --name <name>    Set the name of the player");
//        System.out.println("  -h, --host <host>    Set the host of the server");
//        System.out.println("  -p, --port <port>    Set the port of the server");
//        System.out.println("  -help, --help        Print this help menu");
//    }
//
//    public static GameType getGameType() {
//        return gameType;
//    }
//
//    public static void setGameType(GameType gameType) {
//        Main.gameType = gameType;
//    }
//
//    public static void toggleBattleshipGrid() {
//        if (gameType == GameType.BATTLESHIP) {
//            GUI_Frame.getBattleshipGUI().getPlayerGrid().enableGrid();
//            GUI_Frame.getBattleshipGUI().getOpponentGrid().enableGrid();
//        } else {
//            GUI_Frame.getBattleshipGUI().getPlayerGrid().disableGrid();
//            GUI_Frame.getBattleshipGUI().getOpponentGrid().disableGrid();
//        }
//    }
//
//    public static void toggleTTTGrid() {
//        if (gameType == GameType.TTT) {
//            GUI_Frame.getTickTackToe().getTickTackToeGrid().enableGrid();
//        } else {
//            GUI_Frame.getTickTackToe().getTickTackToeGrid().disableGrid();
//        }
//    }
//
//    public static TelnetClient getTelnetClient() {
//        return client;
//    }
//
//    public static MainFrame getMainFrame() {
//        return GUI_Frame;
//    }
//
//    // public static BattleshipEngine getBattleshipEngine() {
//    // return battleshipEngine;
//    // }
//
//    public static String getPlayerName() {
//        return NAME;
//    }
//
//    public static String showGetName() {
//        String name = JOptionPane.showInputDialog("What is your game name?");
//        if (name == null || name.isEmpty()) {
//            return genName(); // "Klas2Groep4"; // genName();
//        }
//        return name;
//    }
//
//    private static String genName() {
//        int leftLimit = 97; // letter 'a'
//        int rightLimit = 122; // letter 'z'
//        int targetStringLength = 10;
//        Random random = new Random();
//        StringBuilder buffer = new StringBuilder(targetStringLength);
//        for (int i = 0; i < targetStringLength; i++) {
//            int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
//            buffer.append((char) randomLimitedInt);
//        }
//        String generatedString = buffer.toString();
//        return generatedString;
//    }
//
//    public void sendMove(Move move) {
//        client.sendMessage(move.get());
//    }
//
//
// public static void main(String[] args){
//        Main main = new Main(args);
//        GUI_Frame.setTitle(NAME); // Set the title of the window
//
//        Message message = new Message(Messages.getRandomMessage().getValue());
//
//        battleshipGUI.getChatBox().getChatArea().addActionListener((java.awt.event.ActionEvent e) -> {
//            String msg = battleshipGUI.getChatBox().getChatArea().getText();
//            if (!msg.isEmpty()) {
//                client.sendMessage(new Message(msg).get());
//            }
//        });
//
//        tickTackToeGUI.getChatBox().getChatArea().addActionListener((java.awt.event.ActionEvent e) -> {
//            String msg = tickTackToeGUI.getChatBox().getChatArea().getText();
//            if (!msg.isEmpty()) {
//                client.sendMessage(new Message(msg).get());
//            }
//        });
//
//        try {
//            client.connect(HOST, PORT);
//            client.sendMessage(login.get());
//            client.sendMessage(message.get());
//            String response = client.receiveMessage();
//            long time = System.currentTimeMillis();
//            long alive_counter = time + 100000;
//            while (response.contains("")) {
//                if (System.currentTimeMillis() > alive_counter) {
//                    client.sendMessage(new Message("keep-alive").get());
//                    alive_counter = System.currentTimeMillis() + 100000;
//                }
//                client.showMessage("Received: " + response);
//                if (response.contains("Tic-tac-toe")) {
//                    // System.out.println("Tic-tac-toe");
//                    tttResponseHandler.handle(response);
//                } else if (response.contains("Battleship")) {
//                    // System.out.println("Battleship");
//                    battleshipResponseHandler.handle(response);
//                } else {
//                    // System.out.println("Unknown game");
//                    // let the tic tac toe handler handle the message
//                    tttResponseHandler.handle(response);
//                    battleshipResponseHandler.handle(response);
//                }
//                if (gameType == GameType.ENDGAME) {gameType = GameType.NONE;} // Reset the gameType after every handler ran.
//                response = client.receiveMessage();
//            }
//            JOptionPane.showMessageDialog(GUI_Frame, "Connection lost");
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            client.close();
//        }
//    }
}
