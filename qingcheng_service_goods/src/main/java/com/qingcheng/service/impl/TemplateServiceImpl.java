package com.qingcheng.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.qingcheng.dao.TemplateMapper;
import com.qingcheng.entity.PageResult;
import com.qingcheng.pojo.goods.Template;
import com.qingcheng.service.goods.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TemplateServiceImpl implements TemplateService {

    @Autowired
    private TemplateMapper templateMapper;

    public List<Template> findAll() {
        return templateMapper.selectAll();
    }

    public PageResult<Template> findPage(int page, int size) {
        PageHelper.startPage(page, size);
        Page<Template> templates = (Page<Template>) templateMapper.selectAll();
        return new PageResult<Template>(templates.getTotal(), templates.getResult());
    }

    public List<Template> findList(Map<String, Object> searchMap) {
        Example example = createExample(searchMap);
        return templateMapper.selectByExample(example);
    }

    public PageResult<Template> findPage(Map<String, Object> searchMap, int page, int size) {
        PageHelper.startPage(page, size);
        Example example = createExample(searchMap);
        Page<Template> templates = (Page<Template>) templateMapper.selectByExample(example);
        return new PageResult<Template>(templates.getTotal(), templates.getResult());
    }

    public Template findById(Integer id) {
        return templateMapper.selectByPrimaryKey(id);
    }

    public void add(Template template) {
        template.setSpecNum(0);
        template.setParaNum(0);
        templateMapper.insert(template);
    }

    public void update(Template template) {
        templateMapper.updateByPrimaryKeySelective(template);
    }

    public void delete(Integer id) {
        templateMapper.deleteByPrimaryKey(id);
    }

    public Map<String, String> getTemplateMap() {
        List<Template> templates = templateMapper.selectAll();
        Map<String, String> map = new HashMap<>();
        for (Template template : templates) {
            map.put(template.getId().toString(), template.getName());
        }
        return map;
    }

    private Example createExample(Map<String, Object> searchMap) {
        Example example = new Example(Template.class);
        Example.Criteria criteria = example.createCriteria();
        if (searchMap != null) {
            // 模板名称
            if (searchMap.get("name") != null && !"".equals(searchMap.get("name"))) {
                criteria.andLike("name", "%" + searchMap.get("name") + "%");
            }
            // ID
            if (searchMap.get("id") != null) {
                criteria.andEqualTo("id", searchMap.get("id"));
            }
            // 规格数量
            if (searchMap.get("specNum") != null) {
                criteria.andEqualTo("specNum", searchMap.get("specNum"));
            }
            // 参数数量
            if (searchMap.get("paraNum") != null) {
                criteria.andEqualTo("paraNum", searchMap.get("paraNum"));
            }
        }
        return example;
    }

}
