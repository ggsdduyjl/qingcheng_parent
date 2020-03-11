package com.qingcheng.service.goods;

import com.qingcheng.entity.Goods;
import com.qingcheng.entity.PageResult;
import com.qingcheng.exceptions.AdminException;
import com.qingcheng.pojo.goods.Spu;

import java.util.*;

/**
 * spu业务逻辑层
 */
public interface SpuService {

    List<Spu> findAll();

    PageResult<Spu> findPage(int page, int size);

    List<Spu> findList(Map<String, Object> searchMap);

    PageResult<Spu> findPage(Map<String, Object> searchMap, int page, int size);

    Spu findById(String id);

    void add(Spu spu);

    void update(Spu spu);

    void delete(String id);

    /**
     * 保存商品
     *
     * @param goods spu + skuList
     */
    void saveGoods(Goods goods);

    /**
     * 查询商品
     *
     * @param id spu商品id
     * @return spu + skuList
     */
    Goods findGoodsById(String id);

    /**
     * 商品审核
     *
     * @param id      商品spuId
     * @param status  1.通过 2.不通过
     * @param message 理由
     */
    void audit(String id, String status, String message);

    /**
     * 下架商品
     *
     * @param id 商品id
     */
    void pull(String id);

    /**
     * 上架商品
     *
     * @param id 商品id
     */
    void put(String id) throws AdminException;

    /**
     * 批量上架商品
     *
     * @param ids 商品id数组
     * @return 上架商品个数
     */
    int putMany(String[] ids);

    /**
     * 批量下架商品
     *
     * @param ids 商品id数组
     * @return 下架商品数量
     */
    int pullMany(String[] ids);

    /**
     * 删除商品
     *
     * @param id 商品id
     */
    void deleteGoods(String id);

    /**
     * 还原商品
     *
     * @param id 商品id
     */
    void restore(String id);

}
