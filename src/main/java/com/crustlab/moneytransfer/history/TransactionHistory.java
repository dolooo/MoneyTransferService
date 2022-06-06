package com.crustlab.moneytransfer.history;

import com.crustlab.moneytransfer.user.User;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "transaction_history")
public class TransactionHistory {
    @Id
    @SequenceGenerator(name = "transaction_history_seq", sequenceName = "transaction_history_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_history_seq")
    private Long id;
    private User user;
    private List<TransactionLog> transactionLogs;

    @ManyToOne
    @JoinColumn
    public User getUser() {
        return user;
    }

    @OneToMany
    @JoinColumn
    public List<TransactionLog> getTransactionLogs() {
        return transactionLogs;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setTransactionLogs(List<TransactionLog> transactionLogs) {
        this.transactionLogs = transactionLogs;
    }

    public void addTransactionLog(TransactionLog transactionLog, User user) {
        this.transactionLogs.add(transactionLog);
        this.user = user;
    }
}
