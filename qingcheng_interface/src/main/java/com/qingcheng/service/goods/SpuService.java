package com.qingcheng.service.goods;

import com.qingcheng.entity.PageResult;
import com.qingcheng.pojo.goods.Spu;

import java.util.*;

/**
 * spu业务逻辑层
 */
public interface SpuService {

    List<Spu> findAll();

    PageResult<Spu> findPage(int page, int size);

    List<Spu> findList(Map<String, Object> searchMap);

    PageResult<Spu> findPage(Map<String, Object> searchMap, int page, int size);

    Spu findById(String id);

    void add(Spu spu);

    void update(Spu spu);

    void delete(String id);

}
