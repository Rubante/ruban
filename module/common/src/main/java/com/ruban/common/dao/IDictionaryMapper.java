package com.ruban.common.dao;

import java.util.List;

import com.ruban.common.domain.Dictionary;
import com.ruban.framework.dao.IRubanMapper;
import com.ruban.framework.dao.support.RubanDaoRepository;

@RubanDaoRepository
public interface IDictionaryMapper extends IRubanMapper {

    /**
     * 按组查询
     * 
     * @param group
     * @return
     */
    public List<Dictionary> selectByGroup(String group);
}