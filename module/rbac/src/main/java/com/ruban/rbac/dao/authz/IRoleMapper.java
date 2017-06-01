package com.ruban.rbac.dao.authz;

import com.ruban.framework.dao.IRubanMapper;
import com.ruban.framework.dao.support.RubanDaoRepository;
import com.ruban.rbac.domain.authz.Role;

@RubanDaoRepository
public interface IRoleMapper extends IRubanMapper {
    
    /**
     * 更新角色状态
     * 
     * @param role
     * @return
     */
    int updateState(Role role);
}