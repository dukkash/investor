package com.dukkash.investor.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dukkash.investor.model.Company;
import com.dukkash.investor.model.QuarterlyData;
import com.dukkash.investor.util.HibernateUtil;

@Repository
@Transactional
public class QuarterlyDataRepository {
	@Autowired
	private HibernateUtil hibernateUtil;

	public void save(QuarterlyData entity) {
		hibernateUtil.getSession().save(entity);
	}

	public void save(List<QuarterlyData> entities) {
		for (QuarterlyData entity : entities) {
			hibernateUtil.getSession().save(entity);
		}
	}

	public List<QuarterlyData> getCompanyData(Company company) {
		return (List<QuarterlyData>) hibernateUtil.getSession().createCriteria(QuarterlyData.class)
				.add(Restrictions.eq("company", company)).list();		
	}

    public QuarterlyData getByNameAndCompany(String name, Company company) {
		return (QuarterlyData) hibernateUtil.getSession().createCriteria(QuarterlyData.class).add(Restrictions.and(Restrictions.eq("company", company), Restrictions.eq("name", name))).uniqueResult();
	}

}
