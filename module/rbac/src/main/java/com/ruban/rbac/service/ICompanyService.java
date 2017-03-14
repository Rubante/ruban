package com.ruban.rbac.service;

import java.util.List;

import com.ruban.framework.dao.helper.Condition;
import com.ruban.framework.dao.helper.ResultInfo;
import com.ruban.rbac.backend.company.form.CompanyForm;
import com.ruban.rbac.domain.organization.Company;

/**
 * 组织结构
 * 
 * @author ruban
 *
 */
public interface ICompanyService {

    /**
     * 查询所有的组织机构列表
     * 
     * @return
     */
    public List<Company> selectAll();

    /**
     * 根据条件分页查询
     * 
     * @param dto
     * @return
     */
    public ResultInfo<Company> selectByPage(Condition<Company> condition);

    /**
     * 根据条件查询
     * 
     * @param condition
     * @return
     */
    public List<Company> selectByCondition(Condition<Company> condition);

    /**
     * 新建组织机构
     * 
     * @param companyForm
     */
    public Company insert(CompanyForm companyForm);

    /**
     * 更新组织机构
     * 
     * @param companyForm
     */
    public int update(CompanyForm companyForm);

    /**
     * 根据ID删除组织机构
     * 
     * @param id
     * @return
     */
    public int deleteById(Long id);

    /**
     * 根据ID批量删除组织机构
     * 
     * @param ids
     * @return
     */
    public int deleteByIds(String[] ids);

    /**
     * 根据ID排序组织机构
     * 
     * @param ids
     * @return
     */
    public int sortByIds(String[] ids);

    /**
     * 根据ID获取组织机构
     * 
     * @param id
     * @return
     */
    public Company findById(Long id);

    /**
     * 获取组织机构json串
     * 
     * @param rootId
     * @return
     */
    public String getJsonTree(Long rootId);
}
