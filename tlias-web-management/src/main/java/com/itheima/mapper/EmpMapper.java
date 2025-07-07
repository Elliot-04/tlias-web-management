package com.itheima.mapper;

import com.itheima.pojo.Emp;
import com.itheima.pojo.EmpQueryParam;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface EmpMapper {

    // ------------------------------------ 原始分页查询方法 -------------------------------------------
    // @Select("select count(*) from emp e left join dept d on e.dept_id = d.id")
    // public Long count();
    //
    // @Select("select e.*, d.name deptName from emp e left join dept d on e.dept_id = d.id order by e.update_time desc limit #{start}, #{pageSize}")
    // public List<Emp> list(Integer start,Integer pageSize);

    // @Select("select e.*, d.name deptName from emp e left join dept d on e.dept_id = d.id order by e.update_time desc")
    // public List<Emp> list(String name, Integer gender, LocalDate begin, LocalDate end);

    /**
     * 条件查询员工信息
     */
    List<Emp> list(EmpQueryParam empQueryParam);

    /**
     * 新增员工基本信息
     */
    @Options(useGeneratedKeys = true, keyProperty = "id") // 主键返回
    @Insert("insert into emp(username, name, gender, phone, job, salary, image, entry_date, dept_id, create_time, update_time)" +
            "values(#{username}, #{name}, #{gender}, #{phone}, #{job}, #{salary}, #{image}, #{entryDate}, #{deptId}, #{createTime}, #{updateTime})")
    void insert(Emp emp);

    /**
     * 根据ID批量删除员工基本信息
     */
    void deleteByIds(List<Integer> ids);

    /**
     * 根据ID查询员工信息以及工作经历信息
     */
    Emp getById(Integer id);

    /**
     * 根据ID更新员工基本信息
     */
    void updateById(Emp emp);

    /**
     * 统计员工职位人数
     */
    List<Map<String, Object>> countEmpJobData();

    /**
     * 统计员工性别人数
     */
    List<Map<String, Object>> countEmpGenderData();

    /**
     * 查询全部员工信息
     */
    @Select("select id, username, password, name, gender, phone, job, salary, image, entry_date, dept_id, create_time, update_time from emp")
    List<Emp> findAll();
}
