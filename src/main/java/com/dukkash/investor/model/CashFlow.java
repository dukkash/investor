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
@Table(name = "cash_flow")
public class CashFlow {

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "q_data_id", referencedColumnName = "ID")
	private Period quarterlyData;

	@Column(name = "operating_activities_cash")
	private BigDecimal operatingActivitiesCash;

	@Column(name = "investing_activities_cash")
	private BigDecimal investingActivitiesCash;

	@Column(name = "financing_ativities_cash")
	private BigDecimal financingAtivitiesCash;

	@Column(name = "interest_expences")
	private BigDecimal interestExpences;

	@Column(name = "dep_and_amrt_expenses")
	private BigDecimal depAndAmrtExpenses;

	@Column(name = "interest_income")
	private BigDecimal interestIncome;

	@Column(name = "dividend_payment")
	private BigDecimal dividendPayment;

	@Column(name = "debt_issued")
	private BigDecimal debtIssued;

	@Column(name = "debt_payments")
	private BigDecimal debtPayments;

	@Column
	private BigDecimal liquidity;

	public CashFlow() {
		this.debtIssued = new BigDecimal(0);
		this.debtPayments = new BigDecimal(0);
		this.dividendPayment = new BigDecimal(0);
		this.financingAtivitiesCash = new BigDecimal(0);
		this.interestExpences = new BigDecimal(0);
		this.interestIncome = new BigDecimal(0);
		this.investingActivitiesCash = new BigDecimal(0);
		this.liquidity = new BigDecimal(0);
		this.operatingActivitiesCash = new BigDecimal(0);
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

	public BigDecimal getFinancingAtivitiesCash() {
		return financingAtivitiesCash;
	}

	public void setFinancingAtivitiesCash(BigDecimal financingAtivitiesCash) {
		this.financingAtivitiesCash = financingAtivitiesCash;
	}

	public BigDecimal getDepAndAmrtExpenses() {
		return depAndAmrtExpenses;
	}

	public void setDepAndAmrtExpenses(BigDecimal depAndAmrtExpenses) {
		this.depAndAmrtExpenses = depAndAmrtExpenses;
	}

	public BigDecimal getInterestExpences() {
		return interestExpences;
	}

	public void setInterestExpences(BigDecimal interestExpences) {
		this.interestExpences = interestExpences;
	}

	public BigDecimal getInterestIncome() {
		return interestIncome;
	}

	public void setInterestIncome(BigDecimal interestIncome) {
		this.interestIncome = interestIncome;
	}

	public BigDecimal getDividendPayment() {
		return dividendPayment;
	}

	public void setDividendPayment(BigDecimal dividendPayment) {
		this.dividendPayment = dividendPayment;
	}

	public BigDecimal getDebtIssued() {
		return debtIssued;
	}

	public void setDebtIssued(BigDecimal debtIssued) {
		this.debtIssued = debtIssued;
	}

	public BigDecimal getDebtPayments() {
		return debtPayments;
	}

	public void setDebtPayments(BigDecimal debtPayments) {
		this.debtPayments = debtPayments;
	}

	public BigDecimal getLiquidity() {
		return liquidity;
	}

	public void setLiquidity(BigDecimal liquidity) {
		this.liquidity = liquidity;
	}

	@Override
	public String toString() {
		return "CashFlow [id=" + id + ", quarterlyData=" + quarterlyData + ", operatingActivitiesCash="
				+ operatingActivitiesCash + ", investingActivitiesCash=" + investingActivitiesCash
				+ ", financingAtivitiesCash=" + financingAtivitiesCash + ", interestExpences=" + interestExpences
				+ ", interestIncome=" + interestIncome + ", dividendPayment=" + dividendPayment + ", debtIssued="
				+ debtIssued + ", debtPayments=" + debtPayments + ", liquidity=" + liquidity + "]";
	}
}
