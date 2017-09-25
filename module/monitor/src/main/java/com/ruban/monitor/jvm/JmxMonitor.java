package com.ruban.monitor.jvm;

import java.io.IOException;
import java.lang.management.ClassLoadingMXBean;
import java.lang.management.CompilationMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.util.List;

import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ruban.framework.core.utils.commons.StringUtil;
import com.ruban.monitor.jvm.bean.MemoryTool;

/**
 * 获取JMX监控对象
 * 
 * @author yjwang
 *
 */
public class JmxMonitor {

    private static final Logger logger = LoggerFactory.getLogger(JmxMonitor.class);

    private JMXConnector connector = null;

    private String host;

    private int port;

    private String newCollactor = MemoryTool.PS;

    private String oldCollactor = MemoryTool.PS;

    /**
     * 
     * 
     * @param host
     * @param port
     * @return
     */
    public JMXConnector connect(String host, int port) {
        try {
            this.host = host;
            this.port = port;

            JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://" + host + ":" + port + "/jmxrmi");
            connector = JMXConnectorFactory.connect(url);

            logger.info("connect to " + host + ":" + port);

        } catch (IOException ex) {
            logger.error("", ex);
        }

        return connector;
    }

    /**
     * 获取内存JMX对象
     * 
     * @return
     */
    public MemoryMXBean getMemoryMXBean() {

        try {
            return ManagementFactory.newPlatformMXBeanProxy(connector.getMBeanServerConnection(), "java.lang:type=Memory", MemoryMXBean.class);
        } catch (IOException ex) {
            logger.error("jmx create error!", ex);
            connect(host, port);
        }

        return null;
    }

    /**
     * 获取eden使用情况
     * 
     * @return
     */
    public MemoryUsage getEdenMemoryUsage() {

        try {
            // 垃圾回收器
            fixCollactor();

            MemoryPoolMXBean mxBean = ManagementFactory.newPlatformMXBeanProxy(connector.getMBeanServerConnection(),
                    MemoryTool.getEdenSpace(newCollactor), MemoryPoolMXBean.class);

            return mxBean.getUsage();
        } catch (Exception ex) {
            logger.error("jmx create error!", ex);
            connect(host, port);
        }

        return null;
    }

    /**
     * 获取code cache使用情况
     * 
     * @return
     */
    public MemoryUsage getCodeCacheMemoryUsage() {

        try {

            MemoryPoolMXBean mxBean = ManagementFactory.newPlatformMXBeanProxy(connector.getMBeanServerConnection(), MemoryTool.getCodeCache(),
                    MemoryPoolMXBean.class);

            return mxBean.getUsage();
        } catch (Exception ex) {
            logger.error("jmx create error!", ex);
            connect(host, port);
        }

        return null;
    }

    /**
     * 获取old gen使用情况
     * 
     * @return
     */
    public MemoryUsage getOldGenMemoryUsage() {

        try {
            // 垃圾回收器
            fixCollactor();

            MemoryPoolMXBean mxBean = ManagementFactory.newPlatformMXBeanProxy(connector.getMBeanServerConnection(),
                    MemoryTool.getOldGen(oldCollactor), MemoryPoolMXBean.class);

            return mxBean.getUsage();
        } catch (Exception ex) {
            logger.error("jmx create error!", ex);
            connect(host, port);
        }

        return null;
    }

    /**
     * 获取perm gen使用情况
     * 
     * @return
     */
    public MemoryUsage getPermGenMemoryUsage() {

        try {
            // 垃圾回收器
            fixCollactor();

            if (oldCollactor.equals(MemoryTool.G1)) {
                return null;
            } else {
                MemoryPoolMXBean mxBean = ManagementFactory.newPlatformMXBeanProxy(connector.getMBeanServerConnection(),
                        MemoryTool.getPermGen(oldCollactor), MemoryPoolMXBean.class);

                return mxBean.getUsage();
            }
        } catch (Exception ex) {
            try {
                MemoryPoolMXBean mxBean = ManagementFactory.newPlatformMXBeanProxy(connector.getMBeanServerConnection(), MemoryTool.getMetaspace(),
                        MemoryPoolMXBean.class);

                return mxBean.getUsage();
            } catch (Exception e) {
                logger.error("jmx create error!", ex);
                connect(host, port);
            }
        }

        return null;
    }

