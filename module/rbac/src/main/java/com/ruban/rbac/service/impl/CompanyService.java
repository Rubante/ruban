package com.ruban.rbac.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruban.rbac.dao.ICompanyMapper;
import com.ruban.rbac.domain.organization.Company;
import com.ruban.rbac.service.ICompanyService;

@Service
public class CompanyService implements ICompanyService {

    @Autowired
    private ICompanyMapper companyMapper;

    @Override
    public List<Company> selectAll() {
        return companyMapper.selectAll();
    }

    @Override
    public void insert(Company company) {
        companyMapper.insert(company);
    }
}
