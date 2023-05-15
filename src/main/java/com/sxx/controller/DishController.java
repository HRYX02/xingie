package com.sxx.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sxx.common.R;
import com.sxx.dto.DishDto;
import com.sxx.entity.Category;
import com.sxx.entity.Dish;
import com.sxx.entity.DishFlavor;
import com.sxx.service.CategoryService;
import com.sxx.service.DishFlavorService;
import com.sxx.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private DishFlavorService dishFlavorService;

    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto){
        log.info(dishDto.toString());
        dishService.saveWithFlavor(dishDto);
        return R.success("保存成功");
    }

    @GetMapping("/page")
    public R<Page<DishDto>> page(Integer page, Integer pageSize,String name){
        log.info("page:" + page + ",pageSize:" + pageSize);
        Page<Dish> pageInfo = new Page<>(page,pageSize);
        Page<DishDto> dishDtoPage = new Page<>();

        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Dish::getUpdateTime);
        queryWrapper.like(StringUtils.isNotEmpty(name),Dish::getName,name);
        dishService.page(pageInfo,queryWrapper);

        BeanUtils.copyProperties(pageInfo,dishDtoPage,"records");
        List<Dish> records = pageInfo.getRecords();
        List<DishDto> list = records.stream().map((record) -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(record, dishDto);

            Long categoryId = record.getCategoryId();
            Category category = categoryService.getById(categoryId);
            dishDto.setCategoryName(category.getName());

            return dishDto;
        }).collect(Collectors.toList());
        dishDtoPage.setRecords(list);
        return R.success(dishDtoPage);
    }
    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id){
        log.info(id.toString());
        DishDto dishDto = dishService.getByIdWithFlavor(id);
        return R.success(dishDto);
    }
    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto){
        log.info(dishDto.toString());
        dishService.updateWithFlavor(dishDto);
        return R.success("修改成功");
    }
    @PostMapping("/status/0")
    public R<String> prohibit(@RequestParam("ids") List<String> ids){
        ids.stream().forEach((id) -> {
            Dish dish = new Dish();
            dish.setId(Long.parseLong(id));
            dish.setStatus(0);
            dishService.updateById(dish);
        });
        return R.success("停售成功");
    }
    @PostMapping("/status/1")
    public R<String> open(@RequestParam("ids") List<String> ids){
        ids.stream().forEach((id) -> {
            Dish dish = new Dish();
            dish.setId(Long.parseLong(id));
            dish.setStatus(1);
            dishService.updateById(dish);
        });
        return R.success("起售成功");
    }
    @DeleteMapping
    public R<String> delete(@RequestParam("ids") List<String> ids){
        log.info("删除单号"+ids.toString());
        ids.stream().forEach((id) -> {
            long lId = Long.parseLong(id);
            dishService.removeWithFlavor(lId);
        });
        return R.success("删除成功");
    }

    /**
    @GetMapping("/list")
    public R<List<Dish>> list(Dish dish){
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId() != null,Dish::getCategoryId,dish.getCategoryId());
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
        List<Dish> list = dishService.list(queryWrapper);
        return R.success(list);
    }
     */
    @GetMapping("/list")
    public R<List<DishDto>> list(Dish dish){
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId() != null,Dish::getCategoryId,dish.getCategoryId());
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
        List<Dish> list = dishService.list(queryWrapper);
        List<DishDto> dishDtoList = list.stream().map((item) -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);

            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            if (category == null) {
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }
            Long dishId = item.getId();
            LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(DishFlavor::getDishId,dishId);
            List<DishFlavor> dishFlavorList = dishFlavorService.list(lambdaQueryWrapper);
            dishDto.setFlavors(dishFlavorList);
            return dishDto;
        }).collect(Collectors.toList());
        return R.success(dishDtoList);
    }
}
