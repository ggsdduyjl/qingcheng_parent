package com.qingcheng.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.qingcheng.pojo.goods.Sku;
import com.qingcheng.service.goods.CategoryService;
import com.qingcheng.service.goods.SpuService;
import com.qingcheng.vo.GoodsVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.io.File;
import java.io.PrintWriter;
import java.util.*;

@RestController
@RequestMapping("/item")
public class ItemController {

    @Reference
    private SpuService spuService;
    @Reference
    private CategoryService categoryService;

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    // 生成商品详情页地址
    @Value("${pagePath}")
    private String pagePath;
    // 模板文件名
    @Value("${template}")
    private String template;

    /**
     * 生成商品详情页
     *
     * @param id spuId
     */
    @RequestMapping("createPage")
    public String createPage(String id) {
        // 获取商品信息
        GoodsVo vo = spuService.findGoodsById(id);
        // 查询商品分类
        List<String> cateList = new ArrayList<>();
        cateList.add(categoryService.findById(vo.getSpu().getCategory1Id()).getName());
        cateList.add(categoryService.findById(vo.getSpu().getCategory2Id()).getName());
        cateList.add(categoryService.findById(vo.getSpu().getCategory3Id()).getName());
        // spu参数列表
        Map paraItems = null;
        if (StringUtils.isNotBlank(vo.getSpu().getParaItems())) {
            paraItems = JSON.parseObject(vo.getSpu().getParaItems(), Map.class);
        }
        // 生成sku地址列表
        Map<String, String> urlMap = new HashMap<>();
        for (Sku sku : vo.getSkuList()) {
            if ("1".equals(sku.getStatus())) {
                String specJson = JSON.toJSONString(JSON.parseObject(sku.getSpec()), SerializerFeature.MapSortField);
                urlMap.put(specJson, sku.getId() + ".html");
            }
        }
        // 文件夹对象
        File dir = new File(pagePath);
        // 文件夹不存在则创建文件夹
        if (!dir.exists()) {
            dir.mkdirs();
        }
        // 为每一个sku生成一个页面
        for (Sku sku : vo.getSkuList()) {
            // 创建上下文对象
            Context context = new Context();
            // 创建数据模型
            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("spu", vo.getSpu());
            dataModel.put("sku", sku);
            dataModel.put("cateList", cateList);
            if (StringUtils.isNoneBlank((vo.getSpu().getImages()))) {
                dataModel.put("spuImages", vo.getSpu().getImages().split(","));
            }
            dataModel.put("skuImages", sku.getImages().split(","));
            dataModel.put("paraItems", paraItems);
            // 当前sku规格
            Map specItems = JSON.parseObject(sku.getSpec(), Map.class);
            dataModel.put("specItems", specItems);
            // spu规格列表
            JSONObject specMap = JSON.parseObject(vo.getSpu().getSpecItems());
            for (String key : specMap.keySet()) {
                List<String> list = JSON.parseArray( JSON.toJSONString(specMap.get(key)),String.class);
                List<Map<String, Object>> mapList = new ArrayList<>();
                for (String value : list) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("option", value);
                    map.put("checked", specItems.get(key).equals(value));
                    JSONObject spec = JSON.parseObject(sku.getSpec());
                    spec.put(key, value);
                    String specJson = JSON.toJSONString(spec, SerializerFeature.MapSortField);
                    map.put("url", urlMap.get(specJson));
                    mapList.add(map);
                }
                specMap.put(key, mapList);
            }
            dataModel.put("specMap", specMap);
            context.setVariables(dataModel);
            try {
                // 生成页面
                springTemplateEngine.process(template, context, new PrintWriter(new File(dir, sku.getId() + ".html"), "UTF-8"));
                System.out.println(sku.getId() + "生成成功");
            } catch (Exception e) {
                System.out.println(sku.getId() + "生成失败");
            }
        }
        return "生成成功";
    }
}
