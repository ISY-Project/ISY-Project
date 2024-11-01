package src.Hanze;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings({"rawtypes", "unchecked"})
public class HanzeBattleship {
    protected Board board1;
    protected Board board2;
    private List<Boat> boatsPlayer1;
    private List<Boat> boatsPlayer2;
    private List<Integer> placedPlayerOne = new ArrayList();
    private List<Integer> placedPlayerTwo = new ArrayList();
    private List<Integer> p1hitList = new ArrayList();
    private List<Integer> p2hitList = new ArrayList();
    private boolean isPlaceRound = true;

    public boolean getIsPlaceRound() {
        return this.isPlaceRound;
    }

    public HanzeBattleship(Player from, Player to, int gameId) {
        super(from, to, gameId);
        this.board1 = new Board(8, 8, 1);
        this.board2 = new Board(8, 8, 2);
        this.boatsPlayer1 = new ArrayList();
        this.boatsPlayer2 = new ArrayList();
    }

    public HanzeBattleship(Player from, Player to, Board b1, Board b2, int gameId) {
        super(from, to, gameId);
        this.board1 = b1;
        this.board2 = b2;
        this.boatsPlayer1 = new ArrayList();
        this.boatsPlayer2 = new ArrayList();
    }

    public boolean isValidPlace(int[] place) {
        boolean vertical = this.isBoatVertical(place);
        int beginIndex = Math.min(place[0], place[1]);
        int endIndex = Math.max(place[0], place[1]);
        if (endIndex % 8 < beginIndex % 8) {
            return false;
        } else {
            int boatLength;
            if (vertical) {
                boatLength = (endIndex - beginIndex) / 8 + 1;
            } else {
                boatLength = endIndex - beginIndex + 1;
            }

            if (boatLength <= 6 && boatLength != 5 && boatLength >= 2) {
                List<Boat> placedBoats = this.getCurrentPlayerId() == 1 ? this.boatsPlayer1 : this.boatsPlayer2;
                Iterator var7 = placedBoats.iterator();

                Boat b;
                do {
                    if (!var7.hasNext()) {
                        int y = beginIndex;

                        while (y <= endIndex) {
                            List<Integer> placed = this.getCurrentPlayerId() == 1 ? this.placedPlayerOne : this.placedPlayerTwo;
                            if (placed.contains(y)) {
                                return false;
                            }

                            if (vertical) {
                                y += 8;
                            } else {
                                ++y;
                            }
                        }

                        return true;
                    }

                    b = (Boat) var7.next();
                } while (b.getLength() != boatLength);

                return false;
            } else {
                return false;
            }
        }
    }

    public void place(Player player, int[] place) throws GameException {
        if (!this.getCurrentPlayer().equals(player)) {
            this.logger.info("it's not " + player + "'s turn");
            throw new GameException.NotYourTurnException();
        } else {
            int beginIndex = Math.min(place[0], place[1]);
            int endIndex = Math.max(place[0], place[1]);
            boolean vertical = this.isBoatVertical(place);
            List<Integer> boatIndexes = new ArrayList();
            List<Integer> placed = this.getCurrentPlayerId() == 1 ? this.placedPlayerOne : this.placedPlayerTwo;
            int i = beginIndex;

            while (i <= endIndex) {
                boatIndexes.add(i);
                placed.add(i);
                if (i % 8 - 1 >= 0) {
                    placed.add(i - 1);
                }

                if (i % 8 + 1 < 8) {
                    placed.add(i + 1);
                }

                if (i + 8 <= 63) {
                    placed.add(i + 8);
                }

                if (i - 8 >= 0) {
                    placed.add(i - 8);
                }

                if (vertical) {
                    i += 8;
                } else {
                    ++i;
                }
            }

            Boat boat = new Boat(boatIndexes.size(), boatIndexes);
            if (this.getCurrentPlayerId() == 1) {
                this.boatsPlayer1.add(boat);
                if (this.boatsPlayer1.size() == 4) {
                    if (this.boatsPlayer2.size() == 4) {
                        System.out.println("Alle boten zijn geplaatst");
                        this.isPlaceRound = false;
                        this.nextPlayer();
                        return;
                    }

                    this.nextPlayer();
                }
            } else {
                this.boatsPlayer2.add(boat);
                if (this.boatsPlayer2.size() == 4) {
                    if (this.boatsPlayer1.size() == 4) {
                        System.out.println("Alle boten zijn geplaatst");
                        this.isPlaceRound = false;
                        this.nextPlayer();
                        return;
                    }

                    this.nextPlayer();
                }
            }

        }
    }

    public String getGameType() {
        return "Battleship";
    }

    public void doMove(int i) {
        List<Boat> boatList = this.getCurrentPlayerId() == 1 ? this.boatsPlayer2 : this.boatsPlayer1;
        List<Integer> hitlist = this.getCurrentPlayerId() == 1 ? this.p1hitList : this.p2hitList;
        if (hitlist.contains(i)) {
            this.setWinState(this.getOpponent(), "Player shot a place twice");
        } else {
            hitlist.add(i);
            if (i >= 0 && i <= 63) {
                Iterator var4 = boatList.iterator();

                while (var4.hasNext()) {
                    Boat b = (Boat) var4.next();
                    if (b.getPlaced().contains(i)) {
                        b.hit(i);
                        this.getCurrentBoard().set(i, 1);
                        if (b.hasSunk()) {
                            Iterator var6 = b.savePlaced.iterator();

                            while (var6.hasNext()) {
                                int index = (Integer) var6.next();
                                this.getCurrentBoard().set(index, 3);
                            }

                            boatList.remove(b);
                            this.onSink.notifyAll((o) -> {
                                o.callback(new Game.OnSinkData(i, this.getCurrentPlayer(), "GEZONKEN", b.length));
                            });
                            if (boatList.isEmpty()) {
                                this.setWinState(this.getCurrentPlayer(), "Alle boten neergeschoten");
                                return;
                            }
                        } else {
                            this.onValidHit.notifyAll((o) -> {
                                o.callback(new Game.OnHitData(i, this.getCurrentPlayer(), "BOEM"));
                            });
                        }
                        break;
                    }

                    if (b == boatList.get(boatList.size() - 1)) {
                        this.getCurrentBoard().set(i, 2);
                        this.onValidHit.notifyAll((o) -> {
                            o.callback(new Game.OnHitData(i, this.getCurrentPlayer(), "PLONS"));
                        });
                    }
                }

                this.nextPlayer();
            } else {
                this.setWinState(this.getOpponent(), "Player made illegal move");
            }
        }
    }

    public int calculateScore(Player p) {
        Player winner = this.getWinnerPlayer();
        if (winner == null) {
            return 0;
        } else {
            return winner.equals(p) ? 1 : -1;
        }
    }

    private Board getCurrentBoard() {
        return this.getCurrentPlayerId() == 1 ? this.board1 : this.board2;
    }

    private boolean isBoatVertical(int[] index) {
        return (index[1] - index[0]) % 8 == 0;
    }

    public Board getBoard() {
        return this.getCurrentBoard();
    }

    public Board getBoard2() {
        return this.board2;
    }

    public void start() {
        this.logger.info("started");
        this.onTurn.notifyAll((o) -> {
            o.callback(new Game.OnTurnData(this.getCurrentPlayer()));
        });
    }

    public void move(Player player, int i) throws GameException {
        if (this.isPlaceRound) {
            throw new GameException("expected place command");
        } else {
            super.move(player, i);
        }
    }
}
