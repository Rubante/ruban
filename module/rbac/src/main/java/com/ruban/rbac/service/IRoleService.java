package com.ruban.rbac.service;

import java.util.List;

import com.ruban.framework.dao.helper.Condition;
import com.ruban.framework.dao.helper.ResultInfo;
import com.ruban.rbac.backend.role.form.RoleForm;
import com.ruban.rbac.domain.authz.Role;
import com.ruban.rbac.vo.role.RoleVo;

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
    List<Role> selectAll();

    /**
     * 根据条件分页查询
     * 
     * @param dto
     * @return
     */
    ResultInfo<Role> selectByPage(Condition<Role> condition);

    /**
     * 根据条件查询
     * 
     * @param condition
     * @return
     */
    List<Role> selectByCondition(Condition<Role> condition);

    /**
     * 新建角色
     * 
     * @param personForm
     */
    void insert(RoleForm personForm);

    /**
     * 更新角色
     * 
     * @param AccountForm
     */
    int update(RoleForm personForm);

    /**
     * 根据ID删除角色
     * 
     * @param id
     * @return
     */
    int deleteById(Long id);

    /**
     * 根据ID批量删除角色
     * 
     * @param ids
     * @return
     */
    int deleteByIds(String[] ids);

    /**
     * 启用角色
     * 
     * @param id
     * @return
     */
    int enable(Long id);

    /**
     * 禁用角色
     * 
     * @param id
     * @return
     */
    int unable(Long id);

    /**
     * 为角色授权
     * 
     * @param roleVo
     * @return
     */
    int grant(RoleVo roleVo);

    /**
     * 根据ID获取角色
     * 
     * @param id
     * @return
     */
    Role findById(Long id);
}
