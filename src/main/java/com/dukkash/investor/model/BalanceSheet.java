package com.dukkash.investor.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "balance_sheet")
public class BalanceSheet implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "q_data_id", referencedColumnName = "ID")
	private Period quarterlyData;

	@Column(name = "total_assets")
	private BigDecimal totalAssets;

	@Column(name = "total_current_assets")
	private BigDecimal totalCurrentAssets;

	@Column(name = "total_liabilities")
	private BigDecimal totalLiabilities;

	@Column(name = "total_current_liabilities")
	private BigDecimal totalCurrentLiabilities;

	@Column
	private BigDecimal equity;

	@Column
	private BigDecimal prepayments;

	@Column(name = "inventories")
	private BigDecimal inventories;

	@Column(name = "property_plant_equipment")
	private BigDecimal propertyPlantEquipment;

	@Column
	private BigDecimal cash; // cash and cash equivalents

	@Column(name = "short_term_debt")
	private BigDecimal shortTermDebt;

	@Column(name = "long_term_debt")
	private BigDecimal longTermDebt;

	@Column(name = "trade_receivables")
	private BigDecimal tradeReceivables; // for banks -> loans and receivables

	@Column(name = "other_receivables")
	private BigDecimal otherReceivables;

	@Column(name = "trade_payables")
	private BigDecimal tradePayables;

	@Column(name = "other_payables")
	private BigDecimal otherPayables;

	@Column(name = "total_debt")
	private BigDecimal totalDebt;

	@Column(name = "intangible_assets")
	private BigDecimal intangibleAssets; // used when calculating the book value

	public BalanceSheet() {
		this.cash = new BigDecimal(0);
		this.equity = new BigDecimal(0);
		this.intangibleAssets = new BigDecimal(0);
		this.inventories = new BigDecimal(0);
		this.longTermDebt = new BigDecimal(0);
		this.otherPayables = new BigDecimal(0);
		this.prepayments = new BigDecimal(0);
		this.propertyPlantEquipment = new BigDecimal(0);
		this.shortTermDebt = new BigDecimal(0);
		this.totalAssets = new BigDecimal(0);
		this.totalCurrentAssets = new BigDecimal(0);
		this.totalCurrentLiabilities = new BigDecimal(0);
		this.totalLiabilities = new BigDecimal(0);
		this.totalDebt = new BigDecimal(0);
		this.tradePayables = new BigDecimal(0);
		this.otherReceivables = new BigDecimal(0);
		this.tradeReceivables = new BigDecimal(0);
	}

	public BigDecimal getIntangibleAssets() {
		return intangibleAssets;
	}

	public void setIntangibleAssets(BigDecimal intangibleAssets) {
		this.intangibleAssets = intangibleAssets;
	}

	public BigDecimal getTotalDebt() {
		return totalDebt;
	}

	public void setTotalDebt(BigDecimal totalDebt) {
		this.totalDebt = totalDebt;
	}

	public BigDecimal getOtherPayables() {
		return otherPayables;
	}

	public void setOtherPayables(BigDecimal otherPayables) {
		this.otherPayables = otherPayables;
	}

	public BigDecimal getOtherReceivables() {
		return otherReceivables;
	}

	public void setOtherReceivables(BigDecimal otherReceivables) {
		this.otherReceivables = otherReceivables;
	}

	public BigDecimal getPrepayments() {
		return prepayments;
	}

	public void setPrepayments(BigDecimal prepayments) {
		this.prepayments = prepayments;
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

	public BigDecimal getTotalAssets() {
		return totalAssets;
	}

	public void setTotalAssets(BigDecimal totalAssets) {
		this.totalAssets = totalAssets;
	}

	public BigDecimal getTotalCurrentAssets() {
		return totalCurrentAssets;
	}

	public void setTotalCurrentAssets(BigDecimal totalCurrentAssets) {
		this.totalCurrentAssets = totalCurrentAssets;
	}

	public BigDecimal getTotalLiabilities() {
		return totalLiabilities;
	}

	public void setTotalLiabilities(BigDecimal totalLiabilities) {
		this.totalLiabilities = totalLiabilities;
	}

	public BigDecimal getTotalCurrentLiabilities() {
		return totalCurrentLiabilities;
	}

	public void setTotalCurrentLiabilities(BigDecimal totalCurrentLiabilities) {
		this.totalCurrentLiabilities = totalCurrentLiabilities;
	}

	public BigDecimal getEquity() {
		return equity;
	}

	public void setEquity(BigDecimal equity) {
		this.equity = equity;
	}

	public BigDecimal getInventories() {
		return inventories;
	}

	public void setInventories(BigDecimal inventories) {
		this.inventories = inventories;
	}

	public BigDecimal getPropertyPlantEquipment() {
		return propertyPlantEquipment;
	}

	public void setPropertyPlantEquipment(BigDecimal propertyPlantEquipment) {
		this.propertyPlantEquipment = propertyPlantEquipment;
	}

	public BigDecimal getCash() {
		return cash;
	}

	public void setCash(BigDecimal cash) {
		this.cash = cash;
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

	@Override
	public String toString() {
		return "BalanceSheet [id=" + id + ", quarterlyData=" + quarterlyData + ", totalAssets=" + totalAssets
				+ ", totalCurrentAssets=" + totalCurrentAssets + ", totalLiabilities=" + totalLiabilities
				+ ", totalCurrentLiabilities=" + totalCurrentLiabilities + ", equity=" + equity + ", prepayments="
				+ prepayments + ", inventories=" + inventories + ", propertyPlantEquipment=" + propertyPlantEquipment
				+ ", cash=" + cash + ", shortTermDebt=" + shortTermDebt
				+ ", longTermDebt=" + longTermDebt + ", tradeReceivables=" + tradeReceivables + ", otherReceivables="
				+ otherReceivables + ", tradePayables=" + tradePayables + ", otherPayables=" + otherPayables
				+ ", totalDebt=" + totalDebt + ", intangibleAssets=" + intangibleAssets + "]";
	}

	
}
