package org.bitshifters.ui.components;

import org.bitshifters.ClientController;
import org.bitshifters.telnet.Notifiers.interfaces.OnConnectionSwitch;
import org.bitshifters.telnet.TelnetClient;
import org.bitshifters.ui.MainFrame;

public class ConnectionStatus extends CustomBorderPane implements OnConnectionSwitch {
    private static final TelnetClient telnet = ClientController.telnet;
    private final Status status = new Status();

    public ConnectionStatus(MainFrame mainFrame) {
        super(mainFrame);
    }

    @Override
    public void OnConnectionSwitch(boolean isConnected) {
        status.setConnectionLabel(isConnected);
    }

    @Override
    public void OnConnectionSwitch(String connectionText) {
        status.setConnectionLabel(connectionText);
    }
}
