package com.dukkash.investor.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity(name = "EXCHANGE")
public class Exchange {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Integer id;

    @Column
    private String name;

    @ManyToOne
    private Country country;

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

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
