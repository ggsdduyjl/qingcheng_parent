package com.qingcheng.service.order;

import com.qingcheng.entity.PageResult;
import com.qingcheng.pojo.order.OrderConfig;

import java.util.*;

public interface OrderConfigService {

    List<OrderConfig> findAll();

    PageResult<OrderConfig> findPage(int page, int size);

    List<OrderConfig> findList(Map<String, Object> searchMap);

    PageResult<OrderConfig> findPage(Map<String, Object> searchMap, int page, int size);

    OrderConfig findById(Integer id);

    void add(OrderConfig orderConfig);

    void update(OrderConfig orderConfig);

    void delete(Integer id);

}
