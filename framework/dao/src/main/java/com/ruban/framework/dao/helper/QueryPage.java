package com.ruban.framework.dao.helper;

import java.io.Serializable;

/**
 * 分页信息
 * 
 * @author ruban
 * 
 * @param <T>
 */
public class QueryPage<T> implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * 当前页数
     */
    protected String currentPage = "1";

    /**
     * 每页记录数
     */
    protected int recordsPerPage = 10;

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

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public int getRecordsPerPage() {
        return recordsPerPage;
    }

    public void setRecordsPerPage(int recordsPerPage) {
        this.recordsPerPage = recordsPerPage;
    }

    public int getStartIndex() {
        return (Integer.parseInt(getCurrentPage()) - 1) * getRecordsPerPage();
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
