package com.ruban.framework.web.page;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class JsonResult {

    /** 结果标识 **/
    private int flag;

    /** 消息 **/
    private String msg;

    /** 结果 **/
    private Map<String, Object> result = new HashMap<>();

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

    public Map<String, Object> getResult() {
        return result;
    }

    public void setResult(Map<String, Object> result) {
        this.result = result;
    }

    public void add(String key, Object value) {
        this.result.put(key, value);
    }
}
