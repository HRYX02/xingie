package com.sxx.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sxx.common.R;
import com.sxx.dto.SetmealDto;
import com.sxx.entity.Category;
import com.sxx.entity.Setmeal;
import com.sxx.service.CategoryService;
import com.sxx.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SetmealService setmealService;
    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto) {
        log.info(setmealDto.toString());
        setmealService.saveSetmealWithDish(setmealDto);
        return R.success("添加成功");
    }
    @GetMapping("/page")
    public R<Page<SetmealDto>> page(int page,int pageSize,String name) {
        log.info(page+pageSize+name);
        Page<Setmeal> pageInfo = new Page<>(page,pageSize);
        Page<SetmealDto> setmealDtoPage = new Page<>();
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(name),Setmeal::getName,name);
        setmealService.page(pageInfo,queryWrapper);

        BeanUtils.copyProperties(pageInfo,setmealDtoPage,"records");
        List<Setmeal> records = pageInfo.getRecords();
        List<SetmealDto> list = records.stream().map((record) -> {
            //TODO 不仅需要对page进行copy，还需要对套餐进行copy
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(record,setmealDto);
            Long categoryId = record.getCategoryId();
            Category category = categoryService.getById(categoryId);
            setmealDto.setCategoryName(category.getName());
            return setmealDto;
        }).collect(Collectors.toList());
        setmealDtoPage.setRecords(list);
        return R.success(setmealDtoPage);
    }
    @DeleteMapping
    public R<String> delete(@RequestParam("ids") List<String> ids) {
        setmealService.removeSetmealWithDish(ids);
        return R.success("删除成功");
    }
    @PostMapping("/status/0")
    public R<String> prohibit(@RequestParam("ids") List<String> ids) {
        ids.stream().forEach((id) -> {
            Setmeal setmeal = new Setmeal();
            setmeal.setId(Long.parseLong(id));
            setmeal.setStatus(0);
            setmealService.updateById(setmeal);
        });
        return R.success("停售成功");
    }
    @PostMapping("/status/1")
    public R<String> open(@RequestParam("ids") List<String> ids) {
        ids.stream().forEach((id) -> {
            Setmeal setmeal = new Setmeal();
            setmeal.setId(Long.parseLong(id));
            setmeal.setStatus(1);
            setmealService.updateById(setmeal);
        });
        return R.success("起售成功");
    }
    @GetMapping("/{id}")
    public R<SetmealDto> get(@PathVariable Long id){
        SetmealDto setmealDto = setmealService.getSetmealWithDish(id);
        return R.success(setmealDto);
    }
    @PutMapping
    public R<String> update(@RequestBody SetmealDto setmealDto){
        log.info(setmealDto.toString());
        setmealService.updateSetmealWithDish(setmealDto);
        return R.success("修改成功");
    }
}