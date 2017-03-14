package com.ruban.framework.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.PageInfo;
import com.ruban.framework.dao.IRubanDao;
import com.ruban.framework.dao.IRubanMapper;
import com.ruban.framework.dao.helper.Condition;
import com.ruban.framework.dao.helper.ResultInfo;

@Repository
public class RubanDao implements IRubanDao {

    @Override
    public <E> ResultInfo<E> findByPage(Condition<E> condition, IRubanMapper mapper) {

        RowBounds rowBounds = new RowBounds(condition.getPageNum(), condition.getPageSize());

        List<E> resultList = mapper.selectWithCondition(rowBounds, condition);

        PageInfo<E> pageInfo = new PageInfo<E>(resultList);

        ResultInfo<E> result = pageInfoToResultInfo(pageInfo);

        return result;
    }

    /***
     * 分页信息转换
     * 
     * @param pageInfo
     * @param resultInfo
     */
    public <F> ResultInfo<F> pageInfoToResultInfo(PageInfo<F> pageInfo) {
        ResultInfo<F> resultInfo = new ResultInfo<F>();

        // 汇总信息
        resultInfo.setTotal(pageInfo.getTotal());
        resultInfo.setPages(pageInfo.getPages());
        resultInfo.setList(pageInfo.getList());
        resultInfo.setPageNum(pageInfo.getPageNum());
        resultInfo.setPageSize(pageInfo.getPageSize());

        // 上下页
        resultInfo.setFirstPage(pageInfo.getFirstPage());
        resultInfo.setPrePage(pageInfo.getPrePage());
        resultInfo.setNextPage(pageInfo.getNextPage());
        resultInfo.setLastPage(pageInfo.getLastPage());

        // 起始结束行
        resultInfo.setStartRow(pageInfo.getStartRow());
        resultInfo.setEndRow(pageInfo.getEndRow());

        return resultInfo;
    }
}
