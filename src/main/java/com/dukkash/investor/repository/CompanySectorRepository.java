package com.dukkash.investor.repository;

import com.dukkash.investor.model.CompanySector;
import com.dukkash.investor.util.HibernateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CompanySectorRepository {

    @Autowired
    private HibernateUtil hibernateUtil;

    public void save(CompanySector entity) {
    }

    public CompanySector delete(CompanySector entity) {
        // TODO Auto-generated method stub
        return null;
    }

    public CompanySector update(CompanySector entity) {
        return null;
    }

    public CompanySector getById(int id) {
        return hibernateUtil.getSession().get(CompanySector.class, id);
    }


    public List<CompanySector> getAll() {
        return hibernateUtil.getSession().createCriteria(CompanySector.class).list();
    }

}
