package com.dukkash.investor;

import com.dukkash.investor.parser.html.InvestingStocksParser;
import com.dukkash.investor.parser.html.IsYatirimParser;
import com.dukkash.investor.service.CompanyService;
import com.dukkash.investor.service.PeriodService;
import com.dukkash.investor.util.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.rowset.CachedRowSet;
import java.util.Date;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class InvestorApp {

    public static void maint(String[] args) throws Exception {
        Date d = new Date();
        System.out.println(d.getTime());
    }

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext ctx = SpringApplication.run(InvestorApp.class, args);
        XSLUtil parser = ctx.getBean(XSLUtil.class);
        InvestorUtil util = ctx.getBean(InvestorUtil.class);
        CompanyService companyService = ctx.getBean(CompanyService.class);
        PeriodService qService = ctx.getBean(PeriodService.class);
        InvestingStocksParser iparser = ctx.getBean(InvestingStocksParser.class);
        IsYatirimParser isYatirimParser = ctx.getBean(IsYatirimParser.class);
        KAPCustomReportReader kapReader = ctx.getBean(KAPCustomReportReader.class);

        isYatirimParser.parseCompany();
                
      //  iparser.updateCompanyPricesOfBIST();
      //  kapReader.parseQuarterlyFinancialReport("D:\\doc\\investing\\KAP_REPORTS\\zoren.xls");
       // parser.parseData();
       
     //  iparser.getStocks("TR", "https://www.investing.com/indices/ise-all-shares-components");
  //      isYatirim.parseCompanyData("D:\\doc\\investing\\reports\\petkim.xls","PETKM", new BigDecimal("1500000000"));

      //  QuarterlyData qData = qService.getByNameAndCompany("2017-Q3", companyService.getCompanyByTickerSymbol("THYAO"));

    //    companyService.updateStockPrices();

      //  parser.parseQuarterlyFinancialReport("ENKAI","D:\\projects\\investor\\src\\main\\resources\\reports\\report.xls", 
        //		new BigDecimal(1000),"2016-Q2", "15.08.2016", new BigDecimal("4600000000"));

     //   parser.parseBankQuarterlyFinancialReport("HALKB","D:\\projects\\investor\\src\\main\\resources\\reports\\report.xls",
     //           new BigDecimal(1000),"2016-Q2", "08.08.2016", new BigDecimal("1250000000"));


      // companyService.calculatePE("YATAS");
     //   util.normalizeQuarters("2016", "ENKAI");

        //  OutputUtil.listCompany(company);
        
        CachedRowSet set;
    }
}
