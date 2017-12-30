package com.dukkash.investor.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity(name = "TRADE")
public class Trade {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id", referencedColumnName = "ID")
    private Company company;

    @Column
    private BigDecimal units;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "transaction_id", referencedColumnName = "ID")
    private Transaction transaction;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "exchange_id", referencedColumnName = "ID")
    private Exchange exchange;

    @Column(name = "is_buy", columnDefinition="BIT")
    private Boolean isBuy;

    @Column(name = "trade_date")
    private Date tradeDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "commission_id", referencedColumnName = "ID")
    private Transaction commission;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public BigDecimal getUnits() {
        return units;
    }

    public void setUnits(BigDecimal units) {
        this.units = units;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Exchange getExchange() {
        return exchange;
    }

    public void setExchange(Exchange exchange) {
        this.exchange = exchange;
    }

    public Boolean getBuy() {
        return isBuy;
    }

    public void setBuy(Boolean buy) {
        isBuy = buy;
    }

    public Date getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(Date tradeDate) {
        this.tradeDate = tradeDate;
    }

    public Transaction getCommission() {
        return commission;
    }

    public void setCommission(Transaction commission) {
        this.commission = commission;
    }
}
