package com.itheima.controller;

import com.itheima.anno.Log;
import com.itheima.pojo.Dept;
import com.itheima.pojo.Result;
import com.itheima.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequestMapping("/depts")
@RestController
public class DeptController {

    // private static final Logger log = LoggerFactory.getLogger(DeptController.class);

    @Autowired
    private DeptService deptService;

    // @RequestMapping(value = "/depts", method = RequestMethod.GET)
    @GetMapping
    public Result list() {
        // System.out.println("查询所有部门信息");
        log.info("查询所有部门信息");
        List<Dept> deptList = deptService.finaAll();
        return Result.success(deptList);
    }

    @Log
    @DeleteMapping
    public Result delete(Integer id) {
        // System.out.println("根据ID删除部门：" + id);
        log.info("根据ID删除部门：{}", id);
        deptService.deleteById(id);
        return Result.success();
    }

    @Log
    @PostMapping
    public Result add(@RequestBody Dept dept) {
        // System.out.println("添加部门：" + dept);
        log.info("添加部门：{}", dept);
        deptService.add(dept);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id) {
        // System.out.println("根据ID查询部门：" + id);
        log.info("根据ID查询部门：{}", id);
        Dept dept = deptService.getById(id);
        return Result.success(dept);
    }

    @Log
    @PutMapping
    public Result update(@RequestBody Dept dept) {
        // System.out.println("修改部门：" + dept);
        log.info("修改部门：{}", dept);
        deptService.update(dept);
        return Result.success();
    }
}
