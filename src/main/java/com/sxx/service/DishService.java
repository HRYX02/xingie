package com.sxx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sxx.dto.DishDto;
import com.sxx.entity.Dish;

public interface DishService extends IService<Dish> {
    void saveWithFlavor(DishDto dishDto);
    DishDto getByIdWithFlavor(Long id);
    void updateWithFlavor(DishDto dishDto);
    void removeWithFlavor(Long id);
}
