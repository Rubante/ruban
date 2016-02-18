package com.ruban.rbac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceLocator {
    
    @Autowired
    private ICompanyService companyService;

    public ICompanyService getCompanyService() {
        return companyService;
    }
}
