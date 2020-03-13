package com.qingcheng.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.qingcheng.dao.OrderLogMapper;
import com.qingcheng.entity.PageResult;
import com.qingcheng.pojo.order.OrderLog;
import com.qingcheng.service.order.OrderLogService;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

@Service
public class OrderLogServiceImpl implements OrderLogService {

    @Autowired
    private OrderLogMapper orderLogMapper;

    public List<OrderLog> findAll() {
        return orderLogMapper.selectAll();
    }

    public PageResult<OrderLog> findPage(int page, int size) {
        PageHelper.startPage(page, size);
        Page<OrderLog> orderLogs = (Page<OrderLog>) orderLogMapper.selectAll();
        return new PageResult<OrderLog>(orderLogs.getTotal(), orderLogs.getResult());
    }

    public List<OrderLog> findList(Map<String, Object> searchMap) {
        Example example = createExample(searchMap);
        return orderLogMapper.selectByExample(example);
    }

    public PageResult<OrderLog> findPage(Map<String, Object> searchMap, int page, int size) {
        PageHelper.startPage(page, size);
        Example example = createExample(searchMap);
        Page<OrderLog> orderLogs = (Page<OrderLog>) orderLogMapper.selectByExample(example);
        return new PageResult<OrderLog>(orderLogs.getTotal(), orderLogs.getResult());
    }

    public OrderLog findById(String id) {
        return orderLogMapper.selectByPrimaryKey(id);
    }

    public void add(OrderLog orderLog) {
        orderLogMapper.insert(orderLog);
    }

    public void update(OrderLog orderLog) {
        orderLogMapper.updateByPrimaryKeySelective(orderLog);
    }

    public void delete(String id) {
        orderLogMapper.deleteByPrimaryKey(id);
    }

    private Example createExample(Map<String, Object> searchMap) {
        Example example = new Example(OrderLog.class);
        Example.Criteria criteria = example.createCriteria();
        if (searchMap != null) {
            // ID
            if (searchMap.get("id") != null && !"".equals(searchMap.get("id"))) {
                criteria.andLike("id", "%" + searchMap.get("id") + "%");
            }
            // 操作员
            if (searchMap.get("operater") != null && !"".equals(searchMap.get("operater"))) {
                criteria.andLike("operater", "%" + searchMap.get("operater") + "%");
            }
            // 订单状态
            if (searchMap.get("orderStatus") != null && !"".equals(searchMap.get("orderStatus"))) {
                criteria.andLike("orderStatus", "%" + searchMap.get("orderStatus") + "%");
            }
            // 付款状态
            if (searchMap.get("payStatus") != null && !"".equals(searchMap.get("payStatus"))) {
                criteria.andLike("payStatus", "%" + searchMap.get("payStatus") + "%");
            }
            // 发货状态
            if (searchMap.get("consignStatus") != null && !"".equals(searchMap.get("consignStatus"))) {
                criteria.andLike("consignStatus", "%" + searchMap.get("consignStatus") + "%");
            }
            // 备注
            if (searchMap.get("remarks") != null && !"".equals(searchMap.get("remarks"))) {
                criteria.andLike("remarks", "%" + searchMap.get("remarks") + "%");
            }
        }
        return example;
    }

}
