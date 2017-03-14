package com.ruban.rbac.domain.organization;

import com.ruban.framework.dao.domain.PersistentObject;

/**
 * 公司内的岗位设置，例如：<br />
 * java开发工程师 <br />
 * 测试工程师 <br />
 * 架构师<br />
 * UI设计师 <br />
 * ...... <br />
 * 
 * @author ruban
 *
 */
public class Job extends PersistentObject {

    /** 岗位名称 **/
    private String name;

    /** 岗位说明 **/
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
