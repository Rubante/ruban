package com.ruban.rbac.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruban.framework.core.utils.commons.DateUtil;
import com.ruban.framework.core.utils.commons.RandomUtil;
import com.ruban.framework.dao.IRubanDao;
import com.ruban.framework.dao.helper.Condition;
import com.ruban.framework.dao.helper.ResultInfo;
import com.ruban.rbac.backend.role.form.RoleForm;
import com.ruban.rbac.dao.authz.IRoleMapper;
import com.ruban.rbac.domain.authz.Role;
import com.ruban.rbac.service.IRoleService;

@Service
public class RoleService implements IRoleService {

    @Autowired
    private IRoleMapper roleMapper;

    @Autowired
    private IRubanDao rubanDao;

    @Override
    public List<Role> selectAll() {
        return roleMapper.selectAll();
    }

    @Override
    public ResultInfo<Role> selectByPage(Condition<Role> condition) {

        ResultInfo<Role> result = rubanDao.findByPage(condition, roleMapper);
        return result;
    }

    @Override
    public List<Role> selectByCondition(Condition<Role> condition) {
        return roleMapper.selectWithCondition(condition);
    }

    @Override
    public void insert(RoleForm roleForm) {

        Role role = new Role();

        role.setName(roleForm.getName());
        role.setType(roleForm.getType());
        role.setCompanyId(roleForm.getCompanyId());
        role.setMemo(roleForm.getMemo());

        role.setAddTime(DateUtil.getToday());
        role.setModTime(DateUtil.getToday());
        role.setAddUserId(0L);
        role.setModUserId(0L);
        role.setUpdateLock(RandomUtil.getUpdateLock());

        roleMapper.insert(role);
    }

    @Override
    public int update(RoleForm roleForm) {

        Role role = findById(roleForm.getId());

        role.setName(roleForm.getName());
        role.setType(roleForm.getType());
        role.setCompanyId(roleForm.getCompanyId());
        role.setMemo(roleForm.getMemo());

        role.setModTime(DateUtil.getToday());
        role.setModUserId(0L);
        role.setUpdateLock(RandomUtil.getUpdateLock());

        role.setHoldLock(roleForm.getHoldLock());

        int result = roleMapper.update(role);

        return result;
    }

    /**
     * 根据ID删除人员
     * 
     * @param id
     * @return
     */
    public int deleteById(Long id) {
        return roleMapper.deleteById(id);
    }

    /**
     * 批量删除人员
     * 
     * @param ids
     * @return
     */
    public int deleteByIds(String[] ids) {
        return roleMapper.deleteByIds(ids);
    }

    /**
     * 排序人员
     * 
     * @param id
     * @return
     */
    public int sortByIds(String[] ids) {
        int count = 0;
        for (int i = 0; ids != null && i < ids.length; i++) {
            Long id = Long.parseLong(ids[i]);
            count += roleMapper.updateOrderCode(id, i + 1);
        }

        return count;
    }

    @Override
    public Role findById(Long id) {
        return roleMapper.findById(id);
    }
}
