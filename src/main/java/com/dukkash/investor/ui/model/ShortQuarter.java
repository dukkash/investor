package com.dukkash.investor.ui.model;

import java.math.BigDecimal;

public class ShortQuarter {
    private String name;
    private String tickerSymbol;
    private BigDecimal shares;
    private BigDecimal revenue;
    private BigDecimal equity;
    private BigDecimal netProfit;
    private String notes;

    public ShortQuarter() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTickerSymbol() {
        return tickerSymbol;
    }

    public void setTickerSymbol(String tickerSymbol) {
        this.tickerSymbol = tickerSymbol;
    }

    public BigDecimal getShares() {
        return shares;
    }

    public void setShares(BigDecimal shares) {
        this.shares = shares;
    }

    public BigDecimal getRevenue() {
        return revenue;
    }

    public void setRevenue(BigDecimal revenue) {
        this.revenue = revenue;
    }

    public BigDecimal getEquity() {
        return equity;
    }

    public void setEquity(BigDecimal equity) {
        this.equity = equity;
    }

    public BigDecimal getNetProfit() {
        return netProfit;
    }

    public void setNetProfit(BigDecimal netProfit) {
        this.netProfit = netProfit;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
