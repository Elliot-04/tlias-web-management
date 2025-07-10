package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.exception.DeleteClazzException;
import com.itheima.mapper.ClazzMapper;
import com.itheima.pojo.Clazz;
import com.itheima.pojo.ClazzQueryParam;
import com.itheima.pojo.PageResult;
import com.itheima.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClazzServiceImpl implements ClazzService {

    @Autowired
    private ClazzMapper clazzMapper;

    /**
     * 分页查询班级信息
     */
    @Override
    public PageResult<Clazz> page(ClazzQueryParam clazzQueryParam) {
        PageHelper.startPage(clazzQueryParam.getPage(), clazzQueryParam.getPageSize());
        List<Clazz> clazzList = clazzMapper.list(clazzQueryParam);

        clazzList.forEach(clazz -> {
            if (clazz.getEndDate().isBefore(LocalDate.now())) {
                clazz.setStatus("已结课"); // 已结束
            } else if (clazz.getBeginDate().isAfter(LocalDate.now())) {
                clazz.setStatus("未开班"); // 未开始
            } else {
                clazz.setStatus("在读中"); // 进行中
            }
        });

        Page<Clazz> p = (Page<Clazz>) clazzList;
        return new PageResult<Clazz>(p.getTotal(), p.getResult());
    }

    /**
     * 添加班级信息
     */
    @Override
    public void add(Clazz clazz) {
        clazz.setCreateTime(LocalDateTime.now());
        clazz.setUpdateTime(LocalDateTime.now());
        clazzMapper.insert(clazz);
    }

    /**
     * 根据ID查询班级
     */
    @Override
    public Clazz getById(Integer id) {
        return clazzMapper.getById(id);
    }

    /**
     * 修改班级信息
     */
    @Override
    public void update(Clazz clazz) {
        clazz.setUpdateTime(LocalDateTime.now());
        clazzMapper.update(clazz);
    }

    /**
     * 删除班级
     */
    @Override
    public void deleteById(Integer id) {
        // 1. 检查该班级下的学生人数
        Integer stuCount = clazzMapper.stuCountByClazzId(id);

        // 2. 如果有学生，则抛出业务异常
        if (stuCount > 0) {
            throw new DeleteClazzException("对不起，该班级下有学生，不能直接删除!（班级ID：" + id + "）");
        }

        // 3. 如果没有学生，则执行删除操作
        clazzMapper.deleteById(id);
    }

    /**
     * 查询所有班级
     */
    @Override
    public List<Clazz> findAll() {
        return clazzMapper.findAll();
    }
}
















