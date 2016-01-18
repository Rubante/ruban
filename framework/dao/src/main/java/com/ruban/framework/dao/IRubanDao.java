package com.ruban.framework.dao;

import com.ruban.framework.dao.domain.PersistentObject;

public interface IRubanDao {

    /**
     * 插入数据
     * 
     * @param domain
     * @return
     */
    public int insert(PersistentObject domain);
    
    /**
     * 依据ID进行删除
     * 
     * @param id
     * @return
     */
    public int deleteById(String id);
    
    /**
     * 更新数据
     * 
     * @param domain
     * @return
     */
    public int update(PersistentObject domain);
    
    
    public void find();
    
    public void findById();
    
    public void query();
}
