package com.ruban.framework.core.dto;

import java.util.List;

/**
 * 通用结果接口
 * 
 * @author ruban
 * 
 * @param <T>
 */
public interface IResultDTO<T> {

    /**
     * 获取总页数
     * 
     * @return
     */
    public String getTotalPages();

    /**
     * 获取总记录数
     * 
     * @return
     */
    public int getTotalRecords();

    /**
     * 获取列表结果
     * 
     * @return
     */
    public List<T> getRecordList();
}