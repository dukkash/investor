package com.dukkash.investor.ui.model;

import com.dukkash.investor.model.CompanyNote;

import java.math.BigDecimal;
import java.util.List;

public class EstimateModel {
    private String tickerSymbol;
    private String companyName;
    private BigDecimal currentPrice;
    private String price; // current price derived from the Company
    private String estimatedDate;
    private String estimatedBy;
    private String description;
    private BigDecimal targetPrice;
    private String targetDate;
    private List<CompanyNote> notes;

    public EstimateModel(){}

    public List<CompanyNote> getNotes() {
        return notes;
    }

    public void setNotes(List<CompanyNote> notes) {
        this.notes = notes;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTickerSymbol() {
        return tickerSymbol;
    }

    public void setTickerSymbol(String tickerSymbol) {
        this.tickerSymbol = tickerSymbol;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getEstimatedDate() {
        return estimatedDate;
    }

    public void setEstimatedDate(String estimatedDate) {
        this.estimatedDate = estimatedDate;
    }

    public String getEstimatedBy() {
        return estimatedBy;
    }

    public void setEstimatedBy(String estimatedBy) {
        this.estimatedBy = estimatedBy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getTargetPrice() {
        return targetPrice;
    }

    public void setTargetPrice(BigDecimal targetPrice) {
        this.targetPrice = targetPrice;
    }

    public String getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(String targetDate) {
        this.targetDate = targetDate;
    }
}
