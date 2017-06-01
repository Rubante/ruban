package com.ruban.rbac.service;

import java.util.List;

import com.ruban.framework.dao.helper.Condition;
import com.ruban.framework.dao.helper.ResultInfo;
import com.ruban.rbac.backend.account.form.AccountForm;
import com.ruban.rbac.domain.authz.Account;

/**
 * 账号
 * 
 * @author ruban
 *
 */
public interface IAccountService {

    /**
     * 查询所有的账号列表
     * 
     * @return
     */
    public List<Account> selectAll();

    /**
     * 根据条件分页查询
     * 
     * @param dto
     * @return
     */
    public ResultInfo<Account> selectByPage(Condition<Account> condition);

    /**
     * 根据条件查询
     * 
     * @param condition
     * @return
     */
    public List<Account> selectByCondition(Condition<Account> condition);

    /**
     * 新建账号
     * 
     * @param accountForm
     */
    public void insert(AccountForm accountForm);

    /**
     * 更新账号
     * 
     * @param ResourceForm
     */
    public int update(AccountForm accountForm);

    /**
     * 根据ID删除账号
     * 
     * @param id
     * @return
     */
    public int deleteById(Long id);

    /**
     * 根据ID批量删除账号
     * 
     * @param ids
     * @return
     */
    public int deleteByIds(String[] ids);

    /**
     * 根据ID排序账号
     * 
     * @param ids
     * @return
     */
    public int sortByIds(String[] ids);

    /**
     * 根据ID获取账号
     * 
     * @param id
     * @return
     */
    public Account findById(Long id);
}
