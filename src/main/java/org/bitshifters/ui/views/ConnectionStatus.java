package org.bitshifters.ui.views;

import org.bitshifters.ClientController;
import org.bitshifters.telnet.TelnetClient;
import org.bitshifters.ui.MainFrame;
import org.bitshifters.ui.components.CustomBorderPane;

public class ConnectionStatus extends CustomBorderPane {
    private static final TelnetClient telnet = ClientController.telnet;

    public ConnectionStatus(MainFrame mainFrame) {
        super(mainFrame);
    }

    
}
