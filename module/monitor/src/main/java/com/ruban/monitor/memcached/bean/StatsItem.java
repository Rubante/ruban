package com.ruban.monitor.memcached.bean;

/**
 * slab数据项
 * 
 * @author yjwang
 *
 */
public class StatsItem extends Item {

    private static final long serialVersionUID = 1L;

    /** 固定值：items **/
    private String command;

    /** slab的索引号 **/
    private int slabId;

    /** item的name **/
    private String name;

    /** 项描述 **/
    private String description;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public int getSlabId() {
        return slabId;
    }

    public void setSlabId(int slabId) {
        this.slabId = slabId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return getCommand() + ":" + getSlabId() + ":" + getName() + ":" + getValue();
    }
}
