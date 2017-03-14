package com.ruban.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruban.common.dao.IResourceFieldMapper;
import com.ruban.common.domain.ResourceField;
import com.ruban.common.service.IResourceFieldService;
import com.ruban.common.vo.ResourceFieldVo;
import com.ruban.framework.core.utils.commons.DateUtil;
import com.ruban.framework.core.utils.commons.RandomUtil;
import com.ruban.framework.dao.IRubanDao;
import com.ruban.framework.dao.helper.Condition;
import com.ruban.framework.dao.helper.ResultInfo;

@Service
public class ResourceFieldService implements IResourceFieldService {

    @Autowired
    private IResourceFieldMapper resourceFieldMapper;

    @Autowired
    private IRubanDao rubanDao;

    @Override
    public List<ResourceField> selectAll() {
        return resourceFieldMapper.selectAll();
    }

    @Override
    public ResultInfo<ResourceField> selectByPage(Condition<ResourceField> condition) {

        ResultInfo<ResourceField> result = rubanDao.findByPage(condition, resourceFieldMapper);
        return result;
    }

    @Override
    public List<ResourceField> selectByCondition(Condition<ResourceField> condition) {
        return resourceFieldMapper.selectWithCondition(condition);
    }

    @Override
    public void insert(ResourceFieldVo resourceFieldVo) {

        ResourceField resourceField = new ResourceField();

        resourceField.setCode(resourceFieldVo.getCode());
        resourceField.setName(resourceFieldVo.getName());
        resourceField.setFlag(resourceFieldVo.getFlag());
        resourceField.setMemo(resourceFieldVo.getMemo());
        resourceField.setResourceId(resourceFieldVo.getResourceId());
        resourceField.setAddTime(DateUtil.getToday());
        resourceField.setAddUserId(1L);
        resourceField.setModTime(DateUtil.getToday());
        resourceField.setModUserId(1L);

        resourceField.setModTime(DateUtil.getToday());

        resourceField.setUpdateLock(RandomUtil.getUpdateLock());

        resourceFieldMapper.insert(resourceField);
    }

    @Override
    public int update(ResourceFieldVo resourceFieldVo) {

        int result = 0;

        ResourceField resourceField = findById(resourceFieldVo.getId());

        if (resourceField != null) {
            resourceField.setCode(resourceFieldVo.getCode());
            resourceField.setName(resourceFieldVo.getName());
            resourceField.setFlag(resourceFieldVo.getFlag());
            resourceField.setMemo(resourceFieldVo.getMemo());
            resourceField.setModTime(DateUtil.getToday());
            resourceField.setModUserId(1L);

            resourceField.setHoldLock(resourceFieldVo.getHoldLock());

            result = resourceFieldMapper.update(resourceField);
        }

        return result;
    }

    /**
     * 根据ID删除资源属性
     * 
     * @param id
     * @return
     */
    public int deleteById(Long id) {
        return resourceFieldMapper.deleteById(id);
    }

    /**
     * 批量删除资源属性
     * 
     * @param ids
     * @return
     */
    public int deleteByIds(String[] ids) {
        return resourceFieldMapper.deleteByIds(ids);
    }

    /**
     * 排序资源
     * 
     * @param id
     * @return
     */
    public int sortByIds(String[] ids) {
        int count = 0;
        for (int i = 0; ids != null && i < ids.length; i++) {
            Long id = Long.parseLong(ids[i]);
            count += resourceFieldMapper.updateOrderCode(id, i + 1);
        }

        return count;
    }

    @Override
    public ResourceField findById(Long id) {
        return resourceFieldMapper.findById(id);
    }
}
