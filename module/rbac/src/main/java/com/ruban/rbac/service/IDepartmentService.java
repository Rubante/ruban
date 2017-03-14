package com.ruban.rbac.service;

import java.util.List;

import com.ruban.framework.dao.helper.Condition;
import com.ruban.framework.dao.helper.ResultInfo;
import com.ruban.rbac.backend.department.form.DepartmentForm;
import com.ruban.rbac.domain.organization.Department;

/**
 * 组织机构：部门
 * 
 * @author ruban
 *
 */
public interface IDepartmentService {

    /**
     * 查询所有的部门列表
     * 
     * @return
     */
    public List<Department> selectAll();

    /**
     * 根据条件分页查询
     * 
     * @param dto
     * @return
     */
    public ResultInfo<Department> selectByPage(Condition<Department> condition);

    /**
     * 根据条件查询
     * 
     * @param condition
     * @return
     */
    public List<Department> selectByCondition(Condition<Department> condition);

    /**
     * 新建部门
     * 
     * @param departmentForm
     */
    public void insert(DepartmentForm departmentForm);

    /**
     * 更新部门
     * 
     * @param departmentForm
     */
    public int update(DepartmentForm departmentForm);

    /**
     * 根据ID删除部门
     * 
     * @param id
     * @return
     */
    public int deleteById(Long id);

    /**
     * 根据ID批量删除部门
     * 
     * @param ids
     * @return
     */
    public int deleteByIds(String[] ids);

    /**
     * 根据ID排序部门
     * 
     * @param ids
     * @return
     */
    public int sortByIds(String[] ids);

    /**
     * 根据ID获取部门
     * 
     * @param id
     * @return
     */
    public Department findById(Long id);

    /**
     * 获取部门json串
     * 
     * @param rootId
     * @return
     */
    public String getJsonTree(Long rootId);
}
