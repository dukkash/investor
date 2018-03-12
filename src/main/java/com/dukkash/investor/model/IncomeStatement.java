package com.dukkash.investor.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "income_statement")
public class IncomeStatement {

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "q_data_id", referencedColumnName = "ID")
	private Period quarterlyData;

	@Column(name = "revenue")
	private BigDecimal revenue;

	@Column(name = "cost_of_sales")
	private BigDecimal costOfSales;

	@Column(name = "gross_profit")
	private BigDecimal grossProfit;

	@Column(name = "operating_profit")
	private BigDecimal operatingProfit;

	@Column(name = "interest_expenses")
	private BigDecimal interestExpenses;

	@Column(name = "tax_expenses")
	private BigDecimal taxExpenses;

	@Column(name = "net_profit")
	private BigDecimal netProfit;

	@Column(name = "interest_income")
	private BigDecimal interestIncome;

	@Column(name = "non_interest_income")
	private BigDecimal nonInterestIncome;

	@Column(name = "finance_income")
	private BigDecimal financeIncome;

	@Column(name = "finance_cost")
	private BigDecimal financeCost;

	public IncomeStatement() {
		this.costOfSales = new BigDecimal(0);
		this.financeCost = new BigDecimal(0);
		this.financeIncome = new BigDecimal(0);
		this.grossProfit = new BigDecimal(0);
		this.interestExpenses = new BigDecimal(0);
		this.interestIncome = new BigDecimal(0);
		this.netProfit = new BigDecimal(0);
		this.nonInterestIncome = new BigDecimal(0);
		this.operatingProfit = new BigDecimal(0);
		this.revenue = new BigDecimal(0);
		this.taxExpenses = new BigDecimal(0);
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

	public BigDecimal getCostOfSales() {
		return costOfSales;
	}

	public void setCostOfSales(BigDecimal costOfSales) {
		this.costOfSales = costOfSales;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Period getQuarterlyData() {
		return quarterlyData;
	}

	public void setQuarterlyData(Period quarterlyData) {
		this.quarterlyData = quarterlyData;
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

	public BigDecimal getOperatingProfit() {
		return operatingProfit;
	}

	public void setOperatingProfit(BigDecimal operatingProfit) {
		this.operatingProfit = operatingProfit;
	}

	public BigDecimal getInterestExpenses() {
		return interestExpenses;
	}

	public void setInterestExpenses(BigDecimal interestExpenses) {
		this.interestExpenses = interestExpenses;
	}

	public BigDecimal getTaxExpenses() {
		return taxExpenses;
	}

	public void setTaxExpenses(BigDecimal taxExpenses) {
		this.taxExpenses = taxExpenses;
	}

	public BigDecimal getNetProfit() {
		return netProfit;
	}

	public void setNetProfit(BigDecimal netProfit) {
		this.netProfit = netProfit;
	}

	@Override
	public String toString() {
		return "IncomeStatement [id=" + id + ", quarterlyData=" + quarterlyData + ", revenue=" + revenue
				+ ", costOfSales=" + costOfSales + ", grossProfit=" + grossProfit
				+ ", operatingProfit=" + operatingProfit + ", interestExpenses=" + interestExpenses + ", taxExpenses="
				+ taxExpenses + ", netProfit=" + netProfit + ", interestIncome=" + interestIncome
				+ ", nonInterestIncome=" + nonInterestIncome + ", financeIncome=" + financeIncome + ", financeCost="
				+ financeCost + "]";
	}

}
