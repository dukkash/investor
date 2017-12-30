package com.dukkash.investor.util;

import com.dukkash.investor.model.Company;
import com.dukkash.investor.model.IncomeStatement;
import com.dukkash.investor.model.QuarterlyData;
import com.dukkash.investor.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.dukkash.investor.service.QuarterlyDataService;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Component
@Transactional
public class InvestorUtil {

    @Autowired
    private QuarterlyDataService quarterlyDataService;

    @Autowired
    private CompanyService companyService;

    public void normalizeQuarters(String year, String tickerSymbol) {
        String Q1 = year + "-Q1";
        String Q2 = year + "-Q2";
        String Q3 = year + "-Q3";
        String Q4 = year + "-Q4";

        Company company = companyService.getCompanyByTickerSymbol(tickerSymbol);
        System.out.println(company.getQuarterlyData().size());

        QuarterlyData q1=null,q2=null,q3=null,q4=null;
        int quarters = 0;

        for(QuarterlyData temp: company.getQuarterlyData()) {
            if(temp.getName().equals(Q1)) {
                q1 = temp;
                quarters++;
            } else  if(temp.getName().equals(Q2)) {
                q2 = temp;
                quarters++;
            } else  if(temp.getName().equals(Q3)) {
                q3 = temp;
                quarters++;
            } else  if(temp.getName().equals(Q4)) {
                q4 = temp;
                quarters++;
            }
        }

        if(q4 != null) {
          // distractQuarter(q3, q4);
        }

        if(q3 != null) {
          // distractQuarter(q2, q3);
        }

        if(q2 != null) {
         //  distractQuarter(q1, q2);
        }
    }

    private void distractQuarter(QuarterlyData smaller, QuarterlyData bigger) {
        IncomeStatement isBig = bigger.getIncomeStatement();
        IncomeStatement isSmall = smaller.getIncomeStatement();

        System.out.println(isBig);
        System.out.println(isSmall);

        isBig.setRevenue(isBig.getRevenue().subtract(isSmall.getRevenue()));
        isBig.setOperatingProfit(isBig.getOperatingProfit().subtract(isSmall.getOperatingProfit()));
        isBig.setTaxExpenses(isBig.getTaxExpenses().subtract(isSmall.getTaxExpenses()));
        isBig.setNetProfit(isBig.getNetProfit().subtract(isSmall.getNetProfit()));

        if(isBig.getGrossProfit() != null && isBig.getGrossProfit().equals(new BigDecimal(0))) {
            isBig.setGrossProfit(isBig.getGrossProfit().subtract(isSmall.getGrossProfit()));
        }

        if(isBig.getFinanceIncome() != null && isBig.getFinanceIncome().equals(new BigDecimal(0))) {
            isBig.setFinanceIncome(isBig.getFinanceIncome().subtract(isSmall.getFinanceIncome()));
        }

        if(isBig.getFinanceCost() != null && isBig.getFinanceCost().equals(new BigDecimal(0))) {
            isBig.setFinanceCost(isBig.getFinanceCost().subtract(isSmall.getFinanceCost()));
        }

        if(isBig.getInterestIncome() != null && isBig.getInterestIncome().equals(new BigDecimal(0))) {
            isBig.setInterestIncome(isBig.getInterestIncome().subtract(isSmall.getInterestIncome()));
        }

        if(isBig.getInterestExpenses() != null && isBig.getInterestExpenses().equals(new BigDecimal(0))) {
            isBig.setInterestExpenses(isBig.getInterestExpenses().subtract(isSmall.getInterestExpenses()));
        }

        System.out.println(isBig);
    }
}













































