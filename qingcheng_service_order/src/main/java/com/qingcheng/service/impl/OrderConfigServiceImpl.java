package com.qingcheng.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.qingcheng.dao.OrderConfigMapper;
import com.qingcheng.entity.PageResult;
import com.qingcheng.pojo.order.OrderConfig;
import com.qingcheng.service.order.OrderConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

@Service
public class OrderConfigServiceImpl implements OrderConfigService {

    @Autowired
    private OrderConfigMapper orderConfigMapper;

    public List<OrderConfig> findAll() {
        return orderConfigMapper.selectAll();
    }

    public PageResult<OrderConfig> findPage(int page, int size) {
        PageHelper.startPage(page, size);
        Page<OrderConfig> orderConfigs = (Page<OrderConfig>) orderConfigMapper.selectAll();
        return new PageResult<OrderConfig>(orderConfigs.getTotal(), orderConfigs.getResult());
    }

    public List<OrderConfig> findList(Map<String, Object> searchMap) {
        Example example = createExample(searchMap);
        return orderConfigMapper.selectByExample(example);
    }

    public PageResult<OrderConfig> findPage(Map<String, Object> searchMap, int page, int size) {
        PageHelper.startPage(page, size);
        Example example = createExample(searchMap);
        Page<OrderConfig> orderConfigs = (Page<OrderConfig>) orderConfigMapper.selectByExample(example);
        return new PageResult<OrderConfig>(orderConfigs.getTotal(), orderConfigs.getResult());
    }

    public OrderConfig findById(Integer id) {
        return orderConfigMapper.selectByPrimaryKey(id);
    }

    public void add(OrderConfig orderConfig) {
        orderConfigMapper.insert(orderConfig);
    }

    public void update(OrderConfig orderConfig) {
        orderConfigMapper.updateByPrimaryKeySelective(orderConfig);
    }

    public void delete(Integer id) {
        orderConfigMapper.deleteByPrimaryKey(id);
    }

    private Example createExample(Map<String, Object> searchMap) {
        Example example = new Example(OrderConfig.class);
        Example.Criteria criteria = example.createCriteria();
        if (searchMap != null) {
            // ID
            if (searchMap.get("id") != null) {
                criteria.andEqualTo("id", searchMap.get("id"));
            }
            // 正常订单超时时间（分）
            if (searchMap.get("orderTimeout") != null) {
                criteria.andEqualTo("orderTimeout", searchMap.get("orderTimeout"));
            }
            // 秒杀订单超时时间（分）
            if (searchMap.get("seckillTimeout") != null) {
                criteria.andEqualTo("seckillTimeout", searchMap.get("seckillTimeout"));
            }
            // 自动收货（天）
            if (searchMap.get("takeTimeout") != null) {
                criteria.andEqualTo("takeTimeout", searchMap.get("takeTimeout"));
            }
            // 售后期限
            if (searchMap.get("serviceTimeout") != null) {
                criteria.andEqualTo("serviceTimeout", searchMap.get("serviceTimeout"));
            }
            // 自动五星好评
            if (searchMap.get("commentTimeout") != null) {
                criteria.andEqualTo("commentTimeout", searchMap.get("commentTimeout"));
            }
        }
        return example;
    }

}
