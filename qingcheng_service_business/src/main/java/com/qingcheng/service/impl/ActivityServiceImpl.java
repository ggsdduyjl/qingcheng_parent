package com.qingcheng.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.qingcheng.dao.ActivityMapper;
import com.qingcheng.entity.PageResult;
import com.qingcheng.pojo.business.Activity;
import com.qingcheng.service.business.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityMapper activityMapper;

    public List<Activity> findAll() {
        return activityMapper.selectAll();
    }

    public PageResult<Activity> findPage(int page, int size) {
        PageHelper.startPage(page,size);
        Page<Activity> activitys = (Page<Activity>) activityMapper.selectAll();
        return new PageResult<Activity>(activitys.getTotal(),activitys.getResult());
    }

    public List<Activity> findList(Map<String, Object> searchMap) {
        Example example = createExample(searchMap);
        return activityMapper.selectByExample(example);
    }

    public PageResult<Activity> findPage(Map<String, Object> searchMap, int page, int size) {
        PageHelper.startPage(page,size);
        Example example = createExample(searchMap);
        Page<Activity> activitys = (Page<Activity>) activityMapper.selectByExample(example);
        return new PageResult<Activity>(activitys.getTotal(),activitys.getResult());
    }

    public Activity findById(Integer id) {
        return activityMapper.selectByPrimaryKey(id);
    }

    public void add(Activity activity) {
        activityMapper.insert(activity);
    }

    public void update(Activity activity) {
        activityMapper.updateByPrimaryKeySelective(activity);
    }

    public void delete(Integer id) {
        activityMapper.deleteByPrimaryKey(id);
    }

    private Example createExample(Map<String, Object> searchMap){
        Example example=new Example(Activity.class);
        Example.Criteria criteria = example.createCriteria();
        if(searchMap!=null){
            // 活动标题
            if(searchMap.get("title")!=null && !"".equals(searchMap.get("title"))){
                criteria.andLike("title","%"+searchMap.get("title")+"%");
            }
            // 状态
            if(searchMap.get("status")!=null && !"".equals(searchMap.get("status"))){
                criteria.andLike("status","%"+searchMap.get("status")+"%");
            }
            // 活动内容
            if(searchMap.get("content")!=null && !"".equals(searchMap.get("content"))){
                criteria.andLike("content","%"+searchMap.get("content")+"%");
            }
            // ID
            if(searchMap.get("id")!=null ){
                criteria.andEqualTo("id",searchMap.get("id"));
            }
        }
        return example;
    }

}
