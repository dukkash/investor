package com.dukkash.investor.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "COMPANY_SECTOR")
public class CompanySector {

	@Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Integer id;

    @Column
    private String name;

    @Column
    private String code;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id", referencedColumnName = "ID")
    private CompanySector parent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public CompanySector getParent() {
        return parent;
    }

    public void setParent(CompanySector parent) {
        this.parent = parent;
    }
}
