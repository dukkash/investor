package com.dukkash.investor.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity(name = "TRANSACTION")
public class Transaction {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Integer id;

    @Column
    private BigDecimal amount;

    @Column
    private Date date;

    @Column
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "source_account", referencedColumnName = "ID")
    private Account sourceAccount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "destination_account", referencedColumnName = "ID")
    private Account destinationAccount;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Account getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(Account sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public Account getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(Account destinationAccount) {
        this.destinationAccount = destinationAccount;
    }
}
