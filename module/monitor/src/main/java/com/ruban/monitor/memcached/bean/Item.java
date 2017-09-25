package com.ruban.monitor.memcached.bean;

import java.io.Serializable;

/**
 * 通用key，value结构
 * 
 * @author yjwang
 *
 */
public class Item implements Serializable, Comparable<Item> {

    private static final long serialVersionUID = 1L;

    /**
     * 任意字符串，最大长度250个字节，ASCII模式，不含有空格及换行符
     **/
    private String key;

    /** 32个bit位的"flag"值 **/
    private String flag;

    /**
     * 过期时间，按秒记。<br/>
     * 0意为着永不过期。最大30天，超过30天该值被当作unix时间戳的准确时间对待
     **/
    private int exp;

    /** 64位的CAS值，保持唯一性 **/
    private String casId;

    /** 数据大小 **/
    private String size;

    private Object value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public String getCasId() {
        return casId;
    }

    public void setCasId(String casId) {
        this.casId = casId;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return key + ":" + value;
    }

    @Override
    public int compareTo(Item o) {
        return this.getKey().compareTo(o.getKey());
    }
}
