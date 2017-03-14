package com.ruban.stock.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruban.framework.dao.IRubanDao;
import com.ruban.framework.dao.helper.Condition;
import com.ruban.framework.dao.helper.ResultInfo;
import com.ruban.stock.dao.ICompanyMapper;
import com.ruban.stock.domain.customer.Customer;
import com.ruban.stock.service.ICompanyService;

@Service
public class CompanyService implements ICompanyService {

    @Autowired
    private ICompanyMapper companyMapper;

    @Autowired
    private IRubanDao rubanDao;

    @Override
    public List<Customer> selectAll() {
        return companyMapper.selectAll();
    }

    @Override
    public ResultInfo<Customer> selectByPage(Condition<Customer> condition) {

        ResultInfo<Customer> result = rubanDao.findByPage(condition, companyMapper);
        return result;
    }

    @Override
    public void insert(Customer company) {
        companyMapper.insert(company);
    }
}
