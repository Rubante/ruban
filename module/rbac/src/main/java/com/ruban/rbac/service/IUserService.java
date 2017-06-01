package com.ruban.rbac.service;

import java.util.List;

import com.ruban.framework.dao.helper.Condition;
import com.ruban.framework.dao.helper.ResultInfo;
import com.ruban.rbac.backend.user.form.UserForm;
import com.ruban.rbac.domain.authz.User;

/**
 * 账号
 * 
 * @author ruban
 *
 */
public interface IUserService {

    /**
     * 查询所有的账号列表
     * 
     * @return
     */
    public List<User> selectAll();

    /**
     * 根据条件分页查询
     * 
     * @param dto
     * @return
     */
    public ResultInfo<User> selectByPage(Condition<User> condition);

    /**
     * 根据条件查询
     * 
     * @param condition
     * @return
     */
    public List<User> selectByCondition(Condition<User> condition);

    /**
     * 新建账号
     * 
     * @param accountForm
     */
    public void insert(UserForm accountForm);

    /**
     * 更新账号
     * 
     * @param ResourceForm
     */
    public int update(UserForm accountForm);

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
    public User findById(Long id);
}
