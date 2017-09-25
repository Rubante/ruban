package com.ruban.monitor.server.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.ruban.framework.core.utils.commons.DateUtil;
import com.ruban.framework.core.utils.commons.StringUtil;
import com.ruban.monitor.common.BaseController;
import com.ruban.monitor.common.MonitorConstant;
import com.ruban.monitor.common.ResponseMessage;
import com.ruban.monitor.dao.CommonDao;
import com.ruban.monitor.server.bean.SmsTemplate;
import com.ruban.monitor.server.domain.WarnKey;
import com.ruban.monitor.util.EmailTool;

/**
 * 报警设置
 * 
 * @author yjwang
 *
 */
@Controller
@RequestMapping(value = "/warn")
public class WarnController extends BaseController implements WarnKey {

    private static final Logger logger = LoggerFactory.getLogger(WarnController.class);

    @RequestMapping(value = "index")
    public String index(Model model, HttpServletRequest request) {

        // 模板列表
        templateList(model, request);

        CommonDao<String> dao = getDao();

        // 短信平台
        String appKey = dao.getFromHash(PREFIX + CONFIG, APP_KEY);
        String secetKey = dao.getFromHash(PREFIX + CONFIG, SECRET_KEY);

        model.addAttribute(APP_KEY, appKey);
        model.addAttribute(SECRET_KEY, secetKey);

        // 邮箱配置
        String smtp = dao.getFromHash(PREFIX_EMAIL + CONFIG, EMAIL_SMTP);
        String port = dao.getFromHash(PREFIX_EMAIL + CONFIG, EMAIL_PORT);
        String account = dao.getFromHash(PREFIX_EMAIL + CONFIG, EMAIL_ACCOUNT);
        String password = dao.getFromHash(PREFIX_EMAIL + CONFIG, EMAIL_PASSWORD);
        String testEmail = dao.getFromHash(PREFIX_EMAIL + CONFIG, EMAIL_TEST);

        model.addAttribute(EMAIL_SMTP, smtp);
        model.addAttribute(EMAIL_PORT, port);
        model.addAttribute(EMAIL_ACCOUNT, account);
        model.addAttribute(EMAIL_PASSWORD, password);
        model.addAttribute(EMAIL_TEST, testEmail);

        return "server/warn";
    }

    /**
     * 短信模板列表
     * 
     * @param model
     * @param requst
     * @return
     */
    @RequestMapping(value = "sms/list")
    public String templateList(Model model, HttpServletRequest requst) {

        CommonDao<SmsTemplate> mapDao = getDao();

        Map<Long, SmsTemplate> map = mapDao.getFromZSet(PREFIX + SMS_TEMPLATE_LIST, 0, DateUtil.getUnixTime());

        List<SmsTemplate> templates = new ArrayList<>();

        Iterator<Long> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            Long key = iterator.next();
            SmsTemplate template = map.get(key);
            templates.add(template);
        }

        model.addAttribute(SMS_TEMPLATE_LIST, templates);

