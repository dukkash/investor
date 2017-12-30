package com.dukkash.investor.service;

import com.dukkash.investor.model.Country;
import com.dukkash.investor.model.Exchange;
import com.dukkash.investor.repository.CompanyRepository;
import com.dukkash.investor.repository.CountryRepository;
import com.dukkash.investor.repository.InvestorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class InvestorService {

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    private InvestorRepository investorRepository;

    public Exchange getExchangeByCountryCode(String countryCode) {
        Country country = countryRepository.getCountryByCode(countryCode);

        return investorRepository.getExchangeByCountryCode(country);
    }
}
