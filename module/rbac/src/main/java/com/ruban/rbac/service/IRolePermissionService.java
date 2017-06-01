package com.ruban.rbac.service;

import java.util.List;

import com.ruban.framework.dao.helper.Condition;
import com.ruban.framework.dao.helper.ResultInfo;
import com.ruban.rbac.backend.permission.form.PermissionForm;
import com.ruban.rbac.domain.authz.Permission;

/**
 * 权限
 * 
 * @author ruban
 *
 */
public interface IPermissionService {

    /**
     * 查询所有的权限列表
     * 
     * @return
     */
    public List<Permission> selectAll();

    /**
     * 根据条件分页查询
     * 
     * @param dto
     * @return
     */
    public ResultInfo<Permission> selectByPage(Condition<Permission> condition);

    /**
     * 根据条件查询
     * 
     * @param condition
     * @return
     */
    public List<Permission> selectByCondition(Condition<Permission> condition);

    /**
     * 新建权限
     * 
     * @param personForm
     */
    public void insert(PermissionForm personForm);

    /**
     * 更新权限
     * 
     * @param AccountForm
     */
    public int update(PermissionForm personForm);

    /**
     * 根据ID删除权限
     * 
     * @param id
     * @return
     */
    public int deleteById(Long id);

    /**
     * 根据ID批量删除权限
     * 
     * @param ids
     * @return
     */
    public int deleteByIds(String[] ids);

    /**
     * 根据ID排序权限
     * 
     * @param ids
     * @return
     */
    public int sortByIds(String[] ids);

    /**
     * 根据ID获取权限
     * 
     * @param id
     * @return
     */
    public Permission findById(Long id);
}
