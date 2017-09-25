package com.ruban.monitor.memcached.bean;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class ServerStats {

    private String ip;

    private InetSocketAddress socketAddress;

    private List<Stats> stats = new ArrayList<>();

    private List<StatsItem> items = new ArrayList<>();

    private List<StatsSetting> settings = new ArrayList<>();

    private List<StatsSlab> slabs = new ArrayList<>();

    private List<StatsSize> sizes = new ArrayList<>();

    private List<Item> values = new ArrayList<>();

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public InetSocketAddress getSocketAddress() {
        return socketAddress;
    }

    public void setSocketAddress(InetSocketAddress socketAddress) {
        this.socketAddress = socketAddress;
    }

    public List<StatsItem> getItems() {
        return items;
    }

    public void setItems(List<StatsItem> items) {
        this.items = items;
    }

    public List<Stats> getStats() {
        return stats;
    }

    public void setStats(List<Stats> stats) {
        this.stats = stats;
    }

    public List<StatsSetting> getSettings() {
        return settings;
    }

    public void setSettings(List<StatsSetting> settings) {
        this.settings = settings;
    }

    public List<StatsSlab> getSlabs() {
        return slabs;
    }

    public void setSlabs(List<StatsSlab> slabs) {
        this.slabs = slabs;
    }

    public List<StatsSize> getSizes() {
        return sizes;
    }

    public void setSizes(List<StatsSize> sizes) {
        this.sizes = sizes;
    }

    public List<Item> getValues() {
        return values;
    }

    public void setValues(List<Item> values) {
        this.values = values;
    }

}
