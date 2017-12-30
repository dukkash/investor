package com.dukkash.investor.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dukkash.investor.model.BalanceSheet;
import com.dukkash.investor.model.CashFlow;
import com.dukkash.investor.model.Company;
import com.dukkash.investor.model.IncomeStatement;
import com.dukkash.investor.model.QuarterlyData;
import com.dukkash.investor.service.CompanyService;
import com.dukkash.investor.service.QuarterlyDataService;

@Component
public class KAPCustomReportReader {
	private static final Object KEY_MULTIPLIER = "multiplier";
	private static final Object KEY_TOTAL_SHARES = "total shares";
	private static final Object KEY_TICKER_SYMBOL = "ticker symbol";
	private static final Object KEY_PERIODS = "periods";

	private BigDecimal multiplier;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private QuarterlyDataService quarterlyDataService;

	private List<QuarterlyData> quarters;

	public void parseQuarterlyFinancialReport(String fileName) {

		try (FileInputStream excelFile = new FileInputStream(new File(fileName))) {		
			@SuppressWarnings("resource")
			Iterator<Row> iterator =  new HSSFWorkbook(excelFile).getSheet("Sheet1").iterator();
			quarters = getQuarters(iterator);

			readFinancialReport(iterator);
			validateData();
			normalizeEarnings();
	//		quarterlyDataService.save(quarters);

			//System.out.println(quarters);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	//	System.out.println(quarters);
	}

	private void validateData() throws Exception {
		for (QuarterlyData qd : quarters) {
			if(qd.getBalanceSheet().getTotalAssets() == null) {
				throw new Exception("Missing total assets for " + qd.getName());
			} else if(qd.getBalanceSheet().getTotalCurrentAssets() == null) {
				throw new Exception("Missing getTotalCurrentAssets for " + qd.getName());
			} else if(qd.getBalanceSheet().getTotalLiabilities() == null) {
				throw new Exception("Missing getTotalLiabilities for " + qd.getName());
			} else if(qd.getBalanceSheet().getEquity() == null) {
				throw new Exception("Missing getEquity for " + qd.getName());
			} else if(qd.getCashFlow().getInvestingActivitiesCash() == null) {
				throw new Exception("Missing getInvestingActivitiesCash for " + qd.getName());
			} else if(qd.getIncomeStatement().getRevenue() == null) {
				throw new Exception("Missing getRevenue for " + qd.getName());
			}
		}
	}

	private void normalizeEarnings() {
		Map<String, List<QuarterlyData>> years = new HashMap<>();

		for (QuarterlyData qd : quarters) {
			String quarterYear = qd.getName().substring(0, 4);
			List<QuarterlyData> temp = years.get(quarterYear);

			if (temp == null) {
				temp = new ArrayList<>();
				temp.add(qd);
				years.put(quarterYear, temp);
			} else {
				temp.add(qd);
			}
		}

		for (String data : years.keySet()) {
			List<QuarterlyData> temp = years.get(data);
			temp = temp.stream().sorted((o1, o2) -> o2.getName().compareTo(o1.getName())).collect(Collectors.toList());
			years.put(data, temp);
		}

		for (String data : years.keySet()) {
			List<QuarterlyData> temp = years.get(data);

			if (temp.size() == 1) {
				continue;
			}

			for (int i = 1; i < temp.size(); i++) {
				QuarterlyData upper = temp.get(i - 1);
				QuarterlyData lower = temp.get(i);

				updateQuarterEarning(upper, lower);
			}
		}
		System.out.println(23);
	}

	private void updateQuarterEarning(QuarterlyData upper, QuarterlyData lower) {
		IncomeStatement ui = upper.getIncomeStatement();
		IncomeStatement li = lower.getIncomeStatement();

		if (ui.getCostOfSales() != null && li.getCostOfSales() != null) {
			ui.setCostOfSales(ui.getCostOfSales().subtract(li.getCostOfSales()));
		}

		if (ui.getFinanceCost() != null && li.getFinanceCost() != null) {
			ui.setFinanceCost(ui.getFinanceCost().subtract(li.getFinanceCost()));
		}

		if (ui.getFinanceIncome() != null && li.getFinanceIncome() != null) {
			ui.setFinanceIncome(ui.getFinanceIncome().subtract(li.getFinanceIncome()));
		}

		if (ui.getGrossProfit() != null && li.getGrossProfit() != null) {
			ui.setGrossProfit(ui.getGrossProfit().subtract(li.getGrossProfit()));
		}

		if (ui.getNetProfit() != null && li.getNetProfit() != null) {
			ui.setNetProfit(ui.getNetProfit().subtract(li.getNetProfit()));
		}

		if (ui.getOperatingProfit() != null && li.getOperatingProfit() != null) {
			ui.setOperatingProfit(ui.getOperatingProfit().subtract(li.getOperatingProfit()));
		}

		if (ui.getRevenue() != null && li.getRevenue() != null) {
			ui.setRevenue(ui.getRevenue().subtract(li.getRevenue()));
		}

		if (ui.getTaxExpenses() != null && li.getTaxExpenses() != null) {
			ui.setTaxExpenses(ui.getTaxExpenses().subtract(li.getTaxExpenses()));
		}

		if (ui.getInterestExpenses() != null && li.getInterestExpenses() != null) {
			ui.setInterestExpenses(ui.getInterestExpenses().subtract(li.getInterestExpenses()));
		}

		if (ui.getInterestIncome() != null && li.getInterestIncome() != null) {
			ui.setInterestIncome(ui.getInterestIncome().subtract(li.getInterestIncome()));
		}

		if (ui.getNonInterestIncome() != null && li.getNonInterestIncome() != null) {
			ui.setNonInterestIncome(ui.getNonInterestIncome().subtract(li.getNonInterestIncome()));
		}
	}

	private List<QuarterlyData> getQuarters(Iterator<Row> iterator) throws Exception {
		List<QuarterlyData> quarters = new ArrayList<>();
		Company company = null;
		BigDecimal totalShares = null;

		if (iterator.hasNext()) {
			Row currentRow = iterator.next();
			String symbol = currentRow.getCell(0).getStringCellValue().trim();

			if (symbol.equals(KEY_TICKER_SYMBOL)) {
				symbol = currentRow.getCell(1).getStringCellValue().trim();
			} else {
				throw new Exception("Missing ticker symbol key name.");
			}

			if (symbol == null || symbol.isEmpty()) {
				throw new Exception("Missing ticker symbol name.");
			}

			company = companyService.getCompanyByTickerSymbol(symbol);

			if (company == null) {
				throw new Exception(String.format("Missing company with %s symbol name.", symbol));
			}
		}

		if (iterator.hasNext()) {
			Row currentRow = iterator.next();

			if (currentRow.getCell(0).getStringCellValue().trim().equals(KEY_MULTIPLIER)) {
				Cell currentCell = currentRow.getCell(1);
				currentCell.setCellType(Cell.CELL_TYPE_STRING);
				multiplier = new BigDecimal(currentCell.getStringCellValue().replace(".", "").replace(",", "").trim());
			} else {
				throw new Exception("Missing multiplier for %s symbol.");
			}
		}

		if (iterator.hasNext()) {
			Row currentRow = iterator.next();

			if (currentRow.getCell(0).getStringCellValue().trim().equals(KEY_TOTAL_SHARES)) {
				Cell currentCell = currentRow.getCell(1);
				currentCell.setCellType(Cell.CELL_TYPE_STRING);
				totalShares = new BigDecimal(currentCell.getStringCellValue().replace(".", "").replace(",", "").trim());
			} else {
				throw new Exception("Missing shares number.");
			}
		}

		if (iterator.hasNext()) {
			Row currentRow = iterator.next();
			Iterator<Cell> cellIterator = currentRow.cellIterator();

			if (cellIterator.hasNext() && !cellIterator.next().getStringCellValue().trim().equals(KEY_PERIODS)) {
				throw new Exception("Missing periods dates.");
			}

			while (cellIterator.hasNext()) {
				QuarterlyData qData = new QuarterlyData();
				BalanceSheet bs = new BalanceSheet();
				IncomeStatement is = new IncomeStatement();
				CashFlow cf = new CashFlow();
				qData.setCompany(company);
				qData.setName(getQuarterNameFromDate(cellIterator.next().getStringCellValue()));
				qData.setBalanceSheet(bs);				
				qData.setIncomeStatement(is);
				qData.setCashFlow(cf);
				cf.setQuarterlyData(qData);
				is.setQuarterlyData(qData);
				bs.setQuarterlyData(qData);				
				qData.setSharesOutstanding(totalShares);

				quarters.add(qData);
			}
		}

		return quarters;
	}

	private String getQuarterNameFromDate(String dateStr) {
		String[] dateParts = dateStr.split("\\.");
		return dateParts[2] + "-Q" + (Integer.valueOf(dateParts[1]).intValue() / 3);
	}

	private void readFinancialReport(Iterator<Row> iterator) {
		int rowCounter = 0;
		int index = 0;

		for (QuarterlyData q : quarters) {
			q.getBalanceSheet().setTotalDebt(new BigDecimal(0));
			q.getCashFlow().setDebtPayments(new BigDecimal(0));
			q.getCashFlow().setDebtIssued(new BigDecimal(0));
			q.getBalanceSheet().setPrepayments(new BigDecimal(0));
			q.getBalanceSheet().setOtherReceivables(new BigDecimal(0));
		}

		while (iterator.hasNext()) {
			Row currentRow = iterator.next();
			Iterator<Cell> cellIter = currentRow.cellIterator();
			String currentElementKey = cellIter.next().getStringCellValue().trim().toLowerCase();
			index = 0;

			if (currentElementKey.equals("cash and cash equivalents") && rowCounter == 1) {
				while (cellIter.hasNext() && index < quarters.size()) {
					quarters.get(index).getBalanceSheet().setCash(getValue(cellIter.next()));
					quarters.get(index).getCashFlow().setLiquidity(quarters.get(index).getBalanceSheet().getCash());
					index++;
				}
			} else if (currentElementKey.equals("trade receivables") && rowCounter == 19) {
				while (cellIter.hasNext() && index < quarters.size()) {
					quarters.get(index++).getBalanceSheet().setTradeReceivables(getValue(cellIter.next()));
				}
			} else if (currentElementKey.equals("other receivables") && rowCounter == 26) {
				while (cellIter.hasNext() && index < quarters.size()) {
					quarters.get(index++).getBalanceSheet().setOtherReceivables(getValue(cellIter.next()));
				}
			} else if (currentElementKey.equals("inventories") && rowCounter == 36) {
				while (cellIter.hasNext() && index < quarters.size()) {
					quarters.get(index++).getBalanceSheet().setInventories(getValue(cellIter.next()));
				}
			} else if (currentElementKey.equals("prepayments") && rowCounter == 39) {
				while (cellIter.hasNext() && index < quarters.size()) {
					quarters.get(index++).getBalanceSheet().setPrepayments(getValue(cellIter.next()));
				}
			} else if (currentElementKey.equals("total current assets") && rowCounter == 51) {
				while (cellIter.hasNext() && index < quarters.size()) {
					quarters.get(index++).getBalanceSheet().setTotalCurrentAssets(getValue(cellIter.next()));
				}
			} else if (currentElementKey.equals("other receivables") && rowCounter == 69) {
				while (cellIter.hasNext() && index < quarters.size()) {
					quarters.get(index).getBalanceSheet().setOtherReceivables(
							quarters.get(index).getBalanceSheet().getOtherReceivables().add(getValue(cellIter.next())));
					index++;
				}
			} else if (currentElementKey.equals("other receivables") && rowCounter == 75) {
				while (cellIter.hasNext() && index < quarters.size()) {
					quarters.get(index).getBalanceSheet().setOtherReceivables(
							quarters.get(index).getBalanceSheet().getOtherReceivables().add(getValue(cellIter.next())));
					index++;
				}
			} else if (currentElementKey.equals("property, plant and equipment") && rowCounter == 90) {
				while (cellIter.hasNext() && index < quarters.size()) {
					quarters.get(index++).getBalanceSheet().setPropertyPlantEquipment(getValue(cellIter.next()));
				}
			} else if (currentElementKey.equals("intangible assets and goodwill") && rowCounter == 103) {
				while (cellIter.hasNext() && index < quarters.size()) {
					quarters.get(index++).getBalanceSheet().setIntangibleAssets(getValue(cellIter.next()));
				}
			} else if (currentElementKey.equals("prepayments") && rowCounter == 113) {
				while (cellIter.hasNext() && index < quarters.size()) {
					quarters.get(index).getBalanceSheet().setPrepayments(
							quarters.get(index).getBalanceSheet().getPrepayments().add(getValue(cellIter.next())));
					index++;
				}
			} else if (currentElementKey.equals("total assets") && rowCounter == 123) {
				while (cellIter.hasNext() && index < quarters.size()) {
					quarters.get(index++).getBalanceSheet().setTotalAssets(getValue(cellIter.next()));
				}
			} else if (currentElementKey.equals("current borrowings") && rowCounter == 126) {
				while (cellIter.hasNext() && index < quarters.size()) {
					quarters.get(index).getBalanceSheet().setShortTermDebt(getValue(cellIter.next()));
					quarters.get(index).getBalanceSheet().setTotalDebt(quarters.get(index).getBalanceSheet()
							.getTotalDebt().add(quarters.get(index).getBalanceSheet().getShortTermDebt()));
					index++;
				}
			} else if (currentElementKey.equals("trade payables") && rowCounter == 152) {
				while (cellIter.hasNext() && index < quarters.size()) {
					quarters.get(index).getBalanceSheet().setTradePayables(getValue(cellIter.next()));
					quarters.get(index).getBalanceSheet().setTotalDebt(quarters.get(index).getBalanceSheet()
							.getTotalDebt().add(quarters.get(index).getBalanceSheet().getTradePayables()));
					index++;
				}
			} else if (currentElementKey.equals("other payables") && rowCounter == 159) {
				while (cellIter.hasNext() && index < quarters.size()) {
					quarters.get(index).getBalanceSheet().setOtherPayables(getValue(cellIter.next()));
					quarters.get(index).getBalanceSheet().setTotalDebt(quarters.get(index).getBalanceSheet()
							.getTotalDebt().add(quarters.get(index).getBalanceSheet().getOtherPayables()));
					index++;
				}
			} else if (currentElementKey.equals("total current liabilities") && rowCounter == 184) {
				while (cellIter.hasNext() && index < quarters.size()) {
					quarters.get(index++).getBalanceSheet().setTotalCurrentLiabilities(getValue(cellIter.next()));
				}
			} else if (currentElementKey.equals("long term borrowings") && rowCounter == 186) {
				while (cellIter.hasNext() && index < quarters.size()) {
					quarters.get(index).getBalanceSheet().setLongTermDebt(getValue(cellIter.next()));
					quarters.get(index).getBalanceSheet().setTotalDebt(quarters.get(index).getBalanceSheet()
							.getTotalDebt().add(quarters.get(index).getBalanceSheet().getLongTermDebt()));
					index++;
				}
			} else if (currentElementKey.equals("trade payables") && rowCounter == 201) {
				while (cellIter.hasNext() && index < quarters.size()) {
					quarters.get(index).getBalanceSheet().setTotalDebt(
							quarters.get(index).getBalanceSheet().getTotalDebt().add(getValue(cellIter.next())));
					index++;
				}
			} else if (currentElementKey.equals("other payables") && rowCounter == 218) {
				while (cellIter.hasNext() && index < quarters.size()) {
					quarters.get(index).getBalanceSheet().setTotalDebt(
							quarters.get(index).getBalanceSheet().getTotalDebt().add(getValue(cellIter.next())));
					index++;
				}
			} else if (currentElementKey.equals("total liabilities") && rowCounter == 234) {
				while (cellIter.hasNext() && index < quarters.size()) {
					quarters.get(index++).getBalanceSheet().setTotalLiabilities(getValue(cellIter.next()));
				}
			} else if (currentElementKey.equals("total equity") && rowCounter == 287) {
				while (cellIter.hasNext() && index < quarters.size()) {
					quarters.get(index++).getBalanceSheet().setEquity(getValue(cellIter.next()));
				}
			} else if (currentElementKey.equals("revenue") && rowCounter == 291) {
				while (cellIter.hasNext() && index < quarters.size()) {
					quarters.get(index++).getIncomeStatement().setRevenue(getValue(cellIter.next()));
				}
			} else if (currentElementKey.equals("cost of sales") && rowCounter == 292) {
				while (cellIter.hasNext() && index < quarters.size()) {
					quarters.get(index++).getIncomeStatement().setCostOfSales(getValue(cellIter.next()));
				}
			} else if (currentElementKey.equals("gross profit (loss) from commercial operations")
					&& rowCounter == 293) {
				while (cellIter.hasNext() && index < quarters.size()) {
					quarters.get(index++).getIncomeStatement().setGrossProfit(getValue(cellIter.next()));
				}
			} else if (currentElementKey.equals("profit (loss) from operating activities") && rowCounter == 326) {
				while (cellIter.hasNext() && index < quarters.size()) {
					quarters.get(index++).getIncomeStatement().setOperatingProfit(getValue(cellIter.next()));
				}
			} else if (currentElementKey.equals("finance income") && rowCounter == 338) {
				while (cellIter.hasNext() && index < quarters.size()) {
					quarters.get(index++).getIncomeStatement().setFinanceIncome(getValue(cellIter.next()));
				}
			} else if (currentElementKey.equals("finance costs") && rowCounter == 339) {
				while (cellIter.hasNext() && index < quarters.size()) {
					quarters.get(index++).getIncomeStatement().setFinanceCost(getValue(cellIter.next()));
				}
			} else if (currentElementKey.equals("tax (expense) income, continuing operations") && rowCounter == 342) {
				while (cellIter.hasNext() && index < quarters.size()) {
					quarters.get(index++).getIncomeStatement().setTaxExpenses(getValue(cellIter.next()));
				}
			} else if (currentElementKey.equals("profit (loss)") && rowCounter == 347) {
				while (cellIter.hasNext() && index < quarters.size()) {
					quarters.get(index++).getIncomeStatement().setNetProfit(getValue(cellIter.next()));
				}
			} else if (currentElementKey.equals("cash flows from (used in) operating activities")
					&& rowCounter == 356) {
				while (cellIter.hasNext() && index < quarters.size()) {
					quarters.get(index++).getCashFlow().setOperatingActivitiesCash(getValue(cellIter.next()));
				}
			} else if (currentElementKey.equals("cash flows from (used in) investing activities")
					&& rowCounter == 467) {
				while (cellIter.hasNext() && index < quarters.size()) {
					quarters.get(index++).getCashFlow().setInvestingActivitiesCash(getValue(cellIter.next()));
				}
			} else if (currentElementKey.equals("cash flows from (used in) financing activities")
					&& rowCounter == 508) {
				while (cellIter.hasNext() && index < quarters.size()) {
					quarters.get(index++).getCashFlow().setFinancingAtivitiesCash(getValue(cellIter.next()));
				}
			} else if (currentElementKey.equals("adjustments for depreciation and amortisation expense")
					&& rowCounter == 361) {
				while (cellIter.hasNext() && index < quarters.size()) {
					quarters.get(index++).getCashFlow().setDepAndAmrtExpenses(getValue(cellIter.next()));
				}
			} else if (currentElementKey.equals("proceeds from borrowings") && rowCounter == 524) {
				while (cellIter.hasNext() && index < quarters.size()) {
					quarters.get(index).getCashFlow().setDebtIssued(
							quarters.get(index).getCashFlow().getDebtIssued().add(getValue(cellIter.next())));
					index++;
				}
			} else if (currentElementKey.equals("proceeds from loans") && rowCounter == 525) {
				while (cellIter.hasNext() && index < quarters.size()) {
					quarters.get(index).getCashFlow().setDebtIssued(
							quarters.get(index).getCashFlow().getDebtIssued().add(getValue(cellIter.next())));
					index++;
				}
			} else if (currentElementKey.equals("repayments of borrowings") && rowCounter == 529) {
				while (cellIter.hasNext() && index < quarters.size()) {
					quarters.get(index).getCashFlow().setDebtPayments(
							quarters.get(index).getCashFlow().getDebtPayments().add(getValue(cellIter.next())));
					index++;
				}
			} else if (currentElementKey.equals("loan repayments") && rowCounter == 530) {
				while (cellIter.hasNext() && index < quarters.size()) {
					quarters.get(index).getCashFlow().setDebtPayments(
							quarters.get(index).getCashFlow().getDebtPayments().add(getValue(cellIter.next())));
					index++;
				}
			} else if (currentElementKey.equals("dividends paid") && rowCounter == 540) {
				while (cellIter.hasNext() && index < quarters.size()) {
					quarters.get(index++).getCashFlow().setDividendPayment(getValue(cellIter.next()));
				}
			} else if (currentElementKey.equals("interest paid") && rowCounter == 541) {
				while (cellIter.hasNext() && index < quarters.size()) {
					quarters.get(index++).getCashFlow().setInterestExpences(getValue(cellIter.next()));
				}
			} else if (currentElementKey.equals("interest received") && rowCounter == 542) {
				while (cellIter.hasNext() && index < quarters.size()) {
					quarters.get(index++).getCashFlow().setInterestIncome(getValue(cellIter.next()));
				}
			}

			rowCounter++;
		}
	}

	private BigDecimal getValue(Cell cell) {
		cell.setCellType(Cell.CELL_TYPE_STRING);
		String cellValue = cell.getStringCellValue().replace(".", "");

		if (cellValue == null || cellValue.isEmpty()) {
			return new BigDecimal(0);
		}

		return new BigDecimal(cellValue.trim()).multiply(multiplier).setScale(0, BigDecimal.ROUND_HALF_UP);
	}

}
