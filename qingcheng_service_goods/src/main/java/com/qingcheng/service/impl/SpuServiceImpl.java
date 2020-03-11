package com.qingcheng.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.qingcheng.dao.*;
import com.qingcheng.entity.Goods;
import com.qingcheng.entity.PageResult;
import com.qingcheng.exceptions.AdminException;
import com.qingcheng.pojo.goods.*;
import com.qingcheng.service.goods.SpuService;
import com.qingcheng.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

@Service(interfaceClass = SpuService.class)
public class SpuServiceImpl implements SpuService {

    @Autowired
    private SpuMapper spuMapper;
    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private CategoryBrandMapper categoryBrandMapper;
    @Autowired
    private GoodsAuditLoggingMapper goodsAuditLoggingMapper;
    @Autowired
    private GoodsLogMapper goodsLogMapper;

    @Autowired
    IdWorker idWorker;

    public List<Spu> findAll() {
        return spuMapper.selectAll();
    }

    public PageResult<Spu> findPage(int page, int size) {
        PageHelper.startPage(page, size);
        Page<Spu> spus = (Page<Spu>) spuMapper.selectAll();
        return new PageResult<Spu>(spus.getTotal(), spus.getResult());
    }

    public List<Spu> findList(Map<String, Object> searchMap) {
        Example example = createExample(searchMap);
        return spuMapper.selectByExample(example);
    }

    public PageResult<Spu> findPage(Map<String, Object> searchMap, int page, int size) {
        PageHelper.startPage(page, size);
        Example example = createExample(searchMap);
        Page<Spu> spus = (Page<Spu>) spuMapper.selectByExample(example);
        return new PageResult<Spu>(spus.getTotal(), spus.getResult());
    }

    public Spu findById(String id) {
        return spuMapper.selectByPrimaryKey(id);
    }

    public void add(Spu spu) {
        spuMapper.insert(spu);
    }

