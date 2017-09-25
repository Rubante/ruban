package com.ruban.rbac.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruban.framework.core.utils.commons.DateUtil;
import com.ruban.framework.core.utils.commons.RandomUtil;
import com.ruban.framework.core.utils.commons.StringUtil;
import com.ruban.framework.dao.IRubanDao;
import com.ruban.framework.dao.helper.Condition;
import com.ruban.framework.dao.helper.ResultInfo;
import com.ruban.rbac.backend.person.form.PersonForm;
import com.ruban.rbac.dao.organization.IPersonMapper;
import com.ruban.rbac.domain.organization.Person;
import com.ruban.rbac.service.IPersonService;
import com.ruban.rbac.service.contants.PersonContant;

@Service
public class PersonService implements IPersonService {

    @Autowired
    private IPersonMapper personMapper;

    @Autowired
    private IRubanDao rubanDao;

    @Override
    public List<Person> selectAll() {
        return personMapper.selectAll();
    }

    @Override
    public ResultInfo<Person> selectByPage(Condition<Person> condition) {

        ResultInfo<Person> result = rubanDao.findByPage(condition, personMapper);
        return result;
    }

    @Override
    public List<Person> selectByCondition(Condition<Person> condition) {
        return personMapper.selectWithCondition(condition);
    }

    @Override
    public int insert(PersonForm personForm) {

        Person person = new Person();

        person.setName(personForm.getName());
        person.setCode(personForm.getCode());
        person.setBirthday(personForm.getBirthday());
        person.setEmail(personForm.getEmail());
        person.setSex(personForm.getSex());
        person.setCardid(personForm.getCardid());
        person.setPhoto(personForm.getPhoto());
        person.setAddress(personForm.getAddress());
        person.setEntryDate(personForm.getEntryDate());
        person.setDepartureDate(personForm.getDepartureDate());
        person.setSalary(personForm.getSalary());
        person.setJobId(personForm.getJobId());
        person.setTitleId(personForm.getTitleId());
        person.setState(1);
        person.setMemo(personForm.getMemo());
        person.setOrderCode(1);

        person.setAddTime(DateUtil.getToday());
        person.setModTime(DateUtil.getToday());
        person.setAddUserId(0L);
        person.setModUserId(0L);
        person.setUpdateLock(RandomUtil.getUpdateLock());
        person.setDepartmentId(personForm.getDepartmentId());
        person.setCompanyId(personForm.getCompanyId());

        return personMapper.insert(person);
    }

    @Override
    public int update(PersonForm personForm) {

        Person person = findById(personForm.getId());

        person.setName(personForm.getName());
        person.setCode(personForm.getCode());
        person.setBirthday(personForm.getBirthday());
        person.setEmail(personForm.getEmail());
        person.setSex(personForm.getSex());
        person.setCardid(personForm.getCardid());

        // 判断是否删除
        if (PersonContant.DEL_FLAG.equals(personForm.getDelPhoto())) {
            person.setPhoto(null);
        } else {
            if (StringUtil.isNotNullOrEmpty(personForm.getPhoto())) {
                person.setPhoto(personForm.getPhoto());
            }
        }

        person.setAddress(personForm.getAddress());
        person.setEntryDate(personForm.getEntryDate());
        person.setDepartureDate(personForm.getDepartureDate());
        person.setSalary(personForm.getSalary());
        person.setJobId(personForm.getJobId());
        person.setTitleId(personForm.getTitleId());
        person.setState(1);
        person.setMemo(personForm.getMemo());
        person.setOrderCode(1);

        person.setModTime(DateUtil.getToday());
        person.setModUserId(0L);
        person.setUpdateLock(RandomUtil.getUpdateLock());
        person.setDepartmentId(personForm.getDepartmentId());
        person.setCompanyId(personForm.getCompanyId());

        person.setHoldLock(personForm.getHoldLock());

        int result = personMapper.update(person);

        return result;
    }

    /**
     * 根据ID删除人员
     * 
     * @param id
     * @return
     */
    public int deleteById(Long id) {
        return personMapper.deleteById(id);
    }

    /**
     * 批量删除人员
     * 
     * @param ids
     * @return
     */
    public int deleteByIds(String[] ids) {
        return personMapper.deleteByIds(ids);
    }

    /**
     * 排序人员
     * 
     * @param id
     * @return
     */
    public int sortByIds(String[] ids) {
        int count = 0;
        for (int i = 0; ids != null && i < ids.length; i++) {
            Long id = Long.parseLong(ids[i]);
            count += personMapper.updateOrderCode(id, i + 1);
        }

        return count;
    }

    @Override
    public Person findById(Long id) {
        return personMapper.findById(id);
    }
}
