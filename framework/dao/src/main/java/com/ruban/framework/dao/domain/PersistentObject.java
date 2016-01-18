package com.ruban.framework.dao.domain;

import java.io.Serializable;

/**
 * 业务对象接口
 * 
 * 确保每个业务对象都有UPDATE_TIME字段，用于乐观锁的控制
 * 
 * @author ruban
 *
 */
public interface PersistentObject extends Serializable {

    public Integer getUpdateTime();

    public void setUpdate(Integer updateTime);
}
