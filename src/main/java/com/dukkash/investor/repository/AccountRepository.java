package com.dukkash.investor.repository;


import com.dukkash.investor.model.Account;
import com.dukkash.investor.util.HibernateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class AccountRepository {

    @Autowired
    private HibernateUtil hibernateUtil;


    public List<Account> getAll() {
        return hibernateUtil.getSession().createCriteria(Account.class).list();
    }

    public Account getAccountById(Integer id) {
        return hibernateUtil.getSession().get(Account.class, id);
    }

    public void update(Account account) {
        hibernateUtil.getSession().saveOrUpdate(account);
    }
}
