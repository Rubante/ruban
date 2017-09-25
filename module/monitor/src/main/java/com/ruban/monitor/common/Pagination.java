package com.ruban.monitor.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 针对前端列表底部显示
 * 
 * @author yjwang
 *
 */
public class Pagination<T> {

    /** 是否有首页 **/
    private boolean start;

    /** 是否有末页 **/
    private boolean end;

    /** 页码标注 **/
    private String value;

    /** 当前页码激活 **/
    private String active;

    /** 页码列表 **/
    private List<Pagination<T>> list;

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public boolean isEnd() {
        return end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public List<Pagination<T>> getList() {
        return list;
    }

    public void setList(List<Pagination<T>> list) {
        this.list = list;
    }

    public void gereate(int current, int pages) {

        list = new ArrayList<>();

        int beforeCount = 0;
        // 当前页码前面
        for (int i = current; current <= 5 && i > 0 || (pages - current) < 5 && (i > pages - 10) && i > 0
                || i > current - 5 && i > 0; i--) {
            Pagination<T> pagination = new Pagination<>();
            pagination.setValue(String.valueOf(i));

            beforeCount++;
            if (i == current) {
                pagination.setActive("1");
            }
            list.add(0, pagination);
        }

        // 当前页码后面
        int i = current + 1;
        int maxPage = pages < 10 ? pages : 10;
        while (beforeCount < pages && i <= (current + maxPage - beforeCount)) {
            Pagination<T> pagination = new Pagination<>();
            pagination.setValue(String.valueOf(i));
            list.add(0, pagination);
            i++;
        }

        PageComparetor<T> comparetor = new PageComparetor<T>();

        Collections.sort(list, comparetor);
    }
}

class PageComparetor<T> implements Comparator<Pagination<T>> {

    @Override
    public int compare(Pagination<T> o1, Pagination<T> o2) {
        int num1 = Integer.parseInt(o1.getValue());
        int num2 = Integer.parseInt(o2.getValue());

        return num1 - num2;
    }
}
