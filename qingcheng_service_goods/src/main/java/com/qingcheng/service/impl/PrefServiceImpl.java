package com.qingcheng.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.qingcheng.dao.PrefMapper;
import com.qingcheng.entity.PageResult;
import com.qingcheng.pojo.goods.Pref;
import com.qingcheng.service.goods.PrefService;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

@Service
public class PrefServiceImpl implements PrefService {

    @Autowired
    private PrefMapper prefMapper;

    public List<Pref> findAll() {
        return prefMapper.selectAll();
    }

    public PageResult<Pref> findPage(int page, int size) {
        PageHelper.startPage(page, size);
        Page<Pref> prefs = (Page<Pref>) prefMapper.selectAll();
        return new PageResult<Pref>(prefs.getTotal(), prefs.getResult());
    }

    public List<Pref> findList(Map<String, Object> searchMap) {
        Example example = createExample(searchMap);
        return prefMapper.selectByExample(example);
    }

    public PageResult<Pref> findPage(Map<String, Object> searchMap, int page, int size) {
        PageHelper.startPage(page, size);
        Example example = createExample(searchMap);
        Page<Pref> prefs = (Page<Pref>) prefMapper.selectByExample(example);
        return new PageResult<Pref>(prefs.getTotal(), prefs.getResult());
    }

    public Pref findById(Integer id) {
        return prefMapper.selectByPrimaryKey(id);
    }

    public void add(Pref pref) {
        prefMapper.insert(pref);
    }

    public void update(Pref pref) {
        prefMapper.updateByPrimaryKeySelective(pref);
    }

    public void delete(Integer id) {
        prefMapper.deleteByPrimaryKey(id);
    }

    private Example createExample(Map<String, Object> searchMap) {
        Example example = new Example(Pref.class);
        Example.Criteria criteria = example.createCriteria();
        if (searchMap != null) {
            // 类型
            if (searchMap.get("type") != null && !"".equals(searchMap.get("type"))) {
                criteria.andLike("type", "%" + searchMap.get("type") + "%");
            }
            // 状态
            if (searchMap.get("state") != null && !"".equals(searchMap.get("state"))) {
                criteria.andLike("state", "%" + searchMap.get("state") + "%");
            }
            // ID
            if (searchMap.get("id") != null) {
                criteria.andEqualTo("id", searchMap.get("id"));
            }
            // 分类ID
            if (searchMap.get("cateId") != null) {
                criteria.andEqualTo("cateId", searchMap.get("cateId"));
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
