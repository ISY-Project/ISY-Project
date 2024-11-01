package src.Hanze;

public class Player {
    private String name;
    private int id;
    private Game game = null;
    private ClientOutputWriter output;
    private boolean disconnected = false;
    public final Delegate<Delegate.CallbackWithParam<OnQuitData>> onDisconnect = new Delegate();
    public final Delegate<Delegate.Callback> onReconnect = new Delegate();

    public Game getGame() {
        return this.game;
    }

    public synchronized void setGame(Game newGame) {
        this.game = newGame;
        this.id = this.game.getCurrentPlayer().equals(this) ? this.game.getCurrentPlayerId()
                : this.game.getCurrentPlayerId() % 2 + 1;
        this.game.onTurn.register(this::notifyTurn);
        this.game.onValidMove.register(this::notifyValidMove);
        this.game.onResult.register(this::notifyResult);
        this.game.onValidHit.register(this::notifyHit);
        this.game.onSink.register(this::notifySink);
        if (this.output != null) {
            ClientOutputWriter.KeyValueArray data = new ClientOutputWriter.KeyValueArray();
            data.add("PLAYERTOMOVE", this.game.getPlayerWhite().name);
            data.add("GAMETYPE", this.game.getGameType());
            data.add("OPPONENT", this.game.getOpponent(this).getName());
            this.output.print("GAME MATCH", data);
        }
    }

    public Player() {
    }

    public Player(String name) {
        this.name = name;
    }

    public synchronized void setName(String name) {
        this.name = name;
    }

    public synchronized String getName() {
        return this.name;
    }

    public synchronized int getId() {
        return this.id;
    }

    public synchronized void challenge(Player from, GameFactory.GameType gameType, int id) {
        if (this.output != null) {
            ClientOutputWriter.KeyValueArray data = new ClientOutputWriter.KeyValueArray();
            data.add("CHALLENGER", from.getName());
            data.add("CHALLENGENUMBER", String.valueOf(id));
            data.add("GAMETYPE", gameType.toString());
            this.output.print("GAME CHALLENGE", data);
        }
    }

    public synchronized void challengeCancelled(int id) {
        if (this.output != null) {
            ClientOutputWriter.KeyValueArray data = new ClientOutputWriter.KeyValueArray();
            data.add("CHALLENGENUMBER", String.valueOf(id));
            this.output.print("GAME CHALLENGE CANCELLED", data);
        }
    }

    public void notifyResult(Game.OnResultData o) {
        this.game = null;
        if (this.output != null) {
            String result = "DRAW";
            if (o.winner != null) {
                if (o.winner.equals(this)) {
                    result = "WIN";
                } else {
                    result = "LOSS";
                }
            }

            ClientOutputWriter.KeyValueArray data2 = new ClientOutputWriter.KeyValueArray();
            data2.add("PLAYERONESCORE", "" + o.player1Score);
            data2.add("PLAYERTWOSCORE", "" + o.player2Score);
            data2.add("COMMENT", o.comment);
            this.output.print("GAME " + result, data2);
        }
    }

    public void notifyValidMove(Game.OnMoveData o) {
        if (this.output != null) {
            ClientOutputWriter.KeyValueArray data2 = new ClientOutputWriter.KeyValueArray();
            data2.add("PLAYER", this.game.getCurrentPlayer().getName());
            data2.add("MOVE", Integer.toString(o.move));
            data2.add("DETAILS", "");
            this.output.print("GAME MOVE", data2);
        }
    }

    public void notifyHit(Game.OnHitData o) {
        if (this.output != null) {
            ClientOutputWriter.KeyValueArray data2 = new ClientOutputWriter.KeyValueArray();
            data2.add("PLAYER", this.game.getCurrentPlayer().getName());
            data2.add("MOVE", Integer.toString(o.move));
            data2.add("RESULT", o.result);
            this.output.print("GAME MOVE", data2);
        }
    }

    public void notifySink(Game.OnSinkData o) {
        if (this.output != null) {
            ClientOutputWriter.KeyValueArray data2 = new ClientOutputWriter.KeyValueArray();
            data2.add("PLAYER", this.game.getCurrentPlayer().getName());
            data2.add("MOVE", Integer.toString(o.move));
            data2.add("RESULT", o.result);
            if (o.length != 0) {
                data2.add("LENGTH", Integer.toString(o.length));
            }

            this.output.print("GAME MOVE", data2);
        }
    }

    public void notifyTurn(Game.OnTurnData o) {
        if (this.output != null) {
            if (o.currentPlayer.equals(this)) {
                ClientOutputWriter.KeyValueArray data2 = new ClientOutputWriter.KeyValueArray();
                data2.add("TURNMESSAGE", "");
                this.output.print("GAME YOURTURN", data2);
            }

        }
    }

    public void setOutputWriter(ClientOutputWriter output) {
        this.output = output;
    }

    public void disconnect(String reason) {
        this.disconnected = true;
        this.onDisconnect.notifyAll((o) -> {
            o.callback(new OnQuitData(this, reason));
        });
    }

    public void reconnect() {
        this.disconnected = false;
        this.onReconnect.notifyAll((o) -> {
            o.callback();
        });
    }

    public boolean isDisconnected() {
        return this.disconnected;
    }

    public boolean isPlaying() {
        return this.game != null;
    }

    public String toString() {
        return this == null ? "null" : this.getName();
    }

    public int compareTo(Player compare) {
        return this.getName().compareTo(compare.getName());
    }
}
