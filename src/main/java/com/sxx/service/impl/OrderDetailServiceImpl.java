package com.sxx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxx.dao.OrderDetailDao;
import com.sxx.entity.OrderDetail;
import com.sxx.service.OrderDetailService;
import org.springframework.stereotype.Service;

/**
 * @author SHIXINXI
 * @description
 * @create 2023-05-15-17:56
 */

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailDao, OrderDetail> implements OrderDetailService {
}
