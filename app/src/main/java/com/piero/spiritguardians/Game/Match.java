package com.piero.spiritguardians.Game;

import com.piero.spiritguardians.Players.Player;

public class Match {

    private String matchCode;
    private Player player1;
    private Player player2;
    private int turn;

    public Match(String matchCode, Player player1, Player player2, int turn) {
        this.matchCode = matchCode;
        this.player1 = player1;
        this.player2 = player2;
        this.turn = turn;
    }

    public String getMatchCode() {
        return matchCode;
    }

    public void setMatchCode(String matchCode) {
        this.matchCode = matchCode;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }
}
