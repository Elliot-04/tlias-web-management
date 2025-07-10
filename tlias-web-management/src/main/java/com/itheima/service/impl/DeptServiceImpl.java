package com.itheima.service.impl;

import com.itheima.exception.DeleteDeptException;
import com.itheima.mapper.DeptMapper;
import com.itheima.pojo.Dept;
import com.itheima.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptMapper deptMapper;

    /**
     * 查询所有部门
     */
    @Override
    public List<Dept> finaAll() {
        return deptMapper.findAll();
    }

    /**
     * 删除部门
     */
    @Override
    public void deleteById(Integer id) {
        Integer empCount = deptMapper.empCountByDeptId(id);
        if (empCount > 0) {
            throw new DeleteDeptException("该部门下有员工，不能删除!");
        }
        deptMapper.deleteById(id);
    }

    /**
     * 添加部门
     */
    @Override
    public void add(Dept dept) {
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());
        deptMapper.insert(dept);
    }

    /**
     * 根据ID查询部门信息
     */
    @Override
    public Dept getById(Integer id) {
        return deptMapper.getById(id);
    }

    /**
     * 修改部门信息
     */
    @Override
    public void update(Dept dept) {
        dept.setUpdateTime(LocalDateTime.now());
        deptMapper.update(dept);
    }
}
