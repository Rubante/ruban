package com.ruban.monitor.server.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.ruban.framework.core.utils.commons.DateUtil;
import com.ruban.framework.core.utils.commons.StringUtil;
import com.ruban.monitor.common.BaseController;
import com.ruban.monitor.common.MonitorConstant;
import com.ruban.monitor.common.ResponseMessage;
import com.ruban.monitor.dao.CommonDao;
import com.ruban.monitor.server.bean.User;
import com.ruban.monitor.server.bean.UserWarn;
import com.ruban.monitor.server.domain.UserKey;

/**
 * 系统登陆及用户管理
 * 
 * @author yjwang
 *
 */
@Controller
@RequestMapping(value = "/user")
public class UserController extends BaseController implements UserKey {

    /**
     * 用户管理主界面
     * 
     * @param model
     * @param requst
     * @return
     */
    @RequestMapping(value = "main")
    public String main(Model model, HttpServletRequest requst) {

        CommonDao<User> mapDao = getDao();

        Map<Long, User> map = mapDao.getFromZSet(PREFIX + USER_LIST, 0, DateUtil.getUnixTime());

        List<User> users = new ArrayList<>();

        Iterator<Long> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            Long key = iterator.next();
            User user = map.get(key);
            users.add(user);
        }

        model.addAttribute(USER_LIST, users);

