package com.ruban.rbac.domain.authz;

import java.util.Date;

/**
 * 系统中的权限
 * 
 * @author ruban
 *
 */
public class Permission {

    /** 主键 **/
    private int id;

    /** 操作方式 **/
    private String operateType;

    /** 操作说明 **/
    private String operateDescription;

    /** 备注 **/
    private String memo;

    /** 修改时间 **/
    private Date updatetime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public String getOperateDescription() {
        return operateDescription;
    }

    public void setOperateDescription(String operateDescription) {
        this.operateDescription = operateDescription;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

}
