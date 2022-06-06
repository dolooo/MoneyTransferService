package com.crustlab.moneytransfer.account;

import com.crustlab.moneytransfer.user.User;

import java.util.ArrayList;
import java.util.List;

public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void initializeAccounts(User user) {
        if (user.getAccountList().isEmpty()) {
            List<Account> accountList = new ArrayList<>();
            accountList.add(new Account("PLN", user));
            accountList.add(new Account("USD", user));
            accountList.add(new Account("EUR", user));
            user.setAccountList(accountList);
        }
    }

}
