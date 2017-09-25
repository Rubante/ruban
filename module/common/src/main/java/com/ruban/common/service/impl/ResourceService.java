package com.ruban.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruban.common.dao.IResourceMapper;
import com.ruban.common.dict.CommonState;
import com.ruban.common.dict.YesNo;
import com.ruban.common.domain.Resource;
import com.ruban.common.service.IResourceService;
import com.ruban.common.vo.ResourceVo;
import com.ruban.framework.core.utils.commons.DateUtil;
import com.ruban.framework.core.utils.commons.RandomUtil;
import com.ruban.framework.core.utils.format.FormatUtil;
import com.ruban.framework.dao.IRubanDao;
import com.ruban.framework.dao.helper.Condition;
import com.ruban.framework.dao.helper.ResultInfo;

@Service
public class ResourceService implements IResourceService {

    private static final Logger logger = LoggerFactory.getLogger(ResourceService.class);

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

        // 构建排序字段
        buildOrderCode(resource, resourceVo);

        // 属性拷贝
        BeanUtils.copyProperties(resourceVo, resource);

        // 添加修改时间
        resource.setAddTime(DateUtil.getToday());
        resource.setModTime(DateUtil.getToday());

        // 乐观锁
        resource.setUpdateLock(RandomUtil.getUpdateLock());

        // 插入数据
        resourceMapper.insert(resource);

        // 设置层级路径
        buildLevelAndPath(resource);
        resource.setHoldLock(resource.getUpdateLock());
        resourceMapper.update(resource);

        // 更新子节点数量
        updateChildrenNum(resourceVo);

