package com.ruban.monitor.memcached;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ruban.framework.core.utils.commons.StringUtil;
import com.ruban.monitor.memcached.bean.ServerStats;
import com.ruban.monitor.memcached.domain.Server;
import com.ruban.monitor.memcached.executor.ItemCommandExcutor;
import com.ruban.monitor.memcached.executor.SettingCommandExcutor;
import com.ruban.monitor.memcached.executor.SizeCommandExcutor;
import com.ruban.monitor.memcached.executor.SlabCommandExcutor;
import com.ruban.monitor.memcached.executor.StatsCommandExcutor;

import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.auth.AuthDescriptor;
import net.spy.memcached.transcoders.Transcoder;

/**
 * memcached监控执行者
 * 
 * @author yjwang
 *
 */
public class MemcachedMonitor {

    private static final Logger logger = LoggerFactory.getLogger(MemcachedMonitor.class);

    private MemcachedClient client;

    public void connect(MemcachedConfiguration cfg) {

        // 校验配置信息
        boolean validate = cfg.validate();
        if (!validate) {
            logger.error("memcached configuration error!");
            return;
        }

        List<Server> serverList = cfg.getServerList();

        List<InetSocketAddress> inetAddrList = new ArrayList<>();
        for (int i = 0; i < serverList.size(); i++) {

            Server server = serverList.get(i);
            InetSocketAddress inetAddr = new InetSocketAddress(server.getHost(), server.getPort());
            inetAddrList.add(inetAddr);
        }

        try {
            ConnectionFactoryBuilder builder = new ConnectionFactoryBuilder();

            String username = cfg.getUsername();
            String password = cfg.getPassword();

            // 认证信息
            if (StringUtil.isNotNullOrEmpty(username) && StringUtil.isNotNullOrEmpty(password)) {
                builder.setAuthDescriptor(AuthDescriptor.typical(username, password));
            }

            // 默认为数组（hash值对数组长度取余，因此采用的客户端及其算法决定了监控数据项是否能够有效）
            // 采用一致性哈希算法：要指明各地址的权重(将IP地址重复传入代替权重)

            builder.setLocatorType(cfg.getLocator());
            builder.setHashAlg(cfg.getHashAlgorithm());

            if (cfg.getTrancoder() != null) {
                try {
                    @SuppressWarnings("unchecked")
                    Transcoder<Object> trancoder = (Transcoder<Object>) Class.forName(cfg.getTrancoder()).newInstance();
                    builder.setTranscoder(trancoder);
                } catch (Exception ex) {
                    logger.error("trancoder error!" + ex.getMessage());
                }
            }

            client = new MemcachedClient(builder.build(), inetAddrList);

        } catch (IOException ex) {
            logger.error("", ex);
        }
    }

    /**
     * 获取memcached的状态信息
     * 
     * @return
     */
    public List<ServerStats> getStats() {

        StatsCommandExcutor statsCmd = new StatsCommandExcutor(client);
        List<ServerStats> result = statsCmd.execute();

        return result;
    }

    /**
     * 获取服务器配置
     * 
     * @return
     */
    public List<ServerStats> getSetting() {

        SettingCommandExcutor settingCmd = new SettingCommandExcutor(client);
        List<ServerStats> result = settingCmd.execute();

        return result;
    }

    /**
     * 获取数据统计信息
     * 
     * @return
     */
    public List<ServerStats> getSize() {

        SizeCommandExcutor settingCmd = new SizeCommandExcutor(client);
        List<ServerStats> result = settingCmd.execute();

        return result;
    }

    /**
     * 获取数据项明细信息
     * 
     * @return
     */
    public List<ServerStats> getItems() {

        ItemCommandExcutor settingCmd = new ItemCommandExcutor(client);
        List<ServerStats> result = settingCmd.execute();

        return result;
    }

    /**
     * 获取数据项列表信息
     * 
     * @return
     */
    public List<ServerStats> getSlab() {

        SlabCommandExcutor settingCmd = new SlabCommandExcutor(client);
        List<ServerStats> result = settingCmd.execute();

        return result;
    }

    /**
     * 清空缓存
     * 
     * @param delay
     */
    public void flush(int delay) {
        client.flush(delay);
    }

    public MemcachedClient getClient() {
        return client;
    }

    public void setClient(MemcachedClient client) {
        this.client = client;
    }

}
