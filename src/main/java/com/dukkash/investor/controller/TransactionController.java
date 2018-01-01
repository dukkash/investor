package com.dukkash.investor.controller;

import com.dukkash.investor.model.Transaction;
import com.dukkash.investor.model.TransactionType;
import com.dukkash.investor.service.AccountService;
import com.dukkash.investor.service.TransactionService;
import com.dukkash.investor.ui.model.TransactionModel;
import com.dukkash.investor.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.ValidationException;
import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    public TransactionService transactionService;

    @Autowired
    public AccountService accountService;

    @RequestMapping(value = "/getTransactionTypes", method = RequestMethod.GET)
    public List<TransactionType> getTransactionTypes() {
        return transactionService.getAllTransactionTypes();
    }


    @RequestMapping(value = "/addTransaction", method = RequestMethod.POST)
    public ResponseEntity<String> addTransaction(@RequestBody TransactionModel model) {
        try {
            validateTransaction(model);
        } catch (ValidationException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Transaction transaction = getTransaction(model);

        transactionService.addTransaction(transaction);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void validateTransaction(TransactionModel model) throws ValidationException {
        if(model.getAccount() == null) {
            throw  new ValidationException("Missing acount.");
        } else if(model.getAmount() == null) {
            throw  new ValidationException("Missing amount.");
        } else if(model.getDate() == null) {
            throw  new ValidationException("Missing date.");
        } else if(model.getDescription() == null) {
            throw  new ValidationException("Missing description.");
        } else if(model.getIsDebit() == null) {
            throw  new ValidationException("Missing is debt.");
        } else if(model.getTransactionType() == null) {
            throw  new ValidationException("Missing t type.");
        }
    }

    public Transaction getTransaction(TransactionModel model) {
        Transaction t = new Transaction();
        t.setAccount(accountService.getAccountById(model.getAccount()));
        t.setAmount(model.getAmount());
        t.setDate(DateUtil.getDate(model.getDate()));
        t.setDebit(model.getIsDebit() == 0? true: false);
        t.setDescription(model.getDescription());
        t.setTransactionType(transactionService.getTransactionTypeById(model.getTransactionType()));

        return t;
    }
}
