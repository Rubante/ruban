package com.ruban.rbac.dao.authz;

import com.ruban.framework.dao.IRubanMapper;
import com.ruban.framework.dao.support.RubanDaoRepository;

@RubanDaoRepository
public interface IRolePermissionMapper extends IRubanMapper {

    /**
     * 依据roleId删除资源授权
     * 
     * @param roleId
     * @return
     */
    int deleteByRoleId(Long roleId);
}