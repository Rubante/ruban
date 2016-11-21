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

    public <T> List<T> selectAll();

    public <T> List<T> selectAll(RowBounds rowBounds);

    public <T> List<T> selectWithCondition(RowBounds rowBounds, Condition<T> condition);

    public <T> void insert(T obj);
}
