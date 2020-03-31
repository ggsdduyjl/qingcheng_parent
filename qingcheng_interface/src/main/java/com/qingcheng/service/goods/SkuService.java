package com.qingcheng.service.goods;

import com.qingcheng.entity.PageResult;
import com.qingcheng.pojo.goods.Sku;

import java.util.*;

public interface SkuService {

    List<Sku> findAll();

    PageResult<Sku> findPage(int page, int size);

    List<Sku> findList(Map<String, Object> searchMap);

    PageResult<Sku> findPage(Map<String, Object> searchMap, int page, int size);

    Sku findById(String id);

    void add(Sku sku);

    void update(Sku sku);

    void delete(String id);

    /**
     * 将所有sku的价格放入redis
     */
    void saveAllPriceToRedis();

    /**
     * 获取sku商品价格
     *
     * @param id skuid
     */
    Integer findPrice(String id);

    /**
     * 保存价格到缓存
     *
     * @param id    skuid
     * @param price 价格
     */
    void savePriceToRedis(String id, Integer price);

    /**
     * 删除缓存中的价格
     *
     * @param id skuid
     */
    void deleteRedisPrice(String id);
}
