package com.piero.spiritguardians.Players;

import com.piero.spiritguardians.Cards.Card;
import com.piero.spiritguardians.Cards.Deck1;

import java.util.ArrayList;

public class Player {

    private String name;
    private String deckCode = "deck 1";
    private int castle_points;
    private int fence_points;
    private int worker_points;
    private int soldier_points;
    private int magic_points;
    private int worker;
    private int soldier;
    private int magic;
    private int gold;
    private boolean isFirstPlayer;

    //optional
    private int img_profile;
    private int img_castle;

    public Player(String name, String deckCode, int castle_points, int fence_points, int worker_points, int soldier_points, int magic_points, int worker, int soldier, int magic, int gold, boolean isFirstPlayer) {
        this.name = name;
        this.deckCode = deckCode;
        this.castle_points = castle_points;
        this.fence_points = fence_points;
        this.worker_points = worker_points;
        this.soldier_points = soldier_points;
        this.magic_points = magic_points;
        this.worker = worker;
        this.soldier = soldier;
        this.magic = magic;
        this.gold = gold;
        this.isFirstPlayer = isFirstPlayer;
    }

    public Player(String name, ArrayList<Card> deck, int castle_points, int fence_points, int worker_points, int soldier_points, int magic_points, int worker, int soldier, int magic, int gold, boolean isFirstPlayer, int img_profile, int img_castle) {
        this.name = name;
        this.deckCode = deckCode;
        this.castle_points = castle_points;
        this.fence_points = fence_points;
        this.worker_points = worker_points;
        this.soldier_points = soldier_points;
        this.magic_points = magic_points;
        this.worker = worker;
        this.soldier = soldier;
        this.magic = magic;
        this.gold = gold;
        this.isFirstPlayer = isFirstPlayer;
        this.img_profile = img_profile;
        this.img_castle = img_castle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeckCode() {
        return deckCode;
    }

    public void setDeckCode(String deckCode) {
        this.deckCode = deckCode;
    }

    public int getCastle_points() {
        return castle_points;
    }

    public void setCastle_points(int castle_points) {
        this.castle_points = castle_points;
    }

    public int getFence_points() {
        return fence_points;
    }

    public void setFence_points(int fence_points) {
        this.fence_points = fence_points;
    }

    public int getWorker_points() {
        return worker_points;
    }

    public void setWorker_points(int worker_points) {
        this.worker_points = worker_points;
    }

    public int getSoldier_points() {
        return soldier_points;
    }

    public void setSoldier_points(int soldier_points) {
        this.soldier_points = soldier_points;
    }

    public int getMagic_points() {
        return magic_points;
    }

    public void setMagic_points(int magic_points) {
        this.magic_points = magic_points;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getImg_profile() {
        return img_profile;
    }

    public void setImg_profile(int img_profile) {
        this.img_profile = img_profile;
    }

    public int getImg_castle() {
        return img_castle;
    }

    public void setImg_castle(int img_castle) {
        this.img_castle = img_castle;
    }

    public int getWorker() {
        return worker;
    }

    public void setWorker(int worker) {
        this.worker = worker;
    }

    public int getSoldier() {
        return soldier;
    }

    public void setSoldier(int soldier) {
        this.soldier = soldier;
    }

    public int getMagic() {
        return magic;
    }

    public void setMagic(int magic) {
        this.magic = magic;
    }

    public boolean isFirstPlayer() {
        return isFirstPlayer;
    }

    public void setFirstPlayer(boolean firstPlayer) {
        isFirstPlayer = firstPlayer;
    }

    public ArrayList<Card> getDeck(String deckCode, Deck1 deck){

        //TODO In the future it must implement a class Deck parent from Deck1
            if(deckCode.equals("Deck 1")){
                return deck.getDeck();
            }
            else return null;
        }
}
