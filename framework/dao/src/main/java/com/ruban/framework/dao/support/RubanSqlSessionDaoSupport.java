package com.ruban.framework.dao.support;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;

/**
 * sqlSession扩展类自扩充
 * 
 * @author ruban
 *
 */
public class RubanSqlSessionDaoSupport extends SqlSessionDaoSupport {

    /**
     * 规定namespace插入数据
     * 
     * @param parameter
     * @return
     */
    public int insert(Object parameter) {
        return insert(getClass().getName() + ".insert", parameter);
    }

    /**
     * 自定namespace插入数据
     * 
     * @param statementName
     * @param parameter
     * @return
     */
    public int insert(String statementName, Object parameter) {
        return getSqlSession().insert(statementName, parameter);
    }

    /**
     * 规定namespace删除数据
     * 
     * @param parameter
     * @return
     */
    public int delete(Object parameter) {
        return delete(getClass().getName() + ".delete", parameter);
    }

    /**
     * 自定义namespace删除数据
     * 
     * @param statementName
     * @param parameter
     * @return
     */
    public int delete(String statementName, Object parameter) {
        return getSqlSession().delete(statementName, parameter);
    }

    /**
     * 规定格式依据参数（ID）删除数据
     * 
     * @param parameter
     * @return
     */
    public int deleteById(Object parameter) {
        return delete(getClass().getName() + ".deleteById", parameter);
    }

    /**
     * 自定义namespace依据参数(ID)删除数据
     * 
     * @param statementName
     * @param parameter
     * @return
     */
    public int deleteById(String statementName, Object parameter) {
        return getSqlSession().delete(statementName, parameter);
    }

    /**
     * 规定namespace更新数据
     * 
     * @param parameter
     * @return
     */
    public int update(Object parameter) {
        return update(getClass().getName() + ".update", parameter);
    }

    /**
     * 自定namespace更新数据
     * 
     * @param parameter
     * @return
     */
    public int update(String statementName, Object parameter) {
        return getSqlSession().update(statementName, parameter);
    }

    /**
     * 规定namespace根据参数(ID)更新数据
     * 
     * @param parameter
     * @return
     */
    public int updateById(Object parameter) {
        return update(getClass().getName() + ".updateById", parameter);
    }

    /**
     * 自定义namespace根据参数(ID)更新数据
     * 
     * @param statementName
     * @param parameter
     * @return
     */
    public int updateById(String statementName, Object parameter) {
        return getSqlSession().update(statementName, parameter);
    }

    /**
     * 规定namespace根据参数(ID)查找数据
     * 
     * @param parameter
     * @return
     */
    public <T> T findById(Object parameter) {
        return findById(getClass().getName() + ".findById", parameter);
    }

    /**
     * 自定义namespace根据参数（ID）查找数据
     * 
     * @param statementName
     * @param parameter
     * @return
     */
    public <T> T findById(String statementName, Object parameter) {
        return getSqlSession().selectOne(statementName, parameter);
    }

    /**
     * 规定namespace查找数据
     * 
     * @param parameter
     * @return
     */
    public <T> T find(Object parameter) {
        return find(getClass().getName() + ".find", parameter);
    }

    /**
     * 自定义namespace查询数据
     * 
     * @param statementName
     * @param parameter
     * @return
     */
    public <T> T find(String statementName, Object parameter) {
        return getSqlSession().selectOne(statementName, parameter);
    }

    /**
     * 
     * 固定namespace查询数据
     * 
     * @param parameter
     * @return
     */
    public <T> List<T> queryList(Map<String, Object> parameter) {
        return queryList(getClass().getName() + ".queryList", parameter);
    }

    /**
     * 自定义格式查询数据
     * 
     * @param statementName
     * @param parameter
     * @return
     */
    public <T> List<T> queryList(String statementName, Map<String, Object> parameter) {
        return getSqlSession().selectList(statementName, parameter);
    }
}
