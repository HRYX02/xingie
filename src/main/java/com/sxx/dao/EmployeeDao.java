package com.sxx.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxx.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeDao extends BaseMapper<Employee> {

}
