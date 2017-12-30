package com.dukkash.investor.repository;

import com.dukkash.investor.model.AnalysedLevel;
import com.dukkash.investor.model.Country;
import com.dukkash.investor.model.Exchange;
import com.dukkash.investor.util.HibernateUtil;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class InvestorRepository {

    @Autowired
    private HibernateUtil hibernateUtil;

    public AnalysedLevel getAnalysedLevelByLevelCode(String level) {
        return (AnalysedLevel) hibernateUtil.getSession().createCriteria(AnalysedLevel.class)
                .add(Restrictions.eq("level", level)).uniqueResult();
    }

    public Exchange getExchangeByCountryCode(Country country) {
        return (Exchange) hibernateUtil.getSession().createCriteria(Exchange.class)
                .add(Restrictions.eq("country", country)).uniqueResult();
    }
}
