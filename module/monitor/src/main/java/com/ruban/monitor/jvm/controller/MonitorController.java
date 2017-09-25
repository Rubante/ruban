package com.ruban.monitor.jvm.controller;

import java.lang.management.MemoryUsage;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
import com.google.common.collect.Lists;
import com.ruban.framework.core.utils.biz.IpUtil;
import com.ruban.framework.core.utils.commons.DateUtil;
import com.ruban.framework.core.utils.commons.StringUtil;
import com.ruban.monitor.common.ConfigHolder;
import com.ruban.monitor.common.MonitorConstant;
import com.ruban.monitor.common.ResponseMessage;
import com.ruban.monitor.dao.CommonDao;
import com.ruban.monitor.jvm.JmxMonitor;
import com.ruban.monitor.jvm.bean.LoadedClassInfo;
import com.ruban.monitor.jvm.bean.MemoryBean;
import com.ruban.monitor.jvm.bean.MemoryTool;
import com.ruban.monitor.jvm.domain.Server;

/**
 * JVM监控
 * 
 * @author yjwang
 *
 */
@Controller(value = "jvmMointorController")
@RequestMapping(value = "/jvm")
public class MonitorController extends JvmController {

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
    public String start(HttpServletRequest request) {
        ResponseMessage message = new ResponseMessage();

        String id = request.getParameter("id");

        // 判断上送id是否正确
        if (!StringUtil.isDigit(id)) {
            message.setFlag(1);
            message.setMsg("id 参数错误！");

            return JSON.toJSONString(message);
        }

        CommonDao<Server> serverDao = getDao();

        // 保存数据
        final CommonDao<MemoryBean> memoryBeanDao = getDao();
        final CommonDao<LoadedClassInfo> loadedClassInfoDao = getDao();

        final Server server = serverDao.getFromZSet(PREFIX + SERVER_LIST, Long.parseLong(id));

        String stateKey = PREFIX + MONITOR_STATE + ":" + server.getHost() + ":" + server.getPort();

        // 创建监听器
        int schedulRate = server.getRate();

        ScheduledFuture<?> future = ConfigHolder.getFuture(stateKey);

        if (future != null && !future.isCancelled()) {
            future.cancel(true);
        }

        if (schedulRate > 0 && schedulRate < 1000000) {

            server.setState(1);
            serverDao.putToZSet(PREFIX + SERVER_LIST, server.getId(), server);

            future = scheduler.scheduleAtFixedRate(new Runnable() {

                @Override
                public void run() {

                    // 收集数据
                    Long time = DateUtil.getUnixTime();
                    JmxMonitor monitor = getMonitor(server.getHost(), server.getPort());
                    MemoryUsage heapMemory = monitor.getMemoryMXBean().getHeapMemoryUsage();
                    if (heapMemory != null)
                        memoryBeanDao.putToZSet(PREFIX + MEM_HEAP + server.getHost(), time, MemoryTool.convert(heapMemory, time));

                    MemoryUsage nonHeapMemory = monitor.getMemoryMXBean().getNonHeapMemoryUsage();
                    if (nonHeapMemory != null)
                        memoryBeanDao.putToZSet(PREFIX + MEM_NON_HEAP + server.getHost(), time, MemoryTool.convert(nonHeapMemory, time));

                    MemoryUsage codeMemory = monitor.getCodeCacheMemoryUsage();
                    if (codeMemory != null)
                        memoryBeanDao.putToZSet(PREFIX + MEM_CODE_CACHE + server.getHost(), time, MemoryTool.convert(codeMemory, time));

                    MemoryUsage edenMemory = monitor.getEdenMemoryUsage();
                    if (edenMemory != null)
                        memoryBeanDao.putToZSet(PREFIX + MEM_EDEN + server.getHost(), time, MemoryTool.convert(edenMemory, time));

                    MemoryUsage oldGenMemory = monitor.getOldGenMemoryUsage();
                    if (oldGenMemory != null)
                        memoryBeanDao.putToZSet(PREFIX + MEM_OLD_GEN + server.getHost(), time, MemoryTool.convert(oldGenMemory, time));

                    MemoryUsage survivorMemory = monitor.getSurvivorSpaceMemoryUsage();
                    if (survivorMemory != null)
                        memoryBeanDao.putToZSet(PREFIX + MEM_SURVIVOR_SPACE + server.getHost(), time, MemoryTool.convert(survivorMemory, time));

                    MemoryUsage permGenMemory = monitor.getPermGenMemoryUsage();
                    if (permGenMemory != null)
                        memoryBeanDao.putToZSet(PREFIX + MEM_PERM_GEN + server.getHost(), time, MemoryTool.convert(permGenMemory, time));

                    LoadedClassInfo classInfo = LoadedClassInfo.wrap(monitor.getClassLoadingMXBean());
                    classInfo.setId(time);

                    loadedClassInfoDao.putToZSet(PREFIX + LOADED_CLASS + server.getHost(), time, classInfo);

                    logger.info("putToZSet jmx!");
                }
            }, schedulRate * 1000);

            ConfigHolder.putFutrue(stateKey, future);

            message.setMsg("jvm监控已开启");

        } else {
            message.setFlag(MonitorConstant.MSG_ERROR_CODE);
            message.setMsg("监控频率参数错误");
        }

        String result = JSON.toJSONString(message);

        return result;
    }

