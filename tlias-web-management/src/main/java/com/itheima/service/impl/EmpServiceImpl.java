package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.mapper.EmpExprMapper;
import com.itheima.mapper.EmpMapper;
import com.itheima.pojo.*;
import com.itheima.service.EmpLogService;
import com.itheima.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;


import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class EmpServiceImpl implements EmpService {

    @Autowired
    private EmpMapper empMapper;

    @Autowired
    private EmpExprMapper empExprMapper;

    @Autowired
    private EmpLogService empLogService;

    /**
     * 原始分页查询方法
     */
   /* @Override
    public PageResult<Emp> page(Integer page, Integer pageSize) {
        Long total = empMapper.count();

        Integer start = (page - 1) * pageSize;
        List<Emp> rows = empMapper.list(start, pageSize);

        return new PageResult<Emp>(total, rows);
    }*/



    /*@Override
    public PageResult<Emp> page(Integer page, Integer pageSize, String name, Integer gender, LocalDate begin, LocalDate end) {
        PageHelper.startPage(page, pageSize);
        List<Emp> empList = empMapper.list(name, gender, begin, end);

        Page<Emp> p = (Page<Emp>) empList;
        return new PageResult<Emp>(p.getTotal(), p.getResult());
    }*/

    @Override
    public PageResult<Emp> page(EmpQueryParam empQueryParam) {
        PageHelper.startPage(empQueryParam.getPage(), empQueryParam.getPageSize());
        List<Emp> empList = empMapper.list(empQueryParam);

        Page<Emp> p = (Page<Emp>) empList;
        return new PageResult<Emp>(p.getTotal(), p.getResult());
    }

    @Transactional(rollbackFor = {Exception.class}) // 事务管理 - 默认出现运行时异常RuntimeException才会回滚
    @Override
    public void save(Emp emp) {
        try {
            // 1.保存基本信息
            emp.setCreateTime(LocalDateTime.now());
            emp.setUpdateTime(LocalDateTime.now());
            empMapper.insert(emp);

            //int i = 1 / 0; // 模拟异常，测试事务回滚

            // 2.保存工作经历信息
            List<EmpExpr> exprList = emp.getExprList();
            if (!CollectionUtils.isEmpty(exprList)) {
                // 遍历集合设置员工ID
                exprList.forEach(empExpr -> {
                    empExpr.setEmpId(emp.getId());
                });
                empExprMapper.insertBatch(exprList);
            }
        } finally {
            // 记录操作日志
            EmpLog empLog = new EmpLog(null, LocalDateTime.now(), "新增员工" + emp);
            empLogService.insertLog(empLog);
        }


    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void delete(List<Integer> ids) {
        // 1.批量删除员工基本信息
        empMapper.deleteByIds(ids);

        // 2.批量删除工作经历信息
        empExprMapper.deleteByEmpIds(ids);

    }

    /**
     * 根据ID查询员工信息
     */
    @Override
    public Emp getInfo(Integer id) {
        return empMapper.getById(id);
    }

    /**
     * 修改员工信息
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(Emp emp) {
        // 1.根据ID修改员工基本信息
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.updateById(emp);

        // 2.根据ID修改工作经历信息 - 先删再添加
        // 先根据ID删除原有工作经历
        empExprMapper.deleteByEmpIds(Arrays.asList(emp.getId()));

        // 再添加新的工作经历
        List<EmpExpr> exprList = emp.getExprList();
        if (!CollectionUtils.isEmpty(exprList)) {
            exprList.forEach(empExpr -> empExpr.setEmpId(emp.getId()));
            empExprMapper.insertBatch(exprList);
        }
    }

    /**
     * 查询全部员工信息
     */
    @Override
    public List<Emp> findAll() {
        return empMapper.findAll();
    }
}
