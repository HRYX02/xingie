package com.sxx.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxx.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DishDao extends BaseMapper<Dish> {

}