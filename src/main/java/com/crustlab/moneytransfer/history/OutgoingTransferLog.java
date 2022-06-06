package com.crustlab.moneytransfer.history;

import com.crustlab.moneytransfer.user.User;

import java.time.LocalDate;

public class OutgoingTransferLog extends TransactionLog{
    private User recipient;

    public OutgoingTransferLog(LocalDate transactionDate, String currency, double value, String transactionType, User recipient) {
        super(transactionDate, currency, value, transactionType);
        this.recipient = recipient;
    }
}
