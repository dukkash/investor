package com.dukkash.investor.ui.model;

import java.math.BigDecimal;

public class PeriodModel {
	private String name;
	private String tickerSymbol;
	private String earningsDate;
	private BigDecimal shares;
	private BigDecimal revenue;
	private BigDecimal grossProfit;
	private BigDecimal operationalRevenue;
	private BigDecimal operationalProfit;

	private BigDecimal taxExpense;
	private BigDecimal netProfit;
	private BigDecimal interestExpense;
	private BigDecimal interestIncome;
	private BigDecimal nonInterestIncome;
	private BigDecimal totalAssets;
	private BigDecimal totalLiabilities;
	private BigDecimal currentAssets;
	private BigDecimal equity;
    private BigDecimal financeIncome;
    private BigDecimal financeCost;

	private BigDecimal currentLiabilities;
	private BigDecimal inventories;
	private BigDecimal shortTermDebt;
	private BigDecimal longTermDebt;
	private BigDecimal totalDebt;
	private BigDecimal cash;
	private BigDecimal propertyPlantEquipment;
	private BigDecimal loans;
	private BigDecimal deposits;
    private BigDecimal tradeReceivables;
    private BigDecimal tradePayables;
    private BigDecimal otherReceivables;
    private BigDecimal otherPayables;
    private BigDecimal prepayments;
    private BigDecimal intangibleAssets;

	private BigDecimal operatingActivitiesCash;
	private BigDecimal investingActivitiesCash;
	private BigDecimal financialActivitiesCash;
	private BigDecimal liquidity;
	private BigDecimal debtRepaid;
	private BigDecimal dividendPayments;
	private BigDecimal debtIssued;
	private BigDecimal interestPayments;
	private String notes;

	public PeriodModel() {
	}

    public BigDecimal getFinanceIncome() {
        return financeIncome;
    }

    public void setFinanceIncome(BigDecimal financeIncome) {
        this.financeIncome = financeIncome;
    }

    public BigDecimal getFinanceCost() {
        return financeCost;
    }

    public void setFinanceCost(BigDecimal financeCost) {
        this.financeCost = financeCost;
    }

    public BigDecimal getTradeReceivables() {
        return tradeReceivables;
    }

    public void setTradeReceivables(BigDecimal tradeReceivables) {
        this.tradeReceivables = tradeReceivables;
    }

    public BigDecimal getTradePayables() {
        return tradePayables;
    }

    public void setTradePayables(BigDecimal tradePayables) {
        this.tradePayables = tradePayables;
    }

    public BigDecimal getOtherReceivables() {
        return otherReceivables;
    }

    public void setOtherReceivables(BigDecimal otherReceivables) {
        this.otherReceivables = otherReceivables;
    }

    public BigDecimal getOtherPayables() {
        return otherPayables;
    }

    public void setOtherPayables(BigDecimal otherPayables) {
        this.otherPayables = otherPayables;
    }

    public BigDecimal getPrepayments() {
        return prepayments;
    }

    public void setPrepayments(BigDecimal prepayments) {
        this.prepayments = prepayments;
    }

    public BigDecimal getIntangibleAssets() {
        return intangibleAssets;
    }

    public void setIntangibleAssets(BigDecimal intangibleAssets) {
        this.intangibleAssets = intangibleAssets;
    }

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

	public String getEarningsDate() {
		return earningsDate;
	}

