package com.dukkash.investor.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dukkash.investor.exception.InvestorException;
import com.dukkash.investor.parser.html.InvestingStocksParser;
import com.dukkash.investor.util.KAPCustomReportReader;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private KAPCustomReportReader kapCustomReportReader;

	@Autowired
	private InvestingStocksParser investingStocksParser;

	@Value("${kap.reports.repository}")
	private String kapReportsDir;

	@RequestMapping(value = "/updateBistCompPrices", method = RequestMethod.GET)
	public ResponseEntity<String> updateBistCompPrices() {
		
		try {
			investingStocksParser.updateCompanyPricesOfBIST();
		} catch (InvestorException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.EXPECTATION_FAILED);
		}
		
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@RequestMapping(value = "/addKapCompanyFinancialData", method = RequestMethod.GET)
	public ResponseEntity<String> addCompany(@RequestParam String tickerSymbol) {

		if (tickerSymbol == null || tickerSymbol.isEmpty()) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		} else if (kapReportsDir == null || kapReportsDir.isEmpty()) {
			System.out.println("Repository of reports can not be located!");
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}

		String fileFullParth = kapReportsDir + tickerSymbol.toLowerCase() + ".xls";

		if (!new File(fileFullParth).exists()) {
			System.out.println("Report file can not be located!");
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}

		kapCustomReportReader.parseQuarterlyFinancialReport(fileFullParth);

		return new ResponseEntity<String>(HttpStatus.OK);
	}

}
