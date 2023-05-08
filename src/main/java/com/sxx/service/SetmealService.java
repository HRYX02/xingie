package com.sxx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sxx.dto.SetmealDto;
import com.sxx.entity.Setmeal;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    void saveSetmealWithDish(SetmealDto setmealDto);

    @Transactional(rollbackFor = Exception.class)
    void removeSetmealWithDish(List<String> ids);

    SetmealDto getSetmealWithDish(Long id);

    void updateSetmealWithDish(SetmealDto setmealDto);
}
