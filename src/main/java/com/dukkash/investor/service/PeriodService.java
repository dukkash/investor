package com.dukkash.investor.service;

import java.util.List;

import javax.transaction.Transactional;

import com.dukkash.investor.model.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dukkash.investor.model.Period;
import com.dukkash.investor.repository.PeriodRepository;

@Service
@Transactional
public class PeriodService {

	@Autowired
	private PeriodRepository quarterlyDataRepository;

	public void save(List<Period> entities) {
		quarterlyDataRepository.save(entities);
	}

	public void save(Period entity) {
		quarterlyDataRepository.save(entity);
	}

	public Period getByNameAndCompany(String name, Company company) {
		return quarterlyDataRepository.getByNameAndCompany(name, company);
	}

	public List<Period> getCompanyData(Company company) {
		return quarterlyDataRepository.getCompanyData(company);
	}
}
