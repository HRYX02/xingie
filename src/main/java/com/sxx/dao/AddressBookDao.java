package com.sxx.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxx.entity.AddressBook;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author SHIXINXI
 * @description
 * @create 2023-05-12-9:26
 */

@Mapper
public interface AddressBookDao extends BaseMapper<AddressBook> {
}
