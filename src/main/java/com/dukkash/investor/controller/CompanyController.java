package com.dukkash.investor.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.dukkash.investor.model.*;
import com.dukkash.investor.service.CompanyNoteService;
import com.dukkash.investor.service.InvestorService;
import com.dukkash.investor.service.PeriodService;
import com.dukkash.investor.ui.model.CompanyModel;
import com.dukkash.investor.ui.model.CompanyNoteModel;
import com.dukkash.investor.ui.model.EstimateModel;
import com.dukkash.investor.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dukkash.investor.service.CompanyService;

@RestController
@RequestMapping("/company")
public class CompanyController {
	
	@Autowired
	private CompanyService companyService;

    @Autowired
    private CompanyNoteService companyNoteService;

	@Autowired
	private PeriodService quarterlyDataService;

    @Autowired
    private InvestorService investorService;

	@RequestMapping(value = "/getCompaniesDetailed", method = RequestMethod.GET)
	public List<CompanyModel> getCompaniesDetailed(@RequestParam String countryCode) {
		List<CompanyModel> countries = companyService.getCompaniesDetailed(countryCode);


		return countries;
	}


	@RequestMapping(value = "/addEstimation", method = RequestMethod.POST)
	public ResponseEntity<String> addEstimation(@RequestBody EstimateModel model) {
		Company company = companyService.getCompanyByTickerSymbol(model.getTickerSymbol());

		if (company == null) {
			return new ResponseEntity<String>(HttpStatus.PRECONDITION_FAILED);
		}

		Estimate estimate = new Estimate();
		estimate.setCompany(company);
		estimate.setCurrentPrice(model.getCurrentPrice());
		estimate.setDescription(model.getDescription());
		estimate.setEstimatedBy(model.getEstimatedBy());
		estimate.setTargetDate(DateUtil.getDate(model.getTargetDate()));
		estimate.setEstimatedDate(DateUtil.getDate(model.getEstimatedDate()));
		estimate.setTargetPrice(model.getTargetPrice());

		companyService.save(estimate);

		return new ResponseEntity<String>(HttpStatus.OK);
	}

    @RequestMapping(value = "/getEstimatedCompanies", method = RequestMethod.GET)
    public List<EstimateModel> getEstimatedCompanies() {
        List<Estimate> c = companyService.getEstimatedCompanies();
        List<EstimateModel> elements = new ArrayList<>(c.size());
        System.out.println("Companies retrieved by the controller: " + c.size());

        c.stream().sorted((a, b)-> a.getCompany().getTickerSymbol().compareTo(b.getCompany().getTickerSymbol())).forEach((a)-> elements.add(a.toEstimateModel()) );
        elements.forEach((a) -> a.getNotes().forEach((e)-> e.setCompany(null)));

        return elements;
    }

	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	public List<CompanyModel> getAllCompanies(@RequestParam String countryCode) {
		Exchange exchange = investorService.getExchangeByCountryCode(countryCode);
		List<CompanyModel> countries;

		if(countryCode.equals("TR")) {
			countries = companyService.getAllCalculated(exchange.getCountry());
		} else {
            exchange = investorService.getExchangeByCountryCode("TR");
			countries = companyService.getAllCalculatedExclude(exchange.getCountry());
		}

		countries = countries.stream().sorted((a, b)-> a.getBuyIndicator().compareTo(b.getBuyIndicator())).collect(Collectors.toList());
		System.out.println("Companies retrieved by the controller: " + countries.size());
		return countries;
	}


    @RequestMapping(value = "/getImportanceLevels", method = RequestMethod.GET)
    public List<ImportanceLevel> getImportanceLevels() {
        List<ImportanceLevel> c = companyNoteService.getImportanceLevels();
        System.out.println("Companies retrieved by the controller: " + c.size());
        return c;
    }

    @RequestMapping(value = "/addCompanyNote", method = RequestMethod.POST)
    public ResponseEntity<String> addCompanyNote(@RequestBody CompanyNoteModel noteModel) {

        if(noteModel == null || noteModel.getNote().isEmpty() || noteModel.getSymbol().isEmpty()) {
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }

        Company company = companyService.getCompanyByTickerSymbol(noteModel.getSymbol());

        if(company == null) {
            return new ResponseEntity<String>(HttpStatus.PRECONDITION_FAILED);
        }

        ImportanceLevel level = new ImportanceLevel();

        if(noteModel.getImportanceLevelId() != 0) {
            level.setId(noteModel.getImportanceLevelId());
        } else {
            level.setId(0);
        }

        CompanyNote note = new CompanyNote();
        note.setCompany(company);
        note.setIssuedDate(new Date());
        note.setNote(noteModel.getNote());
        note.setImportanceLevel(level);

        companyNoteService.save(note);

        return new ResponseEntity<String>(HttpStatus.OK);
    }

	@RequestMapping(value = "/addNewCompany", method = RequestMethod.POST)
	public ResponseEntity<String> addCompany(@RequestBody CompanyModel company) {
		if(validateCompanyModel(company)) {
			companyService.createNewCompany(company);
		} else {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@RequestMapping(value = "/getCompany", method = RequestMethod.GET)
	public List<Period> getCompany(@RequestParam String tickerSymbol) {
		if(tickerSymbol == null || tickerSymbol.isEmpty()) {
			return new ArrayList<>();
		}

		List<Period> data = companyService.getDetailedCompanyByTickerSymbol(tickerSymbol);

		for(Period period: data) {
			period.setCompany(null);
			period.getIncomeStatement().setQuarterlyData(null);
			period.getCashFlow().setQuarterlyData(null);
			period.getBalanceSheet().setQuarterlyData(null);
        }
		if(data == null) {
			return new ArrayList<>();
		}

        data = data.stream().sorted((a, b)-> b.getName().compareTo(a.getName())).collect(Collectors.toList());

		return data;
	}

	@RequestMapping(value = "/checkCompany", method = RequestMethod.GET)
	public String checkCompany(@RequestParam String searchElement) {
		if(searchElement == null || searchElement.isEmpty()) {
			return "";
		}

		Company company = companyService.getCompanyByTickerSymbol(searchElement);

		if(company == null) {
			company = companyService.getCompanyByName(searchElement);

			if(company == null) {
				return "";
			}
		}

		return company.getTickerSymbol();
	}

	private boolean validateCompanyModel(CompanyModel company) {
		if(company.getCountry().equals(Integer.valueOf(0))) {
			System.out.println("Please provide a valid country!");
			return false;
		} else if(company.getSector().equals(Integer.valueOf(0))) {
			System.out.println("Please provide a valid sector!");
			return false;
		} else if(company.getName().equals("")) {
			System.out.println("Please provide a valid name!");
			return false;
		} else if(company.getPrice() == null) {
			System.out.println("Please provide a valid price!");
			return false;
		} else if(company.getSharesOutstanding() == null) {
            System.out.println("Please provide a valid price!");
            return false;
        } else if(company.getTickerSymbol().equals("")) {
			System.out.println("Please provide a valid symbol!");
			return false;
		} else if(companyService.getCompanyByTickerSymbol(company.getTickerSymbol()) != null) {
			System.out.println("Company with that ticker symbol already exist!");
			return false;
		}

		return true;
	}
}
