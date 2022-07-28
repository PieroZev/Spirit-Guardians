package com.piero.spiritguardians.Players;

public class User {

    private int userCode;
    private String username;
    private String password;
    private String email;
    private int img_profile;
    private int img_castle;

    public User(int userCode, String username, String password, String email, int img_profile, int img_castle) {
        this.userCode = userCode;
        this.username = username;
        this.password = password;
        this.email = email;
        this.img_profile = img_profile;
        this.img_castle = img_castle;
    }

    public User(int userCode, String username, String email) {
        this.userCode = userCode;
        this.username = username;
        this.email = email;
    }

    public int getUserCode() {
        return userCode;
    }

    public void setUserCode(int userCode) {
        this.userCode = userCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}
