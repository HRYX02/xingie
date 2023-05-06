package com.sxx.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sxx.entity.Employee;
import com.sxx.service.EmployeeService;
import com.sxx.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Resource
    private EmployeeService employeeService;
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
        String password = employee.getPassword();
        employee.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,employee.getUsername()).eq(Employee::getPassword,employee.getPassword());
        Employee employeeResult = employeeService.getOne(queryWrapper);
        if (employeeResult == null) {
            return R.error("账户或密码输入错误");
        }
        request.getSession().setAttribute("employeeResult",employeeResult.getId());
        return R.success(employee);
    };

    @PostMapping
    public R<String> addEmployee(HttpServletRequest request, @RequestBody Employee employee){
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        employeeService.save(employee);
        return R.success("添加成功");
    }

    @GetMapping("/page")
    public R<Page<Employee>> page(HttpServletRequest request ,int page, int pageSize, String name){
        log.info(page + "," + pageSize + "," + name);
        Page<Employee> pageInfo = new Page(page,pageSize);
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name != null,Employee::getName,name);
        employeeService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id){
        Employee employee = employeeService.getById(id);
        return R.success(employee);
    }
    @PutMapping()
    public R<String> update(HttpServletRequest request,@RequestBody Employee employee){
        log.info(employee.toString());
        boolean update = employeeService.updateById(employee);
        if (update) {
            return R.success("修改成功");
        }
        return R.error("修改失败");
    }
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        request.getSession().removeAttribute("employeeResult");
        return R.success("退出成功");
    }
}
