package com.qingcheng.service.goods;

import com.qingcheng.entity.PageResult;
import com.qingcheng.pojo.goods.Album;

import java.util.*;

/**
 * album业务逻辑层
 */
public interface AlbumService {

    List<Album> findAll();

    PageResult<Album> findPage(int page, int size);

    List<Album> findList(Map<String, Object> searchMap);

    PageResult<Album> findPage(Map<String, Object> searchMap, int page, int size);

    Album findById(Long id);

    void add(Album album);

    void update(Album album);

    void delete(Long id);

}
