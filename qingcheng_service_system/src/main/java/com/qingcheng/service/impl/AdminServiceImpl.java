package com.qingcheng.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.qingcheng.dao.AdminMapper;
import com.qingcheng.entity.PageResult;
import com.qingcheng.pojo.system.Admin;
import com.qingcheng.service.system.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    public List<Admin> findAll() {
        return adminMapper.selectAll();
    }

    public PageResult<Admin> findPage(int page, int size) {
        PageHelper.startPage(page, size);
        Page<Admin> admins = (Page<Admin>) adminMapper.selectAll();
        return new PageResult<Admin>(admins.getTotal(), admins.getResult());
    }

    public List<Admin> findList(Map<String, Object> searchMap) {
        Example example = createExample(searchMap);
        return adminMapper.selectByExample(example);
    }

    public PageResult<Admin> findPage(Map<String, Object> searchMap, int page, int size) {
        PageHelper.startPage(page, size);
        Example example = createExample(searchMap);
        Page<Admin> admins = (Page<Admin>) adminMapper.selectByExample(example);
        return new PageResult<Admin>(admins.getTotal(), admins.getResult());
    }

    public Admin findById(Integer id) {
        return adminMapper.selectByPrimaryKey(id);
    }

    public void add(Admin admin) {
        adminMapper.insert(admin);
    }

    public void update(Admin admin) {
        adminMapper.updateByPrimaryKeySelective(admin);
    }

    public void delete(Integer id) {
        adminMapper.deleteByPrimaryKey(id);
    }

    private Example createExample(Map<String, Object> searchMap) {
        Example example = new Example(Admin.class);
        Example.Criteria criteria = example.createCriteria();
        if (searchMap != null) {
            // 用户名
            if (searchMap.get("loginName") != null && !"".equals(searchMap.get("loginName"))) {
                criteria.andEqualTo("loginName", searchMap.get("loginName"));
            }
            // 密码
            if (searchMap.get("password") != null && !"".equals(searchMap.get("password"))) {
                criteria.andEqualTo("password", searchMap.get("password"));
            }
            // 状态
            if (searchMap.get("status") != null && !"".equals(searchMap.get("status"))) {
                criteria.andEqualTo("status", searchMap.get("status"));
            }
            // id
            if (searchMap.get("id") != null) {
                criteria.andEqualTo("id", searchMap.get("id"));
            }

        }
        return example;
    }

}
