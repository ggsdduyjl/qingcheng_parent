package com.qingcheng.controller.goods;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qingcheng.entity.PageResult;
import com.qingcheng.entity.Result;
import com.qingcheng.pojo.goods.Brand;
import com.qingcheng.service.goods.BrandService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/brand")
public class BrandController {

    @Reference
    private BrandService brandService;

    @RequestMapping("findAll")
    public List<Brand> findAll() {
        return brandService.findAll();
    }

    @RequestMapping("findPage")
    public PageResult<Brand> findPage(@RequestBody Map<String, Object> searchMap, Integer page, Integer size) {
        if (page == null || page == 0) {
            page = 1;
        }
        if (size == null || size == 0) {
            size = 10;
        }
        return brandService.findPage(searchMap, page, size);
    }

    @PostMapping("findList")
    public List<Brand> findList(@RequestBody Map<String, Object> searchMap) {
        return brandService.findList(searchMap);
    }

    @RequestMapping("findById")
    public Brand findById(Integer id) {
        return brandService.findById(id);
    }

    @RequestMapping("add")
    public Result add(@RequestBody Brand brand) {
        brandService.add(brand);
        return Result.success();
    }

    @RequestMapping("update")
    public Result update(@RequestBody Brand brand) {
        brandService.update(brand);
        return Result.success();
    }

    @RequestMapping("delete")
    public Result delete(Integer id) {
        brandService.delete(id);
        return Result.success();
    }
}
