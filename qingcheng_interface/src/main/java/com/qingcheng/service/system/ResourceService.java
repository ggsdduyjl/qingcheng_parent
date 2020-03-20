package com.qingcheng.service.system;

import com.qingcheng.entity.PageResult;
import com.qingcheng.pojo.system.Resource;

import java.util.*;

public interface ResourceService {

    List<Resource> findAll();

    PageResult<Resource> findPage(int page, int size);

    List<Resource> findList(Map<String, Object> searchMap);

    PageResult<Resource> findPage(Map<String, Object> searchMap, int page, int size);

    Resource findById(Integer id);

    void add(Resource resource);

    void update(Resource resource);

    void delete(Integer id);

}
