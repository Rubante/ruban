package com.ruban.rbac.dao;

import java.util.List;

import com.ruban.framework.dao.support.RubanDaoRepository;
import com.ruban.rbac.domain.organization.Company;

@RubanDaoRepository
public interface ICompanyMapper {

    public List<Company> selectAll();

    public void insert(Company company);
}
