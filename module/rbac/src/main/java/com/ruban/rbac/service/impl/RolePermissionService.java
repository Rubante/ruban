package com.ruban.rbac.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruban.framework.core.utils.commons.DateUtil;
import com.ruban.framework.core.utils.commons.RandomUtil;
import com.ruban.framework.dao.IRubanDao;
import com.ruban.framework.dao.helper.Condition;
import com.ruban.framework.dao.helper.ResultInfo;
import com.ruban.rbac.backend.permission.form.PermissionForm;
import com.ruban.rbac.dao.authz.IPermissionMapper;
import com.ruban.rbac.domain.authz.Permission;
import com.ruban.rbac.service.IPermissionService;

@Service
public class PermissionService implements IPermissionService {

    @Autowired
    private IPermissionMapper permissionMapper;

    @Autowired
    private IRubanDao rubanDao;

    @Override
    public List<Permission> selectAll() {
        return permissionMapper.selectAll();
    }

    @Override
    public ResultInfo<Permission> selectByPage(Condition<Permission> condition) {

        ResultInfo<Permission> result = rubanDao.findByPage(condition, permissionMapper);
        return result;
    }

    @Override
    public List<Permission> selectByCondition(Condition<Permission> condition) {
        return permissionMapper.selectWithCondition(condition);
    }

    @Override
    public void insert(PermissionForm permissionForm) {

        Permission permission = new Permission();

        permission.setType(permissionForm.getType());
        permission.setName(permissionForm.getName());
        permission.setCompanyId(permissionForm.getCompanyId());
        permission.setMemo(permissionForm.getMemo());

        permission.setAddTime(DateUtil.getToday());
        permission.setModTime(DateUtil.getToday());
        permission.setAddUserId(0L);
        permission.setModUserId(0L);
        permission.setUpdateLock(RandomUtil.getUpdateLock());

        permissionMapper.insert(permission);
    }

    @Override
    public int update(PermissionForm permissionForm) {

        Permission permission = findById(permissionForm.getId());

        permission.setType(permissionForm.getType());
        permission.setName(permissionForm.getName());
        permission.setMemo(permissionForm.getMemo());
        permission.setCompanyId(permissionForm.getCompanyId());

        permission.setModTime(DateUtil.getToday());
        permission.setModUserId(0L);
        permission.setUpdateLock(RandomUtil.getUpdateLock());

        permission.setHoldLock(permissionForm.getHoldLock());

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
    public Permission findById(Long id) {
        return permissionMapper.findById(id);
    }
}
