package com.qingcheng.init;

import com.qingcheng.service.goods.CategoryService;
import com.qingcheng.service.goods.SkuService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

public class InitService implements InitializingBean {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SkuService skuService;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("将分类导航菜单加入到redis。。。");
        categoryService.saveCategoryTreeToRedis();
        System.out.println("将sku价格加入到redis。。。");
        skuService.saveAllPriceToRedis();
    }
}
