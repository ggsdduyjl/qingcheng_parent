package com.qingcheng.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qingcheng.pojo.business.Ad;
import com.qingcheng.service.business.AdService;
import com.qingcheng.service.goods.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
public class IndexController {

    private final static String INDEX = "index";

    @Reference
    private AdService adService;
    @Reference
    private CategoryService categoryService;

    @RequestMapping("/index")
    public String index(Model model) {
        // 查询首页轮播图
        List<Ad> lbList = adService.findByPosition("index_lb");
        // 查询首页做出分类菜单
        List<Map> categoryTree = categoryService.findCategoryTree();
        model.addAttribute("lbList", lbList);
        model.addAttribute("categoryTree", categoryTree);
        return INDEX;
    }
}
