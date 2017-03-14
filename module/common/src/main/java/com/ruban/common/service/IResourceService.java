package com.ruban.common.service;

import java.util.List;

import com.ruban.common.domain.Resource;
import com.ruban.common.vo.ResourceVo;
import com.ruban.framework.dao.helper.Condition;
import com.ruban.framework.dao.helper.ResultInfo;

/**
 * 资源
 * 
 * @author ruban
 *
 */
public interface IResourceService {

    /**
     * 查询所有的资源列表
     * 
     * @return
     */
    public List<Resource> selectAll();

    /**
     * 根据条件分页查询
     * 
     * @param dto
     * @return
     */
    public ResultInfo<Resource> selectByPage(Condition<Resource> condition);

    /**
     * 根据条件查询
     * 
     * @param condition
     * @return
     */
    public List<Resource> selectByCondition(Condition<Resource> condition);

    /**
     * 新建资源
     * 
     * @param resourceVo
     */
    public void insert(ResourceVo resourceVo);

    /**
     * 更新资源
     * 
     * @param resourceVo
     */
    public int update(ResourceVo resourceVo);

    /**
     * 根据ID删除资源
     * 
     * @param id
     * @return
     */
    public int deleteById(Long id);

    /**
     * 根据ID批量删除资源
     * 
     * @param ids
     * @return
     */
    public int deleteByIds(String[] ids);

    /**
     * 根据ID排序资源
     * 
     * @param ids
     * @return
     */
    public int sortByIds(String[] ids);

    /**
     * 根据ID获取资源
     * 
     * @param id
     * @return
     */
    public Resource findById(Long id);
}
