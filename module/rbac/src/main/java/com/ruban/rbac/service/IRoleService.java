package com.ruban.rbac.service;

import java.util.List;

import com.ruban.framework.dao.helper.Condition;
import com.ruban.framework.dao.helper.ResultInfo;
import com.ruban.rbac.backend.role.form.RoleForm;
import com.ruban.rbac.domain.authz.Role;

/**
 * 角色
 * 
 * @author ruban
 *
 */
public interface IRoleService {

    /**
     * 查询所有的角色列表
     * 
     * @return
     */
    public List<Role> selectAll();

    /**
     * 根据条件分页查询
     * 
     * @param dto
     * @return
     */
    public ResultInfo<Role> selectByPage(Condition<Role> condition);

    /**
     * 根据条件查询
     * 
     * @param condition
     * @return
     */
    public List<Role> selectByCondition(Condition<Role> condition);

    /**
     * 新建角色
     * 
     * @param personForm
     */
    public void insert(RoleForm personForm);

    /**
     * 更新角色
     * 
     * @param AccountForm
     */
    public int update(RoleForm personForm);

    /**
     * 根据ID删除角色
     * 
     * @param id
     * @return
     */
    public int deleteById(Long id);

    /**
     * 根据ID批量删除角色
     * 
     * @param ids
     * @return
     */
    public int deleteByIds(String[] ids);

    /**
     * 根据ID排序角色
     * 
     * @param ids
     * @return
     */
    public int sortByIds(String[] ids);

    /**
     * 根据ID获取角色
     * 
     * @param id
     * @return
     */
    public Role findById(Long id);
}
