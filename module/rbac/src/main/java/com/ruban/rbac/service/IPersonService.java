package com.ruban.rbac.service;

import java.util.List;

import com.ruban.framework.dao.helper.Condition;
import com.ruban.framework.dao.helper.ResultInfo;
import com.ruban.rbac.backend.person.form.PersonForm;
import com.ruban.rbac.domain.organization.Person;

/**
 * 人员
 * 
 * @author ruban
 *
 */
public interface IPersonService {

    /**
     * 查询所有的人员列表
     * 
     * @return
     */
    public List<Person> selectAll();

    /**
     * 根据条件分页查询
     * 
     * @param dto
     * @return
     */
    public ResultInfo<Person> selectByPage(Condition<Person> condition);

    /**
     * 根据条件查询
     * 
     * @param condition
     * @return
     */
    public List<Person> selectByCondition(Condition<Person> condition);

    /**
     * 新建人员
     * 
     * @param personForm
     */
    public void insert(PersonForm personForm);

    /**
     * 更新人员
     * 
     * @param AccountForm
     */
    public int update(PersonForm personForm);

    /**
     * 根据ID删除人员
     * 
     * @param id
     * @return
     */
    public int deleteById(Long id);

    /**
     * 根据ID批量删除人员
     * 
     * @param ids
     * @return
     */
    public int deleteByIds(String[] ids);

    /**
     * 根据ID排序人员
     * 
     * @param ids
     * @return
     */
    public int sortByIds(String[] ids);

    /**
     * 根据ID获取人员
     * 
     * @param id
     * @return
     */
    public Person findById(Long id);
}
