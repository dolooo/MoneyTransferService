package com.crustlab.moneytransfer.account;

import com.crustlab.moneytransfer.exception.NotEnoughMoneyException;
import com.crustlab.moneytransfer.user.User;

import javax.persistence.*;

@Entity
@Table
public class Account {
    @Id
    private Long accountId;
    private String currency;
    private double balance;
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn
    public User getUser() {
        return user;
    }

    public Account(Long accountId, User user, String currency, double balance) {
        this.accountId = accountId;
        this.user = user;
        this.currency = currency;
        this.balance = balance;
    }

    public Account(String currency, User user) {
        this.currency = currency;
        this.balance = 0;
        this.user = user;
    }

    public Account() {
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public void deposit(double amountOfDeposit) {
        this.balance += amountOfDeposit;
    }

    public void withdraw(double amountOfWithdraw) throws NotEnoughMoneyException {
        if (amountOfWithdraw > this.balance) {
            throw new NotEnoughMoneyException();
        }
        this.balance -= amountOfWithdraw;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
