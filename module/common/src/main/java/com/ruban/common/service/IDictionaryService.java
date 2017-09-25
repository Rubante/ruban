package com.ruban.common.service;

import java.util.List;

import com.ruban.common.domain.Dictionary;
import com.ruban.framework.dao.helper.Condition;
import com.ruban.framework.dao.helper.ResultInfo;

/**
 * 数据字典
 * 
 * @author ruban
 *
 */
public interface IDictionaryService {

    /**
     * 查询所有的数据字典列表
     * 
     * @return
     */
    public List<Dictionary> selectAll();

    /**
     * 按组查询数据字典列表
     * 
     * @param group
     * @return
     */
    public List<Dictionary> selectByGroup(String group);

    /**
     * 分页查询
     * 
     * @param dto
     * @return
     */
    public ResultInfo<Dictionary> selectByPage(Condition<Dictionary> condition);

    /**
     * 新建数据字典
     * 
     * @param dictionary
     */
    public int insert(Dictionary dictionary);
}
