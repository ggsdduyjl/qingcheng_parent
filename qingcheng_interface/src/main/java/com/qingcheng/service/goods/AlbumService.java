package com.qingcheng.service.goods;

import com.qingcheng.entity.PageResult;
import com.qingcheng.pojo.goods.Album;

import java.util.*;

public interface AlbumService {

    List<Album> findAll();

    PageResult<Album> findPage(int page, int size);

    List<Album> findList(Map<String, Object> searchMap);

    PageResult<Album> findPage(Map<String, Object> searchMap, int page, int size);

    Album findById(Long id);

    void add(Album album);

    void update(Album album);

    void delete(Long id);

    /**
     * 往相册中添加图片
     *
     * @param album id：相册id imageItems：上传图片的url
     */
    void addImageItem(Album album);

    /**
     * 删除相册中的图片
     *
     * @param album id：相册id imageItems：图片的url
     */
    void deleteImageItem(Album album);

}
