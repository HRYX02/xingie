package com.sxx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxx.dao.SetmealDao;
import com.sxx.dto.SetmealDto;
import com.sxx.entity.Setmeal;
import com.sxx.entity.SetmealDish;
import com.sxx.service.SetmealDishService;
import com.sxx.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealDao, Setmeal> implements SetmealService {

    @Autowired
    private SetmealDishService setmealDishService;
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
}