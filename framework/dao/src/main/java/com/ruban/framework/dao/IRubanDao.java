package com.ruban.framework.dao;

import com.ruban.framework.dao.helper.Condition;
import com.ruban.framework.dao.helper.ResultInfo;

public interface IRubanDao {

    /**
     * 分页查询
     * 
     * @param pageDTO
     * @return
     */
    public <T> ResultInfo<T> findByPage(Condition<T> condition, IRubanMapper mapper);
}
