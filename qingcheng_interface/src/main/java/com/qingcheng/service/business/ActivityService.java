package com.qingcheng.service.business;

import com.qingcheng.entity.PageResult;
import com.qingcheng.pojo.business.Activity;

import java.util.*;

public interface ActivityService {

    List<Activity> findAll();

    PageResult<Activity> findPage(int page, int size);

    List<Activity> findList(Map<String, Object> searchMap);

    PageResult<Activity> findPage(Map<String, Object> searchMap, int page, int size);

    Activity findById(Integer id);

    void add(Activity activity);

    void update(Activity activity);

    void delete(Integer id);

}
