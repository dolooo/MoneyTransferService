package com.crustlab.moneytransfer.exception;

public class NotEnoughMoneyException extends Exception {

    public NotEnoughMoneyException() {
        System.out.println("You don't have enough money in this account!");
    }

    public NotEnoughMoneyException(String message) {
        super(message);
    }
}
