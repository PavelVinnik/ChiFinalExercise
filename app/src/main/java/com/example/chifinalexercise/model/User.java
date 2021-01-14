package com.example.chifinalexercise.model;

public class User {

    private String mUsername;
    private String mEmail;

    public User(String username, String email) {
        this.mUsername = username;
        this.mEmail = email;
    }

    public String getUsername() {
        return mUsername;
    }

    public String getEmail() {
        return mEmail;
    }
}
