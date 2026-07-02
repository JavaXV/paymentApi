package com.example.ajo.ajo.pet;

public class LoginResponse {

    private String status;
    private String message;
    private Accounts account;

    public LoginResponse(String status, String message, Accounts account) {
        this.status = status;
        this.message = message;
        this.account = account;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Accounts getAccount() {
        return account;
    }
}
