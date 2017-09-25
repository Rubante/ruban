package com.ruban.rbac.dao.authz;

import com.ruban.framework.dao.IRubanMapper;
import com.ruban.framework.dao.support.RubanDaoRepository;
import com.ruban.rbac.domain.authz.User;

@RubanDaoRepository
public interface IUserMapper extends IRubanMapper {

    /**
     * 更新状态
     * 
     * @param user
     * @return
     */
    int updateState(User user);

    /**
     * 根据用户名查找用户
     * 
     * @param username
     * @return
     */
    User findByUsername(String username);
}