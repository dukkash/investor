package com.dukkash.investor.service;

import java.util.List;

import javax.transaction.Transactional;

import com.dukkash.investor.model.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dukkash.investor.model.QuarterlyData;
import com.dukkash.investor.repository.QuarterlyDataRepository;

@Service
@Transactional
public class QuarterlyDataService {

	@Autowired
	private QuarterlyDataRepository quarterlyDataRepository;

	public void save(List<QuarterlyData> entities) {
		quarterlyDataRepository.save(entities);
	}

	public void save(QuarterlyData entity) {
		quarterlyDataRepository.save(entity);
	}

	public QuarterlyData getByNameAndCompany(String name, Company company) {
		return quarterlyDataRepository.getByNameAndCompany(name, company);
	}

	public List<QuarterlyData> getCompanyData(Company company) {
		return quarterlyDataRepository.getCompanyData(company);
	}
}
