package com.itheima.service;

import com.itheima.pojo.PageResult;
import com.itheima.pojo.Student;
import com.itheima.pojo.StudentQueryParam;
import org.springframework.stereotype.Service;

import java.util.List;

public interface StudentService {
    /**
     * 分页查询
     */
    PageResult<Student> page(StudentQueryParam studentQueryParam);

    /**
     * 添加学生
     */
    void add(Student student);

    /**
     * 根据ID查询学生
     */
    Student getById(Integer id);

    /**
     * 修改学生
     */
    void update(Student student);

    /**
     * 删除学生
     */
    void delete(List<Integer> ids);

    /**
     * 修改学生违纪分数（+score）
     */
    void updateViolation(Integer id, Integer score);
}
