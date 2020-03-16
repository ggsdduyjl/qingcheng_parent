package com.qingcheng.service.order;

import com.qingcheng.entity.PageResult;
import com.qingcheng.pojo.order.Order;
import com.qingcheng.vo.OrderVo;

import java.util.*;

public interface OrderService {

    List<Order> findAll();

    PageResult<Order> findPage(int page, int size);

    List<Order> findList(Map<String, Object> searchMap);

    PageResult<Order> findPage(Map<String, Object> searchMap, int page, int size);

    Order findById(String id);

    void add(Order order);

    void update(Order order);

    void delete(String id);

    /**
     * 查询订单信息
     *
     * @param id 主订单id
     * @return 主单信息 + 订单明细集合
     */
    OrderVo getOrderInfo(String id);

    /**
     * 订单超时自动处理
     */
    void orderTimeOutLogic();

}
