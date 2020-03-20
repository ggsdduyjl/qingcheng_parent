package com.qingcheng.service.system;

import com.qingcheng.entity.PageResult;
import com.qingcheng.pojo.system.Admin;

import java.util.*;

public interface AdminService {

    List<Admin> findAll();

    PageResult<Admin> findPage(int page, int size);

    List<Admin> findList(Map<String, Object> searchMap);

    PageResult<Admin> findPage(Map<String, Object> searchMap, int page, int size);

    Admin findById(Integer id);

    void add(Admin admin);

    void update(Admin admin);

    void delete(Integer id);

}
