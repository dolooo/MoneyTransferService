package com.crustlab.moneytransfer.user;

import com.crustlab.moneytransfer.account.Account;
import com.crustlab.moneytransfer.history.TransactionHistory;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class User {
    @Id
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    private Long userId;
    private List<Account> accountList;
    private TransactionHistory transactionHistory;

    @OneToMany(cascade={CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(nullable=false)
    public List<Account> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
    }

    @OneToOne
    @JoinColumn
    public TransactionHistory getTransactionHistory() {
        return transactionHistory;
    }

    public Long getUserId() {
        return userId;
    }

    public void setTransactionHistory(TransactionHistory transactionHistory) {
        this.transactionHistory = transactionHistory;
    }

}
