package com.qingcheng.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.qingcheng.dao.PreferentialMapper;
import com.qingcheng.entity.PageResult;
import com.qingcheng.pojo.order.Preferential;
import com.qingcheng.service.order.PreferentialService;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

@Service
public class PreferentialServiceImpl implements PreferentialService {

    @Autowired
    private PreferentialMapper preferentialMapper;

    public List<Preferential> findAll() {
        return preferentialMapper.selectAll();
    }

    public PageResult<Preferential> findPage(int page, int size) {
        PageHelper.startPage(page, size);
        Page<Preferential> preferentials = (Page<Preferential>) preferentialMapper.selectAll();
        return new PageResult<Preferential>(preferentials.getTotal(), preferentials.getResult());
    }

    public List<Preferential> findList(Map<String, Object> searchMap) {
        Example example = createExample(searchMap);
        return preferentialMapper.selectByExample(example);
    }

    public PageResult<Preferential> findPage(Map<String, Object> searchMap, int page, int size) {
        PageHelper.startPage(page, size);
        Example example = createExample(searchMap);
        Page<Preferential> preferentials = (Page<Preferential>) preferentialMapper.selectByExample(example);
        return new PageResult<Preferential>(preferentials.getTotal(), preferentials.getResult());
    }

    public Preferential findById(Integer id) {
        return preferentialMapper.selectByPrimaryKey(id);
    }

    public void add(Preferential preferential) {
        preferentialMapper.insert(preferential);
    }

    public void update(Preferential preferential) {
        preferentialMapper.updateByPrimaryKeySelective(preferential);
    }

    public void delete(Integer id) {
        preferentialMapper.deleteByPrimaryKey(id);
    }

    private Example createExample(Map<String, Object> searchMap) {
        Example example = new Example(Preferential.class);
        Example.Criteria criteria = example.createCriteria();
        if (searchMap != null) {
            // 状态
            if (searchMap.get("state") != null && !"".equals(searchMap.get("state"))) {
                criteria.andLike("state", "%" + searchMap.get("state") + "%");
            }
            // 类型1不翻倍 2翻倍
            if (searchMap.get("type") != null && !"".equals(searchMap.get("type"))) {
                criteria.andLike("type", "%" + searchMap.get("type") + "%");
            }
            // ID
            if (searchMap.get("id") != null) {
                criteria.andEqualTo("id", searchMap.get("id"));
            }
            // 消费金额
            if (searchMap.get("buyMoney") != null) {
                criteria.andEqualTo("buyMoney", searchMap.get("buyMoney"));
            }
            // 优惠金额
            if (searchMap.get("preMoney") != null) {
                criteria.andEqualTo("preMoney", searchMap.get("preMoney"));
            }
        }
        return example;
    }

}
