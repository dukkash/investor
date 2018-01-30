package com.dukkash.investor.repository;

import java.util.List;

import javax.transaction.Transactional;

import com.dukkash.investor.model.CompanyNote;
import com.dukkash.investor.model.Estimate;
import com.dukkash.investor.ui.model.EstimateModel;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dukkash.investor.model.Company;
import com.dukkash.investor.model.Country;
import com.dukkash.investor.util.HibernateUtil;

@Repository
@Transactional
public class CompanyRepository {

	@Autowired
	private HibernateUtil hibernateUtil;

	public void save(Company entity) {
		hibernateUtil.getSession().save(entity);
	}

	public void update(Company entity) {
		hibernateUtil.getSession().update(entity);
	}


	public void save(List<Company> companies) {
		for (Company company : companies) {
			hibernateUtil.getSession().save(company);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Company> getAll() {
		return hibernateUtil.getSession().createCriteria(Company.class).list();
	}

	public Company getCompanyByTickerSymbol(String tickerSymbol) {
		return (Company) hibernateUtil.getSession().createCriteria(Company.class)
				.add(Restrictions.eq("tickerSymbol", tickerSymbol.toUpperCase())).uniqueResult();
	}

	public void saveOrUpdate(Company entity) {
		hibernateUtil.getSession().saveOrUpdate(entity);
	}


	public List<Company> getAllByCountry(Country country) {
		return (List<Company>) hibernateUtil.getSession().createCriteria(Company.class)
				.add(Restrictions.eq("country", country)).list();
	}

    public void save(Estimate estimate) {
		hibernateUtil.getSession().save(estimate);
    }

    public List<Estimate> getEstimatedCompanies() {
        return hibernateUtil.getSession().createCriteria(Estimate.class).list();
    }

    public List<CompanyNote> getNotesByCompany(Company company) {
        return (List<CompanyNote>) hibernateUtil.getSession().createCriteria(CompanyNote.class)
                .add(Restrictions.eq("company", company)).list();
    }

	public List<Company> getAllCalculatedExcept(Country country) {
		return (List<Company>) hibernateUtil.getSession().createCriteria(Company.class)
				.add(Restrictions.ne("country", country)).list();
	}

    public Company getCompanyByName(String name) {
		return (Company) hibernateUtil.getSession().createCriteria(Company.class)
				.add(Restrictions.eq("name", name.toUpperCase())).uniqueResult();
    }
}
