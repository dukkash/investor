package com.dukkash.investor.parser.html;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import com.dukkash.investor.model.Company;
import com.dukkash.investor.model.Period;
import com.dukkash.investor.model.BalanceSheet;
import com.dukkash.investor.model.IncomeStatement;

public class YahooParser {

    public static BigDecimal getStockMarketPrice(String url) throws IOException {
        BigDecimal stockPrice = null;
        org.jsoup.nodes.Document doc = Jsoup.connect(url).get();

        String s = doc.toString();
        int beginIndex = s.indexOf("root.App.main = ");
        int endIndex = s.indexOf("}(this));") - 2;
        JSONObject json = new JSONObject(s.substring(beginIndex + 16, endIndex));
        JSONObject temp = (JSONObject) json.getJSONObject("context");
        temp = (JSONObject) temp.getJSONObject("dispatcher");
        temp = (JSONObject) temp.getJSONObject("stores");
        JSONObject price = temp.getJSONObject("QuoteSummaryStore").getJSONObject("price");
        Double regularMarketPrice = (Double) price.getJSONObject("regularMarketPrice").get("raw");
        stockPrice = new BigDecimal(regularMarketPrice.doubleValue());

        return stockPrice;
    }

	public Company parseCompanyk(String url) throws IOException {

		Company company = new Company();

		org.jsoup.nodes.Document doc = Jsoup.connect("https://finance.yahoo.com/quote/BAC/financials?p=BAC").get();

		String s = doc.toString();

		int beginIndex = s.indexOf("root.App.main = ");
		int endIndex = s.indexOf("}(this));") - 2;

		// System.out.println(s);
		System.out.println(s.substring(beginIndex + 16, endIndex));
		JSONObject json = new JSONObject(s.substring(beginIndex + 16, endIndex));
		JSONObject temp = (JSONObject) json.getJSONObject("context");
		temp = (JSONObject) temp.getJSONObject("dispatcher");
		temp = (JSONObject) temp.getJSONObject("stores");

		// JSONArray cashflowStatementHistory =
		// temp.getJSONObject("QuoteSummaryStore").getJSONArray("cashflowStatementHistory");
		JSONObject balanceSheetHistoryQuarterly = temp.getJSONObject("QuoteSummaryStore").getJSONObject("balanceSheetHistoryQuarterly");
		JSONArray balanceSheetStatements = balanceSheetHistoryQuarterly.getJSONArray("balanceSheetStatements");
		JSONObject q1 = balanceSheetStatements.getJSONObject(0);
		JSONObject q2 = balanceSheetStatements.getJSONObject(1);
		JSONObject q3 = balanceSheetStatements.getJSONObject(2);
		JSONObject q4 = balanceSheetStatements.getJSONObject(3);

		//company.setCountryCode("US");
		company.setStockUrl(url);
		company.setTickerSymbol("BAC");

		
		String sharesOutstanding;
		String dividend;
	

		
		
		JSONObject earnings = temp.getJSONObject("QuoteSummaryStore").getJSONObject("earnings");
		
		addQuarterlyData(q1, company, earnings);
		addQuarterlyData(q2, company, earnings);
		addQuarterlyData(q3, company, earnings);
		addQuarterlyData(q4, company, earnings);
		
		JSONObject price = temp.getJSONObject("QuoteSummaryStore").getJSONObject("price");
		Double regularMarketPrice = (Double) price.getJSONObject("regularMarketPrice").get("raw");
		Long averageDailyVolume3Month = (Long) temp.getJSONObject("averageDailyVolume3Month").get("raw");

		company.setName(temp.getString("shortName"));
		

		company.setPrice(new BigDecimal(regularMarketPrice));

		company.setTickerSymbol(temp.getString("symbol"));

		
		return company;
	}

	public void addQuarterlyData(JSONObject qJSON, Company company, JSONObject earnings ) {
		Period qData = new Period();
		qData.setCompany(company);

		BalanceSheet qb1 = new BalanceSheet();

		Long element = (Long) qJSON.getJSONObject("totalLiab").get("raw");

		if (element != null) {
			qb1.setTotalLiabilities(new BigDecimal(element));
		} // totalAssets
		
		element = (Long) qJSON.getJSONObject("totalAssets").get("raw");

		if (element != null) {
			qb1.setTotalAssets(new BigDecimal(element));
		}
		
		String date = qJSON.getJSONObject("endDate").getString("fmt");
		String dateParts[] = date.split("-");
		String quarter = "";
		
		if(dateParts[1].equals("03")) {
			qData.setName("Q1-" + dateParts[0]);
			quarter = "Q1" + dateParts[0];
		} else if(dateParts[1].equals("06")) {
			qData.setName("Q2-" + dateParts[0]);
			quarter = "Q2" + dateParts[0];
		} else if(dateParts[1].equals("09")) {
			qData.setName("Q3-" + dateParts[0]);
			quarter = "Q3" + dateParts[0];
		} else  {
			qData.setName("Q4-" + dateParts[0]);
			quarter = "Q4" + dateParts[0];
		}
				
		LocalDate localDate = LocalDate.of(Integer.valueOf(dateParts[0]), Integer.valueOf(dateParts[1]), Integer.valueOf(dateParts[2]));
		Date date1 =  Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

		
		IncomeStatement is = new IncomeStatement();
		is.setQuarterlyData(qData);
		
		JSONArray financialsChart = earnings.getJSONObject("financialsChart").getJSONArray("quarterly");
		
		for(int i=0;i<4;i++ ) {
			if(financialsChart.getJSONObject(i).getString("date").equals(quarter)) {
				element = (Long) financialsChart.getJSONObject(i).getJSONObject("revenue").get("raw");
				
				if (element != null) {				
					is.setRevenue(new BigDecimal(element));
				}
				
				element = (Long) financialsChart.getJSONObject(i).getJSONObject("earnings").get("raw");

			}
		}
		
	}

	public static void main(String[] args) throws IOException {
		new YahooParser().getStockMarketPrice("https://finance.yahoo.com/quote/BAC/financials?p=BAC");

	}
}
