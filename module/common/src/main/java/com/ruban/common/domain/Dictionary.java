package com.ruban.common.domain;

import com.ruban.framework.dao.domain.PersistentObject;

/**
 * 整个系统的基础数据字典，业务数据字典应该单独设立，避免过于集中
 * 
 * @author ruban
 *
 */
public class Dictionary extends PersistentObject {

    /** 主键 **/
    private Long id;
    /** 组 **/
    private String groupKey;
    /** 编码 **/
    private String code;
    /** 数值 **/
    private String value;
    /** 备注 **/
    private String memo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupKey() {
        return groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

}
