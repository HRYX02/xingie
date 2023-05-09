package com.sxx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxx.dao.SetmealDao;
import com.sxx.dto.SetmealDto;
import com.sxx.entity.Setmeal;
import com.sxx.entity.SetmealDish;
import com.sxx.service.SetmealDishService;
import com.sxx.service.SetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author SxxStar
 * @description 套餐管理的实现类
 */
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealDao, Setmeal> implements SetmealService {

    @Autowired
    private SetmealDishService setmealDishService;

    /**
     * @description setmealDto = setmeal + setmealDish所以需要将setmealDto的SetmealDishes提取出来并将setmeal的id赋值给SetmealDishes的setmealId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveSetmealWithDish(SetmealDto setmealDto) {
        this.save(setmealDto);
        Long id = setmealDto.getId();
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map((setmealDish) -> {
            setmealDish.setSetmealId(id);
            return setmealDish;
        }).collect(Collectors.toList());
        setmealDishService.saveBatch(setmealDishes);
    }

    /**
     * @description 删除套餐的同时需要将套餐中对应的菜品删除
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeSetmealWithDish(List<String> ids) {
        ids.stream().forEach((id) -> {
            this.removeById(id);
            LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SetmealDish::getSetmealId,id);
            setmealDishService.remove(queryWrapper);
        });
    }

    /**
     * @description setmeal + setmealDish = setmealDto
     */
    @Override
    public SetmealDto getSetmealWithDish(Long id) {
        SetmealDto setmealDto = new SetmealDto();
        Setmeal setmeal = this.getById(id);
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId,id);
        List<SetmealDish> list = setmealDishService.list(queryWrapper);
        BeanUtils.copyProperties(setmeal,setmealDto);
        setmealDto.setSetmealDishes(list);
        return setmealDto;
    }

    /**
     * @description 修改套餐前需要将原来套餐中对应的菜品删除，再添加新的菜品
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSetmealWithDish(SetmealDto setmealDto){
        this.updateById(setmealDto);
        Long id = setmealDto.getId();
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId,id);
        setmealDishService.remove(queryWrapper);
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map((setmealDish) -> {
           setmealDish.setSetmealId(id);
           return setmealDish;
        }).collect(Collectors.toList());
        setmealDishService.saveBatch(setmealDishes);
    }
}