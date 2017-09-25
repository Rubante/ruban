package com.ruban.rbac.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruban.framework.core.utils.commons.StringUtil;
import com.ruban.framework.dao.IRubanDao;
import com.ruban.framework.dao.helper.Condition;
import com.ruban.framework.dao.helper.ResultInfo;
import com.ruban.rbac.dao.authz.IRolePermissionMapper;
import com.ruban.rbac.domain.authz.RolePermission;
import com.ruban.rbac.service.IRolePermissionService;
import com.ruban.rbac.vo.permission.PermissionVo;

@Service
public class RolePermissionService implements IRolePermissionService {

    @Autowired
    private IRolePermissionMapper permissionMapper;

    @Autowired
    private IRubanDao rubanDao;

    @Override
    public List<RolePermission> selectAll() {
        return permissionMapper.selectAll();
    }

    @Override
    public ResultInfo<RolePermission> selectByPage(Condition<RolePermission> condition) {

        ResultInfo<RolePermission> result = rubanDao.findByPage(condition, permissionMapper);
        return result;
    }

    @Override
    public List<RolePermission> selectByCondition(Condition<RolePermission> condition) {
        return permissionMapper.selectWithCondition(condition);
    }

    @Override
    public void insert(PermissionVo permissionVo) {

        RolePermission permission = new RolePermission();

        permissionMapper.insert(permission);
    }

    @Override
    public int update(PermissionVo permissionVo) {

        RolePermission permission = findById(permissionVo.getId());

        int result = permissionMapper.update(permission);

        return result;
    }

    /**
     * 根据ID删除权限
     * 
     * @param id
     * @return
     */
    public int deleteById(Long id) {
        return permissionMapper.deleteById(id);
    }

    /**
     * 批量删除权限
     * 
     * @param ids
     * @return
     */
    public int deleteByIds(String[] ids) {
        return permissionMapper.deleteByIds(ids);
    }

    @Override
    public int deleteByRoleId(Long roleId) {
        return permissionMapper.deleteByRoleId(roleId);
    }

    /**
     * 排序权限
     * 
     * @param id
     * @return
     */
    public int sortByIds(String[] ids) {
        int count = 0;
        for (int i = 0; ids != null && i < ids.length; i++) {
            Long id = Long.parseLong(ids[i]);
            count += permissionMapper.updateOrderCode(id, i + 1);
        }

        return count;
    }

    @Override
    public RolePermission findById(Long id) {
        return permissionMapper.findById(id);
    }

    @Override
    public int grant(PermissionVo vo) {
        int count = 0;

        if (vo.getResources() != null) {
            for (int i = 0; i < vo.getResources().size(); i++) {
                RolePermission rolePermission = new RolePermission();
                boolean valid = true;
                String resourceId = vo.getResources().get(i);
                if (StringUtil.isDigit(resourceId)) {
                    rolePermission.setRoleId(vo.getRoleId());
                    rolePermission.setResourceId(Long.parseLong(resourceId));
                } else {
                    valid = false;
                }

                if (vo.getFlags() != null) {
                    String flag = vo.getFlags().get(i);
                    if (valid && StringUtil.isDigit(flag)) {
                        rolePermission.setFlag(Integer.parseInt(flag));
                    } else {
                        valid = false;
                    }
                }

                if (valid) {
                    // 常规属性
                    rolePermission.setModTime(vo.getModTime());
                    rolePermission.setModUserId(vo.getModUserId());

                    count += permissionMapper.insert(rolePermission);
                }
            }
        }

        return count;
    }

}
