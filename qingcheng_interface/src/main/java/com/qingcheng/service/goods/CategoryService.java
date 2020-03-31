package com.qingcheng.service.goods;

import com.qingcheng.entity.PageResult;
import com.qingcheng.exceptions.AdminException;
import com.qingcheng.pojo.goods.Category;

import java.util.*;

public interface CategoryService {

    List<Category> findAll();

    PageResult<Category> findPage(int page, int size);

    List<Category> findList(Map<String, Object> searchMap);

    PageResult<Category> findPage(Map<String, Object> searchMap, int page, int size);

    Category findById(Integer id);

    void add(Category category);

    void update(Category category);

    void delete(Integer id) throws AdminException;

    /**
     * 查询首页商品分类菜单
     */
    List<Map> findCategoryTree();

    /**
     * 将分类菜单加入到redis
     */
    void saveCategoryTreeToRedis();
}
