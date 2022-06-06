package com.crustlab.moneytransfer.history;

import java.time.LocalDate;

public class ExchangeLog extends TransactionLog{
    private String newCurrency;
    private double valueAfterExchange;

    public ExchangeLog(LocalDate transactionDate, String currency, String newCurrency, double value,
                       double valueAfterExchange, String transactionType) {
        super(transactionDate, currency, value, transactionType);
        this.newCurrency = newCurrency;
        this.valueAfterExchange = valueAfterExchange;
    }
}
