package com.itheima.mapper;

import com.itheima.pojo.Student;
import com.itheima.pojo.StudentQueryParam;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StudentMapper {

    /**
     * 条件查询学生信息
     */
    List<Student> list(StudentQueryParam studentQueryParam);

    /**
     * 添加学生信息
     */
    @Options(useGeneratedKeys = true, keyProperty = "id") // 主键返回
    void insert(Student student);

    /**
     * 根据ID查询学生信息
     */
    Student getById(Integer id);

    /**
     * 修改学生信息
     */
    void update(Student student);

    /**
     * 根据ID批量删除员工基本信息
     */
    void deleteByIds(List<Integer> ids);

    /**
     * 修改学生违纪分数（+score）
     */
    void updateViolation(Integer id, Integer score);
}
