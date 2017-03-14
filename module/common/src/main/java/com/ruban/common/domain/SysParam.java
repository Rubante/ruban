package com.ruban.common.domain;

import com.ruban.framework.dao.domain.PersistentObject;

/**
 * 该表纪录系统中全局设定的参数
 * 
 * @author ruban
 *
 */
public class SysParam extends PersistentObject {

    /** 主键 **/
    private Long id;
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
