package com.qingcheng.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.qingcheng.dao.CategoryMapper;
import com.qingcheng.entity.PageResult;
import com.qingcheng.exceptions.AdminException;
import com.qingcheng.pojo.goods.Category;
import com.qingcheng.service.goods.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    public List<Category> findAll() {
        return categoryMapper.selectAll();
    }

    public PageResult<Category> findPage(int page, int size) {
        PageHelper.startPage(page, size);
        Page<Category> categorys = (Page<Category>) categoryMapper.selectAll();
        return new PageResult<Category>(categorys.getTotal(), categorys.getResult());
    }

    public List<Category> findList(Map<String, Object> searchMap) {
        Example example = createExample(searchMap);
        return categoryMapper.selectByExample(example);
    }

    public PageResult<Category> findPage(Map<String, Object> searchMap, int page, int size) {
        PageHelper.startPage(page, size);
        Example example = createExample(searchMap);
        Page<Category> categorys = (Page<Category>) categoryMapper.selectByExample(example);
        return new PageResult<Category>(categorys.getTotal(), categorys.getResult());
    }

    public Category findById(Integer id) {
        return categoryMapper.selectByPrimaryKey(id);
    }

    public void add(Category category) {
        categoryMapper.insert(category);
    }

    public void update(Category category) {
        categoryMapper.updateByPrimaryKeySelective(category);
    }

    public void delete(Integer id) throws AdminException {
        // 判断是否存在下级分类
        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("parentId", id);
        int count = categoryMapper.selectCountByExample(example);
        if (count > 0) {
            throw new AdminException("存在下级分类不能删除");
        }
        categoryMapper.deleteByPrimaryKey(id);
    }

    private Example createExample(Map<String, Object> searchMap) {
        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        if (searchMap != null) {
            // 分类名称
            if (searchMap.get("name") != null && !"".equals(searchMap.get("name"))) {
                criteria.andLike("name", "%" + searchMap.get("name") + "%");
            }
            // 是否显示
            if (searchMap.get("isShow") != null && !"".equals(searchMap.get("isShow"))) {
                criteria.andLike("isShow", "%" + searchMap.get("isShow") + "%");
            }
            // 是否导航
            if (searchMap.get("isMenu") != null && !"".equals(searchMap.get("isMenu"))) {
                criteria.andLike("isMenu", "%" + searchMap.get("isMenu") + "%");
            }
            // 分类ID
            if (searchMap.get("id") != null) {
                criteria.andEqualTo("id", searchMap.get("id"));
            }
            // 商品数量
            if (searchMap.get("goodsNum") != null) {
                criteria.andEqualTo("goodsNum", searchMap.get("goodsNum"));
            }
            // 排序
            if (searchMap.get("seq") != null) {
                criteria.andEqualTo("seq", searchMap.get("seq"));
            }
            // 上级ID
            if (searchMap.get("parentId") != null) {
                criteria.andEqualTo("parentId", searchMap.get("parentId"));
            }
            // 模板ID
            if (searchMap.get("templateId") != null) {
                criteria.andEqualTo("templateId", searchMap.get("templateId"));
            }
        }
        return example;
    }

}