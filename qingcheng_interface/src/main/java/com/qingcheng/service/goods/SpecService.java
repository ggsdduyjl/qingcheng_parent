package com.qingcheng.service.goods;

import com.qingcheng.entity.PageResult;
import com.qingcheng.pojo.goods.Spec;

import java.util.*;

/**
 * spec业务逻辑层
 */
public interface SpecService {

    List<Spec> findAll();

    PageResult<Spec> findPage(int page, int size);

    List<Spec> findList(Map<String, Object> searchMap);

    PageResult<Spec> findPage(Map<String, Object> searchMap, int page, int size);

    Spec findById(Integer id);

    void add(Spec spec);

    void update(Spec spec);

    void delete(Integer id);

}
