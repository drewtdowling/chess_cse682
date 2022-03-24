package com.cse682.chess_cse682.orm;

public class User implements DatabaseItem {
    private String username;
    private String password;
    private int uid;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() {
        this.username = "Guest";
        this.password = "";
        this.uid = 0;
    }

    public String getUsername() {
        return username;
    }
}
