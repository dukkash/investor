package com.dukkash.investor.controller;

import com.dukkash.investor.model.Account;
import com.dukkash.investor.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    public AccountService accountService;

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public List<Account> getAll(){
        return accountService.getAll();
    }

}
