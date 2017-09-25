package com.ruban.monitor.memcached.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruban.framework.core.utils.commons.DateUtil;
import com.ruban.framework.core.utils.commons.RandomUtil;
import com.ruban.framework.core.utils.commons.StringUtil;
import com.ruban.monitor.common.Pagination;
import com.ruban.monitor.dao.CommonDao;
import com.ruban.monitor.memcached.bean.Item;

/**
 * memcached操作
 * 
 * @author yjwang
 *
 */
@Controller(value = "memcachedOperation")
@RequestMapping(value = "/memcached")
public class OperationController extends MemcachedController {

    private static final Logger logger = LoggerFactory.getLogger(OperationController.class);

    @RequestMapping(value = "opt/data/list")
    public String list(Model model, HttpServletRequest request) {

        String page = request.getParameter("page");

        int start = 0;
        int end = 9;

        if (StringUtil.isDigit(page)) {
            start = (Integer.parseInt(page) - 1) * 10;
            end = start + 9;
        } else {
            page = "1";
        }

        CommonDao<Long> dao = getDao();
        CommonDao<Item> itemDao = getDao();

        List<Item> items = new ArrayList<>();

        List<Long> ids = dao.getList(PREFIX + DATA_ID, start, end);

        Long pages = dao.sizeOfList(PREFIX + DATA_ID);
        if (ids != null) {
            for (int i = 0; i < ids.size(); i++) {
                Item item = itemDao.getFromHash(PREFIX + DATA_ID_KEY, ids.get(i));

                if (item != null)
                    items.add(item);
                else
                    dao.remOfList(PREFIX + DATA_ID, i, i);
            }
        }

        model.addAttribute("total", pages);

        Pagination<Item> pagination = new Pagination<>();
        pagination.gereate(Integer.parseInt(page), pages.intValue());

        model.addAttribute("pagination", pagination);

        model.addAttribute("items", items);

        return "memcached/operation/list";
    }

    /**
     * 数据读取
     * 
     * @return
     */
    @RequestMapping(value = "opt/read/page")
    public String readPage() {
        return "memcached/operation/read";
    }

    /**
     * 数据读取
     * 
     * @return
     */
    @RequestMapping(value = "opt/read")
    public String read(Model model, HttpServletRequest request) {

        String key = request.getParameter("key");

        if (StringUtil.isNotNullOrEmpty(key)) {
            Object data = getOperator().get(key);
            String type = "";
            if (data == null) {
                data = "无";
                type = "无";

                CommonDao<String> dao = getDao();
                CommonDao<Long> longDao = getDao();
                Long id = longDao.getFromHash(PREFIX + DATA_KEY_ID, key);
                if (id != null) {
                    longDao.removeFromHash(PREFIX + DATA_KEY_ID, key);
                    dao.removeFromHash(PREFIX + DATA_ID_KEY, id);
                    longDao.removeFromList(PREFIX + DATA_ID, id);
                }
            } else {
                // 该key值是否已经在监控系统存在过
                dataList(key, null);

                type = data.getClass().getName();
            }
            model.addAttribute("data", data);
            model.addAttribute("type", type);
        }

        return "memcached/operation/readData";
    }

    /**
     * 写入页面
     * 
     * @return
     */
    @RequestMapping(value = "opt/write/page")
    public String writePage() {

        return "memcached/operation/write";
    }

    /**
     * 写入操作
     * 
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "opt/write")
    public String write(Model model, HttpServletRequest request) {

        String key = request.getParameter("key");
        String type = request.getParameter("type");
        String value = request.getParameter("value");
        String time = request.getParameter("time");

        try {
            if ("string".equals(type)) {
                if (StringUtil.isDigit(time)) {
                    getOperator().set(key, Integer.parseInt(time), value);
                } else {
                    getOperator().set(key, value);
                }
            } else if ("hex".equals(type)) {
                if (StringUtil.isDigit(time)) {
                    getOperator().set(key, Integer.parseInt(time), Base64Utils.decodeFromString(value));
                } else {
                    getOperator().set(key, Base64Utils.decodeFromString(value));
                }
            }

            dataList(key, time);

        } catch (Exception ex) {
            logger.error("设置错误！", ex);
        }

        return "写入成功";
    }

    /**
     * 失效时间设置
     * 
     * @return
     */
    @RequestMapping(value = "opt/touch/page")
    public String touchPage() {
        return "memcached/operation/touch";
    }

    /**
     * 失效操作
     * 
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "opt/touch")
    public String touch(Model model, HttpServletRequest request) {

        String key = request.getParameter("key");
        String time = request.getParameter("time");

        if (StringUtil.isDigit(time)) {
            Object data = getOperator().get(key);

            if (data != null) {
                getOperator().touch(key, Integer.parseInt(time));

                dataList(key, time);
            }

            return "设置成功！";
        }

        return "设置失败！";
    }

    /**
     * 组织key，id直接的对应关系
     * 
     * @param key
     * @param time
     */
    private void dataList(String key, String time) {
        CommonDao<Item> itemDao = getDao();
        CommonDao<Long> longDao = getDao();

        Long id = longDao.getFromHash(PREFIX + DATA_KEY_ID, key);
        if (id == null) {
            id = Long.parseLong(DateUtil.getTime() + RandomUtil.nextStringByDigit(5));
            longDao.putToHash(PREFIX + DATA_KEY_ID, key, id);

            longDao.addToList(PREFIX + DATA_ID, id);
        }

        Item item = itemDao.getFromHash(PREFIX + DATA_ID_KEY, id);
        if (item == null) {
            item = new Item();
        }

        item.setKey(key);

        // 设置失效时间
        if (StringUtil.isDigit(time)) {
            item.setExp(Integer.parseInt(time));
        }

        itemDao.putToHash(PREFIX + DATA_ID_KEY, id, item);
    }

    /**
     * 刷新页面
     * 
     * @return
     */
    @RequestMapping(value = "opt/flush/page")
    public String flushPage() {

        return "memcached/operation/flush";
    }

    /**
     * 清空缓存
     * 
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "opt/flush")
    public String flush(Model model, HttpServletRequest request) {
        CommonDao<String> dao = getDao();
        CommonDao<Long> longDao = getDao();
        CommonDao<Item> itemDao = getDao();

        List<Long> ids = longDao.getAllList(PREFIX + DATA_ID);
        if (ids != null) {
            for (int i = 0; i < ids.size(); i++) {
                Long id = ids.get(i);

                Item item = itemDao.getFromHash(PREFIX + DATA_ID_KEY, id);
                if (item != null) {
                    longDao.removeFromHash(PREFIX + DATA_KEY_ID, item.getKey());
                }
                dao.removeFromHash(PREFIX + DATA_ID_KEY, id);
            }
        }

        getMonitor().flush(0);

        return "已清空缓存！";
    }

}