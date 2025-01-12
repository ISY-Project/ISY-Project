package org.bitshifters;

import org.bitshifters.telnet.Notifiers.Notifier;
import org.bitshifters.ui.components.Status;

public class Notifiers {
    public static final Notifier<Status, Boolean> connectionNotifier = new Notifier<>();
}
