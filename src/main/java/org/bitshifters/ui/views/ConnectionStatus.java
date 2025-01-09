package org.bitshifters.ui.views;

import org.bitshifters.ClientController;
import org.bitshifters.telnet.OnConnectionSwitch;
import org.bitshifters.telnet.TelnetClient;
import org.bitshifters.ui.MainFrame;
import org.bitshifters.ui.components.CustomBorderPane;

public class ConnectionStatus extends CustomBorderPane implements OnConnectionSwitch {
    private static final TelnetClient telnet = ClientController.telnet;
    private final Status status = new Status();

    public ConnectionStatus(MainFrame mainFrame) {
        super(mainFrame);
    }

    @Override
    public void OnConnectionSwitch(boolean isConnected) {
        status.setStatusText(isConnected.toString());
    }
}
