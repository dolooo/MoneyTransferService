package com.crustlab.moneytransfer.history;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "transaction_log")
public class TransactionLog {
    @Id
    @SequenceGenerator(name = "transaction_log_seq", sequenceName = "transaction_log_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_log_seq")
    private Long id;
    private TransactionHistory transactionHistory;
    private LocalDate transactionDate;
    private String transactionType;
    private String currency;
    private double value;

    @ManyToOne
    @JoinColumn
    public TransactionHistory getTransactionHistory() {
        return transactionHistory;
    }

    public void setTransactionHistory(TransactionHistory transactionHistory) {
        this.transactionHistory = transactionHistory;
    }

    public TransactionLog(LocalDate transactionDate, String currency, double value, String transactionType) {
        this.transactionDate = transactionDate;
        this.currency = currency;
        this.value = value;
        this.transactionType = transactionType;
    }

    public TransactionLog() {
    }
}
