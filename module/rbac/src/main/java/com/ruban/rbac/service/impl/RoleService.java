package com.ruban.rbac.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruban.common.dict.CommonState;
import com.ruban.framework.core.utils.commons.DateUtil;
import com.ruban.framework.core.utils.commons.RandomUtil;
import com.ruban.framework.dao.IRubanDao;
import com.ruban.framework.dao.helper.Condition;
import com.ruban.framework.dao.helper.ResultInfo;
import com.ruban.rbac.backend.role.form.RoleForm;
import com.ruban.rbac.dao.authz.IRoleMapper;
import com.ruban.rbac.domain.authz.Role;
import com.ruban.rbac.service.IRolePermissionService;
import com.ruban.rbac.service.IRoleService;
import com.ruban.rbac.vo.permission.PermissionVo;
import com.ruban.rbac.vo.role.RoleVo;

@Service
public class RoleService implements IRoleService {

    @Autowired
    private IRoleMapper roleMapper;

    @Autowired
    private IRolePermissionService permissionServcie;

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

        BeanUtils.copyProperties(roleForm, role);

        role.setUpdateLock(RandomUtil.getUpdateLock());

        roleMapper.insert(role);
    }

    @Override
    public int update(RoleForm roleForm) {

        Role role = findById(roleForm.getId());

        BeanUtils.copyProperties(roleForm, role);

        role.setUpdateLock(RandomUtil.getUpdateLock());

        int result = roleMapper.update(role);

        return result;
    }

    /**
     * 根据ID删除角色
     * 
     * @param id
     * @return
     */
    public int deleteById(Long id) {
        return roleMapper.deleteById(id);
    }

    /**
     * 批量删除角色
     * 
     * @param ids
     * @return
     */
    public int deleteByIds(String[] ids) {
        return roleMapper.deleteByIds(ids);
    }

    @Override
    public int enable(Long id) {
        Role role = findById(id);

        Role vo = new Role();
        vo.setId(role.getId());
        vo.setModTime(DateUtil.getNowTime());
        vo.setHoldLock(role.getUpdateLock());
        vo.setUpdateLock(RandomUtil.getUpdateLock());
        vo.setState(CommonState.Enable.getValue());

        return roleMapper.updateState(vo);
    }

    @Override
    public int unable(Long id) {
        Role role = findById(id);

        Role vo = new Role();
        vo.setId(role.getId());
        vo.setModTime(DateUtil.getNowTime());
        vo.setHoldLock(role.getUpdateLock());
        vo.setUpdateLock(RandomUtil.getUpdateLock());
        vo.setState(CommonState.Unable.getValue());

        return roleMapper.updateState(vo);
    }

    @Override
    public int grant(RoleVo roleVo) {

        // 删除原有的授权
        permissionServcie.deleteByRoleId(roleVo.getId());

        PermissionVo vo = new PermissionVo();
        vo.setRoleId(roleVo.getId());
        vo.setResources(roleVo.getResources());
        vo.setFlags(roleVo.getFlags());
        vo.setModTime(roleVo.getModTime());
        vo.setModUserId(roleVo.getModUserId());

        return permissionServcie.grant(vo);
    }

    @Override
    public Role findById(Long id) {
        return roleMapper.findById(id);
    }
}
