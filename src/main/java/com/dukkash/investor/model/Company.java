package com.dukkash.investor.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "COMPANY")
public class Company {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Integer id;

	@Column
	private String name;

	@Column(name = "ticker_symbol")
	private String tickerSymbol;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "sector_id", referencedColumnName = "ID")
	private CompanySector sector;

	@Column
	private BigDecimal price;

	@Column(name = "shares_outstanding")
	private BigDecimal sharesOutstanding;

	@Column(name="stock_url")
	private String stockUrl;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "country_id", referencedColumnName = "ID")
	private Country country;

	@Column
	private String about;

	@Column(name = "next_earnings_date")
	private Date nextEarningsDate;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "company")
	private List<QuarterlyData> quarterlyData;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "analysed_level_id", referencedColumnName = "ID")
	private AnalysedLevel analysedLevel;

	public Company() {
	}

	public AnalysedLevel getAnalysedLevel() {
		return analysedLevel;
	}

	public void setAnalysedLevel(AnalysedLevel analysedLevel) {
		this.analysedLevel = analysedLevel;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public BigDecimal getSharesOutstanding() {
		return sharesOutstanding;
	}

	public void setSharesOutstanding(BigDecimal sharesOutstanding) {
		this.sharesOutstanding = sharesOutstanding;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Date getNextEarningsDate() {
		return nextEarningsDate;
	}

	public void setNextEarningsDate(Date nextEarningsDate) {
		this.nextEarningsDate = nextEarningsDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public CompanySector getSector() {
		return sector;
	}

	public void setSector(CompanySector sector) {
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

	public void setStockUrl(String stockUrl) {
		this.stockUrl = stockUrl;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public List<QuarterlyData> getQuarterlyData() {
		return quarterlyData;
	}

	public void setQuarterlyData(List<QuarterlyData> quarterlyData) {
		this.quarterlyData = quarterlyData;
	}
}
