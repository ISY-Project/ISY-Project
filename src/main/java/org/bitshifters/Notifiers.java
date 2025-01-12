package org.bitshifters;

import org.bitshifters.telnet.Notifiers.Notifier;
import org.bitshifters.ui.components.Status;
import org.bitshifters.ui.components.TurnIndicator;

public class Notifiers {
    public static final Notifier<Status, Boolean> connection = new Notifier<>();
    public static final Notifier<TurnIndicator, Boolean> turn = new Notifier<>();
}
