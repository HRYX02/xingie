package com.sxx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sxx.dto.SetmealDto;
import com.sxx.entity.Setmeal;

public interface SetmealService extends IService<Setmeal> {
    void saveSetmealWithDish(SetmealDto setmealDto);
}
