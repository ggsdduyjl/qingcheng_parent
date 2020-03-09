package com.qingcheng.service.goods;

import com.qingcheng.entity.PageResult;
import com.qingcheng.pojo.goods.Template;

import java.util.*;

/**
 * template业务逻辑层
 */
public interface TemplateService {

    List<Template> findAll();

    PageResult<Template> findPage(int page, int size);

    List<Template> findList(Map<String, Object> searchMap);

    PageResult<Template> findPage(Map<String, Object> searchMap, int page, int size);

    Template findById(Integer id);

    void add(Template template);

    void update(Template template);

    void delete(Integer id);

    Map<String,String> getTemplateMap();

}
