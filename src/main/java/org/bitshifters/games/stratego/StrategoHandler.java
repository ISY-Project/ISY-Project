package org.bitshifters.games.stratego;


import org.bitshifters.gameclient.games.GameTypes;
import org.bitshifters.gameclient.telnet.EventHandler;

public class StrategoHandler extends EventHandler {
    StrategoHandler() {
        super(GameTypes.Stratego);
    }

    @Override
    public void onChallenge(String playerName, int game, int gameNumber) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onChallenge'");
    }

    @Override
    public void onCancel(int gameNumber) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onCancel'");
    }

    @Override
    public void onMatch() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onMatch'");
    }

    @Override
    public void onYourTurn(String message) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onYourTurn'");
    }

    @Override
    public void onMove(String[] data) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onMove'");
    }

    @Override
    public void onWin() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onWin'");
    }

    @Override
    public void onLose() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onLose'");
    }

    @Override
    public void onDraw() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onDraw'");
    }

    @Override
    public void onHelp(String message) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onHelp'");
    }

    @Override
    public void onError(String message) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onError'");
    }

    @Override
    public void onMessage(String message) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onMessage'");
    }
}
