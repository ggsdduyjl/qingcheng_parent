package com.qingcheng.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.qingcheng.dao.ParaMapper;
import com.qingcheng.dao.TemplateMapper;
import com.qingcheng.entity.PageResult;
import com.qingcheng.pojo.goods.Para;
import com.qingcheng.pojo.goods.Template;
import com.qingcheng.service.goods.ParaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

@Service(interfaceClass = ParaService.class)
public class ParaServiceImpl implements ParaService {

    @Autowired
    private ParaMapper paraMapper;
    @Autowired
    private TemplateMapper templateMapper;

    public List<Para> findAll() {
        return paraMapper.selectAll();
    }

    public PageResult<Para> findPage(int page, int size) {
        PageHelper.startPage(page, size);
        Page<Para> paras = (Page<Para>) paraMapper.selectAll();
        return new PageResult<Para>(paras.getTotal(), paras.getResult());
    }

    public List<Para> findList(Map<String, Object> searchMap) {
        Example example = createExample(searchMap);
        return paraMapper.selectByExample(example);
    }

    public PageResult<Para> findPage(Map<String, Object> searchMap, int page, int size) {
        PageHelper.startPage(page, size);
        Example example = createExample(searchMap);
        Page<Para> paras = (Page<Para>) paraMapper.selectByExample(example);
        return new PageResult<Para>(paras.getTotal(), paras.getResult());
    }

    public Para findById(Integer id) {
        return paraMapper.selectByPrimaryKey(id);
    }

    @Transactional
    public void add(Para para) {
        paraMapper.insert(para);
        // 将模板中的参数+1
        Template template = templateMapper.selectByPrimaryKey(para.getTemplateId());
        if (template.getParaNum() != null) {
            template.setParaNum(template.getParaNum() + 1);
            templateMapper.updateByPrimaryKeySelective(template);
        }
    }

    public void update(Para para) {
        paraMapper.updateByPrimaryKeySelective(para);
    }

    @Transactional
    public void delete(Integer id) {
        // 将模板中的参数-1
        Para para = paraMapper.selectByPrimaryKey(id);
        Template template = templateMapper.selectByPrimaryKey(para.getTemplateId());
        if (template.getParaNum() != null && template.getParaNum() > 0) {
            template.setParaNum(template.getParaNum() - 1);
            templateMapper.updateByPrimaryKeySelective(template);
        }
        paraMapper.deleteByPrimaryKey(id);
    }

    private Example createExample(Map<String, Object> searchMap) {
        Example example = new Example(Para.class);
        Example.Criteria criteria = example.createCriteria();
        if (searchMap != null) {
            // 名称
            if (searchMap.get("name") != null && !"".equals(searchMap.get("name"))) {
                criteria.andLike("name", "%" + searchMap.get("name") + "%");
            }
            // 选项
            if (searchMap.get("options") != null && !"".equals(searchMap.get("options"))) {
                criteria.andLike("options", "%" + searchMap.get("options") + "%");
            }
            // id
            if (searchMap.get("id") != null) {
                criteria.andEqualTo("id", searchMap.get("id"));
            }
            // 排序
            if (searchMap.get("seq") != null) {
                criteria.andEqualTo("seq", searchMap.get("seq"));
            }
            // 模板ID
            if (searchMap.get("templateId") != null) {
                criteria.andEqualTo("templateId", searchMap.get("templateId"));
            }
        }
        return example;
    }

}
