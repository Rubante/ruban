package com.ruban.framework.dao.datasource;

public class RunbanDataSourceHolder {
    public static final ThreadLocal<String> holder = new InheritableThreadLocal<String>();

    public static void putDataSource(String name) {
        holder.set(name);
    }

    public static String getDataSouce() {
        return holder.get();
    }
}