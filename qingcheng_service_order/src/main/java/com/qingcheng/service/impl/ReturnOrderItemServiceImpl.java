package com.qingcheng.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.qingcheng.dao.ReturnOrderItemMapper;
import com.qingcheng.entity.PageResult;
import com.qingcheng.pojo.order.ReturnOrderItem;
import com.qingcheng.service.order.ReturnOrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

@Service
public class ReturnOrderItemServiceImpl implements ReturnOrderItemService {

    @Autowired
    private ReturnOrderItemMapper returnOrderItemMapper;

    public List<ReturnOrderItem> findAll() {
        return returnOrderItemMapper.selectAll();
    }

    public PageResult<ReturnOrderItem> findPage(int page, int size) {
        PageHelper.startPage(page, size);
        Page<ReturnOrderItem> returnOrderItems = (Page<ReturnOrderItem>) returnOrderItemMapper.selectAll();
        return new PageResult<ReturnOrderItem>(returnOrderItems.getTotal(), returnOrderItems.getResult());
    }

    public List<ReturnOrderItem> findList(Map<String, Object> searchMap) {
        Example example = createExample(searchMap);
        return returnOrderItemMapper.selectByExample(example);
    }

    public PageResult<ReturnOrderItem> findPage(Map<String, Object> searchMap, int page, int size) {
        PageHelper.startPage(page, size);
        Example example = createExample(searchMap);
        Page<ReturnOrderItem> returnOrderItems = (Page<ReturnOrderItem>) returnOrderItemMapper.selectByExample(example);
        return new PageResult<ReturnOrderItem>(returnOrderItems.getTotal(), returnOrderItems.getResult());
    }

    public ReturnOrderItem findById(Long id) {
        return returnOrderItemMapper.selectByPrimaryKey(id);
    }

    public void add(ReturnOrderItem returnOrderItem) {
        returnOrderItemMapper.insert(returnOrderItem);
    }

    public void update(ReturnOrderItem returnOrderItem) {
        returnOrderItemMapper.updateByPrimaryKeySelective(returnOrderItem);
    }

    public void delete(Long id) {
        returnOrderItemMapper.deleteByPrimaryKey(id);
    }

    private Example createExample(Map<String, Object> searchMap) {
        Example example = new Example(ReturnOrderItem.class);
        Example.Criteria criteria = example.createCriteria();
        if (searchMap != null) {
            // 标题
            if (searchMap.get("title") != null && !"".equals(searchMap.get("title"))) {
                criteria.andLike("title", "%" + searchMap.get("title") + "%");
            }
            // 图片地址
            if (searchMap.get("image") != null && !"".equals(searchMap.get("image"))) {
                criteria.andLike("image", "%" + searchMap.get("image") + "%");
            }
            // 单价
            if (searchMap.get("price") != null) {
                criteria.andEqualTo("price", searchMap.get("price"));
            }
            // 数量
            if (searchMap.get("num") != null) {
                criteria.andEqualTo("num", searchMap.get("num"));
            }
            // 总金额
            if (searchMap.get("money") != null) {
                criteria.andEqualTo("money", searchMap.get("money"));
            }
            // 支付金额
            if (searchMap.get("payMoney") != null) {
                criteria.andEqualTo("payMoney", searchMap.get("payMoney"));
            }
            // 重量
            if (searchMap.get("weight") != null) {
                criteria.andEqualTo("weight", searchMap.get("weight"));
            }
        }
        return example;
    }

}
