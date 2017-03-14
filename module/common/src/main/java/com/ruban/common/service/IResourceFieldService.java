package com.ruban.common.service;

import java.util.List;

import com.ruban.common.domain.ResourceField;
import com.ruban.common.vo.ResourceFieldVo;
import com.ruban.framework.dao.helper.Condition;
import com.ruban.framework.dao.helper.ResultInfo;

/**
 * 资源属性
 * 
 * @author ruban
 *
 */
public interface IResourceFieldService {

    /**
     * 查询所有的资源属性列表
     * 
     * @return
     */
    public List<ResourceField> selectAll();

    /**
     * 根据条件分页查询
     * 
     * @param dto
     * @return
     */
    public ResultInfo<ResourceField> selectByPage(Condition<ResourceField> condition);

    /**
     * 根据条件查询
     * 
     * @param condition
     * @return
     */
    public List<ResourceField> selectByCondition(Condition<ResourceField> condition);

    /**
     * 新建资源属性
     * 
     * @param resourceFieldVo
     */
    public void insert(ResourceFieldVo resourceFieldVo);

    /**
     * 更新资源属性
     * 
     * @param resourceFieldVo
     */
    public int update(ResourceFieldVo resourceFieldVo);

    /**
     * 根据ID删除资源属性
     * 
     * @param id
     * @return
     */
    public int deleteById(Long id);

    /**
     * 根据ID批量删除资源属性
     * 
     * @param ids
     * @return
     */
    public int deleteByIds(String[] ids);

    /**
     * 根据ID排序资源属性
     * 
     * @param ids
     * @return
     */
    public int sortByIds(String[] ids);

    /**
     * 根据ID获取资源属性
     * 
     * @param id
     * @return
     */
    public ResourceField findById(Long id);
}
