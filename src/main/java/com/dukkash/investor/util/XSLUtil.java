package com.dukkash.investor.util;

import com.dukkash.investor.model.BalanceSheet;
import com.dukkash.investor.model.CashFlow;
import com.dukkash.investor.model.IncomeStatement;
import com.dukkash.investor.model.QuarterlyData;
import com.dukkash.investor.service.CompanyService;
import com.dukkash.investor.service.QuarterlyDataService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

@Component
public class XSLUtil {

	private BigDecimal multiplier;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private QuarterlyDataService quarterlyDataService;

	public void parseData() throws IOException {
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			}
		} };

		// Activate the new trust manager
		try {
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
		}

		// And as before now you can use URL and URLConnection
		URL url = new URL("https://www.kap.org.tr/en/downloadAsExcel/640550");
		URLConnection connection = url.openConnection();
		InputStream is = connection.getInputStream();

		// org.jsoup.nodes.Document doc = Jsoup.connect(url).get().
		URL website = new URL("https://www.kap.org.tr/en/downloadAsExcel/640550");
		try (InputStream in = website.openStream()) {
			System.out.println(90);
			// Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
		}
		FileInputStream excelFile = new FileInputStream(new File(""));
		Workbook workbook = new HSSFWorkbook(excelFile);
		Sheet sheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = sheet.iterator();

		while (iterator.hasNext()) {
			Row temp = iterator.next();
		}

	}

	public void parseBankQuarterlyFinancialReport(String symbol, String fileName, BigDecimal multiplier,
			String quarterName, String earningDate, BigDecimal sharesOuts) {
		this.multiplier = multiplier;
		QuarterlyData qData = new QuarterlyData();

		try {
			FileInputStream excelFile = new FileInputStream(new File(fileName));
			Workbook workbook = new HSSFWorkbook(excelFile);
			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = sheet.iterator();

			qData.setName(quarterName);
			qData.setEarningsDate(DateUtil.getDate(earningDate));
			qData.setSharesOutstanding(sharesOuts);
			qData.setCompany(companyService.getCompanyByTickerSymbol(symbol));

			QuarterlyData d = quarterlyDataService.getByNameAndCompany(qData.getName(), qData.getCompany());

			if (d != null) {
				throw new Exception("QuarterlyData already exist!");
			}

			readBankFinancialReport(iterator, qData);
			validateBankResult(qData);

			quarterlyDataService.save(qData);
			System.out.println(qData);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(qData);
	}

	private void validateBankResult(QuarterlyData qData) throws Exception {
		BalanceSheet bs = qData.getBalanceSheet();
		IncomeStatement is = qData.getIncomeStatement();
		CashFlow cf = qData.getCashFlow();
		BigDecimal zero = new BigDecimal(0);

		if (bs.getTotalAssets() == null || bs.getTotalAssets().equals(zero)) {
			throw new Exception("getTotalAssets is missing!");
		}

		if (bs.getTotalLiabilities() == null || bs.getTotalLiabilities().equals(zero)) {
			throw new Exception("getTotalLiabilities is missing!");
		}

		if (bs.getTradePayables() == null || bs.getTradePayables().equals(zero)) {
			throw new Exception("getTradePayables is missing!");
		}

		if (bs.getTradeReceivables() == null || bs.getTradeReceivables().equals(zero)) {
			throw new Exception("getTradeReceivables is missing!");
		}

		if (cf.getLiquidity() == null || cf.getLiquidity().equals(zero)) {
			throw new Exception("getCash is missing!");
		}

		if (bs.getEquity() == null || bs.getEquity().equals(zero)) {
			throw new Exception("getEquity is missing!");
		}

		if (is.getRevenue() == null || is.getRevenue().equals(zero)) {
			throw new Exception("getRevenue is missing!");
		}

		if (is.getNetProfit() == null || is.getNetProfit().equals(zero)) {
			throw new Exception("getNetProfit is missing!");
		}

		if (cf.getFinancingAtivitiesCash() == null || cf.getFinancingAtivitiesCash().equals(zero)) {
			throw new Exception("getFinancingAtivitiesCash is missing!");
		}

		if (cf.getOperatingActivitiesCash() == null || cf.getOperatingActivitiesCash().equals(zero)) {
			throw new Exception("getOperatingActivitiesCash is missing!");
		}

		if (cf.getInvestingActivitiesCash() == null || cf.getInvestingActivitiesCash().equals(zero)) {
			throw new Exception("getInvestingActivitiesCash is missing!");
		}
	}

	private void readBankFinancialReport(Iterator<Row> iterator, QuarterlyData qData) {
		BalanceSheet bs = new BalanceSheet();
		IncomeStatement is = new IncomeStatement();
		CashFlow cf = new CashFlow();

		qData.setBalanceSheet(bs);
		bs.setQuarterlyData(qData);
		qData.setIncomeStatement(is);
		is.setQuarterlyData(qData);
		qData.setCashFlow(cf);
		cf.setQuarterlyData(qData);
		int counter = 0;

		while (iterator.hasNext()) {
			Row currentRow = iterator.next();
			Cell currentCell = currentRow.getCell(2);
			counter++;

			if (currentCell == null) {
				continue;
			}

			if (currentCell.getCellTypeEnum() == CellType.STRING
					&& (currentCell.getStringCellValue().trim().equals("ASSET ITEMS")
							|| currentCell.getStringCellValue().trim().equals("Income Statement"))) {
				counter = 0;
				continue;
			}

			if (currentCell.getCellTypeEnum() == CellType.STRING
					&& currentCell.getStringCellValue().trim().toUpperCase().equals("LOANS AND RECEIVABLES")
					&& counter == 22) {
				currentCell = currentRow.getCell(8);
				bs.setTradeReceivables(getNumericValue(currentCell));
				iterator.next(); // read the next line with the same name
			} else if (currentCell.getCellTypeEnum() == CellType.STRING
					&& currentCell.getStringCellValue().trim().toUpperCase().equals("TOTAL ASSETS") && counter == 66) {
				currentCell = currentRow.getCell(8);
				bs.setTotalAssets(getNumericValue(currentCell));
			} else if (currentCell.getCellTypeEnum() == CellType.STRING
					&& currentCell.getStringCellValue().trim().toUpperCase().equals("DEPOSITS") && counter == 68) {
				currentCell = currentRow.getCell(8);
				bs.setTradePayables(getNumericValue(currentCell));
			} else if (currentCell.getCellTypeEnum() == CellType.STRING
					&& currentCell.getStringCellValue().trim().equals("EQUITY") && counter == 109) {
				currentCell = currentRow.getCell(8);
				bs.setEquity(getNumericValue(currentCell));
				bs.setTotalLiabilities(bs.getTotalAssets().subtract(bs.getEquity()));
			} else if (currentCell.getCellTypeEnum() == CellType.STRING
					&& currentCell.getStringCellValue().trim().equals("INTEREST INCOME") && counter == 2) {
				currentCell = currentRow.getCell(7);
				is.setInterestIncome(getNumericValue(currentCell));
				is.setRevenue(is.getInterestIncome());
			} else if (currentCell.getCellTypeEnum() == CellType.STRING
					&& currentCell.getStringCellValue().trim().equals("INTEREST EXPENSES") && counter == 14) {
				currentCell = currentRow.getCell(7);
				is.setInterestExpenses(getNumericValue(currentCell));
			} else if (currentCell.getCellTypeEnum() == CellType.STRING
					&& (currentCell.getStringCellValue().trim().equals("Finance Lease Income")
							|| currentCell.getStringCellValue().trim().equals("Other Interest Income")
							|| currentCell.getStringCellValue().trim().equals("Fees and Commissions Received")
							|| currentCell.getStringCellValue().trim().equals("OTHER OPERATING INCOME")
							|| currentCell.getStringCellValue().trim().equals("DIVIDEND INCOME"))) {
				currentCell = currentRow.getCell(7);
				is.setRevenue(getNumericValue(currentCell).add(is.getRevenue()));
			} else if (currentCell.getCellTypeEnum() == CellType.STRING
					&& currentCell.getStringCellValue().trim().equals("NET OPERATING INCOME (LOSS)") && counter == 37) {
				currentCell = currentRow.getCell(7);
				is.setOperatingProfit(getNumericValue(currentCell));
			} else if (currentCell.getCellTypeEnum() == CellType.STRING
					&& currentCell.getStringCellValue().trim().equals("TAX PROVISION FOR CONTINUING OPERATIONS (+/-)")
					&& counter == 42) {
				currentCell = currentRow.getCell(7);
				is.setTaxExpenses(getNumericValue(currentCell));
			} else if (currentCell.getCellTypeEnum() == CellType.STRING
					&& currentCell.getStringCellValue().trim().equals("NET PROFIT (LOSS)") && counter == 59) {
				currentCell = currentRow.getCell(7);
				is.setNetProfit(getNumericValue(currentCell));
			} else if (currentCell.getCellTypeEnum() == CellType.STRING
					&& currentCell.getStringCellValue().trim().equals("Net Cash Provided From Banking Operations")) {
				currentCell = currentRow.getCell(6);
				cf.setOperatingActivitiesCash(getNumericValue(currentCell));
				counter = 0;
			} else if (currentCell.getCellTypeEnum() == CellType.STRING && currentCell.getStringCellValue().trim()
					.equals("Net cash flows from (used in) investing activities")) {
				currentCell = currentRow.getCell(6);
				cf.setInvestingActivitiesCash(getNumericValue(currentCell));
			} else if (currentCell.getCellTypeEnum() == CellType.STRING && currentCell.getStringCellValue().trim()
					.equals("Net cash flows from (used in) financing activities")) {
				currentCell = currentRow.getCell(6);
				cf.setFinancingAtivitiesCash(getNumericValue(currentCell));
			} else if (currentCell.getCellTypeEnum() == CellType.STRING
					&& currentCell.getStringCellValue().trim().equals("Dividends paid")) {
				currentCell = currentRow.getCell(6);
				cf.setDividendPayment(getNumericValue(currentCell));
			} else if (currentCell.getCellTypeEnum() == CellType.STRING && currentCell.getStringCellValue().trim()
					.equals("Cash and Cash Equivalents at End of the Period")) {
				currentCell = currentRow.getCell(6);
				cf.setLiquidity(getNumericValue(currentCell));
			}
		}
	}

	public void parseQuarterlyFinancialReport(String symbol, String fileName, BigDecimal multiplier, String quarterName,
			String earningDate, BigDecimal sharesOuts) {
		this.multiplier = multiplier;
		QuarterlyData qData = new QuarterlyData();
		try {
			FileInputStream excelFile = new FileInputStream(new File(fileName));
			Workbook workbook = new HSSFWorkbook(excelFile);
			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = sheet.iterator();

			qData.setName(quarterName);
			qData.setEarningsDate(DateUtil.getDate(earningDate));
			qData.setSharesOutstanding(sharesOuts);
			qData.setCompany(companyService.getCompanyByTickerSymbol(symbol));

			QuarterlyData d = quarterlyDataService.getByNameAndCompany(qData.getName(), qData.getCompany());

			if (d != null) {
				throw new Exception("QuarterlyData already exist!");
			}

			readFinancialReport(iterator, qData);
			validateResult(qData);

			quarterlyDataService.save(qData);
			System.out.println(qData);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(qData);
	}

	private void validateResult(QuarterlyData qData) throws Exception {
		BalanceSheet bs = qData.getBalanceSheet();
		IncomeStatement is = qData.getIncomeStatement();
		CashFlow cf = qData.getCashFlow();
		BigDecimal zero = new BigDecimal(0);

		if (bs.getTotalAssets() == null || bs.getTotalAssets().equals(zero)) {
			throw new Exception("getTotalAssets is missing!");
		}

		if (bs.getTotalCurrentAssets() == null || bs.getTotalCurrentAssets().equals(zero)) {
			throw new Exception("getTotalCurrentAssets is missing!");
		}

		if (bs.getTotalLiabilities() == null || bs.getTotalLiabilities().equals(zero)) {
			throw new Exception("getTotalLiabilities is missing!");
		}

		if (bs.getTotalCurrentLiabilities() == null || bs.getTotalCurrentLiabilities().equals(zero)) {
			throw new Exception("getTotalCurrentLiabilities is missing!");
		}

		if (bs.getShortTermDebt() == null || bs.getShortTermDebt().equals(zero)) {
			throw new Exception("getShortTermDebt is missing!");
		}

		if (bs.getLongTermDebt() == null || bs.getLongTermDebt().equals(zero)) {
			throw new Exception("getLongTermDebt is missing!");
		}

		if (bs.getCash() == null || bs.getCash().equals(zero)) {
			throw new Exception("getCash is missing!");
		}

		if (bs.getEquity() == null || bs.getEquity().equals(zero)) {
			throw new Exception("getEquity is missing!");
		}

		if (is.getRevenue() == null || is.getRevenue().equals(zero)) {
			throw new Exception("getRevenue is missing!");
		}

		if (is.getNetProfit() == null || is.getNetProfit().equals(zero)) {
			throw new Exception("getNetProfit is missing!");
		}

		if (cf.getFinancingAtivitiesCash() == null || cf.getFinancingAtivitiesCash().equals(zero)) {
			throw new Exception("getFinancingAtivitiesCash is missing!");
		}

		if (cf.getOperatingActivitiesCash() == null || cf.getOperatingActivitiesCash().equals(zero)) {
			throw new Exception("getOperatingActivitiesCash is missing!");
		}

		if (cf.getInvestingActivitiesCash() == null || cf.getInvestingActivitiesCash().equals(zero)) {
			throw new Exception("getInvestingActivitiesCash is missing!");
		}
	}

	private void readFinancialReport(Iterator<Row> iterator, QuarterlyData qData) throws Exception {
		BalanceSheet bs = new BalanceSheet();
		IncomeStatement is = new IncomeStatement();
		CashFlow cf = new CashFlow();

		qData.setBalanceSheet(bs);
		bs.setQuarterlyData(qData);
		qData.setIncomeStatement(is);
		is.setQuarterlyData(qData);
		qData.setCashFlow(cf);
		cf.setQuarterlyData(qData);

		while (iterator.hasNext()) {
			Row currentRow = iterator.next();
			Cell currentCell = currentRow.getCell(2);

			if (currentCell == null) {
				continue;
			}

			if (currentCell.getCellTypeEnum() == CellType.STRING
					&& currentCell.getStringCellValue().trim().equals("CURRENT ASSETS")) {
				readCurrentAssets(iterator, bs);
			} else if (currentCell.getCellTypeEnum() == CellType.STRING
					&& currentCell.getStringCellValue().trim().equals("NON-CURRENT ASSETS")) {
				readNonCurrentAssets(iterator, bs);
			} else if (currentCell.getCellTypeEnum() == CellType.STRING
					&& currentCell.getStringCellValue().trim().equals("CURRENT LIABILITIES")) {
				readCurrentLiabilities(iterator, bs);
			} else if (currentCell.getCellTypeEnum() == CellType.STRING
					&& currentCell.getStringCellValue().trim().equals("NON-CURRENT LIABILITIES")) {
				readNonCurrentLiabilities(iterator, bs);
			} else if (currentCell.getCellTypeEnum() == CellType.STRING
					&& currentCell.getStringCellValue().trim().equals("Total equity")) {
				currentCell = currentRow.getCell(6);
				bs.setEquity(getNumericValue(currentCell));
			} else if (currentCell.getCellTypeEnum() == CellType.STRING
					&& currentCell.getStringCellValue().trim().equals("PROFIT (LOSS)")) {
				readIncomeStatement(iterator, is);
			} else if (currentCell.getCellTypeEnum() == CellType.STRING && currentCell.getStringCellValue().trim()
					.equals("CASH FLOWS FROM (USED IN) OPERATING ACTIVITIES")) {
				currentCell = currentRow.getCell(6);
				cf.setOperatingActivitiesCash(getNumericValue(currentCell));
			} else if (currentCell.getCellTypeEnum() == CellType.STRING && currentCell.getStringCellValue().trim()
					.equals("CASH FLOWS FROM (USED IN) INVESTING ACTIVITIES")) {
				currentCell = currentRow.getCell(6);
				cf.setInvestingActivitiesCash(getNumericValue(currentCell));
			} else if (currentCell.getCellTypeEnum() == CellType.STRING && currentCell.getStringCellValue().trim()
					.equals("CASH FLOWS FROM (USED IN) FINANCING ACTIVITIES")) {
				currentCell = currentRow.getCell(6);
				cf.setFinancingAtivitiesCash(getNumericValue(currentCell));
				readCashFlow(iterator, cf);
			}
		}
	}

	private void readCashFlow(Iterator<Row> iterator, CashFlow cf) throws Exception {
		int rowCounter = 0; // it is used for validating the file content

		while (iterator.hasNext()) {
			Row currentRow = iterator.next();
			Cell currentCell = currentRow.getCell(2);

			if (currentCell != null && currentCell.getStringCellValue().trim().equals("Repayments of borrowings")) {
				currentCell = currentRow.getCell(6);
				cf.setDebtPayments(getNumericValue(currentCell));
				validate(rowCounter, 20, currentCell.getStringCellValue());
			} else if (currentCell != null
					&& currentCell.getStringCellValue().trim().equals("Proceeds from borrowings")) {
				currentCell = currentRow.getCell(6);
				cf.setDebtIssued(getNumericValue(currentCell));
				validate(rowCounter, 15, currentCell.getStringCellValue());
			} else if (currentCell != null
					&& currentCell.getStringCellValue().trim().equals("Payments of Finance Lease Liabilities")) {
				currentCell = currentRow.getCell(6);
				cf.setDebtPayments(cf.getDebtPayments().add(getNumericValue(currentCell)));
				validate(rowCounter, 27, currentCell.getStringCellValue());
			} else if (currentCell != null && currentCell.getStringCellValue().trim().equals("Dividends Paid")) {
				currentCell = currentRow.getCell(6);
				cf.setDividendPayment(getNumericValue(currentCell));
				validate(rowCounter, 31, currentCell.getStringCellValue());
			} else if (currentCell != null && currentCell.getStringCellValue().trim().equals("Interest paid")) {
				currentCell = currentRow.getCell(6);
				cf.setInterestExpences(getNumericValue(currentCell));
				validate(rowCounter, 32, currentCell.getStringCellValue());
			} else if (currentCell != null && currentCell.getStringCellValue().trim().equals("Interest Received")) {
				currentCell = currentRow.getCell(6);
				cf.setInterestIncome(getNumericValue(currentCell));
				validate(rowCounter, 33, currentCell.getStringCellValue());
			} else if (currentCell != null && currentCell.getStringCellValue().trim()
					.equals("CASH AND CASH EQUIVALENTS AT THE END OF THE PERIOD")) {
				currentCell = currentRow.getCell(6);
				cf.setLiquidity(getNumericValue(currentCell));
				validate(rowCounter, 43, currentCell.getStringCellValue());
				return;
			}

			rowCounter++;
		}
	}

	private void readIncomeStatement(Iterator<Row> iterator, IncomeStatement is) throws Exception {
		int rowCounter = 0; // it is used for validating the file content

		while (iterator.hasNext()) {
			Row currentRow = iterator.next();
			Cell currentCell = currentRow.getCell(2);

			if (currentCell != null && currentCell.getStringCellValue().trim().equals("Revenue")) {
				currentCell = currentRow.getCell(7);
				is.setRevenue(getNumericValue(currentCell));
				validate(rowCounter, 0, currentCell.getStringCellValue());
			} else if (currentCell != null && currentCell.getStringCellValue().trim()
					.equals("GROSS PROFIT (LOSS) FROM COMMERCIAL OPERATIONS")) {
				currentCell = currentRow.getCell(7);
				is.setGrossProfit(getNumericValue(currentCell));
				validate(rowCounter, 2, currentCell.getStringCellValue());
			} else if (currentCell != null && currentCell.getStringCellValue().trim().equals("Interest Income")) {
				currentCell = currentRow.getCell(7);
				is.setInterestIncome(getNumericValue(currentCell));
				validate(rowCounter, 6, currentCell.getStringCellValue());
			} else if (currentCell != null && currentCell.getStringCellValue().trim().equals("Interest Expenses")) {
				currentCell = currentRow.getCell(7);
				is.setInterestExpenses(getNumericValue(currentCell));
				validate(rowCounter, 17, currentCell.getStringCellValue());
			} else if (currentCell != null && currentCell.getStringCellValue().trim().equals("Finance income")) {
				currentCell = currentRow.getCell(7);
				is.setFinanceIncome(getNumericValue(currentCell));
				validate(rowCounter, 47, currentCell.getStringCellValue());
			} else if (currentCell != null && currentCell.getStringCellValue().trim().equals("Finance costs")) {
				currentCell = currentRow.getCell(7);
				is.setFinanceCost(getNumericValue(currentCell));
				validate(rowCounter, 48, currentCell.getStringCellValue());
			} else if (currentCell != null
					&& currentCell.getStringCellValue().trim().equals("PROFIT (LOSS) FROM OPERATING ACTIVITIES")) {
				currentCell = currentRow.getCell(7);
				is.setOperatingProfit(getNumericValue(currentCell));
				validate(rowCounter, 35, currentCell.getStringCellValue());
			} else if (currentCell != null
					&& currentCell.getStringCellValue().trim().equals("Tax (Expense) Income, Continuing Operations")) {
				currentCell = currentRow.getCell(7);
				is.setTaxExpenses(getNumericValue(currentCell));
				validate(rowCounter, 51, currentCell.getStringCellValue());
			} else if (currentCell != null
					&& currentCell.getStringCellValue().trim().equals("PROFIT (LOSS) FROM CONTINUING OPERATIONS")) {
				currentCell = currentRow.getCell(7);
				is.setNetProfit(getNumericValue(currentCell));
				validate(rowCounter, 54, currentCell.getStringCellValue());
			} else if (currentCell != null && currentCell.getStringCellValue().trim().equals(
					"Other Components Of Other Comprehensive Income That Will Be Reclassified To Profit Or Loss, Tax Effect")) {
				return;
			}

			rowCounter++;
		}

	}

	private void readNonCurrentLiabilities(Iterator<Row> iterator, BalanceSheet bs) throws Exception {
		int rowCounter = 0; // it is used for validating the file content

		while (iterator.hasNext()) {
			Row currentRow = iterator.next();
			Cell currentCell = currentRow.getCell(2);

			if (currentCell != null && currentCell.getStringCellValue().trim().equals("Long Term Borrowings")) {
				currentCell = currentRow.getCell(6);
				bs.setLongTermDebt(getNumericValue(currentCell));
				validate(rowCounter, 0, currentCell.getStringCellValue());
			} else if (currentCell != null && currentCell.getStringCellValue().trim().equals("Trade Payables")) {
				currentCell = currentRow.getCell(6);
				bs.setTradePayables(bs.getTradePayables().add(getNumericValue(currentCell)));
				validate(rowCounter, 15, currentCell.getStringCellValue());
			} else if (currentCell != null && currentCell.getStringCellValue().trim().equals("Other Payables")) {
				currentCell = currentRow.getCell(6);
				bs.setOtherPayables(bs.getOtherPayables().add(getNumericValue(currentCell)));
				validate(rowCounter, 22, currentCell.getStringCellValue());
			} else if (currentCell != null && currentCell.getStringCellValue().trim().equals("Total liabilities")) {
				currentCell = currentRow.getCell(6);
				bs.setTotalLiabilities(getNumericValue(currentCell));
				validate(rowCounter, 48, currentCell.getStringCellValue());
				return;
			}

			rowCounter++;
		}
	}

	private void readCurrentLiabilities(Iterator<Row> iterator, BalanceSheet bs) throws Exception {
		int rowCounter = 0; // it is used for validating the file content

		while (iterator.hasNext()) {
			Row currentRow = iterator.next();
			Cell currentCell = currentRow.getCell(2);

			if (currentCell != null && currentCell.getStringCellValue().trim().equals("Current Borrowings")) {
				currentCell = currentRow.getCell(6);
				bs.setShortTermDebt(getNumericValue(currentCell));
				validate(rowCounter, 0, currentCell.getStringCellValue());
			} else if (currentCell != null
					&& currentCell.getStringCellValue().trim().equals("Current Portion of Non-current Borrowings")) {
				currentCell = currentRow.getCell(6);
				bs.setShortTermDebt(bs.getShortTermDebt().add(getNumericValue(currentCell)));
				validate(rowCounter, 11, currentCell.getStringCellValue());
			} else if (currentCell != null && currentCell.getStringCellValue().trim().equals("Trade Payables")) {
				currentCell = currentRow.getCell(6);
				bs.setTradePayables(getNumericValue(currentCell));
				validate(rowCounter, 26, currentCell.getStringCellValue());
			} else if (currentCell != null && currentCell.getStringCellValue().trim().equals("Other Payables")) {
				currentCell = currentRow.getCell(6);
				bs.setOtherPayables(getNumericValue(currentCell));
				validate(rowCounter, 33, currentCell.getStringCellValue());
			} else if (currentCell != null
					&& currentCell.getStringCellValue().trim().equals("Total current liabilities")) {
				currentCell = currentRow.getCell(6);
				bs.setTotalCurrentLiabilities(getNumericValue(currentCell));
				validate(rowCounter, 58, currentCell.getStringCellValue());
				return;
			}

			rowCounter++;
		}
	}

	private void readNonCurrentAssets(Iterator<Row> iterator, BalanceSheet bs) throws Exception {
		int rowCounter = 0; // it is used for validating the file content

		while (iterator.hasNext()) {
			Row currentRow = iterator.next();
			Cell currentCell = currentRow.getCell(2);

			if (currentCell != null
					&& currentCell.getStringCellValue().trim().equals("Property, plant and equipment")) {
				currentCell = currentRow.getCell(6);
				bs.setPropertyPlantEquipment(getNumericValue(currentCell));
				validate(rowCounter, 37, currentCell.getStringCellValue());
			} else if (currentCell != null && currentCell.getStringCellValue().trim().equals("Trade Receivables")) {
				currentCell = currentRow.getCell(6);
				bs.setTradeReceivables(bs.getTradeReceivables().add(getNumericValue(currentCell)));
				validate(rowCounter, 16, currentCell.getStringCellValue());
			} else if (currentCell != null && currentCell.getStringCellValue().trim().equals("Other Receivables")) {
				currentCell = currentRow.getCell(6);
				bs.setOtherReceivables(bs.getOtherReceivables().add(getNumericValue(currentCell)));
				validate(rowCounter, 22, currentCell.getStringCellValue());
			} else if (currentCell != null && currentCell.getStringCellValue().trim().equals("Inventories")) {
				currentCell = currentRow.getCell(6);
				bs.setInventories(bs.getInventories().add(getNumericValue(currentCell)));
				validate(rowCounter, 32, currentCell.getStringCellValue());
			} else if (currentCell != null && currentCell.getStringCellValue().trim().equals("Prepayments")) {
				currentCell = currentRow.getCell(6);
				bs.setPrepayments(bs.getPrepayments().add(getNumericValue(currentCell)));
				validate(rowCounter, 60, currentCell.getStringCellValue());
			} else if (currentCell != null && currentCell.getStringCellValue().trim().equals("Total assets")) {
				currentCell = currentRow.getCell(6);
				bs.setTotalAssets(getNumericValue(currentCell));
				validate(rowCounter, 70, currentCell.getStringCellValue());
				return;
			}

			rowCounter++;
		}

	}

	private void readCurrentAssets(Iterator<Row> iterator, BalanceSheet bs) throws Exception {
		int rowCounter = 0; // it is used for validating the file content

		while (iterator.hasNext()) {
			Row currentRow = iterator.next();
			Cell currentCell = currentRow.getCell(2);

			if (currentCell != null && currentCell.getStringCellValue().trim().equals("Cash and cash equivalents")) {
				currentCell = currentRow.getCell(6);
				bs.setCash(getNumericValue(currentCell));
				validate(rowCounter, 0, currentCell.getStringCellValue());
			} else if (currentCell != null && currentCell.getStringCellValue().trim().equals("Trade Receivables")) {
				currentCell = currentRow.getCell(6);
				bs.setTradeReceivables(getNumericValue(currentCell));
				validate(rowCounter, 18, currentCell.getStringCellValue());
			} else if (currentCell != null && currentCell.getStringCellValue().trim().equals("Other Receivables")) {
				currentCell = currentRow.getCell(6);
				bs.setOtherReceivables(getNumericValue(currentCell));
				validate(rowCounter, 25, currentCell.getStringCellValue());
			} else if (currentCell != null && currentCell.getStringCellValue().trim().equals("Inventories")) {
				currentCell = currentRow.getCell(6);
				bs.setInventories(getNumericValue(currentCell));
				validate(rowCounter, 35, currentCell.getStringCellValue());
			} else if (currentCell != null && currentCell.getStringCellValue().trim().equals("Prepayments")) {
				currentCell = currentRow.getCell(6);
				bs.setPrepayments(getNumericValue(currentCell));
				validate(rowCounter, 38, currentCell.getStringCellValue());
			} else if (currentCell != null && currentCell.getStringCellValue().trim().equals("Total current assets")) {
				currentCell = currentRow.getCell(6);
				bs.setTotalCurrentAssets(getNumericValue(currentCell));
				validate(rowCounter, 50, currentCell.getStringCellValue());
				return;
			}

			rowCounter++;
		}
	}

	private void validate(int rowCounter, int i, String fieldName) throws Exception {
		if (rowCounter != i) {
			throw new Exception("Invalid document error, related field is " + fieldName);
		}
	}

	private BigDecimal getNumericValue(Cell currentCell) {
		double value = 0;
		boolean valueSet = false;

		if (currentCell.getCellTypeEnum() == CellType.NUMERIC || currentCell.getCellTypeEnum() == CellType.STRING) {
			currentCell.setCellType(Cell.CELL_TYPE_STRING);
			value = Double.parseDouble(currentCell.getStringCellValue().replace(".", ""));
			valueSet = true;
		}

		if (valueSet) {
			return new BigDecimal(value).multiply(multiplier).setScale(0, BigDecimal.ROUND_HALF_UP);
		}

		return new BigDecimal(0);
	}

	private void parseSheet(Iterator<Row> iterator, QuarterlyData qData) {
		BalanceSheet bs = new BalanceSheet();
		IncomeStatement is = new IncomeStatement();
		CashFlow cf = new CashFlow();

		qData.setBalanceSheet(bs);
		qData.setIncomeStatement(is);
		qData.setCashFlow(cf);

		boolean cashFinancialActivitiesReading = false;
		boolean balanceSheetReading = true;
		boolean incomeStatementReading = false;

		while (iterator.hasNext()) {
			int cellCounter = 1;
			Row currentRow = iterator.next();
			Iterator<Cell> cellIterator = currentRow.iterator();

			while (cellIterator.hasNext()) {
				Cell currentCell = cellIterator.next();

				if (cellCounter == 3 && currentCell.getCellTypeEnum() == CellType.STRING) {
					String currentElementKey = currentCell.getStringCellValue().toLowerCase().trim();

					if (balanceSheetReading) {
						if (currentElementKey.equals("cash and cash equivalents")) {
							bs.setCash(getValue(bs.getCash(), cellIterator, 4));
						} else if (currentElementKey.equals("total current assets")) {
							bs.setTotalCurrentAssets(getValue(null, cellIterator, 4));
						} else if (currentElementKey.equals("total assets")) {
							bs.setTotalAssets(getValue(null, cellIterator, 4));
						} else if (currentElementKey.equals("current borrowings")) {
							bs.setShortTermDebt(getValue(null, cellIterator, 4));
						} else if (currentElementKey.equals("total current liabilities")) {
							bs.setTotalCurrentLiabilities(getValue(null, cellIterator, 4));
						} else if (currentElementKey.equals("total liabilities")) {
							bs.setTotalLiabilities(getValue(null, cellIterator, 4));
						} else if (currentElementKey.equals("long term borrowings")) {
							bs.setLongTermDebt(getValue(null, cellIterator, 4));
						} else if (currentElementKey.equals("total equity")) {
							bs.setEquity(getValue(null, cellIterator, 4));
							incomeStatementReading = true;
							balanceSheetReading = false;
						}
					}

					// income statement fields
					if (incomeStatementReading) {
						if (currentElementKey.equals("revenue")) {
							is.setRevenue(getValue(null, cellIterator, 5));
						} else if (currentElementKey.equals("gross profit (loss)")) {
							is.setGrossProfit(getValue(null, cellIterator, 5));
						} else if (currentElementKey.equals("profit (loss) from operating activities")) {
							is.setOperatingProfit(getValue(null, cellIterator, 5));
						} else if (currentElementKey.equals("finance income")) {
							is.setFinanceIncome(getValue(null, cellIterator, 5));
						} else if (currentElementKey.equals("finance costs")) {
							is.setFinanceCost(getValue(null, cellIterator, 5));
						} else if (currentElementKey.equals("tax (expense) income, continuing operations")) {
							is.setTaxExpenses(getValue(null, cellIterator, 5));
						} else if (currentElementKey.equals("profit (loss)")) {
							is.setNetProfit(getValue(null, cellIterator, 5));
						} else if (currentElementKey.equals("cost of sales")) {
							is.setCostOfSales(getValue(null, cellIterator, 5));
						} else if (currentElementKey.equals("interest expenses")) {
							is.setInterestExpenses(getValue(null, cellIterator, 5));
						} else if (currentElementKey.equals("interest income")) {
							is.setInterestIncome(getValue(null, cellIterator, 5));
						}
					}

					// cash flow
					if (currentElementKey.equals("cash flows from (used in) operating activities")) {
						cf.setOperatingActivitiesCash(getValue(null, cellIterator, 4));
						incomeStatementReading = false;
					} else if (currentElementKey.equals("cash flows from (used in) investing activities")) {
						cf.setInvestingActivitiesCash(getValue(null, cellIterator, 4));
					} else if (currentElementKey.equals("cash flows from (used in) financing activities")) {
						cf.setFinancingAtivitiesCash(getValue(null, cellIterator, 4));
						cashFinancialActivitiesReading = true;
					}

					if (cashFinancialActivitiesReading) {
						if (currentElementKey.equals("interest paid")) {
							cf.setInterestExpences(getValue(null, cellIterator, 4));
						} else if (currentElementKey.equals("interest received")) {
							cf.setInterestIncome(getValue(null, cellIterator, 4));
						} else if (currentElementKey.equals("dividends paid")) {
							cf.setDividendPayment(getValue(null, cellIterator, 4));
						}
					}

					if (currentElementKey.equals("cash and cash equivalents at the end of the period")) {
						cf.setLiquidity(getValue(null, cellIterator, 4));
						cashFinancialActivitiesReading = true;
					}
				}

				cellCounter++;
			}
		}
	}

	private BigDecimal getValue(BigDecimal currentValue, Iterator<Cell> cellIterator, int moveSteps) {
		double value = 0;
		boolean valueSet = false;
		DataFormatter objDefaultFormat = new DataFormatter();

		while (cellIterator.hasNext()) {
			Cell currentCell = cellIterator.next();

			if ((--moveSteps) == 0 && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
				currentCell.setCellType(Cell.CELL_TYPE_STRING);
				value = Double.parseDouble(currentCell.getStringCellValue().replace(".", ""));
				valueSet = true;
			}
		}

		if (valueSet) {
			if (currentValue == null) {
				return new BigDecimal(value).multiply(multiplier).setScale(0, BigDecimal.ROUND_HALF_UP);
			} else {
				return new BigDecimal(value).multiply(multiplier).add(currentValue).setScale(0,
						BigDecimal.ROUND_HALF_UP);
			}
		}

		return currentValue;
	}

}
