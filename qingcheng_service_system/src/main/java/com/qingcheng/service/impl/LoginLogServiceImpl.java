package com.qingcheng.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.qingcheng.dao.LoginLogMapper;
import com.qingcheng.entity.PageResult;
import com.qingcheng.pojo.system.LoginLog;
import com.qingcheng.service.system.LoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

@Service
public class LoginLogServiceImpl implements LoginLogService {

    @Autowired
    private LoginLogMapper loginLogMapper;

    public List<LoginLog> findAll() {
        return loginLogMapper.selectAll();
    }

    public PageResult<LoginLog> findPage(int page, int size) {
        PageHelper.startPage(page,size);
        Page<LoginLog> loginLogs = (Page<LoginLog>) loginLogMapper.selectAll();
        return new PageResult<LoginLog>(loginLogs.getTotal(),loginLogs.getResult());
    }

    public List<LoginLog> findList(Map<String, Object> searchMap) {
        Example example = createExample(searchMap);
        return loginLogMapper.selectByExample(example);
    }

    public PageResult<LoginLog> findPage(Map<String, Object> searchMap, int page, int size) {
        PageHelper.startPage(page,size);
        Example example = createExample(searchMap);
        Page<LoginLog> loginLogs = (Page<LoginLog>) loginLogMapper.selectByExample(example);
        return new PageResult<LoginLog>(loginLogs.getTotal(),loginLogs.getResult());
    }

    public LoginLog findById(Integer id) {
        return loginLogMapper.selectByPrimaryKey(id);
    }

    public void add(LoginLog loginLog) {
        loginLogMapper.insert(loginLog);
    }

    public void update(LoginLog loginLog) {
        loginLogMapper.updateByPrimaryKeySelective(loginLog);
    }

    public void delete(Integer id) {
        loginLogMapper.deleteByPrimaryKey(id);
    }

    private Example createExample(Map<String, Object> searchMap){
        Example example=new Example(LoginLog.class);
        Example.Criteria criteria = example.createCriteria();
        if(searchMap!=null){
            // login_name
            if(searchMap.get("loginName")!=null && !"".equals(searchMap.get("loginName"))){
                criteria.andEqualTo("loginName",searchMap.get("loginName"));
            }
            // ip
            if(searchMap.get("ip")!=null && !"".equals(searchMap.get("ip"))){
                criteria.andLike("ip","%"+searchMap.get("ip")+"%");
            }
            // browser_name
            if(searchMap.get("browserName")!=null && !"".equals(searchMap.get("browserName"))){
                criteria.andLike("browserName","%"+searchMap.get("browserName")+"%");
            }
            // 地区
            if(searchMap.get("location")!=null && !"".equals(searchMap.get("location"))){
                criteria.andLike("location","%"+searchMap.get("location")+"%");
            }
            // id
            if(searchMap.get("id")!=null ){
                criteria.andEqualTo("id",searchMap.get("id"));
            }
        }
        return example;
    }

}
