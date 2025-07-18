package com.itheima.service;

import com.itheima.pojo.Emp;
import com.itheima.pojo.EmpQueryParam;
import com.itheima.pojo.LoginInfo;
import com.itheima.pojo.PageResult;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

public interface EmpService {
    // PageResult<Emp> page(Integer page, Integer pageSize, String name, Integer gender, LocalDate begin, LocalDate end);

    /**
     * 分页查询
     */
    PageResult<Emp> page(EmpQueryParam empQueryParam);

    /**
     * 新增员工
     */
    void save(Emp emp);

    /**
     * 批量删除员工
     */
    void delete(List<Integer> ids);

    /**
     * 根据ID查询员工信息
     */
    Emp getInfo(Integer id);

    /**
     * 修改员工信息
     */
    void update(Emp emp);

    /**
     * 查询全部员工信息
     */
    List<Emp> findAll();

    /**
     * 员工登录
     */
    LoginInfo login(Emp emp);
}
