package com.dukkash.investor.controller;

import com.dukkash.investor.model.Country;
import com.dukkash.investor.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/country")
public class CountryController {

    @Autowired
    private CountryService countryService;

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public List<Country> getAllCountries() {
        List<Country> c = countryService.getAll();
        System.out.println("Countries retrieved by the controller: " + c.size());
        return c;
    }
}
