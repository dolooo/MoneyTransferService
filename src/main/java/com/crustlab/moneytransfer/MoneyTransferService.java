package com.crustlab.moneytransfer;

import com.crustlab.moneytransfer.account.Account;
import com.crustlab.moneytransfer.account.AccountRepository;
import com.crustlab.moneytransfer.currency.Currency;
import com.crustlab.moneytransfer.currency.CurrencyDatabase;
import com.crustlab.moneytransfer.exception.NotEnoughMoneyException;
import com.crustlab.moneytransfer.history.ExchangeLog;
import com.crustlab.moneytransfer.history.IncomingTransferLog;
import com.crustlab.moneytransfer.history.TransactionLog;
import com.crustlab.moneytransfer.history.OutgoingTransferLog;
import com.crustlab.moneytransfer.user.User;
import com.crustlab.moneytransfer.user.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class MoneyTransferService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    CurrencyDatabase currencyDatabase = CurrencyDatabase.getInstance();

    public MoneyTransferService(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    public void depositMoney(Long userId, String currency, double amount) {
        Optional<User> user = userRepository.findUserById(userId);
        if (user.isPresent()) {
            Optional<Account> accountToDeposit = accountRepository.findAccountByCurrencyAndUserId(currency, userId);
            accountToDeposit.ifPresent(account -> account.deposit(amount));
            user.get().getTransactionHistory().addTransactionLog(
                    new TransactionLog(LocalDate.now(), currency, amount, "deposit"), user.get());
        }
    }

    public void withdrawMoney(Long userId, String currency, double amount) {
        Optional<User> user = userRepository.findUserById(userId);
        if (user.isPresent()) {
            Optional<Account> accountToWithdraw = accountRepository.findAccountByCurrencyAndUserId(currency, userId);
            accountToWithdraw.ifPresent(account -> {
                try {
                    account.withdraw(amount);
                    user.get().getTransactionHistory().addTransactionLog(
                            new TransactionLog(LocalDate.now(), currency, amount, "withdraw"), user.get());
                } catch (NotEnoughMoneyException e) {
                    e.printStackTrace();
                }
            });

        }
    }

    public void transferToAnotherUser(Long userId, Long recipientId, String currency, double amount) {
        Optional<User> user = userRepository.findUserById(userId);
        Optional<User> recipient = userRepository.findUserById(recipientId);
        if (user.isPresent() && recipient.isPresent()) {
            Optional<Account> accountOfRecipient = accountRepository.findAccountByCurrencyAndUserId(currency, recipientId);
            Optional<Account> accountToWithdraw = accountRepository.findAccountByCurrencyAndUserId(currency, userId);
            accountToWithdraw.ifPresent(account -> {
                try {
                    account.withdraw(amount);
                    accountOfRecipient.ifPresent(recipientAccount -> recipientAccount.deposit(amount));

                    user.get().getTransactionHistory().addTransactionLog(
                            new OutgoingTransferLog(
                                    LocalDate.now(), currency, amount, "transferToAnotherUser", recipient.get()),
                            user.get());
                    recipient.get().getTransactionHistory().addTransactionLog(
                            new IncomingTransferLog(
                                    LocalDate.now(), currency, amount, "transferFromAnotherUser", user.get()),
                            recipient.get());
                } catch (NotEnoughMoneyException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public void exchangeToAnotherCurrency(Long userId, String currencyFrom, String currencyTo, double amount) {
        double exchange = calculateExchange(currencyFrom, currencyTo, amount);
        Optional<User> user = userRepository.findUserById(userId);
        if (user.isPresent()) {
            Optional<Account> accountFrom = accountRepository.findAccountByCurrencyAndUserId(currencyFrom, userId);
            Optional<Account> accountTo = accountRepository.findAccountByCurrencyAndUserId(currencyTo, userId);
            accountFrom.ifPresent(account -> {
                try {
                    account.withdraw(amount);
                    accountTo.ifPresent(account1 -> account1.deposit(amount * exchange));
                    user.get().getTransactionHistory().addTransactionLog(
                            new ExchangeLog(LocalDate.now(), currencyFrom, currencyTo, amount,
                                    amount * exchange, "exchangeToAnotherCurrency"), user.get());
                } catch (NotEnoughMoneyException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public double calculateExchange(String from, String to, double amount) {
        BigDecimal bigDecimalAmount = new BigDecimal(amount);
        Currency fromCurrency = currencyDatabase.getCurrencyByCode(from);
        double fromConverter = Double.parseDouble(fromCurrency.getConverter());
        BigDecimal fromExchange = new BigDecimal(fromCurrency.getExchange());

        Currency toCurrency = currencyDatabase.getCurrencyByCode(to);
        double toConverter = Double.parseDouble(toCurrency.getConverter());
        BigDecimal toExchange = new BigDecimal(toCurrency.getExchange());

        BigDecimal converter = new BigDecimal(toConverter / fromConverter);
        bigDecimalAmount = bigDecimalAmount.multiply(converter).multiply(fromExchange).divide(toExchange, 2, RoundingMode.DOWN);
        return bigDecimalAmount.doubleValue();
    }
}
