package com.itheima.exception;

/**
 * 删除部门异常
 * 当部门下关联有员工时，不能删除部门
 */
public class DeleteDeptException extends RuntimeException{
    public DeleteDeptException(String message) {
        super(message);
    }
}
