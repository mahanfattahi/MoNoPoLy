package server;

import model.GameState;

public class TurnManager {
    private int currentPlayerIndex;
    private int totalPlayers;

    public TurnManager(int totalPlayers) {
        this.totalPlayers = totalPlayers;
        this.currentPlayerIndex = 0;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public void nextTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % totalPlayers;
    }

    public boolean isTurn(int playerIndex) {
        return currentPlayerIndex == playerIndex;
    }
}