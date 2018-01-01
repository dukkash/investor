package com.dukkash.investor.service;

import com.dukkash.investor.model.Account;
import com.dukkash.investor.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Component
@Transactional
public class AccountService {

    @Autowired
    public AccountRepository accountRepository;

    public List<Account> getAll() {
        return accountRepository.getAll();
    }

    public Account getAccountById(Integer accountId) {
        return accountRepository.getAccountById(accountId);
    }

    public void subtractAmount(Account account, BigDecimal amount) {
        account.setAmount(account.getAmount().subtract(amount));
        accountRepository.update(account);
    }
}
