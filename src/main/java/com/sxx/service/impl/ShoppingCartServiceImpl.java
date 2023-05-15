package com.sxx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxx.dao.ShoppingCartDao;
import com.sxx.entity.ShoppingCart;
import com.sxx.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author SHIXINXI
 * @description
 * @create 2023-05-11-16:12
 */

@Slf4j
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartDao, ShoppingCart> implements ShoppingCartService {
}
