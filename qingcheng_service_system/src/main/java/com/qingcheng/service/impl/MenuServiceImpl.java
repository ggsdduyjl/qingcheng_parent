package com.qingcheng.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.qingcheng.dao.MenuMapper;
import com.qingcheng.entity.PageResult;
import com.qingcheng.pojo.system.Menu;
import com.qingcheng.service.system.MenuService;
import com.qingcheng.vo.MenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    public List<Menu> findAll() {
        return menuMapper.selectAll();
    }

    public PageResult<Menu> findPage(int page, int size) {
        PageHelper.startPage(page, size);
        Page<Menu> menus = (Page<Menu>) menuMapper.selectAll();
        return new PageResult<Menu>(menus.getTotal(), menus.getResult());
    }

    public List<Menu> findList(Map<String, Object> searchMap) {
        Example example = createExample(searchMap);
        return menuMapper.selectByExample(example);
    }

    public PageResult<Menu> findPage(Map<String, Object> searchMap, int page, int size) {
        PageHelper.startPage(page, size);
        Example example = createExample(searchMap);
        Page<Menu> menus = (Page<Menu>) menuMapper.selectByExample(example);
        return new PageResult<Menu>(menus.getTotal(), menus.getResult());
    }

    public Menu findById(String id) {
        return menuMapper.selectByPrimaryKey(id);
    }

    public void add(Menu menu) {
        menuMapper.insert(menu);
    }

    public void update(Menu menu) {
        menuMapper.updateByPrimaryKeySelective(menu);
    }

    public void delete(String id) {
        menuMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<MenuVo> findMenu() {
        List<Menu> menus = menuMapper.selectAll();
        return findByParentId(menus, "0");
    }

    /**
     * 根据父id查询子菜单
     *
     * @param list     从list中国查找
     * @param parentId 父id
     */
    private List<MenuVo> findByParentId(List<Menu> list, String parentId) {
        List<MenuVo> vos = new ArrayList<>();
        for (Menu menu : list) {
            if (menu.getParentId().equals(parentId)) {
                MenuVo vo = new MenuVo();
                vo.setPath(menu.getId());
                vo.setIcon(menu.getIcon());
                vo.setLinkUrl(menu.getUrl());
                vo.setTitle(menu.getName());
                vo.setChildren(findByParentId(list, menu.getId()));
                vos.add(vo);
            }
        }
        return vos;
    }

    private Example createExample(Map<String, Object> searchMap) {
        Example example = new Example(Menu.class);
        Example.Criteria criteria = example.createCriteria();
        if (searchMap != null) {
            // 菜单ID
            if (searchMap.get("id") != null && !"".equals(searchMap.get("id"))) {
                criteria.andLike("id", "%" + searchMap.get("id") + "%");
            }
            // 菜单名称
            if (searchMap.get("name") != null && !"".equals(searchMap.get("name"))) {
                criteria.andLike("name", "%" + searchMap.get("name") + "%");
            }
            // 图标
            if (searchMap.get("icon") != null && !"".equals(searchMap.get("icon"))) {
                criteria.andLike("icon", "%" + searchMap.get("icon") + "%");
            }
            // URL
            if (searchMap.get("url") != null && !"".equals(searchMap.get("url"))) {
                criteria.andLike("url", "%" + searchMap.get("url") + "%");
            }
            // 上级菜单ID
            if (searchMap.get("parentId") != null && !"".equals(searchMap.get("parentId"))) {
                criteria.andLike("parentId", "%" + searchMap.get("parentId") + "%");
            }
        }
        return example;
    }

}