    public void update(Spu spu) {
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    public void delete(String id) {
        spuMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @Override
    public void saveGoods(Goods goods) {
        Date date = new Date();
        // 保存spu
        Spu spu = goods.getSpu();
        if (spu.getId() == null || "".equals(spu.getId())) { // 添加
            spu.setId(idWorker.nextId() + "");
            spuMapper.insertSelective(spu);
        } else { // 修改
            spuMapper.updateByPrimaryKeySelective(spu);
            // 删除sku
            Example example = new Example(Sku.class);
            example.createCriteria().andEqualTo("spuId", spu.getId());
            skuMapper.deleteByExample(example);
        }
        // 保存sku
        List<Sku> skuList = goods.getSkuList();
        Brand brand = brandMapper.selectByPrimaryKey(spu.getBrandId());
        Category category = categoryMapper.selectByPrimaryKey(spu.getCategory3Id());
        for (Sku sku : skuList) {
            if (sku.getId() == null || "".equals(sku.getId())) {
                sku.setId(idWorker.nextId() + "");
                sku.setCreateTime(date);
            }
            sku.setSpuId(spu.getId());
            sku.setUpdateTime(date);
            // 拼接sku名称 = spu名称 + 规格
            StringBuilder name = new StringBuilder(spu.getName());
            if (sku.getSpec() != null && !"".equals(sku.getSpec())) {
                Map map = JSON.parseObject(sku.getSpec(), Map.class);
                for (Object value : map.values()) {
                    name.append(" ").append(value);
                }
            }
            sku.setName(name.toString());
            if (brand != null) {
                sku.setBrandName(brand.getName());
            }
            sku.setCategoryId(spu.getCategory3Id());
            if (category != null) {
                sku.setCategoryName(category.getName());
            }
            skuMapper.insertSelective(sku);
        }
        // 建立分类和品牌的关联
        CategoryBrand cb = new CategoryBrand();
        cb.setCategoryId(spu.getCategory3Id());
        cb.setBrandId(spu.getBrandId());
        int count = categoryBrandMapper.selectCount(cb);
        if (count == 0) {
            categoryBrandMapper.insert(cb);
        }
    }

    @Override
    public Goods findGoodsById(String id) {
        Goods goods = new Goods();
        Spu spu = spuMapper.selectByPrimaryKey(id);
        goods.setSpu(spu);
        Example example = new Example(Sku.class);
        example.createCriteria().andEqualTo("spuId", id);
        List<Sku> skuList = skuMapper.selectByExample(example);
        goods.setSkuList(skuList);
        return goods;
    }

    @Transactional
    @Override
    public void audit(String id, String status, String message) {
        Date date = new Date();
        Spu spu = new Spu();
        spu.setId(id);
        spu.setStatus(status);
        // 审核通过，将商品上架
        if ("1".equals(status)) {
            spu.setIsMarketable("1");
        }
        spuMapper.updateByPrimaryKeySelective(spu);
        // 记录商品审核记录
        GoodsAuditLogging logging = new GoodsAuditLogging();
        logging.setCreateTime(date);
        logging.setGoodsId(id);
        logging.setStatus(status);
        logging.setMessage(message);
        // TODO 没做登录，userId和userName暂时写死
        logging.setUserId("1");
        logging.setUserName("admin");
        goodsAuditLoggingMapper.insertSelective(logging);
        // 记录商品日志
        GoodsLog log = new GoodsLog();
        log.setCreateTime(date);
        log.setGoodsId(id);
        log.setUserId(logging.getUserId());
        log.setUserName(logging.getUserName());
        log.setFromStatus("0");
        log.setToStatus(status);
        goodsLogMapper.insertSelective(log);
    }

    @Transactional
    @Override
    public void pull(String id) {
        Spu spu = new Spu();
        spu.setId(id);
        spu.setIsMarketable("0"); // 下架
        spuMapper.updateByPrimaryKeySelective(spu);
        // 记录商品日志
        GoodsLog log = new GoodsLog();
        log.setCreateTime(new Date());
        log.setGoodsId(id);
        // TODO 没做登录，userId和userName暂时写死
        log.setUserId("1");
        log.setUserName("admin");
        log.setFromMarketable("1");
        log.setToMarketable("0");
        goodsLogMapper.insertSelective(log);
    }

    @Transactional
    @Override
    public void put(String id) throws AdminException {
        Spu spu = spuMapper.selectByPrimaryKey(id);
        if (!"1".equals(spu.getStatus())) {
            throw new AdminException("此商品未通过审核");
        }
        spu.setIsMarketable("1"); // 上架
        spuMapper.updateByPrimaryKeySelective(spu);
        // 记录商品日志
        GoodsLog log = new GoodsLog();
        log.setCreateTime(new Date());
        log.setGoodsId(id);
        // TODO 没做登录，userId和userName暂时写死
        log.setUserId("1");
        log.setUserName("admin");
        log.setFromMarketable("0");
        log.setToMarketable("1");
        goodsLogMapper.insertSelective(log);
    }

    @Transactional
    @Override
    public int putMany(String[] ids) {
        Spu spu = new Spu();
        spu.setIsMarketable("1");
        Example example = new Example(Spu.class);
        example.createCriteria().andIn("id", Arrays.asList(ids))
                .andEqualTo("status", "1") // 审核通过的
                .andEqualTo("isMarketable", "0") // 没下架的
                .andEqualTo("isDelete", "0"); // 没删除的
        // 记录商品日志
        List<Spu> spus = spuMapper.selectByExample(example);
        Date date = new Date();
        for (Spu s : spus) {
            GoodsLog log = new GoodsLog();
            log.setCreateTime(date);
            log.setGoodsId(s.getId());
            // TODO 没做登录，userId和userName暂时写死
            log.setUserId("1");
            log.setUserName("admin");
            log.setFromMarketable("0");
            log.setToMarketable("1");
            goodsLogMapper.insertSelective(log);
        }
        return spuMapper.updateByExampleSelective(spu, example);
    }

    @Transactional
    @Override
    public int pullMany(String[] ids) {
        Spu spu = new Spu();
        spu.setIsMarketable("0");
        Example example = new Example(Spu.class);
        example.createCriteria().andIn("id", Arrays.asList(ids))
                .andEqualTo("isMarketable", "1") // 上架的
                .andEqualTo("isDelete", "0"); // 没删除的
        // 记录商品日志
        List<Spu> spus = spuMapper.selectByExample(example);
        Date date = new Date();
        for (Spu s : spus) {
            GoodsLog log = new GoodsLog();
            log.setCreateTime(date);
            log.setGoodsId(s.getId());
            // TODO 没做登录，userId和userName暂时写死
            log.setUserId("1");
            log.setUserName("admin");
            log.setFromMarketable("1");
            log.setToMarketable("0");
            goodsLogMapper.insertSelective(log);
        }
        return spuMapper.updateByExampleSelective(spu, example);
    }

    @Override
    public void deleteGoods(String id) {
        Spu spu = new Spu();
        spu.setId(id);
        spu.setIsDelete("1");
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    @Override
    public void restore(String id) {
        Spu spu = new Spu();
        spu.setId(id);
        spu.setIsDelete("0");
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    private Example createExample(Map<String, Object> searchMap) {
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        if (searchMap != null) {
            // 主键
            if (searchMap.get("id") != null && !"".equals(searchMap.get("id"))) {
                criteria.andLike("id", "%" + searchMap.get("id") + "%");
            }
            // 货号
            if (searchMap.get("sn") != null && !"".equals(searchMap.get("sn"))) {
                criteria.andLike("sn", "%" + searchMap.get("sn") + "%");
            }
            // SPU名
            if (searchMap.get("name") != null && !"".equals(searchMap.get("name"))) {
                criteria.andLike("name", "%" + searchMap.get("name") + "%");
            }
            // 副标题
            if (searchMap.get("caption") != null && !"".equals(searchMap.get("caption"))) {
                criteria.andLike("caption", "%" + searchMap.get("caption") + "%");
            }
            // 图片
            if (searchMap.get("image") != null && !"".equals(searchMap.get("image"))) {
                criteria.andLike("image", "%" + searchMap.get("image") + "%");
            }
            // 图片列表
            if (searchMap.get("images") != null && !"".equals(searchMap.get("images"))) {
                criteria.andLike("images", "%" + searchMap.get("images") + "%");
            }
            // 售后服务
            if (searchMap.get("saleService") != null && !"".equals(searchMap.get("saleService"))) {
                criteria.andLike("saleService", "%" + searchMap.get("saleService") + "%");
            }
            // 介绍
            if (searchMap.get("introduction") != null && !"".equals(searchMap.get("introduction"))) {
                criteria.andLike("introduction", "%" + searchMap.get("introduction") + "%");
            }
            // 规格列表
            if (searchMap.get("specItems") != null && !"".equals(searchMap.get("specItems"))) {
                criteria.andLike("specItems", "%" + searchMap.get("specItems") + "%");
            }
            // 参数列表
            if (searchMap.get("paraItems") != null && !"".equals(searchMap.get("paraItems"))) {
                criteria.andLike("paraItems", "%" + searchMap.get("paraItems") + "%");
            }
            // 是否上架
            if (searchMap.get("isMarketable") != null && !"".equals(searchMap.get("isMarketable"))) {
                criteria.andLike("isMarketable", "%" + searchMap.get("isMarketable") + "%");
            }
            // 是否启用规格
            if (searchMap.get("isEnableSpec") != null && !"".equals(searchMap.get("isEnableSpec"))) {
                criteria.andLike("isEnableSpec", "%" + searchMap.get("isEnableSpec") + "%");
            }
            // 是否删除
            if (searchMap.get("isDelete") != null && !"".equals(searchMap.get("isDelete"))) {
                criteria.andLike("isDelete", "%" + searchMap.get("isDelete") + "%");
            }
            // 审核状态
            if (searchMap.get("status") != null && !"".equals(searchMap.get("status"))) {
                criteria.andLike("status", "%" + searchMap.get("status") + "%");
            }
            // 品牌ID
            if (searchMap.get("brandId") != null) {
                criteria.andEqualTo("brandId", searchMap.get("brandId"));
            }
            // 一级分类
            if (searchMap.get("category1Id") != null) {
                criteria.andEqualTo("category1Id", searchMap.get("category1Id"));
            }
            // 二级分类
            if (searchMap.get("category2Id") != null) {
                criteria.andEqualTo("category2Id", searchMap.get("category2Id"));
            }
            // 三级分类
            if (searchMap.get("category3Id") != null) {
                criteria.andEqualTo("category3Id", searchMap.get("category3Id"));
            }
            // 模板ID
            if (searchMap.get("templateId") != null) {
                criteria.andEqualTo("templateId", searchMap.get("templateId"));
            }
            // 运费模板id
            if (searchMap.get("freightId") != null) {
                criteria.andEqualTo("freightId", searchMap.get("freightId"));
            }
            // 销量
            if (searchMap.get("saleNum") != null) {
                criteria.andEqualTo("saleNum", searchMap.get("saleNum"));
            }
            // 评论数
            if (searchMap.get("commentNum") != null) {
                criteria.andEqualTo("commentNum", searchMap.get("commentNum"));
            }
        }
        return example;
    }

}
