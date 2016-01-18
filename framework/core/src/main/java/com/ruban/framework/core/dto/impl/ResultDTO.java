package com.ruban.framework.core.dto.impl;

import java.util.List;

import com.ruban.framework.core.dto.IResultDTO;

/**
 * 查询结果DTO
 * 
 * @author ruban
 * 
 * @param <T>
 */
public class ResultDTO<T> implements IResultDTO<T> {

    /**
     * 总页数
     */
    protected String totalPages;
    /**
     * 总记录数
     */
    protected int totalRecords;
    /**
     * 当前页记录列表
     */
    protected List<T> recordList;

    @Override
    public String getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(String totalPages) {
        this.totalPages = totalPages;
    }

    @Override
    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    @Override
    public List<T> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<T> recordList) {
        this.recordList = recordList;
    }
}
