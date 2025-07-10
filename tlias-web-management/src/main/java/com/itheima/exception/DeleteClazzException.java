package com.itheima.exception;

/**
 * 删除班级异常
 * 当班级下关联有学生时，不能删除班级
 */
public class DeleteClazzException extends RuntimeException{
    public DeleteClazzException(String message) {
        super(message);
    }
}
