package com.ruban.monitor.memcached.domain;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * memcached地址
 * 
 * @author yjwang
 *
 */
public class Server implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键 **/
    @JSONField(name = "id")
    private Long id;

    /** 主机IP **/
    @JSONField(name = "host")
    private String host;

    /** 端口 **/
    @JSONField(name = "port")
    private int port;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

}
