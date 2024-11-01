// Source code is decompiled from a .class file using FernFlower decompiler.
package src.Hanze;

import java.util.ArrayList;

public abstract class Game {
    protected Board board = null;
    private final Player playerWhite;
    private final Player playerBlack;
    private final int gameId;
    private Player winner = null;
    private boolean gameEnded = false;
    private Integer tournamentId = null;
    private String endReason;
    protected int curPlayerId = 1;
    protected LambdaStampedLock curPlayerLock = new LambdaStampedLock();
    public final Delegate<Delegate.CallbackWithParam<OnTurnData>> onTurn = new Delegate();
    public final Delegate<Delegate.CallbackWithParam<OnMoveData>> onMove = new Delegate();
    public final Delegate<Delegate.CallbackWithParam<OnMoveData>> onValidMove = new Delegate();
    public final Delegate<Delegate.CallbackWithParam<OnHitData>> onValidHit = new Delegate();
    public final Delegate<Delegate.CallbackWithParam<OnSinkData>> onSink = new Delegate();
    public final Delegate<Delegate.CallbackWithParam<OnResultData>> onResult = new Delegate();
    public final Delegate<Delegate.CallbackWithParam<OnErrorData>> onError = new Delegate();
    public final Delegate<Delegate.Callback> onOk = new Delegate();
    private final GameTimer timer;
    protected Logger logger;
    private final LambdaStampedLock stateLock = new LambdaStampedLock();
    Delegate.CallbackWithParam<Player.OnQuitData> onPlayerDisconnects = (o) -> {
        if (!this.gameEnded) {
            this.setWinState(this.getOpponent(o.player), o.comment);
        }
    };

    public Game(Player from, Player to, int gameId) {
        this.playerWhite = from;
        this.playerBlack = to;
        this.gameId = gameId;
        this.logger = LoggerFactory.getLogger("game " + this.toString());
        this.timer = new GameTimer((Integer) CompositionRoot.globalSettings.turnTimeLimit.getValue(),
                this::currentPlayerDidNotRespondInTime);
        this.onTurn.register((o) -> {
            this.timer.restart();
        });
        this.onResult.register((o) -> {
            this.timer.stop();
        });
        this.playerBlack.onDisconnect.register(this.onPlayerDisconnects);
        this.playerWhite.onDisconnect.register(this.onPlayerDisconnects);
    }

    public void start() {
        this.logger.info("started");
        this.onTurn.notifyAll((o) -> {
            o.callback(new OnTurnData(this.getCurrentPlayer()));
        });
    }

    public Player getPlayerWhite() {
        return this.playerWhite;
    }

    public Player getPlayerBlack() {
        return this.playerBlack;
    }

    public abstract String getGameType();

    public int getGameId() {
        return this.gameId;
    }

    public Board getBoard() {
        return (Board) this.stateLock.returnReadLocked(() -> {
            return this.board;
        });
    }

    public Board getBoard2() {
        return (Board) this.stateLock.returnReadLocked(() -> {
            return this.board;
        });
    }

    protected void currentPlayerDidNotRespondInTime() {
        this.setWinState(this.getOpponent(), "Turn timelimit reached");
    }

    public void move(Player player, int i) throws GameException {
        long stamp = this.stateLock.writeLock();
        this.onMove.notifyAll((o) -> {
            o.callback(new OnMoveData(i, this.getCurrentPlayer(), ""));
        });

        try {
            if (this.gameEnded) {
                this.logger.info("" + player + " tried to make move while game is finished already");
                throw new GameException.GameFinishedException();
            }

            if (!this.getCurrentPlayer().equals(player)) {
                this.logger.info("it's not " + player + "'s turn");
                throw new GameException.NotYourTurnException();
            }

            this.doMove(i);
        } finally {
            this.stateLock.unlockWrite(stamp);
        }

    }

    public abstract void doMove(int i);

    public abstract int calculateScore(Player p);

    public String getWinner() {
        return (String) this.stateLock.returnReadLocked(() -> {
            return this.winner != null ? this.winner.getName() : null;
        });
    }

    public Player getWinnerPlayer() {
        return this.winner;
    }

    public void setWinState(Player winner, String reason) {
        this.logger.info("winner: " + winner + "; " + reason);
        if (this.gameEnded) {
            throw new RuntimeException("Game has already ended mate");
        } else if (winner != null && winner != this.playerBlack && winner != this.playerWhite) {
            throw new RuntimeException("Given player is not part of game");
        } else {
            this.winner = winner;
            this.gameEnded = true;
            this.endReason = reason;
            int p1Score = this.calculateScore(this.playerWhite);
            int p2Score = this.calculateScore(this.playerBlack);
            this.onResult.notifyAll((o) -> {
                o.callback(new OnResultData(this.gameId, winner, reason, p1Score, p2Score));
            });
            this.playerWhite.onDisconnect.unregister(this.onPlayerDisconnects);
            this.playerBlack.onDisconnect.unregister(this.onPlayerDisconnects);
        }
    }

    public void forfeit(Player p) {
        this.stateLock.doWriteLocked(() -> {
            this.setWinState(this.getOpponent(p), "Player forfeited");
        });
    }

    public Player getCurrentPlayer() {
        return this.curPlayerId == 1 ? this.playerWhite : this.playerBlack;
    }

    public int getCurrentPlayerId() {
        return (Integer) this.curPlayerLock.returnReadLocked(() -> {
            return this.curPlayerId;
        });
    }

    public void setTournamentId(int tournamentId) {
        this.tournamentId = tournamentId;
    }

    public Integer getTournamentId() {
        return this.tournamentId;
    }

    public String getEndReason() {
        return (String) this.curPlayerLock.returnReadLocked(() -> {
            return this.endReason;
        });
    }

    protected Player getOpponent() {
        return (Player) this.curPlayerLock.returnReadLocked(() -> {
            return this.curPlayerId == 1 ? this.playerBlack : this.playerWhite;
        });
    }

    protected Player getOpponent(Player p) {
        return p.equals(this.playerWhite) ? this.playerBlack : this.playerWhite;
    }

    public void nextPlayer() {
        this.curPlayerLock.doWriteLocked(() -> {
            this.curPlayerId = this.curPlayerId % 2 + 1;
        });
        this.onTurn.notifyAll((o) -> {
            o.callback(new OnTurnData(this.getCurrentPlayer()));
        });
    }

    public int getPlayerId(Player p) {
        return p.equals(this.playerWhite) ? 1 : 2;
    }

    public String[] getTileImages() {
        return new String[] { "", "X.png", "O.png", "sink.png" };
    }

    public String toString() {
        int var10000 = this.gameId;
        return "[" + var10000 + "," + this.playerWhite.getName() + "," + this.playerBlack.getName() + "]";
    }

    public boolean getGameEnded() {
        return (Boolean) this.curPlayerLock.returnReadLocked(() -> {
            return this.gameEnded;
        });
    }

    public ArrayList<Integer> getScores() {
        ArrayList<Integer> list = new ArrayList();
        list.add(this.calculateScore(this.getPlayerWhite()));
        list.add(this.calculateScore(this.getPlayerBlack()));
        return list;
    }
}
