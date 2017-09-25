package com.ruban.monitor.memcached.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.ruban.framework.core.utils.biz.IpUtil;
import com.ruban.framework.core.utils.commons.DateUtil;
import com.ruban.framework.core.utils.commons.StringUtil;
import com.ruban.monitor.common.BaseController;
import com.ruban.monitor.common.MonitorConstant;
import com.ruban.monitor.common.ResponseMessage;
import com.ruban.monitor.dao.CommonDao;
import com.ruban.monitor.memcached.database.MemcachedKey;
import com.ruban.monitor.memcached.domain.Server;
import com.ruban.monitor.memcached.domain.Warn;

/**
 * 配置管理
 * 
 * @author yjwang
 *
 */
@Controller(value = "memcachedConfigController")
@RequestMapping(value = "/memcached")
public class ConfigController extends BaseController implements MemcachedKey {

    private static final Logger logger = LoggerFactory.getLogger(ConfigController.class);

    /**
     * 配置入口页
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "config/base")
    public String settingBase(Model model) {

        CommonDao<String> commonDao = getDao();

        String locator = commonDao.get(PREFIX + LOCATOR);
        String hash = commonDao.get(PREFIX + HASH);
        String statsRate = commonDao.get(PREFIX + STATS_RATE);

        model.addAttribute(LOCATOR, locator);
        model.addAttribute(HASH, hash);
        model.addAttribute(STATS_RATE, statsRate);

        return "memcached/config/base";
    }

    /**
     * 服务器
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "config/server")
    public String server(Model model) {

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

        CommonDao<String> commonDao = getDao();
        // 监控状态
        String monitorState = commonDao.get(PREFIX + MONITOR_STATE);
        model.addAttribute(MONITOR_STATE, monitorState);

        return "memcached/config/server";
    }

    /**
     * 配置保存页
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "config/baseSave", method = RequestMethod.POST)
    public String settingBaseSave(HttpServletRequest request) {

        ResponseMessage response = new ResponseMessage();

        CommonDao<String> commonDao = getDao();

        // 上送参数
        String locator = request.getParameter(LOCATOR);
        String hash = request.getParameter(HASH);
        String statsRate = request.getParameter(STATS_RATE);

        if (!StringUtil.isDigit(statsRate)) {
            response.setFlag(MonitorConstant.MSG_ERROR_CODE);
            response.setMsg("频率应该为整数");

            return JSON.toJSONString(response);
        }

        commonDao.update(PREFIX + LOCATOR, locator);
        commonDao.update(PREFIX + HASH, hash);
        commonDao.update(PREFIX + STATS_RATE, statsRate);

        response.setMsg("修改成功");

        return JSON.toJSONString(response);
    }

    /**
     * 序列化页面
     * 
     * @return
     */
    @RequestMapping(value = "config/serializable/page")
    public String settingSerializablePage(Model model) {
        CommonDao<String> commonDao = getDao();

        String className = commonDao.get(PREFIX + SERIA_CLASS_NAME);

        model.addAttribute("className", className);

        return "memcached/config/serializable";
    }

    /**
     * 设置序列化规则
     * 
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "config/serializable")
    public String settingSerializable(Model model, HttpServletRequest request) {

        ResponseMessage response = new ResponseMessage();

        // 反序列化类
        String serialize = request.getParameter(SERIA_CLASS_NAME);
        CommonDao<String> commonDao = getDao();

        if (StringUtil.isNotNullOrEmpty(serialize)) {
            try {
                Class.forName(serialize);

                commonDao.update(PREFIX + SERIA_CLASS_NAME, serialize);

                response.setMsg("设置成功！");
            } catch (ClassNotFoundException e) {
                logger.error("class not found");
                response.setFlag(MonitorConstant.MSG_ERROR_CODE);
                response.setMsg("设置失败,class不存在！");
            }
        } else {
            commonDao.delete(PREFIX + SERIA_CLASS_NAME);
            response.setMsg("清空设置！");
            response.setFlag(0);
        }

        return JSON.toJSONString(response);
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

        if (!IpUtil.checkIp(host)) {
            message.setFlag(MonitorConstant.MSG_ERROR_CODE);
            message.setMsg("ip地址错误！");
        } else if (!StringUtil.isDigit(port)) {
            message.setFlag(MonitorConstant.MSG_ERROR_CODE);
            message.setMsg("端口填写错误！");
        } else {

            CommonDao<Server> dao = getDao();

            Server server = new Server();
            server.setId(DateUtil.getUnixTime());
            server.setHost(host);
            server.setPort(Integer.parseInt(port));

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

        if (!IpUtil.checkIp(host)) {
            message.setFlag(MonitorConstant.MSG_ERROR_CODE);
            message.setMsg("ip地址错误！");
        } else if (!StringUtil.isDigit(port)) {
            message.setFlag(MonitorConstant.MSG_ERROR_CODE);
            message.setMsg("端口填写错误！");
        } else {

            CommonDao<Server> dao = getDao();

            Server server = new Server();
            server.setId(Long.parseLong(id));
            server.setHost(host);
            server.setPort(Integer.parseInt(port));

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

    /**
     * 报警配置
     * 
     * @return
     */
    @RequestMapping(value = "config/warn")
    public String warn(Model model) {
        CommonDao<Warn> dao = getDao();

        Warn cpu = dao.get(PREFIX + WARN_CPU);
        Warn hit = dao.get(PREFIX + WARN_HIT);

        model.addAttribute("cpu", cpu);
        model.addAttribute("hit", hit);

        return "memcached/config/warn";
    }

    /**
     * 监控项
     * 
     * @return
     */
    @RequestMapping(value = "config/warnSave")
    @ResponseBody
    public String warnSave(HttpServletRequest request) {
        ResponseMessage message = new ResponseMessage();

        String item = request.getParameter(ITEM);

        String value = request.getParameter(VALUE);

        String rate = request.getParameter(RATE);

        Warn warn = new Warn();

        warn.setId(DateUtil.getUnixTime());
        warn.setItem(item);
        warn.setValue(value);
        warn.setRate(rate);

        CommonDao<Warn> dao = getDao();
        if ("cpu".equals(item)) {
            dao.update(PREFIX + WARN_CPU, warn);
        } else if ("hit".equals(item)) {
            dao.update(PREFIX + WARN_HIT, warn);
        }

        message.setMsg("保存成功！");

        return JSON.toJSONString(message);
    }

}