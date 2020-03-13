package com.qingcheng.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.qingcheng.dao.ReturnCauseMapper;
import com.qingcheng.entity.PageResult;
import com.qingcheng.pojo.order.ReturnCause;
import com.qingcheng.service.order.ReturnCauseService;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

@Service
public class ReturnCauseServiceImpl implements ReturnCauseService {

    @Autowired
    private ReturnCauseMapper returnCauseMapper;

    public List<ReturnCause> findAll() {
        return returnCauseMapper.selectAll();
    }

    public PageResult<ReturnCause> findPage(int page, int size) {
        PageHelper.startPage(page, size);
        Page<ReturnCause> returnCauses = (Page<ReturnCause>) returnCauseMapper.selectAll();
        return new PageResult<ReturnCause>(returnCauses.getTotal(), returnCauses.getResult());
    }

    public List<ReturnCause> findList(Map<String, Object> searchMap) {
        Example example = createExample(searchMap);
        return returnCauseMapper.selectByExample(example);
    }

    public PageResult<ReturnCause> findPage(Map<String, Object> searchMap, int page, int size) {
        PageHelper.startPage(page, size);
        Example example = createExample(searchMap);
        Page<ReturnCause> returnCauses = (Page<ReturnCause>) returnCauseMapper.selectByExample(example);
        return new PageResult<ReturnCause>(returnCauses.getTotal(), returnCauses.getResult());
    }

    public ReturnCause findById(Integer id) {
        return returnCauseMapper.selectByPrimaryKey(id);
    }

    public void add(ReturnCause returnCause) {
        returnCauseMapper.insert(returnCause);
    }

    public void update(ReturnCause returnCause) {
        returnCauseMapper.updateByPrimaryKeySelective(returnCause);
    }

    public void delete(Integer id) {
        returnCauseMapper.deleteByPrimaryKey(id);
    }

    private Example createExample(Map<String, Object> searchMap) {
        Example example = new Example(ReturnCause.class);
        Example.Criteria criteria = example.createCriteria();
        if (searchMap != null) {
            // 原因
            if (searchMap.get("cause") != null && !"".equals(searchMap.get("cause"))) {
                criteria.andLike("cause", "%" + searchMap.get("cause") + "%");
            }
            // 是否启用
            if (searchMap.get("status") != null && !"".equals(searchMap.get("status"))) {
                criteria.andLike("status", "%" + searchMap.get("status") + "%");
            }
            // ID
            if (searchMap.get("id") != null) {
                criteria.andEqualTo("id", searchMap.get("id"));
            }
            // 排序
            if (searchMap.get("seq") != null) {
                criteria.andEqualTo("seq", searchMap.get("seq"));
            }
        }
        return example;
    }

}
