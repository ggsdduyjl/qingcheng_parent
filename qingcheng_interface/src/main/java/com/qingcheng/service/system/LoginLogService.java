package com.qingcheng.service.system;

import com.qingcheng.entity.PageResult;
import com.qingcheng.pojo.system.LoginLog;

import java.util.*;

public interface LoginLogService {

    List<LoginLog> findAll();

    PageResult<LoginLog> findPage(int page, int size);

    List<LoginLog> findList(Map<String, Object> searchMap);

    PageResult<LoginLog> findPage(Map<String, Object> searchMap, int page, int size);

    LoginLog findById(Integer id);

    void add(LoginLog loginLog);

    void update(LoginLog loginLog);

    void delete(Integer id);

}
