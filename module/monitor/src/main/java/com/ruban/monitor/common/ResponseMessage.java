package com.ruban.monitor.common;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 通用返回message
 * 
 * @author yjwang
 *
 */
public class ResponseMessage {

    @JSONField(name = "flag")
    private int flag = 0;

    @JSONField(name = "msg")
    private String msg = "";

    @JSONField(name = "value")
    private Object value;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

}
