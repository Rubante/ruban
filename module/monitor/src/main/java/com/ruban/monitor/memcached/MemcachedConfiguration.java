package com.ruban.monitor.memcached;

import java.util.ArrayList;
import java.util.List;

import com.ruban.framework.core.utils.biz.IpUtil;
import com.ruban.framework.core.utils.commons.StringUtil;
import com.ruban.monitor.memcached.domain.Server;

import net.spy.memcached.ConnectionFactoryBuilder.Locator;
import net.spy.memcached.HashAlgorithm;

/**
 * memcached配置信息<br/>
 * 关于memcached的认证：<br/>
 * sasl 默认安装，支持的：getpwent kerberos5 pam rimap
 * shadow ldap
 * 
 * 
 * 
 * @author yjwang
 *
 */
public class MemcachedConfiguration {

    /**
     * 主机IP及端口
     */
    private List<String> servers = new ArrayList<>();

    private List<Server> serverList = new ArrayList<>();

    /** 负载算法 **/
    private Locator locator;

    /** hash算法 **/
    private HashAlgorithm hashAlgorithm;

    /** 登录用户名 **/
    private String username;

    /** 登录密码 **/
    private String password;

    /** 序列化类 **/
    private String trancoder;

    public List<String> getServers() {
        return servers;
    }

    public void setServers(List<String> servers) {
        this.servers = servers;
    }

    /**
     * 校验服务地址是否正确
     * 
     * @return
     */
    public boolean validate() {

        for (int i = 0; i < servers.size(); i++) {
            String[] serverArr = servers.get(i).split(":");
            if (serverArr.length == 2) {
                String host = serverArr[0];

                // ip校验
                if (!IpUtil.checkIp(host)) {
                    continue;
                }

                // 数据长度校验
                if (serverArr.length != 2) {
                    continue;
                }

                // 端口号
                String port = serverArr[1];

                if (StringUtil.isDigit(port)) {
                    Server server = new Server();
                    server.setHost(host);
                    server.setPort(Integer.parseInt(port));

                    serverList.add(server);
                }
            }
        }

        return true;
    }

    public List<Server> getServerList() {
        return serverList;
    }

    public void setServerList(List<Server> serverList) {
        this.serverList = serverList;
    }

    public void addServer(String server) {
        servers.add(server);
    }

    public Locator getLocator() {
        return locator;
    }

    public void setLocator(Locator locator) {
        this.locator = locator;
    }

    public HashAlgorithm getHashAlgorithm() {
        return hashAlgorithm;
    }

    public void setHashAlgorithm(HashAlgorithm hashAlgorithm) {
        this.hashAlgorithm = hashAlgorithm;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTrancoder() {
        return trancoder;
    }

    public void setTrancoder(String trancoder) {
        this.trancoder = trancoder;
    }

}