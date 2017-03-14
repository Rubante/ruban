package com.ruban.rbac.domain.organization;

import com.ruban.framework.dao.domain.PersistentObject;

/**
 * 公司内的职务设置,例如：<br />
 * 经理<br />
 * 总监<br />
 * 董事<br />
 * ...... <br />
 * 
 * @author ruban
 *
 */
public class Title extends PersistentObject {

    /** 职务名称 **/
    private String name;

    /** 职务说明 **/
    private String memo;

    /** 所属公司 **/
    private Long companyId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

}
