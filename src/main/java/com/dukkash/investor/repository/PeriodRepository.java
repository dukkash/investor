package com.dukkash.investor.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dukkash.investor.model.Company;
import com.dukkash.investor.model.Period;
import com.dukkash.investor.util.HibernateUtil;

@Repository
@Transactional
public class PeriodRepository {
	@Autowired
	private HibernateUtil hibernateUtil;

	public void save(Period entity) {
		hibernateUtil.getSession().save(entity);
	}

	public void save(List<Period> entities) {
		for (Period entity : entities) {
			hibernateUtil.getSession().save(entity);
		}
	}

	public List<Period> getCompanyData(Company company) {
		return (List<Period>) hibernateUtil.getSession().createCriteria(Period.class)
				.add(Restrictions.eq("company", company)).list();		
	}

    public Period getByNameAndCompany(String name, Company company) {
		return (Period) hibernateUtil.getSession().createCriteria(Period.class).add(Restrictions.and(Restrictions.eq("company", company), Restrictions.eq("name", name))).uniqueResult();
	}

}
