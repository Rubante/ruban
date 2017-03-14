package com.ruban.stock.service;

import java.util.List;

import com.ruban.framework.dao.helper.Condition;
import com.ruban.framework.dao.helper.QueryPage;
import com.ruban.framework.dao.helper.ResultInfo;
import com.ruban.stock.domain.customer.Customer;

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
    public List<Customer> selectAll();

    /**
     * 分页查询
     * 
     * @param dto
     * @return
     */
    public ResultInfo<Customer> selectByPage(Condition<Customer> condition);

    /**
     * 新建公司
     * 
     * @param company
     */
    public void insert(Customer company);
}
