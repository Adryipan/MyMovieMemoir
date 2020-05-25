package com.example.mymoviemenoir.entity;

public class Credentials {

    private PersonWithId userId;
    private String username;
    private String password;
    private String signUpDate;

    public Credentials(PersonWithId userId, String username, String password, String signUpDate) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.signUpDate = signUpDate;
    }

    public PersonWithId getUserId() {
        return userId;
    }

    public void setUserId(PersonWithId userId) {
        this.userId = userId;
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

    public String getSignUpDate() {
        return signUpDate;
    }

    public void setSignUpDate(String signUpDate) {
        this.signUpDate = signUpDate;
    }
}
