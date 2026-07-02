package com.example.ajo.ajo.pet;

public class AddMoneyRequest {

    private String accountno;
    private Long amount;

    public String getAccountno() {
        return accountno;
    }

    public void setAccountno(String accountno) {
        this.accountno = accountno;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