        return "server/user";
    }

    /**
     * 添加用户
     * 
     */
    @ResponseBody
    @RequestMapping(value = "add")
    public String add(Model model, HttpServletRequest request) {
        ResponseMessage message = new ResponseMessage();
        Long id = DateUtil.getUnixTime();

        // 普通查询
        CommonDao<Long> commonDao = getDao();

        String username = request.getParameter(USERNAME);

        // 校验账号唯一性
        Long oldId = commonDao.getFromHash(PREFIX + USERNAME_LIST, username);
        if (oldId != null) {
            message.setFlag(MonitorConstant.MSG_ERROR_CODE);
            message.setMsg("账号重复");

            return JSON.toJSONString(message);
        }

        commonDao.putToHash(PREFIX + USERNAME_LIST, username, id);

        String name = request.getParameter(NAME);
        String password = request.getParameter(PASSWORD);
        String tel = request.getParameter(TEL);
        String telState = request.getParameter(TELSTATE);
        String email = request.getParameter(EMAIL);
        String emailState = request.getParameter(EMAILSTATE);
        String wechat = request.getParameter(WECHAT);
        String wechatState = request.getParameter(WECHATSTATE);
        String qq = request.getParameter(QQ);

        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setName(name);
        user.setPassword(password);

        user.setTel(tel);
        if (StringUtil.isDigit(telState)) {
            user.setTelState(Integer.parseInt(telState));
        }

        user.setEmail(email);
        if (StringUtil.isDigit(emailState)) {
            user.setEmailState(Integer.parseInt(emailState));
        }

        user.setWechat(wechat);
        if (StringUtil.isDigit(wechatState)) {
            user.setWechatState(Integer.parseInt(wechatState));
        }

        user.setQq(qq);

        // 保存数据
        CommonDao<User> mapDao = getDao();
        mapDao.putToZSet(PREFIX + USER_LIST, id, user);

        message.setMsg("添加成功");
        message.setValue(user);

        return JSON.toJSONString(message);
    }

    /**
     * 删除用户
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "delete")
    public String delete(HttpServletRequest request) {

        ResponseMessage message = new ResponseMessage();

        String id = request.getParameter(ID);

        if (!StringUtil.isDigit(id)) {
            message.setFlag(MonitorConstant.MSG_ERROR_CODE);
            message.setMsg("id错误");
        } else {

            CommonDao<User> dao = getDao();

            User user = dao.removeFromZSet(PREFIX + USER_LIST, Long.parseLong(id));

            // 校验账号唯一性
            dao.getFromHash(PREFIX + USERNAME_LIST, user.getUsername());

            message.setMsg("删除成功！");
        }

        String result = JSON.toJSONString(message);

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "update")
    public String update(Model model, HttpServletRequest request) {
        ResponseMessage message = new ResponseMessage();

        String id = request.getParameter(ID);

        if (!StringUtil.isDigit(id)) {
            message.setFlag(MonitorConstant.MSG_ERROR_CODE);
            message.setMsg("参数错误！");

            return JSON.toJSONString(message);
        }
        String username = request.getParameter(USERNAME);
        String name = request.getParameter(NAME);
        String tel = request.getParameter(TEL);
        String telState = request.getParameter(TELSTATE);
        String email = request.getParameter(EMAIL);
        String emailState = request.getParameter(EMAILSTATE);
        String wechat = request.getParameter(WECHAT);
        String wechatState = request.getParameter(WECHATSTATE);
        String qq = request.getParameter(QQ);

        CommonDao<User> dao = getDao();

        User user = dao.getFromZSet(PREFIX + USER_LIST, Long.parseLong(id));

        user.setUsername(username);
        user.setName(name);

        user.setTel(tel);
        if (StringUtil.isDigit(telState)) {
            user.setTelState(Integer.parseInt(telState));
        }

        user.setEmail(email);
        if (StringUtil.isDigit(emailState)) {
            user.setEmailState(Integer.parseInt(emailState));
        }

        user.setWechat(wechat);
        if (StringUtil.isDigit(wechatState)) {
            user.setWechatState(Integer.parseInt(wechatState));
        }

        user.setQq(qq);

        // 保存数据
        CommonDao<User> mapDao = getDao();
        mapDao.putToZSet(PREFIX + USER_LIST, Long.parseLong(id), user);

        message.setMsg("修改成功");

        return JSON.toJSONString(message);
    }

    /**
     * 用户关心的报警设置
     * 
     * @param model
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "warn")
    public String warn(Model model, HttpServletRequest request) {
        ResponseMessage message = new ResponseMessage();

        String id = request.getParameter(ID);

        if (StringUtil.isNullOrEmpty(id)) {
            if (!StringUtil.isDigit(id)) {
                message.setFlag(MonitorConstant.MSG_ERROR_CODE);
                message.setMsg("参数错误！");

                return JSON.toJSONString(message);
            }
        } else {
            id = String.valueOf(DateUtil.getUnixTime());
        }

        String cpuWarn = request.getParameter(CPU_WARN);
        String hitWarn = request.getParameter(HIT_WARN);

        CommonDao<UserWarn> dao = getDao();

        UserWarn userWarn = new UserWarn();
        Long idLong = Long.parseLong(id);
        userWarn.setId(idLong);
        

        dao.putToZSet(PREFIX + WARN_LIST, idLong, userWarn);

        message.setMsg("修改成功");

        return JSON.toJSONString(message);
    }

    /**
     * 修改密码
     * 
     * @param model
     * @param request
     */
    @ResponseBody
    @RequestMapping(value = "updatePassword")
    public String updatePassword(Model model, HttpServletRequest request) {
        ResponseMessage message = new ResponseMessage();

        String id = request.getParameter(ID);
        String password = request.getParameter(PASSWORD);

        if (StringUtil.isDigit(id)) {
            CommonDao<User> userDao = getDao();
            User user = userDao.getFloorFromZSet(PREFIX + USER_LIST, Long.parseLong(id));
            user.setPassword(password);
            userDao.putToZSet(PREFIX + USER_LIST, Long.parseLong(id), user);

            message.setMsg("密码修改成功");
        } else {
            message.setFlag(MonitorConstant.MSG_ERROR_CODE);
            message.setMsg("修改失败");
        }

        return JSON.toJSONString(message);

    }

    /**
     * 启用用户
     * 
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "start")
    public String start(HttpServletRequest request) {
        ResponseMessage message = new ResponseMessage();

        String id = request.getParameter("id");

        // 判断上送id是否正确
        if (!StringUtil.isDigit(id)) {
            message.setFlag(1);
            message.setMsg("id 参数错误！");

            return JSON.toJSONString(message);
        }

        Long userId = Long.parseLong(id);

        CommonDao<User> userDao = getDao();

        User user = userDao.getFromZSet(PREFIX + USER_LIST, userId);

        if (user != null) {
            user.setState(STATE_ENABLE);
            userDao.putToZSet(PREFIX + USER_LIST, userId, user);
            message.setMsg("操作成功");
        } else {
            message.setFlag(MonitorConstant.MSG_ERROR_CODE);
            message.setMsg("操作失败");
        }

        return JSON.toJSONString(message);
    }

    /**
     * 禁用用户
     * 
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "stop")
    public String stop(HttpServletRequest request) {
        ResponseMessage message = new ResponseMessage();

        String id = request.getParameter("id");

        // 判断上送id是否正确
        if (!StringUtil.isDigit(id)) {
            message.setFlag(1);
            message.setMsg("id 参数错误！");

            return JSON.toJSONString(message);
        }

        Long userId = Long.parseLong(id);

        CommonDao<User> userDao = getDao();

        User user = userDao.getFromZSet(PREFIX + USER_LIST, userId);

        if (user != null) {
            user.setState(STATE_UNABLE);
            userDao.putToZSet(PREFIX + USER_LIST, userId, user);
            message.setMsg("操作成功");
        } else {
            message.setFlag(MonitorConstant.MSG_ERROR_CODE);
            message.setMsg("操作失败");
        }

        return JSON.toJSONString(message);
    }

}