    /**
     * 获取Survivor Space使用情况
     * 
     * @return
     */
    public MemoryUsage getSurvivorSpaceMemoryUsage() {

        try {
            // 垃圾回收器
            fixCollactor();

            MemoryPoolMXBean mxBean = ManagementFactory.newPlatformMXBeanProxy(connector.getMBeanServerConnection(),
                    MemoryTool.getSurvivorSpace(newCollactor), MemoryPoolMXBean.class);

            return mxBean.getUsage();
        } catch (Exception ex) {
            logger.error("jmx create error!", ex);
            connect(host, port);
        }

        return null;
    }

    /**
     * 确定使用哪个垃圾回收器
     * 
     */
    private void fixCollactor() {

        if (StringUtil.isNullOrEmpty(newCollactor)) {
            List<String> args = getRuntimeMXBean().getInputArguments();

            // 根据启动参数，判断使用的垃圾回收器
            for (int i = 0; i < args.size(); i++) {
                if (MemoryTool.COLLACTOR_SERIAL.equals(args.get(i))) {
                    newCollactor = MemoryTool.PS;
                    oldCollactor = MemoryTool.PS;
                    break;
                }

                // garbage first
                if (MemoryTool.COLLACTOR_G1.equals(args.get(i))) {
                    newCollactor = MemoryTool.G1;
                    oldCollactor = MemoryTool.G1;
                    break;
                }

                if (MemoryTool.COLLACTOR_CMS.equals(args.get(i))) {
                    newCollactor = MemoryTool.CMS;
                    oldCollactor = MemoryTool.CMS;
                    break;
                }
            }

        }
    }

    /**
     * 获取RuntimeJMX对象
     * 
     * @return
     */
    public RuntimeMXBean getRuntimeMXBean() {

        try {
            return ManagementFactory.newPlatformMXBeanProxy(connector.getMBeanServerConnection(), "java.lang:type=Runtime", RuntimeMXBean.class);
        } catch (IOException ex) {
            logger.error("jmx create error!", ex);
            connect(host, port);
        }

        return null;
    }

    /**
     * 获取OperatingSystemJMX对象
     * 
     * @return
     */
    public OperatingSystemMXBean getOperatingSystemMXBean() {

        try {
            return ManagementFactory.newPlatformMXBeanProxy(connector.getMBeanServerConnection(), "java.lang:type=OperatingSystem",
                    OperatingSystemMXBean.class);
        } catch (IOException ex) {
            logger.error("jmx create error!", ex);
            connect(host, port);
        }

        return null;
    }

    /**
     * 获取ClassLoadingMXBean对象
     * 
     * @return
     */
    public ClassLoadingMXBean getClassLoadingMXBean() {

        try {
            return ManagementFactory.newPlatformMXBeanProxy(connector.getMBeanServerConnection(), "java.lang:type=ClassLoading",
                    ClassLoadingMXBean.class);
        } catch (IOException ex) {
            logger.error("jmx create error!", ex);
            connect(host, port);
        }

        return null;
    }

    /**
     * 获取CompilationMXBean对象
     * 
     * @return
     */
    public CompilationMXBean getCompilationMXBean() {

        try {
            return ManagementFactory.newPlatformMXBeanProxy(connector.getMBeanServerConnection(), "java.lang:type=Compilation",
                    CompilationMXBean.class);
        } catch (IOException ex) {
            logger.error("jmx create error!", ex);
            connect(host, port);
        }

        return null;
    }
}
