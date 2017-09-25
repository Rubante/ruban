package com.ruban.monitor.memcached.bean;

/**
 * 块信息
 * 
 * @author yjwang
 *
 */
public class StatsSlab extends Item {

    private static final long serialVersionUID = 1L;

    /** 固定值：items **/
    private String command;

    /** 块索引 **/
    private int id;

    /** 块属性名 **/
    private String name;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
