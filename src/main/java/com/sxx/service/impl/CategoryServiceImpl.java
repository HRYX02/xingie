package com.sxx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxx.common.BusinessException;
import com.sxx.dao.CategoryDao;
import com.sxx.entity.Category;
import com.sxx.entity.Dish;
import com.sxx.entity.Setmeal;
import com.sxx.service.CategoryService;
import com.sxx.service.DishService;
import com.sxx.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, Category> implements CategoryService {

    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;

    public boolean remove(Long id){
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();

        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);

        int dishCount = dishService.count(dishLambdaQueryWrapper);
        int setmealCount = setmealService.count(setmealLambdaQueryWrapper);

        if (dishCount > 0) {
            throw new BusinessException("当前分类下关联了菜品，不可删除");
        }
        if (setmealCount > 0) {
            throw new BusinessException("当前分类下关联了套餐，不可删除");
        }

        return super.removeById(id);
    }
}