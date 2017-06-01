package com.ruban.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ruban.common.domain.Resource;
import com.ruban.framework.dao.IRubanMapper;
import com.ruban.framework.dao.support.RubanDaoRepository;

@RubanDaoRepository
public interface IResourceMapper extends IRubanMapper {

    /**
     * 更新叶子节点数
     * 
     * @param resource
     */
    int updateChildrenNum(Resource resource);

    /**
     * 增加叶子节点的数量
     * 
     * @param resource
     * @return
     */
    int increaseChildrenNum(Resource resource);

    /**
     * 减少叶子节点的数量
     * 
     * @param resource
     * @return
     */
    int decreaseChildrenNum(Resource resource);

    /**
     * 获取下级节点 <br />
     * 列表顺序为从上级到下级<br />
     * 
     * @param resourceId
     * @return
     */
    List<Resource> findChildren(Long resourceId);

    /**
     * 获取下级(下一级)节点个数
     * 
     * @param resourceId
     * @return
     */
    int findChildrenNum(Long resourceId);

    /**
     * 获取最后的兄弟节点
     * 
     * @param resource
     * @return
     */
    Resource findLastSlibing(Resource resource);

    /**
     * 更新节点及子节点的显示标识
     * 
     * @param resource
     * @return
     */
    int updateDisplayFlag(Resource resource);

    /**
     * 更新排序相关字段
     * 
     * @param id
     * @param orderCode
     * @param path
     * @param parentId
     * @return
     */
    int updateOrder(@Param("id") Long id, @Param("orderCode") String orderCode, @Param("path") String path,
            @Param("parentId") String parentId);
}