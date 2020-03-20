package com.qingcheng.service.system;

import com.qingcheng.entity.PageResult;
import com.qingcheng.pojo.system.Role;

import java.util.*;

public interface RoleService {

    List<Role> findAll();

    PageResult<Role> findPage(int page, int size);

    List<Role> findList(Map<String, Object> searchMap);

    PageResult<Role> findPage(Map<String, Object> searchMap, int page, int size);

    Role findById(Integer id);

    void add(Role role);

    void update(Role role);

    void delete(Integer id);

}