	public void setEarningsDate(String earningsDate) {
		this.earningsDate = earningsDate;
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

	public BigDecimal getGrossProfit() {
		return grossProfit;
	}

	public void setGrossProfit(BigDecimal grossProfit) {
		this.grossProfit = grossProfit;
	}

	public BigDecimal getOperationalRevenue() {
		return operationalRevenue;
	}

	public void setOperationalRevenue(BigDecimal operationalRevenue) {
		this.operationalRevenue = operationalRevenue;
	}

	public BigDecimal getOperationalProfit() {
		return operationalProfit;
	}

	public void setOperationalProfit(BigDecimal operationalProfit) {
		this.operationalProfit = operationalProfit;
	}

	public BigDecimal getTaxExpense() {
		return taxExpense;
	}

	public void setTaxExpense(BigDecimal taxExpense) {
		this.taxExpense = taxExpense;
	}

	public BigDecimal getNetProfit() {
		return netProfit;
	}

	public void setNetProfit(BigDecimal netProfit) {
		this.netProfit = netProfit;
	}

	public BigDecimal getInterestExpense() {
		return interestExpense;
	}

	public void setInterestExpense(BigDecimal interestExpense) {
		this.interestExpense = interestExpense;
	}

	public BigDecimal getInterestIncome() {
		return interestIncome;
	}

	public void setInterestIncome(BigDecimal interestIncome) {
		this.interestIncome = interestIncome;
	}

	public BigDecimal getNonInterestIncome() {
		return nonInterestIncome;
	}

	public void setNonInterestIncome(BigDecimal nonInterestIncome) {
		this.nonInterestIncome = nonInterestIncome;
	}

	public BigDecimal getTotalAssets() {
		return totalAssets;
	}

	public void setTotalAssets(BigDecimal totalAssets) {
		this.totalAssets = totalAssets;
	}

	public BigDecimal getTotalLiabilities() {
		return totalLiabilities;
	}

	public void setTotalLiabilities(BigDecimal totalLiabilities) {
		this.totalLiabilities = totalLiabilities;
	}

	public BigDecimal getCurrentAssets() {
		return currentAssets;
	}

	public void setCurrentAssets(BigDecimal currentAssets) {
		this.currentAssets = currentAssets;
	}

	public BigDecimal getEquity() {
		return equity;
	}

	public void setEquity(BigDecimal equity) {
		this.equity = equity;
	}

	public BigDecimal getCurrentLiabilities() {
		return currentLiabilities;
	}

	public void setCurrentLiabilities(BigDecimal currentLiabilities) {
		this.currentLiabilities = currentLiabilities;
	}

	public BigDecimal getInventories() {
		return inventories;
	}

	public void setInventories(BigDecimal inventories) {
		this.inventories = inventories;
	}

	public BigDecimal getShortTermDebt() {
		return shortTermDebt;
	}

	public void setShortTermDebt(BigDecimal shortTermDebt) {
		this.shortTermDebt = shortTermDebt;
	}

	public BigDecimal getLongTermDebt() {
		return longTermDebt;
	}

	public void setLongTermDebt(BigDecimal longTermDebt) {
		this.longTermDebt = longTermDebt;
	}

	public BigDecimal getTotalDebt() {
		return totalDebt;
	}

	public void setTotalDebt(BigDecimal totalDebt) {
		this.totalDebt = totalDebt;
	}

	public BigDecimal getCash() {
		return cash;
	}

	public void setCash(BigDecimal cash) {
		this.cash = cash;
	}

	public BigDecimal getPropertyPlantEquipment() {
		return propertyPlantEquipment;
	}

	public void setPropertyPlantEquipment(BigDecimal propertyPlantEquipment) {
		this.propertyPlantEquipment = propertyPlantEquipment;
	}

	public BigDecimal getLoans() {
		return loans;
	}

	public void setLoans(BigDecimal loans) {
		this.loans = loans;
	}

	public BigDecimal getDeposits() {
		return deposits;
	}

	public void setDeposits(BigDecimal deposits) {
		this.deposits = deposits;
	}

	public BigDecimal getOperatingActivitiesCash() {
		return operatingActivitiesCash;
	}

	public void setOperatingActivitiesCash(BigDecimal operatingActivitiesCash) {
		this.operatingActivitiesCash = operatingActivitiesCash;
	}

	public BigDecimal getInvestingActivitiesCash() {
		return investingActivitiesCash;
	}

	public void setInvestingActivitiesCash(BigDecimal investingActivitiesCash) {
		this.investingActivitiesCash = investingActivitiesCash;
	}

	public BigDecimal getFinancialActivitiesCash() {
		return financialActivitiesCash;
	}

	public void setFinancialActivitiesCash(BigDecimal financialActivitiesCash) {
		this.financialActivitiesCash = financialActivitiesCash;
	}

	public BigDecimal getLiquidity() {
		return liquidity;
	}

	public void setLiquidity(BigDecimal liquidity) {
		this.liquidity = liquidity;
	}

	public BigDecimal getDebtRepaid() {
		return debtRepaid;
	}

	public void setDebtRepaid(BigDecimal debtRepaid) {
		this.debtRepaid = debtRepaid;
	}

	public BigDecimal getDividendPayments() {
		return dividendPayments;
	}

	public void setDividendPayments(BigDecimal dividendPayments) {
		this.dividendPayments = dividendPayments;
	}

	public BigDecimal getDebtIssued() {
		return debtIssued;
	}

	public void setDebtIssued(BigDecimal debtIssued) {
		this.debtIssued = debtIssued;
	}

	public BigDecimal getInterestPayments() {
		return interestPayments;
	}

	public void setInterestPayments(BigDecimal interestPayments) {
		this.interestPayments = interestPayments;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
}
