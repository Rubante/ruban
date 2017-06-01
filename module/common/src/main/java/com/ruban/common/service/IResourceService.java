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
    List<Resource> selectAll();

    /**
     * 根据条件分页查询
     * 
     * @param dto
     * @return
     */
    ResultInfo<Resource> selectByPage(Condition<Resource> condition);

    /**
     * 根据条件查询
     * 
     * @param condition
     * @return
     */
    List<Resource> selectByCondition(Condition<Resource> condition);

    /**
     * 新建资源
     * 
     * @param resourceVo
     */
    void insert(ResourceVo resourceVo);

    /**
     * 更新资源
     * 
     * @param resourceVo
     */
    int update(ResourceVo resourceVo);

    /**
     * 更新子节点数量
     * 
     * @param resourceVo
     * @return
     */
    int updateChildrenNum(ResourceVo resourceVo);

    /**
     * 根据ID删除资源
     * 
     * @param id
     * @return
     */
    int deleteById(Long id);

    /**
     * 根据ID批量删除资源
     * 
     * @param ids
     * @return
     */
    int deleteByIds(String[] ids);

    /**
     * 启用资源
     * 
     * @param id
     * @return
     */
    int enable(Long id);

    /**
     * 禁用资源
     * 
     * @param id
     * @return
     */
    int unable(Long id);

    /**
     * 根据ID及层级进行资源排序
     * 
     * @param ids
     * @param levels
     * @param paths
     * @param parentIds
     * @return
     */
    int sortByIds(String[] ids, String[] levels, String[] paths, String[] parentIds);

    /**
     * 根据ID获取资源
     * 
     * @param id
     * @return
     */
    Resource findById(Long id);

    /**
     * 根据ID获取其双亲列表，双亲倒序列表
     * 
     * @param resourceId
     * @return
     */
    List<Resource> getParents(Long resourceId);

    /**
     * 根据ID查询其孩子列表
     * 
     * @param resourceId
     * @return
     */
    List<Resource> getChildren(Long resourceId);

    /**
     * 设置路径及层级
     * 
     * @param resource
     */
    void buildLevelAndPath(Resource resource);

    /**
     * 设置排序字段
     * 
     * @param resource
     * @param vo
     */
    void buildOrderCode(Resource resource, ResourceVo vo);

}
