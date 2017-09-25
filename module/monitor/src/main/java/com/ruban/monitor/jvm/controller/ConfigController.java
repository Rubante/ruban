package com.ruban.monitor.jvm.controller;

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
import com.ruban.framework.core.utils.biz.IpUtil;
import com.ruban.framework.core.utils.commons.DateUtil;
import com.ruban.framework.core.utils.commons.StringUtil;
import com.ruban.monitor.common.MonitorConstant;
import com.ruban.monitor.common.ResponseMessage;
import com.ruban.monitor.dao.CommonDao;
import com.ruban.monitor.jvm.domain.Server;

/**
 * 配置管理
 * 
 * @author yjwang
 *
 */
@Controller("jvmConfigMonitor")
@RequestMapping(value = "/jvm")
public class ConfigController extends JvmController {

    /**
     * 配置入口页
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "config/base")
    public String settingBase(Model model) {
        
        CommonDao<Server> mapDao = getDao();

        Map<Long, Server> map = mapDao.getFromZSet(PREFIX + SERVER_LIST, 0, DateUtil.getUnixTime());

        List<Server> servers = new ArrayList<>();

        Iterator<Long> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            Long key = iterator.next();
            Server server = map.get(key);
            servers.add(server);
        }

        model.addAttribute(SERVER_LIST, servers);

        return "jvm/config/base";
    }

    /**
     * 添加IP地址
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "config/addServer")
    public String addServer(HttpServletRequest request) {

        ResponseMessage message = new ResponseMessage();

        String host = request.getParameter(HOST);

        String port = request.getParameter(PORT);

        String rate = request.getParameter(MONITOR_RATE);

        if (!IpUtil.checkIp(host)) {
            message.setFlag(MonitorConstant.MSG_ERROR_CODE);
            message.setMsg("ip地址错误！");
        } else if (!StringUtil.isDigit(port)) {
            message.setFlag(MonitorConstant.MSG_ERROR_CODE);
            message.setMsg("端口填写错误！");
        } else if (!StringUtil.isDigit(rate)) {
            message.setFlag(MonitorConstant.MSG_ERROR_CODE);
            message.setMsg("监控频率必须为整数！");
        } else {

            CommonDao<Server> dao = getDao();

            Server server = new Server();
            server.setId(DateUtil.getUnixTime());
            server.setHost(host);
            server.setPort(Integer.parseInt(port));
            server.setRate(Integer.parseInt(rate));

            dao.putToZSet(PREFIX + SERVER_LIST, server.getId(), server);

            message.setMsg("添加成功！");
            message.setValue(server);
        }

        String result = JSON.toJSONString(message);

        return result;
    }

    /**
     * 修改IP地址
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "config/updateServer")
    public String updateServer(HttpServletRequest request) {

        ResponseMessage message = new ResponseMessage();

        String id = request.getParameter(ID);

        String host = request.getParameter(HOST);

        String port = request.getParameter(PORT);

        String rate = request.getParameter(MONITOR_RATE);

        if (!IpUtil.checkIp(host)) {
            message.setFlag(MonitorConstant.MSG_ERROR_CODE);
            message.setMsg("ip地址错误！");
        } else if (!StringUtil.isDigit(port)) {
            message.setFlag(MonitorConstant.MSG_ERROR_CODE);
            message.setMsg("端口填写错误！");
        } else if (!StringUtil.isDigit(rate)) {
            message.setFlag(MonitorConstant.MSG_ERROR_CODE);
            message.setMsg("监控频率必须为整数！");
        } else {

            CommonDao<Server> dao = getDao();

            Server server = new Server();
            server.setId(Long.parseLong(id));
            server.setHost(host);
            server.setPort(Integer.parseInt(port));
            server.setRate(Integer.parseInt(rate));

            dao.putToZSet(PREFIX + SERVER_LIST, server.getId(), server);

            message.setMsg("修改成功！");
        }

        String result = JSON.toJSONString(message);

        return result;
    }

    /**
     * 删除IP地址
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "config/deleteServer")
    public String deleteServer(HttpServletRequest request) {

        ResponseMessage message = new ResponseMessage();

        String id = request.getParameter(ID);

        if (!StringUtil.isDigit(id)) {
            message.setFlag(MonitorConstant.MSG_ERROR_CODE);
            message.setMsg("id错误");
        } else {

            CommonDao<Server> dao = getDao();

            dao.removeFromZSet(PREFIX + SERVER_LIST, Long.parseLong(id));

            message.setMsg("删除成功！");
        }

        String result = JSON.toJSONString(message);

        return result;
    }
}