package com.itheima.service;

import com.itheima.pojo.ClazzOption;
import com.itheima.pojo.JobOption;

import java.util.List;
import java.util.Map;

public interface ReportService {

    /**
     * 统计员工职位人数
     */
    JobOption getEmpJobData();

    /**
     * 统计员工性别人数
     */
    List<Map<String, Object>> getEmpGenderData();

    /**
     * 统计各班级人数
     */
    ClazzOption getStudentCountData();

    /**
     * 统计学生学历人数
     */
    List<Map<String, Object>> getStudentDegreeData();

}
