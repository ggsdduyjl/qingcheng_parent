package com.qingcheng.controller.goods;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qingcheng.entity.Goods;
import com.qingcheng.entity.PageResult;
import com.qingcheng.entity.Result;
import com.qingcheng.exceptions.AdminException;
import com.qingcheng.pojo.goods.Spu;
import com.qingcheng.service.goods.SpuService;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/spu")
public class SpuController {

    @Reference
    private SpuService spuService;

    @GetMapping("/findAll")
    public List<Spu> findAll() {
        return spuService.findAll();
    }

    @GetMapping("/findPage")
    public PageResult<Spu> findPage(int page, int size) {
        return spuService.findPage(page, size);
    }

    @PostMapping("/findList")
    public List<Spu> findList(@RequestBody Map<String, Object> searchMap) {
        return spuService.findList(searchMap);
    }

    @PostMapping("/findPage")
    public PageResult<Spu> findPage(@RequestBody Map<String, Object> searchMap, int page, int size) {
        return spuService.findPage(searchMap, page, size);
    }

    @GetMapping("/findById")
    public Spu findById(String id) {
        return spuService.findById(id);
    }


    @PostMapping("/add")
    public Result add(@RequestBody Spu spu) {
        spuService.add(spu);
        return new Result();
    }

    @PostMapping("/update")
    public Result update(@RequestBody Spu spu) {
        spuService.update(spu);
        return new Result();
    }

    @GetMapping("/delete")
    public Result delete(String id) {
        spuService.delete(id);
        return new Result();
    }

    @RequestMapping("/saveGoods")
    public Result saveGoods(@RequestBody Goods goods) {
        spuService.saveGoods(goods);
        return new Result();
    }

    @RequestMapping("/findGoodsById")
    public Goods findGoodsById(String id) {
        return spuService.findGoodsById(id);
    }

    @RequestMapping("/audit")
    public Result audit(@RequestBody Map<String, String> map) {
        spuService.audit(map.get("id"), map.get("status"), map.get("message"));
        return Result.success();
    }

    @RequestMapping("/pull")
    public Result pull(String id) {
        spuService.pull(id);
        return Result.success();
    }

    @RequestMapping("/put")
    public Result put(String id) throws AdminException {
        spuService.put(id);
        return Result.success();
    }

    @RequestMapping("/pullMany")
    public Result pull(@RequestBody String[] ids) {
        int count =  spuService.pullMany(ids);
        return new Result(0,"下架了" + count + "个商品");
    }

    @RequestMapping("/putMany")
    public Result putMany(@RequestBody String[] ids) {
        int count =  spuService.putMany(ids);
        return new Result(0,"上架了" + count + "个商品");
    }

    @RequestMapping("/deleteGoods")
    public Result deleteGoods(String id) {
        spuService.deleteGoods(id);
        return Result.success();
    }

    @RequestMapping("/restore")
    public Result restore(String id) {
        spuService.restore(id);
        return Result.success();
    }

}
