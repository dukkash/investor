package com.dukkash.investor.controller;

import com.dukkash.investor.model.CompanySector;
import com.dukkash.investor.service.CompanySectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/companySector")
public class CompanySectorController {

    @Autowired
    private CompanySectorService companySectorService;

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    private List<CompanySector> getAllSectors() {
        List<CompanySector> c = companySectorService.getAll();
        System.out.println("CompanySector retrieved by the controller: " + c.size());
        return c;
    }
}
