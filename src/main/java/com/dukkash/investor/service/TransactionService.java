package com.dukkash.investor.service;

import com.dukkash.investor.model.Transaction;
import com.dukkash.investor.model.TransactionType;
import com.dukkash.investor.repository.TransactionRepository;
import com.dukkash.investor.ui.model.TransactionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
@Transactional
public class TransactionService {

    @Autowired
    public TransactionRepository transactionRepository;

    @Autowired
    public AccountService accountService;

    public List<TransactionType> getAllTransactionTypes() {
        return transactionRepository.getAllTransactionTypes();
    }

    public TransactionType getTransactionTypeById(Integer id) {
        return transactionRepository.getTransactionTypeById(id);
    }

    public void addTransaction(Transaction transaction) {
        transactionRepository.addTransaction(transaction);
        accountService.subtractAmount(transaction.getAccount(), transaction.getAmount());
    }

    public List<Transaction> getAll() {
        return transactionRepository.getAll();
    }
}
