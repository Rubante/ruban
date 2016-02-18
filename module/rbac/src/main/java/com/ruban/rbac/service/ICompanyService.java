package com.ruban.rbac.service;

import java.util.List;

import com.ruban.rbac.domain.organization.Company;

/**
 * 组织结构：公司
 * 
 * @author ruban
 *
 */
public interface ICompanyService {
    
    /**
     * 查询所有的公司列表
     * 
     * @return
     */
    public List<Company> selectAll();
    
    /**
     * 新建公司
     * 
     * @param company
     */
    public void insert(Company company);
}
