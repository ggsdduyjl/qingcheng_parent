package com.qingcheng.service.order;

import com.qingcheng.entity.PageResult;
import com.qingcheng.pojo.order.Preferential;

import java.util.*;

public interface PreferentialService {

    List<Preferential> findAll();

    PageResult<Preferential> findPage(int page, int size);

    List<Preferential> findList(Map<String, Object> searchMap);

    PageResult<Preferential> findPage(Map<String, Object> searchMap, int page, int size);

    Preferential findById(Integer id);

    void add(Preferential preferential);

    void update(Preferential preferential);

    void delete(Integer id);

}
