package com.sxx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sxx.entity.Orders;

/**
 * @author SHIXINXI
 * @description
 * @create 2023-05-15-17:54
 */
public interface OrdersService extends IService<Orders> {
    void submit(Orders orders);
}
