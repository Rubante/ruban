package com.ruban.framework.dao.helper;

import java.util.List;

/**
 * 查询结果返回
 * 
 * @author ruban
 * 
 * @param <T>
 */
public class ResultInfo<T> {
    // 当前页
    private int pageNum;
    // 每页的数量
    private int pageSize;
    // 当前页面第一个元素在数据库中的行号
    private int startRow;
    // 当前页面最后一个元素在数据库中的行号
    private int endRow;

    // 总记录数
    private long total;
    // 总页数
    private int pages;
    // 结果集
    private List<T> list;

    // 第一页
    private int firstPage;
    // 前一页
    private int prePage;
    // 下一页
    private int nextPage;
    // 最后一页
    private int lastPage;

    // 是否为第一页
    private boolean isFirstPage = false;
    // 是否为最后一页
    private boolean isLastPage = false;
    // 是否有前一页
    private boolean hasPreviousPage = false;
    // 是否有下一页
    private boolean hasNextPage = false;

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

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getEndRow() {
        return endRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(int firstPage) {
        this.firstPage = firstPage;
    }

    public int getPrePage() {
        return prePage;
    }

    public void setPrePage(int prePage) {
        this.prePage = prePage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public boolean isFirstPage() {
        return isFirstPage;
    }

    public void setFirstPage(boolean isFirstPage) {
        this.isFirstPage = isFirstPage;
    }

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean isLastPage) {
        this.isLastPage = isLastPage;
    }

    public boolean isHasPreviousPage() {
        return hasPreviousPage;
    }

    public void setHasPreviousPage(boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

}
