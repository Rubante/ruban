package com.ruban.monitor.jvm.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ruban.monitor.common.BaseController;
import com.ruban.monitor.jvm.JmxMonitor;
import com.ruban.monitor.jvm.domain.JVMKey;

@Controller
@RequestMapping(value = "/jvm")
public class JvmController extends BaseController implements JVMKey {

    private Map<String, JmxMonitor> monitors = new HashMap<>();

    /**
     * 获取JMX监控对象
     * 
     * @param host
     * @param port
     * @return
     */
    public JmxMonitor getMonitor(String host, int port) {

        if (monitors.get(host) == null) {
            JmxMonitor monitor = new JmxMonitor();
            monitor.connect(host, port);
            monitors.put(host, monitor);

            return monitor;
        }

        return monitors.get(host);
    }
}