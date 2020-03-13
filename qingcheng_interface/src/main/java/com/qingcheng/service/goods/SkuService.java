package com.qingcheng.service.goods;

import com.qingcheng.entity.PageResult;
import com.qingcheng.pojo.goods.Sku;

import java.util.*;

public interface SkuService {

    List<Sku> findAll();

    PageResult<Sku> findPage(int page, int size);

    List<Sku> findList(Map<String, Object> searchMap);

    PageResult<Sku> findPage(Map<String, Object> searchMap, int page, int size);

    Sku findById(String id);

    void add(Sku sku);

    void update(Sku sku);

    void delete(String id);

}
