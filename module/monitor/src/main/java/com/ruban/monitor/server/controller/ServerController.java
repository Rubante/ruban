package com.ruban.monitor.server.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.ruban.framework.core.spring.SpringContext;
import com.ruban.framework.core.utils.biz.IpUtil;
import com.ruban.framework.core.utils.commons.DateUtil;
import com.ruban.framework.core.utils.commons.StringUtil;
import com.ruban.monitor.common.ConfigHolder;
import com.ruban.monitor.common.MonitorConstant;
import com.ruban.monitor.common.ResponseMessage;
import com.ruban.monitor.dao.CommonDao;
import com.ruban.monitor.dao.impl.MapDBDaoImpl;
import com.ruban.monitor.dao.impl.RedisDaoImpl;

/**
 * 监控基础配置<br />
 * 
 * 
 * @author yjwang
 *
 */
@Controller
@RequestMapping(value = "/server")
public class ServerController {

    private static final Logger logger = LoggerFactory.getLogger(ServerController.class);

    /**
     * 首页
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "index")
    public String index(Model model) {

        try {
            Properties properties = PropertiesLoaderUtils.loadAllProperties(MonitorConstant.CONFIG);
            // 数据存储方式
            model.addAttribute(MonitorConstant.SELECT, properties.get(MonitorConstant.DB_SELECT));
            // 数据库路径（默认以class目录为基础）
            model.addAttribute(MonitorConstant.FILE_PATH, properties.get(MonitorConstant.DB_FILE_PATH));

            // redis主机
            model.addAttribute(MonitorConstant.HOST, properties.get(MonitorConstant.REDIS_HOST));
            // redis端口
            model.addAttribute(MonitorConstant.PORT, properties.get(MonitorConstant.REDIS_PORT));
            // redis密码
            model.addAttribute(MonitorConstant.PASSWORD, properties.get(MonitorConstant.REDIS_PASSWORD));
            // redis数据库索引
            model.addAttribute(MonitorConstant.DATABASE, properties.get(MonitorConstant.REDIS_DATABASE));
            // 监控数据
            model.addAttribute(MonitorConstant.PREFIX, properties.get(MonitorConstant.REDIS_PREFIX));

            // 缓存配置信息
            ConfigHolder.setProperties(properties);

        } catch (IOException ex) {
            logger.error(MonitorConstant.CONFIG + " not exists error", ex);
        }

        return "server/index";
    }

    /**
     * redis参数保存
     * 
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "redisSave", method = RequestMethod.POST)
    public String save(Model model, HttpServletRequest request) {

        ResponseMessage message = new ResponseMessage();

        String host = (String) request.getParameter(MonitorConstant.HOST);
        String port = (String) request.getParameter(MonitorConstant.PORT);
        String password = (String) request.getParameter(MonitorConstant.PASSWORD);
        String database = (String) request.getParameter(MonitorConstant.DATABASE);
        String prefix = (String) request.getParameter(MonitorConstant.PREFIX);

        if (!IpUtil.checkIp(host)) {
            message.setFlag(MonitorConstant.MSG_ERROR_CODE);
            message.setMsg("ip地址填写错误");
            return JSON.toJSONString(message);
        }

        // 默认地址
        if (StringUtil.isNotNullOrEmpty(port)) {
            if (!StringUtil.isDigit(port)) {
                message.setFlag(MonitorConstant.MSG_ERROR_CODE);
                message.setMsg("端口必须为整数");
                return JSON.toJSONString(message);
            } else {
                int iPort = Integer.parseInt(port);
                if (iPort < 1024 || iPort > 65535) {
                    message.setFlag(MonitorConstant.MSG_ERROR_CODE);
                    message.setMsg("端口范围不正确");
                    return JSON.toJSONString(message);
                }
            }
        } else {
            port = "6379";
        }

        // 数据库名称
        if (StringUtil.isNullOrEmpty(prefix)) {
            prefix = "monitor:";
        }

        try {
            // 原有配置文件
            Properties properties = PropertiesLoaderUtils.loadAllProperties(MonitorConstant.CONFIG);

            // 选择redis存储
            properties.put(MonitorConstant.DB_SELECT, MonitorConstant.SELECT_REDIS);
            // redis主机
            properties.put(MonitorConstant.REDIS_HOST, host);
            // redis端口
            properties.put(MonitorConstant.REDIS_PORT, port);
            // redis密码
            properties.put(MonitorConstant.REDIS_PASSWORD, password);
            // 监控数据库
            properties.put(MonitorConstant.REDIS_DATABASE, database);
            // 数据名
            properties.put(MonitorConstant.REDIS_PREFIX, prefix);

            OutputStream fos = new FileOutputStream(getClass().getResource("/" + MonitorConstant.CONFIG).getFile());

            properties.store(fos, "update at " + DateUtil.getNowDateTimeStr());

            // 缓存配置信息
            ConfigHolder.setProperties(properties);
            message.setMsg("修改成功！");

            // 刷新redis链接
            CommonDao<?> commonDao = SpringContext.getBean(MonitorConstant.REDIS_DAO, RedisDaoImpl.class);
            commonDao.refresh(properties);

        } catch (IOException ex) {
            message.setFlag(MonitorConstant.MSG_ERROR_CODE);
            message.setMsg("出现异常！");
            logger.error(MonitorConstant.CONFIG + " update error", ex);
        }

        return JSON.toJSONString(message);
    }

    /**
     * 数据库存储配置
     * 
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "dbSave")
    public String dbSave(Model model, HttpServletRequest request) {

        ResponseMessage message = new ResponseMessage();

        String filePath = (String) request.getParameter(MonitorConstant.FILE_PATH);

        try {

            // 原有配置文件
            Properties properties = PropertiesLoaderUtils.loadAllProperties(MonitorConstant.CONFIG);

            // 选择mapdb存储
            properties.put(MonitorConstant.DB_SELECT, MonitorConstant.SELECT_MAPDB);

            // redis主机
            properties.put(MonitorConstant.DB_FILE_PATH, filePath);

            OutputStream fos = new FileOutputStream(getClass().getResource("/" + MonitorConstant.CONFIG).getFile());

            properties.store(fos, "update at " + DateUtil.getNowDateTimeStr());

            // 缓存配置信息
            ConfigHolder.setProperties(properties);

            message.setMsg("修改成功！");

            // 刷新redis链接
            CommonDao<?> commonDao = SpringContext.getBean(MonitorConstant.MAPDB_DAO, MapDBDaoImpl.class);
            commonDao.refresh(properties);

        } catch (IOException ex) {
            message.setFlag(MonitorConstant.MSG_ERROR_CODE);
            message.setMsg("出现异常！");
            logger.error(MonitorConstant.CONFIG + " update error", ex);
        }

        return JSON.toJSONString(message);
    }

    /**
     * 微信配置页面
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "wechat")
    public String wechat(Model model) {

        return "server/wechat";
    }

    /**
     * 保存配置微信服务器信息
     * 
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "wechatSave", method = RequestMethod.POST)
    public String wechatSave(Model model, HttpServletRequest request) {

        return "配置成功！";
    }
}
