package com.itheima.aop;

import com.itheima.mapper.OperateLogMapper;
import com.itheima.pojo.OperateLog;
import com.itheima.utils.CurrentHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Slf4j
@Component
@Aspect // 声明为切面类
public class LogAspect {

    @Autowired
    private OperateLogMapper operateLogMapper;

    @Around("@annotation(com.itheima.anno.Log)")
    public Object recordLog(ProceedingJoinPoint joinPoint) throws Throwable {

        // 方法开始执行时间
        long begin = System.currentTimeMillis();

        // 调用目标方法运行
        Object result = joinPoint.proceed();

        // 方法结束执行时间
        long end = System.currentTimeMillis();

        // 获取方法执行时长
        Long costTime = end - begin;

        // 构造日志对象
        OperateLog olog = new OperateLog();
        olog.setOperateEmpId(CurrentHolder.getCurrentId()); //
        olog.setOperateTime(LocalDateTime.now());
        olog.setClassName(joinPoint.getTarget().getClass().getName());
        olog.setMethodName(joinPoint.getSignature().getName());
        olog.setMethodParams(Arrays.toString(joinPoint.getArgs()));
        olog.setReturnValue(result != null ? result.toString() : "void");
        olog.setCostTime(costTime);

        // 插入日志数据到数据库
        operateLogMapper.insert(olog);
        log.info("记录操作日志: {}", olog);

        return result;
    }
}