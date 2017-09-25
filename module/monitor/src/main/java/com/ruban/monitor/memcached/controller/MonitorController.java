package com.ruban.monitor.memcached.controller;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ScheduledFuture;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.ruban.framework.core.utils.commons.DateUtil;
import com.ruban.framework.core.utils.commons.StringUtil;
import com.ruban.framework.core.utils.format.FormatUtil;
import com.ruban.monitor.common.ConfigHolder;
import com.ruban.monitor.common.MonitorConstant;
import com.ruban.monitor.common.ResponseMessage;
import com.ruban.monitor.dao.CommonDao;
import com.ruban.monitor.memcached.bean.ServerStats;
import com.ruban.monitor.memcached.bean.Stats;
import com.ruban.monitor.memcached.domain.Server;

/**
 * memcached监控
 * 
 * @author yjwang
 *
 */
@Controller(value = "memcachedMointorController")
@RequestMapping(value = "/memcached")
public class MonitorController extends MemcachedController {

    private static final Logger logger = LoggerFactory.getLogger(MonitorController.class);

    @Autowired
    private TaskScheduler scheduler;

    /**
     * 开启监控
     * 
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "monitor/start")
    public String start() {
        ResponseMessage message = new ResponseMessage();

        CommonDao<String> commonDao = getDao();

        String statsRate = commonDao.get(PREFIX + STATS_RATE);
        
        Map<String, ServerStats> oldStats = new HashMap<>();

        // 创建监听器

        int schedulRate = Integer.parseInt(statsRate);

        ScheduledFuture<?> future = ConfigHolder.getFuture(PREFIX + STATS);

        if (future != null && !future.isCancelled()) {
            future.cancel(true);
        }

        if (schedulRate > 0 && schedulRate < 1000000) {

            future = scheduler.scheduleAtFixedRate(new Runnable() {

                @Override
                public void run() {

                    // 收集数据(memcached stats 数据)
                    Long time = DateUtil.getUnixTime();
                    List<ServerStats> result = getMonitor().getStats();

                    // 保存数据
                    CommonDao<List<Stats>> statsDao = getDao();

                    for (ServerStats stats : result) {
                        Collections.sort(stats.getStats());
                        ServerStats old = oldStats.put(stats.getIp(), stats);
                        // 计算阈值
                        // 发送报警
                        
                        statsDao.putToZSet(PREFIX + STATS + stats.getIp(), time, stats.getStats());
                    }

                    logger.info("pushToHash in stats!");
                }
            }, schedulRate * 1000);

            ConfigHolder.putFutrue(PREFIX + STATS, future);

            message.setMsg("监控已开启");

            commonDao.update(PREFIX + MONITOR_STATE, "1");
        } else {
            message.setFlag(1);
            message.setMsg("监控频率参数错误");
        }

        String result = JSON.toJSONString(message);

        return result;
    }

    /**
     * 开启监控
     * 
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "monitor/stop")
    public String stop() {
        ResponseMessage message = new ResponseMessage();

        CommonDao<String> commonDao = getDao();

        ScheduledFuture<?> old = ConfigHolder.getFuture(PREFIX + STATS);

        if (old != null) {
            old.cancel(true);
            message.setMsg("memcached监控已停止！");
            commonDao.update(PREFIX + MONITOR_STATE, "2");
        } else {
            message.setFlag(1);
            message.setMsg("未找到相应的监控进程！");
            commonDao.update(PREFIX + MONITOR_STATE, "2");
        }

        String result = JSON.toJSONString(message);

        return result;
    }

    /**
     * 获取服务器最新状态
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "monitor/stats")
    public String stats(Model model) {

        try {
            // 获取状态信息结果
            List<ServerStats> result = getMonitor().getStats();

            // 排序
            for (ServerStats stats : result) {
                Collections.sort(stats.getStats());
            }
            model.addAttribute("stats", result);
        } catch (Exception ex) {
            model.addAttribute(MonitorConstant.MSG, "请配置服务器信息");
            logger.error("config error!", ex);
        }

        return "memcached/monitor/stats";
    }

    /**
     * 服务器配置
     * 
     * @return
     */
    @RequestMapping(value = "monitor/setting")
    public String setting(Model model) {

        try {
            List<ServerStats> result = getMonitor().getSetting();

            // 排序
            for (ServerStats stats : result) {
                Collections.sort(stats.getSettings());
            }

            model.addAttribute("setting", result);
        } catch (Exception ex) {
            model.addAttribute(MonitorConstant.MSG, "请配置服务器信息");
            logger.error("config error!", ex);
        }

        return "memcached/monitor/setting";
    }

