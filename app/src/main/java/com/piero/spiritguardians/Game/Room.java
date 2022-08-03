package com.piero.spiritguardians.Game;

public class Room {

    private String roomName;
    private String roomCode;
    private String password;
    private int userCode;
    private int playerNumber;
    private String matchCode;

    public Room(String roomName, String roomCode, String password, int userCode, int playerNumber, String matchCode) {
        this.roomName = roomName;
        this.roomCode = roomCode;
        this.password = password;
        this.userCode = userCode;
        this.playerNumber = playerNumber;
        this.matchCode = matchCode;
    }

    public Room() {
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserCode() {
        return userCode;
    }

    public void setUserCode(int userCode) {
        this.userCode = userCode;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public String getMatchCode() {
        return matchCode;
    }

    public void setMatchCode(String matchCode) {
        this.matchCode = matchCode;
    }
}
