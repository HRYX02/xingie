package com.sxx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sxx.entity.Category;

public interface CategoryService extends IService<Category> {
    boolean remove(Long id);
}
