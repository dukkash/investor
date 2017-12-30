package com.dukkash.investor.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "COMPANY_NOTE")
public class CompanyNote {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id", referencedColumnName = "ID")
    private Company company;

    @Column
    private String note;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "importance_level", referencedColumnName = "ID")
    private ImportanceLevel importanceLevel;

    @Column(name = "issued_date")
    private Date issuedDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public ImportanceLevel getImportanceLevel() {
        return importanceLevel;
    }

    public void setImportanceLevel(ImportanceLevel importanceLevel) {
        this.importanceLevel = importanceLevel;
    }

    public Date getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(Date issuedDate) {
        this.issuedDate = issuedDate;
    }
}