    /**
     * 数据项统计
     * 
     * @return
     */
    @RequestMapping(value = "monitor/size")
    public String size(Model model) {
        try {
            List<ServerStats> result = getMonitor().getSize();

            // 排序
            for (ServerStats stats : result) {
                Collections.sort(stats.getSizes());
            }

            model.addAttribute("size", result);
        } catch (Exception ex) {
            model.addAttribute(MonitorConstant.MSG, "请配置服务器信息");
            logger.error("config error!", ex);
        }

        return "memcached/monitor/size";
    }

    /**
     * 数据项统计
     * 
     * @return
     */
    @RequestMapping(value = "monitor/items")
    public String items(Model model) {

        try {
            List<ServerStats> result = getMonitor().getItems();

            // 排序
            for (ServerStats stats : result) {
                Collections.sort(stats.getItems());
            }

            model.addAttribute("items", result);
        } catch (Exception ex) {
            model.addAttribute(MonitorConstant.MSG, "请配置服务器信息");
            logger.error("config error!", ex);
        }

        return "memcached/monitor/items";
    }

    /**
     * 图标展示(通用图标展示)<br/>
     * <ul>
     * <li>命中率</li>
     * <li>CPU使用</li>
     * <li>字节流量</li>
     * </ul>
     * 
     * @return
     */
    @RequestMapping(value = "monitor/chart")
    public String chart(Model model, HttpServletRequest request) {

        // 统计的开始时间，结束时间
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");

        Long searchStart = 0L;
        Long searchEnd = 0L;

        if (StringUtil.isNullOrEmpty(startTime)) {
            Date date = DateUtil.subDate(new Date(), 1);
            searchStart = DateUtil.getUnixTime(date);
            startTime = DateUtil.getDateTimeStr(date);
        } else {
            searchStart = DateUtil.getUnixTime(DateUtil.parseDateTime(startTime));
        }

        if (StringUtil.isNullOrEmpty(endTime)) {
            Date date = DateUtil.getNowTime();
            searchEnd = DateUtil.getUnixTime(date);
            endTime = DateUtil.getDateTimeStr(date);
        } else {
            searchEnd = DateUtil.getUnixTime(DateUtil.parseDateTime(endTime));
        }

        String[] item = { "rusage_user", "rusage_system", "uptime", "cmd_get", "get_hits", "get_misses", "bytes_read", "bytes_written", "bytes" };

        Map<String, Map<Long, Map<String, String>>> data = getChartData(searchStart, searchEnd, item);

        Map<String, Map<Long, Map<String, String>>> result = new HashMap<>();

        // CPU
        double oldUser = 0;
        double oldSystem = 0;
        double oldUptime = 0;

        // 命中率
        double oldHits = 0;
        double oldGets = 0;

        // 读取字节
        double oldReadByts = 0;

        // 写入字节
        double oldWriteByts = 0;

        if (data != null) {
            Iterator<String> iterator = data.keySet().iterator();
            Map<Long, Map<String, String>> resultScore = new TreeMap<>();

            while (iterator.hasNext()) {
                String host = iterator.next();
                Map<Long, Map<String, String>> itemMap = data.get(host);

                Iterator<Long> scoreIterator = itemMap.keySet().iterator();

                while (scoreIterator.hasNext()) {
                    Long score = scoreIterator.next();
                    Map<String, String> dataMap = itemMap.get(score);

                    Map<String, String> resultMap = new HashMap<>();

                    // 数据残缺时，进行清理
                    if (dataMap == null || dataMap.get("rusage_user") == null) {
                        CommonDao<List<Stats>> statsDao = getDao();
                        statsDao.removeFromZSet(PREFIX + STATS + host, score);
                        continue;
                    }

                    // CPU使用率
                    double user = Double.parseDouble(dataMap.get("rusage_user"));
                    double system = Double.parseDouble(dataMap.get("rusage_system"));
                    String uptime = dataMap.get("uptime");

                    if (StringUtil.isNullOrEmpty(uptime)) {
                        CommonDao<Long> statsDao = getDao();
                        statsDao.removeFromZSet(PREFIX + STATS + host, score);
                        continue;
                    }

                    double currentUptime = Double.parseDouble(uptime);
                    double top = user - oldUser + system - oldSystem;
                    double timeBottom = currentUptime - oldUptime;

                    if (timeBottom == 0.0) {
                        continue;
                    }

                    String rate = FormatUtil.fmtMicrometerByScale(top / timeBottom * 100 + "", 4);
                    resultMap.put("cpuRate", rate);

                    // 保持旧值
                    oldUser = user;
                    oldSystem = system;
                    oldUptime = currentUptime;

                    // 命中次数
                    String getHits = dataMap.get("get_hits");
                    String gets = dataMap.get("cmd_get");
                    top = Double.parseDouble(getHits) - oldHits;
                    double bottom = Double.parseDouble(gets) - oldGets;
                    if (bottom != 0) {
                        String hitRate = FormatUtil.fmtMicrometerByScale(top / bottom * 100 + "", 2);
                        resultMap.put("hitRate", hitRate);
                    } else {
                        resultMap.put("hitRate", "0.00");
                    }

                    oldHits = Double.parseDouble(getHits);
                    oldGets = Double.parseDouble(gets);

                    // 读取流量
                    double readByts = Double.parseDouble(dataMap.get("bytes_read"));

                    String readRate = FormatUtil.fmtMicrometerByScale((readByts - oldReadByts) / timeBottom + "", 0);
                    resultMap.put("readRate", readRate);

                    oldReadByts = readByts;

                    // 写入流量
                    double writtenByts = Double.parseDouble(dataMap.get("bytes_written"));
                    String writtenRate = FormatUtil.fmtMicrometerByScale((writtenByts - oldWriteByts) / timeBottom + "", 0);
                    resultMap.put("writtenRate", writtenRate);

                    oldWriteByts = writtenByts;

                    resultScore.put(score, resultMap);
                }

                result.put(host, resultScore);
            }
        }

        model.addAttribute("result", result);
        model.addAttribute("startTime", startTime);
        model.addAttribute("endTime", endTime);

        return "memcached/monitor/genericChart";
    }

