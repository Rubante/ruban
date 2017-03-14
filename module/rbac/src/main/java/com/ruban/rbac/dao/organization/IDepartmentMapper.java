package com.ruban.rbac.dao.organization;

import com.ruban.framework.dao.IRubanMapper;
import com.ruban.framework.dao.support.RubanDaoRepository;
import com.ruban.rbac.domain.organization.Department;

@RubanDaoRepository
public interface IDepartmentMapper extends IRubanMapper {

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
     * @param department
     */
    public void updateHasChildren(Department department);
}