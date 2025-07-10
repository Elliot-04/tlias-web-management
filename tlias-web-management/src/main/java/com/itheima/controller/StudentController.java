package com.itheima.controller;

import com.itheima.pojo.*;
import com.itheima.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/students")
@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    /**
     * 分页查询
     */
    @GetMapping
    public Result page(StudentQueryParam studentQueryParam) {
        log.info("分页查询学生信息：{},{},{},{},{}", studentQueryParam);
        PageResult<Student> pageResult = studentService.page(studentQueryParam);
        return Result.success(pageResult);
    }

    /**
     * 添加学生
     */
    @PostMapping
    public Result add(@RequestBody Student student) {
        log.info("添加学生：{}", student);
        studentService.add(student);
        return Result.success();
    }

    /**
     * 根据ID查询学生
     */
    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id) {
        log.info("根据ID查询学生：{}", id);
        Student student = studentService.getById(id);
        return Result.success(student);
    }

    /**
     * 修改学生信息
     */
    @PutMapping
    public Result update(@RequestBody Student student) {
        log.info("修改学生：{}", student);
        studentService.update(student);
        return Result.success();
    }

    /**
     * 删除学生
     */
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable List<Integer> ids) {
        log.info("删除学生：{}", ids);
        studentService.delete(ids);
        return Result.success();
    }

    /**
     * 修改学生违纪分数（+score）
     */
    @PutMapping("/violation/{id}/{score}")
    public Result updateViolation(@PathVariable Integer id, @PathVariable String score) {
        if (score.equals("null") || score == null) {
            score = "0";
        }
        Integer scoreNum = Integer.parseInt(score);
        log.info("修改学生违纪信息：id={}, score+{}", id, scoreNum);
        studentService.updateViolation(id, scoreNum);
        return Result.success();
    }



}
