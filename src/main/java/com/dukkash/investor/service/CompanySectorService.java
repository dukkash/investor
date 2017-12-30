package com.dukkash.investor.service;

import com.dukkash.investor.model.CompanySector;
import com.dukkash.investor.model.Country;
import com.dukkash.investor.repository.CompanySectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Component
@Transactional
public class CompanySectorService {

    @Autowired
    public CompanySectorRepository companySectorRepository;

    public CompanySector getById(int id) {
        return companySectorRepository.getById(id);
    }

    public List<CompanySector> getAll() {
        return companySectorRepository.getAll();
    }
}
