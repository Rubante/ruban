package com.ruban.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruban.common.dao.IDictionaryMapper;
import com.ruban.common.domain.Dictionary;
import com.ruban.common.service.IDictionaryService;
import com.ruban.framework.dao.IRubanDao;
import com.ruban.framework.dao.helper.Condition;
import com.ruban.framework.dao.helper.ResultInfo;

@Service
public class DictionaryService implements IDictionaryService {

    @Autowired
    private IDictionaryMapper dictionaryMapper;

    @Autowired
    private IRubanDao rubanDao;

    @Override
    public List<Dictionary> selectAll() {
        return dictionaryMapper.selectAll();
    }

    @Override
    public List<Dictionary> selectByGroup(String group) {
        return dictionaryMapper.selectByGroup(group);
    }

    @Override
    public ResultInfo<Dictionary> selectByPage(Condition<Dictionary> condition) {
        return rubanDao.findByPage(condition, dictionaryMapper);
    }

    @Override
    public Long insert(Dictionary dictionary) {
        return dictionaryMapper.insert(dictionary);
    }

}
