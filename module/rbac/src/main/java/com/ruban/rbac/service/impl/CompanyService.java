package com.ruban.rbac.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruban.common.dao.IDictionaryMapper;
import com.ruban.common.dict.DictionaryGroupKey;
import com.ruban.common.domain.Dictionary;
import com.ruban.framework.core.utils.commons.DateUtil;
import com.ruban.framework.core.utils.commons.RandomUtil;
import com.ruban.framework.dao.IRubanDao;
import com.ruban.framework.dao.helper.Condition;
import com.ruban.framework.dao.helper.ResultInfo;
import com.ruban.rbac.backend.company.form.CompanyForm;
import com.ruban.rbac.backend.company.form.SearchForm;
import com.ruban.rbac.dao.organization.ICompanyMapper;
import com.ruban.rbac.domain.organization.Company;
import com.ruban.rbac.service.ICompanyService;

@Service
public class CompanyService implements ICompanyService {

    @Autowired
    private ICompanyMapper companyMapper;

    @Autowired
    private IRubanDao rubanDao;

    @Autowired
    private IDictionaryMapper dictionaryMapper;

    @Override
    public List<Company> selectAll() {
        return companyMapper.selectAll();
    }

    @Override
    public ResultInfo<Company> selectByPage(Condition<Company> condition) {

        ResultInfo<Company> result = rubanDao.findByPage(condition, companyMapper);
        return result;
    }

    @Override
    public List<Company> selectByCondition(Condition<Company> condition) {
        return companyMapper.selectWithCondition(condition);
    }

    @Override
    public Company insert(CompanyForm companyForm) {

        Company company = new Company();

        company.setName(companyForm.getName());
        company.setSimpleName(companyForm.getSimpleName());
        company.setCode(companyForm.getCode());
        company.setAddress(companyForm.getAddress());
        company.setPostCode(companyForm.getPostCode());
        company.setEmail(companyForm.getEmail());
        company.setTel(companyForm.getTel());
        company.setMemo(companyForm.getMemo());
        company.setType(companyForm.getType());
        company.setTitle(companyForm.getTitle());
        company.setAddUserId(companyForm.getAddUserId());
        company.setModUserId(companyForm.getModUserId());
        company.setAddTime(DateUtil.getNowTime());
        company.setModTime(DateUtil.getNowTime());
        company.setUpdateLock(RandomUtil.getUpdateLock());

        // 父节点
        if (companyForm.getCompanyId() != null) {
            Long parentId = new Long(companyForm.getCompanyId());
            company.setCompanyId(parentId);

            int count = companyMapper.countByParentId(parentId);
            company.setOrderCode(count + 1);
            Company parent = companyMapper.findById(parentId);

            company.setPathCode(parent.getCode() + companyForm.getCode());
        } else {
            company.setCompanyId(0L);
            company.setCode(companyForm.getCode());
            company.setPathCode(companyForm.getCode());
        }

        companyMapper.insert(company);

        // 更新父节点的叶子节点数量
        updateHasChildren(companyForm);

        return company;
    }

    @Override
    public int update(CompanyForm companyForm) {

        Company company = findById(companyForm.getId());
        company.setCode(companyForm.getCode());
        company.setPathCode(companyForm.getCode());
        company.setName(companyForm.getName());
        company.setAddress(companyForm.getAddress());
        company.setPostCode(companyForm.getPostCode());
        company.setEmail(companyForm.getEmail());
        company.setTel(companyForm.getTel());
        company.setMemo(companyForm.getMemo());
        company.setType(companyForm.getType());
        company.setTitle(companyForm.getTitle());
        company.setModUserId(companyForm.getModUserId());
        company.setModTime(DateUtil.getNowTime());
        company.setUpdateLock(RandomUtil.getUpdateLock());
        company.setHoldLock(companyForm.getHoldLock());

        // 父节点
        if (companyForm.getCompanyId() != null) {
            company.setCompanyId(companyForm.getCompanyId());
        } else {
            company.setCompanyId(0L);
        }

        int result = companyMapper.update(company);

        // 更新父节点的叶子节点数量
        updateHasChildren(companyForm);

        return result;
    }

    /**
     * 更新叶子节点的数量
     * 
     * @param companyForm
     */
    private void updateHasChildren(CompanyForm companyForm) {

        // 如果父节点不为空，则更新其子节点数量
        if (companyForm.getCompanyId() != null && companyForm.getCompanyId() != 0) {
            Long parentId = companyForm.getCompanyId();
            Company parent = companyMapper.findById(parentId);
            int count = companyMapper.countByParentId(parentId);

            parent.setHasChildren(count);
            companyMapper.updateHasChildren(parent);
        }
    }

    /**
     * 根据ID删除组织机构
     * 
     * @param id
     * @return
     */
    public int deleteById(Long id) {
        return companyMapper.deleteById(id);
    }

    /**
     * 批量删除组织机构
     * 
     * @param ids
     * @return
     */
    public int deleteByIds(String[] ids) {
        return companyMapper.deleteByIds(ids);
    }

    /**
     * 排序组织机构
     * 
     * @param id
     * @return
     */
    public int sortByIds(String[] ids) {
        int count = 0;
        for (int i = 0; ids != null && i < ids.length; i++) {
            Long id = Long.parseLong(ids[i]);
            count += companyMapper.updateOrderCode(id, i + 1);
        }

        return count;
    }

    @Override
    public Company findById(Long id) {
        return companyMapper.findById(id);
    }

    @Override
    public String getJsonTree(Long parentId) {
        SearchForm searchForm = new SearchForm();
        searchForm.setCompanyId(parentId);
        List<Company> list = companyMapper.selectWithCondition(searchForm);

        List<String> ztree = new ArrayList<>();

        // 组织机构类型
        List<Dictionary> dicts = dictionaryMapper.selectByGroup(DictionaryGroupKey.COMPANY_TYPE);

        for (int i = 0; dicts != null && i < dicts.size(); i++) {
            Dictionary dictionary = dicts.get(i);
            String node = "{id:'t" + dictionary.getCode() + "', sId:'" + dictionary.getCode() + "', pId:null, name:'"
                    + dictionary.getValue() + "',open: true}";
            ztree.add(node);
        }

        // 构建组织机构树
        for (int i = 0; list != null && i < list.size(); i++) {
            Company company = list.get(i);

            String node = "";

            // 根结点
            if (company.getCompanyId() == null || company.getCompanyId() == 0) {
                node = "{id:'" + company.getId() + "',pId:'t" + company.getType() + "',name:'" + company.getName()
                        + "'";
            } else {
                node = "{id:'" + company.getId() + "',pId:'" + company.getCompanyId() + "',name:'" + company.getName()
                        + "'";
            }

            // 图标
            // node += ",iconSkin:'company_tree_icon_" + company.getType() +
            // "'";

            if (company.getHasChildren() > 0) {
                node += ",open: true}";
            } else {
                node += "}";
            }
            ztree.add(node);
        }

        String[] strArr = ztree.toArray(new String[0]);

        return Arrays.toString(strArr);
    }
}
