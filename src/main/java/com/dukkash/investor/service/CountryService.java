package com.dukkash.investor.service;

import com.dukkash.investor.model.Country;
import com.dukkash.investor.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
@Transactional
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    public Country getById(int id) {
        return countryRepository.getById(id);
    }

    public List<Country> getAll() {
        return countryRepository.getAll();
    }

	public Country getCountryByCode(String countryCode) {
		return countryRepository.getCountryByCode(countryCode);
	}

}
