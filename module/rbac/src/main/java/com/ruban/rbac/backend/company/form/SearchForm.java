package com.ruban.rbac.backend.company.form;

import com.ruban.rbac.vo.company.CompanyCondition;

/**
 * 组织机构：查询条件
 * 
 * @author yjwang
 *
 */
public class SearchForm extends CompanyCondition {

    private static final long serialVersionUID = 6438864561965004773L;

    /** 是否显示下级节点 **/
    private int childDisplay;

    public int getChildDisplay() {
        return childDisplay;
    }

    public void setChildDisplay(int childDisplay) {
        this.childDisplay = childDisplay;
    }

}
