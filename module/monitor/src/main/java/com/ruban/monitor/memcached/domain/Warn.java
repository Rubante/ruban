package com.ruban.monitor.memcached.domain;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 报警设置
 * 
 * @author yjwang
 *
 */
public class Warn implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键 **/
    @JSONField(name = "id")
    private Long id;

    /** 监控项 **/
    @JSONField(name = "item")
    private String item;

    @JSONField(name = "value")
    private String value;

    /** 监控频率 **/
    @JSONField(name = "rate")
    private String rate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

}
