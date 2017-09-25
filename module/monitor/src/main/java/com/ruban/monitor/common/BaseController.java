package com.ruban.monitor.common;

import org.springframework.beans.factory.annotation.Value;

import com.ruban.framework.core.spring.SpringContext;
import com.ruban.monitor.dao.CommonDao;
import com.ruban.monitor.dao.impl.MapDBDaoImpl;
import com.ruban.monitor.dao.impl.RedisDaoImpl;

/**
 * 基础控制器
 * 
 * @author yjwang
 *
 */
public class BaseController {

    @Value("redis.prefix")
    private String prefix;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getKey(String key) {
        return prefix + key;
    }

    /**
     * 配置信息若配置则显示其他监控项
     * 
     * @return
     */
    public String display() {
        if (ConfigHolder.getProperties() != null) {
            return "";
        } else {
            return "none";
        }
    }

    /**
     * 获取存储dao
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> CommonDao<T> getDao() {
        CommonDao<T> commonDao;
        if (MonitorConstant.SELECT_REDIS.equals(ConfigHolder.get(MonitorConstant.DB_SELECT))) {
            commonDao = SpringContext.getBean(MonitorConstant.REDIS_DAO, RedisDaoImpl.class);
        } else {
            commonDao = SpringContext.getBean(MonitorConstant.MAPDB_DAO, MapDBDaoImpl.class);
        }

        if (!commonDao.isReady()) {
            commonDao.init(ConfigHolder.getProperties());
        }

        return commonDao;
    }
}
