package com.ruban.rbac.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruban.framework.dao.IRubanDao;
import com.ruban.framework.dao.helper.Condition;
import com.ruban.framework.dao.helper.ResultInfo;
import com.ruban.rbac.dao.ICompanyMapper;
import com.ruban.rbac.domain.organization.Company;
import com.ruban.rbac.service.ICompanyService;

@Service
public class CompanyService implements ICompanyService {

    @Autowired
    private ICompanyMapper companyMapper;

    @Autowired
    private IRubanDao rubanDao;

    @Override
    public List<Company> selectAll() {
        return companyMapper.selectAll();
    }

    @Override
    public ResultInfo<Company> selectByPage(Condition<Company> condition) {

        ResultInfo<Company> result = rubanDao.findByPage(condition, companyMapper);
        return result;
    }

    @Override
    public void insert(Company company) {
        companyMapper.insert(company);
    }
}
