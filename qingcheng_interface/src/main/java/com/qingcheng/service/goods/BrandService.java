package com.qingcheng.service.goods;

import com.qingcheng.entity.PageResult;
import com.qingcheng.pojo.Brand;

import java.util.List;
import java.util.Map;

public interface BrandService {

    List<Brand> findAll();

    PageResult<Brand> findPage(Integer page, Integer size);

    List<Brand> findList(Map<String, Object> searchMap);

    PageResult<Brand> findPage(Map<String, Object> searchMap, Integer page, Integer size);

    Brand findById(Integer id);

    void add(Brand brand);

    void update(Brand brand);

    void delete(Integer id);
}
