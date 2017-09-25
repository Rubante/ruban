package com.ruban.rbac.service;

import java.util.List;

import com.ruban.framework.dao.helper.Condition;
import com.ruban.framework.dao.helper.ResultInfo;
import com.ruban.rbac.domain.authz.User;
import com.ruban.rbac.vo.user.UserVo;

/**
 * 账号
 * 
 * @author ruban
 *
 */
public interface IUserService {

    /**
     * 查询所有的账号列表
     * 
     * @return
     */
    List<User> selectAll();

    /**
     * 根据条件分页查询
     * 
     * @param dto
     * @return
     */
    ResultInfo<User> selectByPage(Condition<User> condition);

    /**
     * 根据条件查询
     * 
     * @param condition
     * @return
     */
    List<User> selectByCondition(Condition<User> condition);

    /**
     * 新建账号
     * 
     * @param userVo
     */
    void insert(UserVo userVo);

    /**
     * 更新账号
     * 
     * @param userVo
     */
    int update(UserVo userVo);

    /**
     * 根据ID删除账号
     * 
     * @param id
     * @return
     */
    int deleteById(Long id);

    /**
     * 根据ID批量删除账号
     * 
     * @param ids
     * @return
     */
    int deleteByIds(String[] ids);

    /**
     * 根据ID获取账号
     * 
     * @param id
     * @return
     */
    User findById(Long id);

    /**
     * 根据ID停用账号
     * 
     * @param userVo
     * @return
     */
    int stop(UserVo userVo);

    /**
     * 根据ID启用账号
     * 
     * @param userVo
     * @return
     */
    int start(UserVo userVo);

    /**
     * 根据ID禁用账号
     * 
     * @param userVo
     * @return
     */
    int disable(UserVo userVo);

    /**
     * 根据ID解锁账号
     * 
     * @param userVo
     * @return
     */
    int unlock(UserVo userVo);
}
