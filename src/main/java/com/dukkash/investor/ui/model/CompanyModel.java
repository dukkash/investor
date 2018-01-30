package com.dukkash.investor.ui.model;

import java.math.BigDecimal;

public class CompanyModel {
	private Integer id;
	private String name;
	private String about;
	private String tickerSymbol;
	private Integer country;
	private Integer sector;
	private BigDecimal price;
	private String stockUrl;
	private BigDecimal priceEarning; // last 4Q
	private BigDecimal lastTwoQuartersPE; // last 2Q
	private BigDecimal priceBook;
	private BigDecimal equity;
	private BigDecimal marketCap;
	private BigDecimal workingCapital;
	private BigDecimal cash;
	private BigDecimal totalDebt;
	private BigDecimal buyIndicator; // below 22.5 is good, it is P/E * P/BV
	private BigDecimal sharesOutstanding;
	private String nextEarningsDate;

	public CompanyModel() {
	}

	public BigDecimal getLastTwoQuartersPE() {
		return lastTwoQuartersPE;
	}

	public void setLastTwoQuartersPE(BigDecimal lastTwoQuartersPE) {
		this.lastTwoQuartersPE = lastTwoQuartersPE;
	}

	public String getNextEarningsDate() {
        return nextEarningsDate;
    }

    public void setNextEarningsDate(String nextEarningsDate) {
        this.nextEarningsDate = nextEarningsDate;
    }

    public void setStockUrl(String stockUrl) {
		this.stockUrl = stockUrl;
	}

	public BigDecimal getSharesOutstanding() {
		return sharesOutstanding;
	}

	public void setSharesOutstanding(BigDecimal sharesOutstanding) {
		this.sharesOutstanding = sharesOutstanding;
	}

	public BigDecimal getBuyIndicator() {
		return buyIndicator;
	}

	public void setBuyIndicator(BigDecimal buyIndicator) {
		this.buyIndicator = buyIndicator;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public BigDecimal getWorkingCapital() {
		return workingCapital;
	}

	public void setWorkingCapital(BigDecimal workingCapital) {
		this.workingCapital = workingCapital;
	}

	public BigDecimal getPriceEarning() {
		return priceEarning;
	}

	public void setPriceEarning(BigDecimal priceEarning) {
		this.priceEarning = priceEarning;
	}

	public BigDecimal getPriceBook() {
		return priceBook;
	}

	public void setPriceBook(BigDecimal priceBook) {
		this.priceBook = priceBook;
	}

	public BigDecimal getEquity() {
		return equity;
	}

	public void setEquity(BigDecimal equity) {
		this.equity = equity;
	}

	public BigDecimal getMarketCap() {
		return marketCap;
	}

	public void setMarketCap(BigDecimal marketCap) {
		this.marketCap = marketCap;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getTickerSymbol() {
		return tickerSymbol;
	}

	public void setTickerSymbol(String tickerSymbol) {
		this.tickerSymbol = tickerSymbol;
	}

	public Integer getCountry() {
		return country;
	}

	public void setCountry(Integer country) {
		this.country = country;
	}

	public Integer getSector() {
		return sector;
	}

	public void setSector(Integer sector) {
		this.sector = sector;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getStockUrl() {
		return stockUrl;
	}

	public void sestStockUrl(String stockUrl) {
		this.stockUrl = stockUrl;
	}
}
