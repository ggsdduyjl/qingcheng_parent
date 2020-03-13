package com.qingcheng.service.goods;

import com.qingcheng.entity.PageResult;
import com.qingcheng.pojo.goods.Para;

import java.util.*;

public interface ParaService {

    List<Para> findAll();

    PageResult<Para> findPage(int page, int size);

    List<Para> findList(Map<String, Object> searchMap);

    PageResult<Para> findPage(Map<String, Object> searchMap, int page, int size);

    Para findById(Integer id);

    void add(Para para);

    void update(Para para);

    void delete(Integer id);

}