        return "server/warn";
    }

    /**
     * 邮件配置
     * 
     * @param model
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "email/config")
    public String emailConfig(Model model, HttpServletRequest request) {
        ResponseMessage message = new ResponseMessage();

        String smtp = request.getParameter(EMAIL_SMTP);

        String port = request.getParameter(EMAIL_PORT);

        String account = request.getParameter(EMAIL_ACCOUNT);

        String password = request.getParameter(EMAIL_PASSWORD);

        String testEmail = request.getParameter(EMAIL_TEST);

        CommonDao<String> dao = getDao();

        dao.putToHash(PREFIX_EMAIL + CONFIG, EMAIL_SMTP, smtp);
        dao.putToHash(PREFIX_EMAIL + CONFIG, EMAIL_PORT, port);
        dao.putToHash(PREFIX_EMAIL + CONFIG, EMAIL_ACCOUNT, account);
        dao.putToHash(PREFIX_EMAIL + CONFIG, EMAIL_PASSWORD, password);
        dao.putToHash(PREFIX_EMAIL + CONFIG, EMAIL_TEST, testEmail);

        message.setMsg("保存成功！");

        return JSON.toJSONString(message);
    }

    /**
     * 测试邮件
     * 
     * @param model
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "email/sendTest")
    public String sendEmailTest(Model model, HttpServletRequest request) {
        ResponseMessage message = new ResponseMessage();

        CommonDao<String> dao = getDao();

        // 邮箱配置
        String smtp = dao.getFromHash(PREFIX_EMAIL + CONFIG, EMAIL_SMTP);
        String port = dao.getFromHash(PREFIX_EMAIL + CONFIG, EMAIL_PORT);
        String account = dao.getFromHash(PREFIX_EMAIL + CONFIG, EMAIL_ACCOUNT);
        String password = dao.getFromHash(PREFIX_EMAIL + CONFIG, EMAIL_PASSWORD);
        String receiver = dao.getFromHash(PREFIX_EMAIL + CONFIG, EMAIL_TEST);

        EmailTool tool = new EmailTool();
        tool.setSmtp(smtp);
        tool.setPort(port);
        tool.setAccount(account);
        tool.setPassword(password);
        tool.setReceiver(receiver);
        tool.setContent("测试邮件！");
        tool.setTitle("测试邮件");

        try {
            tool.send();
            message.setMsg("发送成功！");
        } catch (Exception ex) {
            message.setFlag(MonitorConstant.MSG_ERROR_CODE);
            message.setMsg("邮件测试发送失败，请确认配置信息配置正确！");
            logger.error("mail send error!", ex);
        }
        return JSON.toJSONString(message);
    }

    /**
     * 短信平台配置
     * 
     * @param model
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "sms/config")
    public String smsConfig(Model model, HttpServletRequest request) {
        ResponseMessage message = new ResponseMessage();

        String appKey = request.getParameter(APP_KEY);

        String secretKey = request.getParameter(SECRET_KEY);

        CommonDao<String> dao = getDao();

        dao.putToHash(PREFIX + CONFIG, APP_KEY, appKey);
        dao.putToHash(PREFIX + CONFIG, SECRET_KEY, secretKey);

        message.setMsg("保存成功！");

        return JSON.toJSONString(message);
    }

    /**
     * 添加短信模板
     * 
     * @param model
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "sms/add")
    public String addSmsTemplate(Model model, HttpServletRequest request) {

        ResponseMessage message = new ResponseMessage();
        Long id = DateUtil.getUnixTime();

        /** 应用场景 **/
        String scene = request.getParameter(SCENE);
        /** 短信签名 **/
        String signature = request.getParameter(SIGNATURE);
        /** 模板ID **/
        String templateId = request.getParameter(TEMPLATE_ID);
        /** 模板内容 **/
        String content = request.getParameter(CONTENT);

        SmsTemplate template = new SmsTemplate();
        template.setId(id);
        template.setScene(scene);
        template.setSignature(signature);
        template.setTemplateId(templateId);
        template.setContent(content);

        // 保存数据
        CommonDao<SmsTemplate> mapDao = getDao();
        mapDao.putToZSet(PREFIX + SMS_TEMPLATE_LIST, id, template);

        message.setMsg("添加成功");
        message.setValue(template);

        return JSON.toJSONString(message);
    }

    /**
     * 修改短信模板
     * 
     * @param model
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "sms/update")
    public String updateSmsTemplate(Model model, HttpServletRequest request) {
        ResponseMessage message = new ResponseMessage();

        String id = request.getParameter(ID);

        if (!StringUtil.isDigit(id)) {
            message.setFlag(MonitorConstant.MSG_ERROR_CODE);
            message.setMsg("参数错误！");

            return JSON.toJSONString(message);
        }

        /** 应用场景 **/
        String scene = request.getParameter(SCENE);
        /** 短信签名 **/
        String signature = request.getParameter(SIGNATURE);
        /** 模板ID **/
        String templateId = request.getParameter(TEMPLATE_ID);
        /** 模板内容 **/
        String content = request.getParameter(CONTENT);

        SmsTemplate template = new SmsTemplate();
        template.setId(Long.parseLong(id));
        template.setScene(scene);
        template.setSignature(signature);
        template.setTemplateId(templateId);
        template.setContent(content);

        // 保存数据
        CommonDao<SmsTemplate> mapDao = getDao();
        mapDao.putToZSet(PREFIX + SMS_TEMPLATE_LIST, Long.parseLong(id), template);

        message.setMsg("修改成功");

        return JSON.toJSONString(message);
    }

    /**
     * 删除短信模板
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "sms/delete")
    public String delete(HttpServletRequest request) {

        ResponseMessage message = new ResponseMessage();

        String id = request.getParameter(ID);

        if (!StringUtil.isDigit(id)) {
            message.setFlag(MonitorConstant.MSG_ERROR_CODE);
            message.setMsg("id错误");
        } else {
            CommonDao<SmsTemplate> dao = getDao();

            SmsTemplate template = dao.removeFromZSet(PREFIX + SMS_TEMPLATE_LIST, Long.parseLong(id));
            message.setValue(template);
            message.setMsg("删除成功！");
        }

        String result = JSON.toJSONString(message);

        return result;
    }
}
