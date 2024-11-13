package src;

import java.util.Arrays;
import src.Telnet.Login;

public class test {
    private static String NAME;
    private static String HOST;  
    private static int PORT;
    private static Login login;

    
    public static String[] parse_args(String[] args) throws Exception {
        String[] out = new String[3];
        out[0] = "test";
        out[1] = "172.201.112.199"; // <- official IP // "localhost"; // "65.21.191.106";
        out[2] = "7789";
        if (args.length == 0) {
            return out;
        }
        for (int i = 0; i < args.length; i++) {
            if (args[i].contains("-n") || args[i].contains("--name")) {
                out[0] = args[i + 1];
                i += 1;
            } else if (args[i].equals("-h")  || args[i].contains("--host")) {
                out[1] = args[i + 1];
                i += 1;
            } else if (args[i].equals("-p")  || args[i].contains("--port")) {
                out[2] = args[i + 1];
                i += 1;
            } else if (args[i].contains("-help") || args[i].contains("--help")) {
                printHelpMenu();
                System.exit(0);
            } else {
                throw new Exception("Invalid argument");
                
            }
        }
        return out;
    }

    public static void printHelpMenu() {
        System.out.println("Usage: java -jar <jarfile> [options]");
        System.out.println("Options:");
        System.out.println("  -n, --name <name>    Set the name of the player");
        System.out.println("  -h, --host <host>    Set the host of the server");
        System.out.println("  -p, --port <port>    Set the port of the server");
        System.out.println("  -help, --help        Print this help menu");
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public test(String[] args) {
        try {
            String[] out = parse_args(args);
            test.NAME = out[0];
            test.HOST = out[1];
            test.PORT = Integer.parseInt(out[2]);
            test.login = new Login(test.NAME);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("One or more invalid argument(s): " + Arrays.toString(args));
            printHelpMenu();
            System.exit(1);
        }
    }

    @SuppressWarnings("unused")
    public static void main(String[] args) {
        test test = new test(args);
        System.out.println(HOST);
        System.out.println(PORT);
        System.out.println(NAME);
        System.out.println(login);
    }
}
