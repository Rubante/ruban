package com.ruban.rbac.service;

import java.util.List;

import com.ruban.framework.dao.helper.Condition;
import com.ruban.framework.dao.helper.ResultInfo;
import com.ruban.rbac.domain.authz.RolePermission;
import com.ruban.rbac.vo.permission.PermissionVo;

/**
 * 角色权限 <br />
 * 角色拥有权限，权限在系统中跟资源是一致的<br />
 * 在角色上进行授权管理，角色可以继承或排异其他角色<br />
 * 
 * @author ruban
 *
 */
public interface IRolePermissionService {

    /**
     * 查询所有的权限列表
     * 
     * @return
     */
    List<RolePermission> selectAll();

    /**
     * 根据条件分页查询
     * 
     * @param dto
     * @return
     */
    ResultInfo<RolePermission> selectByPage(Condition<RolePermission> condition);

    /**
     * 根据条件查询
     * 
     * @param condition
     * @return
     */
    List<RolePermission> selectByCondition(Condition<RolePermission> condition);

    /**
     * 新建权限
     * 
     * @param permissionVo
     */
    void insert(PermissionVo permissionVo);

    /**
     * 更新权限
     * 
     * @param permissionVo
     */
    int update(PermissionVo permissionVo);

    /**
     * 根据ID删除权限
     * 
     * @param id
     * @return
     */
    int deleteById(Long id);

    /**
     * 根据ID批量删除权限
     * 
     * @param ids
     * @return
     */
    int deleteByIds(String[] ids);

    /**
     * 根据RoleId批量删除权限
     * 
     * @param roleId
     * @return
     */
    int deleteByRoleId(Long roleId);

    /**
     * 根据ID排序权限
     * 
     * @param ids
     * @return
     */
    int sortByIds(String[] ids);

    /**
     * 根据ID获取权限
     * 
     * @param id
     * @return
     */
    RolePermission findById(Long id);

    /**
     * 角色授权
     * 
     * @param vo
     * @return
     */
    int grant(PermissionVo vo);
}
