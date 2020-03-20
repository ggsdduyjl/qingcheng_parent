package com.qingcheng.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.qingcheng.dao.RoleMapper;
import com.qingcheng.entity.PageResult;
import com.qingcheng.pojo.system.Role;
import com.qingcheng.service.system.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    public List<Role> findAll() {
        return roleMapper.selectAll();
    }

    public PageResult<Role> findPage(int page, int size) {
        PageHelper.startPage(page, size);
        Page<Role> roles = (Page<Role>) roleMapper.selectAll();
        return new PageResult<Role>(roles.getTotal(), roles.getResult());
    }

    public List<Role> findList(Map<String, Object> searchMap) {
        Example example = createExample(searchMap);
        return roleMapper.selectByExample(example);
    }

    public PageResult<Role> findPage(Map<String, Object> searchMap, int page, int size) {
        PageHelper.startPage(page, size);
        Example example = createExample(searchMap);
        Page<Role> roles = (Page<Role>) roleMapper.selectByExample(example);
        return new PageResult<Role>(roles.getTotal(), roles.getResult());
    }

    public Role findById(Integer id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    public void add(Role role) {
        roleMapper.insert(role);
    }

    public void update(Role role) {
        roleMapper.updateByPrimaryKeySelective(role);
    }

    public void delete(Integer id) {
        roleMapper.deleteByPrimaryKey(id);
    }

    private Example createExample(Map<String, Object> searchMap) {
        Example example = new Example(Role.class);
        Example.Criteria criteria = example.createCriteria();
        if (searchMap != null) {
            // 角色名称
            if (searchMap.get("name") != null && !"".equals(searchMap.get("name"))) {
                criteria.andLike("name", "%" + searchMap.get("name") + "%");
            }
            // ID
            if (searchMap.get("id") != null) {
                criteria.andEqualTo("id", searchMap.get("id"));
            }

        }
        return example;
    }
}
