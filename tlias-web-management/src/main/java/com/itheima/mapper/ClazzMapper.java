package com.itheima.mapper;

import com.itheima.pojo.Clazz;
import com.itheima.pojo.ClazzQueryParam;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface ClazzMapper {

    /**
     * 条件查询班级信息
     */
    List<Clazz> list(ClazzQueryParam clazzQueryParam);

    /**
     * 添加班级信息
     */
    @Insert("insert into clazz(name, room, begin_date, end_date, master_id, subject, create_time, update_time) values(#{name}, #{room}, #{beginDate}, #{endDate}, #{masterId}, #{subject}, #{createTime}, #{updateTime})")
    void insert(Clazz clazz);

    /**
     * 根据ID查询班级信息
     */
    @Select("select id, name, room, begin_date, end_date, master_id, subject, create_time, update_time from clazz where id = #{id}")
    Clazz getById(Integer id);

    /**
     * 修改班级信息
     */
    @Update("update clazz set name = #{name}, room = #{room}, begin_date = #{beginDate}, end_date = #{endDate}, master_id = #{masterId}, subject = #{subject}, update_time = #{updateTime} where id = #{id}")
    void update(Clazz clazz);

    /**
     * 删除班级信息
     */
    @Delete("delete from clazz where id = #{id}")
    void deleteById(Integer id);

    /**
     * 根据班级ID查询班级名下的学生数量
     */
    @Select("select count(*) from student where clazz_id = #{clazzId}")
    Integer stuCountByClazzId(Integer clazzId);

    /**
     * 查询所有班级信息
     */
    @Select("select id, name, room, begin_date, end_date, master_id, subject, create_time, update_time from clazz")
    List<Clazz> findAll();

    /**
     * 统计班级学生人数
     */
    List<Map<String, Object>> countStudentCountData();

    /**
     * 统计学生学历人数
     */
    List<Map<String, Object>> countStudentDegreeData();
}
