package com.dukkash.investor.ui.model;

import com.dukkash.investor.model.Transaction;

import java.math.BigDecimal;

public class TransactionModel {

    private Integer id;

    private BigDecimal amount;

    private String date;

    private String description;

    private Integer account;

    private Integer isDebit;

    private Integer transactionType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getAccount() {
        return account;
    }

    public void setAccount(Integer account) {
        this.account = account;
    }

    public Integer getIsDebit() {
        return isDebit;
    }

    public void setIsDebit(Integer isDebit) {
        this.isDebit = isDebit;
    }

    public Integer getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(Integer transactionType) {
        this.transactionType = transactionType;
    }
}
