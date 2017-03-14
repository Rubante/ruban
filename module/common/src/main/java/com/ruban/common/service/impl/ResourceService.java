package com.ruban.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruban.common.dao.IResourceMapper;
import com.ruban.common.domain.Resource;
import com.ruban.common.service.IResourceService;
import com.ruban.common.vo.ResourceVo;
import com.ruban.framework.core.utils.commons.DateUtil;
import com.ruban.framework.core.utils.commons.RandomUtil;
import com.ruban.framework.dao.IRubanDao;
import com.ruban.framework.dao.helper.Condition;
import com.ruban.framework.dao.helper.ResultInfo;

@Service
public class ResourceService implements IResourceService {

    @Autowired
    private IResourceMapper resourceMapper;

    @Autowired
    private IRubanDao rubanDao;

    @Override
    public List<Resource> selectAll() {
        return resourceMapper.selectAll();
    }

    @Override
    public ResultInfo<Resource> selectByPage(Condition<Resource> condition) {

        ResultInfo<Resource> result = rubanDao.findByPage(condition, resourceMapper);
        return result;
    }

    @Override
    public List<Resource> selectByCondition(Condition<Resource> condition) {
        return resourceMapper.selectWithCondition(condition);
    }

    @Override
    public void insert(ResourceVo resourceVo) {

        Resource resource = new Resource();

        resource.setName(resourceVo.getName());
        resource.setType(resourceVo.getType());
        resource.setLink(resourceVo.getLink());
        resource.setIcon(resourceVo.getIcon());
        resource.setFlag(resourceVo.getFlag());
        resource.setMemo(resourceVo.getMemo());

        resource.setAddTime(DateUtil.getToday());
        resource.setModTime(DateUtil.getToday());
        resource.setAddUserId(0L);
        resource.setModUserId(0L);
        resource.setUpdateLock(RandomUtil.getUpdateLock());

        resourceMapper.insert(resource);
    }

    @Override
    public int update(ResourceVo resourceVo) {

        int result = 0;

        Resource resource = findById(resourceVo.getId());

        if (resource != null) {
            resource.setName(resourceVo.getName());
            resource.setType(resourceVo.getType());
            resource.setLink(resourceVo.getLink());
            resource.setIcon(resourceVo.getIcon());
            resource.setFlag(resourceVo.getFlag());
            resource.setMemo(resourceVo.getMemo());

            resource.setModTime(DateUtil.getToday());
            resource.setModUserId(0L);
            resource.setUpdateLock(RandomUtil.getUpdateLock());

            resource.setHoldLock(resourceVo.getHoldLock());

            result = resourceMapper.update(resource);
        }

        return result;
    }

    /**
     * 根据ID删除资源
     * 
     * @param id
     * @return
     */
    public int deleteById(Long id) {
        return resourceMapper.deleteById(id);
    }

    /**
     * 批量删除资源
     * 
     * @param ids
     * @return
     */
    public int deleteByIds(String[] ids) {
        return resourceMapper.deleteByIds(ids);
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
            count += resourceMapper.updateOrderCode(id, i + 1);
        }

        return count;
    }

    @Override
    public Resource findById(Long id) {
        return resourceMapper.findById(id);
    }
}
