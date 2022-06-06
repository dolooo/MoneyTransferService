package com.crustlab.moneytransfer.account;

import com.crustlab.moneytransfer.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{
        @Query("select a from Account a where a.currency =?1")
        Optional<Account> findAccountByCurrency(String currencyCode);

        @Query("select a from Account a where a.currency =?1 and a.user.userId =?1")
        Optional<Account> findAccountByCurrencyAndUserId(String currencyCode, Long userId);

        @Query("select a from Account a where a.user =?1")
        boolean checkIfUserHaveAccounts(User user);
}
