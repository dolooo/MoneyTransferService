package com.crustlab.moneytransfer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/moneytransfer")
public class MoneyTransferController {
    private final MoneyTransferService moneyTransferService;

    @Autowired
    public MoneyTransferController(MoneyTransferService moneyTransferService) {
        this.moneyTransferService = moneyTransferService;
    }

    @PutMapping(path ="{userId}")
    public void depositMoney(@PathVariable("userId") Long userId,
                             @RequestParam double amountOfMoney,
                             @RequestParam String currency) {
        moneyTransferService.depositMoney(userId, currency, amountOfMoney);
    }

    //TODO withdraw, transferToRecipient, exchangeToAnotherCurrency
}
