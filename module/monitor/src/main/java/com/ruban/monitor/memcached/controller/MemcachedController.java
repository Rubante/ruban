package com.ruban.monitor.memcached.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ruban.framework.core.utils.commons.DateUtil;
import com.ruban.monitor.common.BaseController;
import com.ruban.monitor.dao.CommonDao;
import com.ruban.monitor.memcached.MemcachedConfiguration;
import com.ruban.monitor.memcached.MemcachedMonitor;
import com.ruban.monitor.memcached.MemcachedOperator;
import com.ruban.monitor.memcached.database.MemcachedKey;
import com.ruban.monitor.memcached.domain.Server;

import net.spy.memcached.ConnectionFactoryBuilder.Locator;
import net.spy.memcached.DefaultHashAlgorithm;

@Controller
@RequestMapping(value = "/memcached")
public class MemcachedController extends BaseController implements MemcachedKey {

    private MemcachedMonitor monitor;

    private MemcachedOperator operator;

    /***
     * 创建memcached监听器
     * 
     * @return
     */
    protected MemcachedMonitor getMonitor() {

        if (monitor == null) {
            CommonDao<String> commonDao = getDao();

            String locator = commonDao.get(PREFIX + LOCATOR);
            String hash = commonDao.get(PREFIX + HASH);

            CommonDao<Server> mapDao = getDao();

            // 获取监控服务器列表
            Map<Long, Server> map = mapDao.getFromZSet(PREFIX + SERVER_LIST, 0, DateUtil.getUnixTime());

            List<Server> servers = new ArrayList<>();
            Iterator<Long> iterator = map.keySet().iterator();
            while (iterator.hasNext()) {
                Long key = iterator.next();
                Server server = map.get(key);
                servers.add(server);
            }

            MemcachedConfiguration cfg = new MemcachedConfiguration();
            cfg.setServerList(servers);

            // 获取负载算法
            cfg.setLocator(Locator.valueOf(locator));

            // 获取hash算法
            cfg.setHashAlgorithm(DefaultHashAlgorithm.valueOf(hash));

            // 读取反序列化算法
            String className = commonDao.get(PREFIX + SERIA_CLASS_NAME);
            cfg.setTrancoder(className);

            MemcachedMonitor monitor = new MemcachedMonitor();
            monitor.connect(cfg);

            this.monitor = monitor;
        }

        return this.monitor;
    }

    /**
     * 获取操作类
     * 
     * @return
     */
    public MemcachedOperator getOperator() {

        monitor = getMonitor();

        if (operator == null) {
            operator = new MemcachedOperator();
            operator.setClient(monitor.getClient());
        }

        return operator;
    }
}