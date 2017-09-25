package com.ruban.monitor.memcached.executor;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ruban.monitor.memcached.bean.ServerStats;

import net.spy.memcached.MemcachedClient;

public abstract class CommandExcutor {

    private static final Logger logger = LoggerFactory.getLogger(CommandExcutor.class);

    private MemcachedClient client;

    private String cmd = "";

    public CommandExcutor(MemcachedClient client) {
        this.client = client;
    }

    public MemcachedClient getClient() {
        return client;
    }

    public void setClient(MemcachedClient client) {
        this.client = client;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    /**
     * 解析执行stats items命令
     * 
     * @param cmd
     * @throws Exception
     */
    public List<ServerStats> execute() {

        List<ServerStats> results = new ArrayList<>();

        Map<SocketAddress, Map<String, String>> stats = null;

        try {
            if ("stats".equals(getCmd())) {
                stats = getClient().getStats();
            } else {
                stats = getClient().getStats(getCmd());
            }
        } catch (Exception e) {
            logger.error("getStats error", e);

            return null;
        }

        Iterator<SocketAddress> iterator = stats.keySet().iterator();

        while (iterator.hasNext()) {
            ServerStats serverStats = new ServerStats();

            SocketAddress socketAddress = iterator.next();

            serverStats.setIp(((InetSocketAddress) socketAddress).getAddress().getHostAddress());
            serverStats.setSocketAddress((InetSocketAddress) socketAddress);

            results.add(serverStats);

            Map<String, String> values = stats.get(socketAddress);

            doExecute(serverStats, values);
        }

        return results;
    }

    /**
     * 解析执行具体指令命令
     * 
     * @param cmd
     */
    public abstract void doExecute(ServerStats serverStatus, Map<String, String> values);

}
