package com.dukkash.investor.repository;

import com.dukkash.investor.model.Transaction;
import com.dukkash.investor.model.TransactionType;
import com.dukkash.investor.util.HibernateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class TransactionRepository {


    @Autowired
    private HibernateUtil hibernateUtil;


    public List<TransactionType> getAllTransactionTypes() {
        return hibernateUtil.getSession().createCriteria(TransactionType.class).list();
    }

    public TransactionType getTransactionTypeById(Integer id) {
        return hibernateUtil.getSession().get(TransactionType.class, id);
    }

    public void addTransaction(Transaction transaction) {
        hibernateUtil.getSession().save(transaction);
    }
}
