package com.ruban.framework.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.ruban.framework.dao.helper.Condition;

/**
 * 共用mapper
 * 
 * @author yjwang
 *
 */
public interface IRubanMapper {

    /**
     * 无条件查询
     * 
     * @return
     */
    public <T> List<T> selectAll();

    /**
     * 分页查询
     * 
     * @param rowBounds
     * @return
     */
    public <T> List<T> selectAll(RowBounds rowBounds);

    /**
     * 带条件分页查询
     * 
     * @param rowBounds
     * @param condition
     * @return
     */
    public <T> List<T> selectWithCondition(RowBounds rowBounds, Condition<T> condition);

    /**
     * 根据条件
     * 
     * @param condition
     * @return
     */
    public <T> List<T> selectWithCondition(Condition<T> condition);

    /**
     * 插入数据
     * 
     * @param obj
     */
    public <T> Long insert(T obj);

    /**
     * 更新数据
     * 
     * @param obj
     */
    public <T> int update(T obj);

    /**
     * 单条删除
     * 
     * @param id
     * @return
     */
    public int deleteById(Long id);

    /**
     * 多条删除
     * 
     * @param ids
     * @return
     */
    public int deleteByIds(String[] ids);

    /**
     * 更新组织机构的顺序
     * 
     * @param ids
     * @return
     */
    public int updateOrderCode(Long id, int orderCode);

    /**
     * 单条查询
     * 
     * @param id
     * @return
     */
    public <T> T findById(Long id);
}
