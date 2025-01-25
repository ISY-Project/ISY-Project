package org.bitshifters.telnet;

import java.util.Map;

public class MatchData {
    public String playerToMove;
    public String gameType;
    public String opponentName;

    MatchData(String PlayerToMove, String GameType, String Opponent) {
        this.playerToMove = PlayerToMove;
        this.gameType = GameType;
        this.opponentName = Opponent;
    }

    public MatchData (Map<String, String> data) {
        this(data.get("PLAYERTOMOVE"), data.get("GAMETYPE"), data.get("OPPONENT"));
    }
}