    /**
     * 结束监控
     * 
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "monitor/stop")
    public String stop(HttpServletRequest request) {
        ResponseMessage message = new ResponseMessage();

        String id = request.getParameter("id");

        // 判断上送id是否正确
        if (!StringUtil.isDigit(id)) {
            message.setFlag(1);
            message.setMsg("id 参数错误！");

            return JSON.toJSONString(message);
        }

        CommonDao<String> commonDao = getDao();

        CommonDao<Server> serverDao = getDao();

        Map<Long, Server> map = serverDao.getFromZSet(PREFIX + SERVER_LIST, 0, DateUtil.getUnixTime());

        Iterator<Long> iterator = map.keySet().iterator();

        while (iterator.hasNext()) {
            Long key = iterator.next();

            final Server server = map.get(key);

            if (server.getState() == 1) {
                server.setState(0);
                serverDao.putToZSet(PREFIX + SERVER_LIST, server.getId(), server);

                String stateKey = PREFIX + MONITOR_STATE + ":" + server.getHost() + ":" + server.getPort();

                ScheduledFuture<?> old = ConfigHolder.getFuture(stateKey);

                if (old != null) {
                    old.cancel(true);
                    message.setMsg("jvm监控已停止！");
                    commonDao.update(stateKey, "2");
                } else {
                    message.setFlag(1);
                    message.setMsg("未找到相应的监控进程！");
                    commonDao.update(stateKey, "2");
                }
            }

        }

        String result = JSON.toJSONString(message);

        return result;
    }

    /**
     * 获取服务器操作系统信息
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "monitor/os")
    public String os(Model model, HttpServletRequest request) {
        Map<String, OperatingSystemMXBean> result = new HashMap<>();

        CommonDao<Server> serverDao = getDao();

        Map<Long, Server> servers = serverDao.getFromZSet(PREFIX + SERVER_LIST, 0, DateUtil.getUnixTime());

        Iterator<Long> iterator = servers.keySet().iterator();

        String host = request.getParameter(HOST);

        String port = request.getParameter(PORT);

        if (!IpUtil.checkIp(host)) {
            if (iterator.hasNext()) {
                Long key = iterator.next();

                final Server server = servers.get(key);

                // 当前ip
                host = server.getHost();
                port = String.valueOf(server.getPort());
            }
        }

        if (StringUtil.isNullOrEmpty(port)) {
            port = "9999";
        }

        JmxMonitor monitor = getMonitor(host, Integer.parseInt(port));

        result.put(host, monitor.getOperatingSystemMXBean());

        model.addAttribute("servers", servers);
        model.addAttribute("result", result);
        model.addAttribute(HOST, host);

        return "jvm/monitor/os";
    }

    /**
     * 获取运行环境
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "monitor/runtime")
    public String runtime(Model model, HttpServletRequest request) {
        Map<String, RuntimeMXBean> result = new HashMap<>();

        CommonDao<Server> serverDao = getDao();

        Map<Long, Server> servers = serverDao.getFromZSet(PREFIX + SERVER_LIST, 0, DateUtil.getUnixTime());

        Iterator<Long> iterator = servers.keySet().iterator();

        String host = request.getParameter(HOST);

        String port = request.getParameter(PORT);

        if (!IpUtil.checkIp(host)) {
            if (iterator.hasNext()) {
                Long key = iterator.next();

                final Server server = servers.get(key);

                // 当前ip
                host = server.getHost();
                port = String.valueOf(server.getPort());
            }
        }

        if (StringUtil.isNullOrEmpty(port)) {
            port = "9999";
        }

        JmxMonitor monitor = getMonitor(host, Integer.parseInt(port));
        result.put(host, monitor.getRuntimeMXBean());
        
        model.addAttribute("servers", servers);
        model.addAttribute("result", result);
        model.addAttribute(HOST, host);

        return "jvm/monitor/runtime";
    }

    /**
     * 内存状态
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "monitor/memory")
    public String memory(Model model, HttpServletRequest request) {

        CommonDao<Server> serverDao = getDao();

        CommonDao<MemoryBean> memoryBeanDao = getDao();

        // 统计的开始时间，结束时间
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");

        Long searchStart = 0L;
        Long searchEnd = 0L;

        if (StringUtil.isNullOrEmpty(startTime)) {
            Date date = DateUtil.subDate(new Date(), 2);
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

        Map<Long, Server> map = serverDao.getFromZSet(PREFIX + SERVER_LIST, 0, DateUtil.getUnixTime());

        Iterator<Long> iterator = map.keySet().iterator();

        Map<String, List<MemoryBean>> heapResult = new HashMap<>();
        Map<String, List<MemoryBean>> nonHeapResult = new HashMap<>();
        Map<String, List<MemoryBean>> codeCacheResult = new HashMap<>();
        Map<String, List<MemoryBean>> edenResult = new HashMap<>();
        Map<String, List<MemoryBean>> oldGenResult = new HashMap<>();
        Map<String, List<MemoryBean>> survivorSpaceResult = new HashMap<>();
        Map<String, List<MemoryBean>> permGenResult = new HashMap<>();

        while (iterator.hasNext()) {
            Long key = iterator.next();

            final Server server = map.get(key);

            Map<Long, MemoryBean> heapMap = memoryBeanDao.getFromZSet(PREFIX + MEM_HEAP + server.getHost(), searchStart, searchEnd);
            heapResult.put(server.getHost(), Lists.newArrayList(heapMap.values()));

            Map<Long, MemoryBean> nonHeapMap = memoryBeanDao.getFromZSet(PREFIX + MEM_NON_HEAP + server.getHost(), searchStart, searchEnd);
            nonHeapResult.put(server.getHost(), Lists.newArrayList(nonHeapMap.values()));

            Map<Long, MemoryBean> codeCacheMap = memoryBeanDao.getFromZSet(PREFIX + MEM_CODE_CACHE + server.getHost(), searchStart, searchEnd);
            codeCacheResult.put(server.getHost(), Lists.newArrayList(codeCacheMap.values()));

            Map<Long, MemoryBean> edenMap = memoryBeanDao.getFromZSet(PREFIX + MEM_EDEN + server.getHost(), searchStart, searchEnd);
            edenResult.put(server.getHost(), Lists.newArrayList(edenMap.values()));

            Map<Long, MemoryBean> oldGenMap = memoryBeanDao.getFromZSet(PREFIX + MEM_OLD_GEN + server.getHost(), searchStart, searchEnd);
            oldGenResult.put(server.getHost(), Lists.newArrayList(oldGenMap.values()));

            Map<Long, MemoryBean> survivorSpaceMap = memoryBeanDao.getFromZSet(PREFIX + MEM_SURVIVOR_SPACE + server.getHost(), searchStart,
                    searchEnd);
            survivorSpaceResult.put(server.getHost(), Lists.newArrayList(survivorSpaceMap.values()));

            Map<Long, MemoryBean> permGenMap = memoryBeanDao.getFromZSet(PREFIX + MEM_PERM_GEN + server.getHost(), searchStart, searchEnd);
            permGenResult.put(server.getHost(), Lists.newArrayList(permGenMap.values()));
        }

        model.addAttribute("heapResult", heapResult);
        model.addAttribute("nonHeapResult", nonHeapResult);
        model.addAttribute("codeCacheResult", codeCacheResult);
        model.addAttribute("edenResult", edenResult);
        model.addAttribute("oldGenResult", oldGenResult);
        model.addAttribute("survivorSpaceResult", survivorSpaceResult);
        model.addAttribute("permGenResult", permGenResult);
        model.addAttribute("startTime", startTime);
        model.addAttribute("endTime", endTime);

        return "jvm/monitor/memory";
    }

    /**
     * 类加载信息
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "monitor/classload")
    public String classload(Model model, HttpServletRequest request) {

        CommonDao<Server> serverDao = getDao();

        CommonDao<LoadedClassInfo> loadedClassInfoDao = getDao();

        // 统计的开始时间，结束时间
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");

        Long searchStart = 0L;
        Long searchEnd = 0L;

        if (StringUtil.isNullOrEmpty(startTime)) {
            Date date = DateUtil.subDate(new Date(), 2);
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

        Map<Long, Server> map = serverDao.getFromZSet(PREFIX + SERVER_LIST, 0, DateUtil.getUnixTime());

        Iterator<Long> iterator = map.keySet().iterator();

        Map<String, List<LoadedClassInfo>> classloadResult = new HashMap<>();

        while (iterator.hasNext()) {
            Long key = iterator.next();

            final Server server = map.get(key);

            Map<Long, LoadedClassInfo> classloadMap = loadedClassInfoDao.getFromZSet(PREFIX + LOADED_CLASS + server.getHost(), searchStart,
                    searchEnd);
            classloadResult.put(server.getHost(), Lists.newArrayList(classloadMap.values()));
        }

        model.addAttribute("classloadResult", classloadResult);

        model.addAttribute("startTime", startTime);
        model.addAttribute("endTime", endTime);

        return "jvm/monitor/classload";
    }

}