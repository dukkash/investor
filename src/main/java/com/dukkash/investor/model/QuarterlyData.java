package com.dukkash.investor.model;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity(name = "quarterly_data")
public class QuarterlyData implements Serializable {

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Integer id;

	@ManyToOne
	private Company company;

	@Column
	private String name;

	@Column(name = "earnings_date")
	private Date earningsDate;

	@Column(name = "shares_outstanding")
	private BigDecimal sharesOutstanding;
	
	@Column(name = "maximum_price")
	private BigDecimal maximumPrice;

	@Column(name = "notes")
	private String notes;

	@OneToOne(fetch = FetchType.EAGER, mappedBy = "quarterlyData", cascade = CascadeType.ALL)
	private BalanceSheet balanceSheet;

	@OneToOne(fetch = FetchType.EAGER, mappedBy = "quarterlyData", cascade = CascadeType.ALL)
	private IncomeStatement incomeStatement;

	@OneToOne(fetch = FetchType.EAGER, mappedBy = "quarterlyData", cascade = CascadeType.ALL)
	private CashFlow cashFlow;

	@Column(name = "is_forecast", columnDefinition="BIT")
	private Boolean isForecast;

    public Boolean getForecast() {
        return isForecast;
    }

    public void setForecast(Boolean forecast) {
        isForecast = forecast;
    }

    public BigDecimal getMaximumPrice() {
		return maximumPrice;
	}

	public void setMaximumPrice(BigDecimal maximumPrice) {
		this.maximumPrice = maximumPrice;
	}

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getEarningsDate() {
		return earningsDate;
	}

	public void setEarningsDate(Date earningsDate) {
		this.earningsDate = earningsDate;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public BigDecimal getSharesOutstanding() {
		return sharesOutstanding;
	}

	public void setSharesOutstanding(BigDecimal sharesOutstanding) {
		this.sharesOutstanding = sharesOutstanding;
	}

	public BalanceSheet getBalanceSheet() {
		return balanceSheet;
	}

	public void setBalanceSheet(BalanceSheet balanceSheet) {
		this.balanceSheet = balanceSheet;
	}

	public IncomeStatement getIncomeStatement() {
		return incomeStatement;
	}

	public void setIncomeStatement(IncomeStatement incomeStatement) {
		this.incomeStatement = incomeStatement;
	}

	public CashFlow getCashFlow() {
		return cashFlow;
	}

	public void setCashFlow(CashFlow cashFlow) {
		this.cashFlow = cashFlow;
	}

	@Override
	public String toString() {
		return "QuarterlyData [id=" + id + ", company=" + company + ", name=" + name + ", earningsDate=" + earningsDate
				+ ", sharesOutstanding=" + sharesOutstanding + ", maximumPrice=" + maximumPrice + ", notes=" + notes
				+ ", balanceSheet=" + balanceSheet + ", incomeStatement=" + incomeStatement + ", cashFlow=" + cashFlow
				+ "]";
	}

	

}
