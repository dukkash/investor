package com.dukkash.investor.service;

import com.dukkash.investor.model.CompanyNote;
import com.dukkash.investor.model.ImportanceLevel;
import com.dukkash.investor.repository.CompanyNoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
@Transactional
public class CompanyNoteService {

    @Autowired
    private CompanyNoteRepository companyNoteRepository;

    public void save(CompanyNote entity) {
        companyNoteRepository.save(entity);
    }

    public List<ImportanceLevel> getImportanceLevels() {
        return companyNoteRepository.getImportanceLevels();
    }
}
