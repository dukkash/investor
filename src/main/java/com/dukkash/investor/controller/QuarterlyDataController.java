package com.dukkash.investor.controller;

import java.math.BigDecimal;

import javax.validation.ValidationException;

import com.dukkash.investor.exception.InvestorException;
import com.dukkash.investor.model.*;
import com.dukkash.investor.ui.model.ShortQuarter;
import com.dukkash.investor.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dukkash.investor.service.CompanyService;
import com.dukkash.investor.service.QuarterlyDataService;
import com.dukkash.investor.ui.model.QuarterlyDataModel;
import com.dukkash.investor.validator.QuarterlyDataValidator;

@RestController
@RequestMapping("/quarterlyData")
public class QuarterlyDataController {

	@Autowired
	private CompanyService companyService;

	@Autowired
	private QuarterlyDataService quarterlyDataService;

    @RequestMapping(value = "/addShortQuarterlyData", method = RequestMethod.POST)
    public ResponseEntity<String> addShortQuarterlyData(@RequestBody ShortQuarter shortQuarter) {
        QuarterlyData qData = new QuarterlyData();

        try {
            Company company = companyService.getCompanyByTickerSymbol(shortQuarter.getTickerSymbol().trim().toUpperCase());

            if(company == null) {
                throw new InvestorException("Company missing. " +  shortQuarter.getTickerSymbol());
            }

            qData.setCompany(company);
            qData.setName(shortQuarter.getName());
            qData.setNotes(shortQuarter.getNotes());
            qData.setSharesOutstanding(shortQuarter.getShares());

            BalanceSheet bSheet = new BalanceSheet();
            qData.setBalanceSheet(bSheet);
            bSheet.setEquity(shortQuarter.getEquity());
            bSheet.setTotalAssets(shortQuarter.getTotalAssets());
            bSheet.setTotalLiabilities(shortQuarter.getTotalLiabilities());
            bSheet.setQuarterlyData(qData);

            IncomeStatement iStat = new IncomeStatement();
            qData.setIncomeStatement(iStat);
            iStat.setRevenue(shortQuarter.getRevenue());
            iStat.setNetProfit(shortQuarter.getNetProfit());
            iStat.setQuarterlyData(qData);
        } catch (InvestorException e) {
            return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        quarterlyDataService.save(qData);

        return new ResponseEntity<>(HttpStatus.OK);
    }

	@RequestMapping(value = "/addQuarterlyData", method = RequestMethod.POST)
	public ResponseEntity<String> addQuarterlyData(@RequestBody QuarterlyDataModel model) {
		QuarterlyData qData = new QuarterlyData();

        try {
            populateQuarterlyData(model, qData);

            BalanceSheet bSheet = new BalanceSheet();
            qData.setBalanceSheet(bSheet);
            populateBalanceSheet(model, bSheet);
            bSheet.setQuarterlyData(qData);

            IncomeStatement iStat = new IncomeStatement();
            qData.setIncomeStatement(iStat);
            iStat.setQuarterlyData(qData);
            populateIncomeStatement(model, iStat);

            CashFlow cFlow = new CashFlow();
            qData.setCashFlow(cFlow);
            cFlow.setQuarterlyData(qData);
            populateCashFlow(model, cFlow);
        } catch (InvestorException e) {
            return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

		quarterlyDataService.save(qData);
		return new ResponseEntity<>(HttpStatus.OK);
	}

    private void populateCashFlow(QuarterlyDataModel model, CashFlow cFlow) {
        if(model.getDebtIssued() != null) {
            cFlow.setDebtIssued(model.getDebtIssued());
        }

        if(model.getDebtRepaid() != null) {
            cFlow.setDebtPayments(model.getDebtRepaid());
        }

        if(model.getDividendPayments() != null) {
            cFlow.setDividendPayment(model.getDividendPayments());
        }

        cFlow.setFinancingAtivitiesCash(model.getFinancialActivitiesCash());
        cFlow.setInvestingActivitiesCash(model.getInvestingActivitiesCash());
        cFlow.setOperatingActivitiesCash(model.getOperatingActivitiesCash());

        if(model.getInterestExpense() != null) {
            cFlow.setInterestExpences(model.getInterestExpense());
        }

        if(model.getInterestIncome() != null) {
            cFlow.setInterestIncome(model.getInterestIncome());
        }

        cFlow.setLiquidity(model.getLiquidity());
    }

    private void populateIncomeStatement(QuarterlyDataModel model, IncomeStatement iStat) {
        iStat.setRevenue(model.getRevenue());
        iStat.setGrossProfit(model.getGrossProfit());
        iStat.setCostOfSales(iStat.getRevenue().subtract(iStat.getGrossProfit()));
        iStat.setOperatingProfit(model.getOperationalProfit());
        iStat.setNetProfit(model.getNetProfit());
        iStat.setTaxExpenses(model.getTaxExpense());

        if(model.getInterestExpense() != null) {
            iStat.setInterestExpenses(model.getInterestExpense());
        }

        if(model.getInterestIncome() != null) {
            iStat.setInterestIncome(model.getInterestIncome());
        }

        if(model.getNonInterestIncome() != null) {
            iStat.setNonInterestIncome(model.getNonInterestIncome());
        }

        iStat.setFinanceIncome(model.getFinanceIncome());
        iStat.setFinanceCost(model.getFinanceCost());
    }

    private void populateBalanceSheet(QuarterlyDataModel model, BalanceSheet bSheet) {
        bSheet.setTotalAssets(model.getTotalAssets());
        bSheet.setTotalLiabilities(model.getTotalLiabilities());
        bSheet.setTotalCurrentAssets(model.getCurrentAssets());
        bSheet.setTotalCurrentLiabilities(model.getCurrentLiabilities());
        bSheet.setEquity(bSheet.getTotalAssets().subtract(bSheet.getTotalLiabilities()));

        bSheet.setInventories(model.getInventories());
        bSheet.setPropertyPlantEquipment(model.getPropertyPlantEquipment());
        bSheet.setCash(model.getCash());

        bSheet.setShortTermDebt(model.getShortTermDebt());
        bSheet.setLongTermDebt(model.getLongTermDebt());

        if(model.getTradeReceivables() != null) {
            bSheet.setTradeReceivables(model.getTradeReceivables());
        }

        if(model.getTradePayables() != null) {
            bSheet.setTradePayables(model.getTradePayables());
        }

        if(model.getOtherReceivables() != null) {
            bSheet.setOtherReceivables(model.getOtherReceivables());
        }

        if(model.getOtherPayables() != null) {
            bSheet.setOtherPayables(model.getOtherPayables());
        }

        if(model.getPrepayments() != null) {
            bSheet.setPrepayments(model.getPrepayments());
        }

        bSheet.setTotalDebt(bSheet.getShortTermDebt().add(bSheet.getLongTermDebt()).add(bSheet.getTradePayables()).add(bSheet.getOtherPayables()));
        bSheet.setIntangibleAssets(model.getIntangibleAssets());
    }

    private void populateQuarterlyData(QuarterlyDataModel model, QuarterlyData qData) throws InvestorException {
        Company company = companyService.getCompanyByTickerSymbol(model.getTickerSymbol().trim().toUpperCase());

        if(company == null) {
            throw new InvestorException("Company missing." +  model.getTickerSymbol());
        }

        qData.setCompany(company);
        qData.setName(model.getName());
        qData.setNotes(model.getNotes());
        qData.setEarningsDate(DateUtil.getDate(model.getEarningsDate()));
        qData.setSharesOutstanding(model.getShares());
	}
}
