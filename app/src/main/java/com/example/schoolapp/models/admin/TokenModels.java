package com.example.schoolapp.models.admin;

public class TokenModels {

    private String nis, token;

    public TokenModels(){

    }

    public TokenModels(String nis, String token) {
        this.nis = nis;
        this.token = token;
    }

    public String getNis() {
        return nis;
    }

    public void setNis(String nis) {
        this.nis = nis;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
