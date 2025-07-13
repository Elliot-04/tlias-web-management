package com.itheima.mapper;

import com.itheima.pojo.LogQueryParam;
import com.itheima.pojo.OperateLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LogMapper {
    @Select("select l.*, e.name operateEmpName from operate_log l left join emp e on l.operate_emp_id = e.id")
    List<OperateLog> list(LogQueryParam logQueryParam);
}
