package com.qingcheng.service.order;

import com.qingcheng.entity.PageResult;
import com.qingcheng.pojo.order.ReturnOrderItem;

import java.util.*;

public interface ReturnOrderItemService {

    List<ReturnOrderItem> findAll();

    PageResult<ReturnOrderItem> findPage(int page, int size);

    List<ReturnOrderItem> findList(Map<String, Object> searchMap);

    PageResult<ReturnOrderItem> findPage(Map<String, Object> searchMap, int page, int size);

    ReturnOrderItem findById(Long id);

    void add(ReturnOrderItem returnOrderItem);

    void update(ReturnOrderItem returnOrderItem);

    void delete(Long id);

}