        // 修改上级节点的叶子数量
        if (resourceVo.getParentId() != null) {
            Resource newParent = resourceMapper.findById(resource.getParentId());
            newParent.setHoldLock(newParent.getUpdateLock());
            resourceMapper.increaseChildrenNum(newParent);
        }
    }

    @Override
    public int update(ResourceVo resourceVo) {

        int result = 0;

        Resource resource = findById(resourceVo.getId());

        if (resource != null) {

            // 防止自身设置自己为父节点
            if (resource.getId().equals(resourceVo.getParentId())) {
                resourceVo.setParentId(resource.getParentId());
            }

            // 上级节点变动时，修改上级节点的叶子数量
            if (resource.getParentId() != resourceVo.getParentId()) {
                Resource parent = resourceMapper.findById(resource.getParentId());
                if (parent != null) {
                    parent.setHoldLock(parent.getUpdateLock());
                    resourceMapper.decreaseChildrenNum(parent);
                }

                Resource newParent = resourceMapper.findById(resourceVo.getParentId());
                newParent.setHoldLock(newParent.getUpdateLock());
                resourceMapper.increaseChildrenNum(newParent);
            }

            // 构建排序字段
            buildOrderCode(resource, resourceVo);

            // 属性拷贝
            BeanUtils.copyProperties(resourceVo, resource);

            // 修改时间
            resource.setModTime(DateUtil.getToday());

            // 生成乐观锁
            resource.setUpdateLock(RandomUtil.getUpdateLock());

            // 设置层级路径
            buildLevelAndPath(resource);
            result = resourceMapper.update(resource);

            // 更新子节点数量
            resourceVo.setHoldLock(resource.getUpdateLock());
            updateChildrenNum(resourceVo);
        }

        return result;
    }

    @Override
    public int updateChildrenNum(ResourceVo resourceVo) {
        int num = resourceMapper.findChildrenNum(resourceVo.getId());
        resourceVo.setChildrenNum(num);

        return resourceMapper.updateChildrenNum(resourceVo);
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

    @Override
    public int enable(Long id) {

        int count = 0;
        Resource resource = findById(id);
        // 更新自身
        resource.setState(CommonState.Enable.getValue());
        resource.setHoldLock(resource.getUpdateLock());
        // 需要判断上级是否有节点处于禁用状态
        resource.setDisplayFlag(YesNo.Yes.getValue());

        // 循环父级节点，若存在失效的，则不展示
        List<Resource> parents = getParents(resource.getParentId());

        if (parents != null && !parents.isEmpty()) {
            for (Resource parent : parents) {

                // 找到一层父级未生效
                if (parent.getState() != CommonState.Enable.getValue()) {
                    resource.setDisplayFlag(YesNo.No.getValue());
                    break;
                }
            }
        }

        count += resourceMapper.update(resource);

        // 本级不显示，则下级全部不显示
        if (resource.getDisplayFlag() == YesNo.No.getValue()) {
            Resource condition = new Resource();
            condition.setOrderCode(resource.getOrderCode());
            condition.setDisplayFlag(YesNo.No.getValue());

            count += resourceMapper.updateDisplayFlag(condition);
        } else {
            // 将下级逐层启用
            List<Resource> children = resourceMapper.findChildren(id);

            if (children != null && !children.isEmpty()) {
                String ignoreCode = "";
                for (Resource child : children) {

                    // 被忽略的层级，子孙级全部忽略
                    if (child.getOrderCode().startsWith(ignoreCode)) {
                        continue;
                    }
                    Resource condition = new Resource();
                    // 当前级别
                    if (child.getState() == CommonState.Enable.getValue()) {
                        condition.setId(child.getId());
                        condition.setDisplayFlag(YesNo.Yes.getValue());

                        // 将层次编码置空
                        ignoreCode = "";
                    } else {
                        condition.setOrderCode(child.getOrderCode());
                        condition.setDisplayFlag(YesNo.No.getValue());

                        ignoreCode = child.getOrderCode();
                    }

                    count += resourceMapper.updateDisplayFlag(condition);
                }
            }
        }

        return count;
    }

    @Override
    public int unable(Long id) {
        Resource resource = findById(id);

        // 更新自身
        resource.setState(CommonState.Unable.getValue());
        resource.setDisplayFlag(YesNo.No.getValue());
        resource.setHoldLock(resource.getUpdateLock());

        resourceMapper.update(resource);

        // 下级资源显示表示为不显示
        Resource condition = new Resource();
        condition.setOrderCode(resource.getOrderCode());
        condition.setDisplayFlag(resource.getDisplayFlag());

        return resourceMapper.updateDisplayFlag(condition);
    }

    /**
     * 排序资源
     * 
     * @param id
     * @return
     */
    public int sortByIds(String[] ids, String[] levels, String[] paths, String[] parentIds) {
        int count = 0;
        Map<String, Integer> orderCode = new HashMap<>();
        int prevLevel = 0;

        for (int i = 0; ids != null && i < ids.length; i++) {
            Long id = Long.parseLong(ids[i]);
            int level = Integer.parseInt(levels[i]);
            String preKey = String.valueOf(prevLevel);
            String key = String.valueOf(level);

            // 初始值后需要累加
            if (level <= prevLevel) {
                Integer value = orderCode.get(key);
                if (value == null) {
                    value = 1;
                } else {
                    value++;
                }
                orderCode.put(key, value);

                // 下级返回上级时，将下级清零
                if (level < prevLevel) {
                    orderCode.put(preKey, 1);
                }
            } else {
                // 上级进入下级后，初始化为1
                orderCode.put(key, 1);
            }

            prevLevel = level;

            int pathLevel = 0;
            StringBuffer number = new StringBuffer();
            while (pathLevel <= level) {
                number.append(FormatUtil.fmtNumber(orderCode.get(String.valueOf(pathLevel)), 4));
                pathLevel++;
            }

            if (logger.isDebugEnabled()) {
                logger.debug("id:" + id + ";level:" + level + "orderCode:" + number);
            }

            count += resourceMapper.updateOrder(id, number.toString(), paths[i], parentIds[i]);
        }

        return count;
    }

    @Override
    public Resource findById(Long id) {
        return resourceMapper.findById(id);
    }

    @Override
    public List<Resource> getParents(Long parentId) {
        List<Resource> parents = new ArrayList<>();

        Resource parent = resourceMapper.findById(parentId);
        if (parent != null) {
            parents.add(parent);
            while (parent.getParentId() != 0) {
                parent = resourceMapper.findById(parent.getParentId());
                parents.add(parent);
            }
        }

        return parents;
    }

    @Override
    public List<Resource> getChildren(Long resourceId) {
        List<Resource> children = new ArrayList<>();

        Resource resource = resourceMapper.findById(resourceId);
        if (resource != null) {
            while (resource.getParentId() != 0) {
                resource = resourceMapper.findById(resource.getParentId());
                children.add(resource);
            }
        }

        return children;
    }

    @Override
    public void buildLevelAndPath(Resource resource) {

        StringBuffer path = new StringBuffer();

        // 循环拼装资源(资源ID)的双亲全路径
        if (resource != null) {
            List<Resource> parents = getParents(resource.getParentId());

            if (!parents.isEmpty()) {
                for (int i = parents.size() - 1; i >= 0; i--) {
                    parents.get(i);
                    path.append("_" + parents.get(i).getId());
                }

                // 依据双亲个数设定层级
                resource.setLevel(parents.size());
            }

            path.append("_" + resource.getId());

            // 设置全路径
            resource.setPath(path.toString());
        }
    }

    @Override
    public void buildOrderCode(Resource resource, ResourceVo vo) {

        // 排序
        String orderCode = resource.getOrderCode();

        if (vo != null) {

            // 新设置双亲节点
            Long newParentId = vo.getParentId();

            boolean newOrderCode = false;
            // 设置新的双亲节点
            if (newParentId != null) {
                // 双亲节点不相等
                if (resource == null || !newParentId.equals(resource.getParentId())) {
                    newOrderCode = true;
                }
            } else {
                // 新增，修改前后都没有
                if (resource == null || resource.getParentId() == null || resource.getParentId() == 0) {
                    newOrderCode = true;
                }
            }

            if (newOrderCode) {
                Resource lastSlbing = resourceMapper.findLastSlibing(vo);
                // 获取最后的兄弟节点排序字段
                if (lastSlbing != null) {
                    orderCode = lastSlbing.getOrderCode();
                    String first = orderCode.substring(0, orderCode.length() - 4);

                    // 截取后四位进行累加1
                    String last = orderCode.substring(orderCode.length() - 4, orderCode.length());
                    int num = Integer.parseInt(last);
                    orderCode = first + FormatUtil.fmtNumber(++num, 4);
                } else {
                    Resource parent = findById(vo.getParentId());
                    orderCode = parent.getOrderCode() + FormatUtil.fmtNumber(1, 4);
                }
                if (vo.getId() != null) {
                    // 将所有下级节点的排序编码全部修正
                    List<Resource> children = resourceMapper.findChildren(vo.getId());
                    if (children != null) {
                        for (Resource child : children) {
                            String childOrderCode = child.getOrderCode();
                            childOrderCode = orderCode + childOrderCode.substring(orderCode.length());
                            resourceMapper.updateOrderCodeByLevel(child.getId(), childOrderCode);
                        }
                    }
                }
            }
        }

        // 更新排序字段
        vo.setOrderCode(orderCode);
    }
}
