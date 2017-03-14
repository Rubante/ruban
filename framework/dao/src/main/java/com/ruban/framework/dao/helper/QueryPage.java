package com.ruban.framework.dao.helper;

import java.io.Serializable;

/**
 * 分页信息
 * 
 * @author ruban
 * 
 * @param <T>
 */
public class QueryPage<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 当前页数
     */
    protected int pageNum = 1;

    /**
     * 每页记录数
     */
    protected int pageSize = 10;

    /**
     * 查询起始数
     * 
     */
    protected int startIndex = 0;

    /**
     * 排序的字段
     */
    protected String sortKey;
    /**
     * 排序方式：默认 正序
     */
    protected String ascDesc = "asc";

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStartIndex() {
        return (getPageNum() - 1) * getPageSize();
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public String getAscDesc() {
        return ascDesc;
    }

    public void setAscDesc(String ascDesc) {
        this.ascDesc = ascDesc;
    }

    public String getSortKey() {
        return sortKey;
    }

    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
    }
}
