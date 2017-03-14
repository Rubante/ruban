package com.ruban.rbac.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruban.framework.core.utils.commons.DateUtil;
import com.ruban.framework.core.utils.commons.RandomUtil;
import com.ruban.framework.dao.IRubanDao;
import com.ruban.framework.dao.helper.Condition;
import com.ruban.framework.dao.helper.ResultInfo;
import com.ruban.rbac.backend.account.form.AccountForm;
import com.ruban.rbac.dao.authz.IAccountMapper;
import com.ruban.rbac.domain.authz.Account;
import com.ruban.rbac.service.IAccountService;

@Service
public class AccountService implements IAccountService {

    @Autowired
    private IAccountMapper accountMapper;

    @Autowired
    private IRubanDao rubanDao;

    @Override
    public List<Account> selectAll() {
        return accountMapper.selectAll();
    }

    @Override
    public ResultInfo<Account> selectByPage(Condition<Account> condition) {

        ResultInfo<Account> result = rubanDao.findByPage(condition, accountMapper);
        return result;
    }

    @Override
    public List<Account> selectByCondition(Condition<Account> condition) {
        return accountMapper.selectWithCondition(condition);
    }

    @Override
    public void insert(AccountForm accountForm) {

        Account account = new Account();

        account.setAccountNo(accountForm.getAccountNo());
        account.setName(accountForm.getName());
        account.setPassword(accountForm.getPassword());
        account.setMemo(accountForm.getMemo());
        
        account.setAddTime(DateUtil.getToday());
        account.setModTime(DateUtil.getToday());
        account.setAddUserId(0L);
        account.setModUserId(0L);
        account.setUpdateLock(RandomUtil.getUpdateLock());

        accountMapper.insert(account);
    }

    @Override
    public int update(AccountForm accountForm) {

        Account account = findById(accountForm.getId());

        account.setAccountNo(accountForm.getAccountNo());
        account.setName(accountForm.getName());
        account.setPassword(accountForm.getPassword());
        account.setMemo(accountForm.getMemo());
        
        account.setModTime(DateUtil.getToday());
        account.setModUserId(0L);
        account.setUpdateLock(RandomUtil.getUpdateLock());

        account.setHoldLock(accountForm.getHoldLock());

        int result = accountMapper.update(account);

        return result;
    }

    /**
     * 根据ID删除人员
     * 
     * @param id
     * @return
     */
    public int deleteById(Long id) {
        return accountMapper.deleteById(id);
    }

    /**
     * 批量删除人员
     * 
     * @param ids
     * @return
     */
    public int deleteByIds(String[] ids) {
        return accountMapper.deleteByIds(ids);
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
            count += accountMapper.updateOrderCode(id, i + 1);
        }

        return count;
    }

    @Override
    public Account findById(Long id) {
        return accountMapper.findById(id);
    }
}
