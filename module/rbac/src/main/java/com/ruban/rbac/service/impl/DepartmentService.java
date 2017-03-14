package com.ruban.rbac.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruban.framework.core.utils.commons.DateUtil;
import com.ruban.framework.core.utils.commons.RandomUtil;
import com.ruban.framework.dao.IRubanDao;
import com.ruban.framework.dao.helper.Condition;
import com.ruban.framework.dao.helper.ResultInfo;
import com.ruban.rbac.backend.department.form.DepartmentForm;
import com.ruban.rbac.backend.department.form.SearchForm;
import com.ruban.rbac.dao.organization.IDepartmentMapper;
import com.ruban.rbac.domain.organization.Department;
import com.ruban.rbac.service.IDepartmentService;

@Service
public class DepartmentService implements IDepartmentService {

    @Autowired
    private IDepartmentMapper departmentMapper;

    @Autowired
    private IRubanDao rubanDao;

    @Override
    public List<Department> selectAll() {
        return departmentMapper.selectAll();
    }

    @Override
    public ResultInfo<Department> selectByPage(Condition<Department> condition) {

        ResultInfo<Department> result = rubanDao.findByPage(condition, departmentMapper);
        return result;
    }

    @Override
    public List<Department> selectByCondition(Condition<Department> condition) {
        return departmentMapper.selectWithCondition(condition);
    }

    @Override
    public void insert(DepartmentForm departmentForm) {

        Department department = new Department();

        department.setCode(departmentForm.getCode());
        department.setName(departmentForm.getName());
        department.setSimpleName(departmentForm.getSimpleName());
        department.setPersonId(departmentForm.getPersonId());
        department.setSetupTime(departmentForm.getSetupTime());
        department.setState(departmentForm.getState());
        department.setMemo(departmentForm.getMemo());
        department.setDepartmentId(departmentForm.getDepartmentId());
        department.setCompanyId(departmentForm.getCompanyId());
        department.setAddTime(DateUtil.getNowTime());
        department.setAddUserId(0L);
        department.setModTime(DateUtil.getNowTime());
        department.setModUserId(0L);
        department.setUpdateLock(RandomUtil.getUpdateLock());

        // 父节点
        if (departmentForm.getDepartmentId() != null && departmentForm.getDepartmentId() != 0) {
            Long parentId = new Long(departmentForm.getDepartmentId());
            department.setDepartmentId(parentId);

            int count = departmentMapper.countByParentId(parentId);
            department.setOrderCode(count + 1);

            // 更新父节点的叶子节点数量
            updateHasChildren(departmentForm);
        } else {
            department.setCode(departmentForm.getCode());
        }

        departmentMapper.insert(department);
    }

    @Override
    public int update(DepartmentForm departmentForm) {

        Department department = findById(departmentForm.getId());
        department.setCode(departmentForm.getCode());
        department.setName(departmentForm.getName());
        department.setSimpleName(departmentForm.getSimpleName());
        department.setPersonId(departmentForm.getPersonId());
        department.setSetupTime(departmentForm.getSetupTime());
        department.setState(departmentForm.getState());
        department.setMemo(departmentForm.getMemo());
        department.setPersonId(departmentForm.getPersonId());
        department.setDepartmentId(departmentForm.getDepartmentId());
        department.setCompanyId(departmentForm.getCompanyId());
        department.setModTime(DateUtil.getNowTime());
        department.setModUserId(0L);
        department.setUpdateLock(RandomUtil.getUpdateLock());
        department.setHoldLock(departmentForm.getHoldLock());
        // 父节点
        if (departmentForm.getDepartmentId() != null && departmentForm.getDepartmentId() != null) {
            department.setDepartmentId(departmentForm.getDepartmentId());

            // 更新父节点的叶子节点数量
            updateHasChildren(departmentForm);
        }

        int result = departmentMapper.update(department);

        return result;
    }

    /**
     * 更新叶子节点的数量
     * 
     * @param departmentForm
     */
    private void updateHasChildren(DepartmentForm departmentForm) {
        Long parentId = new Long(departmentForm.getDepartmentId());
        Department parent = departmentMapper.findById(parentId);
        int count = departmentMapper.countByParentId(parentId);
        parent.setHasChildren(count);
        departmentMapper.updateHasChildren(parent);
    }

    /**
     * 根据ID删除组织机构
     * 
     * @param id
     * @return
     */
    public int deleteById(Long id) {
        return departmentMapper.deleteById(id);
    }

    /**
     * 批量删除组织机构
     * 
     * @param ids
     * @return
     */
    public int deleteByIds(String[] ids) {
        return departmentMapper.deleteByIds(ids);
    }

    /**
     * 排序组织机构
     * 
     * @param id
     * @return
     */
    public int sortByIds(String[] ids) {
        int count = 0;
        for (int i = 0; ids != null && i < ids.length; i++) {
            Long id = Long.parseLong(ids[i]);
            count += departmentMapper.updateOrderCode(id, i + 1);
        }

        return count;
    }

    @Override
    public Department findById(Long id) {
        return departmentMapper.findById(id);
    }

    @Override
    public String getJsonTree(Long parentId) {
        SearchForm searchForm = new SearchForm();
        searchForm.setDepartmentId(parentId);
        List<Department> list = departmentMapper.selectWithCondition(searchForm);

        List<String> ztree = new ArrayList<>();
        // 构建组织机构树
        for (int i = 0; list != null && i < list.size(); i++) {
            Department department = list.get(i);
            String node = "{id:" + department.getId() + ",pId:" + department.getDepartmentId() + ",name:'"
                    + department.getName() + "'";
            // 图标
            // node += ",iconSkin:'department_tree_icon_" + department.getType()
            // +
            // "'";

            if (department.getHasChildren() > 0) {
                node += ",open: true}";
            } else {
                node += "}";
            }
            ztree.add(node);
        }

        // 无数据
        if (ztree.size() == 0) {
            String node = "{name:'无'}";
            ztree.add(node);
        }

        String[] strArr = ztree.toArray(new String[0]);

        return Arrays.toString(strArr);
    }
}
