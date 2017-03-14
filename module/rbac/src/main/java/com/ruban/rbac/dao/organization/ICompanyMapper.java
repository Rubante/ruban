package com.ruban.rbac.dao.organization;

import com.ruban.framework.dao.IRubanMapper;
import com.ruban.framework.dao.support.RubanDaoRepository;
import com.ruban.rbac.domain.organization.Company;

@RubanDaoRepository
public interface ICompanyMapper extends IRubanMapper {

    /**
     * 根据父节点查询节点数量
     * 
     * @param parentId
     * @return
     */
    public int countByParentId(Long parentId);

    /**
     * 更新节点数量
     * 
     * @param company
     */
    public void updateHasChildren(Company company);
}