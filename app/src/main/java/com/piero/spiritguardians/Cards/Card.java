package com.piero.spiritguardians.Cards;

import android.graphics.drawable.Drawable;

public class Card {

    private int code;
    private int atk;
    private int def;
    private int cost;
    private int image;
    private String title;

    public Card(int code, int atk, int def, int cost, int image, String title) {
        this.code = code;
        this.atk = atk;
        this.def = def;
        this.cost = cost;
        this.image = image;
        this.title = title;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
