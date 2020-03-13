package com.qingcheng.service.goods;

import com.qingcheng.entity.PageResult;
import com.qingcheng.pojo.goods.Pref;

import java.util.*;

public interface PrefService {

    List<Pref> findAll();

    PageResult<Pref> findPage(int page, int size);

    List<Pref> findList(Map<String, Object> searchMap);

    PageResult<Pref> findPage(Map<String, Object> searchMap, int page, int size);

    Pref findById(Integer id);

    void add(Pref pref);

    void update(Pref pref);

    void delete(Integer id);

}
