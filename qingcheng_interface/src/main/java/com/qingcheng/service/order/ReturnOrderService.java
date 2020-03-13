package com.qingcheng.service.order;

import com.qingcheng.entity.PageResult;
import com.qingcheng.pojo.order.ReturnOrder;

import java.util.*;

public interface ReturnOrderService {

    List<ReturnOrder> findAll();

    PageResult<ReturnOrder> findPage(int page, int size);

    List<ReturnOrder> findList(Map<String, Object> searchMap);

    PageResult<ReturnOrder> findPage(Map<String, Object> searchMap, int page, int size);

    ReturnOrder findById(Long id);

    void add(ReturnOrder returnOrder);

    void update(ReturnOrder returnOrder);

    void delete(Long id);

}
