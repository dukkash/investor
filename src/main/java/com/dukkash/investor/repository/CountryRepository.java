package com.dukkash.investor.repository;

import com.dukkash.investor.model.Country;
import com.dukkash.investor.util.HibernateUtil;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CountryRepository {
	
    @Autowired
    private HibernateUtil hibernateUtil;

    public void save(Country entity) {
    }

    public Country delete(Country entity) {
        // TODO Auto-generated method stub
        return null;
    }

    public Country update(Country entity) {
        return null;
    }

    public Country getById(int id) {
        return hibernateUtil.getSession().get(Country.class, id);
    }

    @SuppressWarnings("unchecked")
	public List<Country> getAll() {
        return hibernateUtil.getSession().createCriteria(Country.class).list();
    }

	public Country getCountryByCode(String countryCode) {
		return (Country) hibernateUtil.getSession().createCriteria(Country.class)
				.add(Restrictions.eq("countryCode", countryCode)).uniqueResult();
	}

}

