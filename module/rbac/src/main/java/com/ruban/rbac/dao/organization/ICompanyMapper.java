package com.ruban.rbac.dao.organization;

import org.apache.ibatis.annotations.Param;

import com.ruban.framework.dao.IRubanMapper;
import com.ruban.framework.dao.support.RubanDaoRepository;
import com.ruban.rbac.domain.organization.Company;

@RubanDaoRepository
public interface ICompanyMapper extends IRubanMapper {

    /**
     * 根据父节点查询节点数量
     * 
     * @param parentId
     * @return
     */
    int countByParentId(Long parentId);

    /**
     * 更新节点数量
     * 
     * @param id
     */
    void updateChildrenNum(Long id);

    /**
     * 根据父节点查询最大编码的数据记录
     * 
     * @param parentId
     * @return
     */
    Company getMaxCodeChild(Long parentId);

    /**
     * 更新子节点的pathCode
     * 
     * @param newPathCode
     * @param pathCode
     * @return
     */
    int updatePathCode(@Param("newPathCode") String newPathCode, @Param("pathCode") String pathCode);
}