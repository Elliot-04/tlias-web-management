package com.itheima.service.impl;

import com.itheima.mapper.ClazzMapper;
import com.itheima.mapper.EmpMapper;
import com.itheima.pojo.ClazzOption;
import com.itheima.pojo.JobOption;
import com.itheima.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private EmpMapper empMapper;

    @Autowired
    private ClazzMapper clazzMapper;

    /**
     * 统计员工职位人数
     */
    @Override
    public JobOption getEmpJobData() {
        // 1.调用mapper接口，获取统计数据
        List<Map<String, Object>> list = empMapper.countEmpJobData();   // map: pos=职位名称, num=数量

        // 2.组装结果并返回
        List<Object> jobList = list.stream().map(dataMap -> dataMap.get("pos")).toList();
        List<Object> dataList = list.stream().map(dataMap -> dataMap.get("num")).toList();

        return new JobOption(jobList, dataList);
    }

    /**
     * 统计员工性别人数
     */
    @Override
    public List<Map<String, Object>> getEmpGenderData() {
        return empMapper.countEmpGenderData();   // map: name=性别名称, value=人数
    }

    /**
     * 统计各班级学生人数
     */
    @Override
    public ClazzOption getStudentCountData() {
        List<Map<String, Object>> list = clazzMapper.countStudentCountData(); // map: clazzName=班级名称, studentNum=学生人数
        List<Object> clazzList = list.stream().map(dataMap -> dataMap.get("clazzName")).toList();
        List<Object> dataList = list.stream().map(dataMap -> dataMap.get("studentNum")).toList();
        return new ClazzOption(clazzList, dataList);
    }

    /**
     * 统计学生学历人数
     */
    @Override
    public List<Map<String, Object>> getStudentDegreeData() {
        return clazzMapper.countStudentDegreeData(); // map: name=学历, value=人数
    }
}
