package com.ruban.monitor.jvm.domain;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * JVM监控地址
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

    /** 监控频率 **/
    @JSONField(name = "rate")
    private int rate;
    
    /**是否开启监控**/
    @JSONField(name="state")
    private int state;

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

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

}