    /**
     * 图标展示
     * 
     * @return
     */
    @RequestMapping(value = "monitor/drawChart")
    public String drawChart(Model model, HttpServletRequest request) {

        // 统计的开始时间，结束时间
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        String item = request.getParameter("item");

        Map<String, Map<Long, Map<String, String>>> result = getChartData(Long.parseLong(startDate), Long.parseLong(endDate), item);

        model.addAttribute("result", result);

        return "memcached/monitor/items";
    }

    /**
     * 依据起始时间及监控项，得到列表
     * 
     * @param startDate
     * @param endDate
     * @param key
     * @return
     */
    private Map<String, Map<Long, Map<String, String>>> getChartData(Long startDate, Long endDate, String... items) {

        Map<String, Map<Long, Map<String, String>>> result = new HashMap<>();

        // 开始时间为空
        if (StringUtil.isNullOrEmpty(startDate)) {
            return null;
        }

        // 结束时间为空
        if (StringUtil.isNullOrEmpty(endDate)) {
            endDate = Long.parseLong(DateUtil.getTime());
        }

        // 获取当前所有的server
        CommonDao<Server> mapDao = getDao();
        Map<Long, Server> map = mapDao.getFromZSet(PREFIX + SERVER_LIST, 0, DateUtil.getUnixTime());

        CommonDao<List<Stats>> statsDao = getDao();

        Iterator<Long> iterator = map.keySet().iterator();

        while (iterator.hasNext()) {

            Map<Long, Map<String, String>> dataMap = new TreeMap<>();

            Long key = iterator.next();
            Server server = map.get(key);

            Map<Long, List<Stats>> statsMap = statsDao.getFromZSet(PREFIX + STATS + server.getHost(), startDate, endDate);

            Iterator<Long> statsIterator = statsMap.keySet().iterator();
            while (statsIterator.hasNext()) {
                Long score = statsIterator.next();
                List<Stats> stats = statsMap.get(score);

                Map<String, String> itemMap = new TreeMap<>();
                for (String item : items) {
                    for (int i = 0; i < stats.size(); i++) {

                        if (stats.get(i).getKey().equals(item)) {
                            itemMap.put(item, (String) stats.get(i).getValue());
                        }
                    }
                }

                dataMap.put(score, itemMap);
            }

            result.put(server.getHost(), dataMap);
        }

        return result;
    }
}