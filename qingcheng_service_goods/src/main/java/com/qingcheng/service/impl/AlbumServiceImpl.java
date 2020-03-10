package com.qingcheng.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.qingcheng.dao.AlbumMapper;
import com.qingcheng.entity.AlbumImageItem;
import com.qingcheng.entity.PageResult;
import com.qingcheng.pojo.goods.Album;
import com.qingcheng.service.goods.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    private AlbumMapper albumMapper;

    public List<Album> findAll() {
        return albumMapper.selectAll();
    }

    public PageResult<Album> findPage(int page, int size) {
        PageHelper.startPage(page, size);
        Page<Album> albums = (Page<Album>) albumMapper.selectAll();
        return new PageResult<Album>(albums.getTotal(), albums.getResult());
    }

    public List<Album> findList(Map<String, Object> searchMap) {
        Example example = createExample(searchMap);
        return albumMapper.selectByExample(example);
    }

    public PageResult<Album> findPage(Map<String, Object> searchMap, int page, int size) {
        PageHelper.startPage(page, size);
        Example example = createExample(searchMap);
        Page<Album> albums = (Page<Album>) albumMapper.selectByExample(example);
        return new PageResult<Album>(albums.getTotal(), albums.getResult());
    }

    public Album findById(Long id) {
        return albumMapper.selectByPrimaryKey(id);
    }

    public void add(Album album) {
        albumMapper.insert(album);
    }

    public void update(Album album) {
        albumMapper.updateByPrimaryKeySelective(album);
    }

    public void delete(Long id) {
        albumMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void addImageItem(Album album) {
        // 查出相册
        Album al = albumMapper.selectByPrimaryKey(album.getId());
        // 包装相册图片对象
        AlbumImageItem item = new AlbumImageItem();
        item.setUrl(album.getImageItems());
        item.setUid(System.currentTimeMillis());
        item.setStatus("success");
        // 加入到图片列表中
        List<AlbumImageItem> albumImageItems = JSON.parseArray(al.getImageItems(), AlbumImageItem.class);
        if(albumImageItems == null){
            albumImageItems = new ArrayList<>();
        }
        albumImageItems.add(item);
        al.setImageItems(JSON.toJSONString(albumImageItems));
        albumMapper.updateByPrimaryKeySelective(al);
    }

    @Override
    public void deleteImageItem(Album album) {
        Album al = albumMapper.selectByPrimaryKey(album.getId());
        List<AlbumImageItem> albumImageItems = JSON.parseArray(al.getImageItems(), AlbumImageItem.class);
        for (AlbumImageItem item : albumImageItems) {
            if (item.getUrl().equals(album.getImageItems())) {
                albumImageItems.remove(item);
                break;
            }
        }
        al.setImageItems(JSON.toJSONString(albumImageItems));
        albumMapper.updateByPrimaryKeySelective(al);
    }

    private Example createExample(Map<String, Object> searchMap) {
        Example example = new Example(Album.class);
        Example.Criteria criteria = example.createCriteria();
        if (searchMap != null) {
            // 相册名称
            if (searchMap.get("title") != null && !"".equals(searchMap.get("title"))) {
                criteria.andLike("title", "%" + searchMap.get("title") + "%");
            }
            // 相册封面
            if (searchMap.get("image") != null && !"".equals(searchMap.get("image"))) {
                criteria.andLike("image", "%" + searchMap.get("image") + "%");
            }
            // 图片列表
            if (searchMap.get("imageItems") != null && !"".equals(searchMap.get("imageItems"))) {
                criteria.andLike("imageItems", "%" + searchMap.get("imageItems") + "%");
            }
        }
        return example;
    }

}
