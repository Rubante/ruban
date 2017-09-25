package com.ruban.monitor.server.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.ruban.framework.core.utils.commons.StringUtil;
import com.ruban.monitor.common.BaseController;
import com.ruban.monitor.common.MonitorConstant;
import com.ruban.monitor.common.ResponseMessage;
import com.ruban.monitor.dao.CommonDao;
import com.ruban.monitor.server.bean.User;
import com.ruban.monitor.server.domain.UserKey;

/**
 * 主入口控制类
 * 
 * @author yjwang
 *
 */
@Controller
@RequestMapping(value = "/")
public class MainController extends BaseController {

    @RequestMapping(value = "main")
    public String index(Model model) {

        return "main";
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login(Model model) {
        return "login";
    }

    /**
     * 登陆信息
     * 
     * @param model
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(Model model, HttpServletRequest request) {
        ResponseMessage message = new ResponseMessage();

        String username = request.getParameter(UserKey.USERNAME);
        String password = request.getParameter(UserKey.PASSWORD);

        // 普通查询
        CommonDao<Long> commonDao = getDao();

        // 校验账号唯一性
        Long oldId = commonDao.getFromHash(UserKey.PREFIX + UserKey.USERNAME_LIST, username);
        if (oldId == null) {
            message.setFlag(MonitorConstant.MSG_ERROR_CODE);
            message.setMsg("账号或密码不正确");

            return JSON.toJSONString(message);
        } else {
            CommonDao<User> mapDao = getDao();

            User user = mapDao.getFromZSet(UserKey.PREFIX + UserKey.USER_LIST, oldId);
            if (StringUtil.isNullOrEmpty(password)) {
                message.setFlag(MonitorConstant.MSG_ERROR_CODE);
                message.setMsg("请填写密码");
            } else if (!password.equals(user.getPassword()) || user.getState() != UserKey.STATE_ENABLE) {
                message.setFlag(MonitorConstant.MSG_ERROR_CODE);
                message.setMsg("账号或密码不正确");
            } else {
                request.getSession().setAttribute(MonitorConstant.LOGINED, user);
                message.setMsg("登录成功！");
            }
        }

        return JSON.toJSONString(message);

    }

    /**
     * 退出
     * 
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout(Model model, HttpServletRequest request) {
        request.getSession().removeAttribute(MonitorConstant.LOGINED);
        return "login";
    }
}
