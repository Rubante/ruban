package com.ruban.rbac.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruban.common.dict.UserState;
import com.ruban.framework.core.utils.commons.DateUtil;
import com.ruban.framework.core.utils.commons.RandomUtil;
import com.ruban.framework.dao.IRubanDao;
import com.ruban.framework.dao.helper.Condition;
import com.ruban.framework.dao.helper.ResultInfo;
import com.ruban.rbac.dao.authz.IUserMapper;
import com.ruban.rbac.domain.authz.User;
import com.ruban.rbac.service.IUserService;
import com.ruban.rbac.vo.user.UserVo;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserMapper userMapper;

    @Autowired
    private IRubanDao rubanDao;

    @Override
    public List<User> selectAll() {
        return userMapper.selectAll();
    }

    @Override
    public ResultInfo<User> selectByPage(Condition<User> condition) {

        ResultInfo<User> result = rubanDao.findByPage(condition, userMapper);
        return result;
    }

    @Override
    public List<User> selectByCondition(Condition<User> condition) {
        return userMapper.selectWithCondition(condition);
    }

    @Override
    public void insert(UserVo userVo) {

        User user = new User();

        user.setUsername(userVo.getUsername());
        user.setNick(userVo.getNick());
        user.setPassword(userVo.getPassword());
        user.setMemo(userVo.getMemo());
        user.setState(UserState.New.getValue());

        user.setAddTime(DateUtil.getToday());
        user.setModTime(DateUtil.getToday());
        user.setAddUserId(0L);
        user.setModUserId(0L);
        user.setUpdateLock(RandomUtil.getUpdateLock());

        userMapper.insert(user);
    }

    @Override
    public int update(UserVo userVo) {

        User user = findById(userVo.getId());

        user.setUsername(userVo.getUsername());
        user.setNick(userVo.getNick());
        user.setPassword(userVo.getPassword());
        user.setMemo(userVo.getMemo());

        user.setModTime(DateUtil.getToday());
        user.setModUserId(0L);
        user.setUpdateLock(RandomUtil.getUpdateLock());

        user.setHoldLock(userVo.getHoldLock());

        int result = userMapper.update(user);

        return result;
    }

    /**
     * 根据ID删除账号
     * 
     * @param id
     * @return
     */
    public int deleteById(Long id) {
        return userMapper.deleteById(id);
    }

    /**
     * 批量删除账号
     * 
     * @param ids
     * @return
     */
    public int deleteByIds(String[] ids) {
        return userMapper.deleteByIds(ids);
    }

    @Override
    public User findById(Long id) {
        return userMapper.findById(id);
    }

    /**
     * 停用账号
     * 
     * @param userVo
     * @return
     */
    public int stop(UserVo userVo) {

        User user = buildUser(userVo, UserState.Stop.getValue());

        return userMapper.updateState(user);
    }

    /**
     * 创建更新参数
     * 
     * @param userVo
     * @param state
     * @return
     */
    private UserVo buildUser(UserVo userVo, int state) {
        User user = new User();
        user.setState(state);
        user.setModTime(userVo.getModTime());
        user.setUpdateLock(RandomUtil.getUpdateLock());
        user.setHoldLock(userVo.getHoldLock());

        return userVo;
    }

    /**
     * 启用账号
     * 
     * @param userVo
     * @return
     */
    public int start(UserVo userVo) {

        User user = buildUser(userVo, UserState.Normal.getValue());

        return userMapper.updateState(user);
    }

    /**
     * 禁用账号
     * 
     * @param userVo
     * @return
     */
    public int disable(UserVo userVo) {

        User user = buildUser(userVo, UserState.Disable.getValue());

        return userMapper.updateState(user);
    }

    /**
     * 解锁账号
     * 
     * @param userVo
     * @return
     */
    public int unlock(UserVo userVo) {

        User user = buildUser(userVo, UserState.Normal.getValue());

        return userMapper.updateState(user);
    }
}
