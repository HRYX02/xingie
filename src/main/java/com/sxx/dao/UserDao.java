package com.sxx.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxx.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author SHIXINXI
 * @description
 * @create 2023-05-10-20:38
 */

@Mapper
public interface UserDao extends BaseMapper<User> {
}
