package com.sxx.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sxx.entity.Category;
import com.sxx.service.CategoryService;
import com.sxx.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/page")
    public R<Page<Category>> page(int page,int pageSize){
        log.info("page:"+page+"pageSize"+pageSize);
        Page<Category> pageInfo = new Page<>(page,pageSize);
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Category::getSort);
        categoryService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }
    @PostMapping
    public R<String> save(@RequestBody Category category){
        log.info(category.toString());
        categoryService.save(category);
        return R.success("添加成功");
    }
    @DeleteMapping
    public R<String> delete(Long ids){
        log.info(ids.toString());
        categoryService.remove(ids);
        return R.success("删除成功");
    }
    @PutMapping
    public R<String> modify(@RequestBody Category category){
        log.info(category.toString());
        categoryService.updateById(category);
        return R.success("修改成功");
    }
    @GetMapping("list")
    public R<List<Category>> list(Category category){
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getType,category.getType());
        List<Category> list = categoryService.list(queryWrapper);
        return R.success(list);
    }
}
