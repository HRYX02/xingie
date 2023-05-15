package com.sxx.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxx.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author SHIXINXI
 * @description
 * @create 2023-05-15-17:53
 */

@Mapper
public interface OrdersDao extends BaseMapper<Orders> {
}
