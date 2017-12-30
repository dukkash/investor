package com.dukkash.investor.repository;

import com.dukkash.investor.model.CompanyNote;
import com.dukkash.investor.model.ImportanceLevel;
import com.dukkash.investor.util.HibernateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class CompanyNoteRepository {

    @Autowired
    private HibernateUtil hibernateUtil;

    public void save(CompanyNote entity) {
        hibernateUtil.getSession().save(entity);
    }

    public List<ImportanceLevel> getImportanceLevels() {
        return hibernateUtil.getSession().createCriteria(ImportanceLevel.class).list();
    }
}
