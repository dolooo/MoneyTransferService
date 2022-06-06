package com.crustlab.moneytransfer.history;

import com.crustlab.moneytransfer.user.User;

import java.time.LocalDate;

public class IncomingTransferLog extends TransactionLog{
    private User sender;

    public IncomingTransferLog(LocalDate transactionDate, String currency, double value, String transactionType, User sender) {
        super(transactionDate, currency, value, transactionType);
        this.sender = sender;
    }
}
