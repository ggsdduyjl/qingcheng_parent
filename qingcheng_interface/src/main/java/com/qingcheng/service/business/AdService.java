package com.qingcheng.service.business;

import com.qingcheng.entity.PageResult;
import com.qingcheng.pojo.business.Ad;

import java.util.*;

public interface AdService {

    List<Ad> findAll();

    PageResult<Ad> findPage(int page, int size);

    List<Ad> findList(Map<String, Object> searchMap);

    PageResult<Ad> findPage(Map<String, Object> searchMap, int page, int size);

    Ad findById(Integer id);

    void add(Ad ad);

    void update(Ad ad);

    void delete(Integer id);

    /**
     * 根据广告位子获取广告列表
     *
     * @param position 广告位置
     * @return 广告列表
     */
    List<Ad> findByPosition(String position);

}
