package com.dukkash.investor.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.dukkash.investor.Constants;
import com.dukkash.investor.model.*;
import com.dukkash.investor.parser.html.YahooParser;
import com.dukkash.investor.repository.*;
import com.dukkash.investor.ui.model.CompanyModel;
import com.dukkash.investor.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dukkash.investor.exception.InvestorException;

@Service
@Transactional
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private CompanySectorRepository companySectorRepository;

    @Autowired
    private InvestorRepository investorRepository;

    @Autowired
    private PeriodRepository quarterlyDataRepository;

    public void save(Company entity) {
        companyRepository.save(entity);
    }

    public void save(List<Company> stocks) {
        companyRepository.save(stocks);
    }

    public void update(Company entity) {
        companyRepository.update(entity);
    }

    public List<Company> getAll() {
        return companyRepository.getAll();
    }

    public Company getCompanyByTickerSymbol(String tickerSymbol) {
        return companyRepository.getCompanyByTickerSymbol(tickerSymbol);
    }

    public void saveOrUpdate(Company entity) {
        companyRepository.saveOrUpdate(entity);
    }

    public List<Period> getDetailedCompanyByTickerSymbol(String tickerSymbol) {
        Company company = companyRepository.getCompanyByTickerSymbol(tickerSymbol);

        if (company != null) {
            return quarterlyDataRepository.getCompanyData(company);
        }

        return null;
    }

    public List<CompanyModel> getAllCalculatedExclude(Country country) {
        List<Company> companies = companyRepository.getAllCalculatedExcept(country);
        return getAllCalculated(companies);
    }

    public List<CompanyModel> getAllCalculated(Country country) {
        List<Company> companies = companyRepository.getAllByCountry(country);
        return getAllCalculated(companies);
    }

    public List<CompanyModel> getAllCalculated(List<Company> companies) {
        List<CompanyModel> companyModels = new ArrayList<>();

        for (Company company : companies) {
            List<Period> qData = quarterlyDataRepository.getCompanyData(company);
            CompanyModel temp = populateCompanyExtraFields(company, qData);

            if (temp != null) {
                companyModels.add(temp);
            }
        }

        return companyModels;
    }

    private CompanyModel populateCompanyExtraFields(Company company, List<Period> qData) {
        CompanyModel cModel = new CompanyModel();
        cModel.setName(company.getName());
        cModel.setTickerSymbol(company.getTickerSymbol());
        cModel.setPrice(company.getPrice());

        if (qData == null || qData.isEmpty() || qData.size() < 4 || qData.get(0).getBalanceSheet() == null ||
                qData.get(0).getCashFlow() == null || qData.get(0).getIncomeStatement() == null) {
            return null;
        }

        cModel.setId(company.getId());
        qData = qData.stream().sorted(Comparator.comparing(Period::getName)).collect(Collectors.toList());

        Period lastQuarter = qData.get(qData.size() - 1);
        BigDecimal marketCap = company.getPrice().multiply(lastQuarter.getSharesOutstanding()).setScale(0, BigDecimal.ROUND_DOWN);
        cModel.setMarketCap(marketCap);
        cModel.setEquity(lastQuarter.getBalanceSheet().getEquity());

        BigDecimal last4QEarningsTotal = new BigDecimal(0);
        BigDecimal last2QEarningsTotal = new BigDecimal(0);

        for (int i = qData.size() - 4, j = 0; i < qData.size(); i++,j++) {
            last4QEarningsTotal = last4QEarningsTotal.add(qData.get(i).getIncomeStatement().getNetProfit());

            if ( j > 1) {
                last2QEarningsTotal = last2QEarningsTotal.add(qData.get(i).getIncomeStatement().getNetProfit());
            }
        }

        last2QEarningsTotal = last2QEarningsTotal.multiply(new BigDecimal(2));
        cModel.setLastTwoQuartersPE(marketCap.divide(last2QEarningsTotal, 2, BigDecimal.ROUND_HALF_UP));
        cModel.setPriceEarning(marketCap.divide(last4QEarningsTotal, 2, BigDecimal.ROUND_HALF_UP));
        cModel.setPriceBook(marketCap.divide(cModel.getEquity().subtract(lastQuarter.getBalanceSheet().getIntangibleAssets()), 2, BigDecimal.ROUND_HALF_UP));
        cModel.setBuyIndicator((cModel.getPriceBook().multiply(cModel.getPriceEarning())).setScale(1, BigDecimal.ROUND_DOWN));

        if (lastQuarter.getBalanceSheet().getTotalCurrentAssets() != null && !lastQuarter.getBalanceSheet().getTotalCurrentAssets().equals(new BigDecimal(0))) {
            cModel.setWorkingCapital(lastQuarter.getBalanceSheet().getTotalCurrentAssets().subtract(lastQuarter.getBalanceSheet().getTotalCurrentLiabilities()));
        } else {
            cModel.setWorkingCapital(new BigDecimal(0));
        }

        if (lastQuarter.getBalanceSheet().getCash() != null) {
            cModel.setCash(lastQuarter.getBalanceSheet().getCash());
        } else if (lastQuarter.getCashFlow() != null && lastQuarter.getCashFlow().getLiquidity() != null) {
            cModel.setCash(lastQuarter.getCashFlow().getLiquidity());
        } else {
            cModel.setCash(new BigDecimal(0));
        }

        BigDecimal totalDebt = new BigDecimal(0);

        if (lastQuarter.getBalanceSheet().getLongTermDebt() != null) {
            totalDebt = totalDebt.add(lastQuarter.getBalanceSheet().getLongTermDebt());
        }

        if (lastQuarter.getBalanceSheet().getShortTermDebt() != null) {
            totalDebt = totalDebt.add(lastQuarter.getBalanceSheet().getShortTermDebt());
        }

        if (lastQuarter.getBalanceSheet().getTradePayables() != null) {
            totalDebt = totalDebt.add(lastQuarter.getBalanceSheet().getTradePayables());
        }

        if (lastQuarter.getBalanceSheet().getOtherPayables() != null) {
            totalDebt = totalDebt.add(lastQuarter.getBalanceSheet().getOtherPayables());
        }

        cModel.setTotalDebt(totalDebt);

        return cModel;
    }

    public void createNewCompany(CompanyModel companyModel) {
        Country country = countryRepository.getById(companyModel.getCountry());
        CompanySector sector = companySectorRepository.getById(Integer.valueOf(companyModel.getSector()));

        Company company = new Company();
        company.setName(companyModel.getName());
        company.setTickerSymbol(companyModel.getTickerSymbol());
        company.setSector(sector);
        company.setPrice(companyModel.getPrice());
        company.setCountry(country);
        company.setStockUrl(companyModel.getStockUrl());
        company.setAbout(companyModel.getAbout());
        company.setNextEarningsDate(DateUtil.getDate(companyModel.getNextEarningsDate()));
        company.setSharesOutstanding(companyModel.getSharesOutstanding());
        company.setAnalysedLevel(investorRepository.getAnalysedLevelByLevelCode(Constants.COMPANY_NOT_ANALYSED_LEVEL_CODE));

        companyRepository.save(company);
    }

    public void updateStockPrices() {
        List<Company> companies = companyRepository.getAll();

        for (Company com : companies) {
            try {
                BigDecimal result = YahooParser.getStockMarketPrice(com.getStockUrl());
                com.setPrice(result);
                Thread.sleep(1000);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Company> getAllByCountryCode(String countryCode) throws InvestorException {
        Country country = countryRepository.getCountryByCode(countryCode);

        if (country == null) {
            throw new InvestorException(String.format("Country witht he code %s cannot be found!", countryCode));
        }
        return companyRepository.getAllByCountry(country);
    }

    public void save(Estimate estimate) {
        companyRepository.save(estimate);
    }

    public List<Estimate> getEstimatedCompanies() {
        List<Estimate> estimates = companyRepository.getEstimatedCompanies();

        for (Estimate e : estimates) {
            List<CompanyNote> notes = companyRepository.getNotesByCompany(e.getCompany());
            e.setNotes(notes);
        }

        return estimates;
    }

    public Company getCompanyByName(String name) {
        return companyRepository.getCompanyByName(name);
    }

    public List<CompanyModel> getCompaniesDetailed(String countryCode) {
        Country country = countryRepository.getCountryByCode(countryCode);
        List<Company> companies = companyRepository.getAllByCountry(country);


        return null;
    }
}








































