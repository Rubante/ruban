package com.ruban.framework.core.spring;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/***
 * 扩展配置文件属性值处理，通过该类可以获取配置文件的值
 * 
 * @author ruban
 *
 */
public class RubanPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(RubanPropertyPlaceholderConfigurer.class);

    private Properties props;

    @Override
    protected Properties mergeProperties() throws IOException {
        Properties properties = super.mergeProperties();

        if (logger.isDebugEnabled()) {
            logger.debug("plain property values:" + properties.toString());
        }

        props = properties;

        return properties;
    }

    /**
     * 获取系统及应用的配置文件属性
     * 
     * @param key
     * @return
     */
    public String getValue(String key) {
        return props.getProperty(key);
    }
}
