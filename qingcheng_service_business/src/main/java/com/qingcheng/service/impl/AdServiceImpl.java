package com.qingcheng.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.qingcheng.dao.AdMapper;
import com.qingcheng.entity.PageResult;
import com.qingcheng.enums.CacheKey;
import com.qingcheng.pojo.business.Ad;
import com.qingcheng.service.business.AdService;
import jdk.nashorn.internal.runtime.linker.LinkerCallSite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class AdServiceImpl implements AdService {

    @Autowired
    private AdMapper adMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    public List<Ad> findAll() {
        return adMapper.selectAll();
    }

    public PageResult<Ad> findPage(int page, int size) {
        PageHelper.startPage(page, size);
        Page<Ad> ads = (Page<Ad>) adMapper.selectAll();
        return new PageResult<Ad>(ads.getTotal(), ads.getResult());
    }

    public List<Ad> findList(Map<String, Object> searchMap) {
        Example example = createExample(searchMap);
        return adMapper.selectByExample(example);
    }

    public PageResult<Ad> findPage(Map<String, Object> searchMap, int page, int size) {
        PageHelper.startPage(page, size);
        Example example = createExample(searchMap);
        Page<Ad> ads = (Page<Ad>) adMapper.selectByExample(example);
        for (Ad ad : ads.getResult()) {
            ad.setPosition(getPosition(ad.getPosition()));
        }
        return new PageResult<Ad>(ads.getTotal(), ads.getResult());
    }

    public Ad findById(Integer id) {
        return adMapper.selectByPrimaryKey(id);
    }

    public void add(Ad ad) {
        adMapper.insert(ad);
        saveAdToRedisByPosition(ad.getPosition());
    }

    public void update(Ad ad) {
        String position = adMapper.selectByPrimaryKey(ad.getId()).getPosition();
        saveAdToRedisByPosition(position);
        adMapper.updateByPrimaryKeySelective(ad);
        if (!ad.getPosition().equals(position)) {
            saveAdToRedisByPosition(ad.getPosition());
        }
    }

    public void delete(Integer id) {
        String position = adMapper.selectByPrimaryKey(id).getPosition();
        adMapper.deleteByPrimaryKey(id);
        saveAdToRedisByPosition(position);
    }

    @Override
    public List<Ad> findByPosition(String position) {
        String adsJson = (String) redisTemplate.boundHashOps(CacheKey.AD.toString()).get(position);
        return JSON.parseArray(adsJson, Ad.class);
    }

    @Override
    public void saveAdToRedis() {
        for (String position : getAllPosition()) {
            saveAdToRedisByPosition(position);
        }
    }

    @Override
    public void saveAdToRedisByPosition(String position) {
        Date date = new Date();
        Example example = new Example(Ad.class);
        example.createCriteria().andEqualTo("position", position)
                .andLessThanOrEqualTo("startTime", date)
                .andGreaterThanOrEqualTo("endTime", date)
                .andEqualTo("status", 1);
        List<Ad> ads = adMapper.selectByExample(example);
        redisTemplate.boundHashOps(CacheKey.AD.toString()).put(position, JSON.toJSONString(ads));
    }

    /**
     * 所有广告位置
     */
    private List<String> getAllPosition() {
        List<String> list = new ArrayList<>();
        list.add("index_lb");
        list.add("index_amusing");
        list.add("index_ea_lb");
        list.add("index_ea");
        list.add("index_mobile_lb");
        list.add("index_mobile");
        return list;
    }

    /**
     * 根据广告位置代码得到位置名称
     *
     * @param code 广告位置代码
     * @return 位置名称
     */
    private String getPosition(String code) {
        String name = "";
        switch (code) {
            case "index_lb":
                name = "首页轮播图";
                break;
            case "index_amusing":
                name = "有趣区";
                break;
            case "index_ea_lb":
                name = "家用电器楼层轮播图";
                break;
            case "index_ea":
                name = "家用电器楼层广告";
                break;
            case "index_mobile_lb":
                name = "手机通讯楼层轮播图";
                break;
            case "index_mobile":
                name = "手机通信楼层广告";
                break;
        }
        return name;
    }

    private Example createExample(Map<String, Object> searchMap) {
        Example example = new Example(Ad.class);
        Example.Criteria criteria = example.createCriteria();
        if (searchMap != null) {
            // 广告名称
            if (searchMap.get("name") != null && !"".equals(searchMap.get("name"))) {
                criteria.andLike("name", "%" + searchMap.get("name") + "%");
            }
            // 广告位置
            if (searchMap.get("position") != null && !"".equals(searchMap.get("position"))) {
                criteria.andEqualTo("position", searchMap.get("position"));
            }
            // 状态
            if (searchMap.get("status") != null && !"".equals(searchMap.get("status"))) {
                criteria.andLike("status", "%" + searchMap.get("status") + "%");
            }
            // 图片地址
            if (searchMap.get("image") != null && !"".equals(searchMap.get("image"))) {
                criteria.andLike("image", "%" + searchMap.get("image") + "%");
            }
            // URL
            if (searchMap.get("url") != null && !"".equals(searchMap.get("url"))) {
                criteria.andLike("url", "%" + searchMap.get("url") + "%");
            }
            // 备注
            if (searchMap.get("remarks") != null && !"".equals(searchMap.get("remarks"))) {
                criteria.andLike("remarks", "%" + searchMap.get("remarks") + "%");
            }
            // ID
            if (searchMap.get("id") != null) {
                criteria.andEqualTo("id", searchMap.get("id"));
            }
            if (searchMap.get("startTime") != null) {
                criteria.andGreaterThanOrEqualTo("startTime", searchMap.get("startTime"));
            }
            if (searchMap.get("endTime") != null) {
                criteria.andLessThanOrEqualTo("endTime", searchMap.get("endTime"));
            }
        }
        return example;
    }

}
