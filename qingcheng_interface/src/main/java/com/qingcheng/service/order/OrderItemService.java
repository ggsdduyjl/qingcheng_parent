package com.qingcheng.service.order;

import com.qingcheng.entity.PageResult;
import com.qingcheng.pojo.order.OrderItem;

import java.util.*;

public interface OrderItemService {

    List<OrderItem> findAll();

    PageResult<OrderItem> findPage(int page, int size);

    List<OrderItem> findList(Map<String, Object> searchMap);

    PageResult<OrderItem> findPage(Map<String, Object> searchMap, int page, int size);

    OrderItem findById(String id);

    void add(OrderItem orderItem);

    void update(OrderItem orderItem);

    void delete(String id);

}
