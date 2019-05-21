package com.tensquare.base.controller;

import entity.Result;
import entity.StatusCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理类
 */
@ControllerAdvice // 异常已经生效，不用配置
public class BaseExceptionHandler{

    /**
     * 异常处理方法
     */
    //@ExceptionHandler(value = NullPointerException.class) //该方法捕获所有空指针异常
    @ExceptionHandler(value = Exception.class)   //该方法捕获所有异常
    @ResponseBody
    public Result error(Exception e){
        return new Result(false, StatusCode.ERROR,"操作失败："+e.getMessage());
    }

}
